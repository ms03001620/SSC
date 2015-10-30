package com.icomp.isscp.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.icomp.isscp.R;
import com.icomp.isscp.SettingActivity;
import com.icomp.isscp.resp.RespLogin;
import com.mark.mobile.utils.LogUtils;

public class WebFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mUrl;
    private RespLogin mUser;
    private OnFragmentInteractionListener mListener;
    private WebView webview;

    public WebFragment() {
    }

    public static WebFragment newInstance(String param1, RespLogin param2) {
        WebFragment fragment = new WebFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putParcelable(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.menu_main);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.action_settings) {
                    Intent intent = new Intent(getActivity(), SettingActivity.class);
                    intent.putExtra("data-user", mUser);
                    getActivity().startActivityForResult(intent, 10010);
                    return true;
                }
                return false;
            }
        });

        if (mUrl.endsWith("My/Index")) {
            toolbar.setVisibility(View.VISIBLE);
        } else {
            toolbar.setVisibility(View.GONE);
        }

        webview = (WebView) view.findViewById(R.id.webview);
        webview.getSettings().setJavaScriptEnabled(true);
        //webview.addJavascriptInterface(new MyJavaScriptInterface(), "HTMLOUT");

        webview.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                LogUtils.paintD("shouldOverrideUrlLoading:", url);
                onButtonPressed(url);
                view.loadUrl(url);
                return true;
            }

            public void onPageFinished(WebView view, String url) {
                //webview.loadUrl("javascript:window.HTMLOUT.processHTML(document.getElementsByTagName('html')[0].innerHTML);");
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
            }

            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            }
        });

        LogUtils.paintD("loadUrl:", mUrl);
        if (!TextUtils.isEmpty(mUrl)) {
            webview.loadUrl(mUrl);
        }
        return view;
    }

    class MyJavaScriptInterface {
        @JavascriptInterface
        @SuppressWarnings("unused")
        public void processHTML(String html){
            LogUtils.paintD("processHTML:", html);
        }
    }


    public void onButtonPressed(String uri) {
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
        void onFragmentInteraction(String uri);
    }
}
