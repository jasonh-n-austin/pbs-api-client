package com.paperbackswap.modules;

import com.google.inject.AbstractModule;
import com.paperbackswap.Oauth.OAuth10aBackoff;
import com.paperbackswap.Oauth.OAuthBackoff;
import com.paperbackswap.Oauth.PbsOauth;
import com.paperbackswap.Oauth.PbsOauth10a;

public class OauthModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(PbsOauth.class).to(PbsOauth10a.class);
        bind(OAuthBackoff.class).to(OAuth10aBackoff.class);
    }
}
