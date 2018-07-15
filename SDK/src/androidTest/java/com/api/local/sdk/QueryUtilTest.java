package com.api.local.sdk;

import com.api.local.sdk.web.QueryUtil;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.fail;

public class QueryUtilTest {

    private static int mId = 1;
    private static int mPostId = 1;
    @Before
    public void setUp() {
        try {
            QueryUtil.getInstance();
            QueryUtil.getInstance().setCurrentPostId(mPostId);
            QueryUtil.getInstance().setCurrentCommentId(mId);

        } catch (Exception err) {
            fail();
        }
    }
    @Test
    public void QueryUtilFunctionTest() {
        assertNotNull(QueryUtil.getInstance().getUrl());
        assertEquals(QueryUtil.getInstance().getCurrentPostId(), mPostId);
        assertEquals(QueryUtil.getInstance().getCurrentCommentId(), mId);
    }
}
