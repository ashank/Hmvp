package com.funhotel.tvllibrary.utils;

import android.annotation.SuppressLint;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.OvershootInterpolator;
import android.view.animation.TranslateAnimation;

import java.lang.reflect.Field;

/**
 * Created by zhangyetao on 2016/4/12.
 * TODO:
 */
public class ZoomOutPageTransformer implements ViewPager.PageTransformer {
    private static final float MIN_ALPHA = 0.0f;

    @SuppressLint("NewApi")
    public void transformPage(View view, float position) {

        if (position <= 0) {
            AlphaAnimation anima = new AlphaAnimation(1.0f, 0f);
            anima.setDuration(3000);
            anima.setRepeatMode(TranslateAnimation.REVERSE);
            anima.setRepeatCount(TranslateAnimation.INFINITE);
            view.startAnimation(anima);

        }
         if (position <= 1 && position>0){
            AlphaAnimation anima2 = new AlphaAnimation(0, 1.0f);
            anima2.setDuration(3000);
            anima2.setRepeatMode(TranslateAnimation.REVERSE);
            view.startAnimation(anima2);
        }

    }

    public void setViewPagerScrollSpeed(ViewPager viewPager) {
        try {
            Field field = ViewPager.class.getDeclaredField("mScroller");
            field.setAccessible(true);
            ViewPagerScroller viewPagerScroller = new ViewPagerScroller(viewPager.getContext(), new OvershootInterpolator(0.6F));
            field.set(viewPager, viewPagerScroller);
            viewPagerScroller.setDuration(10);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }
}
