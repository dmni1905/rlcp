package rlcp;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import rlcp.echo.RlcpEchoRequest;
import rlcp.echo.RlcpEchoResponse;
import rlcp.calculate.RlcpCalculateRequest;
import rlcp.calculate.RlcpCalculateResponse;
import rlcp.check.RlcpCheckRequest;
import rlcp.check.RlcpCheckResponse;
import rlcp.exception.BadRlcpHeaderException;
import rlcp.exception.BadRlcpRequestException;
import rlcp.exception.BadRlcpResponseException;
import rlcp.exception.RlcpException;
import rlcp.generate.RlcpGenerateRequest;
import rlcp.generate.RlcpGenerateResponse;
import rlcp.util.Util;

/**
 * Class for RLCP networking. Specified by Request and Response classes that are
 * to be implementations of {@code RlcpRequest} and {@code RlcpResponse}
 * interfaces. Provides static getters for RlcpConnectors for each method.
 * Provides public {@code execute()} method, that establishes network
 * connection, sends request, receives Respose as raw String and parses it as
 * specified method response.
 */
public class RlcpConnector<Request extends RlcpRequest, Response extends RlcpResponse> {

    private RlcpConnector() {
    }

    /**
     * Returns new instance of RlcpConnector for any RLCP method.
     *
     * @return new instance of RlcpConnector for any RLCP method
     */
    public static RlcpConnector getGenericConnector() {
        return new RlcpConnector();
    }

    /**
     * Returns new instance of RlcpConnector for Check RLCP method.
     *
     * @return new instance of RlcpConnector for Check RLCP method
     */
    public static RlcpConnector<RlcpCheckRequest, RlcpCheckResponse> getCheckConnector() {
        return new RlcpConnector<RlcpCheckRequest, RlcpCheckResponse>();
    }

    /**
     * Returns new instance of RlcpConnector for Generate RLCP method.
     *
     * @return new instance of RlcpConnector for Generate RLCP method
     */
    public static RlcpConnector<RlcpGenerateRequest, RlcpGenerateResponse> getGenerateConnector() {
        return new RlcpConnector<RlcpGenerateRequest, RlcpGenerateResponse>();
    }

    public static RlcpConnector<RlcpEchoRequest, RlcpEchoResponse> getEchoConnector () {
        return new RlcpConnector<RlcpEchoRequest, RlcpEchoResponse>();
    }

    /**
     * Returns new instance of RlcpConnector for Calculate RLCP method.
     *
     * @return new instance of RlcpConnector for Calculate RLCP method
     */
    public static RlcpConnector<RlcpCalculateRequest, RlcpCalculateResponse> getCalculateConnector() {
        return new RlcpConnector<RlcpCalculateRequest, RlcpCalculateResponse>();
    }

    /**
     * Establishes network connection, sends request, receives Respose as raw
     * String and parses it as specified method response.
     *
     * @param request RlcpRequest to send to RLCP-server
     * @param timeout timeout for connection in milliseconds
     * @return RlcpResponse from RLCP-server
     * @throws UnknownHostException
     * @throws IOException
     * @throws BadRlcpResponseException
     */
    public Response execute(Request request, int timeout) {
        Socket socket = null;
        String rawResponse;
        try {
            String requestString = request.toString();
            socket = createSocket(request, timeout);
            send(requestString, socket);
            rawResponse = Util.readSocketInputUntilInputShutdown(socket);
        } catch (Exception exc) {
            throw new RlcpException(exc);
        } finally {
            try {
                socket.close();
            } catch (Exception ex) {
            }
        }
        return (Response) Rlcp.parseResponse(rawResponse, request.getMethod().getResponseClass());
    }

    private void send(String requestString, Socket socket) throws IOException {
        socket.getOutputStream().write(requestString.getBytes());
        socket.getOutputStream().flush();
        socket.shutdownOutput();
    }

    private Socket createSocket(Request request, int timeout) throws BadRlcpRequestException, IOException {
        String host = null;
        int port = 0;
        try {
            host = request.getHeader().getUrl().getHost();
            port = Integer.parseInt(request.getHeader().getUrl().getPort());
        } catch (Exception ex) {
            throw new BadRlcpRequestException("Error while reading host and port from request for socket in RlcpConnector. Host is " + host + " and port is " + port, ex);
        }

        try {
            Socket socket = new Socket();
            socket.connect(new InetSocketAddress(InetAddress.getByName(host), port), timeout);
            return socket;
        } catch (IOException ex) {
            throw new IOException("There was a problem to connect with RLCP-server", ex);
        }

    }
}
