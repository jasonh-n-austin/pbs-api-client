package com.paperbackswap.data;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;

import com.paperbackswap.Url.PbsUrlBuilder;
import com.paperbackswap.exceptions.BookListBuilderException;
import com.paperbackswap.modules.BookModule;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Builds a list of books from PBS API
 * Supports list RequestTypes, as well as detail/single book RequestType
 * @see com.paperbackswap.Url.PbsUrl
 */
public class BookListBuilderJson implements BookListBuilder {
	private static Injector mInjector;

	@Inject
	public BookListBuilderJson() {
		mInjector = Guice.createInjector(new BookModule());
	}

	public BookList construct(JSONObject source)
			throws BookListBuilderException {
		return fromResponse(source);
	}

	/***
	 * Parses book responses into a list of JSON objects
	 * 
	 * @param response JSON object with PBS response including books
	 * @return List of books
	 * @throws BookListBuilderException
	 */
	private static BookList fromResponse(JSONObject response)
			throws BookListBuilderException {
		List<Book> bookList = new ArrayList<Book>();
		JSONArray booksArr = getBooksArray(response);
		if (booksArr != null) {
			for (int i = 0; i < booksArr.length(); i++) {
				JSONObject book = booksArr.optJSONObject(i);
				if (book != null) {
					bookList.add(mInjector.getInstance(BookBuilder.class)
							.construct(book));
				}
			}
		} else {
			JSONObject book = getBookObject(response);
			if (book != null) {
				bookList.add(mInjector.getInstance(BookBuilder.class)
						.construct(book));
			}
		}
		return new BookList(bookList, getNextPage(response));
	}

	private static JSONObject getBookObject(JSONObject response) throws BookListBuilderException {
		JSONObject books = getBooksObject(response);
		return books.optJSONObject("Book");
	}
	
	private static JSONArray getBooksArray(JSONObject response)
			throws BookListBuilderException {
		JSONArray bookArr = null;
		JSONObject books = getBooksObject(response);
		Object book = books.opt("Book");
        if (!(book instanceof JSONArray)) {
            return null;
        }
        bookArr = books.optJSONArray("Book");
        return bookArr;
	}

	private static JSONObject getBooksObject(JSONObject response)
			throws BookListBuilderException {
		if (response != null) {
			JSONObject resp = response.optJSONObject("Response");
			if (resp == null || resp.has("error")) {
				throw new BookListBuilderException(String.format("While retrieving books: %s",
                        resp != null ? resp.optString("error") : null));
			} else {
				return resp.optJSONObject("Books");
			}
		} else {
			throw new BookListBuilderException("Response object was null");
		}
	}

	private static PbsUrlBuilder getNextPage(JSONObject response) {
		PbsUrlBuilder next = null;
		if (response.has("Response")) {
			JSONObject resp = null;
			try {
				resp = response.getJSONObject("Response");
			} catch (JSONException e1) {
                //TODO: Deal with parsing exceptions
			}
			if (resp != null && resp.has("ResultsNextSet")) {
				try {
                    next = PbsUrlBuilder.fromUrl(resp.getString("ResultsNextSet"));
					// next = new URL(resp.getString("ResultsNextSet"));
				} catch (JSONException e) {
                    //TODO: Deal with parsing exceptions
				}
			}
		}
		return next;
	}
}
