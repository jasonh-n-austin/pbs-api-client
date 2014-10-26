package com.paperbackswap.Oauth;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.paperbackswap.Test.GuiceJUnitRunner;
import com.paperbackswap.modules.OauthCacheModule;
import com.paperbackswap.modules.OauthModule;
import junit.framework.TestCase;
import oauth.signpost.exception.OAuthException;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(GuiceJUnitRunner.class)
@GuiceJUnitRunner.GuiceModules({ OauthCacheModule.class })
public class PbsOauthCacheTests {
    static Injector mInjector;

    @Inject
    public void setup() {
        mInjector = Guice.createInjector(new OauthCacheModule());
    }

    @Test
    public void creates_oauth() {
        PbsOauth pbsOauth = mInjector.getInstance(PbsOauth.class).construct("X", "Y");
        assertNotNull(pbsOauth);
        assertFalse(pbsOauth.isAuthorized());
    }

    @Test
    public void test_request_token() throws OAuthException {
        PbsOauth pbsOauth = mInjector.getInstance(PbsOauth.class).construct("abc123", "6d1c3573504d7497186db11b992b523e", "");
        String requestUrl = pbsOauth.getRequestTokenUrl("http://localhost/catchme");
        System.out.println(requestUrl);
        TestCase.assertNotNull(requestUrl);
    }
}
