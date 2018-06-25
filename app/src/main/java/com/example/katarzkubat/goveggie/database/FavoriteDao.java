package com.example.katarzkubat.goveggie.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.katarzkubat.goveggie.Model.Favorites;
import com.example.katarzkubat.goveggie.Model.Restaurant;

import java.util.List;

@Dao
public interface FavoriteDao {

    @Query("SELECT * FROM Favorites ORDER BY id")
    List<Favorites> loadAllFavorites();

    @Insert
    void insertRestaurant(Favorites favorites);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateFavorite(Favorites favorites);

    @Delete
    void deleteFavorite(Favorites favorites);
}
