package com.paperbackswap.data;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.List;
import java.util.Map;


public class BookImpl implements Book, Serializable {
	public BookImpl() {}

    private String status;
	private String title;
	private String description;
	private String isbn10;
	private String isbn13;
	private String image;
	private float rating;
	private int published;
	private String publisher;
	private int numberOfPages;
	private int wishes;
	private String binding;
	private boolean showRatings;
	private boolean available;
	private List<String> authors;
	private Map<CoverImageType, String> coverImages;
    private Integer queuePosition;
    private Integer queueTotal;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

	public String getAuthorsList() {
		return StringUtils.join(authors, ", ");
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getIsbn10() {
		return isbn10;
	}

	public void setIsbn10(String isbn10) {
		this.isbn10 = isbn10;
	}

	public String getIsbn13() {
		return isbn13;
	}

	public void setIsbn13(String isbn13) {
		this.isbn13 = isbn13;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public float getRating() {
		return rating;
	}

	public void setRating(float rating) {
		this.rating = rating;
	}

	public int getPublished() {
		return published;
	}

	public void setPublished(int published) {
		this.published = published;
	}

	public int getNumberOfPages() {
		return numberOfPages;
	}

	public void setNumberOfPages(int numberOfPages) {
		this.numberOfPages = numberOfPages;
	}

	public int getWishes() {
		return wishes;
	}

	public void setWishes(int wishes) {
		this.wishes = wishes;
	}

	public String getBinding() {
		return binding;
	}

	public void setBinding(String binding) {
		this.binding = binding;
	}

	public List<String> getAuthors() {
		return authors;
	}

	public void setAuthors(List<String> authors) {
		this.authors = authors;
	}

	public Map<CoverImageType, String> getCoverImages() {
		return coverImages;
	}

	public void setCoverImages(Map<CoverImageType, String> coverImages) {
		this.coverImages = coverImages;
	}

    public Integer getQueuePosition() {
        return queuePosition;
    }

    public void setQueuePosition(Integer queuePosition) {
        this.queuePosition = queuePosition;
    }

    public Integer getQueueTotal() {
        return queueTotal;
    }

    public void setQueueTotal(Integer queueTotal) {
        this.queueTotal = queueTotal;
    }

	@Override
	public boolean getShowRatings() {
		return showRatings;
	}

	@Override
	public void setShowRatings(boolean showRatings) {
		this.showRatings = showRatings;
	}

	@Override
	public String getPublisher() {
		return publisher;
	}

	@Override
	public void setPublisher(String publisher) {
		this.publisher = publisher;
		
	}

	@Override
	public boolean getAvailable() {
		return available;
	}

	@Override
	public void setAvailable(boolean available) {
		this.available = available;
	}
}