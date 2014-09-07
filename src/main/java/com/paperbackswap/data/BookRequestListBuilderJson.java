package com.paperbackswap.data;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.paperbackswap.exceptions.*;
import com.paperbackswap.modules.BookModule;

import java.util.List;

/**
 * Generates a list of requests from PBS v2 API RequestType=Requests
 */
public class BookRequestListBuilderJson implements BookRequestListBuilder {
    private static Injector mInjector;
    private BookResponseHandler responseHandler;

    @Inject
    public BookRequestListBuilderJson() {
        mInjector = Guice.createInjector(new BookModule());
    }

    @Override
    public List<BookRequest> construct(Object source)
            throws BookListBuilderException, InvalidBookException, InvalidBooksResponseException,
            BooksResponseHasErrorsException, InvalidBookRequestException {
        responseHandler = mInjector.getInstance(BookResponseHandler.class).construct(source);

        List<BookRequest> requests = responseHandler.getBookRequestList();
        return requests;
    }

}
