package com.paperbackswap.data;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.paperbackswap.exceptions.BookListBuilderException;
import com.paperbackswap.exceptions.BooksResponseHasErrorsException;
import com.paperbackswap.exceptions.InvalidBookException;
import com.paperbackswap.exceptions.InvalidBooksResponseException;
import com.paperbackswap.modules.BookModule;
import org.json.JSONObject;

/**
 * Builds a list of books from PBS API
 * Supports list RequestTypes, as well as detail/single book RequestType
 * @see com.paperbackswap.Url.PbsUrl
 */
public class BookListBuilderJson implements BookListBuilder {
	private static Injector mInjector;

    @Inject
    private BookListBuilderJson() {
        mInjector = Guice.createInjector(new BookModule());
    }

	public BookList construct(Object response)
            throws BookListBuilderException, InvalidBookException, InvalidBooksResponseException, BooksResponseHasErrorsException {
        if (!(response instanceof JSONObject)) {
            throw new BookListBuilderException("Object provided is not a JSONObject");
        }
        JSONObject responseJson = (JSONObject)response;
        BookResponseHandler handler = mInjector.getInstance(BookResponseHandler.class).construct(responseJson);

        return new BookList(handler.getBookList(), handler.getNextPage());
	}
}
