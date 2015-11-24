package rlcp.server.processor;

import rlcp.check.CheckingResult;
import rlcp.server.processor.PreCheckProcessor.PreCheckResult;

import java.util.List;

/**
 * Created by efimchick on 24.11.15.
 */
public interface PostCheckProcessor<T> extends Processor {
    void postCheck(PreCheckResult<T> preCheckResult, List<CheckingResult> checkingResults, List<CheckProcessor> checkers);
}
