package com.paperbackswap.Url;

import gumi.builders.UrlBuilder;

import java.net.URI;
import java.util.HashSet;
import java.util.Set;

/**
 * OAuth 1.0a URLs for PBS API
 */
@SuppressWarnings("ALL")
public class PbsOAuthUrl {

    public static final String REQUEST_TOKEN ="http://www.paperbackswap.com/api/request_token.php";
    public static final String AUTHORIZE ="http://www.paperbackswap.com/api/authorize.php";
    public static final String ACCESS_TOKEN = "http://www.paperbackswap.com/api/access_token.php";

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
