package com.paperbackswap.data;

import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

public class BookRequestImpl implements BookRequest, Serializable {
    private static final long serialVersionUID = 7626471155622776147L;
    @SerializedName("ID")
    private String id;
    @SerializedName("Status")
    private String status;
    @SerializedName("MailDeadline")
    private Date mailDeadline;
    @SerializedName("Direction")
    private BookRequestDirection direction;
    @SerializedName("Destination")
    private String destination;
    @SerializedName("Book")
    private BookImpl book;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getStatus() {
        return status;
    }

    @Override
    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public Date getMailDeadline() {
        return mailDeadline;
    }

    @Override
    public void setMailDeadline(Date deadline) {
        this.mailDeadline = deadline;
    }

    @Override
    public BookRequestDirection getDirection() {
        return direction;
    }

    @Override
    public void setDirection(BookRequestDirection direction) {
        this.direction = direction;
    }

    @Override
    public void setDirection(String direction) {
        this.direction = BookRequestDirection.fromString(direction);
    }

    @Override
    public String getDestination() {
        return destination;
    }

    @Override
    public void setDestination(String destination) {
        this.destination = destination;
    }

    @Override
    public Book getBook() {
        return book;
    }

    @Override
    public void setBook(Book book) {
        this.book = (BookImpl)book;
    }

}
