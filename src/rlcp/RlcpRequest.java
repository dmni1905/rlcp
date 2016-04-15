package rlcp;

import java.io.IOException;
import java.io.Serializable;
import java.net.UnknownHostException;

import rlcp.exception.BadRlcpHeaderException;
import rlcp.exception.BadRlcpRequestException;
import rlcp.exception.BadRlcpResponseException;
import rlcp.exception.RlcpException;
import rlcp.method.RlcpMethod;
import rlcp.util.Util;

import static rlcp.util.Util.*;

/**
 * Interface for RLCP-request entity. Contains RlcpRequestHeader,
 * RlcpRequestBody implemenattion and getters for them. Also provides method to
 * get instance of RlcpMethod implementation of this request and {@code execute()}
 * method to send request to RLCP-server and receive Response.
 */
public abstract class RlcpRequest implements Serializable {

    /**
     * Returns request header.
     *
     * @return request header
     */
    public abstract RlcpRequestHeader getHeader();

    /**
     * Returns request body.
     *
     * @return request body
     */
    public abstract RlcpRequestBody getBody();

    /**
     * Returns RlcpMethod implementation of this request. Should NEVER return null.
     *
     * @return RlcpMethod implementation of this request. Should NEVER return null
     */
    public abstract RlcpMethod getMethod();

    /**
     * Executes request, sending it to RLCP-server and recieving response to
     * return.
     *
     * @return RlcpResponse implementation instance for this method returned
     * from RLCP-server
     * @throws UnknownHostException, IOException, BadRlcpResponseException, BadRlcpRequestException
     */
    public RlcpResponse execute() throws RlcpException {
        return execute(0);
    }

    /**
     * Executes request, sending it to RLCP-server and recieving response to
     * return.
     *
     * @param timeout timeout for connection in milliseconds
     * @return RlcpResponse implementation instance for this method returned
     * from RLCP-server
     * @throws UnknownHostException, IOException, BadRlcpResponseException, BadRlcpRequestException
     */
    public RlcpResponse execute(int timeout) throws RlcpException {
        return getMethod().getConnector().execute(this, timeout);
    }

    /**
     * Returns String representation that are to send to RLCP-server. Also this
     * String should be parsed by RlcpParser implementations.
     * <p>
     * Note: Headers and empty string are divided by "\r\n" sequence. Strings of
     * body divided by "\n" character. This was made to make flow.jar
     * backward-compatible with old Virtual labs.
     *
     * @return String representation of RlcpRequest.
     */
    @Override
    public String toString() {
        return new StringBuilder()
                .append(getHeader().toString())
                .append(winLineSeparator)
                .append(getBody().toString())
                .append(unixLineSeparator)
                .toString();
    }
}
