package com.api.local.demo;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.api.local.sdk.BaseActivity;
import com.api.local.sdk.LsService;

public class MainActivity extends BaseActivity {
    private Boolean mIsConnectionEstablished = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    LsService mService;
    boolean mBound = false;


    @Override
    protected void onStart() {
        super.onStart();
        // Bind to LocalService
        Intent serviceIntent = new Intent(this, LsService.class);
        //bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
        mIsConnectionEstablished = true;
        startService(serviceIntent);
    }

    @Override
    protected void onDestroy() {
        Intent serviceIntent = new Intent(this, LsService.class);
        stopService(serviceIntent); // make sure Sdl Service is stopped.
        mIsConnectionEstablished = false;
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        super.onStop();
        //unbindService(mConnection);
        mBound = false;
    }

    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            LsService.LocalBinder binder = (LsService.LocalBinder) service;
            mService = binder.getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
        }
    };
}
