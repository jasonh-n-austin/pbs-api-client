package com.paperbackswap.data;

import com.paperbackswap.exceptions.BookListBuilderException;
import com.paperbackswap.exceptions.InvalidBookException;
import org.json.JSONObject;

public interface BookBuilder {
	public Book construct(JSONObject source) throws BookListBuilderException, InvalidBookException;
}