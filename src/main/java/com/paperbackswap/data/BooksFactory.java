package com.paperbackswap.data;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.paperbackswap.exceptions.*;
import com.paperbackswap.modules.BookModule;
import org.json.JSONObject;

import java.util.List;

@SuppressWarnings("UnusedDeclaration")
public class BooksFactory {
    private static Injector mInjector;

    static  {
        mInjector = Guice.createInjector(new BookModule());
    }

    public static Book getBook(JSONObject response) throws InvalidBookException, InvalidBooksResponseException, BookListBuilderException, ResponseHasErrorsException {
        return mInjector.getInstance(BookBuilder.class).construct(response);
    }

    public static BookList getBookList(JSONObject response) throws InvalidBookException, InvalidBooksResponseException, ResponseHasErrorsException, BookListBuilderException, InvalidResponseException {
        return mInjector.getInstance(BookListBuilder.class).construct(response);
    }

    public static List<BookRequest> getBookRequestList(JSONObject response) throws InvalidBookException, InvalidBooksResponseException, ResponseHasErrorsException, BookListBuilderException, InvalidBookRequestException, InvalidResponseException {
        return mInjector.getInstance(BookRequestListBuilder.class).construct(response);
    }
}
