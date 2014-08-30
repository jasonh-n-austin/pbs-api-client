package com.paperbackswap.Data;

import com.paperbackswap.data.Book;
import com.paperbackswap.data.BookList;
import com.paperbackswap.data.BooksFactory;
import com.paperbackswap.exceptions.BookListBuilderException;
import com.paperbackswap.exceptions.BooksResponseHasErrorsException;
import com.paperbackswap.exceptions.InvalidBookException;
import com.paperbackswap.exceptions.InvalidBooksResponseException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class BooksFactoryTests {
    private JSONObject testBooks;
    private JSONObject testBook;

    @Before
    public void setup() throws IOException {
        final String testBooksFile = "test_books.json";
        final String testBookFile = "test_book.json";

        testBooks = TestDataLoader.loadTestFileToJson(testBooksFile);
        assertNotNull(testBooks);

        testBook = TestDataLoader.loadTestFileToJson(testBookFile);
        assertNotNull(testBook);

    }

    @Test
    public void creates_book_list() throws InvalidBookException, InvalidBooksResponseException, BooksResponseHasErrorsException, BookListBuilderException {
        BookList list = BooksFactory.getBookList(testBooks);
        assertEquals(7, list.size());
    }

    @Test
    public void creates_book_in_list() throws InvalidBookException, InvalidBooksResponseException, BooksResponseHasErrorsException, BookListBuilderException {
        Book book = BooksFactory.getBook(testBook);
        assertNotNull(book);
        assertEquals("Dinosaurs!", book.getTitle());
    }
}
