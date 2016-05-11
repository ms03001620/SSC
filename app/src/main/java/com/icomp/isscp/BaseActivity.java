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

import com.mark.mobile.utils.LogUtils;
import com.mark.mobile.utils.PreferencesUtils;

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



    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(menuItem);
    }


    public void logout(){
        PreferencesUtils.remove("data-json-string");
        startActivity(new Intent(this, LoginActivity.class));
        setResult(RESULT_OK);
        finish();
    }


}
