package com.api.local.sdk;

import android.os.AsyncTask;
import android.util.Log;

import com.api.local.sdk.interfaces.IResponseListener;
import com.api.local.sdk.response.Comment;
import com.api.local.sdk.web.QueryUtil;
import com.api.local.sdk.web.WebApiHandler;

import org.junit.Test;
import java.util.concurrent.CountDownLatch;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.TestCase.fail;

public class WebApiHandlerTest {
    @Test
    public void WebApiHandlerQueryTest() {
        final CountDownLatch signal = new CountDownLatch(1);
        try {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    //Must run in async way
                    new AsyncTask<Void, Void, Exception>() {
                        @Override
                        protected Exception doInBackground(Void... params) {
                            WebApiHandler.getInstance().request(QueryUtil.getInstance().getUrl(),
                                    new IResponseListener() {
                                        @Override
                                        public void onResult(Comment comment) {
                                            assertNotNull(comment);
                                            assertNotNull(comment.getPostId());
                                            assertNotNull(comment.getId());
                                            assertNotNull(comment.getName());
                                            assertNotNull(comment.getEmail());
                                            assertNotNull(comment.getBody());
                                        }
                                    });
                            try {
                                signal.await();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                                fail();
                            }
                            return null;
                        }

                        @Override //after recognizer initialization complete
                        protected void onPostExecute(Exception result) {
                            if (result != null) {
                                //Ready to next action
                                fail();
                            }
                        }
                    }.execute();
                }
            }).start();
        } catch (Exception err) {
            fail();
        }
    }
}
