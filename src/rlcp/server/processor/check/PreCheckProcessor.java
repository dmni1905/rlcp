package rlcp.server.processor.check;

import rlcp.generate.GeneratingResult;
import rlcp.check.ConditionForChecking;
import rlcp.server.processor.Processor;

import java.util.List;

/**
 * Sometimes checking of several units need to share setup.
 * <p>
 * For use it is necessary to add in {@code ProcessorFactoryContainer}, using implementing the {@code ProcessorFactory} interface.
 *
 * @see rlcp.server.processor.factory.ProcessorFactoryContainer
 * @see rlcp.server.processor.factory.ProcessorFactory
 */
public interface PreCheckProcessor<T> extends Processor {
    /**
     * General method. It will be run before checking.
     *
     * @param conditions       list condition for checking
     * @param instructions     serialized user answer
     * @param generatingResult previously generated with Generate method call data
     * @return result of checking for list condition
     */
    PreCheckResult<T> preCheck(List<ConditionForChecking> conditions, String instructions, GeneratingResult generatingResult);

    /**
     * Class for checking result entity
     *
     * @param <T> result of checking
     */
    public static class PreCheckResult<T> {

        T value;

        /**
         * Simple constructor
         *
         * @param value result of checking
         */
        public PreCheckResult(T value) {
            this.value = value;
        }

        /**
         * Returns result of checking
         *
         * @return result of checking
         */
        public T getValue() {
            return value;
        }

        ;
    }
}
