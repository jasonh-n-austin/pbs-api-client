package com.paperbackswap.data;

import com.paperbackswap.exceptions.BookListBuilderException;
import com.paperbackswap.exceptions.BooksResponseHasErrorsException;
import com.paperbackswap.exceptions.InvalidBookException;
import com.paperbackswap.exceptions.InvalidBooksResponseException;

public interface BookListBuilder {
	public BookList construct(Object source) throws InvalidBookException, BookListBuilderException, InvalidBooksResponseException, BooksResponseHasErrorsException;
}