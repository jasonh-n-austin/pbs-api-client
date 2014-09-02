package com.paperbackswap.data;

import com.cedarsoftware.util.io.JsonReader;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class BookFactory {
    /**
     * Builds book object from json serialized with toJson()
     * @param json
     */
    public static Book fromJson (String json) throws IOException {
        InputStream inputStream = new ByteArrayInputStream(json.getBytes());
        JsonReader jr = new JsonReader(inputStream);
        return  (Book) jr.readObject();
    }
}
