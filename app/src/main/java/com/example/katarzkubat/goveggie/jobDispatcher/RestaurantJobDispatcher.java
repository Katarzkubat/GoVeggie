package com.example.katarzkubat.goveggie.jobDispatcher;

import android.content.Context;
import android.util.Log;

import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.Trigger;

import java.util.concurrent.TimeUnit;

public class RestaurantJobDispatcher {

    private static final String REMINDER_JOB_TAG = "reminder_job_tag";
    private static final int REMINDER_INTERVAL_MINUTES = 1;
    private static final int REMINDER_INTERVAL_SECONDS = (int) (TimeUnit.MINUTES.toSeconds(REMINDER_INTERVAL_MINUTES));
    private static final int SYNC_FLEXTIME_SECONDS = REMINDER_INTERVAL_SECONDS;

    public static void scheduleJob(Context context) {

        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(context));

        Job constraintReminderJob = dispatcher.newJobBuilder()
                .setService(RestaurantJobService.class)
                .setTag(REMINDER_JOB_TAG)
                .setConstraints(Constraint.ON_UNMETERED_NETWORK)
                .setLifetime(Lifetime.FOREVER)
                .setRecurring(true)
                .setTrigger(Trigger.executionWindow(10,60))
                .setReplaceCurrent(true)
                //.setRetryStrategy(RetryStrategy.DEFAULT_EXPONENTIAL)
                .build();
        Log.d("RUNJOB", "scheduled");
        dispatcher.mustSchedule(constraintReminderJob);
    }
}
