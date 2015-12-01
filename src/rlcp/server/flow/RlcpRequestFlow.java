package rlcp.server.flow;

import rlcp.RlcpRequest;
import rlcp.RlcpRequestBody;
import rlcp.RlcpResponse;
import rlcp.RlcpResponseBody;
import rlcp.check.RlcpCheckResponseBody;
import rlcp.exception.RlcpException;
import rlcp.server.config.Config;
import rlcp.server.processor.factory.ProcessorFactoryContainer;

import java.util.concurrent.*;

/**
 *
 * @author efimchick
 */
public abstract class RlcpRequestFlow {

    protected Config config;
    /**
     * Processes RLCP request using specified RequestProcessLogic from LogicContainer.
     * 
     * @param rlcpRequest Rlcp Request
     * @param processorFactoryContainer container of RequestProcessLogic instances
     * @return Rlcp Response
     */
    public RlcpResponse processRequest(RlcpRequest rlcpRequest, ProcessorFactoryContainer processorFactoryContainer, Config config) {
        this.config = config;

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<RlcpResponseBody> future = executor.submit(new ProcessRequestBodyTask(rlcpRequest, processorFactoryContainer));

        try {
            RlcpResponseBody responseBody = future.get(config.getRequestFlowTimeLimit(), TimeUnit.SECONDS);
            return responseBody.getMethod().buildResponse(responseBody);
        } catch (TimeoutException|ExecutionException|InterruptedException e) {
            future.cancel(true);
            e.printStackTrace();
            throw new RlcpException("Failed to process request");
        } finally {
            executor.shutdownNow();
        }
    }
//
//    /**
//     * Returns necessary RequestProcessLogic instance from LogicContainer
//     * @param logicContainer container of RequestProcessLogic instances
//     * @return necessary RequestProcessLogic instance from LogicContainer
//     */
//    public abstract RequestProcessAlgorithm getAlgorithmFactory(LogicContainer logicContainer);
    
    /**
     * Processes RlcpRequestBody with specified RequestProcessLogic. Returns body of RlcpResponse.
     * @param processorFactoryContainer
     * @param body Rlcp request body instance
     * @return body of RlcpResponse
     */
    public abstract RlcpResponseBody processBody(ProcessorFactoryContainer processorFactoryContainer, RlcpRequestBody body);

    private class ProcessRequestBodyTask implements Callable<RlcpResponseBody> {
        private final RlcpRequest rlcpRequest;
        private final ProcessorFactoryContainer processorFactoryContainer;

        public ProcessRequestBodyTask(RlcpRequest rlcpRequest, ProcessorFactoryContainer processorFactoryContainer) {
            this.rlcpRequest = rlcpRequest;
            this.processorFactoryContainer = processorFactoryContainer;
        }

        @Override
        public RlcpResponseBody call() throws Exception {
            RlcpRequestBody requestBody = rlcpRequest.getBody();
            RlcpResponseBody responseBody = processBody(processorFactoryContainer, requestBody);
            return responseBody;
        }
    }
}
