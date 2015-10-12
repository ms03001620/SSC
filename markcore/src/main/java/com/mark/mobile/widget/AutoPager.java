package com.mark.mobile.widget;

import java.lang.ref.WeakReference;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * 自动切换Pager
 * @author mazj
 */
public class AutoPager extends ViewPager {
	private final static String TAG = "AutoPager";
	
	private final static int PLAY = 1;
	private final static int STOP = 2;
	private int mCurrentIntex;
	private UIHandler mHandler = null;

	public AutoPager(Context context) {
		this(context, null);
	}
	
	public AutoPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		mHandler = new UIHandler(new WeakReference<AutoPager>(this));
	}
	
	@Override
	protected void onVisibilityChanged(View changedView, int visibility) {
		Log.d(TAG, "onVisibilityChanged, visibility="+visibility);
		if(visibility == View.VISIBLE){
			autoChange();
		}else{
			stopChange();
		}
		super.onVisibilityChanged(changedView, visibility);
	}
	
	@Override
	protected void onAttachedToWindow() {
		Log.d(TAG, "onAttachedToWindow=");
		super.onAttachedToWindow();
		autoChange();
	}
	
	@Override
	protected void onDetachedFromWindow() {
		Log.d(TAG, "onDetachedFromWindow=");
		super.onDetachedFromWindow();
		stopChange();
	}

	private void autoChange(){
		PagerAdapter adaper = this.getAdapter();
		if(adaper==null || adaper.getCount()<2){
			return;
		}
		if(!mHandler.hasMessages(PLAY)){
			mHandler.sendEmptyMessageDelayed(PLAY, 7000);
		}
	}
	
	private boolean stopChange(){
		if(mHandler.hasMessages(PLAY)){
			mHandler.removeMessages(PLAY);
			return true;
		}
		return false;
	}
	
	private static class UIHandler extends Handler {
		public WeakReference<AutoPager> view;

		public UIHandler(WeakReference<AutoPager> view) {
			this.view = view;
		}

		@Override
		public void handleMessage(Message msg) {
			final AutoPager activity = view.get();
			if(activity==null){
				return;
			}
			switch (msg.what) {
				case PLAY:
					activity.setCurrentItemAuto();
					//start next change with delay
					activity.autoChange();
					break;
				case STOP:
					//stop change
					activity.stopChange();
					break;
			}
			super.handleMessage(msg);
		}
	}
	
	public void setCurrentItemAuto() {
		PagerAdapter adaper = getAdapter();
		if(adaper!=null){
			int count = adaper.getCount();
			if(count>0){
				setCurrentItem(++mCurrentIntex % count);
			}
		}
	}
}
