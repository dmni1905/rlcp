package rlcp.server.config;

import java.util.Collections;
import java.util.List;

import rlcp.server.logger.Logger;
import rlcp.util.Util;

import static rlcp.util.Constants.rlcpDefaultCheckUnitTimeLimitInSec;
import static rlcp.util.Constants.rlcpDefaultRequestFlowTimeLimitInSec;

/**
 * Class for RLCP-server configuration storing. Unmodifiable.
 */
public class Config {

    private final long requestFlowTimeLimit;
    private final long checkUnitTimeLimit;
    private List<User> users;
    private int port;

    /**
     * Simple constructor.
     *  @param port  server port
     * @param users list of server users. Must not be null.
     * @param requestFlowTimeLimit
     * @param checkUnitTimeLimit
     */
    public Config(int port, List<User> users, long requestFlowTimeLimit, long checkUnitTimeLimit) {
        Util.checkNotNull("UserList is null in Config constructor. It can be empty but never null", users);
        this.users = Collections.unmodifiableList(users);
        this.port = port;
        this.requestFlowTimeLimit = requestFlowTimeLimit;
        this.checkUnitTimeLimit = checkUnitTimeLimit;
    }

    /**
     * Returns list of users. Note: it is unmodifiable.
     *
     * @return list of users
     */
    public List<User> getUsers() {
        return users;
    }

    /**
     * Returns server port.
     *
     * @return server port
     */
    public int getPort() {
        return port;
    }

    /**
     * Returns {@code true} if login/password is not necessery (it is empty user list is set), {@code false} otherwise.
     *
     * @return {@code true} if login/password is not necessery (it is empty user list is set), {@code false} otherwise
     */
    public boolean isPublicAuthAllowed() {
        return users.isEmpty();
    }

    /**
     * Prints server's configuration in logger
     */
    public void printOut() {
        Logger.log("Configuration is:");
        Logger.log("Port: " + port);
        Logger.log("Users: " + users);
        Logger.log("Flow TimeLimit: " + requestFlowTimeLimit);
        Logger.log("CheckUnit TimeLimit: " + checkUnitTimeLimit);
    }

    /**
     * Returns time limits for request flow
     *
     * @return time limits for request flow
     */
    public long getRequestFlowTimeLimit() {
        return requestFlowTimeLimit;
    }

    /**
     * Returns time limits for check unit
     *
     * @return time limits for check unit
     */
    public long getCheckUnitTimeLimit() {
        return checkUnitTimeLimit;
    }

}
