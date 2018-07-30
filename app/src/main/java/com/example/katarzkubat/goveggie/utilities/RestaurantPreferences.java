package com.example.katarzkubat.goveggie.utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.preference.PreferenceManager;

import com.example.katarzkubat.goveggie.R;

public class RestaurantPreferences {

    public static String getChosenPreferences(Context context) {

        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);

        String keyOption = context.getString(R.string.option);
        String defaultOption = context.getString(R.string.vegetarian);

        return sharedPreferences.getString(keyOption, defaultOption);
    }
}
