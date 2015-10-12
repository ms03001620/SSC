package com.mark.mobile.volley;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.mark.mobile.MainApp;

import java.net.ConnectException;
import java.net.UnknownHostException;


public abstract class RespListenerToast<T> extends RespListener<T> {

    @Override
    public void onErrorResponse(VolleyError volleyError) {
        decodeVolleyError(volleyError);
    }
    public void decodeVolleyError(VolleyError error){
        long timeMs = error.getNetworkTimeMs();
        String msg = "time passed:"+ timeMs;
        if(error.getCause() instanceof UnknownHostException){
            msg+=", reason:"+"无法访问服务器";
        }else if(error.getCause() instanceof ConnectException){
            msg+=", reason:"+"无法打开网络连接";
        }else if(error instanceof TimeoutError){
            msg+=", reason:"+"连接超时";
        }else if(error instanceof ServerError){
            msg+=", reason:"+"服务器错误";
        }
        Toast.makeText(MainApp.getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    public void onShow(){}
    public void onDismiss(){}

}
