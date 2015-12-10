package rlcp.generate;

import java.io.IOException;
import java.net.UnknownHostException;
import rlcp.RlcpRequest;
import rlcp.RlcpRequestBody;
import rlcp.RlcpRequestHeader;
import rlcp.exception.BadRlcpRequestException;
import rlcp.exception.BadRlcpResponseException;
import rlcp.method.Generate;
import rlcp.util.Util;

/**
 * RlcpRequest implementation for Generate method.
 */
public class RlcpGenerateRequest extends RlcpRequest {

    private RlcpRequestHeader header;
    private RlcpGenerateRequestBody body;

    /**
     * Simple constructor.
     *
     * @param header request header (should be not null)
     * @param body   request body (should be not null)
     * @throws IllegalArgumentException if header or body is null
     */
    public RlcpGenerateRequest(RlcpRequestHeader header, RlcpGenerateRequestBody body) throws IllegalArgumentException {
        Util.checkNotNull("Header or body is null in RlcpGenerateRequest constructor", header, body);
        this.header = header;
        this.body = body;
    }

    /**
     * Returns request header for generating.
     *
     * @return request header for generating
     * @see RlcpRequestHeader
     */
    @Override
    public RlcpRequestHeader getHeader() {
        return header;
    }

    /**
     * Returns request body for generating.
     *
     * @return request body for generating.
     * @see RlcpGenerateRequestBody
     */
    @Override
    public RlcpGenerateRequestBody getBody() {
        return body;
    }

    /**
     * Returns {@code Generate} instance. Should NEVER return null.
     *
     * @return {@code Generate} instance. Should NEVER return null
     * @see rlcp.method.Generate
     */
    @Override
    public Generate getMethod() {
        return Generate.getInstance();
    }

    /**
     * Executes request, sending it to RLCP-server and recieving response to
     * return.
     *
     * @param timeout timeout for connection in milliseconds
     * @return {@code RlcpGenerateResponse} instance for this method returned
     * from RLCP-server
     * @throws UnknownHostException, IOException, BadRlcpResponseException, BadRlcpRequestException
     * @see RlcpGenerateResponse
     */
    @Override
    public RlcpGenerateResponse execute(int timeout) throws UnknownHostException, IOException, BadRlcpResponseException, BadRlcpRequestException {
        return (RlcpGenerateResponse) super.execute(timeout);
    }

    /**
     * Executes request, sending it to RLCP-server and recieving response to
     * return.
     *
     * @return {@code RlcpGenerateResponse} instance for this method returned
     * from RLCP-server
     * @throws UnknownHostException, IOException, BadRlcpResponseException, BadRlcpRequestException
     * @see RlcpGenerateResponse
     */
    @Override
    public RlcpGenerateResponse execute() throws UnknownHostException, IOException, BadRlcpResponseException, BadRlcpRequestException {
        return execute(0);
    }
}
