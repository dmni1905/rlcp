package rlcp.echo;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import rlcp.RlcpRequestBody;
import rlcp.method.Echo;
import rlcp.method.RlcpMethod;

public class RlcpEchoRequestBody extends RlcpRequestBody {

    @Override
    public Document getDocument() {
        Document doc = DocumentHelper.createDocument();
        return doc;
    }

    @Override
    public RlcpMethod getMethod() {
        return Echo.getInstance();
    }

    @Override
    public RlcpEchoRequest prepareRequest(String url) {
        return (RlcpEchoRequest) super.prepareRequest(url);
    }

    @Override
    public String toString() {
        return "";
    }
}
