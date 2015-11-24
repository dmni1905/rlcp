package rlcp.exception;

public class BadRlcpUrlException extends Exception {

    public BadRlcpUrlException(String string) {
        super(string);
    }

    public BadRlcpUrlException(Throwable thrwbl) {
        super(thrwbl);
    }

    public BadRlcpUrlException(String string, Throwable thrwbl) {
        super(string, thrwbl);
    }

    public BadRlcpUrlException() {
        super();
    }
}
