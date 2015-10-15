package com.mark.mobile.volley;

import android.app.ProgressDialog;
import android.content.Context;


public abstract class RespListenerDialogToast<T> extends RespListenerToast<T> {
    private ProgressDialog mDialog;

    public RespListenerDialogToast(Context dialog){
        //ProgressDialog dialog = new ProgressDialog(this, R.style.MyTheme);
        mDialog = new ProgressDialog(dialog);
        mDialog.setMessage("加载中...");
        mDialog.setCancelable(true);
        mDialog.setCanceledOnTouchOutside(false);
    }

    public void onShow() {
        mDialog.show();
    }

    public void onDismiss() {
        mDialog.dismiss();
    }

}
