package com.funhotel.mvp.common;

import android.app.Application;

/**
 * Created by zhiyahan on 2017/3/24.
 */

public class BaseApplication extends Application {

    private static BaseApplication baseApplication;

    public static  BaseApplication getInstance(){


        return baseApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        baseApplication=this;
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
    }


    @Override
    public void registerActivityLifecycleCallbacks(ActivityLifecycleCallbacks callback) {
        super.registerActivityLifecycleCallbacks(callback);

    }

    @Override
    public void unregisterActivityLifecycleCallbacks(ActivityLifecycleCallbacks callback) {
        super.unregisterActivityLifecycleCallbacks(callback);
    }
}
