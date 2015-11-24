package rlcp.server.processor;

import rlcp.generate.GeneratingResult;
import rlcp.check.ConditionForChecking;

import java.util.List;

/**
 * Created by efimchick on 24.11.15.
 */
public interface PreCheckProcessor<T> extends Processor {
    PreCheckResult<T> preCheck(List<ConditionForChecking> conditions, String instructions, GeneratingResult generatingResult);

    public static class PreCheckResult<T> {

        T value;

        public PreCheckResult(T value) {
            this.value = value;
        }

        public T getValue(){
            return value;
        };
    }
}
