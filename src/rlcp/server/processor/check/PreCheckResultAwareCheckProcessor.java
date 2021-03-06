package rlcp.server.processor.check;

/**
 * Interface for Check method processing flow module with  manipulate programmatically the {@code PreCheckResult}
 */
public interface PreCheckResultAwareCheckProcessor<T> extends CheckProcessor, PreCheckResultAware<T>{
}
