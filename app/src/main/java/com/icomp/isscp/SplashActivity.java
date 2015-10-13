package com.icomp.isscp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import java.lang.ref.WeakReference;

public class SplashActivity extends BaseActivity {

    private UIHandler mHandler = null;
    private final static int ACTION_ENTER_MAIN = 30010;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        //getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
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
        return false;
    }

    private void goMainPage() {
        startActivity(new Intent(this, MainActivity.class));
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
