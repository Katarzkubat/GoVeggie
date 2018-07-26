package com.example.katarzkubat.goveggie.JobDispatcher;

import android.os.AsyncTask;
import android.util.Log;

import com.example.katarzkubat.goveggie.Utilities.PopulateRestaurant;
import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;


public class RestaurantJobService extends JobService {

    private AsyncTask mBackgroundTask;

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
Log.d("RUNJOB","runjob");
       new PopulateRestaurant(RestaurantJobService.this).execute();
        jobFinished(jobParameters, true);
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        Log.d("RUNJOB","onStop");

        return true;
    }
}
