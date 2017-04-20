package com.funhotel.tvllibrary.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;

import com.funhotel.tvllibrary.R;

/**
 * Created by chensuilun on 16-4-19.
 */
public class SimpleTextCursorWheelLayout extends CursorWheelLayout {

    private onSimpleItemSelected mSimpleItemSelected;

    public SimpleTextCursorWheelLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.TRANSPARENT);
        float left = getResources().getDimension(R.dimen.px1426);
        float top = getResources().getDimension(R.dimen.px776);
        float bottom = getResources().getDimension(R.dimen.px1854);
        float right = getResources().getDimension(R.dimen.px1184);
        canvas.clipRect(left, top, bottom, right);//以view的左上角为相对坐标原点
    }

    public interface onSimpleItemSelected{
        void onSimpleItemSelected(View v,int position);
        void onNothingSelected(View v,int position);
    }
    public void setOnSimpleItemSelected(onSimpleItemSelected onSimpleItemSelected) {
        this.mSimpleItemSelected = onSimpleItemSelected;
    }


    @Override
    protected void onInnerItemSelected(View v,int position) {
        super.onInnerItemSelected(v,position);
        if (v == null) {
            return;
        }

        if (null!=mSimpleItemSelected){
            mSimpleItemSelected.onSimpleItemSelected(v,position);
        }
    }

    @Override
    protected void onInnerItemUnselected(View v,int position) {
        super.onInnerItemUnselected(v,position);
        if (v == null) {
            return;
        }
        v.animate().scaleX(1).scaleY(1);
        if (null!=mSimpleItemSelected){
            mSimpleItemSelected.onNothingSelected(v,position);
        }
    }


    private int mRootDiameter = Math.max(getMeasuredWidth(), getMeasuredHeight());


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        int layoutDiameter = mRootDiameter;//直径
        int layoutRadial = (int) (layoutDiameter / 2.0);//半径
        final int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = getChildAt(i);
            child.setRotation(0);
        }

    }
}

