package com.icomp.isscp.fragment;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.webkit.WebView;

/**
 * Created by Mark on 2015/10/29.
 */
public abstract class BaseFragment extends Fragment {
    private WebFragment.OnFragmentInteractionListener mListener;
    protected WebView mWebview;

    public void onButtonPressed(String url) {
        if (mListener != null) {
            mListener.onFragmentInteraction(url);
        }
    }

    public void onPageLoad(String url){
        mWebview.loadUrl(url);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (WebFragment.OnFragmentInteractionListener) activity;
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
