package rlcp.check;

import java.io.Serializable;

/**
 * Class for condition for checking entity. Unmodifiable.
 */
public class ConditionForChecking implements Serializable {

    private int id;
    private long time;
    private String input;
    private String output;

    /**
     * Simple constructor.
     *
     * @param id     identifier
     * @param time   time limit in milliseconds
     * @param input  input data
     * @param output expected output data
     */
    public ConditionForChecking(int id, long time, String input, String output) {
        this.id = id;
        this.time = time;
        this.input = input;
        this.output = output;
    }

    /**
     * Returns identifier.
     *
     * @return identifier
     */
    public int getId() {
        return id;
    }

    /**
     * Returns input data.
     *
     * @return input data
     */
    public String getInput() {
        return input;
    }

    /**
     * Returns expected output data.
     *
     * @return expected output data
     */
    public String getOutput() {
        return output;
    }

    /**
     * Returns time limit in milliseconds.
     *
     * @return time limit in milliseconds
     */
    public long getTime() {
        return time;
    }
}
