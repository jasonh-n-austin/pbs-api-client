package com.paperbackswap.data;

import com.paperbackswap.exceptions.*;

public interface BookListBuilder {
	public BookList construct(Object source) throws InvalidBookException, BookListBuilderException, InvalidBooksResponseException, ResponseHasErrorsException, InvalidResponseException;
}