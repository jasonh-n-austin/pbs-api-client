package com.paperbackswap.data;

import com.paperbackswap.Url.PbsUrlBuilder;
import com.paperbackswap.exceptions.*;

import java.util.List;

public interface BookResponseHandler {
    public BookResponseHandler construct(Object response)
            throws InvalidBooksResponseException, ResponseHasErrorsException, InvalidResponseException;
//    public JSONObject getBooksObject();
//    public JSONObject getSingleBookObject();
//    public JSONArray getBooksArray();
    public List<Book> getBookList() throws InvalidBooksResponseException, BookListBuilderException, InvalidBookException;
    public List<BookRequest> getBookRequestList() throws InvalidBookException, InvalidBooksResponseException,
            ResponseHasErrorsException, BookListBuilderException, InvalidBookRequestException;
    public PbsUrlBuilder getNextPage();
}
