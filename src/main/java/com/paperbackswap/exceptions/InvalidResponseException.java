package com.paperbackswap.exceptions;

public class InvalidResponseException extends PbsException {
    private static final long serialVersionUID = 1979143620986079304L;

    public InvalidResponseException(String message) {
        super(message);
    }
}
