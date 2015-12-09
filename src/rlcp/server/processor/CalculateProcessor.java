package rlcp.server.processor;

import rlcp.calculate.CalculatingResult;
import rlcp.generate.GeneratingResult;

/**
 * Interface for Calculate method processing module.
 * <p>
 * For use it is necessary to put in {@code ProcessorFactoryContainer}, using Implementing the {@code ProcessorFactory} interface.
 *
 * @see rlcp.server.processor.factory.ProcessorFactoryContainer
 * @see rlcp.server.processor.factory.ProcessorFactory
 */
public interface CalculateProcessor extends Processor {

    /**
     * General method. Defines flow of Calculate method processing. Results are available through getters after this method call.
     *
     * @param condition        Condition for calculating
     * @param instructions     serialized user answer
     * @param generatingResult previously generated with Generate method call data
     * @return data for users
     */
    CalculatingResult calculate(String condition, String instructions, GeneratingResult generatingResult);

}
