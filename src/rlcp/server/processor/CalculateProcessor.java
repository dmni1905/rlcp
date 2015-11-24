package rlcp.server.processor;

import rlcp.calculate.CalculatingResult;
import rlcp.generate.GeneratingResult;

/**
 * Interface for Calculate method processing flow module.
 * 
 * @author Eugene Efimchick
 */
public interface CalculateProcessor extends Processor {
    
    /**
     * General method. Defines flow of Calculate method processing. Results are available through getters after this method call.
     * 
     * @param condition Condition for calculating
     * @param instructions serialized user answer
     * @param generatingResult previously generated with Generate method call data
     */
    CalculatingResult calculate(String condition, String instructions, GeneratingResult generatingResult);

}
