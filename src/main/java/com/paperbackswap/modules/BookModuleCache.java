package com.paperbackswap.modules;

import com.google.inject.AbstractModule;
import com.paperbackswap.data.*;

public class BookModuleCache extends AbstractModule {
    @Override
    protected void configure() {
        bind(ResponseHandler.class).to(ResponseHandlerJsonCache.class);
        bind(PbsResponse.class).to(PbsResponseJson.class);
        bind(BookBuilder.class).to(BookBuilderJsonCache.class);
        bind(BookListBuilder.class).to(BookListBuilderJsonCache.class);
        bind(BookRequestBuilder.class).to(BookRequestBuilderJsonCache.class);
        bind(BookRequestListBuilder.class).to(BookRequestListBuilderJson.class);
        bind(Book.class).to(BookImpl.class);
        bind(BookRequest.class).to(BookRequestImpl.class);
        bind(BookResponseHandler.class).to(BookResponseHandlerJsonCache.class);
        bind(BookCoverBuilder.class).to(BookCoverBuilderJson.class);
        bind(LinkProcessor.class).to(LinkProcessorJsonCache.class);
        bind(ResponseError.class).to(ResponseErrorJsonCache.class);
    }
}
