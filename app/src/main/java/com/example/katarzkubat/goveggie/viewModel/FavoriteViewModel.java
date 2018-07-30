package com.example.katarzkubat.goveggie.viewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.katarzkubat.goveggie.database.AppDatabase;
import com.example.katarzkubat.goveggie.model.Restaurant;

import java.util.List;

public class FavoriteViewModel extends AndroidViewModel {

    private LiveData<List<Restaurant>> favorites;

    public FavoriteViewModel(@NonNull Application application) {
        super(application);
        AppDatabase database = AppDatabase.getInstance(this.getApplication());
        favorites = database.restaurantDao().loadFavoritesRestaurant();
    }

    public LiveData<List<Restaurant>> getFavorites() {
        return favorites;
    }
}
