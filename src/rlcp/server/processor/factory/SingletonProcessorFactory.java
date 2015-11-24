package rlcp.server.processor.factory;

import rlcp.server.processor.Processor;

import java.util.Objects;

/**
 * Created by efimchick on 24.11.15.
 */
public class SingletonProcessorFactory<T extends Processor> implements ProcessorFactory<T> {

    private T instance;

    public SingletonProcessorFactory(T instance) {
        Objects.requireNonNull(instance);
        this.instance = instance;
    }

    @Override
    public T getInstance() {
        return  instance;
    }
}