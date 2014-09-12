package com.paperbackswap.data;

import org.json.JSONObject;

public class PbsResponseJson implements PbsResponse {
    private RequestType requestType;
    private JSONObject response;

    @Override
    public RequestType getRequestType() {
        return requestType;
    }

    @Override
    public void setRequestType(RequestType requestType) {
        this.requestType = requestType;
    }

    @Override
    public JSONObject getResponse() {
        return response;
    }

    @Override
    public void setResponse(JSONObject response) {
        this.response = response;
    }
}
