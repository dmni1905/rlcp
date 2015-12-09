package rlcp.server.processor;

import rlcp.generate.GeneratingResult;
import rlcp.check.ConditionForChecking;

import java.math.BigDecimal;

/**
 * Interface for Check method processing flow module.
 * <p>
 * For use it is necessary to add in {@code ProcessorFactoryContainer}, using Implementing the {@code ProcessorFactory} interface.
 *
 * @author Eugene Efimchick
 * @see rlcp.server.processor.factory.ProcessorFactoryContainer
 * @see rlcp.server.processor.factory.ProcessorFactory
 */
public interface CheckProcessor extends Processor {

    /**
     * General method. Defines algorithm of single check unit processing, called for each check unit in request, usually in parallel threads.
     *
     * @param condition    condition for checking
     * @param instructions serialized user answer
     * @param preGenerated previously generated with Generate method call data
     * @return check result for condition (score checking and its comment)
     * @throws Exception
     */
    CheckingSingleConditionResult checkSingleCondition(ConditionForChecking condition, String instructions, GeneratingResult preGenerated) throws Exception;


    /**
     * Class for Check result
     */
    class CheckingSingleConditionResult {
        private BigDecimal result;
        private String comment;

        /**
         * Simple constructor.
         *
         * @param result  score checking
         * @param comment comment for checking. It contains a message if the user made a mistake
         */
        public CheckingSingleConditionResult(BigDecimal result, String comment) {
            this.result = result;
            this.comment = comment;
        }

        /**
         * Returns score checking
         *
         * @return score checking
         */
        public BigDecimal getResult() {
            return result;
        }

        /**
         * Returns comment for check. It contains a message if the user made a mistake
         *
         * @return comment for check
         */
        public String getComment() {
            return comment;
        }
    }
}
