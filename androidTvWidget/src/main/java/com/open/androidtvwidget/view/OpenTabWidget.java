package com.open.androidtvwidget.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TabWidget;

public class OpenTabWidget extends TabWidget {

	public OpenTabWidget(Context context) {
		super(context);
	}

	public OpenTabWidget(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

//	@Override
//	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
//		super.onMeasure(expandSpec, heightMeasureSpec);
//	}
	
}
