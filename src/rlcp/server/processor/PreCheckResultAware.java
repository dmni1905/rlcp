package rlcp.server.processor;

import rlcp.server.processor.PreCheckProcessor.PreCheckResult;

/**
 * When you creates an objects instance that implements this interface,
 * the instance is provided with a reference to that {@code PreCheckResult} from {@code PreCheckProcessor}.
 * <p>
 * Thus {@code CheckProcessor} can manipulate programmatically the {@code PreCheckResult}.
 * Called before checking each units.
 *
 * @see PreCheckResult
 * @see CheckProcessor
 */
public interface PreCheckResultAware<T> {
    /**
     * General method. It will be executed at the beginning checking for each check unit in request.
     *
     * @param preCheckResult checking's result
     */
    void setPreCheckResult(PreCheckResult<T> preCheckResult);
}
