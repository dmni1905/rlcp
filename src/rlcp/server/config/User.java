package rlcp.server.config;

import rlcp.util.Util;

/**
 * Class for user data. Contains login and password. Unmodifiable.
 */
public class User {

    private String login;
    private String password;

    /**
     * Simple constructor.
     * @param login user Login. Must not be null.
     * @param password userPassword. Must not be null.
     * @throws IllegalArgumentException if login or password is null.
     */
    public User(String login, String password) throws IllegalArgumentException {
        Util.checkNotNull("login or password in User constructor was null", login, password);
        this.login = login;
        this.password = password;
    }

    /**
     * Returns user login.
     * @return user login
     */
    public String getLogin() {
        return login;
    }

    /**
     * Returns user password.
     * @return user password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Returns String representation as login + ":" + password.
     * @return String representation as login + ":" + password
     */
    @Override
    public String toString() {
        return login + ":" + password;
    }
    
    
}
