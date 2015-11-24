package rlcp.check;

import java.util.Collections;
import java.util.List;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import rlcp.generate.GeneratingResult;
import rlcp.RlcpRequestBody;
import rlcp.method.Check;
import rlcp.method.RlcpMethod;

import static rlcp.util.Constants.*;

/**
 * RlcpRequestBody implementation for Check method.
 *
 * @author Eugene Efimchick
 */
public class RlcpCheckRequestBody extends RlcpRequestBody {

    private List<ConditionForChecking> conditionsList;
    private String instructions;
    private GeneratingResult preGenerated;

    /**
     * Simple constructor.
     *
     * @param conditionsList list of conditions for checking
     * @param instructions instructions from user
     * @param preGenerated preGenerated data
     */
    public RlcpCheckRequestBody(List<ConditionForChecking> conditionsList, String instructions, GeneratingResult preGenerated) {
        this.conditionsList = Collections.unmodifiableList(conditionsList);
        this.instructions = instructions;
        this.preGenerated = preGenerated;
    }

    /**
     * Simple constructor with null as PreGenerated. Usual for static frames.
     *
     * @param conditionsList list of conditions for checking
     * @param instructions instructions from user
     */
    public RlcpCheckRequestBody(List<ConditionForChecking> conditionsList, String instructions) {
        this(conditionsList, instructions, null);
    }

    /**
     * Returns list of conditions for checking. Warning: it is unmodifiable.
     *
     * @return list of conditions for checking
     */
    public List<ConditionForChecking> getConditionsList() {
        return conditionsList;
    }

    /**
     * Returns instructions from user.
     *
     * @return instructions from user
     */
    public String getInstructions() {
        return instructions;
    }

    /**
     * Returns pregenerated data.
     *
     * @return pregenerated data
     */
    public GeneratingResult getPreGenerated() {
        return preGenerated;
    }

    @Override
    public RlcpCheckRequest prepareRequest(String url) {
        return (RlcpCheckRequest) super.prepareRequest(url);
    }
    
    @Override
    public Document getDocument() {
        Document document = DocumentHelper.createDocument();

        Element requestElement = document.addElement(REQUEST);

        addConditionElement(requestElement);
        addInstructionsElement(requestElement);
        addPreGeneratedElement(requestElement);

        return document;
    }

    private void addConditionElement(Element requestElement) {
        Element conditionsElement = requestElement.addElement(CONDITIONS);
        for (ConditionForChecking conditionForChecking : conditionsList) {
            addSingleConditionElement(conditionsElement, conditionForChecking);
        }
    }

    private void addInstructionsElement(Element requestElement) {
        requestElement.addElement(INSTRUCTIONS).addComment(instructions);
    }

    private void addPreGeneratedElement(Element requestElement) {
        if (preGenerated != null) {
            Element preGeneratedElement = requestElement.addElement(PRE_GENERATED);
            preGeneratedElement.addElement(PRE_GENERATED_TEXT).addComment(preGenerated.getText());
            preGeneratedElement.addElement(PRE_GENERATED_CODE).addComment(preGenerated.getCode());
            preGeneratedElement.addElement(PRE_GENERATED_INSTRUCTIONS).addComment(preGenerated.getInstructions());
        }
    }

    private void addSingleConditionElement(Element conditionsElement, ConditionForChecking conditionForChecking) {
        Element conditionElement = conditionsElement.addElement(CONDITION_FOR_CHECKING);
        conditionElement.addAttribute(ID, Integer.toString(conditionForChecking.getId()));
        conditionElement.addAttribute(TIME, Long.toString(conditionForChecking.getTime()));
        conditionElement.addElement(INPUT).addComment(conditionForChecking.getInput());
        conditionElement.addElement(OUTPUT).addComment(conditionForChecking.getOutput());
    }

    @Override
    public RlcpMethod getMethod() {
        return Check.getInstance();
    }
}
