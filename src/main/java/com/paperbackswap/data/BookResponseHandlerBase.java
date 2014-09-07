package com.paperbackswap.data;

import com.paperbackswap.exceptions.InvalidBooksResponseException;
import org.json.JSONException;
import org.json.JSONObject;

public class BookResponseHandlerBase {
    protected JSONObject responseObject;
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
}
