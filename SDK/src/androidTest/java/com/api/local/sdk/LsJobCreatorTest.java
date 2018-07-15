package com.api.local.sdk;

import android.support.test.InstrumentationRegistry;

import com.api.local.sdk.jobs.LsJobCreator;
import com.evernote.android.job.JobManager;

import org.junit.Test;

public class LsJobCreatorTest {
    @Test
    public void QueryUtilFunctionTest() {

        JobManager.create(InstrumentationRegistry.getTargetContext()).addJobCreator(new LsJobCreator());
    }
}
