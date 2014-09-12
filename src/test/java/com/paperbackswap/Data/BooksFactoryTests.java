package com.paperbackswap.Data;

import com.paperbackswap.Test.TestDataLoader;
import com.paperbackswap.data.Book;
import com.paperbackswap.data.BookList;
import com.paperbackswap.data.BooksFactory;
import com.paperbackswap.exceptions.*;
import org.json.JSONObject;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class BooksFactoryTests {

    @Test
    public void creates_book_list() throws InvalidBookException, InvalidBooksResponseException, ResponseHasErrorsException, BookListBuilderException, IOException, InvalidResponseException {
        final String testBooksFile = "test_books.json";
        JSONObject testBooks = TestDataLoader.loadTestFileToJson(testBooksFile);
        assertNotNull(testBooks);

        BookList list = BooksFactory.getBookList(testBooks);
        assertEquals(7, list.size());
    }

    @Test
    public void creates_book_in_list() throws InvalidBookException, InvalidBooksResponseException, ResponseHasErrorsException, BookListBuilderException, IOException {
        final String testBookFile = "test_book.json";
        JSONObject testBook = TestDataLoader.loadTestFileToJson(testBookFile);
        assertNotNull(testBook);

        Book book = BooksFactory.getBook(testBook);
        assertNotNull(book);
        assertEquals("Dinosaurs!", book.getTitle());
    }

    @Test
    public void creates_recently_posted() throws IOException, InvalidBookException, InvalidBooksResponseException, ResponseHasErrorsException, BookListBuilderException, InvalidResponseException {
        final String testBooksFile = "test_books_recently_posted.json";
        JSONObject testBooks = TestDataLoader.loadTestFileToJson(testBooksFile);
        assertNotNull(testBooks);

        BookList list = BooksFactory.getBookList(testBooks);
        assertEquals(4, list.size());
    }
}
