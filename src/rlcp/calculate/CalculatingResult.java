package rlcp.calculate;

/**
 * Simple unmodifiable data container. Contains data returned by Calculate method
 */
public class CalculatingResult {
    private String text;
    private String code;

    public CalculatingResult(String text, String code) {
        this.text = text;
        this.code = code;
    }

    /**
     * Returns data through Calculate method to show to user.
     *
     * @return data through Calculate to show to user
     */
    public String getText() {
        return text;
    }

    /**
     * Returns data for virtual stand through Calculate.
     *
     * @return data for virtual stand through Calculate
     */
    public String getCode() {
        return code;
    }
}
