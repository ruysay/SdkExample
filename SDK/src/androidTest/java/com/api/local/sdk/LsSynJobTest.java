package com.api.local.sdk;

import android.support.test.InstrumentationRegistry;

import com.api.local.sdk.jobs.LsJobCreator;
import com.api.local.sdk.jobs.LsSyncJob;
import com.evernote.android.job.JobManager;
import com.evernote.android.job.JobRequest;

import org.junit.Before;
import org.junit.Test;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import static android.test.MoreAsserts.assertNotEqual;
import static junit.framework.Assert.fail;

public class LsSynJobTest {

    private Set<JobRequest> jobRequests = null;
    private int jobId = -1;

    @Before
    public void setup() {
        JobManager.create(InstrumentationRegistry.getTargetContext()).addJobCreator(new LsJobCreator());
        JobManager.instance().cancelAllForTag(LsSyncJob.TAG);
        jobRequests = JobManager.instance().getAllJobRequestsForTag(LsSyncJob.TAG);
    }
    @Test
    public void scheduleJobTest() {
        if (!jobRequests.isEmpty()) {
            fail();
            return;
        }
        jobId = new JobRequest.Builder(LsSyncJob.TAG)
                .setPeriodic(TimeUnit.MINUTES.toMillis(15), TimeUnit.MINUTES.toMillis(7))
                .setUpdateCurrent(true) // calls cancelAllForTag(NoteSyncJob.TAG) for you
                .setRequiredNetworkType(JobRequest.NetworkType.CONNECTED)
                .setRequirementsEnforced(true)
                .build()
                .schedule();
        assertNotEqual(jobId, -1);
    }
}
