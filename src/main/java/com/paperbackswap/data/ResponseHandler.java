package com.paperbackswap.data;

import com.paperbackswap.exceptions.InvalidResponseException;
import com.paperbackswap.exceptions.ResponseHasErrorsException;

public interface ResponseHandler {
    public PbsResponse construct(Object response, int statusCode) throws ResponseHasErrorsException, InvalidResponseException;
    public PbsResponse getResponse();
}
