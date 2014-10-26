package com.paperbackswap.Oauth;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;
import oauth.signpost.exception.OAuthException;

public interface OAuthBackoff {
    public String retrieveRequestTokenWithRetry(OAuthProvider provider, OAuthConsumer consumer, String callback) throws OAuthException;
    public OAuthProvider retrieveAccessTokenWithRetry(OAuthProvider provider, OAuthConsumer consumer, String verifier) throws OAuthException;
    public long getWaitTimeExponential(int retryCount);

    public enum Results {
        SUCCESS,
        SERVER_ERROR
    }
}
