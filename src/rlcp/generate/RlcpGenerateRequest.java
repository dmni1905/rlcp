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
 * @author Eugene Efimchick
 */
public class RlcpGenerateRequest extends RlcpRequest {

    private RlcpRequestHeader header;
    private RlcpGenerateRequestBody body;

    /**
     * Simple constructor.
     * @param header request header (should be not null)
     * @param body request body (should be not null)
     * @throws IllegalArgumentException if header or body is null
     */
    public RlcpGenerateRequest(RlcpRequestHeader header, RlcpGenerateRequestBody body) throws IllegalArgumentException {
        Util.checkNotNull("Header or body is null in RlcpGenerateRequest constructor", header, body);
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
    public Generate getMethod() {
        return Generate.getInstance();
    }
    
    @Override
    public RlcpGenerateResponse execute(int timeout) throws UnknownHostException, IOException, BadRlcpResponseException, BadRlcpRequestException {
        return (RlcpGenerateResponse) super.execute(timeout);
    }
    
    @Override
    public RlcpGenerateResponse execute() throws UnknownHostException, IOException, BadRlcpResponseException, BadRlcpRequestException {
        return execute(0);
    }
}
