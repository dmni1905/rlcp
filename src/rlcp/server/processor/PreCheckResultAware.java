package rlcp.server.processor;

import rlcp.server.processor.PreCheckProcessor.PreCheckResult;

/**
 * Created by efimchick on 24.11.15.
 */
public interface PreCheckResultAware<T> {
    void setPreCheckResult(PreCheckResult<T> preCheckResult);
}
