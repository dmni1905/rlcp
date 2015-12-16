package rlcp.method;

import rlcp.*;
import rlcp.generate.*;

/**
 * RlcpMethod implementation for Generate RLCP-method. Singleton with static
 * instance getter.
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

    /**
     * Returns instance of RlcpConnector for Generate method.
     *
     * @return instance of RlcpConnector for Generate method
     */
    @Override
    public RlcpConnector getConnector() {
        return RlcpConnector.getGenerateConnector();
    }

    /**
     * Returns RlcpGenerateRequest.
     *
     * @return RlcpGenerateRequest
     */
    @Override
    public Class getRequestClass() {
        return RlcpGenerateRequest.class;
    }

    /**
     * Returns RlcpGenerateResponse.
     *
     * @return RlcpGenerateResponse
     */
    @Override
    public Class getResponseClass() {
        return RlcpGenerateResponse.class;
    }

    /**
     * Returns RlcpGenerateResponseBody.
     *
     * @return RlcpGenerateResponseBody
     */
    @Override
    public Class getResponseBodyClass() {
        return RlcpGenerateResponseBody.class;
    }

    /**
     * Returns RlcpGenerateRequestBody.
     *
     * @return RlcpGenerateRequestBody
     */
    @Override
    public Class getRequestBodyClass() {
        return RlcpGenerateRequestBody.class;
    }

    /**
     * Returns RlcpRequestHeader for Generate method with specified url and
     * contentLength.
     *
     * @param url           url to RLCP-server
     * @param contentLength body content length
     * @return RlcpParser implementation for Generate method
     */
    @Override
    public RlcpRequestHeader createRequestHeader(RlcpUrl url, int contentLength) {
        return RlcpRequestHeader.createGenerateHeader(url, contentLength);
    }

    /**
     * Returns Generate method name as String of lowercase letters, but first letter is
     * capital.
     *
     * @return Generate method name as String of lowercase letters, but first letter is
     * capital.
     */
    @Override
    public String getName() {
        return "Generate";
    }

    /**
     * Returns new RlcpGenerateRequest instance that consists of specified header and body.
     *
     * @param header request header
     * @param body   request body
     * @return new RlcpGenerateRequest instance
     */
    @Override
    public RlcpGenerateRequest newRequest(RlcpRequestHeader header, RlcpRequestBody body) {
        return new RlcpGenerateRequest(header, (RlcpGenerateRequestBody) body);
    }

    /**
     * Returns new RlcpGenerateResponse instance that consists of specified header and body.
     *
     * @param header response header
     * @param body   response body
     * @return new RlcpGenerateResponse instance
     */
    @Override
    public RlcpGenerateResponse newResponse(RlcpResponseHeader header, RlcpResponseBody body) {
        return new RlcpGenerateResponse(header, (RlcpGenerateResponseBody) body);
    }

}
