package com.api.local.sdk.interfaces;

import com.api.local.sdk.response.Comment;

public interface IResponseListener {
    void onResult(Comment comment);
}
