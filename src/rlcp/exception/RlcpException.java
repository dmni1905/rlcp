package rlcp.exception;

public class RlcpException extends RuntimeException {

    public RlcpException() {
    }

    public RlcpException(String message) {
        super(message);
    }

    public RlcpException(Throwable cause) {
        super(cause);
    }

    public RlcpException(String message, Throwable cause) {
        super(message, cause);
    }

    
}
