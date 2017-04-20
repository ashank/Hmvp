package com.funhotel.tvllibrary.view;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by dell on 2016/10/15.
 * ListView重新获取焦点时，让其重新选中上次被选的item
 */

public class PlayerMenuListView extends ListView{

    public PlayerMenuListView(Context context) {
        super(context);
    }

    public PlayerMenuListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PlayerMenuListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onFocusChanged(boolean gainFocus, int direction, Rect previouslyFocusedRect) {
        int lastSelectItem = getSelectedItemPosition();
        super.onFocusChanged(gainFocus, direction, previouslyFocusedRect);
        if (gainFocus) {
            setSelection(lastSelectItem);
        }
    }
}
