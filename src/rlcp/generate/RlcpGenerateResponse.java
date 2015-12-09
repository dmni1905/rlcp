package rlcp.generate;

import rlcp.RlcpResponse;
import rlcp.RlcpResponseHeader;
import rlcp.method.Generate;
import rlcp.util.Util;

/**
 * RlcpResponse implementation for Generate method.
 */
public class RlcpGenerateResponse extends RlcpResponse {

    private RlcpResponseHeader header;
    private RlcpGenerateResponseBody body;

    /**
     * Simple constructor.
     * @param header response header (should be not null)
     * @param body response body
     * @throws IllegalArgumentException if header is null
     */
    public RlcpGenerateResponse(RlcpResponseHeader header, RlcpGenerateResponseBody body) throws IllegalArgumentException {
        Util.checkNotNull("Header is null in RlcpGenerateResponse constructor", header);
        this.header = header;
        this.body = body;
    }

    /**
     * return response body for Generate method.
     *
     * @return response body for Generate method.
     */
    @Override
    public RlcpGenerateResponseBody getBody() {
        return body;
    }

    /**
     * return response header for Generate method.
     *
     * @return response header for Generate method.
     */
    @Override
    public RlcpResponseHeader getHeader() {
        return header;
    }

    /**
     * Returns RlcpMethod implementation of this Generate-response. Should NEVER return null.
     *
     * @return RlcpMethod implementation of this Generate-response. Should NEVER return null.
     */
    @Override
    public Generate getMethod() {
        return Generate.getInstance();
    }
}
