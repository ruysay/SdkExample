package com.api.local.sdk.web;

public class QueryUtil {
    public static final int MAX_POST_ID = 100;
    public static final int MAX_COMMENT_ID = 500;
    private String BASE_URL = "https://jsonplaceholder.typicode.com/comments?postId=";
    private static QueryUtil mInstance = null;
    private static final Object mLock = new Object();

    private String mQueryUrl = null;
    private int mCurrentPostId = 1;
    private int mCurrentCommentId = 1;

    public static QueryUtil getInstance() {
        synchronized (mLock) {
            if(mInstance == null) {
                mInstance = new QueryUtil();
            }
            return mInstance;
        }
    }

    public String getUrl() {
        mQueryUrl = BASE_URL+mCurrentPostId;
        return mQueryUrl;
    }

    public void setCurrentPostId(int id) {
        mCurrentPostId = id;
    }

    public int getCurrentPostId() {
        return mCurrentPostId;
    }

    public void setCurrentCommentId(int id) {
        mCurrentCommentId = id;
    }

    public int getCurrentCommentId() {
        return mCurrentCommentId;
    }
}
