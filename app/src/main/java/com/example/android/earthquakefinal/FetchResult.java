package com.example.android.earthquakefinal;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by LUCIFER on 01-02-2018.
 */

public class FetchResult {
    private final static String TAG = "FetchResults";
    private static ArrayList<Earthquake> earthquakes;

    public static ArrayList<Earthquake> fetchData(String url_string) {
        Log.v(TAG,"fetchData() called");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        earthquakes = new ArrayList<>();
        URL url = createURL(url_string);
        String json = makeHTTPConnection(url);
        extractFromJson(json);
        return earthquakes;
    }

    private static void extractFromJson(String json) {
        if (json == null) {
            Log.d(TAG, "Empty Json File");
            return;
        }
        try {
            JSONObject root = new JSONObject(json);
            JSONArray features = root.optJSONArray("features");
            if(features.length()<1){
                Log.d(TAG,"JSON features empty");
                return;
            }
            for(int i=0;i<features.length();i++) {
                JSONObject featureObject = features.optJSONObject(i);
                JSONObject properties = featureObject.optJSONObject("properties");
                earthquakes.add(new Earthquake(properties.optDouble("mag")
                        , properties.optString("place")
                        , properties.optString("url"), new Date(properties.optLong("time"))));

            }
        } catch (JSONException e) {
            Log.e(TAG, "JSON Exception caught");
        }
    }

    private static URL createURL(String string) {
        URL url = null;
        try {
            url = new URL(string);
        } catch (MalformedURLException e) {
            Log.e(TAG, "Unable to create URL object", e);
        } finally {
            return url;
        }
    }

    private static String makeHTTPConnection(URL url) {
        String json = new String();
        if (url == null)
            return null;
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(10000);
            urlConnection.connect();
            inputStream = urlConnection.getInputStream();
            json = parseIntoJson(inputStream);
        } catch (IOException e) {

        } finally {
            if (urlConnection != null)
                urlConnection.disconnect();
            if (inputStream != null)
                try {
                    inputStream.close();
                } catch (IOException e) {
                    Log.e(TAG, "Error in closing the inputStream", e);
                }
            return json;
        }
    }

    private static String parseIntoJson(InputStream inputStream) {
        String line;
        StringBuilder stringBuilder = new StringBuilder();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
        BufferedReader reader = new BufferedReader(inputStreamReader);
        try {
            line = reader.readLine();
            while (line != null) {
                stringBuilder.append(line);
                line = reader.readLine();
            }
        } catch (IOException e) {
            Log.e(TAG, "Unable to read from the Buffered Reader ", e);
        } finally {
            return stringBuilder.toString();
        }
    }
}
