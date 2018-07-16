package com.example.katarzkubat.goveggie.Model;

import android.util.Log;

public class OpeningHours {

    public boolean open_now;

    public OpeningHours(boolean open_now) {

        Log.d("OpeningHours2", ""+open_now);
        this.open_now = open_now;
    }

    public boolean isOpen_now() {
        return open_now;
    }

    public void setOpen_now(boolean open_now) {

        Log.d("setOpen_now", ""+open_now);
        this.open_now = open_now;
    }

    public boolean getOpen_now() {
        return open_now;
    }

}
