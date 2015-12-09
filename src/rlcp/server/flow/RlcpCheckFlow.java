package rlcp.server.flow;

import rlcp.RlcpRequestBody;
import rlcp.check.CheckingResult;
import rlcp.check.ConditionForChecking;
import rlcp.check.RlcpCheckRequestBody;
import rlcp.check.RlcpCheckResponseBody;
import rlcp.generate.GeneratingResult;
import rlcp.server.logger.Logger;
import rlcp.server.processor.*;
import rlcp.server.processor.CheckProcessor.CheckingSingleConditionResult;
import rlcp.server.processor.PreCheckProcessor.PreCheckResult;
import rlcp.server.processor.factory.ProcessorFactoryContainer;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class for processing RLCP Check method requests.
 */
public class RlcpCheckFlow extends RlcpRequestFlow {


    /**
     * The container must to put an instances {@code PreCheckProcessor}, {@code CheckProcessor}, {@code PostCheckProcessor}.
     * At the beginning perform the actions announced in {@code PreCheckProcessor}.
     * Later perform {@code CheckProcessor}, where in front of each check unit perform the actions announced in {@see PreCheckResultAware}.
     * At the end perform the actions announced in {@code PostCheckProcessor}.
     * <p>
     * As a result, request body for checking will be processed and will be returned response body checking.
     *
     * @param processorFactoryContainer flow modules container
     * @param body                      Rlcp request body instance
     * @return body of RLCP response for Calculate method
     * @see PreCheckProcessor
     * @see CheckProcessor
     * @see PreCheckResultAware
     * @see PostCheckProcessor
     */
    @Override
    public RlcpCheckResponseBody processBody(ProcessorFactoryContainer processorFactoryContainer, RlcpRequestBody body) {
        RlcpCheckRequestBody requestBody = (RlcpCheckRequestBody) body;

        PreCheckResult preCheckResult = performPreCheck(processorFactoryContainer, requestBody);

        List<CheckingResult> checkResults = new ArrayList<>();
        List<CheckProcessor> checkProcessors = new ArrayList<>();
        for (ConditionForChecking checkUnit : requestBody.getConditionsList()) {
            CheckProcessor checkProcessor = processorFactoryContainer.getProcessor(CheckProcessor.class);
            checkProcessors.add(checkProcessor);

            CheckingResult checkingResult;
            if (checkProcessor == null) {
                checkingResult = getEmptyCheckingResult(checkUnit);
            } else {
                checkingResult = performCheck(requestBody, preCheckResult, checkUnit, checkProcessor);
            }
            checkResults.add(checkingResult);
        }

        checkResults = performPostCheck(processorFactoryContainer, requestBody, preCheckResult, checkResults, checkProcessors);

        RlcpCheckResponseBody responseBody = new RlcpCheckResponseBody(checkResults);
        return responseBody;

    }

    private CheckingResult getEmptyCheckingResult(ConditionForChecking checkUnit) {
        CheckingResult checkingResult;
        String msg = "No CheckProcessor found";
        Logger.log(msg);
        checkingResult = new CheckingResult(
                checkUnit.getId(),
                0,
                convertResult(BigDecimal.ZERO),
                msg
        );
        return checkingResult;
    }

    private CheckingResult performCheck(RlcpCheckRequestBody requestBody, PreCheckResult preCheckResult, ConditionForChecking checkUnit, CheckProcessor checkProcessor) {
        CheckingResult checkingResult;
        if (checkProcessor instanceof PreCheckResultAware) {
            ((PreCheckResultAware) checkProcessor).setPreCheckResult(preCheckResult);
        }

        CheckThread checkThread = new CheckThread(checkProcessor,
                checkUnit,
                requestBody.getInstructions(),
                requestBody.getPreGenerated()
        );

        long checkUnitTimeLimit = checkUnit.getTime() > 0 ? checkUnit.getTime() : super.config.getCheckUnitTimeLimit();
        long elapsedTime = tryToRunCheckThreadAndGetElapsedTime(checkThread, checkUnitTimeLimit);
        CheckingSingleConditionResult cscResult = checkThread.getResult();
        String result;
        try {
            result = checkAndConvertResult(cscResult.getResult());
        } catch (Exception e) {
            result = checkAndConvertResult(BigDecimal.ZERO);
            Logger.log(e);
        }
        int id = checkUnit.getId();
        checkingResult = new CheckingResult(id, elapsedTime, result, cscResult.getComment());
        return checkingResult;
    }

