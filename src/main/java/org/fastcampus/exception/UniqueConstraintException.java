package org.fastcampus.exception;

public class UniqueConstraintException extends RuntimeException {
    public UniqueConstraintException() {
    }

    public UniqueConstraintException(String message) {
        super(message);
    }
}
