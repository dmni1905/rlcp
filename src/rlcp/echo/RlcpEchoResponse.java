package rlcp.echo;

import rlcp.RlcpResponse;
import rlcp.RlcpResponseBody;
import rlcp.RlcpResponseHeader;
import rlcp.method.Echo;
import rlcp.method.RlcpMethod;

public class RlcpEchoResponse extends RlcpResponse {
    private RlcpResponseHeader header;
    private RlcpEchoResponseBody body;

    public RlcpEchoResponse(RlcpResponseHeader header, RlcpEchoResponseBody body) {
        this.header = header;
        this.body = body;
    }

    @Override
    public RlcpResponseHeader getHeader() {
        return header;
    }

    @Override
    public RlcpResponseBody getBody() {
        return body;
    }

    @Override
    public RlcpMethod getMethod() {
        return Echo.getInstance();
    }


}
