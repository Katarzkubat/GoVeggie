package com.example.katarzkubat.goveggie.UI;

import android.app.ActivityOptions;
import android.appwidget.AppWidgetManager;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
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
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.katarzkubat.goveggie.Adapters.RestaurantAdapter;
import com.example.katarzkubat.goveggie.JobDispatcher.RestaurantJobDispatcher;
import com.example.katarzkubat.goveggie.Utilities.Clicker;
import com.example.katarzkubat.goveggie.Utilities.NetworkUtils;
import com.example.katarzkubat.goveggie.Utilities.RestaurantPreferences;
import com.example.katarzkubat.goveggie.Utilities.SavingLocation;
import com.example.katarzkubat.goveggie.ViewModel.MainViewModel;
import com.example.katarzkubat.goveggie.Model.Restaurant;
import com.example.katarzkubat.goveggie.R;
import com.example.katarzkubat.goveggie.Utilities.PopulateRestaurant;
import com.example.katarzkubat.goveggie.Database.AppDatabase;
import com.example.katarzkubat.goveggie.Widget.RestaurantWidgetProvider;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Places;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

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
    @BindView(R.id.main_error_message) TextView errorMessage;

    private RestaurantAdapter restaurantAdapter;
    private Timer timerObj;
    private TimerTask timerTaskObj;
    private Handler mTimerHandler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(R.string.app_name);
        mToolbar.setTitleTextColor(getResources().getColor(white));
        mToolbar.setLogo(R.drawable.ic_carrot);

        restaurantAdapter = new RestaurantAdapter(this, this);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(restaurantAdapter);

        //mClient sprawdzić
        GoogleApiClient mClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .addApi(Places.GEO_DATA_API)
                .enableAutoManage(this, this)
                .build();

        AppDatabase mDb = AppDatabase.getInstance(getApplicationContext());

        RestaurantJobDispatcher.scheduleJob(this);
    }

    //to  można gdzieś przenieść
    private void loadRestaurant() {
        preferences = RestaurantPreferences.getChosenPreferences(getApplicationContext());
        MainViewModel viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        viewModel.getRestaurant(preferences).observe(this, new Observer<List<Restaurant>>() {
            @Override
            public void onChanged(@Nullable List<Restaurant> restaurantList) {
                Log.d("DRAWTEST", "onChanged: "+restaurantList.size());
                restaurantAdapter.setRestaurant(restaurantList);
                if(restaurantList.size() == 0){
                    cancelTimer();
                    startTimer();
                }else{
                    cancelTimer();
                }
            }
        });
    }
    //Following StackOverflow
    private void startTimer(){
        timerObj = new Timer();
        timerTaskObj = new TimerTask() {
            public void run() {
                mTimerHandler.post(new Runnable() {
                    public void run(){
                        errorMessage.setText(R.string.error_message_norestaurants);
                        errorMessage.setVisibility(View.VISIBLE);
                    }
                });
            }
        };
        timerObj.schedule(timerTaskObj, 2000, 1);
    }

     private void cancelTimer(){
         if(timerObj != null){
             timerObj.cancel();
             timerObj.purge();
         }
         errorMessage.setVisibility(View.INVISIBLE);
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
            preferences = null;
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
        if (preferences != RestaurantPreferences.getChosenPreferences(getApplicationContext())) {

            AsyncTask dataPopulating = new PopulateRestaurant(this);
            SavingLocation.getDeviceLocation(this, dataPopulating);
            loadRestaurant();
        }

        if (!NetworkUtils.isNetworkConnectionAvailable(this)) {
            Toast.makeText(this, R.string.error_message_internet, Toast.LENGTH_SHORT).show();
        }

        if (ActivityCompat.checkSelfPermission(MainActivity.this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            errorMessage.setText(R.string.error_message_location);
        } else {
            errorMessage.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onClick(Restaurant singleRestaurant) {

        Intent openDetail = new Intent(this, DetailActivity.class);
        openDetail.putExtra(OBJECT_NAME, singleRestaurant.getRow_id());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            startActivity(openDetail, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
        }
    }
}

