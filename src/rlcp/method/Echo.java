package rlcp.method;

import rlcp.*;
import rlcp.echo.RlcpEchoRequest;
import rlcp.echo.RlcpEchoRequestBody;
import rlcp.echo.RlcpEchoResponse;
import rlcp.echo.RlcpEchoResponseBody;

public class Echo extends RlcpMethod {
    private static Echo instance = new Echo();

    private Echo() {
    }

    public static Echo getInstance() {
        return instance;
    }

    @Override
    public RlcpConnector getConnector() {
        return RlcpConnector.getEchoConnector();
    }

    @Override
    public Class getRequestClass() {
        return RlcpEchoRequest.class;
    }

    @Override
    public Class getResponseClass() {
        return RlcpEchoResponse.class;
    }

    @Override
    public Class getRequestBodyClass() {
        return RlcpEchoRequestBody.class;
    }

    @Override
    public Class getResponseBodyClass() {
        return RlcpEchoResponseBody.class;
    }

    @Override
    public RlcpRequest newRequest(RlcpRequestHeader header, RlcpRequestBody body) {
        return new RlcpEchoRequest(header, (RlcpEchoRequestBody) body);
    }

    @Override
    public RlcpResponse newResponse(RlcpResponseHeader header, RlcpResponseBody body) {
        return new RlcpEchoResponse(header, (RlcpEchoResponseBody) body);
    }

    @Override
    public RlcpRequestHeader createRequestHeader(RlcpUrl url, int contentLength) {
        return RlcpRequestHeader.createEchoHeader(url, contentLength);
    }

    @Override
    public String getName() {
        return "Echo";
    }
}
