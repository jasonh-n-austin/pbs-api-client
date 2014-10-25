package com.paperbackswap.Data;

import com.google.inject.Inject;
import com.paperbackswap.Test.GuiceJUnitRunner;
import com.paperbackswap.Test.TestDataLoader;
import com.paperbackswap.data.Book;
import com.paperbackswap.data.BookList;
import com.paperbackswap.data.BookListBuilder;
import com.paperbackswap.data.RequestType;
import com.paperbackswap.exceptions.*;
import com.paperbackswap.modules.BookModuleCache;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(GuiceJUnitRunner.class)
@GuiceJUnitRunner.GuiceModules({BookModuleCache.class})
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
        final String testBooksFile = "test_books_cache.json";
        testBooks = TestDataLoader.loadTestFileToJson(testBooksFile);
        assertNotNull(testBooks);

        BookList bookList = bookListBuilder.construct(testBooks, 200);
        assertNotNull(bookList);
        assertEquals(bookList.size(), 5);
        assertNotNull(bookList.get(0));
    }

    @Test
    public void builds_list_has_response_type() throws InvalidBookException, BookListBuilderException, InvalidBooksResponseException, ResponseHasErrorsException, IOException, InvalidResponseException {
        final String testBooksFile = "test_books_cache.json";
        testBooks = TestDataLoader.loadTestFileToJson(testBooksFile);
        assertNotNull(testBooks);

        BookList bookList = bookListBuilder.construct(testBooks, 200);
        assertNotNull(bookList);
        assertNotNull(bookList.getRequestType());
        assertEquals(RequestType.BOOK_SHELF, bookList.getRequestType());
    }

    @Test
    public void builds_paged_list() throws IOException, InvalidBookException, InvalidBooksResponseException, ResponseHasErrorsException, BookListBuilderException, InvalidResponseException {
        final String testBooksPagedFile = "test_books_cache.json";
        testBooksPaged = TestDataLoader.loadTestFileToJson(testBooksPagedFile);
        assertNotNull(testBooksPaged);

        BookList bookList = bookListBuilder.construct(testBooksPaged, 200);
        assertNotNull(bookList);
        assertEquals(bookList.size(), 5);
        assertNotNull(bookList.get(0));
        assertNotNull(bookList.getNextPage());
    }

    // Irrelevant with cached version, which produces an array consistently
//    @Test
//    public void builds_single_as_list() throws InvalidBookException, BookListBuilderException, InvalidBooksResponseException, ResponseHasErrorsException, IOException, InvalidResponseException {
//        final String testBookFile = "test_single_book.json";
//        testBook = TestDataLoader.loadTestFileToJson(testBookFile);
//        assertNotNull(testBook);
//
//        BookList bookList = bookListBuilder.construct(testBook);
//        assertNotNull(bookList);
//        assertEquals(bookList.size(), 1);
//        Book book = bookList.get(0);
//        assertNotNull(book);
//
//    }

    @Test(expected = InvalidResponseException.class)
    public void exception_on_bad_json_response() throws InvalidBookException, InvalidBooksResponseException, ResponseHasErrorsException, BookListBuilderException, InvalidResponseException {
        bookListBuilder.construct("", 200);
    }

    @Test(expected = ResponseHasErrorsException.class)
    public void response_has_error() throws InvalidBookException, InvalidBooksResponseException, ResponseHasErrorsException, BookListBuilderException, IOException, InvalidResponseException {
        final String testBadResponseFile = "test_book_error_cache.json";
        testBadResponse = TestDataLoader.loadTestFileToJson(testBadResponseFile);
        assertNotNull(testBadResponse);

        bookListBuilder.construct(testBadResponse, 404);
    }
}