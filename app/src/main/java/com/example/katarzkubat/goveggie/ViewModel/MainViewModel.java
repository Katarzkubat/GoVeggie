package com.example.katarzkubat.goveggie.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.katarzkubat.goveggie.Model.Restaurant;
import com.example.katarzkubat.goveggie.Database.AppDatabase;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private LiveData<List<Restaurant>> restaurantVegan;
    private LiveData<List<Restaurant>> restaurantVegetarian;

    public MainViewModel(@NonNull Application application) {
        super(application);
        AppDatabase database = AppDatabase.getInstance(application);

            restaurantVegan = database.restaurantDao().loadAllVegan();
            restaurantVegetarian = database.restaurantDao().loadAllVegetarian();
    }

    public LiveData<List<Restaurant>>   getRestaurant(String pref) {
        if(pref.equals("Vegan")){
            return restaurantVegan;
        }else{
            return restaurantVegetarian;
        }
    }
}
