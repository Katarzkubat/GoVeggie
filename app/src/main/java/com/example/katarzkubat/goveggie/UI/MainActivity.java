package com.example.katarzkubat.goveggie.UI;

import android.content.Intent;
import android.location.Location;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.katarzkubat.goveggie.Adapters.RestaurantAdapter;
import com.example.katarzkubat.goveggie.Model.JsonResults;
import com.example.katarzkubat.goveggie.Model.Restaurant;
import com.example.katarzkubat.goveggie.R;
import com.example.katarzkubat.goveggie.Utilities.NetworkUtils;
import com.example.katarzkubat.goveggie.Utilities.OpenJsonUtils;
import com.example.katarzkubat.goveggie.Utilities.RestaurantPreferences;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Places;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.katarzkubat.goveggie.R.color.white;

public class MainActivity extends AppCompatActivity
        implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    @BindView(R.id.main_recycler) RecyclerView mRecyclerView;
    @BindView(R.id.main_toolbar) Toolbar mToolbar;

    private GoogleApiClient mClient;
    private double currentLatitude;
    private double currentLongitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        //getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setTitle(R.string.app_name);
        mToolbar.setTitleTextColor(getResources().getColor(white));

        RestaurantAdapter restaurantAdapter = new RestaurantAdapter(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(restaurantAdapter);

        new PopulateRestaurants().execute();

        mClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .addApi(Places.GEO_DATA_API)
                .enableAutoManage(this, this)
                .build();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.settings) {
            Intent settingsActivity = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(settingsActivity);
            return true;
        }

        if(id == R.id.location) {
            Intent locationActivity = new Intent(MainActivity.this, LocationActivity.class);
            startActivity(locationActivity);
            return true;
        }
        if (id == R.id.favorites) {
            Intent favoritesActivity = new Intent(MainActivity.this, FavoritesActivity.class);
            startActivity(favoritesActivity);
            return true;
        }

        if (id == R.id.map) {
            Intent mapActivity = new Intent(MainActivity.this, MapActivity.class);
            startActivity(mapActivity);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private class PopulateRestaurants extends AsyncTask<String, Void, JsonResults> {

        @Override
        protected JsonResults doInBackground(String... strings) {

            URL restaurantRequestUrl = null;

            String restaurantPreferences = RestaurantPreferences.getChosenPreferences(MainActivity.this);

            restaurantRequestUrl = NetworkUtils.buildRestaurantUrl
                    ("AIzaSyCZyLiKgSRhoxdxHDiqpkeuiwwn6jcxzcY", restaurantPreferences, 50.04709612010728,
                            19.94320872010728);

            try {
                String jsonResponse = NetworkUtils
                        .getResponseFromHttpUrl(restaurantRequestUrl);

                Log.d("JSON", jsonResponse);

                return OpenJsonUtils
                        .getRestaurantFromJson(jsonResponse);

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(JsonResults jsonResults) {
            super.onPostExecute(jsonResults);

//            Log.d("RESTAURANT", String.valueOf(jsonResults.getResults().size()));
        }
    }
}

