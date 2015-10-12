package com.mark.mobile.widget;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;

public class OnItemSelectedListenerWrapper implements OnItemSelectedListener {

    private int lastPosition;
    private OnItemSelectedListener listener;

    public OnItemSelectedListenerWrapper(OnItemSelectedListener aListener) {
        lastPosition = 0;
        listener = aListener;
    }

    @Override
    public void onItemSelected(AdapterView<?> aParentView, View aView, int aPosition, long anId) {
        if (lastPosition == aPosition) {
        } else {
            listener.onItemSelected(aParentView, aView, aPosition, anId);
        }
        lastPosition = aPosition;
    }

    @Override
    public void onNothingSelected(AdapterView<?> aParentView) {
        listener.onNothingSelected(aParentView);
    }

	public int getLastPosition() {
		return lastPosition;
	}

	public void setLastPosition(int lastPosition) {
		this.lastPosition = lastPosition;
	}
    
    
}