package rlcp.check;

import java.io.Serializable;

/**
 * Class for checking result entity. Unmodifiable. 
 * @author Eugene Efimchick
 */
public class CheckingResult implements Serializable {

    private int id;
    private long time;
    private String result;
    private String output;

    /**
     * Simple constuctor.
     * @param id identifier
     * @param time elapsed time in milliseconds
     * @param result String representation of result. For example "1.0","0.0","0.55".
     * @param output text comment
     */
    public CheckingResult(int id, long time, String result, String output) {
        this.id = id;
        this.time = time;
        this.result = result;
        this.output = output;
    }

    /**
     * Returns identifier.
     * @return identifier
     */
    public int getId() {
        return id;
    }

    /**
     * Returns elapsed time in milliseconds.
     * @return elapsed time in milliseconds
     */
    public long getTime() {
        return time;
    }

    /**
     * Returns String representation of result. For example "1.0","0.0","0.55".
     * @return String representation of result
     */
    public String getResult() {
        return result;
    }

    /**
     * Returns text comment.
     * @return text comment
     */
    public String getOutput() {
        return output;
    }
}
