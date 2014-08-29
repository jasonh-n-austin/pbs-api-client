package com.paperbackswap.data;

import com.paperbackswap.exceptions.BookListBuilderException;
import com.paperbackswap.exceptions.BooksResponseHasErrorsException;
import com.paperbackswap.exceptions.InvalidBookException;
import com.paperbackswap.exceptions.InvalidBooksResponseException;
import org.json.JSONObject;

public interface BookListBuilder {
	public BookList construct(JSONObject source) throws InvalidBookException, BookListBuilderException, InvalidBooksResponseException, BooksResponseHasErrorsException;
}