package rlcp.server.processor.factory;

import rlcp.server.processor.Processor;

import java.lang.reflect.Modifier;
import java.util.Arrays;

/**
 * Default constructor ProcessorFactory. It is used for putting of flow modules container.
 *
 * @param <T> interface for RLCP methods processing flow modules
 */
public class DefaultConstructorProcessorFactory<T extends Processor> implements ProcessorFactory<T> {

    private Class<? extends T> clazz;

    /**
     * Simple constructor.
     *
     * @param clazz class name, implementing the interface {@code Processor}
     * @see Processor
     */
    public DefaultConstructorProcessorFactory(Class<? extends T> clazz) {

        boolean hasDefaultConstructor = Arrays.stream(clazz.getConstructors()).anyMatch(
                c ->
                        c.getParameterTypes().length == 0
                                && Modifier.isPublic(c.getModifiers())
        );
        if (!hasDefaultConstructor) {
            throw new IllegalArgumentException("Class " + clazz + "has no constructor without parameters;");
        }
        this.clazz = clazz;
    }

    /**
     * Every time will return new instance of the RLCP methods processing flow modules
     *
     * @return new instance of the RLCP methods processing flow modules
     */
    @Override
    public T getInstance() {
        try {
            return clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new IllegalStateException("Error at creating " + clazz + " instance");
        }
    }
}
