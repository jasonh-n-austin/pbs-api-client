package com.paperbackswap.data;

import com.paperbackswap.exceptions.*;
import org.json.JSONObject;

public interface BookRequestBuilder {
    public BookRequest construct(JSONObject source) throws InvalidBookRequestException, InvalidBooksResponseException, BooksResponseHasErrorsException, InvalidBookException, BookListBuilderException;
}
