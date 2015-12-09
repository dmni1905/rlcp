package rlcp.server.processor.factory;

import rlcp.server.processor.Processor;

/**
 * Instance for processing flow module
 */
public interface ProcessorFactory<T extends Processor> {
    /**
     * Return process instance
     *
     * @return process instance
     */
    T getInstance();
}
