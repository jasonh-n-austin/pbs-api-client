package com.paperbackswap.data;

import com.google.inject.Inject;
import com.paperbackswap.exceptions.*;
import org.json.JSONObject;

/**
 * Builds a list of books from PBS-cache API
 * Supports list RequestTypes, as well as detail/single book RequestType
 * @see com.paperbackswap.Url.PbsUrl
 */
public class BookListBuilderJsonCache extends JsonBuilderBase implements BookListBuilder {
    private BookResponseHandler mBookResponseHandler;

    @Inject
    private BookListBuilderJsonCache(BookResponseHandler bookResponseHandler) {
        mBookResponseHandler = bookResponseHandler;
    }

    public BookList construct(Object response, int statusCode)
            throws BookListBuilderException, InvalidBooksResponseException, InvalidResponseException, ResponseHasErrorsException, InvalidBookException {
        JSONObject responseJson = validateObject(response);
        BookResponseHandler handler = mBookResponseHandler.construct(responseJson, statusCode);

        return new BookList(handler.getBookList(), handler.getNextPage(), handler.getRequestType());
    }
}
