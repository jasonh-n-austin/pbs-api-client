package com.paperbackswap.data;

import com.paperbackswap.exceptions.InvalidBookException;
import com.paperbackswap.exceptions.InvalidResponseException;

public interface BookBuilder {
    public Book construct(Object source) throws InvalidBookException, InvalidResponseException;
}
