package com.paperbackswap.data;

import com.paperbackswap.Url.PbsUrlBuilder;
import com.paperbackswap.exceptions.*;

import java.util.List;

public interface BookResponseHandler {
    public BookResponseHandler construct(Object response, int statusCode)
            throws InvalidBooksResponseException, ResponseHasErrorsException, InvalidResponseException;
//    public JSONObject getBooksObject();
//    public JSONObject getSingleBookObject();
//    public JSONArray getBooksArray();
    public List<Book> getBookList() throws InvalidBooksResponseException, BookListBuilderException, InvalidBookException, InvalidResponseException;
    public List<BookRequest> getBookRequestList() throws InvalidBookException, InvalidBooksResponseException,
            ResponseHasErrorsException, BookListBuilderException, InvalidBookRequestException, InvalidResponseException;
    public PbsUrlBuilder getNextPage() throws InvalidResponseException;
    public RequestType getRequestType();
}
