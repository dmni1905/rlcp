package rlcp.calculate;

/**
 * Created by efimchick on 24.11.15.
 */
public class CalculatingResult {
    private String text;
    private String code;

    public CalculatingResult(String text, String code) {
        this.text = text;
        this.code = code;
    }

    public String getText() {
        return text;
    }

    public String getCode() {
        return code;
    }
}
