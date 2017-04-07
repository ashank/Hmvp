package com.funhotel.presentation;

import android.content.Context;
import android.hardware.display.DisplayManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {

    DifferentDislay mPresentation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DisplayManager mDisplayManager;// 屏幕管理类
        mDisplayManager = (DisplayManager) this
                .getSystemService(Context.DISPLAY_SERVICE);
        Display[] displays = mDisplayManager.getDisplays();

        if (mPresentation == null) {
            mPresentation = new DifferentDislay(this, displays[displays.length - 1]);// displays[1]是副屏

            mPresentation.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
            mPresentation.show();
        }
    }
}
