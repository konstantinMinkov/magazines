package com.kpi.magazines.utils.http;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import lombok.extern.log4j.Log4j2;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Konstantin Minkov on 27.07.2016.
 *
 * Provides static methods for HTTP calls.
 */

@Log4j2
public class Http {

    /**
     * POST request to external server.
     * @param urlString - url where the request should go.
     * @param data - any Object to be send as JSON to the server.
     * @return result of type JsonObject, or null in the case of external error.
     */
    public static JsonObject post(String urlString, Object data) {
        final URL url;
        HttpURLConnection urlConnection = null;
        final BufferedReader inputStream;
        final OutputStream outputStream;
        final Gson gson = new Gson();
        final StringBuilder builder = new StringBuilder();
        try {
            url = new URL(urlString);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setDoOutput(true);
            outputStream = urlConnection.getOutputStream();
            outputStream.write(gson.toJson(data).getBytes());
            outputStream.flush();
            if (urlConnection.getResponseCode() < 200 || urlConnection.getResponseCode() > 299) {
                new BufferedReader(new InputStreamReader(urlConnection.getErrorStream())).lines().forEach(log::error);
            }
            inputStream = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            inputStream.lines().forEach(builder::append);
        } catch (Exception e) {
            return null;
        }
        return gson.fromJson(builder.toString(), new TypeToken<JsonObject>(){}.getType());
    }

    /**
     * Makes GET request to external server with params.
     * @param urlString - url where the request should go with params.
     * @return result of type JsonObject, or null in the case of external error.
     */
    public static JsonObject get(String urlString) {
        final URL url;
        final HttpURLConnection urlConnection;
        final BufferedReader inputStream;
        final Gson gson = new Gson();
        final StringBuilder builder = new StringBuilder();
        try {
            url = new URL(urlString);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("Encoding", "UTF-8");
            urlConnection.connect();
            if (urlConnection.getResponseCode() < 200 || urlConnection.getResponseCode() > 299) {
                new BufferedReader(new InputStreamReader(urlConnection.getErrorStream())).lines().forEach(log::error);
            }
            inputStream = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), Charset.forName("UTF-8")));
            inputStream.lines().forEach(builder::append);
        } catch (Exception e) {
            return null;
        }
        return gson.fromJson(builder.toString(), new TypeToken<JsonObject>(){}.getType());
    }
}
