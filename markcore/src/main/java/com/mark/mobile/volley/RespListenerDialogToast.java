package com.mark.mobile.volley;

import android.app.Dialog;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.mark.mobile.MainApp;

import java.net.ConnectException;
import java.net.UnknownHostException;


public abstract class RespListenerDialogToast<T> extends RespListenerToast<T> {
    private Dialog mDialog;

    public RespListenerDialogToast(Dialog dialog){
        mDialog = dialog;
    }

    public void onShow() {
        mDialog.show();
    }

    public void onDismiss() {
        mDialog.dismiss();
    }

}
