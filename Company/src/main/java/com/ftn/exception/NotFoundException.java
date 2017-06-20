package com.ftn.exception;

/**
 * Created by JELENA on 19.6.2017.
 */
public class NotFoundException extends RuntimeException {

    public NotFoundException() {
    }

    public NotFoundException(String message) {
        super(message);
    }
}
