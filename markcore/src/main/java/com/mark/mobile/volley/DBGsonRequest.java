package com.mark.mobile.volley;


import android.net.Uri;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.mark.mobile.mapper.ContentMapper;

import java.util.Map;

public class DBGsonRequest<T> extends GsonRequest<T> {
    public DBGsonRequest(String url, RespListener listener, Class responseType, Map param) {
        super(url, listener, responseType, param);
    }

    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        Response<T> resutl = super.parseNetworkResponse(response);
        ContentMapper.overwrite(resutl.result, getUri(), mResponseType);
        return resutl;
    }

    public Uri getUri() {
        return null;
    }

}
