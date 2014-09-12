package com.paperbackswap.Data;

import com.google.inject.Inject;
import com.paperbackswap.Test.GuiceJUnitRunner;
import com.paperbackswap.Test.TestDataLoader;
import com.paperbackswap.data.Book;
import com.paperbackswap.data.BookRequest;
import com.paperbackswap.data.BookRequestDirection;
import com.paperbackswap.data.BookResponseHandler;
import com.paperbackswap.exceptions.*;
import com.paperbackswap.modules.BookModule;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(GuiceJUnitRunner.class)
@GuiceJUnitRunner.GuiceModules({BookModule.class})
public class BookResponseHandlerTests {
    BookResponseHandler responseHandler;

    @Inject
    public void set_builders(BookResponseHandler bookResponseHandler) {
        this.responseHandler = bookResponseHandler;
    }

    @Test
    public void loads_valid_response() throws IOException, InvalidBooksResponseException, ResponseHasErrorsException,
            InvalidBookException, BookListBuilderException, InvalidResponseException {
        final String testBooksFile = "test_books.json";
        JSONObject testBooks = TestDataLoader.loadTestFileToJson(testBooksFile);
        assertNotNull(testBooks);

        responseHandler.construct(testBooks);
        List<Book> books = responseHandler.getBookList();
        assertNotNull(books);
        assertEquals(7, books.size());
    }

    @Test
    public void loads_books_response() throws IOException, InvalidBooksResponseException, ResponseHasErrorsException,
            InvalidBookException, BookListBuilderException, InvalidResponseException {
        final String testBooksFile = "test_books.json";
        JSONObject testBooks = TestDataLoader.loadTestFileToJson(testBooksFile);
        assertNotNull(testBooks);

        responseHandler.construct(testBooks);
        List<Book> books = responseHandler.getBookList();
        assertNotNull(books);
        assertEquals(7, books.size());
    }

    @Test
    public void loads_single_book_request_outbound() throws IOException, InvalidBooksResponseException, ResponseHasErrorsException,
            InvalidBookException, BookListBuilderException, InvalidBookRequestException, InvalidResponseException {
        final String testBooksFile = "test_book_request_single_outbound.json";
        JSONObject testBooks = TestDataLoader.loadTestFileToJson(testBooksFile);
        assertNotNull(testBooks);

        responseHandler.construct(testBooks);
        List<BookRequest> requests = responseHandler.getBookRequestList();

        assertEquals(1, requests.size());
        for (BookRequest request : requests) {
            assertNotNull(request.getId());
            assertEquals(BookRequestDirection.Outgoing, request.getDirection());
        }
    }

    @Test
    public void loads_single_book_request_inbound() throws IOException, InvalidBooksResponseException, ResponseHasErrorsException,
            InvalidBookException, BookListBuilderException, InvalidBookRequestException, InvalidResponseException {
        final String testBooksFile = "test_book_request_single_inbound.json";
        JSONObject testBooks = TestDataLoader.loadTestFileToJson(testBooksFile);
        assertNotNull(testBooks);

        responseHandler.construct(testBooks);
        List<BookRequest> requests = responseHandler.getBookRequestList();

        assertEquals(1, requests.size());
        for (BookRequest request : requests) {
            assertNotNull(request.getId());
            assertEquals(BookRequestDirection.Incoming, request.getDirection());
        }
    }

    @Test
    public void loads_book_requests() throws IOException, InvalidBooksResponseException, ResponseHasErrorsException,
            InvalidBookException, BookListBuilderException, InvalidBookRequestException, InvalidResponseException {
        final String testBooksFile = "test_book_request_multiple.json";
        JSONObject testBooks = TestDataLoader.loadTestFileToJson(testBooksFile);
        assertNotNull(testBooks);

        responseHandler.construct(testBooks);
        List<BookRequest> requests = responseHandler.getBookRequestList();

        assertEquals(2, requests.size());
        for (BookRequest request : requests) {
            assertNotNull(request.getId());
        }
    }

    @Test
    public void loads_no_items() throws IOException, InvalidBooksResponseException, ResponseHasErrorsException,
            InvalidBookException, BookListBuilderException, InvalidBookRequestException, InvalidResponseException {
        final String testBooksFile = "test_requests_no_items.json";
        JSONObject testBooks = TestDataLoader.loadTestFileToJson(testBooksFile);
        assertNotNull(testBooks);

        responseHandler.construct(testBooks);
        List<BookRequest> requests = responseHandler.getBookRequestList();

        assertEquals(0, requests.size());
    }

}
