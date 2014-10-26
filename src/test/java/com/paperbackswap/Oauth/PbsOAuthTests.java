package com.paperbackswap.Oauth;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.paperbackswap.Test.GuiceJUnitRunner;
import com.paperbackswap.modules.OauthModule;
import junit.framework.TestCase;
import oauth.signpost.exception.OAuthException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(GuiceJUnitRunner.class)
@GuiceJUnitRunner.GuiceModules({ OauthModule.class })
public class PbsOAuthTests {
    static Injector mInjector;

    @Inject
    public void setup() {
        mInjector = Guice.createInjector(new OauthModule());
    }

    @Test
    public void creates_singleton() {
        PbsOauth pbsOauth = mInjector.getInstance(PbsOauth.class).construct("X", "Y");
        assertNotNull(pbsOauth);
        assertFalse(pbsOauth.isAuthorized());
    }

    @Test
    public void creates_authorized_singleton() {
        PbsOauth pbsOauth = mInjector.getInstance(PbsOauth.class).construct("X", "Y", "Z", "ZZ");
        assertNotNull(pbsOauth);
        assertTrue(pbsOauth.isAuthorized());
    }

    @Test
    public void is_signed() {
        PbsOauth pbsOauth = mInjector.getInstance(PbsOauth.class).construct("X", "Y");
        //String testUrl = "http://xxyzzz.com/v1/x.html?oauth_consumer_key=6d1c3573504d7497186db11b992b523e&oauth_nonce=5342006803197489593&oauth_signature=16Y0ailVZgk%2BTmZnWxtBM8yCFqQ%3D&oauth_signature_method=HMAC-SHA1&oauth_timestamp=1408860112&oauth_token=1421be5443f688195d09a43c2efa8a6f&oauth_version=1.0&";
        String testUrl = "http://www.xxxxx.com/api/v2/index.php?oauth_consumer_key=6d1c3573504d7497186db11b992b523e&oauth_nonce=5342006803197489593&oauth_signature=16Y0ailVZgk%2BTmZnWxtBM8yCFqQ%3D&oauth_signature_method=HMAC-SHA1&oauth_timestamp=1408860112&oauth_token=1421be5443f688195d09a43c2efa8a6f&oauth_version=1.0&Limit=10&Offset=10";
        Assert.assertTrue(pbsOauth.isSigned(testUrl));
    }

    @Test
    public void test_request_token() throws OAuthException {
        PbsOauth pbsOauth = mInjector.getInstance(PbsOauth.class).construct("6d1c3573504d7497186db11b992b523e", "8bb941b3ca99d152e47c3e8a96f7f493");
        String requestUrl = pbsOauth.getRequestTokenUrl("http://localhost/catchme");
        System.out.println(requestUrl);
        TestCase.assertNotNull(requestUrl);
    }

}
