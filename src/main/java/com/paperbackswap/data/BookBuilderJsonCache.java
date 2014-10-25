package com.paperbackswap.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.inject.Inject;
import com.paperbackswap.exceptions.InvalidBookException;
import com.paperbackswap.exceptions.InvalidResponseException;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Date;
import java.util.Map;

public class BookBuilderJsonCache extends JsonBuilderBase implements BookBuilder {
    private final BookCoverBuilder bookCoverBuilder;

    @Inject
    public BookBuilderJsonCache(BookCoverBuilder bookCoverBuilder) {
        this.bookCoverBuilder = bookCoverBuilder;
    }

    @Override
    public Book construct(Object response) throws InvalidBookException, InvalidResponseException {
        JSONObject responseJson = validateObject(response);

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Date.class, new IsoDateSerializer()).create();
        Book book = gson.fromJson(responseJson.toString(), BookImpl.class);

        Map<Book.CoverImageType, String> bookCovers = bookCoverBuilder.construct(responseJson);
        book.setImage(bookCovers.get(Book.CoverImageType.LargeImage));
        book.setCoverImages(bookCovers);

        JSONArray links = responseJson.optJSONArray("_links");
        if (links != null) {
            for (int i = 0; i < links.length(); i++) {
                JSONObject link = links.optJSONObject(i);
                if (link != null) {
                    String rel = link.optString("rel");
                    if (!StringUtils.isEmpty(rel)) {
                        if (rel.equals("buy")) {
                            book.setBuyItNew(link.optString("href"));
                        }
                        if (rel.equals("details")) {
                            //TODO: Do we need a link to details?
                            // It's just a URL with ISBN
                        }
                    }
                }
            }
        }

        return book;
    }
}
