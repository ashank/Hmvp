package com.funhotel.presentation;

import android.annotation.TargetApi;
import android.app.Presentation;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.Display;

/**
 * Created by zhiyahan on 2017/4/7.
 */

@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
public class DifferentDislay extends Presentation {

    public DifferentDislay(Context outerContext, Display display) {
        super(outerContext,display);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);

    }
}
