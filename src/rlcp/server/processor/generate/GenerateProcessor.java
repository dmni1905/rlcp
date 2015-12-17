package rlcp.server.processor.generate;

import rlcp.generate.GeneratingResult;
import rlcp.server.processor.Processor;

/**
 * Interface for Generate method processing module.
 * <p>
 * For use it is necessary to add in {@code ProcessorFactoryContainer}, using Implementing the {@code ProcessorFactory} interface.
 *
 * @see rlcp.server.processor.factory.ProcessorFactoryContainer
 * @see rlcp.server.processor.factory.ProcessorFactory
 */
public interface GenerateProcessor extends Processor {

    /**
     * General method. Defines flow of Generate method processing. Results are
     * available through getters after this method call.
     *
     * @param condition condition for generating
     * @return data for users
     */
    GeneratingResult generate(String condition);

}
