package com.paperbackswap.data;

import com.paperbackswap.exceptions.InvalidMemberDataException;
import com.paperbackswap.exceptions.InvalidResponseException;
import com.paperbackswap.exceptions.ResponseHasErrorsException;

public interface MemberDataBuilder {
    public MemberData construct(Object source, int statusCode) throws InvalidMemberDataException, InvalidResponseException, ResponseHasErrorsException;
}
