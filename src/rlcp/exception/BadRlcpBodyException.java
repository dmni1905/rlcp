package rlcp.exception;

public class BadRlcpBodyException extends Exception {

    public BadRlcpBodyException() {
        super();
    }

    public BadRlcpBodyException(String string) {
        super(string);
    }

    public BadRlcpBodyException(Throwable thrwbl) {
        super(thrwbl);
    }

    public BadRlcpBodyException(String string, Throwable thrwbl) {
        super(string, thrwbl);
    }
    
}
