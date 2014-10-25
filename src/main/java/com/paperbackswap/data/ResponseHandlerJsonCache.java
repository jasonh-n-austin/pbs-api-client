package com.paperbackswap.data;

import com.google.inject.Inject;
import com.paperbackswap.exceptions.InvalidResponseException;
import com.paperbackswap.exceptions.ResponseHasErrorsException;
import org.json.JSONObject;

/**
 * Processes the response wrapper for all PBS API responses
 */
public class ResponseHandlerJsonCache extends JsonBuilderBase implements ResponseHandler {
    private PbsResponse pbsResponse;
    private ResponseError mError;

    @Inject
    public ResponseHandlerJsonCache(PbsResponse pbsResponse, ResponseError error) {
        this.pbsResponse = pbsResponse;
        this.mError = error;
    }

    @Override
    public PbsResponse construct(Object response, int statusCode) throws ResponseHasErrorsException, InvalidResponseException {
        {
            JSONObject responseJson = validateObject(response);

            if (statusCode >= 200 && statusCode < 300) {
                return processResponse(responseJson);
            } else if (statusCode >= 300) {
                ResponseError error = mError.fromResponse(response);
                throw new ResponseHasErrorsException(error.toString());
            } else {
                throw new InvalidResponseException(String.format("Invalid HTTP status: %s", statusCode));
            }
        }
    }

    PbsResponse processResponse(JSONObject responseJson) throws ResponseHasErrorsException {
        if (responseJson.has("error")) {
            String error = responseJson.getString("error");
            String code = responseJson.optString("code");
            String message = responseJson.optString("message");
            throw new ResponseHasErrorsException(String.format("%s(%s): %s",
                    error, code, message));
        }
        pbsResponse.setRequestType(RequestType.fromString(responseJson.optString("RequestType")));
        pbsResponse.setResponse(responseJson);
        return pbsResponse;
    }

    public PbsResponse getResponse() {
        return pbsResponse;
    }
}
