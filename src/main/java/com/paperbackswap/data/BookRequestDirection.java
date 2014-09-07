package com.paperbackswap.data;

public enum BookRequestDirection {
    Incoming("Incoming"),
    Outgoing("Outgoing");

    String mText;
    BookRequestDirection(String text) {
        mText = text;
    }

    public static BookRequestDirection fromString(String direction) {
        if (direction.trim().equals(Incoming.toString())) return Incoming;
        if (direction.trim().equals(Outgoing.toString())) return Outgoing;
        return null;
    }

    public String toString() {
        return mText;
    }
}
