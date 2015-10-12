package com.mark.mobile;

import android.app.Application;
import android.content.Context;

import com.mark.mobile.utils.AppUtils;

public class MainApp extends Application{

    private static Context sContext;
    private static boolean sDebug;

    public void onCreate() {
        super.onCreate();
        sContext = getApplicationContext();
        sDebug = AppUtils.isApkDebugable(this);
    }

    public static Context getContext() {
        return sContext;
    }
    public static boolean isDebug(){
        return sDebug;
    }
}
