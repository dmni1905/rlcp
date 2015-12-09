package rlcp;

import java.io.Serializable;
import java.util.StringTokenizer;

import rlcp.exception.BadRlcpUrlException;
import rlcp.util.Util;

/**
 * Class for RLCP URL entity. Provides getters and parser methods.
 */
public class RlcpUrl implements Serializable {

    private static String rlcpUrlDelimiters = ":@";

    private static boolean logPassHostPortExpected(StringTokenizer tokenizer) {
        return tokenizer.countTokens() == 4;
    }

    private static boolean hostPortExpected(StringTokenizer tokenizer) {
        return tokenizer.countTokens() == 2;
    }

    private String host, port, login, password;

    /**
     * Simple constructor. Beware of IllegalArgumentException.
     *
     * @param host     host (not null)
     * @param port     port (not null)
     * @param login    RLCP-server login
     * @param password RLCP-server password
     * @throws IllegalArgumentException if host or port is null
     */
    public RlcpUrl(String host, String port, String login, String password) throws IllegalArgumentException {
        Util.checkStringNotNullNotEmpty(host);
        Util.checkStringNotNullNotEmpty(port);
        this.host = host;
        this.port = port;
        this.login = login;
        this.password = password;
    }

    /**
     * Constructor with null login and password.
     *
     * @param host host (not null)
     * @param port port (not null)
     * @throws IllegalArgumentException if host or port is null
     */
    public RlcpUrl(String host, String port) throws IllegalArgumentException {
        this(host, port, null, null);
    }

    /**
     * Returns parsed from raw String RlcpUrl instance
     *
     * @param rlcpUrlString raw String RlcpUrl representation
     * @return RlcpUrl instance
     * @throws BadRlcpUrlException
     */
    public static RlcpUrl parse(String rlcpUrlString) throws BadRlcpUrlException {
        Util.checkStringNotNullNotEmpty(rlcpUrlString);

        String operable = rlcpUrlString;
        if (operable.indexOf("rlcp://") != 0) {
            throw new BadRlcpUrlException(rlcpUrlString + " is not rlcpUrl");
        }
        operable = rlcpUrlString.substring(("rlcp://").length());

        String login = null;
        String password = null;
        String host = null;
        String port = null;

        StringTokenizer tokenizer = new StringTokenizer(operable, rlcpUrlDelimiters);

        if (logPassHostPortExpected(tokenizer)) {
            login = tokenizer.nextToken();
            password = tokenizer.nextToken();
            host = tokenizer.nextToken();
            port = tokenizer.nextToken();
        } else if (hostPortExpected(tokenizer)) {
            host = tokenizer.nextToken();
            port = tokenizer.nextToken();
        } else {
            throw new BadRlcpUrlException(rlcpUrlString + " has wrong array of elements");
        }

        return new RlcpUrl(host, port, login, password);
    }

    /**
     * Returns String representation of RlcpUrl instance.
     *
     * @return String representation of RlcpUrl instance
     */
    @Override
    public String toString() {
        if (login != null && password != null) {
            return "rlcp://" + login + ":" + password + "@" + host + ":" + port;
        } else {
            return "rlcp://" + host + ":" + port;
        }

    }

    /**
     * Returns host.
     *
     * @return host
     */
    public String getHost() {
        return host;
    }

    /**
     * Returns login.
     *
     * @return login
     */
    public String getLogin() {
        return login;
    }

    /**
     * Returns password.
     *
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Returns port.
     *
     * @return port
     */
    public String getPort() {
        return port;
    }
}