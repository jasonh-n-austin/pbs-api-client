package com.paperbackswap.data;

import com.cedarsoftware.util.io.JsonReader;
import com.paperbackswap.Url.PbsUrlBuilder;
import org.omg.CORBA.Request;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BookList extends ArrayList<Book> {
	private static final long serialVersionUID = -8227039917486379533L;
	private final PbsUrlBuilder nextPage;
    private final RequestType requestType;

	public BookList(List<Book> source, PbsUrlBuilder nextPage, RequestType requestType) {
		super(source);
		this.nextPage = nextPage;
        this.requestType = requestType;
	}

	@SuppressWarnings("UnusedDeclaration")
    public PbsUrlBuilder getNextPage() {
		return nextPage;
	}

    public ArrayList<String> getJson() throws IOException {
        ArrayList<String> result = new ArrayList<String>();
        Iterator<Book> iterator = iterator();
        while (iterator.hasNext()) {
            result.add(iterator.next().toJson());
        }
        return result;
    }

    public static BookList fromJson(List<String> jsonList) throws IOException {
        ArrayList<Book> list = new ArrayList<Book>();
        for (String json : jsonList) {
            InputStream inputStream = new ByteArrayInputStream(json.getBytes());
            JsonReader jr = new JsonReader(inputStream);
            Book book = (Book) jr.readObject();
            list.add(book);
        }
        return new BookList(list, null, null);
    }

    public RequestType getRequestType() {
        return requestType;
    }
}
