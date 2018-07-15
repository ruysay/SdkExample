package com.api.local.sdk;

import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.MediumTest;
import android.support.test.rule.ServiceTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.TimeoutException;

@RunWith(AndroidJUnit4.class)
@MediumTest
public class LsServicePlusTest {

    @Rule
    public final ServiceTestRule mServiceRule = new ServiceTestRule();

    // test for a service which is started with startService
    @Test
    public void testWithStartedService() throws TimeoutException {
        mServiceRule.startService(new Intent(InstrumentationRegistry.getTargetContext(),
                        LsServicePlus.class));
        // test code
    }
}
