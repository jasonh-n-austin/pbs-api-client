package com.paperbackswap;

import com.paperbackswap.Url.Defaults;
import com.paperbackswap.Url.PbsUrl;
import com.paperbackswap.Url.PbsUrlBuilder;
import com.paperbackswap.Url.PbsUrlParams;
import org.junit.Test;

import java.net.URI;

import static org.junit.Assert.*;

public class PbsUrlTests {
    private PbsUrlBuilder builder;

    @Test
    public void creates_new_uri() {
        builder = PbsUrlBuilder.fromPath(PbsUrl.MEMBER_WISH_LIST);
        URI uri = builder.toUri();
        assertEquals(uri.getHost(), Defaults.HOST.toString());
        assertEquals(uri.getScheme(), Defaults.SCHEME.toString());
        assertEquals(uri.getPath(), PbsUrl.MEMBER_WISH_LIST.getPath());
        assertTrue(uri.getQuery().startsWith(PbsUrl.MEMBER_WISH_LIST.getQuery()));
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
        builder = PbsUrlBuilder.fromPath(PbsUrl.ISBN_LIST);
        assertFalse(builder.toUri().toString().contains("%3D"));
    }

    @Test
    public void creates_with_isbn() {
        builder = PbsUrlBuilder.fromPath(PbsUrl.ISBN_LIST).withIsbn("1234");
        assertNotNull(builder);
        assertTrue(builder.toUri().getQuery().contains("ISBN=1234"));
    }


    @Test
    public void creates_with_isbn_string() {
        builder = PbsUrlBuilder.fromPath(PbsUrl.ISBN_LIST).withIsbn("1234");
        assertNotNull(builder);
        assertTrue(builder.toUri().getQuery().contains("ISBN=1234"));
    }

    @Test
    public void sets_default_limit() {
        builder = PbsUrlBuilder.fromPath(PbsUrl.MEMBER_WISH_LIST);
        assertTrue(builder.toUri().getQuery().contains(PbsUrlParams.LIMIT.toString()));
    }

    @Test
    public void enum_produces_full_url_string() {
        PbsUrl url = PbsUrl.BOOK_SHELF;
        URI uri = url.toUri();
        assertTrue(uri.getScheme().startsWith("http"));
        assertTrue(uri.getHost().startsWith("www"));
        assertTrue(uri.getQuery().toString().contains("RequestType="));
    }

    @Test
    public void adds_offset_with_default_limit() {
        PbsUrlBuilder builder = PbsUrl.BOOK_SHELF.toBuilder();
        assertFalse(builder.toUri().getQuery().contains("Offset="));
        assertTrue(builder.toUri().getQuery().contains("Limit=50")); //default

        builder.withOffset(50);
        assertTrue(builder.toUri().getQuery().contains("Offset=50"));
        assertTrue(builder.toUri().getQuery().contains("Limit=50")); //default
    }

    @Test
    public void adds_offset_with_set_limit() {
        PbsUrlBuilder builder = PbsUrl.BOOK_SHELF.toBuilder();
        assertFalse(builder.toUri().getQuery().contains("Offset="));
        assertTrue(builder.toUri().getQuery().contains("Limit=50")); //default

        builder.withOffsetLimit(10, 10);
        assertTrue(builder.toUri().getQuery().contains("Offset=10"));
        assertTrue(builder.toUri().getQuery().contains("Limit=10")); //default
    }
}