package com.paperbackswap.exceptions;

public class BooksResponseHasErrorsException extends Throwable {
    private static final long serialVersionUID = 1979184620986079304L;
    public BooksResponseHasErrorsException(String message) {
        super(message);
    }

}
