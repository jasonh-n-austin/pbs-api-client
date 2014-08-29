package com.paperbackswap.Data;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

import java.io.IOException;

public class TestDataLoader {
    public static JSONObject loadTestFileToJson(String path) throws IOException {
        // Loads test booklist with 7 items
        String testFile = IOUtils.toString(
                Thread.currentThread().getContextClassLoader().getResourceAsStream(path),
                "UTF-8");
        return new JSONObject(testFile);
    }
}
