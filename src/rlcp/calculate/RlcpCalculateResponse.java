package rlcp.calculate;

import rlcp.RlcpResponse;
import rlcp.RlcpResponseHeader;
import rlcp.method.Calculate;
import rlcp.util.Util;

/**
 * RlcpResponse implementation for Calculate method.
 */
public class RlcpCalculateResponse extends RlcpResponse {

    private RlcpResponseHeader header;
    private RlcpCalculateResponseBody body;

    /**
     * Simple constructor.
     *
     * @param header response header (should be not null)
     * @param body   response body
     * @throws IllegalArgumentException if header is null
     */
    public RlcpCalculateResponse(RlcpResponseHeader header, RlcpCalculateResponseBody body) throws IllegalArgumentException {
        Util.checkNotNull("Header is null in RlcpCalculateResponse constructor", header);
        this.header = header;
        this.body = body;
    }

    /**
     * return response body for Calculate method.
     *
     * @return response body for Calculate method.
     */
    @Override
    public RlcpCalculateResponseBody getBody() {
        return body;
    }

    /**
     * return response header for Calculate method.
     *
     * @return response header for Calculate method.
     */
    @Override
    public RlcpResponseHeader getHeader() {
        return header;
    }

    /**
     * Returns RlcpMethod implementation of this Calculate-response. Should NEVER return null.
     *
     * @return RlcpMethod implementation of this Calculate-response. Should NEVER return null.
     */
    @Override
    public Calculate getMethod() {
        return Calculate.getInstance();
    }
}
