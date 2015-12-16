package rlcp.method;

import rlcp.*;
import rlcp.check.*;

/**
 * RlcpMethod implementation for Check RLCP-method. Singleton with static
 * instance getter.
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

    /**
     * Returns instance of RlcpConnector for Check method.
     *
     * @return instance of RlcpConnector for Check method
     */
    @Override
    public RlcpConnector getConnector() {
        return RlcpConnector.getCheckConnector();
    }

    /**
     * Returns RlcpCheckRequest.
     *
     * @return RlcpCheckRequest
     */
    @Override
    public Class getRequestClass() {
        return RlcpCheckRequest.class;
    }

    /**
     * Returns RlcpCheckResponse.
     *
     * @return RlcpCheckResponse
     */
    @Override
    public Class getResponseClass() {
        return RlcpCheckResponse.class;
    }

    /**
     * Returns RlcpCheckRequestBody.
     *
     * @return RlcpCheckRequestBody
     */
    @Override
    public Class getRequestBodyClass() {
        return RlcpCheckRequestBody.class;
    }

    /**
     * Returns RlcpCheckResponseBody.
     *
     * @return RlcpCheckResponseBody
     */
    @Override
    public Class getResponseBodyClass() {
        return RlcpCheckResponseBody.class;
    }

    /**
     * Returns RlcpRequestHeader for Check method with specified url and
     * contentLength.
     *
     * @param url           url to RLCP-server
     * @param contentLength body content length
     * @return RlcpParser implementation for Check method
     */
    @Override
    public RlcpRequestHeader createRequestHeader(RlcpUrl url, int contentLength) {
        return RlcpRequestHeader.createCheckHeader(url, contentLength);
    }

    /**
     * Returns Check method name as String of lowercase letters, but first letter is
     * capital.
     *
     * @return Check method name as String of lowercase letters, but first letter is
     * capital.
     */
    @Override
    public String getName() {
        return "Check";
    }

    /**
     * Returns new RlcpCheckRequest instance that consists of specified header and body.
     *
     * @param header request header
     * @param body   request body
     * @return new RlcpCheckRequest instance
     */
    @Override
    public RlcpCheckRequest newRequest(RlcpRequestHeader header, RlcpRequestBody body) {
        return new RlcpCheckRequest(header, (RlcpCheckRequestBody) body);
    }

    /**
     * Returns new RlcpCheckResponse instance that consists of specified header and body.
     *
     * @param header response header
     * @param body   response body
     * @return new RlcpCheckResponse instance
     */
    @Override
    public RlcpCheckResponse newResponse(RlcpResponseHeader header, RlcpResponseBody body) {
        return new RlcpCheckResponse(header, (RlcpCheckResponseBody) body);
    }
}
