package com.paperbackswap.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.paperbackswap.exceptions.*;
import org.json.JSONObject;

import java.util.Date;

public class BookRequestBuilderJsonCache extends JsonBuilderBase implements BookRequestBuilder {
    @Override
    public BookRequest construct(JSONObject response) throws InvalidBookRequestException, InvalidBooksResponseException, ResponseHasErrorsException, InvalidBookException, BookListBuilderException, InvalidResponseException {
        JSONObject responseJson = validateObject(response);

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Date.class, new IsoDateSerializer()).create();
        BookRequest bookRequest = gson.fromJson(responseJson.toString(), BookRequestImpl.class);
        return bookRequest;
    }
}
