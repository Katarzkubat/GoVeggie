package com.example.katarzkubat.goveggie.UI;

import android.Manifest;
import android.app.ActivityOptions;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.katarzkubat.goveggie.Adapters.RestaurantAdapter;
import com.example.katarzkubat.goveggie.JobDispatcher.RestaurantJobDispatcher;
import com.example.katarzkubat.goveggie.Utilities.Clicker;
import com.example.katarzkubat.goveggie.Utilities.RestaurantPreferences;
import com.example.katarzkubat.goveggie.Utilities.SavingLocation;
import com.example.katarzkubat.goveggie.ViewModel.MainViewModel;
import com.example.katarzkubat.goveggie.Model.Restaurant;
import com.example.katarzkubat.goveggie.R;
import com.example.katarzkubat.goveggie.Utilities.PopulateRestaurant;
import com.example.katarzkubat.goveggie.Database.AppDatabase;
import com.example.katarzkubat.goveggie.ViewModel.MainViewModelFactory;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Places;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.katarzkubat.goveggie.R.color.white;

public class MainActivity extends AppCompatActivity
        implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, Clicker{

    public static final String OBJECT_NAME = "results";
    private static final String PREFERENCES = "preferences";
    public String preferences;

    @BindView(R.id.main_recycler) RecyclerView mRecyclerView;
    @BindView(R.id.main_toolbar) Toolbar mToolbar;

    private RestaurantAdapter restaurantAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Log.d("CHECKPREF4", "oncreate");
        if(savedInstanceState != null) {
            preferences = savedInstanceState.getString(PREFERENCES);
        }

        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(R.string.app_name);
        mToolbar.setTitleTextColor(getResources().getColor(white));

        restaurantAdapter = new RestaurantAdapter(this, this);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(restaurantAdapter);

        if (ActivityCompat.checkSelfPermission
                (this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }

        GoogleApiClient mClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .addApi(Places.GEO_DATA_API)
                .enableAutoManage(this, this)
                .build();

        AppDatabase mDb = AppDatabase.getInstance(getApplicationContext());

        RestaurantJobDispatcher.scheduleJob(this);
        //AsyncTask dataPopulating = new PopulateRestaurant(this);
        //SavingLocation.getDeviceLocation(this, dataPopulating);
        //loadRestaurant();

    }

    private void loadRestaurant() {
        preferences = RestaurantPreferences.getChosenPreferences(getApplicationContext());
        MainViewModel viewModel = ViewModelProviders.of(this, new MainViewModelFactory(this.getApplication(), preferences)).get(MainViewModel.class);
        viewModel.getRestaurant().observe(this, new Observer<List<Restaurant>>() {
            @Override
            public void onChanged(@Nullable List<Restaurant> restaurantList) {

                /*for(int i = 0; i < restaurantList.size(); i++){
                    Log.d("VEGAN", restaurantList.get(i).getName() + " "+restaurantList.get(i).getIsVegan());
                    Log.d("VEGANVEGETARIAN", restaurantList.get(i).getName() + " "+restaurantList.get(i).getIsVegetarian());
                } */
                restaurantAdapter.setRestaurant(restaurantList);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_activity_menu, menu);
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.settings) {
            Intent settingsActivity = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(settingsActivity,ActivityOptions.makeSceneTransitionAnimation(this).toBundle() );
            return true;
        }

        if(id == R.id.location) {
            Intent locationActivity = new Intent(MainActivity.this, LocationActivity.class);
            startActivity(locationActivity, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
            return true;
        }
        if (id == R.id.favorites) {
            Intent favoritesActivity = new Intent(MainActivity.this, FavoritesActivity.class);
            startActivity(favoritesActivity, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
            return true;
        }

        if (id == R.id.map) {
            Intent mapActivity = new Intent(MainActivity.this, MapActivity.class);
            startActivity(mapActivity, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {}

    @Override
    public void onConnectionSuspended(int i) {}

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {}

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("CHECKPREF1", ""+preferences);
        Log.d("CHECKPREF2", ""+
                RestaurantPreferences.getChosenPreferences(getApplicationContext()));

        //if(preferences != RestaurantPreferences.getChosenPreferences(getApplicationContext())) {
            AsyncTask dataPopulating = new PopulateRestaurant(this);
            SavingLocation.getDeviceLocation(this, dataPopulating);
            loadRestaurant();
        //}

        Log.d("RESUME", "puknięcie");
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onStop() {
        super.onStop();
        //preferences = null;
        Log.d("CHECKPREF3", "stop");

    }

    @Override
    public void onClick(Restaurant singleRestaurant) {

        Log.d("SINGLEREST", String.valueOf(singleRestaurant.getRow_id()));

        Intent openDetail = new Intent(this, DetailActivity.class);
        openDetail.putExtra(OBJECT_NAME, singleRestaurant.getRow_id());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            startActivity(openDetail, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

        preferences = RestaurantPreferences.getChosenPreferences(getApplicationContext());
        outState.putString(PREFERENCES, preferences);
        super.onSaveInstanceState(outState);
    }
}

