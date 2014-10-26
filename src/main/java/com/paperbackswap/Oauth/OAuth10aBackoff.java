package com.paperbackswap.Oauth;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;
import oauth.signpost.exception.OAuthException;
import org.apache.commons.lang3.StringUtils;

//TODO: Find a generic way to do this instead of boilerplate
public class OAuth10aBackoff implements OAuthBackoff {
    private static final int MAX_WAIT_INTERVAL = 10;
    private static final int MAX_RETRIES = 5;

    public String retrieveRequestTokenWithRetry(OAuthProvider provider, OAuthConsumer consumer, String callback) throws OAuthException {
        OAuthException lastException = null;
            int retries = 0;
            boolean retry = false;
            String ret = null;
            do {
                try {
                    ret = provider.retrieveRequestToken(consumer, callback);
                } catch (OAuthException e) {
                    lastException = e;

                }

                if (StringUtils.isEmpty(ret)) {
                    retry = true;
                } else {
                    return ret;
                }

                // Wait to retry.
                long waitTime = Math.min(getWaitTimeExponential(retries), MAX_WAIT_INTERVAL);
                System.out.print(String.format("Waiting: %d\n", waitTime));
                try {
                    Thread.sleep(waitTime);
                } catch (InterruptedException ex) {
                    // Thread exception, ignore
                }

            } while (retry && (retries++ < MAX_RETRIES));

        throw lastException;
    }

    public OAuthProvider retrieveAccessTokenWithRetry(OAuthProvider provider, OAuthConsumer consumer, String verifier)
            throws OAuthException {
        OAuthException lastException = null;
        int retries = 0;
        boolean success = false;
        boolean retry = false;
        do {
            try {
                provider.retrieveAccessToken(consumer, verifier);
                success = true;
            } catch (OAuthException e) {
                lastException = e;

            }

            if (success) {
                return provider;
            } else {
                retry = true;
            }

            // Wait to retry.
            long waitTime = Math.min(getWaitTimeExponential(retries), MAX_WAIT_INTERVAL);
            System.out.print(waitTime + "\n");
            try {
                Thread.sleep(waitTime);
            } catch (InterruptedException ex) {
                // Thread exception, ignore
            }

        } while (retry && (retries++ < MAX_RETRIES));

        throw lastException;
    }

    /*
     * Returns the next wait interval, in milliseconds, using an exponential
     * backoff algorithm.
     */
    public long getWaitTimeExponential(int retryCount) {

        long waitTime = ((long) Math.pow(2, retryCount) * 100L);

        return waitTime;
    }
}