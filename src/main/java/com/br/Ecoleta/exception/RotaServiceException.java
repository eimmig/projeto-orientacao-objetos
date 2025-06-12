package com.br.ecoleta.exception;

public class RotaServiceException extends RuntimeException {
    public RotaServiceException(String message) {
        super(message);
    }

    public RotaServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
