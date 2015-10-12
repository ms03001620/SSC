package com.icomp.isscp;

import android.app.Application;
import android.test.ApplicationTestCase;

import com.icomp.isscp.resp.RespLogin;
import com.mark.mobile.volley.RespListenerToast;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
    }

    public void testHello() throws Exception {
        final int expected = 1;
        final int reality = 1;
        assertEquals(expected, reality);
    }

    public void testLogin() throws Exception {
        NetTaskContext.getInstance().doLogin("1100011", "123456", new RespListenerToast<RespLogin>() {
            @Override
            public void onResponse(RespLogin resp) {
                assertFalse(resp.isError());
            }
        });
    }

}