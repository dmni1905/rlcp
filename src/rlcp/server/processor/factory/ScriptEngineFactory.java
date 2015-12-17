package rlcp.server.processor.factory;

import rlcp.server.processor.Processor;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 *  JS constructor ProcessorFactory. It is used for putting of flow modules container
 *
 * @param <T> interface for RLCP methods processing flow modules
 */
public class ScriptEngineFactory<T extends Processor> implements ProcessorFactory<T>{
    private String engineName;
    private File file;
    private Class<T> clazz;

    /**
     * Simple constructor.
     *
     * @param engineName name js-engine. For example, "nashorn"
     * @param file file with js-code
     * @param clazz class name, implementing the interface {@code Processor}
     */
    public ScriptEngineFactory(String engineName, File file, Class<T> clazz) {
        this.engineName = engineName;
        this.file = file;
        this.clazz = clazz;
    }

    private Invocable buildEngine(){
        ScriptEngine engine = new ScriptEngineManager().getEngineByName(engineName);
        try {
            engine.eval(Files.newBufferedReader(file.toPath()));

        } catch (ScriptException | IOException e) {
            e.printStackTrace();
        }
        return (Invocable) engine;
    }

    /**
     * Every time will return new engined of the RLCP methods processing flow modules
     *
     * @return instance of the RLCP methods processing flow modules
     */
    @Override
    public T getInstance() {
        return buildEngine().getInterface(clazz);
    }
}
