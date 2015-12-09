package rlcp.check;

import rlcp.RlcpResponse;
import rlcp.RlcpResponseHeader;
import rlcp.method.Check;
import rlcp.util.Util;

/**
 * RlcpResponse implementation for Check method.
 */
public class RlcpCheckResponse extends RlcpResponse {

    private RlcpResponseHeader header;
    private RlcpCheckResponseBody body;

    /**
     * Simple constructor.
     *
     * @param header response header (should be not null)
     * @param body   response body
     * @throws IllegalArgumentException if header is null
     */
    public RlcpCheckResponse(RlcpResponseHeader header, RlcpCheckResponseBody body) throws IllegalArgumentException {
        Util.checkNotNull("Header is null in RlcpCheckResponse constructor", header);
        this.header = header;
        this.body = body;
    }

    /**
     * return response header for Check method.
     *
     * @return response header for Check method.
     */
    @Override
    public RlcpResponseHeader getHeader() {
        return header;
    }

    /**
     * return response body for Check method.
     *
     * @return response body for Check method.
     */
    @Override
    public RlcpCheckResponseBody getBody() {
        return body;
    }

    /**
     * Returns RlcpMethod implementation of this Check-response. Should NEVER return null.
     *
     * @return RlcpMethod implementation of this Check-response. Should NEVER return null.
     */
    @Override
    public Check getMethod() {
        return Check.getInstance();
    }
}
