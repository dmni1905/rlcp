package rlcp.server;

import java.io.IOException;
import java.net.Socket;
import rlcp.*;
import rlcp.check.RlcpCheckResponse;
import rlcp.exception.NotImplementedMethodException;
import rlcp.exception.UnsupportedRlcpMethodException;
import rlcp.server.config.Config;
import rlcp.server.config.User;
import rlcp.server.logger.Logger;
import rlcp.server.processor.factory.ProcessorFactoryContainer;
import rlcp.util.Util;

/**
 * Class for single request processing thread.
 *
 * @author Eugene Efimchick
 */
public class ServerThread extends Thread {

    private Socket socket;
    private Config config;
    private ProcessorFactoryContainer processorFactoryContainer;

    /**
     * Simple constructor. All params must not be null.
     *
     * @param socket socket instance
     * @param config configuration
     * @param processorFactoryContainer logicContainer
     *
     * @throws IllegalArgumentException if any parameter is null
     */
    public ServerThread(Socket socket, Config config, ProcessorFactoryContainer processorFactoryContainer) throws IllegalArgumentException {
        Util.checkNotNull("Socket, config or logicContainer is null in ServerThread constructor", config, processorFactoryContainer, socket);
        this.socket = socket;
        this.config = config;
        this.processorFactoryContainer = processorFactoryContainer;
    }

    /**
     * Request processing method.
     */
    @Override
    public void run() {

        Logger.log("Request...");

        String serverInput = tryToReadServerInput();
        if (serverInputFailed(serverInput)) {
            handleError(RlcpCode.IncorrectRequest, "input failed");
            return;
        }

        RlcpRequest request = null;
        try {
            request = parseRequest(serverInput, request);
        } catch (UnsupportedRlcpMethodException ex) {
            Logger.log(ex);
            handleError(RlcpCode.UnsupportedMethod, "bad request");
            return;
        } catch (NotImplementedMethodException ex) {
            Logger.log(ex);
            handleError(RlcpCode.NotImplementedMethod, "bad request");
            return;
        } catch (Exception ex) {
            Logger.log(ex);
            handleError(RlcpCode.IncorrectRequest, "bad request");
            return;
        }

        if (!authSuccessful(request)) {
            handleError(RlcpCode.AuthFailed, "authorization failed");
        } else if (!executeRlcpRequest(request)) {
            handleError(RlcpCode.Unavailable, "error processing " + request.getMethod() + " request");
        } else {
            socketClose();
            Logger.log("Request is complete");
        }
    }

    private RlcpRequest parseRequest(String serverInput, RlcpRequest request) throws Exception {
        String methodSupposed = getRawMethod(serverInput);
        ServerMethod method = ServerMethod.lookForMethod(methodSupposed);
        request = method.parseRequest(serverInput);
        return request;
    }

    private boolean serverInputFailed(String serverInput) {
        return serverInput == null || serverInput.isEmpty();
    }

    private String tryToReadServerInput() {
        Logger.log("Input incoming...");
        String input = null;
        try {
            input = Util.readSocketInputUntilInputShutdown(socket);
            Logger.log("Input was read");
        } catch (IOException ex) {
            Logger.log(ex);
        }

        Logger.log(Util.nativeLineSeparator + input);
        return input;

    }

    private boolean executeRlcpRequest(RlcpRequest request) {
        boolean isSuccessful = false;
        try {
            ServerMethod method = ServerMethod.lookForMethod(request.getHeader().getMethod());
            executeMethodForRequest(method, request);
            isSuccessful = true;
        } catch (Exception ex) {
            Logger.log(ex);
        }
        return isSuccessful;
    }

    private boolean authSuccessful(RlcpRequest request) {
        if (config.isPublicAuthAllowed()) {
            return true;
        }
        RlcpUrl url = request.getHeader().getUrl();
        return authorize(url.getLogin(), url.getPassword());
    }

    private boolean authorize(String urlLogin, String urlPassword) {
        for (User user : config.getUsers()) {
            if (singleAuthSuccess(user, urlLogin, urlPassword)) {
                Logger.log("auth success. Login:" + urlLogin);
                return true;
            }
        }
        return false;
    }

    private boolean singleAuthSuccess(User user, String login, String password) {
        return user.getLogin().equals(login) && user.getPassword().equals(password);
    }

    private void executeMethodForRequest(ServerMethod method, RlcpRequest rlcpRequest) throws Exception {
        RlcpResponse rlcpResponse = method.execute(rlcpRequest, processorFactoryContainer);
        sendResponse(rlcpResponse);
        Logger.log("Request method " + method + " is executed");
    }

    private void sendResponse(RlcpResponse response) throws IOException {
        Logger.log("Output sending...");
        Logger.log(Util.nativeLineSeparator + response.toString());
        socket.getOutputStream().write(response.toString().getBytes());
    }

    private void handleError(RlcpCode code, String message) {
        try {
            RlcpResponseHeader responseHeader = RlcpResponseHeader.createFailedHeader(code.getCode(), code.getMessage() + " - " + message);
            RlcpResponse response = new RlcpCheckResponse(responseHeader, null);
            sendResponse(response);
        } catch (IOException ex) {
            Logger.log(ex);
        }
        Logger.log(message);
        Logger.log("Request failed");
        socketClose();
    }

    private void socketClose() {
        try {
            socket.getOutputStream().flush();
        } catch (Exception ex) {
        }
        try {
            socket.getOutputStream().close();
        } catch (Exception ex) {
        }
        try {
            socket.getInputStream().close();
        } catch (Exception ex) {
        }
        try {
            socket.close();
        } catch (Exception ex) {
        }
    }

    private static String getRawMethod(String serverInput) {
        return serverInput.substring(0, serverInput.indexOf(Util.nativeLineSeparator)).toUpperCase();
    }
}
