package com.paperbackswap.Test;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

import java.io.IOException;

public class TestDataLoader {
    public static JSONObject loadTestFileToJson(String path) throws IOException {
        String testFile = IOUtils.toString(
                Thread.currentThread().getContextClassLoader().getResourceAsStream(path),
                "UTF-8");
        return new JSONObject(testFile);
    }
}
