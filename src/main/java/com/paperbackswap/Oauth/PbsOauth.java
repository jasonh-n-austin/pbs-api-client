package com.paperbackswap.Oauth;

import com.paperbackswap.Url.PbsUrlBuilder;
import gumi.builders.UrlBuilder;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;
import oauth.signpost.OAuthProviderListener;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthProvider;
import oauth.signpost.exception.*;
import oauth.signpost.http.HttpRequest;
import oauth.signpost.http.HttpResponse;
import oauth.signpost.signature.AuthorizationHeaderSigningStrategy;
import oauth.signpost.signature.HmacSha1MessageSigner;
import org.apache.commons.lang3.StringUtils;

import static com.paperbackswap.Url.PbsOAuthUrl.*;

@SuppressWarnings("UnusedDeclaration")
public class PbsOauth {
    private boolean isAuthorizing;
    private static OAuthConsumer consumer;
    private static OAuthProvider provider;
    private static final String USER_AGENT_HEADER = "User-Agent";
    private static final String USER_AGENT = "pbs-api-client/1.3";

    public PbsOauth(String apiKey, String apiSecret) {
        consumer = new CommonsHttpOAuthConsumer(apiKey, apiSecret);
        provider = new CommonsHttpOAuthProvider(
                REQUEST_TOKEN,
                ACCESS_TOKEN,
                AUTHORIZE);
        consumer.setSigningStrategy(new AuthorizationHeaderSigningStrategy());
        consumer.setMessageSigner(new HmacSha1MessageSigner());
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

    public String getRequestTokenUrl() {
        return provider.getRequestTokenEndpointUrl();
    }

    public String getAccessTokenUrl() {
        return provider.getAccessTokenEndpointUrl();
    }

    /**
     * Called to begin OAuth dance. Open the returned URL in a browser,
     * @param callback URI to redirect to when authorization is complete, be sure to have listener configured.
     *                 Listener will need to capture 'verifier' query parameter.
     *                 isAuthorizing is set to true when this method is called.
     * @return Token secret string
     * @throws OAuthCommunicationException
     * @throws OAuthExpectationFailedException
     * @throws OAuthNotAuthorizedException
     * @throws OAuthMessageSignerException
     */
    public String getRequestTokenUrl(String callback) throws OAuthException {
        isAuthorizing = true;
        provider.setListener(new OAuthProviderListener() {
            @Override
            public void prepareRequest(HttpRequest request) throws Exception {
                // Custom user agent
                request.setHeader(USER_AGENT_HEADER, USER_AGENT);
            }

            @Override
            public void prepareSubmission(HttpRequest request) throws Exception {
//                System.out.println(request.getAllHeaders());
                //request.setRequestUrl("http://www-paperbackswap-com-r7z8ylmap21m.runscope.net/api/request_token.php");
            }

            @Override
            public boolean onResponseReceived(HttpRequest request, HttpResponse response) throws Exception {
//                BasicHttpResponse responseObject = ((BasicHttpResponse) response.unwrap());
//                System.out.println("Status: " + responseObject.getStatusLine().getStatusCode());
//                String body = IOUtils.toString(responseObject.getEntity().getContent());
//                System.out.println("Body: "+body);
//                Header engineHeader = (Header) responseObject.getHeaders("Engine")[0];
//                System.out.println("Engine: " + engineHeader.getValue());
                return false;
            }
        });

        //return provider.retrieveRequestToken(consumer, callback);
        return OAuthBackoff.retrieveRequestTokenWithRetry(provider, consumer, callback);

    }

    /**
     * Called to retrieve access token from OAuth provider once verifier has been retrieved from request token URI
     * This implies user has authorized the initial request in a web browser.
     * isAuthorizing is set to false if this succeeds.
     *
     * @param verifier Verifier as retrieved from request token URL, as called from getRequestTokenUri method.
     */
    public void retrieveAccessToken(String verifier) throws OAuthException {
        provider.setListener(new OAuthProviderListener() {
            @Override
            public void prepareRequest(HttpRequest request) throws Exception {
                request.setHeader(USER_AGENT_HEADER, USER_AGENT);
            }

            @Override
            public void prepareSubmission(HttpRequest request) throws Exception {

            }

            @Override
            public boolean onResponseReceived(HttpRequest request, HttpResponse response) throws Exception {
                return false;
            }
        });
        provider.setOAuth10a(true); //This is SUPER important...won't work without it
        //provider.retrieveAccessToken(consumer, verifier);
        provider = OAuthBackoff.retrieveAccessTokenWithRetry(provider, consumer, verifier);
        isAuthorizing = false; // authorization dance is complete
    }

    // Keep track of whether the app is in the oauth dance
    public boolean isAuthorizing() {
        return isAuthorizing;
    }

    /**
     * Checks to see if the user has been authorized to make API calls with the app
     *
     * @return Determines if all OAuth 1.0 keys are present and isn't currently in OAuth dance
     */
    public boolean isAuthorized() {
        return (!StringUtils.isEmpty(consumer.getToken()) &&
                !StringUtils.isEmpty(consumer.getTokenSecret())&&
                !isAuthorizing());
    }

    /**
     * Adds OAuth signature query params to URL
     *
     * @param url URL to have OAuth parameters added
     * @return Uses provided OAuth 1.0a key/token to create fresh OAuth query params
     */
    public static String signRequest(String url) throws OAuthCommunicationException, OAuthExpectationFailedException, OAuthMessageSignerException {
        return consumer.sign(PbsUrlBuilder.fromUrl(url).withoutOAuthQuery().toString());
    }

    /**
     * Checks to see if the provided URL has OAuth 1.0a query parameters
     * @param url Any URL with OAuth 1.0a query params
     * @return True if OAuth 1.0a query params are in provided URL
     */
    public static boolean isSigned(String url) {
        UrlBuilder builder = UrlBuilder.fromString(url);

        for (String key : PARAMETERS) {
            if (!builder.queryParameters.containsKey(key)) {
                return false;
            }
        }

        return true;
    }
}