package com.paperbackswap.Data;

import com.google.inject.Inject;
import com.paperbackswap.Test.GuiceJUnitRunner;
import com.paperbackswap.Test.TestDataLoader;
import com.paperbackswap.data.BookRequest;
import com.paperbackswap.data.BookRequestBuilder;
import com.paperbackswap.data.BookRequestDirection;
import com.paperbackswap.exceptions.*;
import com.paperbackswap.modules.BookModuleCache;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import static org.junit.Assert.*;

@RunWith(GuiceJUnitRunner.class)
@GuiceJUnitRunner.GuiceModules({BookModuleCache.class})
public class BookRequestBuilderTests {
    BookRequestBuilder bookRequestBuilder;
    JSONObject testBook;

    @Inject
    public void set_builders(BookRequestBuilder bookResponseBuilder) {
        this.bookRequestBuilder = bookResponseBuilder;
    }

    @Test
    public void builds_book() throws IOException, InvalidBookException, InvalidBooksResponseException, ResponseHasErrorsException, BookListBuilderException, ParseException, InvalidBookRequestException, InvalidResponseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date testDate = sdf.parse("2014-10-24T22:47:38+00:00");

        final String testBookFile = "test_book_request_cache.json";
        testBook = TestDataLoader.loadTestFileToJson(testBookFile);
        assertNotNull(testBook);

        BookRequest request = bookRequestBuilder.construct(testBook);
        assertNotNull(request);
        assertEquals("bDBPVkRteGxKSXM9", request.getId());
        assertEquals("Waiting for Other Member", request.getStatus());
        assertEquals("BRIARCLIFF,TX", request.getDestination());
        assertEquals(BookRequestDirection.Incoming, request.getDirection());
        assertEquals(testDate, request.getMailDeadline());
        assertEquals("9780060518509", request.getBook().getIsbn());
        assertTrue(request.getBook().getTitle().startsWith("Manhunt:"));
        assertEquals("James L. Swanson", request.getBook().getAuthors().get(0));
    }
}
