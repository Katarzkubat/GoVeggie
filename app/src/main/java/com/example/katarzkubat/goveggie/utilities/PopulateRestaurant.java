package com.example.katarzkubat.goveggie.utilities;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.example.katarzkubat.goveggie.model.JsonResults;
import com.example.katarzkubat.goveggie.R;
import com.example.katarzkubat.goveggie.database.AppDatabase;
import com.example.katarzkubat.goveggie.database.RestaurantDao;
import com.example.katarzkubat.goveggie.model.Restaurant;
import com.example.katarzkubat.goveggie.widget.RestaurantWidgetProvider;

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

        if (SavingLocation.mLastKnownLocation == null) {
            return OpenJsonUtils
                    .getRestaurantFromJson("");
        }
        restaurantRequestUrl = NetworkUtils.buildRestaurantUrl
                (context.getResources().getString(R.string.api_key), restaurantPreferences,
                        SavingLocation.mLastKnownLocation.getLatitude(),
                        SavingLocation.mLastKnownLocation.getLongitude());

        try {
            String jsonResponse = NetworkUtils
                    .getResponseFromHttpUrl(restaurantRequestUrl);

            JsonResults results = OpenJsonUtils
                    .getRestaurantFromJson(jsonResponse);
            RestaurantDao rDao = AppDatabase.getInstance(context).restaurantDao();

            rDao.setAllInvisible();

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

                if (singleRestaurant.getRow_id() <= 0) {
                    bulkInsert.add(singleRestaurant);
                }
            }

            if (bulkUpdate.size() > 0) {

                Restaurant[] uBulk = new Restaurant[bulkUpdate.size()];
                for (int i = 0; i < bulkUpdate.size(); i++) {
                    uBulk[i] = bulkUpdate.get(i);
                }

                rDao.updateRestaurants(uBulk);
            }

            if (bulkInsert.size() > 0) {
                Restaurant[] iBulk = new Restaurant[bulkInsert.size()];
                for (int i = 0; i < bulkInsert.size(); i++) {
                    iBulk[i] = bulkInsert.get(i);
                }
                rDao.insertRestaurants(iBulk);
            }

            updateWidget();

            return results;

        } catch (Exception e) {

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



