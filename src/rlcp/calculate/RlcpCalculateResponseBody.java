package rlcp.calculate;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import rlcp.RlcpResponseBody;
import rlcp.method.Calculate;
import rlcp.method.RlcpMethod;
import rlcp.util.Constants;

import static rlcp.util.Constants.*;

/**
 * RlcpResponseBody implementation for Calculate method
 */
public class RlcpCalculateResponseBody extends RlcpResponseBody {

    private CalculatingResult calculatingResult;

    public RlcpCalculateResponseBody(CalculatingResult calculatingResult) {
        this.calculatingResult = calculatingResult;
    }

    /**
     * Simple constructor.
     *
     * @param text data to show to user
     * @param code data for virtual stand
     */
    public RlcpCalculateResponseBody(String text, String code) {
        this(new CalculatingResult(text, code));
    }

    public CalculatingResult getCalculatingResult() {
        return calculatingResult;
    }

    /**
     * Returns XML document representation of RlcpCalculateResponseBody.
     *
     * @return XML document representation of RlcpCalculateResponseBody
     */
    @Override
    public Document getDocument() {
        Document document = DocumentHelper.createDocument();
        Element responseElement = document.addElement(RESPONSE);
        Element resultElement = responseElement.addElement(CALCULATING_RESULT);
        (resultElement.addElement(PRE_GENERATED_TEXT)).addComment(calculatingResult.getText());
        (resultElement.addElement(PRE_GENERATED_CODE)).addComment(calculatingResult.getCode());
        return document;
    }

    /**
     * Returns Calculate instance.
     *
     * @return Calculate instance
     */
    @Override
    public RlcpMethod getMethod() {
        return Calculate.getInstance();
    }
}
