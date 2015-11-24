package rlcp.exception;

public class BadRlcpRequestException extends Exception {

    public BadRlcpRequestException() {
        super();
    }

    public BadRlcpRequestException(String string) {
        super(string);
    }

    public BadRlcpRequestException(Throwable thrwbl) {
        super(thrwbl);
    }

    public BadRlcpRequestException(String string, Throwable thrwbl) {
        super(string, thrwbl);
    }
    
}
