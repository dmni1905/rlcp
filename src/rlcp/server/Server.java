package rlcp.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import rlcp.server.config.Config;
import rlcp.server.config.ConfigParser;
import rlcp.server.logger.Logger;
import rlcp.server.processor.factory.ProcessorFactoryContainer;
import rlcp.util.Util;

/**
 * RLCP-server class.
 */
public class Server implements Runnable {

    private ProcessorFactoryContainer processorFactoryContainer;
    private String configPath;
    private boolean isRunning = false;
    private ServerSocket serverSocket;
    private Thread runningThread = null;

    /**
     * Simple constructor.
     *
     * @param configPath                configuration file path
     * @param processorFactoryContainer flow modules container (must not be {@code null})
     * @throws IllegalArgumentException if logicContainer is {@code null}
     */
    public Server(String configPath, ProcessorFactoryContainer processorFactoryContainer) throws IllegalArgumentException {
        Util.checkNotNull("logicConatainer is null in Server constructor", processorFactoryContainer);
        this.configPath = configPath;
        this.processorFactoryContainer = processorFactoryContainer;
    }

    /**
     * This method reads config(or uses default one if error occured) and starts
     * multi-threaded server.
     */
    public void run() {
        isRunning = true;
        Config config = ConfigParser.readConfigFromFileOrGetDefaults(configPath);
        config.printOut();
        try {
            serverSocket = new ServerSocket(config.getPort());
            while (isRunning()) {
                Socket socket = serverSocket.accept();

                new ServerThread(socket, config, processorFactoryContainer).start();
            }
        } catch (Exception e) {
            if (!isRunning()) {
                Logger.log("Server Stopped.");
                return;
            } else {
                isRunning = false;
                Logger.log(e);
            }
        }
    }

    public void stop() {
        isRunning = false;
        if (serverSocket != null) {
            try {
                serverSocket.close();
            } catch (IOException e) {
                Logger.log("Error closing server: " + e);
            }
        }

    }

    private boolean isRunning() {
        return isRunning;
    }
}
