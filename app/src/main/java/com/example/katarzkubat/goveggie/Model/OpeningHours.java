package com.example.katarzkubat.goveggie.Model;

public class OpeningHours {

    public boolean open_now;

    public OpeningHours(boolean open_now) {

        this.open_now = open_now;
    }

    public boolean isOpen_now() {
        return open_now;
    }

    public void setOpen_now(boolean open_now) {

        this.open_now = open_now;
    }

    public boolean getOpen_now() {
        return open_now;
    }

    public String toString(){
        return open_now ? "open" : "closed";
    }

}
