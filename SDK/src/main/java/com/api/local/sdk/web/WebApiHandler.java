package com.api.local.sdk.web;

import android.util.Log;

import com.api.local.sdk.interfaces.IResponseListener;
import com.api.local.sdk.response.Comment;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.net.ssl.SSLException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WebApiHandler {

    private static final String TAG = WebApiHandler.class.getSimpleName();
    public static WebApiHandler mInstance = null;
    private static final Object mLock = new Object();
    private OkHttpClient client = new OkHttpClient();
    private Gson gson;


    public static WebApiHandler getInstance() {
        synchronized (mLock) {
            if(mInstance == null) {
                mInstance = new WebApiHandler();
            }
            return mInstance;
        }
    }

    private WebApiHandler() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
        gson = gsonBuilder.create();
    }

    public void request(String url, IResponseListener listenr) {
        Log.d("LsService", "==>request");
        Request request = new Request.Builder()
                .url(url)
                .build();
        try {
            Response response = client.newCall(request).execute();
            List<Comment> commentList = new ArrayList<Comment>();
            Comment result = null;
            JSONArray jsonArray = new JSONArray(response.body().string());
            if (jsonArray.length() > 0) {
                commentList = Arrays.asList(gson.fromJson(jsonArray.toString(), Comment[].class));
            }
            String currentPostId = String.valueOf(QueryUtil.getInstance().getCurrentPostId());
            String currentCommentId = String.valueOf(QueryUtil.getInstance().getCurrentCommentId());
            for(Comment comment: commentList) {
                if(comment.getId().equalsIgnoreCase(currentCommentId) ){
                    result = comment;
                }
            }

            if(QueryUtil.getInstance().getCurrentPostId() == QueryUtil.MAX_POST_ID && QueryUtil.getInstance().getCurrentCommentId() == QueryUtil.MAX_COMMENT_ID) {
                QueryUtil.getInstance().setCurrentPostId(0);
                QueryUtil.getInstance().setCurrentCommentId(0);
            }
            if(QueryUtil.getInstance().getCurrentCommentId() % 5 == 0) {
                int _nextPostId = Integer.valueOf(QueryUtil.getInstance().getCurrentPostId())+1;
                QueryUtil.getInstance().setCurrentPostId(_nextPostId);
            }
            int _nextCommentId = Integer.valueOf(QueryUtil.getInstance().getCurrentCommentId())+1;
            QueryUtil.getInstance().setCurrentCommentId(_nextCommentId);

            listenr.onResult(result);
        } catch (SSLException e2) {
            Log.e(TAG, e2.getMessage());
        } catch (SocketTimeoutException e3) {
            Log.e(TAG, e3.getMessage());
        } catch (ConnectException e4) {
            Log.e(TAG, e4.getMessage());
        } catch (UnknownHostException e5) {
            Log.e(TAG, e5.getMessage());
        } catch (JSONException e6) {
            Log.e(TAG, e6.getMessage());
        } catch (IOException e7) {
            Log.e(TAG, e7.getMessage());
        }
    }
}
