package com.example.katarzkubat.goveggie.Model;

import java.util.ArrayList;

public class JsonResults {

    private ArrayList<Restaurant> results;
    private String next_page_token;

    public JsonResults(ArrayList<Restaurant> results, String next_page_token) {
        this.results = results;
        this.next_page_token = next_page_token;
    }

    public ArrayList<Restaurant> getResults() {
        return results;
    }

    public String getNext_page_token() {
        return next_page_token;
    }

}



