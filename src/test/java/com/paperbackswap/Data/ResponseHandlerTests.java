package com.paperbackswap.Data;

import com.google.inject.Inject;
import com.paperbackswap.Test.GuiceJUnitRunner;
import com.paperbackswap.Test.TestDataLoader;
import com.paperbackswap.data.PbsResponse;
import com.paperbackswap.data.RequestType;
import com.paperbackswap.data.ResponseHandler;
import com.paperbackswap.exceptions.*;
import com.paperbackswap.modules.BookModule;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(GuiceJUnitRunner.class)
@GuiceJUnitRunner.GuiceModules({BookModule.class})
public class ResponseHandlerTests {
    private ResponseHandler responseHandler;

    @Inject
    public void setBuilders(ResponseHandler responseHandler) {
        this.responseHandler = responseHandler;
    }

    @Test
    public void creates_valid_response() throws IOException, InvalidResponseException, ResponseHasErrorsException {
        final String testFile = "test_books.json";
        JSONObject testData = TestDataLoader.loadTestFileToJson(testFile);

        PbsResponse pbsResponse = responseHandler.construct(testData);
        assertNotNull(pbsResponse);

        assertTrue(pbsResponse.getResponse() != null);
        assertEquals(RequestType.BOOK_SHELF, pbsResponse.getRequestType());
    }

    @Test
    public void creates_valid_member_response() throws IOException, InvalidResponseException, ResponseHasErrorsException {
        final String testFile = "test_member_data.json";
        JSONObject testData = TestDataLoader.loadTestFileToJson(testFile);

        PbsResponse pbsResponse = responseHandler.construct(testData);
        assertNotNull(pbsResponse);

        assertTrue(pbsResponse.getResponse() != null);
    }

    @Test(expected = InvalidResponseException.class)
    public void exception_on_bad_response() throws InvalidResponseException, ResponseHasErrorsException {
        responseHandler.construct(new JSONObject("{}"));
    }

    @Test(expected = InvalidResponseException.class)
    public void exception_on_bad_json_response() throws InvalidResponseException, ResponseHasErrorsException {
        responseHandler.construct("");
    }
}
