package com.paperbackswap.Url;

import com.paperbackswap.data.RequestType;
import gumi.builders.UrlBuilder;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * List of supported paths in the PBS API
*/
@SuppressWarnings("UnusedDeclaration")
public enum PbsUrl {
    RECENTLY_POSTED("RequestType=%s", RequestType.RECENTLY_POSTED, Limit.ADD_LIMIT),
    MEMBER_WISH_LIST("RequestType=%s", RequestType.MEMBER_WISH_LIST, Limit.ADD_LIMIT),
    RECENTLY_SWAPPED("RequestType=%s", RequestType.RECENTLY_SWAPPED, Limit.ADD_LIMIT),
    TO_BE_READ("RequestType=%s", RequestType.TO_BE_READ, Limit.ADD_LIMIT),
    BOOK_SHELF("RequestType=%s", RequestType.BOOK_SHELF, Limit.ADD_LIMIT),
    CLUB_WISH_LIST("RequestType=%s&ISBN={isbn}", RequestType.CLUB_WISH_LIST, Limit.ADD_LIMIT),
    REQUESTS("RequestType=%s", RequestType.REQUESTS, Limit.ADD_LIMIT),
    ISBN_LIST("RequestType=%s&ISBN={isbn}", RequestType.ISBN_LIST, Limit.ADD_LIMIT),
    KEYWORD_SEARCH("RequestType=%s&Keyword={keyword}", RequestType.KEYWORD_SEARCH, Limit.ADD_LIMIT),
    MEMBER_DATA("RequestType=%s", RequestType.MEMBER_DATA),
    MAIL_REQUEST("RequestType=%s&RequestID={requestId}", RequestType.MAIL_REQUEST),
    ADD_TO_WISH_LIST("RequestType=%s&ISBN={isbn}", RequestType.ADD_TO_WISH_LIST),
    POST_BOOK("RequestType=%s&ISBN={isbn}", RequestType.POST_BOOK),
    REQUEST_BOOK("RequestType=%s&ISBN={isbn}", RequestType.REQUEST_BOOK);

    private String query;
    public Limit LIMIT;
    private final static String BASE_PATH = "/api/v2/index.php";
    private RequestType requestType;

    private PbsUrl(String query, RequestType requestType, Limit addLimit) {
        this.query = String.format(query, requestType);
        this.LIMIT = addLimit;
        this.requestType = requestType;
    }

    private PbsUrl(String query, RequestType requestType) {
        this(query, requestType, Limit.NO_LIMIT);
    }

    public String getPath() {
        return BASE_PATH;
    }

    public String getQuery() {
        return query;
    }

    public RequestType getRequestType() {
        return requestType;
    }

    public String toString() {
        return toBuilder().toString();
    }

    public PbsUrlBuilder toBuilder() {
        return PbsUrlBuilder.fromPath(this);
    }

    public URI toUri() {
        return PbsUrlBuilder.fromPath(this).toUri();
    }

    /**
     * Builds PbsUri from url
     * This only respects RequestType in building a fresh Url; don't trust it for more than that
     * @param url
     * @return
     */
    public static PbsUrl fromString(String url) {
        UrlBuilder builder = UrlBuilder.fromString(url);
        String requestType = builder.queryParameters.get("RequestType").get(0);

        return fromRequestType(RequestType.fromString(requestType));
    }

    /**
     * Builds PbsUri from a specific request type
     * @param requestType
     * @return
     */
    public static PbsUrl fromRequestType(RequestType requestType) {
        for (PbsUrl u : PbsUrl.values()) {
            if (requestType.equals(u.getRequestType())) {
                return u;
            }
        }
        return null;
    }

    public enum Limit { ADD_LIMIT, NO_LIMIT }

    //public static final String googleBookSearch = "http://books.google.com/books/feeds/volumes","q={query}&start-index={startIndex}&max-results={maxResults}";
    //public static final String amazonBookSearch = "http://api.bookmooch.com/api/search","db=amazon.com&txt={text}&page={page}&o=json";

}
