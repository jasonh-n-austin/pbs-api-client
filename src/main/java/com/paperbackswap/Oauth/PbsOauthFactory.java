package com.paperbackswap.Oauth;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.paperbackswap.modules.OauthCacheModule;

public class PbsOauthFactory {
    public static Injector mInjector;

    static {
        mInjector = Guice.createInjector(new OauthCacheModule());
    }

    public static PbsOauth getInstance(String apiKey, String apiSecret) {
            return mInjector.getInstance(PbsOauth.class).construct(apiKey, apiSecret);
    }

    public static PbsOauth getInstance(String deviceId, String apiKey, String apiSecret) {
        return mInjector.getInstance(PbsOauth.class).construct(deviceId, apiKey, apiSecret);
    }


    public static PbsOauth getInstance(String apiKey, String apiSecret, String accessToken, String accessTokenSecret) {
            return mInjector.getInstance(PbsOauth.class).construct(apiKey, apiSecret, accessToken, accessTokenSecret);
    }
}
