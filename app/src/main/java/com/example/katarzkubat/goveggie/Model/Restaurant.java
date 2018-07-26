package com.example.katarzkubat.goveggie.Model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.util.Log;

import java.util.ArrayList;

@Entity(tableName = "restaurant", indices = {@Index(value = "place_id",
        unique = true)})
public class Restaurant {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "row_id")
    private int row_id;
    @ColumnInfo(name = "geometry")
    private Placeinfo geometry;
    @ColumnInfo(name = "id")
    private String id;
    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "opening_hours")
    private OpeningHours opening_hours;

    @ColumnInfo(name = "photos")
    private ArrayList<Pictures> photos;
    @ColumnInfo(name = "place_id")
    private String place_id;
    @ColumnInfo(name = "rating")
    private double rating;
    @ColumnInfo(name = "vicinity")
    private String vicinity;
    @ColumnInfo(name = "nextPage")
    private String nextPage;
    @ColumnInfo(name = "isFavorite")
    private boolean isFavorite;
    @ColumnInfo(name = "isVegan")
    private boolean isVegan;
    @ColumnInfo(name = "isVegetarian")
    private boolean isVegetarian;
    @ColumnInfo(name = "isVisible")
    private boolean isVisible;


    public Restaurant() {
    }

    @Ignore
    public Restaurant(Placeinfo geometry, String name, OpeningHours opening_hours, ArrayList<Pictures> photos,
                      String place_id, double rating, String vicinity, boolean isFavorite, boolean isVegan, boolean isVisible) {
        this.geometry = geometry;
        this.name = name;
        this.opening_hours = opening_hours;
        this.photos = photos;
        this.place_id = place_id;
        this.rating = rating;
        this.vicinity = vicinity;
        this.isFavorite = isFavorite;
        this.isVegan = isVegan;
        this.isVisible = isVisible;
    }

    public Restaurant(String id, String nextPage, Placeinfo geometry, String name,
                      OpeningHours opening_hours, ArrayList<Pictures> photos, String place_id,
                      double rating, String vicinity, boolean isFavorite, boolean isVegan, boolean isVisible) {
        this.id = id;
        this.geometry = geometry;
        this.name = name;
        this.opening_hours = opening_hours;
        this.nextPage = nextPage;
        this.photos = photos;
        this.place_id = place_id;
        this.rating = rating;
        this.vicinity = vicinity;
        this.isFavorite = isFavorite;
        this.isVegan = isVegan;
        this.isVisible = isVisible;
    }

    public int getRow_id() {
        return row_id;
    }

    public String getId() {
        return id;
    }

    public Placeinfo getGeometry() {
        return geometry;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Pictures> getPhotos() {
        return photos;
    }

    public double getRating() {
        return rating;
    }

    public String getVicinity() {
        return vicinity;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setRow_id(int row_id) {
        this.row_id = row_id;
    }

    public void setGeometry(Placeinfo geometry) {
        this.geometry = geometry;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOpening_hours(OpeningHours opening_hours) {
        this.opening_hours = opening_hours;
    }
    public OpeningHours getOpening_hours() {
        return opening_hours;
    }
    public void setPhotos(ArrayList<Pictures> photos) {
        this.photos = photos;
    }

    public void setPlace_id(String place_id) {
        this.place_id = place_id;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public void setVicinity(String vicinity) {
        this.vicinity = vicinity;
    }

    public boolean isOpen_now() {
        return opening_hours != null ? opening_hours.isOpen_now() : false;
    }

    public String getPlace_id() {
        return place_id;
    }

    public String getNextPage() {
        return nextPage;
    }

    public void setNextPage(String nextPage) {
        this.nextPage = nextPage;
    }

    public boolean getIsFavorite() {
        return isFavorite;
    }

    public void setIsFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public boolean getIsVegan() {
        return isVegan;
    }

    public void setIsVegan(boolean vegan) {
        isVegan = vegan;
    }

    public boolean getIsVegetarian() {
        return isVegetarian;
    }

    public void setIsVegetarian(boolean vegetarian) {
        isVegetarian = vegetarian;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }
}

