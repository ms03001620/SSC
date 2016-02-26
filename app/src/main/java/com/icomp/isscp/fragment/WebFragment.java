package com.icomp.isscp.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.icomp.isscp.MainActivity;
import com.icomp.isscp.MainApp;
import com.icomp.isscp.R;
import com.mark.mobile.utils.LogUtils;

public class WebFragment extends BaseFragment {
    private static final String ARG_PARAM1 = "WebFragment.param";
    private String mUrl;

    private ProgressBar mProgressBar;

    public static WebFragment newInstance(String param1) {
        WebFragment fragment = new WebFragment();
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
        View root = inflater.inflate(R.layout.fragment_web, container, false);
        mWebview = (WebView)root.findViewById(R.id.webview);
        mProgressBar = (ProgressBar)root.findViewById(R.id.progressbar);
        mWebview.getSettings().setJavaScriptEnabled(true);
        mWebview.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                onButtonPressed(url);
                view.loadUrl(url);
                return true;
            }

            public void onPageFinished(WebView view, String url) {
                mProgressBar.setVisibility(View.GONE);
            }
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                LogUtils.paintD("onPageStarted ", url);
                if(getUrsList()!=null){
                    if(getUrsList().contains(url)){
                        LogUtils.paintD("show ", url);
                        mProgressBar.setVisibility(View.VISIBLE);
                    }
                }
            }
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                mProgressBar.setVisibility(View.GONE);
            }
        });

        LogUtils.paintD("loadUrl:", mUrl);
        if (!TextUtils.isEmpty(mUrl)) {
            mWebview.loadUrl(mUrl);
        }
        return root;
    }

}
