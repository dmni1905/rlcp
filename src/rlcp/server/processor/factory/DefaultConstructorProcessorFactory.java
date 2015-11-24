package rlcp.server.processor.factory;

import rlcp.server.processor.Processor;

import java.lang.reflect.Modifier;
import java.util.Arrays;

/**
 * Created by efimchick on 24.11.15.
 */
public class DefaultConstructorProcessorFactory<T extends Processor> implements ProcessorFactory<T> {

    private Class<T> clazz;

    public DefaultConstructorProcessorFactory(Class<T> clazz) {

        boolean hasDefaultConstructor = Arrays.stream(clazz.getConstructors()).anyMatch(
                c ->
                        c.getParameterTypes().length == 0
                                && Modifier.isPublic(c.getModifiers())
        );
        if (!hasDefaultConstructor){
            throw  new IllegalArgumentException("Class " + clazz + "has no constructor without parameters;");
        }
        this.clazz = clazz;
    }

    @Override
    public T getInstance() {
        try {
            return clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new IllegalStateException("Error at creating " + clazz + " instance");
        }
    }
}
