package com.paperbackswap.exceptions;

public class InvalidMemberDataException extends Exception {
    public InvalidMemberDataException(String message) {
        super(message);
    }

    public InvalidMemberDataException() {
        super("MemberData response JSON is invalid");
    }
}
