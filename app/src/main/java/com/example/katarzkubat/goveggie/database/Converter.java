package com.example.katarzkubat.goveggie.database;

import android.arch.persistence.room.TypeConverter;

import com.example.katarzkubat.goveggie.model.Locationinfo;
import com.example.katarzkubat.goveggie.model.OpeningHours;
import com.example.katarzkubat.goveggie.model.Pictures;
import com.example.katarzkubat.goveggie.model.Placeinfo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Converter {
    @TypeConverter
    public static Pictures toPictures(String reference) {
        return reference == null ? null : new Pictures(reference);
    }

    @TypeConverter
    public static String toReference(Pictures pictures) {
        return pictures == null ? null : pictures.getPhoto_reference();
    }

    @TypeConverter
    public static Placeinfo placeFromString(String latlng) {
        if (latlng != null) {
            List<String> list = Arrays.asList(latlng.split(";"));
            String lat = list.get(0);
            String lng = list.get(1);

            return new Placeinfo(new Locationinfo(Double.parseDouble(lat), Double.parseDouble(lng)));
        }
        return null;
    }

    @TypeConverter
    public static String toPlaceString(Placeinfo placeinfo) {
        return placeinfo == null ? null : placeinfo.toString();
    }

    @TypeConverter
    public static String toPhotoString(ArrayList<Pictures> pictures) {
        if (pictures != null) {
            Gson gson = new Gson();
            return gson.toJson(pictures);
        }
        return null;
    }

    @TypeConverter
    public static ArrayList<Pictures> picturesFromString(String reference) {
        if (reference != null) {
            Type listType = new TypeToken<ArrayList<Pictures>>() {
            }.getType();
            return new Gson().fromJson(reference, listType);
        }
        return null;
    }

    @TypeConverter
    public static OpeningHours openingFromString(String opening) {
        return new OpeningHours(opening.equals("true") ? true : false);
    }

    @TypeConverter
    public static String toOpeningString(OpeningHours openingHours) {
        return String.valueOf(openingHours == null ? false : openingHours.getOpen_now());
    }
}
