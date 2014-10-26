package com.paperbackswap.Oauth;

import com.paperbackswap.Test.GuiceJUnitRunner;
import com.paperbackswap.modules.OauthCacheModule;
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
    @Test
    public void creates_oauth() {
        PbsOauth pbsOauth = PbsOauthFactory.getInstance("X", "Y");
        assertNotNull(pbsOauth);
        assertFalse(pbsOauth.isAuthorized());
    }

    @Test
    public void creates_authorized_singleton() {
        PbsOauth pbsOauth = PbsOauthFactory.getInstance("X", "Y", "Z", "ZZ");
        assertNotNull(pbsOauth);
        assertTrue(pbsOauth.isAuthorized());
    }

    @Test
    public void test_request_token() throws OAuthException {
        PbsOauth pbsOauth = PbsOauthFactory.getInstance("abc123", "6d1c3573504d7497186db11b992b523e", "");
        String requestUrl = pbsOauth.getRequestTokenUrl("http://localhost/catchme");
        System.out.println(requestUrl);
        TestCase.assertNotNull(requestUrl);
    }
}
