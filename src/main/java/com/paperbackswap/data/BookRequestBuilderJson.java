package com.paperbackswap.data;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.paperbackswap.Url.Defaults;
import com.paperbackswap.exceptions.*;
import com.paperbackswap.modules.BookModule;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Builds a BookRequest object from PBS API v2 response
 * Object provided should be an individual request, not the array
 */
public class BookRequestBuilderJson extends JsonBuilderBase implements BookRequestBuilder {
    private static Injector mInjector;
    private final BookRequest bookRequest;
    private final DateFormat dateFormat = new SimpleDateFormat(Defaults.DATE_FORMAT.toString());

    @Inject
    private BookRequestBuilderJson(BookRequest bookRequest) {
        mInjector = Guice.createInjector(new BookModule());
        this.bookRequest = bookRequest;
    }

    public BookRequest construct(JSONObject response)
            throws InvalidBookRequestException, InvalidBooksResponseException,
            ResponseHasErrorsException, InvalidBookException, BookListBuilderException, InvalidResponseException {
        JSONObject responseJson = validateObject(response);
        if (responseJson != null) {
            try {
                bookRequest.setId(responseJson.getJSONObject("@attributes").optString("ID"));
            } catch (JSONException e) {
                throw new InvalidBookRequestException();
            }

            bookRequest.setDirection(responseJson.optString("Direction"));
            bookRequest.setDestination(responseJson.optString("Destination"));
            bookRequest.setStatus(responseJson.optString("Status"));
            String mailDeadline = responseJson.optString("MailDeadline"); // Only for outgoing
            if (!StringUtils.isEmpty(mailDeadline)) {
                try {
                    bookRequest.setMailDeadline(dateFormat.parse(mailDeadline));
                } catch (ParseException e) {
                    throw new InvalidBookRequestException();
                }
            }

            JSONObject books = responseJson.optJSONObject("Books");
            if (books != null) {
                JSONObject book = books.optJSONObject("Book");
                if (book != null) {
                    bookRequest.setBook(mInjector.getInstance(BookBuilder.class)
                            .construct(book));
                } else {
                    throw new InvalidBookRequestException();
                }
            } else {
                throw new InvalidBookRequestException();
            }

        } else {
            throw new InvalidBookRequestException();
        }
        return bookRequest;
    }

}
