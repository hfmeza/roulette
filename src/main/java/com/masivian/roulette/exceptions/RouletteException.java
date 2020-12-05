package com.masivian.roulette.exceptions;

public class RouletteException extends Exception {

    public RouletteException(String message, Throwable cause) {
        super(message, cause);
    }
    public RouletteException(String message) { super(message);}
}
