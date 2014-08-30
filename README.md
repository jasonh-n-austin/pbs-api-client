pbs-api-client v1.2.1
==============

API client/SDK for Paperbackswap.com

Built as a standalone Java library, usable in Android.

Builds with Gradle.

Uses fluent syntax wherever possible (perhaps at a slight loss of efficiency as mikaelhg's UrlBuilder is final)

**Table of Contents**  *generated with [DocToc](http://doctoc.herokuapp.com/)*

- [Common Usage](#user-content-common-usage)
	- [Build URLs](#user-content-build-urls)
		- [Do the OAuth 1.0a dance](#user-content-do-the-oauth-10a-dance)
		- [Sign the request with saved token/secret](#user-content-sign-the-request-with-saved-tokensecret)
		- [Build a URL from the know list](#user-content-build-a-url-from-the-know-list)
		- [Build a URL from a string](#user-content-build-a-url-from-a-string)
		- [Explicitly set a page](#user-content-explicitly-set-a-page)
		- [Set an isbn in the URL](#user-content-set-an-isbn-in-the-url)
	- [Deserialize responses](#user-content-deserialize-responses)
		- [Turn response text/json into a list of books](#user-content-turn-response-textjson-into-a-list-of-books)
- [Acknowledgements](#user-content-acknowledgements)
	

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
    PbsUrlInfo.MEMBER_WISH_LIST.toString();
);
```

### Sign the request with saved token/secret
```java
PbsOauth oauth = PbsOauth("key", "secret", "token", "tokensecret");
String signedUrl = oauth.signRequest(
    PbsUrlInfo.MEMBER_WISH_LIST.toString()
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

In the event that you need to build your own URL
```java
PbsUrlBuilder builder = PbsUrlBuilder
    .fromUrl(url)
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
// Go fetch response with your favorite HTTP client stack => response
BookList bookList = BooksFactory.getBookList(response);
for (Book book : bookList) {
    System.out.println(book.getTitle());
}
```

### Get next page of results
This is useful when using HTTP, as PBS API automatically generates HTTPS URLs in paging elements. 
The getNextPage() method fixes this behavior, and maintains HTTP.
```
// Go fetch response with your favorite HTTP client stack => response
BookList bookList = BooksFactory.getBookList(response);
// Returns PbsUrlBuilder, so you can manipulate if needed
System.out.println(bookList.getNextPage().toString());
```

### Deserialize one book
```
// Extract one book's JSON => json

// Not typically used directly, this is used internally by getBookList
// Will not work correctly with PBS response
Book book = BooksFactory.getBook(json)
System.out.println(book.getTitle());
```


## Acknowledgements
Thanks to a few great Java libraries that made this much tidier
* [Signpost by mttkay](https://github.com/mttkay/signpost) for OAuth
* [Urlbuilder by mikaelhg](https://github.com/mikaelhg/urlbuilder) for manipulating URLs
* [Guava by Google](https://code.google.com/p/guava-libraries/) for dependency injection & testing