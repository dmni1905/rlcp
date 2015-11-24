package rlcp.method;

import rlcp.*;
import rlcp.check.*;

/**
 * RlcpMethod implementation for Check RLCP-method. Singleton with static
 * instance getter.
 * 
 * @author Eugene Efimchick 
 */
public class Check extends RlcpMethod {

    private static Check instance = new Check();

    private Check() {
    }

    /**
     * Singleton instance getter.
     *
     * @return instance
     */
    public static Check getInstance() {
        return instance;
    }

    @Override
    public RlcpConnector getConnector() {
        return RlcpConnector.getCheckConnector();
    }

    @Override
    public Class getRequestClass() {
        return RlcpCheckRequest.class;
    }

    @Override
    public Class getResponseClass() {
        return RlcpCheckResponse.class;
    }

    @Override
    public Class getRequestBodyClass() {
        return RlcpCheckRequestBody.class;
    }

    @Override
    public Class getResponseBodyClass() {
        return RlcpCheckResponseBody.class;
    }

    @Override
    public RlcpCheckParser getParser() {
        return new RlcpCheckParser();
    }

    @Override
    public RlcpRequestHeader createRequestHeader(RlcpUrl url, int contentLength) {
        return RlcpRequestHeader.createCheckHeader(url, contentLength);
    }

    @Override
    public String getName() {
        return "Check";
    }

    @Override
    public RlcpCheckRequest newRequest(RlcpRequestHeader header, RlcpRequestBody body) {
        return new RlcpCheckRequest(header, (RlcpCheckRequestBody) body);
    }

    @Override
    public RlcpCheckResponse newResponse(RlcpResponseHeader header, RlcpResponseBody body) {
        return new RlcpCheckResponse(header, (RlcpCheckResponseBody) body);
    }
}
