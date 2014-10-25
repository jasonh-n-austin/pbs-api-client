package com.paperbackswap.data;

import com.paperbackswap.exceptions.InvalidResponseException;
import org.json.JSONObject;

public class JsonBuilderBase {
    public JSONObject validateObject(Object response) throws InvalidResponseException {
        if (!(response instanceof JSONObject)) {
            throw new InvalidResponseException("Object provided is not a JSONObject");
        } else {
            return (JSONObject)response;
        }
    }
}
