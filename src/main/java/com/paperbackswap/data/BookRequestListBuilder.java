package com.paperbackswap.data;

import com.paperbackswap.exceptions.*;

import java.util.List;

public interface BookRequestListBuilder {
    public List<BookRequest> construct(Object response, int statusCode) throws InvalidBookException, BookListBuilderException, InvalidBooksResponseException, ResponseHasErrorsException, InvalidBookRequestException, InvalidResponseException;
}
