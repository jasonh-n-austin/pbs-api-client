package com.paperbackswap.data;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.paperbackswap.Url.PbsUrlBuilder;
import com.paperbackswap.exceptions.*;
import com.paperbackswap.modules.BookModule;
import com.paperbackswap.modules.BookModuleCache;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Processes base response from PBS API
 */
public class BookResponseHandlerJsonCache
        implements BookResponseHandler {
    private static Injector mInjector;
    private JSONObject responseObject;
    private ResponseHandler responseHandler;
    private LinkProcessor linkProcessor;

    @Inject
    private BookResponseHandlerJsonCache(ResponseHandler responseHandler, LinkProcessor linkProcessor) {
        mInjector = Guice.createInjector(new BookModuleCache());
        this.responseHandler = responseHandler;
        this.linkProcessor = linkProcessor;
    }

    /**
     * Processes raw response into object
     * @param response raw response as JSONObject from PBS API v2
     * @return A new handler for processing response
     * @throws com.paperbackswap.exceptions.InvalidBooksResponseException
     * @throws com.paperbackswap.exceptions.ResponseHasErrorsException
     */
    public BookResponseHandler construct(Object response, int statusCode) throws InvalidBooksResponseException, ResponseHasErrorsException, InvalidResponseException {
        PbsResponse pbsResponse = responseHandler.construct(response, statusCode);
        this.responseObject = pbsResponse.getResponse();
        return this;
    }

    /**
     * Builds a list of books from response/books
     * @return The JSON array full of books. Could be null if this is a single book response.
     */
    JSONArray getBooksArray() throws InvalidBooksResponseException {
        JSONArray books = responseObject.optJSONArray("Books");
        if (books != null && !(books instanceof JSONArray)) {
            // OK to be null here, this is partly testing for the existence of items
            return null;
        }
        return books;
    }

    /**
     * Retrieves URL for next list of results in paged set, if it exists
     * @return URL for next list of results
     * @see com.paperbackswap.Url.PbsUrlBuilder
     */
    public PbsUrlBuilder getNextPage() throws InvalidResponseException {
        return linkProcessor.getLinkByRef(responseObject, "next");
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
        JSONArray list = responseObject.optJSONArray("Requests");
        if (list != null) {
            for (int i = 0; i < list.length(); i++) {
                JSONObject bookRequest = list.getJSONObject(i);
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

