package rlcp.exception;

public class NotImplementedMethodException extends RuntimeException {

    public NotImplementedMethodException() {
    }

    public NotImplementedMethodException(String message) {
        super(message);
    }

    public NotImplementedMethodException(Throwable cause) {
        super(cause);
    }

    public NotImplementedMethodException(String message, Throwable cause) {
        super(message, cause);
    }

    
}
