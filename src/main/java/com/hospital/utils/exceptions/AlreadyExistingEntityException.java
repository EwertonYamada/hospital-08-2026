package com.hospital.utils.exceptions;

public class AlreadyExistingEntityException extends RuntimeException {
    public AlreadyExistingEntityException(String message) {
        super(message);
    }
}
