package com.paperbackswap.data;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.paperbackswap.exceptions.*;
import com.paperbackswap.modules.BookModule;
import org.json.JSONObject;

/**
 * Builds a list of books from PBS API
 * Supports list RequestTypes, as well as detail/single book RequestType
 * @see com.paperbackswap.Url.PbsUrl
 */
public class BookListBuilderJson extends JsonBuilderBase implements BookListBuilder {
	private static Injector mInjector;

    @Inject
    private BookListBuilderJson() {
        mInjector = Guice.createInjector(new BookModule());
    }

	public BookList construct(Object response, int statusCode)
            throws BookListBuilderException, InvalidBooksResponseException, InvalidResponseException, ResponseHasErrorsException, InvalidBookException {
        JSONObject responseJson = validateObject(response);
        BookResponseHandler handler = mInjector.getInstance(BookResponseHandler.class).construct(responseJson, statusCode);

        return new BookList(handler.getBookList(), handler.getNextPage(), handler.getRequestType());
	}
}
