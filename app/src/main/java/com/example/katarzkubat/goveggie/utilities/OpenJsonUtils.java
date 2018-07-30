package com.example.katarzkubat.goveggie.utilities;

import com.example.katarzkubat.goveggie.model.JsonResults;
import com.google.gson.Gson;

public class OpenJsonUtils {

    public static JsonResults getRestaurantFromJson(String jsonRestaurant) {

        Gson gson = new Gson();

        JsonResults results = gson.fromJson(jsonRestaurant, JsonResults.class);

        return results;
    }
}

