package com.funhotel.tvllibrary.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * @Title: ScaleGridView
 * @Description: 放大缩小的GrideView
 * @author: Zhiyahan
 * @data: 2016/4/21 16:56
 */
public class ScaleGridView extends GridView{
    private int position = 0;

    public ScaleGridView(Context context) {
        super(context);
    }

    public ScaleGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setChildrenDrawingOrderEnabled(true);
    }

    public void setCurrentPosition(int pos) {
        // 刷新adapter前，在activity中调用这句传入当前选中的item在屏幕中的次序
        this.position = pos;
    }

    @SuppressLint("NewApi")
    @Override
    protected void setChildrenDrawingOrderEnabled(boolean enabled) {
        // TODO Auto-generated method stub
        super.setChildrenDrawingOrderEnabled(enabled);
    }

    @Override
    protected int getChildDrawingOrder(int childCount, int i) {
        position = getSelectedItemPosition() - getFirstVisiblePosition();
        if(position<0){
            return 0;
        }else {
            if (i == childCount - 1) {// 这是最后一个需要刷新的item
                if (position > i) {
                    position = i;
                }
                return position;
            }
            if (i == position) {// 这是原本要在最后一个刷新的item
                return childCount - 1;
            }
        }
        return i;// 正常次序的item
    }

/*    @Override
    protected void onFocusChanged(boolean gainFocus, int direction, Rect previouslyFocusedRect) {
        int firstSelectItem = getFirstVisiblePosition();
        super.onFocusChanged(gainFocus, direction, previouslyFocusedRect);
        if (gainFocus) {
            setSelection(firstSelectItem);
        }
    }*/
}