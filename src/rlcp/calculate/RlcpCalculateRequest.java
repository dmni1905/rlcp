package rlcp.calculate;

import java.io.IOException;
import java.net.UnknownHostException;

import rlcp.RlcpRequest;
import rlcp.RlcpRequestBody;
import rlcp.RlcpRequestHeader;
import rlcp.exception.BadRlcpHeaderException;
import rlcp.exception.BadRlcpRequestException;
import rlcp.exception.BadRlcpResponseException;
import rlcp.exception.RlcpException;
import rlcp.method.Calculate;
import rlcp.util.Util;

/**
 * RlcpRequest implementation for Calculate method.
 */
public class RlcpCalculateRequest extends RlcpRequest {

    private RlcpRequestHeader header;
    private RlcpCalculateRequestBody body;

    /**
     * Simple constructor.
     *
     * @param header request header (should be not null)
     * @param body   request body (should be not null)
     * @throws IllegalArgumentException if header or body is null
     */
    public RlcpCalculateRequest(RlcpRequestHeader header, RlcpCalculateRequestBody body) throws IllegalArgumentException {
        Util.checkNotNull("Header or body is null in RlcpCalculateRequest constructor", header, body);
        this.header = header;
        this.body = body;
    }

    /**
     * Returns request header for calculating.
     *
     * @return request header for calculating.
     * @see RlcpRequestHeader
     */
    @Override
    public RlcpRequestHeader getHeader() {
        return header;
    }

    /**
     * Returns request body for calculating.
     *
     * @return request body for calculating.
     * @see RlcpRequestBody
     */
    @Override
    public RlcpCalculateRequestBody getBody() {
        return body;
    }

    /**
     * Returns {@code Calculate} instance. Should NEVER return null.
     *
     * @return {@code Calculate} instance. Should NEVER return null
     * @see rlcp.method.Calculate
     */
    @Override
    public Calculate getMethod() {
        return Calculate.getInstance();
    }

    /**
     * Executes request, sending it to RLCP-server and recieving response to
     * return.
     *
     * @param timeout timeout for connection in milliseconds
     * @return {@code RlcpCalculateResponse} instance for this method returned
     * from RLCP-server
     * @throws UnknownHostException, IOException, BadRlcpResponseException, BadRlcpRequestException
     * @see RlcpCalculateResponse
     */
    @Override
    public RlcpCalculateResponse execute(int timeout) throws RlcpException {
        return (RlcpCalculateResponse) super.execute(timeout);
    }

    /**
     * Executes request, sending it to RLCP-server and recieving response to
     * return.
     *
     * @return {@code RlcpCalculateResponse} instance for this method returned
     * from RLCP-server
     * @throws UnknownHostException, IOException, BadRlcpResponseException, BadRlcpRequestException
     * @see RlcpCalculateResponse
     */
    @Override
    public RlcpCalculateResponse execute() throws RlcpException {
        return execute(0);
    }
}
