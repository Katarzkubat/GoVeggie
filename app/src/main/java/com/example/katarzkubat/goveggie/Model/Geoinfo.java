package com.example.katarzkubat.goveggie.Model;

public class Geoinfo {

    private Placeinfo geometry;

    public Geoinfo(Placeinfo geometry) {
        this.geometry = geometry;
    }

    public Placeinfo getGeometry() {
        return geometry;
    }

    public void setGeometry(Placeinfo geometry) {
        this.geometry = geometry;
    }

    public String toString(){
        return this.geometry.toString();
    }

}