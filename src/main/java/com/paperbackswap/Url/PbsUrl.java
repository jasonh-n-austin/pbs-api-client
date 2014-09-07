package com.paperbackswap.Url;

import com.paperbackswap.data.RequestType;

import java.net.URI;

/**
 * List of supported paths in the PBS API
*/
@SuppressWarnings("UnusedDeclaration")
public enum PbsUrl {
    RECENTLY_POSTED(String.format("RequestType=%s", RequestType.RECENTLY_POSTED), Limit.ADD_LIMIT),
    MEMBER_WISH_LIST(String.format("RequestType=%s", RequestType.MEMBER_WISH_LIST), Limit.ADD_LIMIT),
    RECENTLY_SWAPPED(String.format("RequestType=%s", RequestType.RECENTLY_SWAPPED), Limit.ADD_LIMIT),
    TO_BE_READ(String.format("RequestType=%s", RequestType.TO_BE_READ), Limit.ADD_LIMIT),
    BOOK_SHELF(String.format("RequestType=%s", RequestType.BOOK_SHELF), Limit.ADD_LIMIT),
    CLUB_WISH_LIST(String.format("RequestType=%s&ISBN={isbn}", RequestType.CLUB_WISH_LIST), Limit.ADD_LIMIT),
    REQUESTS(String.format("RequestType=%s", RequestType.REQUESTS), Limit.ADD_LIMIT),
    ISBN_LIST(String.format("RequestType=%s&ISBN={isbn}", RequestType.ISBN_LIST), Limit.ADD_LIMIT),
    KEYWORD_SEARCH(String.format("RequestType=%s&Keyword={keyword}", RequestType.KEYWORD_SEARCH), Limit.ADD_LIMIT),
    MEMBER_DATA(String.format("RequestType=%s", RequestType.MEMBER_DATA)),
    MAIL_REQUEST(String.format("RequestType=%s&RequestID={requestId}", RequestType.MAIL_REQUEST)),
    ADD_TO_WISH_LIST(String.format("RequestType=%s&ISBN={isbn}", RequestType.ADD_TO_WISH_LIST)),
    POST_BOOK(String.format("RequestType=%s&ISBN={isbn}", RequestType.POST_BOOK)),
    REQUEST_BOOK(String.format("RequestType=%s&ISBN={isbn}", RequestType.REQUEST_BOOK));

    private String query;
    public Limit LIMIT;
    private final static String BASE_PATH = "/api/v2/index.php";

    private PbsUrl(String query, Limit addLimit) {
        this.query = query;
        this.LIMIT = addLimit;
    }

    private PbsUrl(String query) {
        this(query, Limit.NO_LIMIT);
    }

    public String getPath() {
        return BASE_PATH;
    }

    public String getQuery() {
        return query;
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

    public enum Limit { ADD_LIMIT, NO_LIMIT }

    //public static final String googleBookSearch = "http://books.google.com/books/feeds/volumes","q={query}&start-index={startIndex}&max-results={maxResults}";
    //public static final String amazonBookSearch = "http://api.bookmooch.com/api/search","db=amazon.com&txt={text}&page={page}&o=json";

}
