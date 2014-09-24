package com.paperbackswap.Oauth;

import oauth.signpost.exception.OAuthException;

public interface CallableOAuth {
    public String retrieveRequestToken(String callback) throws OAuthException;
    public void retrieveAccessToken() throws OAuthException;
}
