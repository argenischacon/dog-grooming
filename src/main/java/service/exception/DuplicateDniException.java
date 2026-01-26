package service.exception;

public class DuplicateDniException extends RuntimeException{

    public DuplicateDniException(String message) {
        super(message);
    }

    public DuplicateDniException(String message, Throwable cause) {
        super(message, cause);
    }

}
