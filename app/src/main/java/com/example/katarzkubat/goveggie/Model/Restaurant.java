package com.example.katarzkubat.goveggie.Model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.util.ArrayList;

@Entity(tableName = "restaurant")
public class Restaurant {

    //pytanie o id w tabelce - jest id miejsca
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="id")
    private int id;
    @ColumnInfo(name = "geometry")
    private Geoinfo geometry;
    //private String id;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "open_now")
    private boolean open_now;

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
    @Ignore
    public Restaurant() {}

    @Ignore
    public Restaurant(Geoinfo geometry, String name, boolean open_now, ArrayList<Pictures> photos, String place_id, double rating, String vicinity) {
        this.geometry = geometry;
        this.name = name;
        this.open_now = open_now;
        this.photos = photos;
        this.place_id = place_id;
        this.rating = rating;
        this.vicinity = vicinity;
    }

    public Restaurant(int id, String nextPage, Geoinfo geometry, String name,
                      boolean open_now, ArrayList<Pictures> photos, String place_id, double rating, String vicinity) {
        this.id = id;
        this.geometry = geometry;
        this.name = name;
        this.open_now = open_now;
        this.nextPage = nextPage;
        this.photos = photos;
        this.place_id = place_id;
        this.rating = rating;
        this.vicinity = vicinity;
    }

    public int getId(){
        return id;
    }

    public Geoinfo getGeometry() {
        return geometry;
    }

    public String getName() {
        return name;
    }

    public boolean isOpenNow() {
        return open_now;
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

    public void setId(int id) {
        this.id = id;
    }

    public void setGeometry(Geoinfo geometry) {
        this.geometry = geometry;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOpen_now(boolean open_now) {
        this.open_now = open_now;
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
        return open_now;
    }

    public String getPlace_id() {
        return place_id;
    }

    public String getNextPage(){
        return nextPage;
    }

}

