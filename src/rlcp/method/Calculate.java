package rlcp.method;

import rlcp.*;
import rlcp.calculate.*;

/**
 * RlcpMethod implementation for Calculate RLCP-method. Singleton with
 * static instance getter.
 * 
 * @author Eugene Efimchick
 */
public class Calculate extends RlcpMethod {
    private static Calculate instance = new Calculate();

    private Calculate() {
    }

    /**
     * Singleton instance getter.
     * @return instance
     */
    public static Calculate getInstance() {
        return instance;
    }

    @Override
    public RlcpConnector getConnector() {
        return RlcpConnector.getCalculateConnector();
    }

    @Override
    public Class getRequestClass() {
        return RlcpCalculateRequest.class;
    }

    @Override
    public Class getResponseClass() {
        return RlcpCalculateResponse.class;
    }

    @Override
    public RlcpCalculateParser getParser() {
        return new RlcpCalculateParser();
    }

    @Override
    public RlcpRequestHeader createRequestHeader(RlcpUrl url, int contentLength) {
        return RlcpRequestHeader.createCalculateHeader(url, contentLength);
    }

    @Override
    public String getName() {
        return "Calculate";
    }

    @Override
    public Class getRequestBodyClass() {
        return RlcpCalculateRequestBody.class;
    }

    @Override
    public Class getResponseBodyClass() {
        return RlcpCalculateResponseBody.class;
    }

    @Override
    public RlcpCalculateRequest newRequest(RlcpRequestHeader header, RlcpRequestBody body) {
        return new RlcpCalculateRequest(header, (RlcpCalculateRequestBody) body);
    }

    @Override
    public RlcpCalculateResponse newResponse(RlcpResponseHeader header, RlcpResponseBody body) {
        return new RlcpCalculateResponse(header, (RlcpCalculateResponseBody) body);
    }
    
}
