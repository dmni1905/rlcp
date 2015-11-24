package rlcp.server.processor.factory;

import java.util.HashMap;
import java.util.Map;
import rlcp.exception.NotImplementedMethodException;
import rlcp.server.processor.*;

/**
 * Class for flow modules container.
 *
 * @author Eugene Efimchick
 */
public class ProcessorFactoryContainer {

    private Map<Class<? extends Processor>, ProcessorFactory<? extends Processor>> storage = new HashMap<>();

    /**
     * Simple constructor with null initial values.
     */
    public ProcessorFactoryContainer() {
    }

    public PreCheckProcessor getPreCheckAlgorithm() {
        return getProcessor(PreCheckProcessor.class);
    }

    public void setPreCheckLogicFactory(ProcessorFactory<PreCheckProcessor> factory) {
        putProcessorFactory(PreCheckProcessor.class, factory);
    }

    public PostCheckProcessor getPostCheckProcessor() {
        return getProcessor(PostCheckProcessor.class);
    }

    public void setPostCheckProcessorFactory(ProcessorFactory<PostCheckProcessor> factory) {
        putProcessorFactory(PostCheckProcessor.class, factory);
    }


    /**
     * Returns CheckFactory instance, if it was previously set.
     *
     * @return CheckFactory instance, if it was previously set.
     * @throws NotImplementedMethodException if there was no CheckProcessor instance
     * set.
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
     * instance set.
     */
    public GenerateProcessor getGenerateProcessor() {
        return  getProcessor(GenerateProcessor.class);
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
     * instance set.
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
     * instance set for that key.
     */
    public <T extends Processor> ProcessorFactory<T> getProcessorFactory(Class<T> key) {
        ProcessorFactory<T> factory = (ProcessorFactory<T>) storage.get(key);
        return factory;
    }

    public <T extends Processor> T getProcessor(Class<T> key) {
        ProcessorFactory<T> factory = (ProcessorFactory<T>) storage.get(key);
        return factory == null ? null : factory.getInstance();
    }

    /**
     * Sets RequestProcessProcessor instance for specified key.
     * 
     * @param clazz
     * @param factory
     */
    private <T extends Processor> void putProcessorFactory(Class<T> clazz, ProcessorFactory<? super T> factory) {
        storage.put(clazz, factory);
    }



}
