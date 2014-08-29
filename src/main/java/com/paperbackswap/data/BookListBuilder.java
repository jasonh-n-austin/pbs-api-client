package com.paperbackswap.data;

import com.paperbackswap.exceptions.BookListBuilderException;
import com.paperbackswap.exceptions.InvalidBookException;
import org.json.JSONObject;

public interface BookListBuilder {
	public BookList construct(JSONObject source) throws InvalidBookException, BookListBuilderException;
}