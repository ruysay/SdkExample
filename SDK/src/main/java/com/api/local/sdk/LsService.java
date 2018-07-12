package com.api.local.sdk;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.api.local.sdk.interfaces.IResponseListener;
import com.api.local.sdk.response.Comment;
import com.api.local.sdk.web.QueryUtil;
import com.api.local.sdk.web.WebApiHandler;


public class LsService extends Service {
    private static final String TAG = LsService.class.getSimpleName();
    public static final int INTERVAL = 5*100;
    public static final int SERVICE_ID = 1101;
    private final IBinder mBinder = new LocalBinder();
    private Handler mHandler = new Handler();

    private Runnable mRunnableTimer   = new Runnable(){
        @Override
        public void run() {
            Log.d(TAG, "running");
            //Must run in async way
            new AsyncTask<Void, Void, Exception>() {
                @Override
                protected Exception doInBackground(Void... params) {
                    WebApiHandler.getInstance().request(QueryUtil.getInstance().getUrl(),
                            mResponseListener);
                    return null;
                }

                @Override //after recognizer initialization complete
                protected void onPostExecute(Exception result) {
                    if (result != null) {
                        Log.e(TAG, "Failed to init recognizer " + result);
                    } else {
                        mHandler.postDelayed(mRunnableTimer, INTERVAL);
                    }
                }
            }.execute();
        }
    };

    private ResponseListener mResponseListener = new ResponseListener();
    private class ResponseListener implements IResponseListener{
        @Override
        public void onResult(Comment comment) {
            Log.i(TAG, "onResult: "+comment.getId()+", "+comment.getBody());
        }
    }

    public class LocalBinder extends Binder {
        public LsService getService() {
            // Return this instance of LocalService so clients can call public methods
            return LsService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onCreate() {

        // Display a notification about us starting.  We put an icon in the status bar.
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        mHandler.post(mRunnableTimer);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        // Cancel the persistent notification.

        // Tell the user we stopped.
    }
}
