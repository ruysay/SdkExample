package com.api.local.sdk;

import android.annotation.SuppressLint;
import android.app.IntentService;
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


public class LsServicePlus extends IntentService {
    private static final String TAG = LsServicePlus.class.getSimpleName();
    public static final int NORMAL_INTERVAL = 60*1000;
    private final IBinder mBinder = new LocalBinder();
    private Handler mHandler = new Handler();

    private Runnable mRunnableTimer   = new Runnable(){
        @SuppressLint("StaticFieldLeak")
        @Override
        public void run() {
            //Must run in async way
            new AsyncTask<Void, Void, Exception>() {
                @Override
                protected Exception doInBackground(Void... params) {
                    WebApiHandler.getInstance().request(QueryUtil.getInstance().getUrl(),
                            mResponseListener);
                    return null;
                }

                @Override //after request complete
                protected void onPostExecute(Exception result) {
                    if (result != null) {
                        Log.e(TAG, "Failed to init recognizer " + result);
                    } else {
                        mHandler.postDelayed(mRunnableTimer, NORMAL_INTERVAL);
                    }
                }
            }.execute();
        }
    };

    private ResponseListener mResponseListener = new ResponseListener();
    public LsServicePlus() {
        super("LsServicePlus");
    }

    private class ResponseListener implements IResponseListener{
        @Override
        public void onResult(Comment comment) {
            Log.i(TAG, "onResult: "+comment.getId()+", "+comment.getBody());
        }
    }

    public class LocalBinder extends Binder {
        public LsServicePlus getService() {
            // Return this instance of LocalService so clients can call public methods
            return LsServicePlus.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        //execute task
        mHandler.post(mRunnableTimer);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // Display a notification about us starting.  We put an icon in the status bar.
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        mHandler.removeCallbacks(mRunnableTimer);
        super.onDestroy();
    }
}
