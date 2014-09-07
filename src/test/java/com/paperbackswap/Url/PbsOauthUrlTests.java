package com.paperbackswap.Url;

import org.junit.Test;

import java.net.URI;

import static org.junit.Assert.assertEquals;

public class PbsOauthUrlTests {
    @Test
    public void constructs_full_uri() {
        PbsOAuthUrl url = PbsOAuthUrl.ACCESS_TOKEN;
        URI uri = url.toUri();
        assertEquals(uri.getHost(), Defaults.HOST.toString());
        assertEquals(uri.getScheme(), Defaults.SCHEME.toString());
    }

    @Test
    public void constructs_full_url() {
        PbsOAuthUrl url = PbsOAuthUrl.ACCESS_TOKEN;
        URI uri = URI.create(url.toString());
        assertEquals(uri.getHost(), Defaults.HOST.toString());
        assertEquals(uri.getScheme(), Defaults.SCHEME.toString());
    }
}
