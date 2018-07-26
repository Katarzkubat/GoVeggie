package com.example.katarzkubat.goveggie.Utilities;

import android.os.Looper;
import android.support.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import android.os.Handler;

//Here I've based on code from tutorial about architecture components

public class AppExecutors {

    private static final Object LOCK = new Object();
    private static AppExecutors sInstance;
    private final Executor diskIO;
   // private final Executor mainThread;
   // private final Executor networkIO;

    public AppExecutors(Executor diskIO, Executor mainThread, Executor networkIO) {
        this.diskIO = diskIO;
      //  this.mainThread = mainThread;
     //   this.networkIO = networkIO;
    }

    public static AppExecutors getsInstance() {
        if(sInstance == null) {
            synchronized (LOCK) {
                sInstance = new AppExecutors(Executors.newSingleThreadExecutor(),
                        Executors.newFixedThreadPool(3),
                        new MainThreadExecutor());
            }
        }
        return sInstance;
    }

    public Executor diskIO() {
        return diskIO;
    }

  //  public Executor mainThread() {
   //     return mainThread;
   // }

   // public Executor networkIO() {
   //     return networkIO;
   // }

    private static class MainThreadExecutor implements Executor {

        private Handler mainThreadHandler = new Handler(Looper.getMainLooper());

        @Override
        public void execute(@NonNull Runnable runnable) {
            mainThreadHandler.post(runnable);

        }
    }
}
