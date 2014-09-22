package com.paperbackswap.Data;


import com.google.inject.Inject;
import com.paperbackswap.Test.GuiceJUnitRunner;
import com.paperbackswap.Test.TestDataLoader;
import com.paperbackswap.data.Book;
import com.paperbackswap.data.BookBuilder;
import com.paperbackswap.exceptions.BookListBuilderException;
import com.paperbackswap.exceptions.InvalidBookException;
import com.paperbackswap.modules.BookModule;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import static org.junit.Assert.*;

@RunWith(GuiceJUnitRunner.class)
@GuiceJUnitRunner.GuiceModules({ BookModule.class })
public class BookBuilderTests {
    private JSONObject testBookMultiAuthor;
    private JSONObject testBook;

    private BookBuilder bookBuilder;

    @Before
    public void setup() throws IOException {
        final String testBookFile = "test_book.json";
        testBook = TestDataLoader.loadTestFileToJson(testBookFile);
        assertNotNull(testBook);
    }

    @Inject
    public void setBuilders(BookBuilder bookBuilder) {
        this.bookBuilder = bookBuilder;
    }

    @Test
    public void builds_book() throws InvalidBookException, BookListBuilderException {
        Book book =  bookBuilder.construct(testBook);
        assertNotNull(book);
        assertEquals("9780756629946", book.getIsbn13());
        assertEquals("0756629942", book.getIsbn10());
        assertEquals("Dinosaurs!", book.getTitle());
        assertEquals("", book.getDescription());
        assertEquals(1, book.getAuthors().size());
        assertEquals("DK Publishing", book.getAuthors().get(0));
        assertEquals(4, book.getCoverImages().size());
        assertEquals("Hardcover", book.getBinding());
        assertEquals(0.0F, book.getRating(), 0.0);
        assertEquals("Active", book.getStatus());
        //assertEquals("http://www.amazon.com/gp/product/0756629942?SubscriptionId=0QCHRJVSKG6F3BRGBNG2&tag=paperbackswap-20&linkCode=xm2&camp=2025&creative=165953&creativeASIN=0756629942",
                //book.getBuyItNewLink();
    }

    // Not running this anymore, as it's use is no longer needed
    // PBS API fixed the bug with large images
    public void backfills_large_image() throws InvalidBookException, BookListBuilderException {
        Book book =  bookBuilder.construct(testBook);
        assertTrue(book.getCoverImages().containsValue("http://cd.pbsstatic.com/l/46/9946/9780756629946.jpg"));
    }

    @Test
    public void builds_book_multi_author() throws InvalidBookException, BookListBuilderException, IOException {
        final String testBookMultiAuthorFile = "test_book_multi-author.json";

        testBookMultiAuthor = TestDataLoader.loadTestFileToJson(testBookMultiAuthorFile);
        assertNotNull(testBookMultiAuthor);

        Book book =  bookBuilder.construct(testBookMultiAuthor);
        assertNotNull(book);
        assertEquals(book.getAuthors().size(), 2);
    }


}
