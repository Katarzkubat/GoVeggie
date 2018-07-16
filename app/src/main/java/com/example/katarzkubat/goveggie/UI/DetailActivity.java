package com.example.katarzkubat.goveggie.UI;

import android.Manifest;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.transition.TransitionManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;


import com.example.katarzkubat.goveggie.Utilities.NetworkUtils;
import com.example.katarzkubat.goveggie.Model.Restaurant;
import com.example.katarzkubat.goveggie.R;
import com.example.katarzkubat.goveggie.Utilities.AppExecutors;
import com.example.katarzkubat.goveggie.Utilities.SavingLocation;
import com.example.katarzkubat.goveggie.ViewModel.DetailViewModel;
import com.example.katarzkubat.goveggie.ViewModel.DisplayDetailModelFactory;
import com.example.katarzkubat.goveggie.Database.AppDatabase;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.katarzkubat.goveggie.R.color.white;
import static com.example.katarzkubat.goveggie.UI.MainActivity.OBJECT_NAME;
public class DetailActivity extends AppCompatActivity implements OnMapReadyCallback {

    @BindView(R.id.detail_open_hours)
    TextView mOpeningHours;
    @BindView(R.id.detail_image_button)
    ImageButton mAddToFavorite;
    @BindView(R.id.detail_address)
    TextView mAddress;
    @BindView(R.id.detail_rating_bar)
    RatingBar mRatingBar;
    @BindView(R.id.detail_rating_value)
    TextView mRating;
    @BindView(R.id.collapsing_toolbar_layout)
    CollapsingToolbarLayout cToolbar;
    @BindView(R.id.detail_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.detail_image)
    ImageView mImageView;

    private AppDatabase mDb;
    private Restaurant favoriteRestaurant;
    private GoogleMap mMap;

    private static final int DEFAULT_ZOOM = 12;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.detail_map_view);
        mapFragment.getMapAsync(this);

        mDb = AppDatabase.getInstance(getApplicationContext());

        Intent takeRestaurantObject = getIntent();
        final int rowId = takeRestaurantObject.getIntExtra(OBJECT_NAME, 1);

        mDb.restaurantDao().loadFavoritesById(rowId);

        DetailViewModel detailViewModel = ViewModelProviders.of(this, new DisplayDetailModelFactory(this.getApplication(), rowId)).get(DetailViewModel.class);
        detailViewModel.getRestaurant().observe(this, new Observer<Restaurant>() {
            @Override
            public void onChanged(@Nullable final Restaurant restaurant) {
                if(restaurant == null){
                    return;
                }

                favoriteRestaurant = restaurant;
                setSupportActionBar(mToolbar);
                mToolbar.setTitleTextColor(getResources().getColor(white));

                mToolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24px);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);

                getSupportActionBar().setTitle(restaurant.getName());

                cToolbar.setCollapsedTitleTextColor(getResources().getColor(white));

                int colorInt = getResources().getColor(white);
                ColorStateList csl = ColorStateList.valueOf(colorInt);

                cToolbar.setExpandedTitleTextColor(csl);
                cToolbar.setCollapsedTitleTypeface(ResourcesCompat.getFont(getApplicationContext(), R.font.montserrat));
                cToolbar.setExpandedTitleTypeface(ResourcesCompat.getFont(getApplicationContext(), R.font.montserrat));

                Picasso.get()
                        .load(NetworkUtils.getImageUrl(restaurant.getPhotos().get(0), "AIzaSyCZyLiKgSRhoxdxHDiqpkeuiwwn6jcxzcY"))
                        .into(mImageView);

                mAddress.setText(restaurant.getVicinity());

                if (restaurant.isOpen_now()) {
                    mOpeningHours.setText(R.string.opening_hours_open);
                } else {
                    mOpeningHours.setText(R.string.opening_hours_closed);
                }

                mRatingBar.setRating((float) restaurant.getRating());
                mRating.setText(Double.toString(restaurant.getRating()));

                if(restaurant.getIsFavorite()) {
                    mAddToFavorite.setImageResource(R.drawable.ic_round_favorite_orange24px);
                } else {
                    mAddToFavorite.setImageResource(R.drawable.ic_round_favorite_border_24px);
                }

                mAddToFavorite.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!restaurant.getIsFavorite()) {
                            addToFavorite(view);
                            mAddToFavorite.setImageResource(R.drawable.ic_round_favorite_orange24px);
                        } else {
                            removeFromFavorite(view);
                            mAddToFavorite.setImageResource(R.drawable.ic_round_favorite_border_24px);
                        }
                    }
                });
            }
        });
    }

    private void addToFavorite(View view) {

        favoriteRestaurant.setIsFavorite(true);
        AppExecutors.getsInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mDb.restaurantDao().insertFavorite(favoriteRestaurant);

            }
        });
    }

    private void removeFromFavorite(final View view) {

        AppExecutors.getsInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                favoriteRestaurant.setIsFavorite(false);
                mDb.restaurantDao().removeFromFavorite(favoriteRestaurant);
            }
        });
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
        displayChosenPoint();
    }

    private void displayChosenPoint() {

        AppDatabase mDb = AppDatabase.getInstance(getApplicationContext());
        DetailViewModel viewModel = ViewModelProviders.of(this).get(DetailViewModel.class);
        viewModel.getRestaurant().observe(this, new Observer<Restaurant>() {
            @Override
            public void onChanged(@Nullable Restaurant restaurant) {
                if(restaurant != null) {
                    LatLng singlePlace = new LatLng(restaurant.getGeometry().getLocation().getLat(),
                            restaurant.getGeometry().getLocation().getLng());

                    mMap.addMarker(new MarkerOptions().position(singlePlace).title(restaurant.getName()));
                }
            }
        });
    }

    private void displayLocationPoint() {

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                new LatLng(SavingLocation.mLastKnownLocation.getLatitude(),
                        SavingLocation.mLastKnownLocation.getLongitude()), DEFAULT_ZOOM));
    }
}
