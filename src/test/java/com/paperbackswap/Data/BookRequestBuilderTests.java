package com.paperbackswap.Data;

import com.google.inject.Inject;
import com.paperbackswap.Test.GuiceJUnitRunner;
import com.paperbackswap.Test.TestDataLoader;
import com.paperbackswap.data.BookRequest;
import com.paperbackswap.data.BookRequestBuilder;
import com.paperbackswap.data.BookRequestDirection;
import com.paperbackswap.exceptions.*;
import com.paperbackswap.modules.BookModule;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.*;

@RunWith(GuiceJUnitRunner.class)
@GuiceJUnitRunner.GuiceModules({BookModule.class})
public class BookRequestBuilderTests {
    BookRequestBuilder bookRequestBuilder;
    JSONObject testBook;

    @Inject
    public void set_builders(BookRequestBuilder bookResponseBuilder) {
        this.bookRequestBuilder = bookResponseBuilder;
    }

    @Test
    public void builds_book() throws IOException, InvalidBookException, InvalidBooksResponseException, ResponseHasErrorsException, BookListBuilderException, ParseException, InvalidBookRequestException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date testDate = sdf.parse("2014-09-08 23:00:00");

        final String testBookFile = "test_book_request.json";
        testBook = TestDataLoader.loadTestFileToJson(testBookFile);
        assertNotNull(testBook);

        BookRequest request = bookRequestBuilder.construct(testBook);
        assertNotNull(request);
        assertEquals("bHo0TlpqaURxNk09", request.getId());
        assertEquals("Waiting for Other Member", request.getStatus());
        assertEquals("INWOOD,IA", request.getDestination());
        assertEquals(BookRequestDirection.Outgoing, request.getDirection());
        assertEquals(testDate, request.getMailDeadline());
        assertEquals("9781570671036", request.getBook().getIsbn13());
        assertTrue(request.getBook().getTitle().startsWith("Becoming Vegan"));
        assertEquals("Vesanto Melina", request.getBook().getAuthors().get(0));
    }
}
