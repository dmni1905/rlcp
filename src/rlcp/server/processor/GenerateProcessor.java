package rlcp.server.processor;

import rlcp.generate.GeneratingResult;

/**
 * Interface for Generate method processing flow module.
 *
 * @author Eugene Efimchick
 */
public interface GenerateProcessor extends Processor {

    /**
     * General method. Defines flow of Generate method processing. Results are
     * available through getters after this method call.
     *
     * @param condition condition for generating
     */
    GeneratingResult generate(String condition);

}
