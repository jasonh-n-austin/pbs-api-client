package com.paperbackswap.Data;

import com.cedarsoftware.util.io.JsonWriter;
import com.google.inject.Inject;
import com.paperbackswap.Test.GuiceJUnitRunner;
import com.paperbackswap.data.Book;
import com.paperbackswap.data.BookFactory;
import com.paperbackswap.modules.BookModule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(GuiceJUnitRunner.class)
@GuiceJUnitRunner.GuiceModules({ BookModule.class })
public class BookTests {
    private Book mBook;

    @Inject
    public void inject_values(Book book) {
        mBook = book;
    }

    @Test
    public void can_serialize_book() throws IOException, ClassNotFoundException {
        mBook.setTitle("Blah");

        String json = JsonWriter.objectToJson(mBook);

        assertNotNull(json);

        Book book = BookFactory.fromJson(json);

        assertNotNull(book);
        assertEquals("Blah", book.getTitle());

    }
}
