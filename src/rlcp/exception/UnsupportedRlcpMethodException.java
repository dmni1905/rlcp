package rlcp.exception;

public class UnsupportedRlcpMethodException extends RuntimeException {

    public UnsupportedRlcpMethodException() {
    }

    public UnsupportedRlcpMethodException(String message) {
        super(message);
    }

    public UnsupportedRlcpMethodException(Throwable cause) {
        super(cause);
    }

    public UnsupportedRlcpMethodException(String message, Throwable cause) {
        super(message, cause);
    }
}
