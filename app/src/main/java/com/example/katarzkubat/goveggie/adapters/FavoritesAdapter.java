package com.example.katarzkubat.goveggie.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.katarzkubat.goveggie.model.Restaurant;
import com.example.katarzkubat.goveggie.R;
import com.example.katarzkubat.goveggie.utilities.Clicker;
import com.example.katarzkubat.goveggie.utilities.NetworkUtils;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
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
        holder.mName.setText(singleFavorite.getName());
        holder.mAddress.setText(singleFavorite.getVicinity());
        holder.mRatingBar.setRating((float) singleFavorite.getRating());

        if (singleFavorite.isOpen_now()) {
            holder.mHours.setText(R.string.opening_hours_open);
        } else {
            holder.mHours.setText(R.string.opening_hours_closed);
        }

        //Following StackOverflow
        final Restaurant sf = singleFavorite;
        final FavoritesAdapter.FavoriteViewHolders fastHolder = holder;

        if (singleFavorite.getPhotos() != null && singleFavorite.getPhotos().size() > 0) {
            Picasso.get()
                    .load(NetworkUtils.getImageUrl(singleFavorite.getPhotos().get(0),
                            appContext.getResources().getString(R.string.api_key)))
                    .networkPolicy(NetworkPolicy.OFFLINE)
                    .into(holder.mPicture, new Callback() {
                        @Override
                        public void onSuccess() {
                        }

                        @Override
                        public void onError(Exception e) {
                            Picasso.get()
                                    .load(NetworkUtils.getImageUrl(sf.getPhotos().get(0),
                                            appContext.getResources().getString(R.string.api_key)))
                                    .placeholder(R.drawable.ic_carrot)
                                    .error(R.drawable.ic_carrot)
                                    .into(fastHolder.mPicture);
                        }
                    });
    } else {
            Picasso.get().load(R.drawable.ic_carrot).into(holder.mPicture);
        }
    }

    @Override
    public int getItemCount() {
        if (null == favorites) {
            return 0;
        }
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
