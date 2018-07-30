package com.example.katarzkubat.goveggie.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import java.util.ArrayList;

@Entity(tableName = "Favorites", indices = {@Index(value = "place_id",
        unique = true)})
public class Favorites{

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "row_id")
    private int row_id;
    @ColumnInfo(name = "id")
    private String id;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "open_now")
    private boolean open_now;
    @ColumnInfo(name = "picture")
    private ArrayList<Pictures> picture;
    @ColumnInfo(name = "place_id")
    private String place_id;
    @ColumnInfo(name = "rating")
    private double rating;
    @ColumnInfo(name = "vicinity")
    private String vicinity;

    @Ignore
    public Favorites() {}

    public Favorites(String id, String name, boolean open_now, ArrayList<Pictures> picture, String place_id, double rating, String vicinity) {

        this.id = id;
        this.name = name;
        this.open_now = open_now;
        this.picture = picture;
        this.place_id = place_id;
        this.rating = rating;
        this.vicinity = vicinity;
    }

    @Ignore
    public Favorites(String name, boolean open_now, ArrayList<Pictures> picture, String place_id, double rating, String vicinity) {
        this.name = name;
        this.open_now = open_now;
        this.picture = picture;
        this.place_id = place_id;
        this.rating = rating;
        this.vicinity = vicinity;
    }

    public String getId() {
        return id;
    }

    public int getRow_id(){
        return row_id;
    }

    public String getName() {
        return name;
    }

    public boolean isOpen_now() {
        return open_now;
    }

    public ArrayList<Pictures> getPicture() {
        return picture;
    }

    public String getPlace_id() {
        return place_id;
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

    public void setName(String name) {
        this.name = name;
    }

    public void setOpen_now(boolean open_now) {
        this.open_now = open_now;
    }

    public void setPicture(ArrayList<Pictures> picture) {
        this.picture = picture;
    }

    public void setPlace_id(String place_id) {
        this.place_id = place_id;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setVicinity(String vicinity) {
        this.vicinity = vicinity;
    }
}
