package com.epereyra.didipay.notification;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.util.Log;

import com.epereyra.didipay.util.Util;

public class NotificationJobService extends JobService {

    private static final String TAG = "NotificationService";

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.d("START JOB", "TEST");
        Util.scheduleJob(getApplicationContext()); // reschedule the job
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return true;
    }
}
