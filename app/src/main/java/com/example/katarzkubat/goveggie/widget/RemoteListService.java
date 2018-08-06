package com.example.katarzkubat.goveggie.widget;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Picture;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.katarzkubat.goveggie.database.AppDatabase;
import com.example.katarzkubat.goveggie.model.OpeningHours;
import com.example.katarzkubat.goveggie.model.Pictures;
import com.example.katarzkubat.goveggie.model.Restaurant;
import com.example.katarzkubat.goveggie.R;
import com.example.katarzkubat.goveggie.utilities.NetworkUtils;
import com.example.katarzkubat.goveggie.utilities.RestaurantPreferences;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;

import static com.example.katarzkubat.goveggie.ui.MainActivity.OBJECT_NAME;

public class RemoteListService extends RemoteViewsService {

    private static final String ROW_ID = "row_id";

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new RemoteListViewsFactory(this.getApplicationContext(), intent);
    }

    public class RemoteListViewsFactory implements RemoteViewsService.RemoteViewsFactory {

        private Context mContext;
        private ArrayList<Restaurant> restaurantArrayList;
        int widgetId;
        AppDatabase mDb;

        public RemoteListViewsFactory(Context applicationContext, Intent intent) {
            mContext = applicationContext;
        }

        @Override
        public RemoteViews getViewAt(int i) {
            final RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(), R.layout.restaurant_widget_list_item);

            int rowId = restaurantArrayList.get(i).getRow_id();
            final String restaurantName = restaurantArrayList.get(i).getName();
            final String address = restaurantArrayList.get(i).getVicinity();
            final OpeningHours openHours = restaurantArrayList.get(i).getOpening_hours();

            remoteViews.setTextViewText(R.id.widget_restaurant_name, restaurantName);
            remoteViews.setTextViewText(R.id.widget_restaurant_address, address);
            remoteViews.setTextViewText(R.id.widget_restaurant_time, openHours.toString());

            try {
                if (NetworkUtils.isNetworkConnectionAvailable(getApplicationContext())) {
                    Pictures image = restaurantArrayList.get(i).getPhotos().get(0);
                    Bitmap b = Picasso.get()
                            .load(NetworkUtils.getImageUrl(image, mContext.getResources().getString(R.string.api_key)))
                            .resize(48, 48).centerCrop().get();
                    remoteViews.setImageViewBitmap(R.id.widget_icon, b);
                } else {
                    remoteViews.setImageViewResource(R.id.widget_icon, R.drawable.ic_carrot);
                }
            } catch (IOException e) {
                e.printStackTrace();
                remoteViews.setImageViewResource(R.id.widget_icon, R.drawable.ic_carrot);
            }

            Bundle extras = new Bundle();
            extras.putInt(OBJECT_NAME, rowId);
            Intent fillInIntent = new Intent();
            fillInIntent.putExtras(extras);
            remoteViews.setOnClickFillInIntent(R.id.widget_list_item, fillInIntent);

            return remoteViews;
        }

        @Override
        public void onCreate() {
            mDb = AppDatabase.getInstance(getApplicationContext());
        }

        @Override
        public void onDataSetChanged() {
            String preferences = RestaurantPreferences.getChosenPreferences(getApplicationContext());
            if (preferences.equals("Vegan")) {

                restaurantArrayList = (ArrayList<Restaurant>) mDb.restaurantDao().loadAllSyncVegan();
            } else {

                restaurantArrayList = (ArrayList<Restaurant>) mDb.restaurantDao().loadAllSyncVegetarian();
            }
        }

        @Override
        public void onDestroy() {
        }

        @Override
        public int getCount() {
            return restaurantArrayList.size();
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }
    }
}
