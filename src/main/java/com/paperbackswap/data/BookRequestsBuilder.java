package com.paperbackswap.data;

import com.paperbackswap.exceptions.*;

import java.util.List;

public interface BookRequestsBuilder {
    public List<BookRequest> construct(Object response) throws InvalidBookException, BookListBuilderException, InvalidBooksResponseException, BooksResponseHasErrorsException, InvalidBookRequestException;
}
