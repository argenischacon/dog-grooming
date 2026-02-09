package com.argenischacon.service.exception;

public class DuplicateDniException extends BusinessException {

    public DuplicateDniException(String message) {
        super(message);
    }

    public DuplicateDniException(String message, Throwable cause) {
        super(message, cause);
    }

}
