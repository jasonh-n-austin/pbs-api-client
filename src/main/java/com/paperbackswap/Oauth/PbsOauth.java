package com.paperbackswap.Oauth;

import oauth.signpost.exception.OAuthException;

public interface PbsOauth {
    static final String USER_AGENT_HEADER = "User-Agent";
    static final String USER_AGENT = "pbs-api-client/1.3";

    public PbsOauth construct(String apiKey, String apiSecret);
    public PbsOauth construct(String deviceId, String apiKey, String apiSecret);
    public PbsOauth construct(String apiKey, String apiSecret, String token, String secret);
    public void setTokenWithSecret(String token, String secret);
    public String getToken();
    public String getTokenSecret();
    public String getRequestTokenUrl();
    public String getAccessTokenUrl();
    public String getRequestTokenUrl(String callback) throws OAuthException;
    public void retrieveAccessToken(String verifier) throws OAuthException;
    public String signRequest(String url) throws OAuthException;
    public boolean isAuthorizing();
    public boolean isAuthorized();
    public boolean isSigned(String url);

}
