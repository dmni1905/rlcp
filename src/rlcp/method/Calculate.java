package rlcp.method;

import rlcp.*;
import rlcp.calculate.*;

/**
 * RlcpMethod implementation for Calculate RLCP-method. Singleton with
 * static instance getter.
 */
public class Calculate extends RlcpMethod {
    private static Calculate instance = new Calculate();

    private Calculate() {
    }

    /**
     * Singleton instance getter.
     *
     * @return instance
     */
    public static Calculate getInstance() {
        return instance;
    }

    /**
     * Returns instance of RlcpConnector for Calculate method.
     *
     * @return instance of RlcpConnector for Calculate method
     */
    @Override
    public RlcpConnector getConnector() {
        return RlcpConnector.getCalculateConnector();
    }

    /**
     * Returns RlcpCalculateRequest.
     *
     * @return RlcpCalculateRequest
     */
    @Override
    public Class getRequestClass() {
        return RlcpCalculateRequest.class;
    }

    /**
     * Returns RlcpCalculateResponse.
     *
     * @return RlcpCalculateResponse
     */
    @Override
    public Class getResponseClass() {
        return RlcpCalculateResponse.class;
    }

    /**
     * Returns RlcpCalculateParser.
     *
     * @return RlcpCalculateParser
     */
    @Override
    public RlcpCalculateParser getParser() {
        return new RlcpCalculateParser();
    }

    /**
     * Returns RlcpRequestHeader for Calculate method with specified url and
     * contentLength.
     *
     * @param url           url to RLCP-server
     * @param contentLength body content length
     * @return RlcpParser implementation for Calculate method
     */
    @Override
    public RlcpRequestHeader createRequestHeader(RlcpUrl url, int contentLength) {
        return RlcpRequestHeader.createCalculateHeader(url, contentLength);
    }

    /**
     * Returns Calculate method name as String of lowercase letters, but first letter is
     * capital.
     *
     * @return Calculate method name as String of lowercase letters, but first letter is
     * capital.
     */
    @Override
    public String getName() {
        return "Calculate";
    }

    /**
     * Returns RlcpCalculateRequestBody.
     *
     * @return RlcpCalculateRequestBody
     */
    @Override
    public Class getRequestBodyClass() {
        return RlcpCalculateRequestBody.class;
    }

    /**
     * Returns RlcpCalculateResponseBody.
     *
     * @return RlcpCalculateResponseBody
     */
    @Override
    public Class getResponseBodyClass() {
        return RlcpCalculateResponseBody.class;
    }

    /**
     * Returns new RlcpCalculateRequest instance that consists of specified header and body.
     *
     * @param header request header
     * @param body   request body
     * @return new RlcpCalculateRequest instance
     */
    @Override
    public RlcpCalculateRequest newRequest(RlcpRequestHeader header, RlcpRequestBody body) {
        return new RlcpCalculateRequest(header, (RlcpCalculateRequestBody) body);
    }

    /**
     * Returns new RlcpCalculateResponse instance that consists of specified header and body.
     *
     * @param header response header
     * @param body   response body
     * @return new RlcpCalculateResponse instance
     */
    @Override
    public RlcpCalculateResponse newResponse(RlcpResponseHeader header, RlcpResponseBody body) {
        return new RlcpCalculateResponse(header, (RlcpCalculateResponseBody) body);
    }

}
