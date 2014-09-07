package com.paperbackswap.data;

import com.paperbackswap.exceptions.InvalidBookException;

public interface BookBuilder {
    public Book construct(Object source) throws InvalidBookException;
}
