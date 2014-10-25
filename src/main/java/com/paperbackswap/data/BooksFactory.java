package com.paperbackswap.data;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.paperbackswap.exceptions.*;
import com.paperbackswap.modules.BookModuleCache;
import org.json.JSONObject;

import java.util.List;

@SuppressWarnings("UnusedDeclaration")
public class BooksFactory {
    private static Injector mInjector;

    static  {
        mInjector = Guice.createInjector(new BookModuleCache());
    }

    public static Book getBook(JSONObject response) throws InvalidBookException, InvalidBooksResponseException, BookListBuilderException, ResponseHasErrorsException, InvalidResponseException {
        return mInjector.getInstance(BookBuilder.class).construct(response);
    }

    public static BookList getBookList(JSONObject response, int statusCode) throws InvalidBookException, InvalidBooksResponseException, ResponseHasErrorsException, BookListBuilderException, InvalidResponseException {
        return mInjector.getInstance(BookListBuilder.class).construct(response, statusCode);
    }

    public static List<BookRequest> getBookRequestList(JSONObject response, int statusCode) throws InvalidBookException, InvalidBooksResponseException, ResponseHasErrorsException, BookListBuilderException, InvalidBookRequestException, InvalidResponseException {
        return mInjector.getInstance(BookRequestListBuilder.class).construct(response, statusCode);
    }
}
