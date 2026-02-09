package com.argenischacon.service.exception;

public class OwnerWithDogsException extends BusinessException {
    public OwnerWithDogsException(String message) {
        super(message);
    }

    public OwnerWithDogsException(String message, Throwable cause) {
        super(message, cause);
    }
}
