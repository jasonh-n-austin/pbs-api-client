package com.paperbackswap;

import org.junit.Test;

import java.net.URI;

import static org.junit.Assert.*;

public class PbsUriTests {
    private PbsUrlBuilder builder;

    @Test
    public void creates_new_uri() {
        builder = PbsUrlBuilder.fromPath(PbsUrlInfo.MEMBER_WISH_LIST);
        URI uri = builder.toUri();
        assertEquals(uri.getHost(), Defaults.HOST.toString());
        assertEquals(uri.getScheme(), Defaults.SCHEME.toString());
        assertTrue((uri.getPath()+"?RequestType=MemberWishList").startsWith(PbsUrlInfo.MEMBER_WISH_LIST.toString()));
    }

    @Test
    public void reverts_to_http() {
        builder = PbsUrlBuilder.fromUrl("https://secure.paperbackswap.com/v2/xxx");
        URI uri = builder.toUri();
        assertEquals(uri.getHost(), Defaults.HOST.toString());
        assertEquals(uri.getScheme(), Defaults.SCHEME.toString());
    }

    @Test
    public void does_not_encode_query() {
        builder = PbsUrlBuilder.fromPath(PbsUrlInfo.ISBN_LIST);
        assertFalse(builder.toUri().toString().contains("%3D"));
    }

    @Test
    public void creates_with_isbn() {
        builder = PbsUrlBuilder.fromPath(PbsUrlInfo.ISBN_LIST).withIsbn(1234L);
        assertNotNull(builder);
        assertTrue(builder.toUri().getQuery().contains("ISBN=1234"));
    }

    @Test
    public void sets_default_limit() {
        builder = PbsUrlBuilder.fromPath(PbsUrlInfo.MEMBER_WISH_LIST);
        assertTrue(builder.toUri().getQuery().contains(PbsUrlInfo.KEY_LIMIT.toString()));
    }
}