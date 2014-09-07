package com.paperbackswap.modules;

import com.google.inject.AbstractModule;
import com.paperbackswap.data.*;

public class BookModule extends AbstractModule {
	@Override
	protected void configure() {
		bind(BookBuilder.class).to(BookBuilderJson.class);
		bind(BookListBuilder.class).to(BookListBuilderJson.class);
        bind(BookRequestBuilder.class).to(BookRequestBuilderJson.class);
        bind(BookRequestListBuilder.class).to(BookRequestListBuilderJson.class);
		bind(Book.class).to(BookImpl.class);
        bind(BookRequest.class).to(BookRequestImpl.class);
        bind(BookResponseHandler.class).to(BookResponseHandlerJson.class);
	}
}
