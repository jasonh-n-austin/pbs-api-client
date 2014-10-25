package com.paperbackswap.data;

import com.paperbackswap.exceptions.InvalidResponseException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class BookCoverBuilderJson extends JsonBuilderBase implements BookCoverBuilder {
    public Map<Book.CoverImageType, String> construct(Object json) throws InvalidResponseException {
        return getCoverImages(validateObject(json));
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
                    getLargeImageFromXl(coverImages.optString("LargeImage")));
            covers.put(Book.CoverImageType.XLargeImage,
                    coverImages.optString("LargeImage"));
        }
        return covers;
    }

    /**
     * Fixes the fact that PBS API only returns blank/stock image for LargeImage
     * Runs the risk that image will come back empty from image server
     * @param imageUrl URL of image at PBS server
     * @return Image URL string with path changed to retrieve large image, if it exists
     */
    String getLargeImageFromXl(String imageUrl) {
        return imageUrl.
                replaceAll("http://c(\\w).pbsstatic.com/xl", "http://c$1.pbsstatic.com/l");
    }
}
