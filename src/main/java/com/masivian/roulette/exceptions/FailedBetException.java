package com.masivian.roulette.exceptions;

public class FailedBetException extends Exception {
    public FailedBetException(String message, Throwable cause) {
        super(message, cause);
    }

    public FailedBetException(String message) {
        super(message);
    }
}
