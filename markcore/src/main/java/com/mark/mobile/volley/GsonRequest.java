package com.mark.mobile.volley;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.mark.mobile.mapper.ContentMapper;
import com.mark.mobile.utils.LogUtils;

import java.util.Map;

public class GsonRequest<T> extends BasePostRequest<T> {
    private static final String TAG = "GsonRequest";

    private final RespListener<T> mListener;
    protected Class<T> mResponseType;

    public GsonRequest(String url, final RespListener listener , Class responseType, Map<String, String> param) {
        super(url, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if(listener!=null){
                    listener.onErrorResponse(volleyError);
                    listener.onDismiss();
                }
            }
        }, param);
        this.mListener = listener;
        if(listener!=null){
            listener.onShow();
        }
        this.mResponseType = responseType;
    }

    protected void deliverResponse(T response) {
        if(mListener!=null){
            mListener.onResponse(response);
            mListener.onDismiss();
        }
    }

    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        String parsed;
        try {
            //Thread.sleep(3000);
            parsed = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            LogUtils.paintW(TAG, parsed);
            Gson gson = ContentMapper.getDefaultContentMapper().getGson();
            T t = gson.fromJson(parsed, mResponseType);
            return Response.success(t, HttpHeaderParser.parseCacheHeaders(response));

        } catch (Exception var4) {
            parsed = new String(response.data);
        }
        return null;
    }
}
