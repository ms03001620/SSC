package com.icomp.isscp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.icomp.isscp.resp.RespLogin;
import com.mark.mobile.utils.PreferencesUtils;

import java.lang.ref.WeakReference;

public class SplashActivity extends BaseActivity {

    private UIHandler mHandler = null;
    private final static int ACTION_ENTER_MAIN = 30010;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mHandler = new UIHandler(new WeakReference<>(this));
        mHandler.sendEmptyMessageDelayed(ACTION_ENTER_MAIN, 2000);
    }

    private static class UIHandler extends Handler {
        public WeakReference<SplashActivity> mActivity;
        public UIHandler(WeakReference<SplashActivity> activity) {
            this.mActivity = activity;
        }

        @Override
        public void handleMessage(Message msg) {
            final SplashActivity activity = mActivity.get();
            switch (msg.what) {
                case ACTION_ENTER_MAIN:
                    if (activity.hasLogined()) {
                        activity.goMainPage();
                    } else {
                        activity.goMainLogin();
                    }
                    activity.finish();
                    break;
            }
            super.handleMessage(msg);
        }
    }

    private boolean hasLogined(){
        String access = PreferencesUtils.getString("data-json-string", "");
        if(!TextUtils.isEmpty(access)){
            return true;
        }
        return false;
    }

    private void goMainPage() {
        String access = PreferencesUtils.getString("data-json-string", "");
        Gson gson = new Gson();
        RespLogin login = gson.fromJson(access, RespLogin.class);
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        intent.putExtra("data-user", login);
        startActivity(intent);
    }

    private void goMainLogin() {
        startActivity(new Intent(this, LoginActivity.class));
    }

    @Override
    public void onBackPressed() {
        mHandler.removeMessages(ACTION_ENTER_MAIN);
        super.onBackPressed();
    }
}
