package com.paperbackswap;


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
    }
}
