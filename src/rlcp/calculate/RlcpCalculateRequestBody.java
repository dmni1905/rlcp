package rlcp.calculate;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import rlcp.generate.GeneratingResult;
import rlcp.RlcpRequestBody;
import rlcp.method.Calculate;
import rlcp.method.RlcpMethod;
import rlcp.util.DomHelper;

import static rlcp.util.Constants.*;

/**
 * RlcpRequestBody implementation for Calculate method.
 * @author Eugene Efimchick
 */
public class RlcpCalculateRequestBody extends RlcpRequestBody {

    private String condition;
    private String instructions;
    private GeneratingResult preGenerated;

    /**
     * Simple constructor.
     * @param condition condition for calculating
     * @param instructions instructions from user
     * @param preGenerated pregenerated data
     */
    public RlcpCalculateRequestBody(String condition, String instructions, GeneratingResult preGenerated) {
        this.condition = condition;
        this.instructions = instructions;
        this.preGenerated = preGenerated;
    }

    /**
     * Returns condition for calculating.
     * @return condition for calculating
     */
    public String getCondition() {
        return condition;
    }

    /**
     * Returns instructions from user.
     * @return instructions from user
     */
    public String getInstructions() {
        return instructions;
    }

    /**
     * Returns preGenerated data.
     * @return preGenerated data
     */
    public GeneratingResult getPreGenerated() {
        return preGenerated;
    }

    @Override
    public RlcpCalculateRequest prepareRequest(String url) {
        return (RlcpCalculateRequest) super.prepareRequest(url);
    }
    
    @Override
    public Document getDocument() {
        Document doc = DocumentHelper.createDocument();
        Element requestElement = doc.addElement(REQUEST);
        requestElement.addElement(CONDITIONS).addElement(CONDITION_FOR_CALCULATING).addElement(INPUT).addComment(condition);
        requestElement.addElement(INSTRUCTIONS).addComment(instructions);
        if (preGenerated != null) DomHelper.addPreGeneratedElement(requestElement, preGenerated);
        return doc;
    }

    @Override
    public RlcpMethod getMethod() {
        return Calculate.getInstance();
    }
}
