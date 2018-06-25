package com.example.katarzkubat.goveggie.UI;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.example.katarzkubat.goveggie.Adapters.FavoritesAdapter;
import com.example.katarzkubat.goveggie.Adapters.RestaurantAdapter;
import com.example.katarzkubat.goveggie.R;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.katarzkubat.goveggie.R.color.white;

public class FavoritesActivity extends AppCompatActivity {

    @BindView(R.id.favorite_recycler) RecyclerView mRecyclerView;
    @BindView(R.id.favorites_toolbar) Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(R.string.favorites_activity);
        mToolbar.setTitleTextColor(getResources().getColor(white));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FavoritesAdapter favoritesAdapter = new FavoritesAdapter(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(favoritesAdapter);
    }
}
