package com.paperbackswap.Data;

import com.google.inject.Inject;
import com.paperbackswap.Test.GuiceJUnitRunner;
import com.paperbackswap.Test.TestDataLoader;
import com.paperbackswap.data.BookList;
import com.paperbackswap.data.BookListBuilder;
import com.paperbackswap.exceptions.*;
import com.paperbackswap.modules.BookModule;
import org.json.JSONObject;
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
    private JSONObject testBooksPaged;
    private JSONObject testBook;
    private JSONObject testBadResponse;

    @Inject
    public void set_builders(BookListBuilder bookListBuilder) {
        this.bookListBuilder = bookListBuilder;
    }

    @Test
    public void builds_list() throws InvalidBookException, BookListBuilderException, InvalidBooksResponseException, ResponseHasErrorsException, IOException, InvalidResponseException {
        final String testBooksFile = "test_books.json";
        testBooks = TestDataLoader.loadTestFileToJson(testBooksFile);
        assertNotNull(testBooks);

        BookList bookList = bookListBuilder.construct(testBooks);
        assertNotNull(bookList);
        assertEquals(bookList.size(), 7);
        assertNotNull(bookList.get(0));
    }

    @Test
    public void builds_paged_list() throws IOException, InvalidBookException, InvalidBooksResponseException, ResponseHasErrorsException, BookListBuilderException, InvalidResponseException {
        final String testBooksPagedFile = "test_books_paged.json";
        testBooksPaged = TestDataLoader.loadTestFileToJson(testBooksPagedFile);
        assertNotNull(testBooksPaged);

        BookList bookList = bookListBuilder.construct(testBooksPaged);
        assertNotNull(bookList);
        assertEquals(bookList.size(), 50);
        assertNotNull(bookList.get(0));
        assertNotNull(bookList.getNextPage());
    }

    @Test
    public void builds_single_as_list() throws InvalidBookException, BookListBuilderException, InvalidBooksResponseException, ResponseHasErrorsException, IOException, InvalidResponseException {
        final String testBookFile = "test_single_book.json";
        testBook = TestDataLoader.loadTestFileToJson(testBookFile);
        assertNotNull(testBook);

        BookList bookList = bookListBuilder.construct(testBook);
        assertNotNull(bookList);
        assertEquals(bookList.size(), 1);
        assertNotNull(bookList.get(0));
    }

    @Test(expected = BookListBuilderException.class)
    public void exception_on_bad_json_response() throws InvalidBookException, InvalidBooksResponseException, ResponseHasErrorsException, BookListBuilderException, InvalidResponseException {
        bookListBuilder.construct("");
    }

    @Test(expected = ResponseHasErrorsException.class)
    public void response_has_error() throws InvalidBookException, InvalidBooksResponseException, ResponseHasErrorsException, BookListBuilderException, IOException, InvalidResponseException {
        final String testBadResponseFile = "test_book_error.json";
        testBadResponse = TestDataLoader.loadTestFileToJson(testBadResponseFile);
        assertNotNull(testBadResponse);

        bookListBuilder.construct(testBadResponse);
    }
}