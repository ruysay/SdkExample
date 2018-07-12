package com.api.local.sdk;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import static android.app.AlarmManager.RTC;

public class BaseActivity extends AppCompatActivity {
    protected Context mContext;

    Intent serviceIntent = null;
    PendingIntent servicePendingIntent = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = BaseActivity.this;

        serviceIntent = new Intent(mContext, LsService.class);
        // make sure you **don't** use *PendingIntent.getBroadcast*, it wouldn't work
        servicePendingIntent =
                PendingIntent.getService(mContext,
                        LsService.SERVICE_ID, // integer constant used to identify the service
                        serviceIntent,
                        PendingIntent.FLAG_CANCEL_CURRENT);  // FLAG to avoid creating a second service if there's already one running
        AlarmManager am = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
        am.setAndAllowWhileIdle(RTC, LsService.INTERVAL, servicePendingIntent);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
    }


}
