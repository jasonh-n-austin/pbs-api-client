package com.paperbackswap.data;

import com.google.inject.Inject;
import com.paperbackswap.exceptions.InvalidBookException;
import com.paperbackswap.exceptions.InvalidResponseException;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class BookBuilderJson extends JsonBuilderBase implements BookBuilder {
    private final Book book;
    private final BookCoverBuilder bookCoverBuilder;

    @Inject
	public BookBuilderJson(Book book, BookCoverBuilder bookCoverBuilder) {
        this.book = book;
        this.bookCoverBuilder = bookCoverBuilder;
	}

	public Book construct(Object response) throws InvalidBookException, InvalidResponseException {
        JSONObject responseJson = null;
        responseJson = validateObject(response);

		if (responseJson != null) {
            Map<Book.CoverImageType, String> bookCovers = bookCoverBuilder.construct(responseJson);
            book.setImage(bookCovers.get(Book.CoverImageType.LargeImage));
            book.setCoverImages(bookCovers);

            book.setStatus(responseJson.optString("Status"));
            book.setBinding(responseJson.optString("Binding"));
            book.setDescription(responseJson.optString("Description"));
            book.setIsbn10(responseJson.optString("ISBN-10"));
            book.setIsbn13(responseJson.optString("ISBN-13"));
            // Only here for alt. formats; defaults to ISBN13 on get
            book.setIsbn(responseJson.optString("ISBN"));
            book.setNumberOfPages(responseJson.optInt("NumberOfPages"));
            book.setPublished(getPublicationYear(responseJson));
            book.setPublisher(responseJson.optString("Publisher"));
            book.setTitle(responseJson.optString("Title"));
            book.setWishes(responseJson.optInt("Wishes"));
            book.setAuthors(getAuthorsList(responseJson));
            book.setRating((float) responseJson.optDouble("Rating"));
            book.setShowRatings(showRating(responseJson));
            book.setAvailable(responseJson.optBoolean("Available"));
            book.setQueuePosition(responseJson.optInt("WishQueuePosition"));
            book.setQueueTotal(responseJson.optInt("WishQueueTotal"));
            book.setBuyItNew(responseJson.optString("BuyItNew"));
        } else {
            throw new InvalidBookException();
        }
		return book;
	}

    List<String> getAuthorsList(JSONObject book) {
		JSONObject authors = book.optJSONObject("Authors");

        if (authors != null) {
            return getAuthors(authors);
        } else {
            return getAuthors(book);
        }
	}

    /**
     * In cases where "Author" is a comma delimited string of authors
     * @param author A comma delimited string of authors
     * @return A list of author strings
     */
    List<String> getDelimitedAuthors(String author) {
        List<String> list = new ArrayList<String>();
        list.addAll(Arrays.asList(author.split(",")));
        return list;
    }

    /**
     *
     * @param json Authors object
     * @return a list of author stings
     */
    List<String> getAuthors(JSONObject json) {
        List<String> list = new ArrayList<String>();

        Object authorsObj = json.opt("Author");
        if ((authorsObj != null) && (authorsObj instanceof String)) {
            list.addAll(getDelimitedAuthors(json.optString("Author")));
        } else {
            JSONArray authorsArr = json.optJSONArray("Author");
            if (authorsArr != null) {
                for (int i = 0; i < authorsArr.length(); i++) {
                    String author = authorsArr.optString(i);
                    if (author != null) {
                        list.add(author);
                    }
                }
            }
        }
        return list;
    }

	int getPublicationYear(JSONObject book) {
		int pubYear = 0;
		String pubDate = book.optString("PublicationDate");
		if (!StringUtils.isEmpty(pubDate) && pubDate.length() >= 4) {
			pubYear = Integer.parseInt(pubDate.substring(0, 4));
		}
		return pubYear;
	}

    boolean showRating(JSONObject book) {
		return book.optBoolean("ShowRatings");
	}
}
