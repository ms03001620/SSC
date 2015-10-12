package com.mark.mobile.volley;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.mark.mobile.MainApp;


public class VolleyBus {
    private static VolleyBus instance;
    private Context mContext;

    private RequestQueue newRequestQueue;

    private VolleyBus(Context context){
        mContext = context;
        newRequestQueue = Volley.newRequestQueue(context);


    }
    public static VolleyBus getInstance() {
        if (instance == null) {
            instance = new VolleyBus(MainApp.getContext());
        }
        return instance;
    }
    public <T> void addToRequestQueue(BasePostRequest<T> req) {
        req.setRetryPolicy(req.getDefaultRetryPolicy());
        newRequestQueue.add(req);
    }

    public Context getContext(){
        return mContext;
    }



}
