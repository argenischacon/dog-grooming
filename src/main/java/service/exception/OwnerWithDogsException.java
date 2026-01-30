package service.exception;

public class OwnerWithDogsException extends RuntimeException {
    public OwnerWithDogsException(String message) {
        super(message);
    }

    public OwnerWithDogsException(String message, Throwable cause) {
        super(message, cause);
    }
}
