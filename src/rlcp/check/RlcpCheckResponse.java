package rlcp.check;

import rlcp.RlcpResponse;
import rlcp.RlcpResponseHeader;
import rlcp.method.Check;
import rlcp.util.Util;

/**
 * RlcpResponse implementation for Check method.
 * @author Eugene Efimchick
 */
public class RlcpCheckResponse extends RlcpResponse {

    private RlcpResponseHeader header;
    private RlcpCheckResponseBody body;

    /**
     * Simple constructor.
     * @param header response header (should be not null)
     * @param body response body
     * @throws IllegalArgumentException if header is null
     */
    public RlcpCheckResponse(RlcpResponseHeader header, RlcpCheckResponseBody body) throws IllegalArgumentException {
        Util.checkNotNull("Header is null in RlcpCheckResponse constructor", header);
        this.header = header;
        this.body = body;
    }

    @Override
    public RlcpResponseHeader getHeader() {
        return header;
    }

    @Override
    public RlcpCheckResponseBody getBody() {
        return body;
    }

    @Override
    public Check getMethod() {
        return Check.getInstance();
    }
}
