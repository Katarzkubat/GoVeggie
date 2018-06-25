package com.example.katarzkubat.goveggie.UI;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toolbar;

import com.example.katarzkubat.goveggie.R;
import com.google.android.gms.maps.MapView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    @BindView(R.id.detail_open_hours) TextView mOpeningHours;
    @BindView(R.id.detail_image_button) Button mAddToFavorite;
    @BindView(R.id.detail_address) TextView mAddress;
    @BindView(R.id.detail_rating_bar) RatingBar mRatingBar;
    @BindView(R.id.detail_rating_value) TextView mRating;
    @BindView(R.id.detail_map_view) MapView mMap;
    @BindView(R.id.detail_toolbar) Toolbar mToolbar;
    @BindView(R.id.detail_image) ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

    }
}
