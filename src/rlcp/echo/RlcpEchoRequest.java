package rlcp.echo;

import rlcp.RlcpRequest;
import rlcp.RlcpRequestBody;
import rlcp.RlcpRequestHeader;
import rlcp.exception.BadRlcpHeaderException;
import rlcp.exception.BadRlcpRequestException;
import rlcp.exception.BadRlcpResponseException;
import rlcp.exception.RlcpException;
import rlcp.method.Echo;
import rlcp.method.RlcpMethod;

import java.io.IOException;
import java.net.UnknownHostException;

public class RlcpEchoRequest extends RlcpRequest {

    private RlcpRequestHeader header;
    private RlcpEchoRequestBody body;

    public RlcpEchoRequest(RlcpRequestHeader header, RlcpEchoRequestBody body){
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
    public RlcpMethod getMethod() {
        return Echo.getInstance();
    }

    @Override
    public RlcpEchoResponse execute(int timeout) throws RlcpException {
        return (RlcpEchoResponse) super.execute(timeout);
    }

    @Override
    public RlcpEchoResponse execute() throws RlcpException {
        return execute(0);
    }
}
