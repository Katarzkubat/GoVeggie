package com.example.katarzkubat.goveggie.Database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.katarzkubat.goveggie.Model.Restaurant;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface RestaurantDao {

    @Query("SELECT * FROM Restaurant ORDER BY row_id")
    LiveData<List<Restaurant>> loadAllRestaurant();

    @Query("SELECT * FROM Restaurant ORDER BY row_id")
    List<Restaurant> loadAllSyncRestaurant();

    @Query("SELECT * FROM Restaurant WHERE isFavorite = 1 ORDER BY row_id")
    LiveData<List<Restaurant>> loadFavoritesRestaurant();

    @Query("SELECT * FROM Restaurant WHERE isFavorite = 1 ORDER BY row_id")
    List<Restaurant> loadSyncFavoritesRestaurant();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertRestaurants(Restaurant... restaurant);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertRestaurant(Restaurant restaurant);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateRestaurant(Restaurant restaurant);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void insertFavorite(Restaurant restaurant);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void removeFromFavorite(Restaurant restaurant);

    @Delete
    void deleteRestaurant(Restaurant restaurant);

    @Query("DELETE FROM Restaurant")
    void deleteAllRestaurant();

    @Query("SELECT * FROM Restaurant WHERE row_id = :row_id")
    LiveData<Restaurant> loadRestaurantById(int row_id);

    @Query("SELECT * FROM Restaurant WHERE isFavorite = 1 AND row_id = :row_id")
    LiveData<Restaurant> loadFavoritesById(int row_id);

    @Query("SELECT * FROM Restaurant WHERE isVegan = 1 ORDER BY row_id")
    LiveData<List<Restaurant>> loadAllVegan();

    @Query("SELECT * FROM Restaurant WHERE isVegetarian = 1 ORDER BY row_id")
    LiveData<List<Restaurant>> loadAllVegetarian();
}
