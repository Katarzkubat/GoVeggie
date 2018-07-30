package com.example.katarzkubat.goveggie.viewModel;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import com.example.katarzkubat.goveggie.model.Restaurant;
import com.example.katarzkubat.goveggie.database.AppDatabase;

public class DetailViewModel extends ViewModel {

    private LiveData<Restaurant> restaurant;

    public DetailViewModel(@NonNull Application application, int rowId) {

        AppDatabase database = AppDatabase.getInstance(application);
        restaurant = database.restaurantDao().loadRestaurantById(rowId);
    }

    public LiveData<Restaurant> getRestaurant() {
        return restaurant;
    }
}
