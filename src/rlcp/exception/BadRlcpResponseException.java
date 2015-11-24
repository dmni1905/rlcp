package rlcp.exception;

public class BadRlcpResponseException extends Exception {

    public BadRlcpResponseException() {
        super();
    }

    public BadRlcpResponseException(String string) {
        super(string);
    }

    public BadRlcpResponseException(Throwable thrwbl) {
        super(thrwbl);
    }

    public BadRlcpResponseException(String string, Throwable thrwbl) {
        super(string, thrwbl);
    }
}
