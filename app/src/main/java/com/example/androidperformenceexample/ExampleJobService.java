package com.example.androidperformenceexample;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.util.Log;

public class ExampleJobService extends JobService {
    private static final String TAG = "ExampleJobService";
    private boolean isJobCancelled = false;

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.d(TAG, "Job Started...");
        workingInBackground(params);

        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.d(TAG, "Job cancelled before completion");
        isJobCancelled = true;

        return true;
    }

    private void workingInBackground(JobParameters params){
        new Thread(() -> {
            for (int i = 0; i < 15; i++){
                Log.d(TAG, "running: " + i);
                if (isJobCancelled){
                    return;
                }

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            Log.d(TAG, "Job finished!");

            jobFinished(params, false);
        }).start();
    }
}
