package com.paperbackswap.Url;

public enum Defaults {
    SCHEME {
        public String toString() {
            return "http";
        }
    },
    HOST {
        public String toString() {
            return "www.paperbackswap.com";
        }
    },
    DATE_FORMAT {
        public String toString() {
            return "yyyy-MM-dd HH:mm:ss";
        }
    }
}