    private static List<CheckingResult> performPostCheck(ProcessorFactoryContainer processorFactoryContainer, RlcpCheckRequestBody requestBody, PreCheckResult preCheckResult, List<CheckingResult> checkResults, List<CheckProcessor> checkProcessors) {
        PostCheckProcessor postCheckProcessor = processorFactoryContainer.getPostCheckProcessor();
        if (postCheckProcessor != null) {
            try {
                ArrayList<CheckingResult> checkingResultsCopy = new ArrayList<>(checkResults);
                postCheckProcessor.postCheck(preCheckResult, checkingResultsCopy, checkProcessors);
                checkResults = getCheckingResultsIfTheyAreValidOrGetDefault(requestBody, checkResults, checkingResultsCopy);
            } catch (Exception e) {
                Logger.log(e);
            }
        }
        return checkResults;
    }

    private static PreCheckResult performPreCheck(ProcessorFactoryContainer processorFactoryContainer, RlcpCheckRequestBody requestBody) {
        PreCheckResult preCheckResult = null;
        PreCheckProcessor preCheckAlgorithm = processorFactoryContainer.getPreCheckAlgorithm();
        if (preCheckAlgorithm != null) {
            try {
                preCheckResult = preCheckAlgorithm.preCheck(
                        requestBody.getConditionsList(),
                        requestBody.getInstructions(),
                        requestBody.getPreGenerated()
                );
            } catch (Exception e) {
                Logger.log(e);
            }
        }
        return preCheckResult;
    }

    private static List<CheckingResult> getCheckingResultsIfTheyAreValidOrGetDefault(RlcpCheckRequestBody requestBody, List<CheckingResult> checkResults, ArrayList<CheckingResult> checkingResultsCopy) {
        List<ConditionForChecking> conditions = requestBody.getConditionsList();
        if (conditions.size() == checkingResultsCopy.size()) {
            int[] conditionIds = conditions.stream().sequential().mapToInt(c -> c.getId()).toArray();
            int[] resultsIds = checkingResultsCopy.stream().sequential().mapToInt(r -> r.getId()).toArray();
            if (Arrays.equals(conditionIds, resultsIds)) {
                boolean resultsAreGood = checkingResultsCopy.stream().anyMatch(r ->
                                !isBetween0and1(new BigDecimal(r.getResult()))
                                        && r.getTime() > 0
                                        && r.getOutput() != null
                );
                if (resultsAreGood) {
                    checkResults = checkingResultsCopy;
                }
            }
        }
        return checkResults;
    }

    private static long tryToRunCheckThreadAndGetElapsedTime(CheckThread thread, long timeLimit) {
        long elapsedTime = 0;
        try {
            elapsedTime = runThreadTaskAndGetElapsedTime(thread, timeLimit);
        } catch (InterruptedException exc) {
            Logger.log(exc);
        }
        return elapsedTime;
    }

    private static long runThreadTaskAndGetElapsedTime(CheckThread thread, long timeLimit) throws InterruptedException {
        thread.setPriority(Thread.currentThread().getPriority() - 1);
        thread.start();
        long startTime = System.currentTimeMillis();
        while ((System.currentTimeMillis() - startTime) < timeLimit * 1000 && !thread.isStopped()) {
            Thread.sleep(50);
        }
        long endTime = System.currentTimeMillis();
        thread.interrupt();
        return endTime - startTime;
    }

    private static String checkAndConvertResult(BigDecimal number) {
        if (isBetween0and1(number)) {
            return convertResult(number);
        } else {
            throw new IllegalArgumentException("number should be in [0;1]");
        }
    }

    private static boolean isBetween0and1(BigDecimal number) {
        return number.compareTo(BigDecimal.ZERO) >= 0 && number.compareTo(BigDecimal.ONE) <= 0;
    }

    private static String convertResult(BigDecimal number) {
        return number.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString();
    }

    /**
     * Class for single check unit processing.
     */
    private static class CheckThread extends Thread {

        private CheckProcessor processor;
        private ConditionForChecking conditionForChecking;
        private String instructions;
        private GeneratingResult generatingResult;
        private CheckingSingleConditionResult result;
        private boolean isStopped = false;

        /**
         * simple constructor.
         *
         * @param checkLogic           flow module
         * @param conditionForChecking condition for checking
         * @param instructions         serialized user anwer
         * @param generatingResult     data from previous Generate method call
         */
        public CheckThread(CheckProcessor checkLogic, ConditionForChecking conditionForChecking, String instructions, GeneratingResult generatingResult) {
            this.processor = checkLogic;
            this.conditionForChecking = conditionForChecking;
            this.instructions = instructions;
            this.generatingResult = generatingResult;
        }

        @Override
        public void run() {
            try {
                result = processor.checkSingleCondition(conditionForChecking, instructions, generatingResult);
            } catch (Exception exc) {
                result = new CheckingSingleConditionResult(BigDecimal.ZERO, "Exception while checking");
            }
            isStopped = true;
        }


        public CheckingSingleConditionResult getResult() {
            return result;
        }

        /**
         * Returns {@code true} if check is finished, {@code false} - otherwise.
         *
         * @return {@code true} if check is finished, {@code false} - otherwise.
         */
        public boolean isStopped() {
            return isStopped;
        }
    }
}
