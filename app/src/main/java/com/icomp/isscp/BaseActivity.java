package com.icomp.isscp;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.RemoteViews;

import com.mark.mobile.utils.LogUtils;

public class BaseActivity extends AppCompatActivity {
    NotificationManager manager;
    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogUtils.paintE("onResume:", this);
    }

    public MainApp getMainApp(){
        return (MainApp)this.getApplication();
    }


    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(menuItem);
    }

    public void showNotifyLoading(){
        Notification myNotify = new Notification();
        myNotify.icon = R.drawable.icon_tel;
        myNotify.tickerText = "加载中";
        myNotify.when = System.currentTimeMillis();
        myNotify.flags = Notification.FLAG_NO_CLEAR;// 不能够自动清除
   /*      RemoteViews rv = new RemoteViews(getPackageName(), R.layout.my_notification);
       rv.setTextViewText(R.id.text_content, "hello wrold!");
        myNotify.contentView = rv;*/
        Intent intent = new Intent(Intent.ACTION_MAIN);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 1, intent, 1);
        myNotify.contentIntent = contentIntent;
        manager.notify(100159, myNotify);
    }

    public void dismissNotifyLoading() {
        manager.cancel(100159);
    }


}
