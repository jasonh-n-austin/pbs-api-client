package com.paperbackswap.Url;

import gumi.builders.UrlBuilder;

import java.net.URI;
import java.net.URL;
import java.util.Set;

public class PbsUrlBuilder {
    public static final Integer DEFAULT_LIMIT = 50;
    private UrlBuilder builder = UrlBuilder.empty();

    protected enum PagingParameters {
        LIMIT {
            public String toString() {
                return "Limit";
            }
        },
        OFFSET {
            public String toString() {
                return "Offset";
            }
        }
    }

    /**
     * Used internally to provide efficient fluent operations, as @UrlBuilder is final
     * @param builder UrlBuilder object
     * @see gumi.builders.UrlBuilder
     */
    private PbsUrlBuilder(UrlBuilder builder) {
        this.builder = builder;
        this.builder = addHostAndScheme(builder);
        this.builder = this.builder.setParameter(PbsUrlParams.LIMIT.toString(), DEFAULT_LIMIT.toString());
    }

    /**
     * Creates a properly formatted URL for the paperbackswap.com API
     * @param url A PBS API URL
     * @return A properly formatted PBS API URL
     */
    public static PbsUrlBuilder fromUrl(String url) {
        UrlBuilder builder = UrlBuilder.fromString(url);
        return new PbsUrlBuilder(builder);
    }

    /**
     * Creates a properly formatted URL for the paperbackswap.com API
     * @param url A PBS API URL
     * @return A properly formatted PBS API URL
     */
    public static PbsUrlBuilder fromUrl(URL url) {
        UrlBuilder builder = UrlBuilder.fromUrl(url);
        return new PbsUrlBuilder(builder);
    }

    /**
     * Creates a properly formatted URL for the paperbackswap.com API
     * @param url A PBS API URL
     * @return A properly formatted PBS API URL
     */
    public static PbsUrlBuilder fromPath(PbsUrl url) {
        String pathPart = url.getPath();
        String queryPart = url.getQuery();

        UrlBuilder builder = UrlBuilder.empty().withPath(pathPart).withQuery(queryPart);
        return new PbsUrlBuilder(builder);
    }

    public PbsUrlBuilder withOffset(Integer offset) {
        builder = builder.setParameter(PagingParameters.LIMIT.toString(), DEFAULT_LIMIT.toString())
                .setParameter(PagingParameters.OFFSET.toString(), offset.toString());
        return this;
    }

    public PbsUrlBuilder withOffsetLimit(Integer offset, Integer limit) {
        builder = builder.setParameter(PagingParameters.LIMIT.toString(), limit.toString())
                .setParameter(PagingParameters.OFFSET.toString(), offset.toString());
        return this;
    }

    public PbsUrlBuilder withAddress(String addressId) {
        builder = builder.setParameter(PbsUrlParams.ADDRESS_ID.toString(), addressId);
        return this;
    }

    public PbsUrlBuilder withoutOAuthQuery() {
        removeOauthQuery();
        return this;
    }

    @Override
    public String toString() {
        return builder.toString();
    }

    public URI toUri() {
        return builder.toUri();
    }

    /**
     * Fills in provided ISBN in URI parameter
     * @param isbn ISBN number, in string form (to preserve leading 0s)
     * @return A properly formatted PBS API URL
     */
    public PbsUrlBuilder withIsbn(String isbn) {
        builder = builder.setParameter(PbsUrlParams.ISBN.toString(), isbn);
        return this;
    }

    public PbsUrlBuilder withRequestId(String requestId) {
        builder = builder.setParameter(PbsUrlParams.REQUEST.toString(), requestId);
        return this;
    }

    /**
     * Fills in provided keyword in URI parameter
     * @param keyword Keyword search phrase
     * @return A properly formatted PBS URL
     */
    public PbsUrlBuilder withKeyword(String keyword) {
        builder = builder.setParameter(PbsUrlParams.KEYWORD.toString(), keyword);
        return this;
    }

    // Extract the ISBN from an PBS API URL.
    @SuppressWarnings("UnusedDeclaration")
    public Long extractIsbn() {

        if (builder.queryParameters.containsKey(PbsUrlParams.ISBN.toString())) {
            // Get first query param match
            return Long.parseLong(builder.queryParameters.get(
                    PbsUrlParams.ISBN.toString()
            ).get(0));
        }
        return 0L;
    }

    /**
     * Strips OAuth 1.0a query parameters from query string
     */
    void removeOauthQuery() {
        removeQuerystringItems(PbsOAuthUrl.PARAMETERS);
/*
        Iterator<String> iterator = builder.queryParameters.keySet().iterator();
        while (iterator.hasNext()) {
            String name = iterator.next();

            if (name.toLowerCase().startsWith("oauth")) {
                builder = builder.removeParameters(name);
            }
        }
*/
    }

    UrlBuilder addHostAndScheme(UrlBuilder builder) {
        return addHostAndScheme(builder, Defaults.HOST.toString(),
                Defaults.SCHEME.toString());
    }

    UrlBuilder addHostAndScheme(UrlBuilder builder, String host, String scheme) {
        return builder
                .withHost(host)
                .withScheme(scheme);
    }

    /**
     * Removes a specific set of querystring items
     * @param excludedItems List of query param names to remove
     */
    void removeQuerystringItems(Set<String> excludedItems) {
        for (String exclude : excludedItems) {
            if (builder.queryParameters.containsKey(exclude)) {
                builder = builder.removeParameters(exclude);
            }
        }
    }

}
