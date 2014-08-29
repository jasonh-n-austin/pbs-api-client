package com.paperbackswap.data;

import org.json.JSONObject;

public interface BookBuilder {
	public Book construct(JSONObject source);
}