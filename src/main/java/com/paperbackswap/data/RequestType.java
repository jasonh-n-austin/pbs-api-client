package com.paperbackswap.data;

public enum RequestType {
    RECENTLY_POSTED("RecentlyPosted"),
    MEMBER_WISH_LIST("MemberWishList"),
    RECENTLY_SWAPPED("RecentlySwapped"),
    TO_BE_READ("TBRPile"),
    BOOK_SHELF("Bookshelf"),
    CLUB_WISH_LIST("ClubWishList"),
    REQUESTS("Requests"),
    BOOKS_READ("BooksRead"),
    ALTERNATE_ADDRESSES("AlternateAddresses"),
    ISBN_LIST("ISBNList"),
    KEYWORD_SEARCH("BookSearch"),
    MEMBER_DATA("MemberData"),
    MAIL_REQUEST("MarkMailed"),
    ADD_TO_WISH_LIST("AddWish"),
    POST_BOOK("PostBook"),
    REQUEST_BOOK("RequestBook");

    private String text;

    private RequestType(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }

    public static RequestType fromString(String text) {
        if (text != null) {
            for (RequestType b : RequestType.values()) {
                if (text.equalsIgnoreCase(b.text)) {
                    return b;
                }
            }
        }
        return null;
    }

}
