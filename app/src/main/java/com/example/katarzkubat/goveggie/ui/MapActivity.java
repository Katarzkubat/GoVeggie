package com.example.katarzkubat.goveggie.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.example.katarzkubat.goveggie.model.Restaurant;
import com.example.katarzkubat.goveggie.R;
import com.example.katarzkubat.goveggie.utilities.RestaurantPreferences;
import com.example.katarzkubat.goveggie.utilities.SavingLocation;
import com.example.katarzkubat.goveggie.viewModel.MainViewModel;
import com.example.katarzkubat.goveggie.database.AppDatabase;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.PlaceDetectionClient;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.katarzkubat.goveggie.R.color.white;
import static com.example.katarzkubat.goveggie.ui.MainActivity.OBJECT_NAME;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener,
        GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;
    @BindView(R.id.map_toolbar)
    Toolbar mToolbar;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private Location mLastKnownLocation;
    private LatLng mDefaultLocation;
    private PlaceDetectionClient mPlaceDetectionClient;

    private static final int DEFAULT_ZOOM = 12;
    private static final String LAT = "lat";
    private static final String LON = "lon";
    private static final String ZOOM = "zoom";
    private static final int PERMISSIONS_REQUEST_FINE_LOCATION = 111;
    private boolean mLocationPermissionGranted = true;
    private HashMap<String, Integer> hashMap = new HashMap<String, Integer>();
    private static final String STATE_KEY_MAP_CAMERA = "cameraState";
    private Double mLat;
    private Double mLon;
    private Float mZoom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        if (savedInstanceState != null) {
            mLat = savedInstanceState.getDouble(LAT);
            mLon = savedInstanceState.getDouble(LON);
            mZoom = savedInstanceState.getFloat(ZOOM);
        }

        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);

        getSupportActionBar().setTitle(R.string.map);
        mToolbar.setTitleTextColor(getResources().getColor(white));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24px);

        mFusedLocationProviderClient
                = LocationServices.getFusedLocationProviderClient(this);

        mPlaceDetectionClient = Places.getPlaceDetectionClient(this, null);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        if (ActivityCompat.checkSelfPermission
                (this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }

        mMap.setMyLocationEnabled(true);

        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        displayLocationPoint();
        displayPoints();

        if (mLat != null && mLon != null && mZoom != null) {
            CameraUpdate camera = CameraUpdateFactory.newLatLngZoom(new LatLng(mLat, mLon), mZoom);
            mLat = null;
            mLon = null;
            mZoom = null;
            mMap.moveCamera(camera);
        }
    }

    private void displayLocationPoint() {
        if (mLat == null || mLon == null || mZoom == null) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                    new LatLng(SavingLocation.mLastKnownLocation.getLatitude(),
                            SavingLocation.mLastKnownLocation.getLongitude()), DEFAULT_ZOOM));
        }
    }

    public void displayPoints() {

        AppDatabase mDb = AppDatabase.getInstance(getApplicationContext());

        String preferences = RestaurantPreferences.getChosenPreferences(getApplicationContext());

        MainViewModel viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        viewModel.getRestaurant(preferences).observe(this, new Observer<List<Restaurant>>() {
            @Override
            public void onChanged(@Nullable List<Restaurant> restaurantList) {

                assert restaurantList != null;
                for (Restaurant restaurant : restaurantList) {

                    try {

                        LatLng singlePlace = new LatLng(restaurant.getGeometry().getLocation().getLat(),
                                restaurant.getGeometry().getLocation().getLng());

                        Marker marker = mMap.addMarker(new MarkerOptions().position(singlePlace).title(restaurant.getName()));
                        hashMap.put(marker.getId(), restaurant.getRow_id());

                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        mMap.setOnMarkerClickListener(MapActivity.this);
        mMap.setOnInfoWindowClickListener(MapActivity.this);
    }

    @SuppressLint("WrongConstant")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onInfoWindowClick(Marker marker) {

        int rowId = hashMap.get(marker.getId());
        Intent openDetailIntent = new Intent(MapActivity.this, DetailActivity.class);
        openDetailIntent.putExtra(OBJECT_NAME, rowId);
        startActivity(openDetailIntent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

        if (mMap != null) {
            outState.putDouble(LAT, mMap.getCameraPosition().target.latitude);
            outState.putDouble(LON, mMap.getCameraPosition().target.longitude);
            outState.putFloat(ZOOM, mMap.getCameraPosition().zoom);
        }

        super.onSaveInstanceState(outState);
    }
}
