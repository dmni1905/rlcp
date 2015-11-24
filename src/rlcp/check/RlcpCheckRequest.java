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
 * @author Eugene Efimchick
 */
public class RlcpCheckRequest extends RlcpRequest{

    private RlcpRequestHeader header;
    private RlcpCheckRequestBody body;

    /**
     * Simple constructor.
     * @param header request header (should be not null)
     * @param body request body (should be not null)
     * @throws IllegalArgumentException if header or body is null
     */
    public RlcpCheckRequest(RlcpRequestHeader header, RlcpCheckRequestBody body) throws IllegalArgumentException {
        Util.checkNotNull("Header or body is null in RlcpCheckRequest constructor", header, body);
        this.header = header;
        this.body = body;
    }
    
    @Override
    public RlcpRequestHeader getHeader() {
        return header;
    }

    @Override
    public RlcpRequestBody getBody() {
        return body;
    }

    @Override
    public Check getMethod() {
        return Check.getInstance();
    }

    @Override
    public RlcpCheckResponse execute(int timeout) throws UnknownHostException, IOException, BadRlcpResponseException, BadRlcpRequestException {
        return (RlcpCheckResponse) super.execute(timeout);
    }
    
    @Override
    public RlcpCheckResponse execute() throws UnknownHostException, IOException, BadRlcpResponseException, BadRlcpRequestException {
        return execute(0);
    }
    
}
