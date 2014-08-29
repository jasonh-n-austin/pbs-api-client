package com.paperbackswap.exceptions;

public class InvalidBookResponseException extends Exception {
    private static final long serialVersionUID = 1979184620986079306L;

    public InvalidBookResponseException(String message) {
        super(message);
    }
}