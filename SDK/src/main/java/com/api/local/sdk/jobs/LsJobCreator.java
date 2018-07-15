package com.api.local.sdk.jobs;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobCreator;

public class LsJobCreator implements JobCreator {

    @Override
    @Nullable
    public Job create(@NonNull String tag) {
        switch (tag) {
            case LsSyncJob.TAG:
                return new LsSyncJob();
            default:
                return null;
        }
    }
}