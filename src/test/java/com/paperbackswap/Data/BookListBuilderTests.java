package com.paperbackswap.Data;

import com.google.inject.Inject;
import com.paperbackswap.Test.GuiceJUnitRunner;
import com.paperbackswap.data.BookList;
import com.paperbackswap.data.BookListBuilder;
import com.paperbackswap.exceptions.BookListBuilderException;
import com.paperbackswap.exceptions.BooksResponseHasErrorsException;
import com.paperbackswap.exceptions.InvalidBookException;
import com.paperbackswap.exceptions.InvalidBooksResponseException;
import com.paperbackswap.modules.BookModule;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(GuiceJUnitRunner.class)
@GuiceJUnitRunner.GuiceModules({BookModule.class})
public class BookListBuilderTests {
    private BookListBuilder bookListBuilder;
    private JSONObject testBooks;
    private JSONObject testBook;
    private JSONObject testBadResponse;

    @Before
    public void setup() throws IOException {
        final String testBooksFile = "test_books.json";
        testBooks = TestDataLoader.loadTestFileToJson(testBooksFile);
        assertNotNull(testBooks);

        final String testBookFile = "test_single_book.json";
        testBook = TestDataLoader.loadTestFileToJson(testBookFile);
        assertNotNull(testBook);

        final String testBadResponseFile = "test_book_error.json";
        testBadResponse = TestDataLoader.loadTestFileToJson(testBadResponseFile);
        assertNotNull(testBadResponse);

    }

    @Inject
    public void set_builders(BookListBuilder bookListBuilder) {
        this.bookListBuilder = bookListBuilder;
    }

    @Test
    public void builds_list() throws InvalidBookException, BookListBuilderException, InvalidBooksResponseException, BooksResponseHasErrorsException {
        BookList bookList = bookListBuilder.construct(testBooks);
        assertNotNull(bookList);
        assertEquals(bookList.size(), 7);
        assertNotNull(bookList.get(0));
    }

    @Test
    public void builds_single_as_list() throws InvalidBookException, BookListBuilderException, InvalidBooksResponseException, BooksResponseHasErrorsException {
        BookList bookList = bookListBuilder.construct(testBook);
        assertNotNull(bookList);
        assertEquals(bookList.size(), 1);
        assertNotNull(bookList.get(0));
    }

    @Test(expected = InvalidBooksResponseException.class)
    public void exception_on_bad_response() throws InvalidBookException, BookListBuilderException, InvalidBooksResponseException, BooksResponseHasErrorsException {
        bookListBuilder.construct(new JSONObject("{}"));
    }

    @Test(expected = BooksResponseHasErrorsException.class)
    public void response_has_error() throws InvalidBookException, InvalidBooksResponseException, BooksResponseHasErrorsException, BookListBuilderException {
        bookListBuilder.construct(testBadResponse);
    }
}