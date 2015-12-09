package rlcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Serializable;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rlcp.exception.BadRlcpHeaderException;
import rlcp.util.Util;

/**
 * Class for header of RlcpResponse entity. Contains content length as integer,
 * response Code as String returned by {@code RlcpCode.getCode()}, and errors as
 * String returned by {@code RlcpCode.getMeassage()} (may contain additinal
 * errors info) or be null. Unmodifiable.
 * <p>
 * Also provides static methods for getting successful and failed header
 * instances.
 */
public class RlcpResponseHeader implements Serializable {

    private RlcpCode responseCode;
    private String errors;
    private int contentLength;
    private RlcpHeaderFieldsContainer headerFields = new RlcpHeaderFieldsContainer();

    /**
     * Simple constructor.
     *
     * @param responseCode  RlcpCode instance. Should not be null.
     * @param errors        String containing additinal errors info. May be null.
     * @param contentLength length of RlcpResponse body
     * @throws IllegalArgumentException if RlcpCode is null.
     */
    private RlcpResponseHeader(RlcpCode responseCode, String errors, int contentLength) throws IllegalArgumentException {
        Util.checkNotNull("Response code is null in RlcpResponseHeader constructor", responseCode);
        this.responseCode = responseCode;
        this.errors = errors;
        this.contentLength = contentLength;
    }

    /**
     * Returns String representation to receive as raw String to RLCP-server.
     *
     * @return String representation to receive as raw String to RLCP-server
     */
    @Override
    public String toString() {

        StringBuilder headerBuilder = new StringBuilder();

        headerBuilder.append(responseCode.getCode());

        if (!isSuccessful()) {
            headerBuilder.append(" ").append(getErrors());
        }

        headerBuilder.append(Util.nativeLineSeparator);
        headerBuilder.append("content-length:").append(contentLength).append(Util.nativeLineSeparator);

        for (String name : headerFields.getHeaderFieldNames()) {
            String value = headerFields.getHeaderFieldByName(name);
            headerBuilder.append(name).append(":").append(value).append(Util.nativeLineSeparator);
        }

        return headerBuilder.toString();
    }

    /**
     * Returns parsed from raw String RlcpResponseHeader instance.
     *
     * @param responseHeaderString raw String RlcpResponseHeader representation
     * @return parsed from raw String RlcpResponseHeader instance
     * @throws BadRlcpHeaderException
     */
    public static RlcpResponseHeader parse(String responseHeaderString) throws BadRlcpHeaderException {
        Util.checkStringNotNullNotEmpty(responseHeaderString);

        BufferedReader br = new BufferedReader(new StringReader(responseHeaderString));
        boolean firstLineRead = false;
        String firstLine = null;
        List<String> headers = new ArrayList<String>();
        try {
            while (br.ready()) {
                String line = br.readLine();
                if (line == null) {
                    break;
                }
                if (!firstLineRead) {
                    firstLine = line;
                    firstLineRead = true;
                } else {
                    headers.add(line);
                }
            }
        } catch (IOException ex) {
            //what could be wrong with StringBuilder
            throw new BadRlcpHeaderException(ex);
        }

        String parsedResponseCode, parsedErrors = null;
        if (firstLine.contains(" ")) {
            parsedResponseCode = firstLine.substring(0, firstLine.indexOf(" "));
            parsedErrors = Util.omitLineSeparatorArtefacts(firstLine.substring(firstLine.indexOf(" ")));
        } else {
            parsedResponseCode = Util.omitLineSeparatorArtefacts(firstLine);
        }

        int parsedContentLength = 0;
        Map<String, String> optionalHeaderFields = new HashMap<String, String>();
        for (String header : headers) {
            if (header.toLowerCase().startsWith("content-length:")) {
                parsedContentLength = Util.parseContentLengthHeaderField(header.toLowerCase(), responseHeaderString);
            } else {
                try {
                    String headerFieldName = Util.parseHeaderFieldName(header);
                    String headerFieldValue = Util.parseNamedHeaderField(header, headerFieldName + ":");
                    optionalHeaderFields.put(headerFieldName, headerFieldValue);
                } catch (BadRlcpHeaderException brhe) {
                }
            }
        }

        RlcpResponseHeader responseHeader = new RlcpResponseHeader(RlcpCode.getByCode(parsedResponseCode), parsedErrors, parsedContentLength);
        for (Map.Entry<String, String> entry : optionalHeaderFields.entrySet()) {
            String name = entry.getKey();
            String value = entry.getValue();
            responseHeader.getOptionalHeaderFieldsContainer().setHeaderField(name, value);
        }
        return responseHeader;
    }

    /**
     * Returns RlcpResponse body content length.
     *
     * @return RlcpResponse body content length
     */
    public int getContentLength() {
        return contentLength;
    }

    /**
     * Returns errors String.
     *
     * @return errors String
     */
    public String getErrors() {
        if (errors != null && errors.contains(responseCode.getMessage())) {
            return errors;
        } else if (errors != null) {
            return responseCode.getMessage() + errors;
        } else {
            return responseCode.getMessage();
        }
    }

    /**
     * Returns response code as returned by {@code RlcpCode.getCode()}.
     *
     * @return response code as returned by {@code RlcpCode.getCode()}
     */
    public String getResponseCode() {
        return responseCode.getCode();
    }

    /**
     * Returns {@code true} if header is successful, {@code false} otherwise.
     *
     * @return {@code true} if header is successful, {@code false} otherwise
     */
    public boolean isSuccessful() {
        return responseCode.equals(RlcpCode.Success);
    }

    /**
     * Returns new header with successful code.
     *
     * @param contentLength body content length
     * @return header with successful code
     */
    public static RlcpResponseHeader createSuccessfulHeader(int contentLength) {
        return new RlcpResponseHeader(RlcpCode.Success, null, contentLength);
    }

    /**
     * Returns new header with specified errorCode and errors message.
     *
     * @param failCode errorCode as returned by {@code RlcpCode.getCode()}
     * @param errors   errors Message
     * @return new header with specified errorCode and errors message
     */
    public static RlcpResponseHeader createFailedHeader(String failCode, String errors) {
        return new RlcpResponseHeader(RlcpCode.getByCode(failCode), errors, 0);
    }

    /**
     * Returns special header fields container, where all other (not content-length) fields stored.
     *
     * @return special header fields container, where all other (not content-length) fields stored.
     */
    public RlcpHeaderFieldsContainer getOptionalHeaderFieldsContainer() {
        return headerFields;
    }

}
