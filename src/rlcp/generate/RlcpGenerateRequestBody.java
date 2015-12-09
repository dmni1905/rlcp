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
 */
public class RlcpGenerateRequestBody extends RlcpRequestBody {

    private String condition;

    /**
     * Simple constructor.
     *
     * @param condition condition for generating
     */
    public RlcpGenerateRequestBody(String condition) {
        this.condition = condition;
    }

    /**
     * Returns condition for generating.
     *
     * @return condition for generating
     */
    public String getCondition() {
        return condition;
    }

    /**
     * RlcpGenerateRequest instance consists of this body and RlcpRequestHeader
     * built for RlcpMethod of this Body with specified url
     *
     * @param url url to RLCP-server
     * @return RlcpGenerateRequest instance consists of this body and RlcpRequestHeader
     * built for RlcpMethod of this Body with specified url
     */
    @Override
    public RlcpGenerateRequest prepareRequest(String url) {
        return (RlcpGenerateRequest) super.prepareRequest(url);
    }

    /**
     * Returns XML document representation of RlcpGenerateRequestBody.
     *
     * @return XML document representation of RlcpGenerateRequestBody
     */
    @Override
    public Document getDocument() {
        Document doc = DocumentHelper.createDocument();
        doc.addElement(REQUEST).addElement(CONDITIONS).addElement(CONDITION_FOR_GENERATING).addElement(INPUT).addComment(condition);
        return doc;
    }

    /**
     * Returns RlcpMethod implementation of this Generate-request body. Should NEVER return null.
     *
     * @return RlcpMethod implementation of this Generate-request body. Should NEVER return null
     */
    @Override
    public RlcpMethod getMethod() {
        return Generate.getInstance();
    }
}
