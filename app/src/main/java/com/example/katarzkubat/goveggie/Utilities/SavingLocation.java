package com.example.katarzkubat.goveggie.Utilities;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.preference.PreferenceManager;


import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.PlaceDetectionClient;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class SavingLocation {

    public static boolean mLocationPermissionGranted = true;
    public static FusedLocationProviderClient mFusedLocationProviderClient;
    public static Location mLastKnownLocation;
    public static LatLng mDefaultLocation;
    public static PlaceDetectionClient mPlaceDetectionClient;
    public static String CURRENT_LOCATION = "current  location";


    public static void getDeviceLocation(final Context context, final AsyncTask dataCallback) {

        if (ActivityCompat.checkSelfPermission
                (context, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat
                .checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        mFusedLocationProviderClient
                = LocationServices.getFusedLocationProviderClient(context);
        Task locationResult = mFusedLocationProviderClient.getLastLocation();

        locationResult.addOnCompleteListener(new OnCompleteListener() {

            @Override
            public void onComplete(@NonNull Task task) {

                if (task.isSuccessful()) {
                    mLastKnownLocation = (Location) task.getResult();

                    double latitude = mLastKnownLocation.getLatitude();
                    double longitude = mLastKnownLocation.getLongitude();

                    int latInt = (int) latitude;
                    int longInt = (int) longitude;

                    SharedPreferences sharedPreferences = PreferenceManager
                            .getDefaultSharedPreferences(context);
                    SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
                    prefsEditor.putInt("LATITUDE", latInt);
                    prefsEditor.putInt("LONGITUDE", longInt);
                    prefsEditor.apply();

                    dataCallback.execute();
                }
            }
        });
    }

}


