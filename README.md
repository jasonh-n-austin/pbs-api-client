pbs-api-client
==============

API client/SDK for Paperbackswap.com

Built as a standalone Java library, usable in Android.

Builds with Gradle.

Uses fluent syntax wherever possible (perhaps at a slight loss of efficiency as mikaelhg's UrlBuilder is final)

# Common Usage

## Build URLs

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
PbsUrlInfo.MEMBER_WISH_LIST.toString();
PbsUrlInfo.MEMBER_WISH_LIST.toUri();
PbsUrlInfo.MEMBER_WISH_LIST.toBuilder();

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
PbsUrlInfo.MEMBER_WISH_LIST.toBuilder()
    .withOffsetLimit(10, 10)
    .toString();
```

### Set an isbn in the URL
```java
PbsUrlInfo.ADD_TO_WISH_LIST.toBuilder()
    .withIsbn(0312851820)
    .toString();
```

## Deserialize responses
The section assumes a dependency on Guava for injection to construct book lists.

It should be noted that the BookListBuilder will process single book responses (e.g. RequestType=ISBNList when only one ISBN is provided) or a list of books. 
May not work properly with Requests at this point. 
Not every field is guaranteed to be deseralized (feel free to PR more).

### Turn response text/json into a list of books
```
@Inject
public void processResponse(BookListBuilder bookListBuilder) {
    // Go fetch response with your favorite HTTP client stack => response
    BookList bookList = bookListBuilder.construct(response));
    for (Book book : bookList) {
        System.out.println(book.getTitle);
    }
}

```


## Acknowledgements
Thanks to a few great Java libraries that made this much tidier
* [Signpost by mttkay](https://github.com/mttkay/signpost)
* [Urlbuilder by mikaelhg](https://github.com/mikaelhg/urlbuilder)
* [Guava by Google](https://code.google.com/p/guava-libraries/)