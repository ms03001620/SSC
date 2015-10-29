package com.icomp.isscp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.icomp.isscp.resp.RespLogin;
import com.mark.mobile.utils.PreferencesUtils;

public class SplashActivity extends BaseActivity {

    private Handler mHandler = null;
    private final static int ACTION_ENTER_MAIN = 30010;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mHandler = new Handler(getMainLooper(), new Handler.Callback() {
            public boolean handleMessage(Message message) {
                switch(message.what){
                    case ACTION_ENTER_MAIN:
                        if(hasLogined()){
                            goMainPage();
                        }else{
                            goMainLogin();
                        }
                        return true;
                }
                return false;
            }
        });
        mHandler.sendEmptyMessageDelayed(ACTION_ENTER_MAIN, 2000);
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
        finish();
    }

    private void goMainLogin() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    @Override
    public void onBackPressed() {
        mHandler.removeMessages(ACTION_ENTER_MAIN);
        super.onBackPressed();
    }
}
