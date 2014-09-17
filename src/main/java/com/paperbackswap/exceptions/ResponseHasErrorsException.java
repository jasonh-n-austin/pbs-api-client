package com.paperbackswap.exceptions;

public class ResponseHasErrorsException extends Exception {
    private static final long serialVersionUID = 1979184620986079304L;
    public ResponseHasErrorsException(String message) {
        super(message);
    }

}
