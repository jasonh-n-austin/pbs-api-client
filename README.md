pbs-api-client
==============

API client/SDK for Paperbackswap.com

Built as a standalone Java library, usable in Android.
Builds with Gradle
Uses fluent syntax wherever possible

## Common Usage

### Build a URL from the know list
~~~
PbsUrlBuilder builder = PbsUrlBuilder
    .fromPath(PbsUrlInfo.MEMBER_WISH_LIST)
    .toString();
~~~

### Build a URL from a string

This is typically utilized when passing links from a response, e.g. 'ResultsNextSet'
~~~
PbsUrlBuilder builder = PbsUrlBuilder
    .fromUrl(nextPage)
    .toString();
~~~

### Explicitly set a page
~~~
PbsUrlBuilder builder = PbsUrlBuilder
    .fromPath(PbsUrlInfo.MEMBER_WISH_LIST)
    .withOffsetLimit(10, 10)
    .toString();
~~~

### Set an isbn in the URL
~~~
PbsUrlBuilder builder = PbsUrlBuilder
    .fromPath(PbsUrlInfo.ADD_TO_WISH_LIST)
    .withIsbn(0312851820)
    .toString();
~~~
