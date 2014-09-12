package com.paperbackswap.data;

import org.json.JSONObject;

public interface PbsResponse {
    public RequestType getRequestType();
    public void setRequestType(RequestType requestType);
    public JSONObject getResponse();
    public void setResponse(JSONObject response);
}
