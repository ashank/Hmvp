package com.funhotel.tvllibrary.view;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * Created by dell on 2016/10/15.
 * GridView重新获取焦点时，让其重新选中上次被选的item
 */

public class LookBackGridView extends GridView{
    private int position;

    public LookBackGridView(Context context) {
        super(context);
    }

    public LookBackGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LookBackGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setSelectedItemPosition(int position){
        this.position=position;
    }

    @Override
    protected void onFocusChanged(boolean gainFocus, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(gainFocus, direction, previouslyFocusedRect);
        if (gainFocus) {
            setSelection(position);
        }
    }
}
