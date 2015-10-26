package com.mark.mobile.volley;

import android.app.ProgressDialog;
import android.content.Context;


public abstract class RespListenerDialogToast<T> extends RespListenerToast<T> {
    private ProgressDialog mDialog;

    public RespListenerDialogToast(Context context, String message, int theme){
        if(theme!=0){
            mDialog = new ProgressDialog(context, theme);
        }else{
            mDialog = new ProgressDialog(context);
        }

        mDialog.setMessage(message);
        mDialog.setCancelable(true);
        mDialog.setCanceledOnTouchOutside(false);
    }

    public RespListenerDialogToast(Context context, String message){
        this(context, message, 0);
    }

    public RespListenerDialogToast(Context context, int theme){
        this(context, "正在提交服务器，请稍后...", theme);
    }

    public RespListenerDialogToast(Context context){
        this(context, "正在提交服务器，请稍后...", 0);
    }

    public void onShow() {
        mDialog.show();
    }

    public void onDismiss() {
        mDialog.dismiss();
    }

}
