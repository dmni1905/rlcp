package rlcp.generate;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import rlcp.RlcpRequest;
import rlcp.RlcpRequestBody;
import rlcp.method.Generate;
import rlcp.method.RlcpMethod;
import rlcp.util.Constants;

import static rlcp.util.Constants.*;

/**
 * RlcpRequestBody implementation for Generate method.
 * @author Eugene Efimchick
 */
public class RlcpGenerateRequestBody extends RlcpRequestBody {

    private String condition;

    /**
     * Simple constructor.
     * @param condition condition for generating
     */
    public RlcpGenerateRequestBody(String condition) {
        this.condition = condition;
    }

    /**
     * Returns condition for generating.
     * @return condition for generating
     */
    public String getCondition() {
        return condition;
    }

    @Override
    public RlcpGenerateRequest prepareRequest(String url) {
        return (RlcpGenerateRequest) super.prepareRequest(url);
    }
    
    @Override
    public Document getDocument() {
        Document doc = DocumentHelper.createDocument();
        doc.addElement(REQUEST).addElement(CONDITIONS).addElement(CONDITION_FOR_GENERATING).addElement(INPUT).addComment(condition);
        return doc;
    }

    @Override
    public RlcpMethod getMethod() {
        return Generate.getInstance();
    }
}
