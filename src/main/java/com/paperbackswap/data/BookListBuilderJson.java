package com.paperbackswap.data;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.paperbackswap.Url.PbsUrlBuilder;
import com.paperbackswap.exceptions.BookListBuilderException;
import com.paperbackswap.exceptions.BooksResponseHasErrorsException;
import com.paperbackswap.exceptions.InvalidBookException;
import com.paperbackswap.exceptions.InvalidBooksResponseException;
import com.paperbackswap.modules.BookModule;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Builds a list of books from PBS API
 * Supports list RequestTypes, as well as detail/single book RequestType
 * @see com.paperbackswap.Url.PbsUrl
 */
public class BookListBuilderJson implements BookListBuilder {
	protected static Injector mInjector;

	@Inject
	public BookListBuilderJson() {
		mInjector = Guice.createInjector(new BookModule());
	}

	public BookList construct(JSONObject source)
            throws BookListBuilderException, InvalidBookException, InvalidBooksResponseException, BooksResponseHasErrorsException {
		return fromResponse(source);
	}

	/***
	 * Parses book responses into a list of JSON objects
	 * 
	 * @param response JSON object with PBS response including books
	 * @return List of books
	 * @throws BookListBuilderException
	 */
	protected BookList fromResponse(JSONObject response)
            throws BookListBuilderException, InvalidBookException, InvalidBooksResponseException, BooksResponseHasErrorsException {

        JSONObject responseObject = response.optJSONObject("Response");
        if (responseObject == null) {
            throw new InvalidBooksResponseException("Response object was invalid");
        }
        if (responseObject.has("error")) {
            throw new BooksResponseHasErrorsException(String.format("Error:%s", responseObject.getString("error")));
        }

        return new BookList(getListOrSingle(responseObject), getNextPage(response));
	}

    /**
     * Expects "Response" object from API response object
     * Builds a list of all books, or a list with one book if a single is present
     * @param responseObject
     * @return
     * @throws InvalidBooksResponseException
     * @throws BookListBuilderException
     * @throws InvalidBookException
     */
    protected List<Book> getListOrSingle(JSONObject responseObject) throws InvalidBooksResponseException, BookListBuilderException, InvalidBookException {
        List<Book> bookList = new ArrayList<Book>();
        JSONArray booksArr = getBooksArray(responseObject);

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
            JSONObject book = getBookObject(responseObject);
            if (book != null) {
                bookList.add(mInjector.getInstance(BookBuilder.class)
                        .construct(book));
            } else {
                throw new InvalidBooksResponseException("No books were found in the response");
            }
        }
        return bookList;
    }


    /**
     * Expects "Response" object from API response object
     * Builds a list of books from response/books
     * @param responseObject
     * @return
     * @throws BookListBuilderException
     * @throws InvalidBooksResponseException
     */
    protected JSONArray getBooksArray(JSONObject responseObject)
            throws BookListBuilderException, InvalidBooksResponseException {
        JSONArray bookArr = null;
        JSONObject books = getBooksObject(responseObject);
        Object book = books.opt("Book");
        if (!(book instanceof JSONArray)) {
            throw new InvalidBooksResponseException("Book list was invalid");
        }
        bookArr = books.optJSONArray("Book");
        return bookArr;
    }

    /**
     * Expects "Response" object from API response object
     * @param response
     * @return
     * @throws BookListBuilderException
     * @throws InvalidBooksResponseException
     */
    protected JSONObject getBooksObject(JSONObject response)
            throws BookListBuilderException, InvalidBooksResponseException {
        return response.optJSONObject("Books");
    }

    /**
     * Expects "Response" object from API response object
     * Retrieves single book from response if no list was provided
     * @param responseObject
     * @return
     * @throws BookListBuilderException
     * @throws InvalidBooksResponseException
     */
	protected JSONObject getBookObject(JSONObject responseObject) throws BookListBuilderException, InvalidBooksResponseException {
		JSONObject books = getBooksObject(responseObject);
		return books.optJSONObject("Book");
	}

    /**
     * Expects "Response" object from API response object
     * Retrieves URL for next list of results in paged set, if it exists
     * @param responseObject
     * @return
     * @throws BookListBuilderException
     * @throws InvalidBooksResponseException
     * @throws BooksResponseHasErrorsException
     */
	protected PbsUrlBuilder getNextPage(JSONObject responseObject) throws BookListBuilderException, InvalidBooksResponseException, BooksResponseHasErrorsException {
		PbsUrlBuilder next = null;

        if (responseObject.has("ResultsNextSet")) {
            next = PbsUrlBuilder.fromUrl(responseObject.getString("ResultsNextSet"));
        }
    
        return next;
	}
}
