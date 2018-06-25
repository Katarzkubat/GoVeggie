package com.example.katarzkubat.goveggie.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.katarzkubat.goveggie.Model.Restaurant;

import java.util.List;

@Dao
public interface RestaurantDao {

    @Query("SELECT * FROM Restaurant ORDER BY id")
    List<Restaurant> loadAllRestaurant();

    @Insert
    void insertRestaurant(Restaurant restaurant);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateRestaurant(Restaurant restaurant);

    @Delete
    void deleteRestaurant(Restaurant restaurant);
}
