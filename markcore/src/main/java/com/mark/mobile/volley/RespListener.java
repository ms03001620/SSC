package com.mark.mobile.volley;


import com.android.volley.Response;
import com.android.volley.VolleyError;

public abstract class RespListener<T> implements Response.Listener<T>, Response.ErrorListener, DialogListener{

    public void onShow(){

    }
    public void onDismiss(){

    }

    public void onErrorResponse(VolleyError var1){

    }
}
