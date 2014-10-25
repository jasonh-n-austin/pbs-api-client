package com.paperbackswap.exceptions;

public class InvalidMemberDataException extends PbsException {
    public InvalidMemberDataException() {
        super("MemberData response JSON is invalid");
    }
}
