package com.example.katarzkubat.goveggie.Utilities;

import android.util.Log;

import com.example.katarzkubat.goveggie.Model.JsonResults;
import com.example.katarzkubat.goveggie.Model.Restaurant;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import java.util.ArrayList;
import java.util.Arrays;

public class OpenJsonUtils {

    public static JsonResults getRestaurantFromJson(String jsonRestaurant) {

        Gson gson = new Gson();

        JsonResults results = gson.fromJson(jsonRestaurant, JsonResults.class);
        Log.d("JSONRESULT", String.valueOf(results.getResults().size()));

        return results;
    }
}

