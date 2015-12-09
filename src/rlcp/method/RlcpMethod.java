package rlcp.method;

import rlcp.*;
import rlcp.exception.BadRlcpUrlException;

/**
 * Interface for RLCP-methods. Provides public static implementstion for each
 * method and Recognizer static class for method name recognition by raw Request
 * or Response String. Also provides some all-around useful methods.
 */
public abstract class RlcpMethod {

    /**
     * Returns method-specified RlcpConnector instance. Should NEVER return
     * null.
     *
     * @return method-specified RlcpConnector instance. Should NEVER return null
     */
    public abstract RlcpConnector getConnector();

    /**
     * Returns request class implementing RlcpRequest. Should NEVER return null.
     *
     * @return request class implementing RlcpRequest. Should NEVER return null
     */
    public abstract Class getRequestClass();

    /**
     * Returns response class implementing RlcpResponse. Should NEVER return
     * null.
     *
     * @return response class implementing RlcpResponse. Should NEVER return
     * null
     */
    public abstract Class getResponseClass();

    /**
     * Returns request body class implementing RlcpRequestBody. Should NEVER return
     * null.
     *
     * @return request body class implementing RlcpRequestBody. Should NEVER return
     * null
     */
    public abstract Class getRequestBodyClass();


    /**
     * Returns response body class implementing RlcpResponseBody. Should NEVER return
     * null.
     *
     * @return response body class implementing RlcpResponseBody. Should NEVER return
     * null
     */
    public abstract Class getResponseBodyClass();

    /**
     * Returns RlcpParser implementation for this method. Should NEVER return
     * null.
     *
     * @return RlcpParser implementation for this method. Should NEVER return
     * null
     */
    public abstract RlcpParser getParser();

    /**
     * Returns RlcpRequest instance for this method with specified body and url.
     *
     * @param url  url to RLCP-server
     * @param body RlcpRequestBody implemenation for this method
     * @return RlcpRequest instanse for this method with specified body and url
     */
    public RlcpRequest buildRequest(String url, RlcpRequestBody body) {
        if (!getRequestBodyClass().isInstance(body)) {
            throw new IllegalArgumentException("body is not instanse of " + getRequestBodyClass().getName());
        }
        try {
            RlcpRequestHeader header = createRequestHeader(RlcpUrl.parse(url), body.getContentLength());
            RlcpRequest request = newRequest(header, body);
            return request;
        } catch (BadRlcpUrlException ex) {
            throw new IllegalArgumentException(ex);
        }
    }

    /**
     * Returns RlcpResponse instance for this method with specified body.
     *
     * @param body RlcpResponseBody implemenation for this method
     * @return RlcpResponse instance for this method with specified body
     */
    public RlcpResponse buildResponse(RlcpResponseBody body) {
        if (!getResponseBodyClass().isInstance(body)) {
            throw new IllegalArgumentException("body is not instance of " + getResponseBodyClass().getName());
        }
        RlcpResponseHeader header = RlcpResponseHeader.createSuccessfulHeader(body.getContentLength());
        RlcpResponse response = newResponse(header, body);
        return response;
    }

    /**
     * Returns new RlcpRequest instance that consists of specified header and body.
     *
     * @param header request header
     * @param body   request body
     * @return new RlcpRequest instance
     */
    public abstract RlcpRequest newRequest(RlcpRequestHeader header, RlcpRequestBody body);

    /**
     * Returns new RlcpResponse instance that consists of specified header and body.
     *
     * @param header response header
     * @param body   response body
     * @return new RlcpResponse instance
     */
    public abstract RlcpResponse newResponse(RlcpResponseHeader header, RlcpResponseBody body);

    /**
     * Returns RlcpRequestHeader for this method with specified url and
     * contentLength.
     *
     * @param url           url to RLCP-server
     * @param contentLength body content length
     * @return RlcpRequestHeader with specified url and contentLength
     */
    public abstract RlcpRequestHeader createRequestHeader(RlcpUrl url, int contentLength);

    /**
     * Returns method name as String of lowercase letters, but first letter is
     * capital. For example, "Check".
     *
     * @return method name as String of lowercase letters, but first letter is
     * capital.
     */
    public abstract String getName();

    /**
     * Returns {@code true} if raw String param looks like raw request or
     * response of this method, {@code false} otherwise.
     *
     * @param raw String to be tested
     * @return {@code true} if raw String param looks like raw request or
     * response of this method, {@code false} otherwise
     */
    public boolean looksLike(String raw) {
        return this.equals(Recognizer.recognizeMethod(raw));
    }

    /**
     * Static class for method name recognition by raw Request or Response.
     */
    public static class Recognizer {

        /**
         * Returns RlcpMethod instance if method recognized, {@code null}
         * otherwise.
         *
         * @param raw raw Request or Response repesentation
         * @return RlcpMethod instance if method recognized, {@code null}
         * otherwise
         */
        public static RlcpMethod recognizeMethod(String raw) {
            String rawInLower = raw.toLowerCase();
            if (rawInLower.startsWith("check")) {
                return Check.getInstance();
            } else if (rawInLower.startsWith("generate")) {
                return Generate.getInstance();
            } else if (rawInLower.startsWith("calculate")) {
                return Calculate.getInstance();
            }

            if (rawInLower.contains("conditionforchecking")) {
                return Check.getInstance();
            } else if (rawInLower.contains("conditionforgenerating")) {
                return Generate.getInstance();
            } else if (rawInLower.contains("conditionforcalculating")) {
                return Calculate.getInstance();
            }

            if (rawInLower.contains("checkingresult")) {
                return Check.getInstance();
            } else if (rawInLower.contains("generatingresult")) {
                return Generate.getInstance();
            } else if (rawInLower.contains("calculatingresult")) {
                return Calculate.getInstance();
            }

            return null;
        }
    }
}
