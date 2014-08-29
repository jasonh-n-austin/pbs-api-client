package com.paperbackswap.data;

import com.paperbackswap.exceptions.BookListBuilderException;
import org.json.JSONObject;

public interface BookListBuilder {
	public BookList construct(JSONObject source) throws BookListBuilderException;
}