package com.paperbackswap;

import gumi.builders.UrlBuilder;

/**
 * List of supported paths in the paperbackswap.com API
*/
public enum PbsUrlInfo {
    DEFAULT_SCHEME {
        public String toString() {
            return "http";
        }
    },
    DEFAULT_HOST {
        public String toString() {
            return "www.paperbackswap.com";
        }
    },
    ISBN_KEY {
        public String toString() {
            return "ISBN";
        }
    },
    LIMIT_KEY {
        public String toString() {
            return "Limit";
        }
    },
    /**
     * Returns fully formed URL with http/www URL
     */
    REQUEST_TOKEN {
        public String toString() {
            return UrlBuilder.empty()
                    .withHost(DEFAULT_HOST.toString())
                    .withScheme(DEFAULT_SCHEME.toString())
                    .withPath("/api/request_token.php")
                    .toString();
        }
    },
    /**
     * Returns fully formed URL with http/www URL
     */
    AUTHORIZE {
        public String toString() {
            return UrlBuilder.empty()
                    .withHost(DEFAULT_HOST.toString())
                    .withScheme(DEFAULT_SCHEME.toString())
                    .withPath("/api/authorize.php")
                    .toString();
        }
    },
    /**
     * Returns fully formed URL with http/www URL
     */
    ACCESS_TOKEN {
        public String toString() {
            return UrlBuilder.empty()
                    .withHost(DEFAULT_HOST.toString())
                    .withScheme(DEFAULT_SCHEME.toString())
                    .withPath("/api/access_token.php")
                    .toString();
        }
    },
    RECENTLY_POSTED {
        public String toString() {
            return "/api/v2/index.php?RequestType=RecentlyPosted";
        }
    },
    MEMBER_WISH_LIST {
        public String toString() {
            return "/api/v2/index.php?RequestType=MemberWishList";
        }
    },
    RECENTLY_SWAPPED {
        public String toString() {
            return "/api/v2/index.php?RequestType=RecentlySwapped";
        }
    },
    MEMBER_WISH_ISBN {
        public String toString() {
            return "/api/v2/index.php?RequestType=ClubWishList&ISBN={isbn}";
        }
    },
    TO_BE_READ {
        public String toString() {
            return "/api/v2/index.php?RequestType=TBRPile";
        }
    },
    BOOK_SHELF {
        public String toString() {
            return "/api/v2/index.php?RequestType=Bookshelf";
        }
    },
    CLUB_WISH_LIST {
        public String toString() {
            return "/api/v2/index.php?RequestType=ClubWishList&ISBN={isbn}";
        }
    },
    MEMBER_DATA {
        public String toString() {
            return "/api/v2/index.php?RequestType=MemberData";
        }
    },
    REQUESTS {
        public String toString() {
            return "/api/v2/index.php?RequestType=Requests";
        }
    },
    MAIL_REQUEST {
        public String toString() {
            return "/api/v2/index.php?RequestType=MarkMailed&RequestID={requestId}";
        }
    },
    ADD_TO_WISH_LIST {
        public String toString() {
            return "/api/v2/index.php?RequestType=AddWish&ISBN={isbn}";
        }
    },
    POST_BOOK {
        public String toString() {
            return "/api/v2/index.php?RequestType=PostBook&ISBN={isbn}";
        }
    },
    REQUEST_BOOK {
        public String toString() {
            return "/api/v2/index.php?RequestType=RequestBook&ISBN={isbn}";
        }
    },
    ISBN_LIST {
        public String toString() {
            return "/api/v2/index.php?RequestType=ISBNList&ISBN={isbn}";
        }
    },
    KEYWORD_SEARCH {
        public String toString() {
            return "/api/v2/index.php?RequestType=BookSearch&Keyword={keyword}";
        }
    }

    //public static final String googleBookSearch = "http://books.google.com/books/feeds/volumes?q={query}&start-index={startIndex}&max-results={maxResults}";
    //public static final String amazonBookSearch = "http://api.bookmooch.com/api/search?db=amazon.com&txt={text}&page={page}&o=json";

}