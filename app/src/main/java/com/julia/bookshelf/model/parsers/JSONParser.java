package com.julia.bookshelf.model.parsers;

import android.util.Log;

import com.julia.bookshelf.model.data.Book;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Julia on 02.01.2015.
 */
public class JSONParser {
    public static ArrayList<Book> parseBooks(String json) {
        ArrayList<Book> bookList = new ArrayList<>();
        try {
            JSONObject reader = new JSONObject(json);
            JSONArray jsonArray = reader.getJSONArray("results");
            Book book;
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                book = new Book();
                book.setTitle(jsonObject.getString("title"));
                book.setAuthor(jsonObject.getString("author"));
                book.setCover(jsonObject.getString("cover"));
                book.setGenre(jsonObject.getString("genre"));
                book.setAnnotation(jsonObject.getString("annotation"));
                book.setFavourite(jsonObject.getBoolean("isFavourite"));
                bookList.add(book);
            }
        } catch (JSONException e) {
            Log.w("BOOKSHELF", e.toString());
        }
        return bookList;
    }

}
