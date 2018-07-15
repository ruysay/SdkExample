package com.api.local.sdk;


import android.app.Application;

import com.api.local.sdk.jobs.LsJobCreator;
import com.evernote.android.job.JobManager;

public class LsApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        JobManager.create(this).addJobCreator(new LsJobCreator());
    }
}