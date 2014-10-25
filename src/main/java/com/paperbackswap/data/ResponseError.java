package com.paperbackswap.data;

import com.paperbackswap.exceptions.InvalidResponseException;

public interface ResponseError {
    public ResponseError fromResponse(Object response) throws InvalidResponseException;
    public String getError();
    public String getMessage();
    public String getCode();
}
