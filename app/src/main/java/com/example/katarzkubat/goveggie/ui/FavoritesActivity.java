package com.example.katarzkubat.goveggie.ui;

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
import android.view.View;
import android.widget.TextView;

import com.example.katarzkubat.goveggie.adapters.FavoritesAdapter;
import com.example.katarzkubat.goveggie.model.Restaurant;
import com.example.katarzkubat.goveggie.utilities.Clicker;
import com.example.katarzkubat.goveggie.viewModel.FavoriteViewModel;
import com.example.katarzkubat.goveggie.R;
import com.example.katarzkubat.goveggie.utilities.AppExecutors;
import com.example.katarzkubat.goveggie.database.AppDatabase;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.katarzkubat.goveggie.R.color.white;
import static com.example.katarzkubat.goveggie.ui.MainActivity.OBJECT_NAME;

public class FavoritesActivity extends AppCompatActivity implements Clicker {

    @BindView(R.id.favorite_recycler)
    RecyclerView mRecycler;
    @BindView(R.id.favorites_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.nofavorite_message_display)
    TextView noFavorite;

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
                assert favoritesList != null;
                if (favoritesList.size() != 0) {
                    favoritesAdapter.setFavorites(favoritesList);
                } else {
                    noFavorite.setVisibility(View.VISIBLE);
                }

            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(Restaurant singleRestaurant) {

        Intent openDetail = new Intent(this, DetailActivity.class);
        openDetail.putExtra(OBJECT_NAME, singleRestaurant.getRow_id());
        startActivity(openDetail, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());

    }
}