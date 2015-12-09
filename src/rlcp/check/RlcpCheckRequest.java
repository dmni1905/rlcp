package rlcp.check;

import java.io.IOException;
import java.net.UnknownHostException;

import rlcp.RlcpRequest;
import rlcp.RlcpRequestBody;
import rlcp.RlcpRequestHeader;
import rlcp.exception.BadRlcpRequestException;
import rlcp.exception.BadRlcpResponseException;
import rlcp.method.Check;
import rlcp.util.Util;

/**
 * RlcpRequest implementation for Check method.
 */
public class RlcpCheckRequest extends RlcpRequest {

    private RlcpRequestHeader header;
    private RlcpCheckRequestBody body;

    /**
     * Simple constructor.
     *
     * @param header request header (should be not null)
     * @param body   request body (should be not null)
     * @throws IllegalArgumentException if header or body is null
     */
    public RlcpCheckRequest(RlcpRequestHeader header, RlcpCheckRequestBody body) throws IllegalArgumentException {
        Util.checkNotNull("Header or body is null in RlcpCheckRequest constructor", header, body);
        this.header = header;
        this.body = body;
    }

    /**
     * Returns request header for checking.
     *
     * @return request header for checking.
     * @see RlcpRequestBody
     */
    @Override
    public RlcpRequestHeader getHeader() {
        return header;
    }

    /**
     * Returns request body for checking.
     *
     * @return request body for checking.
     * @see RlcpRequestBody
     */
    @Override
    public RlcpRequestBody getBody() {
        return body;
    }

    /**
     * Returns {@code Check} instance. Should NEVER return null.
     *
     * @return {@code Check} instance. Should NEVER return null
     * @see rlcp.method.Check
     */
    @Override
    public Check getMethod() {
        return Check.getInstance();
    }

    /**
     * Executes request, sending it to RLCP-server and recieving response to
     * return.
     *
     * @param timeout timeout for connection in milliseconds
     * @return {@code RlcpCheckResponse} instance for this method returned
     * from RLCP-server
     * @throws UnknownHostException, IOException, BadRlcpResponseException, BadRlcpRequestException
     * @see RlcpCheckResponse
     */
    @Override
    public RlcpCheckResponse execute(int timeout) throws UnknownHostException, IOException, BadRlcpResponseException, BadRlcpRequestException {
        return (RlcpCheckResponse) super.execute(timeout);
    }

    /**
     * Executes request, sending it to RLCP-server and recieving response to
     * return.
     *
     * @return {@code RlcpCheckResponse} instance for this method returned
     * from RLCP-server
     * @throws UnknownHostException, IOException, BadRlcpResponseException, BadRlcpRequestException
     * @see RlcpCheckResponse
     */
    @Override
    public RlcpCheckResponse execute() throws UnknownHostException, IOException, BadRlcpResponseException, BadRlcpRequestException {
        return execute(0);
    }

}
