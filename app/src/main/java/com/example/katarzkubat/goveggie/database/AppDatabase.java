package com.example.katarzkubat.goveggie.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import com.example.katarzkubat.goveggie.model.Favorites;
import com.example.katarzkubat.goveggie.model.Restaurant;

@Database(entities = {Restaurant.class, Favorites.class}, version = 9, exportSchema = false)
@TypeConverters({Converter.class})
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase mDataBaseInstance;
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "restaurantlist";
    private static final String LOG_TAG = AppDatabase.class.getSimpleName();

    public static AppDatabase getInstance(Context context) {
        if (mDataBaseInstance == null) {
            synchronized (LOCK) {
                mDataBaseInstance = Room.databaseBuilder(context.getApplicationContext(),
                        AppDatabase.class, AppDatabase.DATABASE_NAME)
                        .fallbackToDestructiveMigration()
                        .build();
            }
        }

        return mDataBaseInstance;
    }

    public abstract RestaurantDao restaurantDao();
}
