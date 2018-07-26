package com.example.katarzkubat.goveggie.ViewModel;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import com.example.katarzkubat.goveggie.Database.AppDatabase;
import com.example.katarzkubat.goveggie.Model.Restaurant;

public class FavoriteDetailViewModel extends ViewModel {

    private LiveData<Restaurant> singleFavorite;

    public FavoriteDetailViewModel(@NonNull Application application, int rowId) {

        AppDatabase database = AppDatabase.getInstance(application);
        singleFavorite = database.restaurantDao().loadFavoritesById(rowId);
    }

    public LiveData<Restaurant> getSingleFavorite() {
        return singleFavorite;
    }
}

