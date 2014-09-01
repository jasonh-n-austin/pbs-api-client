package com.paperbackswap.Url;


public enum PbsUrlParams {
    ISBN {
        public String toString() {
            return "ISBN";
        }
    },
    LIMIT {
        public String toString() {
            return "Limit";
        }
    },
    KEYWORD {
        public String toString() {
            return "Keyword";
        }
    }
}
