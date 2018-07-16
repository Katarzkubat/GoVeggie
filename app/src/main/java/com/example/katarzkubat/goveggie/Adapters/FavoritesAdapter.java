package com.example.katarzkubat.goveggie.Adapters;

import
        android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.katarzkubat.goveggie.Model.Favorites;
import com.example.katarzkubat.goveggie.Model.Restaurant;
import com.example.katarzkubat.goveggie.R;
import com.example.katarzkubat.goveggie.Utilities.Clicker;
import com.example.katarzkubat.goveggie.Utilities.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.FavoriteViewHolders>{

    private ArrayList<Restaurant> favorites = new ArrayList<>();
    private final Context appContext;
    private Clicker clicker;

    public FavoritesAdapter(Context appContext, Clicker clicker) {
        this.appContext = appContext;
        this.clicker = clicker;
    }

    @NonNull
    @Override
    public FavoriteViewHolders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View singleView = LayoutInflater
                .from(parent.getContext()).inflate(R.layout.activity_favorite_list_item, parent, false);

        return new FavoriteViewHolders(singleView);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteViewHolders holder, int position) {
        Restaurant singleFavorite = favorites.get(position);
       // holder.mRating.setText(singleFavorite.getRating());
        holder.mName.setText(singleFavorite.getName());
        holder.mAddress.setText(singleFavorite.getVicinity());
        holder.mRatingBar.setRating((float) singleFavorite.getRating());

        if (singleFavorite.isOpen_now()) {
            holder.mHours.setText(R.string.opening_hours_open);
        } else {
            holder.mHours.setText(R.string.opening_hours_closed);
        }

        Picasso.get()
                .load(NetworkUtils.getImageUrl(singleFavorite.getPhotos().get(0), "AIzaSyCZyLiKgSRhoxdxHDiqpkeuiwwn6jcxzcY"))
              //  .resize(60, 60)
              //  .centerCrop()
                .into(holder.mPicture);
    }

    @Override
    public int getItemCount() {
        if (null == favorites) {
            return 0;
        }
        Log.d("FAVORITESAD", String.valueOf(favorites.size()));
        return favorites.size();
    }

    public class FavoriteViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.favorite_picture) ImageView mPicture;
        @BindView(R.id.favorite_rating_bar) RatingBar mRatingBar;
        @BindView(R.id.favorite_restaurant_name) TextView mName;
        @BindView(R.id.favorite_address) TextView mAddress;
        @BindView(R.id.favorite_opening_hours) TextView mHours;

        public FavoriteViewHolders(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position  = getAdapterPosition();
            clicker.onClick(favorites.get(position));
        }
    }

    public ArrayList<Restaurant> getFavorites() {
        return favorites;
    }

    public void setFavorites(List<Restaurant> favoritesList) {
        favorites = (ArrayList<Restaurant>) favoritesList;
        notifyDataSetChanged();
    }
}
