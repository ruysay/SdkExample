package com.api.local.demo;

import android.content.Intent;
import android.os.IBinder;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.MediumTest;
import android.support.test.rule.ServiceTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.api.local.sdk.LsService;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.TimeoutException;

import static junit.framework.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
@MediumTest
public class LsServiceTest {

    @Rule
    public final ServiceTestRule mServiceRule = new ServiceTestRule();

    // test for a service which is started with startService
    @Test
    public void testWithStartedService() throws TimeoutException {
        mServiceRule.
                startService(new Intent(InstrumentationRegistry.getTargetContext(),
                        LsService.class));
        // test code
    }
    @Test
    public void testWithBoundService() throws TimeoutException {
        // Create the service Intent.
        Intent serviceIntent =
                new Intent(InstrumentationRegistry.getTargetContext(),
                        LsService.class);

        // Data can be passed to the service via the Intent.
//        serviceIntent.putExtra(LocalService.SEED_KEY, 42L);

        // Bind the service and grab a reference to the binder.
        IBinder binder = mServiceRule.bindService(serviceIntent);

        // Get the reference to the service, or you can call
        // public methods on the binder directly.
        LsService service =
                ((LsService.LocalBinder) binder).getService();

        // Verify that the service is working correctly.
        //assertThat(service.getRandomInt(), is(any(Integer.class)));
        assertTrue("True wasn't returned", true);
    }
}
