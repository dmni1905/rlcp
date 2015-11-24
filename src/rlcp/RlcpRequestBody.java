package rlcp;

import org.dom4j.Document;
import rlcp.method.RlcpMethod;
import rlcp.util.DomHelper;
import rlcp.util.Util;

import java.io.Serializable;

import static rlcp.util.Util.*;

/**
 * Interface for RlcpRequest body entity.
 *
 * @author Eugene Efimchick
 */
public abstract class RlcpRequestBody implements Serializable {

    private static final String docTypeName = "Request";
    private static final String docTypePublicId = null;
    private static final String docTypeSystemId = "http://de.ifmo.ru/--DTD/Request.dtd";

    /**
     * Returns XML document representation of RlcpRequestBody.
     *
     * @return XML document representation of RlcpRequestBody
     */
    public abstract Document getDocument();

    /**
     * Returns RlcpRequest instanse consists of this body and RlcpRequestHeader
     * built for RlcpMethod of this Body with specified url.
     *
     * @param url url to RLCP-server
     * @return RlcpRequest instanse consists of this body and RlcpRequestHeader
     * built for RlcpMethod of this Body with specified url
     */
    public RlcpRequest prepareRequest(String url) {
        return getMethod().buildRequest(url, this);
    }


    /**
     * Returns RlcpMethod implementation of this request body. Should NEVER return null.
     *
     * @return RlcpMethod implementation of this request body. Should NEVER return null
     */
    public abstract RlcpMethod getMethod();

    /**
     * Returns XML document representation of RlcpRequestBody, converted to
     * pretty printed String.
     *
     * @return XML document representation of RlcpRequestBody, converted to
     * pretty printed String
     */
    @Override
    public String toString() {
        Document doc = getDocument();
        doc.addDocType(docTypeName, docTypePublicId, docTypeSystemId);
        String bodyString = DomHelper.getPrettyOrCompactIfExc(doc);
        bodyString = bodyString.replace(winLineSeparator, unixLineSeparator);
        bodyString = bodyString.replace(macLineSeparator, unixLineSeparator);
        bodyString = bodyString.trim();
        return bodyString;
    }

    /**
     * Returns length of String returned by {@code toString()} method.
     *
     * @return length of String returned by {@code toString()} method
     */
    public int getContentLength() {
        return toString().length();
    }
}
