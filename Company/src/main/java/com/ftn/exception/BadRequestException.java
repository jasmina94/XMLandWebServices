package com.ftn.exception;

/**
 * Created by JELENA on 19.6.2017.
 */
public class BadRequestException extends RuntimeException {
    public BadRequestException() {}

    public BadRequestException(String message) {
        super(message);
    }
}
