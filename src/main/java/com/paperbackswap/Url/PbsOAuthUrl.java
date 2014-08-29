package com.paperbackswap.Url;

import gumi.builders.UrlBuilder;

import java.net.URI;
import java.util.HashSet;
import java.util.Set;

/**
 * OAuth 1.0a URLs for PBS API
 */
@SuppressWarnings("ALL")
public enum PbsOAuthUrl {

    REQUEST_TOKEN("/api/request_token.php"),
    AUTHORIZE("/api/authorize.php"),
    ACCESS_TOKEN ("/api/access_token.php");

    private String path;

    private PbsOAuthUrl(String path) {
        this.path = path;
    }

    public String toString() {
        return UrlBuilder.empty()
                .withHost(Defaults.HOST.toString())
                .withScheme(Defaults.SCHEME.toString())
                .withPath(path)
                .toString();
    }

    public URI toUri() {
        return UrlBuilder.empty()
                .withHost(Defaults.HOST.toString())
                .withScheme(Defaults.SCHEME.toString())
                .withPath(path)
                .toUri();
    }


    /**
     * Provides reference for all OAuth 1.0a parameter names
     */
    public final static Set<String> PARAMETERS;

    static {
        PARAMETERS = new HashSet<String>();
        PARAMETERS.add("oauth_signature");
        PARAMETERS.add("oauth_nonce");
        PARAMETERS.add("oauth_token");
        PARAMETERS.add("oauth_timestamp");
        PARAMETERS.add("oauth_consumer_key");
        PARAMETERS.add("oauth_signature_method");
        PARAMETERS.add("oauth_version");
    }

}
