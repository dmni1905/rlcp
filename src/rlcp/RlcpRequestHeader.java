package rlcp;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import rlcp.exception.BadRlcpHeaderException;
import rlcp.method.*;
import rlcp.util.Util;

import static rlcp.util.Util.*;

/**
 * Class for header of RlcpRequest entity. Contains url as RlcpUrl instance,
 * content length as integer and method as uppercased String returned by {@code RlcpMethod.getName()}
 * method (due to suport earlier protocol versions).
 * <p>
 * Also provides static methods to build RlcpRequestHeader instances for each
 * Method.
 */
public class RlcpRequestHeader implements Serializable {

    private String method;
    private RlcpUrl url;
    private int contentLength;
    private RlcpHeaderFieldsContainer headerFields = new RlcpHeaderFieldsContainer();

    /**
     * Simple constructor.
     *
     * @param method        RlcpMethod instance. Should be not null
     * @param url           url to RLCP-server. Should be not null
     * @param contentLength content length of request body
     * @throws IllegalArgumentException if method or url is null
     */
    public RlcpRequestHeader(RlcpMethod method, RlcpUrl url, int contentLength) throws IllegalArgumentException {
        checkNotNull("Method or url are null in RlcpRequestHeader constructor", method, url);
        this.method = method.getName().toUpperCase();
        this.url = url;
        this.contentLength = contentLength;
    }

    /**
     * Returns String representation to send as raw String to RLCP-server.
     *
     * @return String representation to send as raw String to RLCP-server
     */
    @Override
    public String toString() {
        StringBuilder headerBuilder = new StringBuilder();

        headerBuilder.append(method).append(winLineSeparator);
        headerBuilder.append("url:").append(url).append(winLineSeparator);
        headerBuilder.append("content-length:").append(contentLength).append(winLineSeparator);

        for (String name : headerFields.getHeaderFieldNames()) {
            String value = headerFields.getHeaderFieldByName(name);
            headerBuilder.append(name).append(":").append(value).append(winLineSeparator);
        }

        return headerBuilder.toString();
    }

    /**
     * Returns parsed from raw String RlcpRequestHeader instance.
     *
     * @param requestHeaderString raw String RlcpRequestHeader representation
     * @return parsed from raw String RlcpRequestHeader instance
     * @throws BadRlcpHeaderException
     */
    public static RlcpRequestHeader parse(String requestHeaderString, RlcpMethod method) throws BadRlcpHeaderException {
        checkStringNotNullNotEmpty(requestHeaderString);

        String lineSeparator = getLineSeparatorFor(requestHeaderString);

        String[] headers = requestHeaderString.split(lineSeparator);

        String parsedMethod = parseMethodHeaderField(headers[0], requestHeaderString);

        RlcpUrl parsedUrl = null;
        int parsedContentLength = 0;
        Map<String, String> optionalHeaderFields = new HashMap<String, String>();
        for (String header : headers) {
            if (header.toLowerCase().startsWith("content-length:")) {
                parsedContentLength = parseContentLengthHeaderField(header, requestHeaderString.toLowerCase());
            } else if (header.startsWith("url:")) {
                parsedUrl = parseUrlHeaderField(headers[1], requestHeaderString);
            } else {
                try {
                    String headerFieldName = parseHeaderFieldName(header);
                    String headerFieldValue = parseNamedHeaderField(header, headerFieldName + ":");
                    optionalHeaderFields.put(headerFieldName, headerFieldValue);
                } catch (BadRlcpHeaderException brhe) {
                }
            }
        }

        if (parsedUrl == null) {
            throw new BadRlcpHeaderException("too few elements in " + requestHeaderString);
        }

        RlcpRequestHeader requestHeader = new RlcpRequestHeader(method, parsedUrl, parsedContentLength);
        for (Map.Entry<String, String> entry : optionalHeaderFields.entrySet()) {
            String name = entry.getKey();
            String value = entry.getValue();
            requestHeader.getOptionalHeaderFieldsContainer().setHeaderField(name, value);
        }

        return requestHeader;
    }

    /**
     * Returns body content-length.
     *
     * @return body content-length
     */
    public int getContentLength() {
        return contentLength;
    }

    /**
     * Returns header method, uppercased String like returned by {@code RlcpMethod.getName().toUpperCase()}.
     *
     * @return header method, uppercased String like returned by {@code RlcpMethod.getName().toUpperCase()}
     */
    public String getMethod() {
        return method;
    }

    /**
     * Returns url to RLCP-server.
     *
     * @return url to RLCP-server
     */
    public RlcpUrl getUrl() {
        return url;
    }

    /**
     * Returns new instanse of RlcpRequestHeader for Check RLCP-method with
     * specified url and contantLength.
     *
     * @param url           url to RLCP-server
     * @param contentLength content length of request body
     * @return new instanse of RlcpRequestHeader for Check RLCP-method with
     * specified url and contantLength
     */
    public static RlcpRequestHeader createCheckHeader(RlcpUrl url, int contentLength) {
        return new RlcpRequestHeader(Check.getInstance(), url, contentLength);
    }

    /**
     * Returns new instanse of RlcpRequestHeader for Generate RLCP-method with
     * specified url and contantLength.
     *
     * @param url           url to RLCP-server
     * @param contentLength content length of request body
     * @return new instanse of RlcpRequestHeader for Generate RLCP-method with
     * specified url and contantLength
     */
    public static RlcpRequestHeader createGenerateHeader(RlcpUrl url, int contentLength) {
        return new RlcpRequestHeader(Generate.getInstance(), url, contentLength);
    }

    /**
     * Returns new instanse of RlcpRequestHeader for Calculate RLCP-method with
     * specified url and contantLength.
     *
     * @param url           url to RLCP-server
     * @param contentLength content length of request body
     * @return new instanse of RlcpRequestHeader for Calculate RLCP-method with
     * specified url and contantLength
     */
    public static RlcpRequestHeader createCalculateHeader(RlcpUrl url, int contentLength) {
        return new RlcpRequestHeader(Calculate.getInstance(), url, contentLength);
    }

    /**
     * Returns special header fields container, where all other (not url and
     * content-length) fields stored.
     *
     * @return special header fields container, where all other (not url and
     * content-length) fields stored
     */
    public RlcpHeaderFieldsContainer getOptionalHeaderFieldsContainer() {
        return headerFields;
    }

    public static RlcpRequestHeader createEchoHeader(RlcpUrl url, int contentLength) {
        return new RlcpRequestHeader(Echo.getInstance(), url, contentLength);
    }
}
