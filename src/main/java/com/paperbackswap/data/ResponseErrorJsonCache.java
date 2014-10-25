package com.paperbackswap.data;

import com.google.inject.Inject;
import com.paperbackswap.exceptions.InvalidResponseException;
import org.json.JSONObject;

public class ResponseErrorJsonCache extends JsonBuilderBase implements ResponseError {
    private String error;
    private String message;
    private String code;

    @Inject
    ResponseErrorJsonCache() {

    }

    ResponseErrorJsonCache(String error, String message, String code) {
        this.error = error;
        this.message = message;
        this.code = code;
    }


    @Override
    public String toString() {
        return String.format("%s: %s", getCode(), getMessage());
    }

    @Override
    public ResponseError fromResponse(Object response) throws InvalidResponseException {
        JSONObject responseJson = validateObject(response);
        String error = responseJson.optString("error");
        String message = responseJson.optString("message");
        String code = responseJson.optString("code");
        return new ResponseErrorJsonCache(error, message, code);
    }

    @Override
    public String getError() {
        return error;
    }
    @Override
    public String getMessage() {
        return message;
    }
    @Override
    public String getCode() {
        return code;
    }
}
