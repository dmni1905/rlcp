package rlcp.echo;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import rlcp.RlcpResponseBody;
import rlcp.method.Echo;
import rlcp.method.RlcpMethod;

public class RlcpEchoResponseBody extends RlcpResponseBody {

    @Override
    public Document getDocument() {
        Document document = DocumentHelper.createDocument();
        return document;
    }

    @Override
    public RlcpMethod getMethod() {
        return Echo.getInstance();
    }

    @Override
    public String toString() {
        return "";
    }
}
