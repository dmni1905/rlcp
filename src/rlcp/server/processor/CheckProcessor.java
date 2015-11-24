package rlcp.server.processor;

import rlcp.check.CheckingResult;
import rlcp.generate.GeneratingResult;
import rlcp.check.ConditionForChecking;

import java.math.BigDecimal;

/**
 * Interface for Check method processing flow module.
 * 
 * @author Eugene Efimchick
 */
public interface CheckProcessor extends Processor {

    /**
     * General method. Defines flow of single check unit processing, called for each check unit in request, usually in parallel threads.
     * @param condition check unit
     * @param instructions serialized user answer
     * @param preGenerated previously generated with Generate method call data
     * @return check result. Should be in [0;1]
     * @throws Exception  
     */
    CheckingSingleConditionResult checkSingleCondition(ConditionForChecking condition, String instructions, GeneratingResult preGenerated) throws Exception;

    class CheckingSingleConditionResult{
        private BigDecimal result;
        private String comment;

        public CheckingSingleConditionResult(BigDecimal result, String comment) {
            this.result = result;
            this.comment = comment;
        }

        public BigDecimal getResult() {
            return result;
        }

        public String getComment() {
            return comment;
        }
    }
}
