package com.paperbackswap.data;

import java.util.Date;

/**
 * POJO for representing PBS book requests
 */
public interface BookRequest {
    public abstract String getId();
    public abstract void setId(String id);

    public abstract String getStatus();
    public abstract void setStatus(String status);

    public abstract Date getMailDeadline();
    public abstract void setMailDeadline(Date deadline);

    public abstract BookRequestDirection getDirection();
    public abstract void setDirection(BookRequestDirection direction);
    public abstract void setDirection(String direction);

    public abstract String getDestination();
    public abstract void setDestination(String destination);

    public abstract Book getBook();
    public abstract void setBook(Book book);
}

