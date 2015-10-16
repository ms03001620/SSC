package com.icomp.isscp.fragment;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.icomp.isscp.R;
import com.icomp.isscp.resp.RespLogin;
import com.mark.mobile.utils.LogUtils;


public class WebFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mUrl;
    private RespLogin mUser;
    private OnFragmentInteractionListener mListener;
    private WebView webview;
    private ProgressBar progressBar;
    NotificationManager manager;

    public static WebFragment newInstance(String param1, RespLogin param2) {
        WebFragment fragment = new WebFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putParcelable(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public WebFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mUrl = getArguments().getString(ARG_PARAM1);
            mUser = getArguments().getParcelable(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_web, container, false);
        manager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
        webview = (WebView)view.findViewById(R.id.webview);
        progressBar = (ProgressBar)view.findViewById(R.id.progressbar);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            public void onPageFinished(WebView view, String url) {
                progressBar.setVisibility(View.GONE);
                //showNotifyLoading();
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                progressBar.setVisibility(View.VISIBLE);
                //dismissNotifyLoading();
            }

            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            }
        });

        String realUrl = makeRealUrl(mUrl, mUser);
        LogUtils.paintD("loadUrl:", realUrl);
        if(!TextUtils.isEmpty(mUrl)){
            webview.loadUrl(realUrl);
        }
        return view;
    }

    public boolean onBackPressed(){
        if(webview.canGoBack()){
            webview.goBack();
            return true;
        }
        return false;
    }


    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(Uri uri);
    }

    public void showNotifyLoading(){
        Notification myNotify = new Notification();
        myNotify.icon = R.drawable.icon_tel;
        myNotify.tickerText = "加载中";
        myNotify.when = System.currentTimeMillis();
        myNotify.flags = Notification.FLAG_NO_CLEAR;// 不能够自动清除
   /*      RemoteViews rv = new RemoteViews(getPackageName(), R.layout.my_notification);
       rv.setTextViewText(R.id.text_content, "hello wrold!");
        myNotify.contentView = rv;*/
        Intent intent = new Intent(Intent.ACTION_MAIN);
        PendingIntent contentIntent = PendingIntent.getActivity(getActivity(), 1, intent, 1);
        myNotify.contentIntent = contentIntent;
        manager.notify(100159, myNotify);

    }

    public void dismissNotifyLoading() {
        manager.cancel(100159);
    }


    public String makeRealUrl(String url, RespLogin user){
        if(!"http://dldx.test.sigilsoft.com/UserService/TokenLogin?TokenID=".equals(url)){
            return url;
        }
        StringBuffer sb = new StringBuffer();
        sb.append(url);
        sb.append(user.getReData());
        return sb.toString();
    }
}
