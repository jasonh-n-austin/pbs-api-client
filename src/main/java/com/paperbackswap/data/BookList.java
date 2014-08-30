package com.paperbackswap.data;

import com.paperbackswap.Url.PbsUrlBuilder;

import java.util.ArrayList;
import java.util.List;

public class BookList extends ArrayList<Book> {
	private static final long serialVersionUID = -8227039917486379533L;
	private final PbsUrlBuilder nextPage;

	public BookList(List<Book> source, PbsUrlBuilder nextPage) {
		super(source);
		this.nextPage = nextPage;
	}

	@SuppressWarnings("UnusedDeclaration")
    public PbsUrlBuilder getNextPage() {
		return nextPage;
	}
}
