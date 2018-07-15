package com.api.local.sdk;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.api.local.sdk.jobs.LsSyncJob;

public class BaseActivity extends AppCompatActivity {
    protected Context mContext;
    Intent serviceIntent = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = BaseActivity.this;
        serviceIntent = new Intent(mContext, LsServicePlus.class);
        startService(serviceIntent);
        setDozeReceiver();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
    }

    private void setDozeReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(PowerManager.ACTION_DEVICE_IDLE_MODE_CHANGED);
        this.registerReceiver(new BroadcastReceiver()
        {
            @Override
            public void onReceive(Context context, Intent intent)
            {
                PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
                Log.d("LsService", "Check Idle: "+pm.isDeviceIdleMode());
                if(pm.isDeviceIdleMode()) {
                    LsSyncJob.scheduleJob();
                }
            }
        }, filter);
    }
}
