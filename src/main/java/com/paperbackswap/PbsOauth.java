package com.paperbackswap;

import gumi.builders.UrlBuilder;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthProvider;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import oauth.signpost.exception.OAuthNotAuthorizedException;
import org.apache.commons.lang3.StringUtils;


public class PbsOauth {
    private boolean isAuthorizing;
    private static OAuthConsumer consumer;
    private static OAuthProvider provider;

    public PbsOauth(String apiKey, String apiSecret) {
        consumer = new CommonsHttpOAuthConsumer(apiKey, apiSecret);
        provider = new CommonsHttpOAuthProvider(
                PbsUrlInfo.REQUEST_TOKEN.toString(),
                PbsUrlInfo.ACCESS_TOKEN.toString(),
                PbsUrlInfo.AUTHORIZE.toString());
    }

    public PbsOauth(String apiKey, String apiSecret, String token, String secret) {
        this(apiKey, apiSecret);
        setTokenWithSecret(token, secret);
    }

    public synchronized void setTokenWithSecret(String token, String secret) {
        consumer.setTokenWithSecret(token, secret);
    }

    public String getToken() {
        return consumer.getToken();
    }

    public String getTokenSecret() {
        return consumer.getTokenSecret();
    }

    /**
     * Called to begin OAuth dance. Open the returned URL in a browser,
     * @param callback URI to redirect to when authorization is complete, be sure to have listener configured.
     *                 Listener will need to capture {@link}=oauth.signpost.OAuth.OAUTH_VERIFIER query parameter.
     *                 isAuthorizing is set to true when this method is called.
     * @return
     * @throws OAuthCommunicationException
     * @throws OAuthExpectationFailedException
     * @throws OAuthNotAuthorizedException
     * @throws OAuthMessageSignerException
     */
    public String getRequestTokenUrl(String callback)
            throws OAuthCommunicationException, OAuthExpectationFailedException,
            OAuthNotAuthorizedException, OAuthMessageSignerException {
        isAuthorizing = true;
        return provider.retrieveRequestToken(consumer, callback);
    }

    /**
     * Called to retrieve access token from OAuth provider once verifier has been retrieved from request token URI
     * This implies user has authorized the initial request in a web browser.
     * isAuthorizing is set to false if this succeeds.
     *
     * @param verifier Verifier as retrieved from request token URL, as called from getRequestTokenUri method.
     */
    public void retrieveAccessToken(String verifier) throws OAuthCommunicationException, OAuthExpectationFailedException, OAuthNotAuthorizedException, OAuthMessageSignerException {
        provider.setOAuth10a(true); //This is SUPER important...won't work without it
        provider.retrieveAccessToken(consumer, verifier);
        isAuthorizing = false; // authorization dance is complete
    }

    // Keep track of whether the app is in the oauth dance
    public boolean isAuthorizing() {
        return isAuthorizing;
    }

    /**
     * Checks to see if the user has been authorized to make API calls with the app
     *
     * @return
     */
    public boolean isAuthorized() {
        if (!StringUtils.isBlank(consumer.getToken()) &&
                !StringUtils.isBlank(consumer.getTokenSecret()) &&
                !isAuthorizing()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Adds OAuth signature query params to URL
     *
     * @param url URL to have OAuth parameters added
     * @return
     */
    public static String signRequest(String url) throws OAuthCommunicationException, OAuthExpectationFailedException, OAuthMessageSignerException {
        return consumer.sign(PbsUrlBuilder.fromUrl(url).withoutOAuthQuery().toString());
        //output = output.replace("www.paperbackswap.com", "www-paperbackswap-com-r7z8ylmap21m.runscope.net");
    }

    /**
     * Checks to see if the provided URL has OAuth 1.0a query parameters
     * @param url
     * @return
     */
    public static boolean isSigned(String url) {
        UrlBuilder builder = UrlBuilder.fromString(url);

        for (String key : PbsUrlBuilder.Oauth.Parameters) {
            boolean contains = builder.queryParameters.containsKey(key);
            if (!contains) {
                return contains;
            }
        }

        return true;
    }
}