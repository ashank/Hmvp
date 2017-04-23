package com.ashank.animation.activityanimation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.ashank.animation.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FourActivity extends AppCompatActivity {

    @BindView(R.id.textView3)
    TextView textView3;
    @BindView(R.id.button2)
    Button button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //使用切换动画
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        setContentView(R.layout.activity_four);
        ButterKnife.bind(this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
           finish();
        }

        return super.onKeyDown(keyCode, event);
    }

    @OnClick(R.id.button2)
    public void onViewClicked() {
        finishAfterTransition();

    }
}
