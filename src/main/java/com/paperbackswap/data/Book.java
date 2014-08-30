package com.paperbackswap.data;

import java.util.List;
import java.util.Map;

@SuppressWarnings("UnusedDeclaration")
public interface Book {

    public abstract String getStatus();

    public abstract void setStatus(String status);

	public abstract String getAuthorsList();

	public abstract String getTitle();

	public abstract void setTitle(String title);

	public abstract String getDescription();

	public abstract void setDescription(String description);

	public abstract String getIsbn10();

	public abstract void setIsbn10(String isbn10);

	public abstract String getIsbn13();

	public abstract void setIsbn13(String isbn13);

	public abstract String getImage();

	public abstract void setImage(String image);

	public abstract float getRating();

	public abstract void setRating(float rating);

	public abstract int getPublished();

	public abstract void setPublished(int published);

	public abstract int getNumberOfPages();

	public abstract void setNumberOfPages(int numberOfPages);

	public abstract int getWishes();

	public abstract void setWishes(int wishes);

	public abstract String getBinding();

	public abstract void setBinding(String binding);

	public abstract List<String> getAuthors();

	public abstract void setAuthors(List<String> authors);

	public abstract Map<CoverImageType, String> getCoverImages();

	public abstract void setCoverImages(Map<CoverImageType, String> coverImages);

	public abstract boolean getShowRatings();
	
	public abstract void setShowRatings(boolean showRatings);
	
	public abstract String getPublisher();
	
	public abstract void setPublisher(String publisher);
	
	public abstract boolean getAvailable();
	
	public abstract void setAvailable(boolean available);

    public abstract Integer getQueuePosition();

    public abstract void setQueuePosition(Integer queuePosition);

    public abstract Integer getQueueTotal();

    public abstract void setQueueTotal(Integer queueTotal);

    public enum CoverImageType {
        SmallImage, MediumImage, LargeImage
    }

}