package rlcp;

import java.io.Serializable;
import org.dom4j.Document;
import rlcp.method.RlcpMethod;
import rlcp.util.DomHelper;

/**
 * Interface for RlcpResponse body entity.
 */
public abstract class RlcpResponseBody implements Serializable{

    private static final String docTypeName = "Response";
    private static final String docTypePublicId = null;
    private static final String docTypeSystemId = "http://de.ifmo.ru/--DTD/Response.dtd";

    /**
     * Returns XML document representation of RlcpResponseBody.
     *
     * @return XML document representation of RlcpResponseBody
     */
    public abstract Document getDocument();

    /**
     * Returns XML document representation of RlcpRequestBody, converted to
     * pretty printed String.
     *
     * @return XML document representation of RlcpRequestBody, converted to
     * pretty printed String
     */
    @Override
    public String toString(){
        Document doc = getDocument();
        doc.addDocType(docTypeName, docTypePublicId, docTypeSystemId);
        return DomHelper.getPrettyOrCompactIfExc(doc);
    }

    /**
     * Returns length of String returned by {@code toString()} method.
     *
     * @return length of String returned by {@code toString()} method
     */
    public int getContentLength(){
        return toString().length();
    }
    
    /**
     * Returns corresponding RlcpMethod instance.
     * @return corresponding RlcpMethod instance
     */
    public abstract RlcpMethod getMethod();
}
