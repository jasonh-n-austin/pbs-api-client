package com.paperbackswap.Oauth;

import com.paperbackswap.Url.PbsOAuthCacheUrl;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class PbsOauthCache implements PbsOauth {
    private String deviceId;
    private String apiKey;

    @Override
    public PbsOauth construct(String apiKey, String apiSecret) {
        this.apiKey = apiKey;
        // API secret is ignored
        return this;
    }

    @Override
    public PbsOauth construct(String deviceId, String apiKey, String apiSecret) {
        this.deviceId = deviceId;
        return construct(apiKey, apiSecret);
    }

    @Override
    public PbsOauth construct(String apiKey, String apiSecret, String token, String secret) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setTokenWithSecret(String token, String secret) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getToken() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getTokenSecret() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getRequestTokenUrl() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getAccessTokenUrl() {
        return null;
    }

    @Override
    public String getRequestTokenUrl(String callback) throws OAuthException {
        JSONObject bodyJson = new JSONObject();
        bodyJson.put("device_id", deviceId)
                .put("client_id", apiKey)
                .put("redirect_uri", PbsOAuthCacheUrl.ACCESS_TOKEN.replace("{device_id}", deviceId));

        String url = PbsOAuthCacheUrl.REQUEST_TOKEN;

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        HttpURLConnection con;
        try {
            con = (HttpURLConnection) new URL(url).openConnection();
            con.setRequestMethod("POST");
            con.setDoInput(true);
            con.setDoOutput(true);
            con.setRequestProperty("Content-Type", "application/json");
            con.connect();
            con.getOutputStream().write((bodyJson.toString()).getBytes());

            InputStream is = con.getInputStream();
            byte[] b = new byte[1024];

            while ( is.read(b) != -1)
                baos.write(b);

            con.disconnect();

        } catch (MalformedURLException e) {
            throw new OAuthCommunicationException(e);
        } catch (ProtocolException e) {
            throw new OAuthCommunicationException(e);
        } catch (IOException e) {
            throw new OAuthCommunicationException(e);
        }
        String response = new String(baos.toByteArray());
        JSONObject responseJson = new JSONObject(response);
        return responseJson.getString("authorize_url");
    }

    @Override
    public void retrieveAccessToken(String verifier) throws OAuthException {
        throw new UnsupportedOperationException();
    }

    @Override
    public String signRequest(String url) throws OAuthException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isAuthorizing() {
        return false;
    }

    @Override
    public boolean isAuthorized() {
        return false;
    }

    @Override
    public boolean isSigned(String url) {
        throw new UnsupportedOperationException();
    }
}
