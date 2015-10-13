package com.icomp.isscp;

import android.support.v7.app.AppCompatActivity;

import com.mark.mobile.utils.LogUtils;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onResume() {
        super.onResume();
        LogUtils.paintE("onResume:", this);
    }

    public MainApp getMainApp(){
        return (MainApp)this.getApplication();
    }
}
