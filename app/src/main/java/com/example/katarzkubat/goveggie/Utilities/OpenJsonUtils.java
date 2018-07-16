package com.example.katarzkubat.goveggie.Utilities;

import android.util.Log;

import com.example.katarzkubat.goveggie.Model.JsonResults;
import com.google.gson.Gson;

public class OpenJsonUtils {

    public static JsonResults getRestaurantFromJson(String jsonRestaurant) {

        Gson gson = new Gson();

        JsonResults results = gson.fromJson(jsonRestaurant, JsonResults.class);
        Log.d("JSONRESULT", String.valueOf(results.getResults().size()));

        return results;
    }
}

