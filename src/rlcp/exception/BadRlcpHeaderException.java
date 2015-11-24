package rlcp.exception;

public class BadRlcpHeaderException extends Exception {

    public BadRlcpHeaderException() {
        super();
    }

    public BadRlcpHeaderException(String string) {
        super(string);
    }

    public BadRlcpHeaderException(Throwable thrwbl) {
        super(thrwbl);
    }

    public BadRlcpHeaderException(String string, Throwable thrwbl) {
        super(string, thrwbl);
    }
}
