package com.paperbackswap.data;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.paperbackswap.exceptions.BookListBuilderException;
import com.paperbackswap.exceptions.BooksResponseHasErrorsException;
import com.paperbackswap.exceptions.InvalidBookException;
import com.paperbackswap.exceptions.InvalidBooksResponseException;
import com.paperbackswap.modules.BookModule;
import org.json.JSONObject;

@SuppressWarnings("UnusedDeclaration")
public class BooksFactory {
    private static Injector mInjector;

    static  {
        mInjector = Guice.createInjector(new BookModule());
    }

    public static Book getBook(JSONObject response) throws InvalidBookException, InvalidBooksResponseException, BookListBuilderException, BooksResponseHasErrorsException {
        return mInjector.getInstance(BookBuilder.class).construct(response);
    }

    public static BookList getBookList(JSONObject response) throws InvalidBookException, InvalidBooksResponseException, BooksResponseHasErrorsException, BookListBuilderException {
        return mInjector.getInstance(BookListBuilder.class).construct(response);
    }
}
