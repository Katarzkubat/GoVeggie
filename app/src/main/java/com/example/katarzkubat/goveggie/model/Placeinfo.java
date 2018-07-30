package com.example.katarzkubat.goveggie.model;

public class Placeinfo {

    private Locationinfo location;

    public Placeinfo(Locationinfo location) {
        this.location = location;
    }

    public void setLocation(Locationinfo location) {
        this.location = location;
    }

    public Locationinfo getLocation() {
        return location;
    }

    public String toString() {
        return location.toString();
    }
}

