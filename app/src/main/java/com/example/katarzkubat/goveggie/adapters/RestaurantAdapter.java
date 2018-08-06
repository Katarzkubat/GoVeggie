package com.example.katarzkubat.goveggie.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolders> {

    private ArrayList<Restaurant> restaurants = new ArrayList<>();
    private Clicker clicker;
    private Context context;

    public RestaurantAdapter(Context appContext, Clicker clicker) {
        context = appContext;
        this.clicker = clicker;
    }

    @NonNull
    @Override
    public RestaurantViewHolders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext()).inflate(R.layout.activity_main_card_item, parent, false);

        return new RestaurantViewHolders(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantAdapter.RestaurantViewHolders holder, int position) {

        Restaurant singleRestaurant = restaurants.get(position);
        holder.mRestaurantName.setText(singleRestaurant.getName());

        final Restaurant sr = singleRestaurant;
        final RestaurantAdapter.RestaurantViewHolders fastHolder = holder;

        if(singleRestaurant.getPhotos() != null && singleRestaurant.getPhotos().size() > 0) {

        Picasso.get()
                .load(NetworkUtils.getImageUrl(singleRestaurant.getPhotos().get(0),
                        context.getResources().getString(R.string.api_key)))
                .networkPolicy(NetworkPolicy.OFFLINE)
                .into(holder.mPicture, new Callback() {
                    @Override
                    public void onSuccess() {}

                    @Override
                    public void onError(Exception e) {
                        Picasso.get()
                                .load(NetworkUtils.getImageUrl(sr.getPhotos().get(0),
                                        context.getResources().getString(R.string.api_key)))
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
        if (null == restaurants) {
            return 0;
        }
        return restaurants.size();
    }

    class RestaurantViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.card_image) ImageView mPicture;
        @BindView(R.id.card_restaurant_name) TextView mRestaurantName;

        public RestaurantViewHolders(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            clicker.onClick(restaurants.get(position));
        }
    }

    public ArrayList<Restaurant> getRestaurants() {
       return restaurants;
    }

    public void setRestaurant(List<Restaurant> restaurantList) {
        restaurants = (ArrayList<Restaurant>) restaurantList;
        notifyDataSetChanged();
    }
}
