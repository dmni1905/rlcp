package rlcp.calculate;

import rlcp.RlcpResponse;
import rlcp.RlcpResponseHeader;
import rlcp.method.Calculate;
import rlcp.util.Util;

/**
 * RlcpResponse implementation for Calculate method.
 * @author Eugene Efimchick
 */
public class RlcpCalculateResponse extends RlcpResponse {

    private RlcpResponseHeader header;
    private RlcpCalculateResponseBody body;

    /**
     * Simple constructor.
     * @param header response header (should be not null)
     * @param body response body
     * @throws IllegalArgumentException if header is null
     */
    public RlcpCalculateResponse(RlcpResponseHeader header, RlcpCalculateResponseBody body) throws IllegalArgumentException {
        Util.checkNotNull("Header is null in RlcpCalculateResponse constructor", header);
        this.header = header;
        this.body = body;
    }

    @Override
    public RlcpCalculateResponseBody getBody() {
        return body;
    }

    @Override
    public RlcpResponseHeader getHeader() {
        return header;
    }

    @Override
    public Calculate getMethod() {
        return Calculate.getInstance();
    }
}
