package com.paperbackswap.modules;

import com.google.inject.AbstractModule;
import com.paperbackswap.data.*;

public class MemberDataModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(ResponseHandler.class).to(ResponseHandlerJson.class);
        bind(PbsResponse.class).to(PbsResponseJson.class);
        bind(MemberData.class).to(MemberDataImpl.class);
        bind(MemberDataBuilder.class).to(MemberDataBuilderJson.class);
    }
}
