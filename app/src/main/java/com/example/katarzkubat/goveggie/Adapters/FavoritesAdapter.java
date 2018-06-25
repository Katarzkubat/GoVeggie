package com.example.katarzkubat.goveggie.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.katarzkubat.goveggie.Model.Favorites;
import com.example.katarzkubat.goveggie.Model.Restaurant;
import com.example.katarzkubat.goveggie.R;
import com.example.katarzkubat.goveggie.Utilities.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.FavoriteViewHolders>{

    private ArrayList<Favorites> favorites = new ArrayList<>();
    private final Context appContext;

    public FavoritesAdapter(Context appContext) {
        this.appContext = appContext;
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
        Favorites singleFavorite = favorites.get(position);
        holder.mRating.setText(singleFavorite.getRating());
        holder.mName.setText(singleFavorite.getName());
        holder.mAddress.setText(singleFavorite.getVicinity());

        Picasso.get()
                .load(NetworkUtils.getImageUrl(singleFavorite.getPicture(), "secretKey"))
                .resize(30, 30)
                .centerCrop()
                .into(holder.mPicture);

    }

    @Override
    public int getItemCount() {
        if (null == favorites) {
            return 0;
        }
        return favorites.size();
    }

    public class FavoriteViewHolders extends RecyclerView.ViewHolder  {

        @BindView(R.id.favorite_picture) ImageView mPicture;
        @BindView(R.id.favorite_rating_value) TextView mRating;
        @BindView(R.id.favorite_restaurant_name) TextView mName;
        @BindView(R.id.favorite_address) TextView mAddress;
        @BindView(R.id.favorite_opening_hours) TextView mHours;

        public FavoriteViewHolders(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void setFavorites(ArrayList<Favorites> favoritesList) {
        favorites = favoritesList;
        notifyDataSetChanged();
    }
}
