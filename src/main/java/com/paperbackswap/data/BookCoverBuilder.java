package com.paperbackswap.data;

import com.paperbackswap.exceptions.InvalidResponseException;

import java.util.Map;

public interface BookCoverBuilder {
    public Map<Book.CoverImageType, String> construct(Object json) throws InvalidResponseException;
}
