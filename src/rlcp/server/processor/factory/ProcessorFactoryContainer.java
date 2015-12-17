package rlcp.server.processor.factory;

import java.util.HashMap;
import java.util.Map;

import rlcp.calculate.CalculatingResult;
import rlcp.exception.NotImplementedMethodException;
import rlcp.generate.GeneratingResult;
import rlcp.server.processor.*;
import rlcp.server.processor.calculate.CalculateProcessor;
import rlcp.server.processor.check.CheckProcessor;
import rlcp.server.processor.check.PostCheckProcessor;
import rlcp.server.processor.check.PreCheckProcessor;
import rlcp.server.processor.generate.GenerateProcessor;

/**
 * Class for flow modules container.
 */
public class ProcessorFactoryContainer {

    private Map<Class<? extends Processor>, ProcessorFactory<? extends Processor>> storage = new HashMap<>();

    /**
     * Simple constructor with null initial values.
     */
    public ProcessorFactoryContainer() {
    }

    /**
     * Return form container PreCheckProcessor instance, if it was previously set.
     *
     * @return PreCheckProcessor instance, if it was previously set.
     */
    public PreCheckProcessor getPreCheckAlgorithm() {
        return getProcessor(PreCheckProcessor.class);
    }

    /**
     * Simple setter.
     *
     * @param factory PreCheckProcessor instance.
     */
    public void setPreCheckProcessorFactory(ProcessorFactory<PreCheckProcessor> factory) {
        putProcessorFactory(PreCheckProcessor.class, factory);
    }

    /**
     * Return form container PostCheckProcessor instance , if it was previously set.
     *
     * @return PostCheckProcessor instance , if it was previously set.
     */
    public PostCheckProcessor getPostCheckProcessor() {
        return getProcessor(PostCheckProcessor.class);
    }

    /**
     * Simple setter.
     *
     * @param factory PostCheckProcessor instance.
     */
    public void setPostCheckProcessorFactory(ProcessorFactory<PostCheckProcessor> factory) {
        putProcessorFactory(PostCheckProcessor.class, factory);
    }


    /**
     * Returns CheckFactory instance, if it was previously set.
     *
     * @return CheckFactory instance, if it was previously set.
     * @throws NotImplementedMethodException if there was no CheckProcessor instance
     *                                       set.
     */
    public CheckProcessor getCheckProcessor() {
        return getProcessor(CheckProcessor.class);
    }

    /**
     * Simple setter.
     *
     * @param factory CheckProcessor instance.
     */
    public void setCheckProcessorFactory(ProcessorFactory<CheckProcessor> factory) {
        putProcessorFactory(CheckProcessor.class, factory);
    }

    /**
     * Simple setter.
     *
     * @param factory GenerateProcessor instance.
     */
    public void setGenerateProcessorFactory(ProcessorFactory<GenerateProcessor> factory) {
        putProcessorFactory(GenerateProcessor.class, factory);
    }

    /**
     * Returns GenerateProcessor instance, if it was previously set.
     *
     * @return GenerateProcessor instance, if it was previously set.
     * @throws NotImplementedMethodException if there was no GenerateProcessor
     *                                       instance set.
     */
    public GenerateProcessor getGenerateProcessor() {
        return getProcessor(GenerateProcessor.class);
    }

    /**
     * Simple setter.
     *
     * @param factory CalculateProcessor instance.
     */
    public void setCalculateProcessorFactory(ProcessorFactory<CalculateProcessor> factory) {
        putProcessorFactory(CalculateProcessor.class, factory);
    }

    /**
     * Returns CalculateProcessor instance, if it was previously set.
     *
     * @return CalculateProcessor instance, if it was previously set.
     * @throws NotImplementedMethodException if there was no CalculateProcessor
     *                                       instance set.
     */
    public CalculateProcessor getCalculateProcessor() {
        return getProcessor(CalculateProcessor.class);
    }

    /**
     * Returns RequestProcessProcessor instance, if it was previously set.
     *
     * @param key key for RequestProcessProcessor instance
     * @return RequestProcessProcessor instance, if it was previously set.
     * @throws NotImplementedMethodException if there was no RequestProcessProcessor
     *                                       instance set for that key.
     */
    public <T extends Processor> ProcessorFactory<T> getProcessorFactory(Class<T> key) {
        ProcessorFactory<T> factory = (ProcessorFactory<T>) storage.get(key);
        return factory;
    }

    /**
     * Return one instance of extends {@code Processor} or null
     *
     * @param key name class
     * @param <T>
     * @return {@code Processor} implementation instance for processing flow module or null
     * @see Processor
     */
    public <T extends Processor> T getProcessor(Class<T> key) {
        ProcessorFactory<T> factory = (ProcessorFactory<T>) storage.get(key);
        return factory == null ? null : factory.getInstance();
    }

    /**
     * Sets RequestProcessProcessor instance for specified key.
     *
     * @param clazz   specified key (name interface for RLCP methods processing flow modules).
     * @param factory RequestProcessProcessor instance
     */
    private <T extends Processor> void putProcessorFactory(Class<T> clazz, ProcessorFactory<T> factory) {
        storage.put(clazz, factory);
    }


    public static void main(String[] args) {
        new ProcessorFactoryContainer().putProcessorFactory(CalculateProcessor.class, new DefaultConstructorProcessorFactory<CalculateProcessor>(CaclculateImpl.class));
    }

    private static class CaclculateImpl implements CalculateProcessor {
        @Override
        public CalculatingResult calculate(String condition, String instructions, GeneratingResult generatingResult) {
            return null;
        }
    }


}
