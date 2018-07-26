package com.example.katarzkubat.goveggie.Database;

import android.arch.lifecycle.LiveData;
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

    @Query("SELECT * FROM Restaurant ORDER BY row_id")
    LiveData<List<Restaurant>> loadAllRestaurant();

    @Query("SELECT * FROM Restaurant ORDER BY row_id")
    List<Restaurant> loadAllSyncRestaurant();

    @Query("SELECT * FROM Restaurant WHERE isFavorite = 1 ORDER BY row_id")
    LiveData<List<Restaurant>> loadFavoritesRestaurant();

    @Query("SELECT * FROM Restaurant WHERE isFavorite = 1 ORDER BY row_id")
    List<Restaurant> loadSyncFavoritesRestaurant();

    @Insert(onConflict = OnConflictStrategy.FAIL)
    void insertRestaurants(Restaurant... restaurant);

    @Insert(onConflict = OnConflictStrategy.FAIL)
    void insertRestaurant(Restaurant restaurant);

    @Update(onConflict = OnConflictStrategy.FAIL)
    void updateRestaurant(Restaurant restaurant);

    @Update(onConflict = OnConflictStrategy.FAIL)
    void updateRestaurants(Restaurant... restaurant);

    @Update(onConflict = OnConflictStrategy.IGNORE)
    void insertFavorite(Restaurant restaurant);

    @Update(onConflict = OnConflictStrategy.IGNORE)
    void removeFromFavorite(Restaurant restaurant);

    @Delete
    void deleteRestaurant(Restaurant restaurant);

    @Query("DELETE FROM Restaurant")
    void deleteAllRestaurant();

    @Query("SELECT * FROM Restaurant WHERE row_id = :row_id")
    LiveData<Restaurant> loadRestaurantById(int row_id);

    @Query("SELECT * FROM Restaurant WHERE isFavorite = 1 AND row_id = :row_id")
    LiveData<Restaurant> loadFavoritesById(int row_id);

    @Query("SELECT * FROM Restaurant WHERE isVegan = 1 AND isVisible = 1 ORDER BY row_id")
    LiveData<List<Restaurant>> loadAllVegan();

    @Query("SELECT * FROM Restaurant WHERE isVegetarian = 1 AND isVisible = 1 ORDER BY row_id")
    LiveData<List<Restaurant>> loadAllVegetarian();

    @Query("SELECT * FROM Restaurant WHERE isVegan = 1 AND isVisible = 1 ORDER BY row_id")
    List<Restaurant> loadAllSyncVegan();

    @Query("SELECT * FROM Restaurant WHERE isVegetarian = 1 AND isVisible = 1 ORDER BY row_id")
    List<Restaurant> loadAllSyncVegetarian();

    @Query("UPDATE Restaurant SET isVisible = 0")
    void setAllInvisible();
}
