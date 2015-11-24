package rlcp.method;

import rlcp.*;
import rlcp.generate.*;

/**
 * RlcpMethod implementation for Generate RLCP-method. Singleton with static
 * instance getter.
 * 
 * @author Eugene Efimchick
 */
public class Generate extends RlcpMethod {
    private static Generate instance = new Generate();

    private Generate() {
    }

    /**
     * Singleton instance getter.
     *
     * @return instance
     */
    public static Generate getInstance() {
        return instance;
    }

    @Override
    public RlcpConnector getConnector() {
        return RlcpConnector.getGenerateConnector();
    }

    @Override
    public Class getRequestClass() {
        return RlcpGenerateRequest.class;
    }

    @Override
    public Class getResponseClass() {
        return RlcpGenerateResponse.class;
    }

    @Override
    public RlcpGenerateParser getParser() {
        return new RlcpGenerateParser();
    }

    @Override
    public Class getResponseBodyClass() {
        return RlcpGenerateResponseBody.class;
    }

    @Override
    public Class getRequestBodyClass() {
        return RlcpGenerateRequestBody.class;
    }

    @Override
    public RlcpRequestHeader createRequestHeader(RlcpUrl url, int contentLength) {
        return RlcpRequestHeader.createGenerateHeader(url, contentLength);
    }

    @Override
    public String getName() {
        return "Generate";
    }

    @Override
    public RlcpGenerateRequest newRequest(RlcpRequestHeader header, RlcpRequestBody body) {
        return new RlcpGenerateRequest(header, (RlcpGenerateRequestBody) body);
    }

    @Override
    public RlcpGenerateResponse newResponse(RlcpResponseHeader header, RlcpResponseBody body) {
        return new RlcpGenerateResponse(header, (RlcpGenerateResponseBody) body);
    }
    
}
