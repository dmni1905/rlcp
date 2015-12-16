package rlcp.generate;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import rlcp.RlcpResponseBody;
import rlcp.method.Generate;
import rlcp.method.RlcpMethod;
import rlcp.util.Constants;

import static rlcp.util.Constants.*;

/**
 * RlcpResponseBody implementation for Generate method
 */
public class RlcpGenerateResponseBody extends RlcpResponseBody {

    private GeneratingResult generatingResult;

    public RlcpGenerateResponseBody(GeneratingResult generatingResult) {
        this.generatingResult = generatingResult;
    }

    /**
     * Simple constructor.
     *
     * @param text data to show to user
     * @param code data for virtual stand
     * @param instructions additional data for RLCP-server processing
     */
    public RlcpGenerateResponseBody(String text, String code, String instructions) {
        this(new GeneratingResult(text, code, instructions));
    }


    public GeneratingResult getGeneratingResult() {
        return generatingResult;
    }

    /**
     * Returns XML document representation of RlcpGenerateResponseBody.
     *
     * @return XML document representation of RlcpGenerateResponseBody
     */
    @Override
    public Document getDocument() {
        Document document = DocumentHelper.createDocument();
        Element responseElement = document.addElement(RESPONSE);
        Element resultElement = responseElement.addElement(GENERATING_RESULT);
        (resultElement.addElement(PRE_GENERATED_TEXT)).addComment(generatingResult.getText());
        (resultElement.addElement(PRE_GENERATED_CODE)).addComment(generatingResult.getCode());
        (resultElement.addElement(PRE_GENERATED_INSTRUCTIONS)).addComment(generatingResult.getInstructions());
        return document;
    }

    /**
     * Returns Generate instance.
     * @return Generate instance
     */
    @Override
    public RlcpMethod getMethod() {
        return Generate.getInstance();
    }
    
    
}
