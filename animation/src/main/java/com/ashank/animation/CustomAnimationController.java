package com.ashank.animation;

import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.LayoutAnimationController;

/**
 * 作者：zhiyahan on 2017/4/22 11:49
 */
public class CustomAnimationController extends LayoutAnimationController {

    private Callback onIndexListener;
    // 7 just lucky number
    public static final int ORDER_CUSTOM  = 7;

    public void setOnIndexListener(Callback onIndexListener) {
        this.onIndexListener = onIndexListener;
    }


    public CustomAnimationController(Animation animation) {
        super(animation);
    }


    public CustomAnimationController(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomAnimationController(Animation animation, float delay) {
        super(animation, delay);
    }


    @Override
    protected int getTransformedIndex(AnimationParameters params) {

        if(getOrder() == ORDER_CUSTOM && onIndexListener != null) {
            return onIndexListener.onIndex(this, params.count, params.index);
        } else {
            return super.getTransformedIndex(params);
        }
    }


    /**
     * callback for get play animation order
     *
     */
    public static interface Callback{

        public int onIndex(CustomAnimationController controller, int count, int index);
    }
}
