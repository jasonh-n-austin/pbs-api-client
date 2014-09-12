package com.paperbackswap.data;

import com.paperbackswap.exceptions.ResponseHasErrorsException;
import com.paperbackswap.exceptions.InvalidBooksResponseException;
import com.paperbackswap.exceptions.InvalidResponseException;

public interface ResponseHandler {
    public PbsResponse construct(Object response) throws ResponseHasErrorsException, InvalidResponseException;
}
