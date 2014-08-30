package com.paperbackswap.data;

import com.google.inject.Inject;
import com.paperbackswap.exceptions.InvalidBookException;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookBuilderJson implements BookBuilder {
    private final Book book;

    @Inject
	public BookBuilderJson(Book book) {
        this.book = book;
	}

	public Book construct(JSONObject source) throws InvalidBookException {
		if (source != null) {
            book.setStatus(source.optString("Status"));
            book.setBinding(source.optString("Binding"));
            book.setDescription(source.optString("Description"));
            book.setImage(getCoverImages(source).get(Book.CoverImageType.LargeImage));
            book.setIsbn10(source.optString("ISBN-10"));
            book.setIsbn13(source.optString("ISBN-13"));
            book.setNumberOfPages(source.optInt("NumberOfPages"));
            book.setPublished(getPublicationYear(source));
            book.setPublisher(source.optString("Publisher"));
            book.setTitle(source.optString("Title"));
            book.setWishes(source.optInt("Wishes"));
            book.setAuthors(getAuthors(source));
            book.setCoverImages(getCoverImages(source));
            book.setRating((float) source.optDouble("Rating"));
            book.setShowRatings(showRating(source));
            book.setAvailable(source.optBoolean("Available"));
            book.setQueuePosition(source.optInt("WishQueuePosition"));
            book.setQueueTotal(source.optInt("WishQueueTotal"));
        } else {
            throw new InvalidBookException();
        }
		return book;
	}

    List<String> getAuthors(JSONObject book) {
		List<String> list = new ArrayList<String>();
		JSONObject authors = book.optJSONObject("Authors");

        if (authors != null) {
            Object authorsObj = authors.opt("Author");
            if ((authorsObj != null) && (authorsObj instanceof String)) {
                list.add(authors.optString("Author"));
            } else {
                JSONArray authorsArr = authors.optJSONArray("Author");
                if (authorsArr != null) {
                    for (int i = 0; i < authorsArr.length(); i++) {
                        String author = authorsArr.optString(i);
                        if (author != null) {
                            list.add(author);
                        }
                    }
                }
            }
        }
		return list;
	}

    Map<Book.CoverImageType, String> getCoverImages(JSONObject book) {
		Map<Book.CoverImageType, String> covers = new HashMap<Book.CoverImageType, String>();

		JSONObject coverImages = book.optJSONObject("CoverImages");
		if (coverImages != null) {
			covers.put(Book.CoverImageType.SmallImage,
					coverImages.optString("SmallImage"));
			covers.put(Book.CoverImageType.MediumImage,
					coverImages.optString("MediumImage"));
			covers.put(Book.CoverImageType.LargeImage,
					getLargeImageFromMedium(coverImages.optString("MediumImage")));
		}
		return covers;
	}

    /**
     * Fixes the fact that PBS API only returns blank/stock image for LargeImage
     * Runs the risk that image will come back empty from image server
     * @param imageUrl URL of image at PBS server
     * @return Image URL string with path changed to retrieve large image, if it exists
     */
    String getLargeImageFromMedium(String imageUrl) {
        return imageUrl.
                replaceAll("http://c(\\w).pbsstatic.com/m", "http://c$1.pbsstatic.com/l");
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
