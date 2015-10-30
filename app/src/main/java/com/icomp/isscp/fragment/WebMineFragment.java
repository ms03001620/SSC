package com.icomp.isscp.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.icomp.isscp.R;
import com.mark.mobile.utils.LogUtils;

public class WebMineFragment extends BaseFragment {
    private static final String ARG_PARAM1 = "WebMineFragment.param";
    private String mUrl;

    public static WebMineFragment newInstance(String param1) {
        WebMineFragment fragment = new WebMineFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mUrl = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_web_mine, container, false);
        final View setting = view.findViewById(R.id.action_setting);
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonPressed("app/action/setting");
            }
        });

        mWebview = (WebView) view.findViewById(R.id.webview);
        mWebview.getSettings().setJavaScriptEnabled(true);
        mWebview.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.endsWith("My/Index")) {
                    setting.setVisibility(View.VISIBLE);
                } else {
                    setting.setVisibility(View.GONE);
                }
                onButtonPressed(url);
                view.loadUrl(url);
                return true;
            }

            public void onPageFinished(WebView view, String url) {}
            public void onPageStarted(WebView view, String url, Bitmap favicon) {}
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {}
        });

        LogUtils.paintD("loadUrl:", mUrl);
        if (!TextUtils.isEmpty(mUrl)) {
            mWebview.loadUrl(mUrl);
        }
        return view;
    }

}
