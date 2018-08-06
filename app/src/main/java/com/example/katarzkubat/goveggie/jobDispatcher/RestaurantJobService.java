package com.example.katarzkubat.goveggie.jobDispatcher;

import android.os.AsyncTask;

import com.example.katarzkubat.goveggie.R;
import com.example.katarzkubat.goveggie.utilities.PopulateRestaurant;
import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;


public class RestaurantJobService extends JobService {

    @Override
    public boolean onStartJob(JobParameters jobParameters) {

       new PopulateRestaurant(RestaurantJobService.this).execute(getResources().getString(R.string.api_key));
        jobFinished(jobParameters, true);
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return true;
    }
}

