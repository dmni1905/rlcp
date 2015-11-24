package rlcp.calculate;

import java.io.IOException;
import java.net.UnknownHostException;
import rlcp.RlcpRequest;
import rlcp.RlcpRequestHeader;
import rlcp.exception.BadRlcpRequestException;
import rlcp.exception.BadRlcpResponseException;
import rlcp.method.Calculate;
import rlcp.util.Util;

/**
 * RlcpRequest implementation for Calculate method.
 * @author Eugene Efimchick
 */
public class RlcpCalculateRequest extends RlcpRequest{

    private RlcpRequestHeader header;
    private RlcpCalculateRequestBody body;

    /**
     * Simple constructor.
     * @param header request header (should be not null)
     * @param body request body (should be not null)
     * @throws IllegalArgumentException if header or body is null
     */
    public RlcpCalculateRequest(RlcpRequestHeader header, RlcpCalculateRequestBody body) throws IllegalArgumentException {
        Util.checkNotNull("Header or body is null in RlcpCalculateRequest constructor", header, body);
        this.header = header;
        this.body = body;
    }

    @Override
    public RlcpRequestHeader getHeader() {
        return header;
    }

    @Override
    public RlcpCalculateRequestBody getBody() {
        return body;
    }

    @Override
    public Calculate getMethod() {
        return Calculate.getInstance();
    }
    
    @Override
    public RlcpCalculateResponse execute(int timeout) throws UnknownHostException, IOException, BadRlcpResponseException, BadRlcpRequestException {
        return (RlcpCalculateResponse) super.execute(timeout);
    }
    
    @Override
    public RlcpCalculateResponse execute() throws UnknownHostException, IOException, BadRlcpResponseException, BadRlcpRequestException {
        return execute(0);
    }
}
