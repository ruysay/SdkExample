package com.api.local.sdk.jobs;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;

import com.api.local.sdk.LsServicePlus;
import com.evernote.android.job.Job;
import com.evernote.android.job.JobManager;
import com.evernote.android.job.JobRequest;

import java.util.Set;
import java.util.concurrent.TimeUnit;

public class LsSyncJob extends Job {

    public static final String TAG = "ls_sync_job";//LsSyncJob.class.getSimpleName();

    @Override
    @NonNull
    protected Result onRunJob(@NonNull Params params) {
        Log.d(TAG, "onRunJob");
        Intent service = new Intent(this.getContext(), LsServicePlus.class);
        this.getContext().startService(service);
        return Result.SUCCESS;
    }

    public static void scheduleJob() {

        Set<JobRequest> jobRequests = JobManager.instance().getAllJobRequestsForTag(LsSyncJob.TAG);
        if (!jobRequests.isEmpty()) {
            Log.d(TAG, "Already scheduled a job in the list");
            return;
        }
        new JobRequest.Builder(LsSyncJob.TAG)
                .setPeriodic(TimeUnit.MINUTES.toMillis(15), TimeUnit.MINUTES.toMillis(7))
                .setUpdateCurrent(true) // calls cancelAllForTag(NoteSyncJob.TAG) for you
                .setRequiredNetworkType(JobRequest.NetworkType.CONNECTED)
                .setRequirementsEnforced(true)
                .build()
                .schedule();
    }
}