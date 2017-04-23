package com.ashank.animation.activityanimation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.TextView;

import com.ashank.animation.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TwoActivity extends AppCompatActivity {

    @BindView(R.id.textView3)
    TextView textView3;
    @BindView(R.id.button2)
    Button button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two);
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
            overridePendingTransition(R.anim.scalein, R.anim.scaleout);
        }

        return super.onKeyDown(keyCode, event);
    }

    @OnClick(R.id.button2)
    public void onViewClicked() {

        finish();
        overridePendingTransition(R.anim.scalein, R.anim.scaleout);

    }
}
