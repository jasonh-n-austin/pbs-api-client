package com.paperbackswap.exceptions;

public class InvalidBooksResponseException extends PbsException {
    private static final long serialVersionUID = 1979184620986079306L;

    public InvalidBooksResponseException(String message) {
        super(message);
    }
}