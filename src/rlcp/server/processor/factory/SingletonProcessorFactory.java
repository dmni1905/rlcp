package rlcp.server.processor.factory;

import rlcp.server.processor.Processor;

import java.util.Objects;

/**
 * Singleton constructor ProcessorFactory. It is used for putting of flow modules container.
 *
 * @param <T> interface for RLCP methods processing flow modules
 */
public class SingletonProcessorFactory<T extends Processor> implements ProcessorFactory<T> {

    private T instance;

    public SingletonProcessorFactory(T instance) {
        Objects.requireNonNull(instance);
        this.instance = instance;
    }

    /**
     * Every time will return singleton instance of the RLCP methods processing flow modules
     *
     * @return singleton instance of the RLCP methods processing flow modules
     */
    @Override
    public T getInstance() {
        return  instance;
    }
}