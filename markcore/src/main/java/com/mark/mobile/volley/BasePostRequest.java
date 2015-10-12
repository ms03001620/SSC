package com.mark.mobile.volley;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;

import java.util.HashMap;
import java.util.Map;

public abstract class BasePostRequest<T> extends Request<T> {

    private Map<String, String> params;

    public BasePostRequest(String url, Response.ErrorListener errorListener, Map<String, String> params) {
        super(Method.DEPRECATED_GET_OR_POST, url, errorListener);

        this.params = params;
        if(this.params==null){
            this.params = new HashMap<String, String>();
        }
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return params;
    }

    public DefaultRetryPolicy getDefaultRetryPolicy(){
        DefaultRetryPolicy r = new DefaultRetryPolicy(30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        return r;
    }

    @Override
    public void addMarker(String tag) {
        super.addMarker(tag);
        Log.d("BasePostRequest mark:", tag);
    }
}
