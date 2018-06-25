package com.example.katarzkubat.goveggie.Utilities;

import android.net.Uri;
import android.util.Log;

import com.example.katarzkubat.goveggie.Model.Pictures;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class NetworkUtils {

    private static final String RESTAURANT_URL = "https://maps.googleapis.com/maps/api/place/";
    private static final String NEARBYSEARCH = "nearbysearch";
    private static final String JSON = "json";
    private static final String LOCATION = "location";
    private static final String RADIUS = "radius";
    private static final String TYPE = "type";
    private static final String KEYWORD = "keyword";

    private static final String PHOTO_PATH = "photo";
    private static final String PHOTOREFERENCE_PARAMETER = "photoreference";
    private static final String MAX_WIDTH = "maxwidth";

    private static final String API_KEY = "key";


    public static String getResponseFromHttpUrl(java.net.URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

    public static URL buildRestaurantUrl(String secretKey, String preference,
                                         double latitude, double longitude) {

        Uri builtRestaurantUri = Uri.parse(RESTAURANT_URL).buildUpon()
                .appendPath(NEARBYSEARCH)
                .appendPath(JSON)
                .appendQueryParameter(LOCATION, String.valueOf(latitude)+ "," + longitude)
                .appendQueryParameter(RADIUS, String.valueOf(1500))
                .appendQueryParameter(TYPE, "restaurant")
                .appendQueryParameter(KEYWORD, preference)
                .appendQueryParameter(API_KEY, secretKey)
                .build();

        URL movieUrl = null;
        try {
            movieUrl = new URL(builtRestaurantUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return movieUrl;
    }

    public static String getImageUrl(ArrayList<Pictures> photoreference, String secretKey) {
        Uri builtImageUri = Uri.parse(RESTAURANT_URL).buildUpon()
                .appendPath(PHOTO_PATH)
                .appendQueryParameter(MAX_WIDTH, "360")
                .appendQueryParameter(PHOTOREFERENCE_PARAMETER, String.valueOf(photoreference))
                .appendQueryParameter(API_KEY, secretKey)
                .build();
        return builtImageUri.toString();

    }


}
