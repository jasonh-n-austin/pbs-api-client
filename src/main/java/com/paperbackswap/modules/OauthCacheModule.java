package com.paperbackswap.modules;

import com.google.inject.AbstractModule;
import com.paperbackswap.Oauth.*;

public class OauthCacheModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(PbsOauth.class).to(PbsOauthCache.class);
        bind(OAuthBackoff.class).to(OAuth10aBackoff.class);
    }

}
