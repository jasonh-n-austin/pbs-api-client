package com.paperbackswap.data;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.paperbackswap.Url.PbsUrlBuilder;
import com.paperbackswap.exceptions.*;
import com.paperbackswap.modules.BookModule;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Processes base response from PBS API
 */
public class BookResponseHandlerJson
        implements BookResponseHandler {
    private static Injector mInjector;
    private JSONObject responseObject;
    private ResponseHandler responseHandler;

    @Inject
    private BookResponseHandlerJson(ResponseHandler responseHandler) {
        mInjector = Guice.createInjector(new BookModule());
        this.responseHandler = responseHandler;
    }

    /**
     * Processes raw response into object
     * @param response raw response as JSONObject from PBS API v2
     * @return A new handler for processing response
     * @throws InvalidBooksResponseException
     * @throws com.paperbackswap.exceptions.ResponseHasErrorsException
     */
    public BookResponseHandler construct(Object response, int statusCode) throws InvalidBooksResponseException, ResponseHasErrorsException, InvalidResponseException {
        PbsResponse pbsResponse = responseHandler.construct(response, statusCode);
        this.responseObject = pbsResponse.getResponse();
        return this;
    }

    /**
     *
     * @return The "Books" node in the response object
     */
    protected JSONObject getBooksObject() throws InvalidBooksResponseException {
        try {
            return responseObject.getJSONObject("Books");
        } catch (JSONException e) {
            throw new InvalidBooksResponseException("Books not found in response");
        }
    }

    /**
     * Retrieves single book from response if no list was provided
     * @return The "Book" node in the response object
     */
    protected JSONObject getSingleBookObject() throws InvalidBooksResponseException {
        JSONObject books = getBooksObject();
        return books.optJSONObject("Book");
    }

    /**
     * Builds a list of books from response/books
     * @return The JSON array full of books. Could be null if this is a single book response.
     */
    JSONArray getBooksArray() throws InvalidBooksResponseException {
        JSONObject books = getBooksObject();
        JSONArray book = books.optJSONArray("Book");
        if (book != null && !(book instanceof JSONArray)) {
            // OK to be null here, this is partly testing for the existence of items
            return null;
        }
        return book;
    }

    /**
     * Retrieves URL for next list of results in paged set, if it exists
     * @return URL for next list of results
     * @see com.paperbackswap.Url.PbsUrlBuilder
     */
    public PbsUrlBuilder getNextPage() {
        PbsUrlBuilder next = null;

        if (responseObject.has("ResultsNextSet")) {
            next = PbsUrlBuilder.fromUrl(responseObject.getString("ResultsNextSet"));
        }

        return next;
    }

    /**
     * Builds a list of all books, or a list with one book if a single is present
     * @return List of Book objects
     * @throws InvalidBooksResponseException
     * @throws com.paperbackswap.exceptions.BookListBuilderException
     * @throws com.paperbackswap.exceptions.InvalidBookException
     * @see com.paperbackswap.data.Book
     */
    public List<Book> getBookList() throws InvalidBooksResponseException, BookListBuilderException, InvalidBookException, InvalidResponseException {
        List<Book> bookList = new ArrayList<Book>();
        JSONArray booksArr = getBooksArray();

        // Tries to get array of books, falls back to single
        if (booksArr != null) {
            // Process as an array
            for (int i = 0; i < booksArr.length(); i++) {
                JSONObject book = booksArr.optJSONObject(i);
                if (book != null) {
                    bookList.add(mInjector.getInstance(BookBuilder.class)
                            .construct(book));
                } else {
                    throw new InvalidBooksResponseException(
                            String.format("Book at position %d was invalid", i));
                }
            }
        } else {
            // Process as single book
            JSONObject book = getSingleBookObject();
            if (book != null) {
                bookList.add(mInjector.getInstance(BookBuilder.class)
                        .construct(book));
            }
        }
        return bookList;
    }

    /**
     * Tries for list of requests, falls back to single object
     * @return List of book requests
     * @throws InvalidBookException
     * @throws InvalidBooksResponseException
     * @throws com.paperbackswap.exceptions.ResponseHasErrorsException
     * @throws BookListBuilderException
     * @throws InvalidBookRequestException
     */
    public List<BookRequest> getBookRequestList() throws InvalidBookException, InvalidBooksResponseException, ResponseHasErrorsException, BookListBuilderException, InvalidBookRequestException, InvalidResponseException {
        List<BookRequest> bookRequestList = new ArrayList<BookRequest>();
        JSONArray list = responseObject.optJSONArray("Request");
        if (list != null) {
            for (int i = 0; i < list.length(); i++) {
                JSONObject bookRequest = list.getJSONObject(i);
                bookRequestList.add(
                        mInjector.getInstance(BookRequestBuilder.class)
                                .construct(bookRequest)
                );
            }
        } else {
            JSONObject bookRequest = responseObject.optJSONObject("Request");
            if (bookRequest != null) {
                bookRequestList.add(
                        mInjector.getInstance(BookRequestBuilder.class)
                                .construct(bookRequest)
                );
            }
        }
        return bookRequestList;
    }

    public RequestType getRequestType() {
        return responseHandler.getResponse().getRequestType();
    }
}
