package com.funhotel.tvllibrary.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.GridView;

import com.funhotel.tvllibrary.R;

public class ZoomGridView extends GridView {
    float mMyScaleX = 1.0f;
    float mMyScaleY = 1.0f;
    protected Rect mMySelectedPaddingRect = new Rect();

    Drawable mPlayIcon;
    protected boolean mShowPlayIcon = false;

    int mPlayIconMargin;

    public void setShowPlayIcon(Boolean show) {
        mShowPlayIcon = show;
        this.invalidate();
    }

    public ZoomGridView(Context contxt) {
        super(contxt);
        mPlayIconMargin = getResources().getDimensionPixelSize(R.dimen.px2);
    }

    public ZoomGridView(Context contxt, AttributeSet attrs) {
        super(contxt, attrs);
        mPlayIconMargin = getResources().getDimensionPixelSize(R.dimen.px2);
    }

    public ZoomGridView(Context contxt, AttributeSet attrs, int defStyle) {
        super(contxt, attrs, defStyle);

        mPlayIconMargin = getResources().getDimensionPixelSize(R.dimen.px2);
    }

    public void setPlayIcon(int resId){
        mPlayIcon = this.getResources().getDrawable(resId);
    }


    @Override
    public void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (mMySelectedDrawable == null)
            return;
        drawSelector(canvas);
        drawPlayIcon(canvas);
    }


    protected void drawPlayIcon(Canvas canvas) {
        if (isFocused() && this.mMySelectedDrawable != null && mShowPlayIcon) {
            Rect r = this.mMySelectedDrawable.getBounds();
            r.bottom -= (mPlayIconMargin+this.mMySelectedPaddingRect.bottom);
            r.right -= (mPlayIconMargin+this.mMySelectedPaddingRect.right);
            r.top = r.bottom - mPlayIcon.getIntrinsicHeight();
            r.left = r.right - mPlayIcon.getIntrinsicWidth();
            mPlayIcon.setBounds(r);
            mPlayIcon.draw(canvas);
        }
    }

    public void setMySelector(int resId) {
        mMySelectedDrawable = getResources().getDrawable(resId);
        mMySelectedPaddingRect = new Rect();
        mMySelectedDrawable.getPadding(mMySelectedPaddingRect);
    }

    protected Drawable mMySelectedDrawable = null;

    protected View mMySelectedView = null;

    protected Rect mTmpSelectedRect = new Rect();
    protected Rect mTmpGridViewRect = new Rect();

    /**
     * 绘画选中的状态
     * @param canvas
     */
    protected void drawSelector(Canvas canvas) {
        View v = getSelectedView();
        if (isFocused() && v != null) {
            scaleCurrentView();

            Rect r = mTmpSelectedRect;
//			getFocusedRect(r);

            v.getGlobalVisibleRect(r);
            getGlobalVisibleRect(mTmpGridViewRect);

            r.offset(-mTmpGridViewRect.left, -mTmpGridViewRect.top);

            r.top -= mMySelectedPaddingRect.top;
            r.left -= mMySelectedPaddingRect.left;
            r.right += mMySelectedPaddingRect.right;
            r.bottom += mMySelectedPaddingRect.bottom;

            //int restoreCount = canvas.save();
            //canvas.scale(v.getScaleX(), v.getScaleY(), r.exactCenterX(), r.exactCenterY());
            mMySelectedDrawable.setBounds(r);
            mMySelectedDrawable.draw(canvas);
            //canvas.restoreToCount(restoreCount);
        }
    }

    public void setMyScaleValues(float scaleX, float scaleY) {
        mMyScaleX = scaleX;
        mMyScaleY = scaleY;
    }

    void scaleCurrentView(){
        View v = getSelectedView();
        unScalePrevView();
        if(v != null){
            //Log.i(TAG,"scaleView");
            mMySelectedView = v;
            mMySelectedView.setScaleX(mMyScaleX);
            mMySelectedView.setScaleY(mMyScaleY);
            mMySelectedView.animate().setDuration(100).start();
        }
    }

    void unScalePrevView(){
        if(mMySelectedView != null){
            mMySelectedView.setScaleX(1);
            mMySelectedView.setScaleY(1);
            mMySelectedView.animate().setDuration(100).start();
            mMySelectedView = null;
        }

    }



    protected void onFocusChanged(boolean gainFocus, int direction, Rect previouslyFocusedRect) {
        if(!gainFocus){
            unScalePrevView();
            requestLayout();
        }
        super.onFocusChanged(gainFocus, direction, previouslyFocusedRect);
    }

    protected int mMyVerticalSpacing = 0;

    public void setMyVerticalSpacing(int verticalSpacing) {
        mMyVerticalSpacing = verticalSpacing;
    }

    public int getMyVerticalSpacing() {
        return mMyVerticalSpacing;
    }

}