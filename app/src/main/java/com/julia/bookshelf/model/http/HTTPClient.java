package com.julia.bookshelf.model.http;

import android.util.Log;

import org.apache.http.HttpStatus;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Julia on 09.01.2015.
 */
public class HTTPClient {

    public static HTTPResponse get(String path) {
        BufferedReader in = null;
        StringBuilder stringBuilder = null;
        int responseCode = 0;
        try {
            URL url = new URL(path);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            addHeaders(connection);
            connection.connect();
            responseCode = connection.getResponseCode();
            if(responseCode == HttpStatus.SC_OK) {
                in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                stringBuilder = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    stringBuilder.append(inputLine);
                }
            }
        } catch (IOException e) {
            Log.w("BOOKSHELF", e.toString());
        } finally {
            closeStream(in);
        }
        String json = stringBuilder != null ? stringBuilder.toString() : null;
        return new HTTPResponse(responseCode,json);
    }

    private static void addHeaders(URLConnection connection) {
        connection.setRequestProperty(Keys.APPlCATION_ID_KEY, Keys.APPlCATION_ID_VALUE);
        connection.setRequestProperty(Keys.REST_API_KEY, Keys.REST_API_VALUE);
    }

    private static void closeStream(BufferedReader in) {
        if (in != null) {
            try {
                in.close();
            } catch (IOException e) {
                Log.w("BOOKSHELF", e.toString());
            }
        }
    }

    public static void post(String path, JSONObject jsonObject) {
        try {
            URL url = new URL(path);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            addHeaders(connection);
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("charset", "utf-8");
//            connection.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
            DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
            wr.writeBytes(jsonObject.toString());
            int responseCode = connection.getResponseCode();
            Log.i("BOOKSHELF", "Response:" + responseCode);
        } catch (IOException e) {
            Log.w("BOOKSHELF", e.toString());
        }
    }

}
