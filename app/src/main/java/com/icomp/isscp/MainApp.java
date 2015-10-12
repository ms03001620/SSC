package com.icomp.isscp;

import com.mark.mobile.utils.PreferencesUtils;

public class MainApp extends com.mark.mobile.MainApp{

    @Override
    public void onCreate() {
        super.onCreate();
        PreferencesUtils.init(this, "com.goufang.mobile");
    }

}
