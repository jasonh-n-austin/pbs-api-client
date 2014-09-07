package com.paperbackswap.Url;


public enum  PbsWebUrl {
    ACCOUNT_MAIN("http://www.paperbackswap.com/members/index.php"),
    BOOKS_I_REQUESTED("http://www.paperbackswap.com/members/index.php?#t=books_i_requested"),
    FROM_TO_MAIL("http://www.paperbackswap.com/members/index.php?#t=from_to_mail"),
    PRINT_POSTAGE("http://www.paperbackswap.com/members/wrapper/index.php?r={requestId}&b=0&o_r=0");

    private String text;

    private PbsWebUrl(String uri) {
        text = uri;
    }

    @Override
    public String toString() {
        return text;
    }

}
