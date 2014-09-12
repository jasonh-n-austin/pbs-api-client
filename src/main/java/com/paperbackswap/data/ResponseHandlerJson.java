package com.paperbackswap.data;

import com.google.inject.Inject;
import com.paperbackswap.exceptions.ResponseHasErrorsException;
import com.paperbackswap.exceptions.InvalidBooksResponseException;
import com.paperbackswap.exceptions.InvalidResponseException;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Processes the response wrapper for all PBS API responses
 */
public class ResponseHandlerJson implements ResponseHandler {
    private PbsResponse pbsResponse;

    @Inject
    public ResponseHandlerJson(PbsResponse pbsResponse) {
        this.pbsResponse = pbsResponse;
    }

    @Override
    public PbsResponse construct(Object response) throws ResponseHasErrorsException, InvalidResponseException {
        {
            JSONObject responseJson = null;
            if (response == null || !(response instanceof JSONObject)) {
                throw new InvalidResponseException("Object provided is not a valid JSONObject");
            } else {
                responseJson = (JSONObject) response;
            }

            JSONObject responseObject;
            try {
                responseObject = responseJson.getJSONObject("Response");
            } catch (JSONException e) {
                throw new InvalidResponseException("Response object did not contain 'Response' element");
            }
            if (responseObject.has("error")) {
                throw new ResponseHasErrorsException(String.format("Error:%s", responseObject.getString("error")));
            }
            pbsResponse.setRequestType(RequestType.fromString(responseObject.optString("RequestType")));
            pbsResponse.setResponse(responseObject);

            return pbsResponse;
        }
    }

    public PbsResponse getResponse() {
        return pbsResponse;
    }
}
