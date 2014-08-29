pbs-api-client
==============

API client/SDK for Paperbackswap.com

Built as a standalone Java library, usable in Android.
Builds with Gradle
Uses fluent syntax wherever possible

## Common Usage

### Do the OAuth 1.0a dance
```java
PbsOauth oauth = PbsOauth("key", "secret");
String requestTokenUrl = oauth.getRequestTokenUrl("http://localhost:8080/capture_verifier")
System.out.println(oauth.isAuthorizing()); // "true"

// Go do things in the browser with requestTokenUrl, capture verifier from callback

oauth.retrieveAccessToken(verifier)
System.out.println(oauth.isAuthorizing()); // "false"

String signedUrl = oauth.signRequest(
    PbsUrlBuilder builder = PbsUrlBuilder
        .fromPath(PbsUrlInfo.MEMBER_WISH_LIST)
        .toString();
        );
```

### Sign the request with saved token/secret
```java
PbsOauth oauth = PbsOauth("key", "secret", "token", "tokensecret");
String signedUrl = oauth.signRequest(
    PbsUrlBuilder builder = PbsUrlBuilder
        .fromPath(PbsUrlInfo.MEMBER_WISH_LIST)
        .toString();
        );
```

### Build a URL from the know list
```java
PbsUrlBuilder builder = PbsUrlBuilder
    .fromPath(PbsUrlInfo.MEMBER_WISH_LIST)
    .toString();
```

### Build a URL from a string

This is typically utilized when passing links from a response, e.g. 'ResultsNextSet'
```java
PbsUrlBuilder builder = PbsUrlBuilder
    .fromUrl(nextPage)
    .toString();
```

### Explicitly set a page
```java
PbsUrlBuilder builder = PbsUrlBuilder
    .fromPath(PbsUrlInfo.MEMBER_WISH_LIST)
    .withOffsetLimit(10, 10)
    .toString();
```

### Set an isbn in the URL
```java
PbsUrlBuilder builder = PbsUrlBuilder
    .fromPath(PbsUrlInfo.ADD_TO_WISH_LIST)
    .withIsbn(0312851820)
    .toString();
```

## Thanks
Acknowledgements to a few great Java libraries that made this much tidier
* [Signpost by mttkay](https://github.com/mttkay/signpost)
* [Urlbuilder by mikaelhg](https://github.com/mikaelhg/urlbuilder)