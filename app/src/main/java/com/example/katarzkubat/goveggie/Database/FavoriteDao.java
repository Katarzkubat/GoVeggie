package com.example.katarzkubat.goveggie.Database;
/*

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.katarzkubat.goveggie.Model.Favorites;

import java.util.List;

@Dao
public interface FavoriteDao {

    @Query("SELECT * FROM Favorites ORDER BY row_id")
    LiveData<List<Favorites>> loadAllFavorites();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertFavorite(Favorites favorites);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateFavorite(Favorites favorites);

    @Delete
    void deleteFavorite(Favorites favorites);

    @Query("SELECT * FROM Favorites WHERE row_id = :row_id")
    LiveData<Favorites> loadFavoritesById(int row_id);
}
*/