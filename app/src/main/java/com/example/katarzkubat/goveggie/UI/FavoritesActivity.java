package com.example.katarzkubat.goveggie.UI;

import android.app.ActivityOptions;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;

import com.example.katarzkubat.goveggie.Adapters.FavoritesAdapter;
import com.example.katarzkubat.goveggie.Model.Restaurant;
import com.example.katarzkubat.goveggie.Utilities.Clicker;
import com.example.katarzkubat.goveggie.ViewModel.FavoriteViewModel;
import com.example.katarzkubat.goveggie.Model.Favorites;
import com.example.katarzkubat.goveggie.R;
import com.example.katarzkubat.goveggie.Utilities.AppExecutors;
import com.example.katarzkubat.goveggie.Database.AppDatabase;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.katarzkubat.goveggie.R.color.white;
import static com.example.katarzkubat.goveggie.UI.MainActivity.OBJECT_NAME;

public class FavoritesActivity extends AppCompatActivity implements Clicker {

    @BindView(R.id.favorite_recycler)
    RecyclerView mRecycler;
    @BindView(R.id.favorites_toolbar)
    Toolbar mToolbar;

    private AppDatabase mDb;
    private FavoritesAdapter favoritesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(R.string.favorites_activity);
        mToolbar.setTitleTextColor(getResources().getColor(white));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24px);

        favoritesAdapter = new FavoritesAdapter(this, this);
        mRecycler.setLayoutManager(new LinearLayoutManager(this));

        mRecycler.setHasFixedSize(true);
        mRecycler.setAdapter(favoritesAdapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback
                (0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(final RecyclerView.ViewHolder viewHolder, int direction) {
                AppExecutors.getsInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        int position = viewHolder.getAdapterPosition();
                        ArrayList<Restaurant> favorites = favoritesAdapter.getFavorites();
                        Restaurant favorite = favorites.get(position);
                        favorite.setIsFavorite(false);
                        mDb.restaurantDao().removeFromFavorite(favorite);
                    }
                });
            }
        }).attachToRecyclerView(mRecycler);

        mDb = AppDatabase.getInstance(getApplicationContext());
        loadFavorites();
    }

    private void loadFavorites() {

        FavoriteViewModel viewModel = ViewModelProviders.of(this).get(FavoriteViewModel.class);
        viewModel.getFavorites().observe(this, new Observer<List<Restaurant>>() {
            @Override
            public void onChanged(@Nullable List<Restaurant> favoritesList) {
                favoritesAdapter.setFavorites(favoritesList);

            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(Restaurant singleRestaurant) {


        Log.d("FAVORITECLICK", String.valueOf(singleRestaurant.getRow_id()));
        Intent openDetail = new Intent(this, DetailActivity.class);
        openDetail.putExtra(OBJECT_NAME, singleRestaurant.getRow_id());
        startActivity(openDetail);

    }
}