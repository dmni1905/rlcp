package rlcp.server.processor.factory;

import rlcp.server.processor.Processor;

/**
 * Created by efimchick on 24.11.15.
 */
public interface ProcessorFactory<T extends Processor> {
    T getInstance();
}
