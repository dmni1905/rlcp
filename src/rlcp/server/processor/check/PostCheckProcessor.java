package rlcp.server.processor.check;

import rlcp.check.CheckingResult;
import rlcp.server.processor.Processor;

import java.util.List;

/**
 * If you allocate expensive external resources in {@code PreCheckProcessor} you need to release them after all units have been check.
 * <p>
 * Is required to add in {@code ProcessorFactoryContainer}, using Implementing the {@code ProcessorFactory} interface.
 *
 * @see rlcp.server.processor.factory.ProcessorFactoryContainer
 * @see rlcp.server.processor.factory.ProcessorFactory
 */
public interface PostCheckProcessor extends Processor {
    /**
     * General method. It will be run after checking.
     *
     * @param preCheckResult  list of check unit
     * @param checkingResults list of results for each check unit
     * @param checkers        result of checking entity
     */
    void postCheck(PreCheckProcessor.PreCheckResult preCheckResult, List<CheckingResult> checkingResults, List<CheckProcessor> checkers);
}
