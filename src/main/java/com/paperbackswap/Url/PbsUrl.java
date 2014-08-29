package com.paperbackswap.Url;

import java.net.URI;

/**
 * List of supported paths in the PBS API
*/
@SuppressWarnings("UnusedDeclaration")
public enum PbsUrl {
    RECENTLY_POSTED("/api/v2/index.php", "RequestType=RecentlyPosted", Limit.ADD_LIMIT),
    MEMBER_WISH_LIST("/api/v2/index.php", "RequestType=MemberWishList", Limit.ADD_LIMIT),
    RECENTLY_SWAPPED("/api/v2/index.php","RequestType=RecentlySwapped", Limit.ADD_LIMIT),
    TO_BE_READ("/api/v2/index.php","RequestType=TBRPile", Limit.ADD_LIMIT),
    BOOK_SHELF("/api/v2/index.php","RequestType=Bookshelf", Limit.ADD_LIMIT),
    CLUB_WISH_LIST("/api/v2/index.php","RequestType=ClubWishList&ISBN={isbn}", Limit.ADD_LIMIT),
    REQUESTS("/api/v2/index.php","RequestType=Requests", Limit.ADD_LIMIT),
    ISBN_LIST("/api/v2/index.php","RequestType=ISBNList&ISBN={isbn}", Limit.ADD_LIMIT),
    KEYWORD_SEARCH("/api/v2/index.php","RequestType=BookSearch&Keyword={keyword}", Limit.ADD_LIMIT),
    MEMBER_DATA("/api/v2/index.php","RequestType=MemberData"),
    MAIL_REQUEST("/api/v2/index.php","RequestType=MarkMailed&RequestID={requestId}"),
    ADD_TO_WISH_LIST("/api/v2/index.php","RequestType=AddWish&ISBN={isbn}"),
    POST_BOOK("/api/v2/index.php","RequestType=PostBook&ISBN={isbn}"),
    REQUEST_BOOK("/api/v2/index.php","RequestType=RequestBook&ISBN={isbn}");

    private String path;
    private String query;
    public Limit LIMIT;

    private PbsUrl(String path, String query, Limit addLimit) {
        this.path = path;
        this.query = query;
        this.LIMIT = addLimit;
    }

    private PbsUrl(String path, String query) {
        this(path, query, Limit.NO_LIMIT);
    }

    public String getPath() {
        return path;
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
