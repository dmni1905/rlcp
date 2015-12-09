package rlcp.generate;

import java.io.Serializable;

/**
 * Simple unmodifiable data container. Contains data returned by Generate method
 * and substituted as param to other methods.
 */
public class GeneratingResult implements Serializable {

    private String text;
    private String code;
    private String instructions;

    /**
     * Simple constructor.
     *
     * @param text data to show to user
     * @param code secret data, data for virtual stand
     * @param instructions additional data for RLCP-server processing
     */
    public GeneratingResult(String text, String code, String instructions) {
        this.text = text;
        this.code = code;
        this.instructions = instructions;
    }

    /**
     * Returns data for virtual stand through Generate method.
     *
     * @return data for virtual stand through Generate method
     */
    public String getCode() {
        return code;
    }

    /**
     * Returns additional data for server processing through Generate method.
     *
     * @return additional data for server processing through Generate method
     */
    public String getInstructions() {
        return instructions;
    }

    /**
     * Returns data through Generate method to show to user.
     *
     * @return data through Generate method to show to user
     */
    public String getText() {
        return text;
    }
}
