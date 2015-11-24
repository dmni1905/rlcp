package rlcp.generate;

import java.io.Serializable;

/**
 * Simple unmodifiable data container. Contains data returned by Generate method
 * and substituted as param to othermethods.
 *
 * @author Eugene Efimchick
 */
public class GeneratingResult implements Serializable {

    private String text;
    private String code;
    private String instructions;

    /**
     * Simple constructor.
     *
     * @param text data to show to user
     * @param code data for applet param
     * @param instructions additional data for RLCP-server processing
     */
    public GeneratingResult(String text, String code, String instructions) {
        this.text = text;
        this.code = code;
        this.instructions = instructions;
    }

    /**
     * Returns data to for applet param.
     *
     * @return data to for applet param
     */
    public String getCode() {
        return code;
    }

    /**
     * Returns additional data for server processing.
     *
     * @return additional data for server processing
     */
    public String getInstructions() {
        return instructions;
    }

    /**
     * Returns data to show to user.
     *
     * @return data to show to user
     */
    public String getText() {
        return text;
    }
}
