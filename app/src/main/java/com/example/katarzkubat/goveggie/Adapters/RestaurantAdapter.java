package com.example.katarzkubat.goveggie.Adapters;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.katarzkubat.goveggie.Model.Restaurant;
import com.example.katarzkubat.goveggie.R;
import com.example.katarzkubat.goveggie.Utilities.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolders> {

    private ArrayList<Restaurant> restaurants = new ArrayList<>();
    private final Context appContext;

    public RestaurantAdapter(Context appContext) {
        this.appContext = appContext;
    }

    @NonNull
    @Override
    public RestaurantViewHolders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
     //   Log.d("ADAPTER", String.valueOf(parent));

        View view = LayoutInflater
                .from(parent.getContext()).inflate(R.layout.activity_main_card_item, parent, false);

        return new RestaurantViewHolders(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantAdapter.RestaurantViewHolders holder, int position) {
        Restaurant singleRestaurant = restaurants.get(position);
        holder.mRestaurantName.setText(singleRestaurant.getName());

        //WTF
       /* Picasso.get()
                .load(NetworkUtils.getImageUrl(singleRestaurant.getPicture(), "secretKey"))
                .resize(50, 50)
                .centerCrop()
                .into(holder.mPicture); */


       // Picasso.with(appContext).load(NetworkUtils.getImageUrl(singleRestaurant.getPicture(), "w185"))
        //        .into(holder.mPicture);

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
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            /*
            startActivity(new Intent(Intent.ACTION_VIEW,
                            ItemsContract.Items.buildItemUri(getItemId(view.getAdapterPosition()))),
                            ActivityOptions.makeSceneTransitionAnimation(ArticleListActivity.this).toBundle());
             */
        }
    }

    public void setRestaurant(ArrayList<Restaurant> restaurantList) {
        restaurants = restaurantList;
        notifyDataSetChanged();
    }
}
