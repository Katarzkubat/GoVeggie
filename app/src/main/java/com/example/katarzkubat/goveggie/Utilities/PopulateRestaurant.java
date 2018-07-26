package com.example.katarzkubat.goveggie.Utilities;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.example.katarzkubat.goveggie.Model.JsonResults;
import com.example.katarzkubat.goveggie.Database.AppDatabase;
import com.example.katarzkubat.goveggie.Database.RestaurantDao;
import com.example.katarzkubat.goveggie.Model.Restaurant;
import com.example.katarzkubat.goveggie.Widget.RestaurantWidgetProvider;

import java.net.URL;
import java.util.ArrayList;

public class PopulateRestaurant extends AsyncTask<Object, Void, JsonResults> {

    Context context;

    public PopulateRestaurant(Context context) {
        this.context = context;
    }

    @Override
    protected JsonResults doInBackground(Object... obj) {

        URL restaurantRequestUrl = null;

        String restaurantPreferences = RestaurantPreferences.getChosenPreferences(context);

        restaurantRequestUrl = NetworkUtils.buildRestaurantUrl
                ("AIzaSyCZyLiKgSRhoxdxHDiqpkeuiwwn6jcxzcY", restaurantPreferences,
                        SavingLocation.mLastKnownLocation.getLatitude(),
                        SavingLocation.mLastKnownLocation.getLongitude());

        try {
            String jsonResponse = NetworkUtils
                    .getResponseFromHttpUrl(restaurantRequestUrl);

            JsonResults results = OpenJsonUtils
                    .getRestaurantFromJson(jsonResponse);
            RestaurantDao rDao = AppDatabase.getInstance(context).restaurantDao();

            rDao.setAllInvisible();
            //rDao.deleteAllRestaurant();

            ArrayList<Restaurant> savedBefore = (ArrayList) rDao.loadAllSyncRestaurant();
            ArrayList<Restaurant> bulkInsert = new ArrayList<>();
            ArrayList<Restaurant> bulkUpdate = new ArrayList<>();

            for (int i = 0; i < results.getResults().size(); i++) {

                Restaurant singleRestaurant = results.getResults().get(i);

                if (restaurantPreferences.equals("Vegan")) {
                    singleRestaurant.setIsVegan(true);
                } else {
                    singleRestaurant.setIsVegetarian(true);
                }
                singleRestaurant.setVisible(true);

                for (int j = 0; j < savedBefore.size(); j++) {

                    if (singleRestaurant.getPlace_id().equals(savedBefore.get(j).getPlace_id())) {
                        singleRestaurant.setIsFavorite(savedBefore.get(j).getIsFavorite());

                        if (restaurantPreferences.equals("Vegan")) {
                            singleRestaurant.setIsVegetarian(savedBefore.get(j).getIsVegetarian());
                        } else {
                            singleRestaurant.setIsVegan(savedBefore.get(j).getIsVegan());
                        }

                        singleRestaurant.setRow_id(savedBefore.get(j).getRow_id());
                        bulkUpdate.add(singleRestaurant);
                        break;
                    }
                }

                if(singleRestaurant.getRow_id() <= 0){
                    bulkInsert.add(singleRestaurant);
                }

                /*try {
                    rDao.insertRestaurant(singleRestaurant);
                } catch (SQLiteConstraintException exception) {

                    rDao.updateRestaurant(singleRestaurant);
                }*/
            }
            Log.d("DRAWTEST", "batchUpdate: "+bulkUpdate.size());

            if(bulkUpdate.size() > 0 ) {

                Restaurant [] uBulk = new Restaurant[bulkUpdate.size()];
                for(int i = 0; i < bulkUpdate.size(); i++) {
                    uBulk[i] = bulkUpdate.get(i);
                }

                rDao.updateRestaurants(uBulk);
            }

            Log.d("DRAWTEST", "batchInsert: "+bulkInsert.size());

            if(bulkInsert.size() > 0 ) {
                 Restaurant[] iBulk = new Restaurant[bulkInsert.size()];
                 for(int i = 0; i < bulkInsert.size(); i++) {
                     iBulk[i] = bulkInsert.get(i);
                 }
                 rDao.insertRestaurants(iBulk);
            }

            Log.d("RUNJOB", "results done");

            updateWidget();

            return results;

        } catch (Exception e) {
            Log.d("RUNJOB", "error: "+e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(JsonResults jsonResults) {
        super.onPostExecute(jsonResults);
    }


    private void updateWidget() {

        Intent intent = new Intent(context, RestaurantWidgetProvider.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);

        int[] widgetIds = appWidgetManager.getAppWidgetIds(
                new ComponentName(context, RestaurantWidgetProvider.class)
        );

        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, widgetIds);
        context.sendBroadcast(intent);
    }
}



