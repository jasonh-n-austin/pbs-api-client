package com.paperbackswap.modules;

import com.google.inject.AbstractModule;
import com.paperbackswap.data.*;

public class BookModule extends AbstractModule {
	@Override
	protected void configure() {
		bind(BookBuilder.class).to(BookBuilderJson.class);
		bind(BookListBuilder.class).to(BookListBuilderJson.class);
		bind(Book.class).to(BookImpl.class);
	}
}
