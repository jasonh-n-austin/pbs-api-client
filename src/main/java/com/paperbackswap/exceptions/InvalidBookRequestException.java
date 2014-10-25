package com.paperbackswap.exceptions;

public class InvalidBookRequestException extends PbsException {
    private static final long serialVersionUID = 1979184680986079307L;
    public InvalidBookRequestException() {
        super("Book request object is invalid");
    }

    public InvalidBookRequestException(String message) {
        super(message);
    }
}
