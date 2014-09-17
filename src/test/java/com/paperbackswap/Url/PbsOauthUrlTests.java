package com.paperbackswap.Url;

import org.junit.Test;

import java.net.URI;

import static org.junit.Assert.assertEquals;

public class PbsOauthUrlTests {
    @Test
    public void constructs_full_url() {
        String url = PbsOAuthUrl.ACCESS_TOKEN;
        URI uri = URI.create(url);
        assertEquals(uri.getHost(), Defaults.HOST.toString());
        assertEquals(uri.getScheme(), Defaults.SCHEME.toString());
    }
}
