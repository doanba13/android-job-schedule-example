package com.example.androidperformenceexample;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void scheduleJob(View v) {
        ComponentName componentName = new ComponentName(this, ExampleJobService.class);
        JobInfo info = new JobInfo.Builder(111, componentName)
                .setRequiresCharging(true) // Start the job only when device charging
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED) // Only execute job when have wifi connection
                .setPersisted(true) // Keep job alive when reboot
                .setPeriodic(15*60*1000) // Specify that job should recur (repeat) with provided interval, not more than one per period
                .build();

        JobScheduler scheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        int resultCode = scheduler.schedule(info);

        if (resultCode == JobScheduler.RESULT_SUCCESS){
            Log.d(TAG, "Job Scheduled!");
        } else {
            Log.d(TAG, "Job scheduling failed!");
        }
    }

    public void cancelJob(View v) {
        JobScheduler scheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        scheduler.cancel(111);

        Log.d(TAG, "Job cancelled!");
    }
}
