package com.ashank.animation.activityanimation;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.ashank.animation.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ThreeActivity extends AppCompatActivity {

    @BindView(R.id.textView3)
    TextView textView3;
    @BindView(R.id.button)
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //使用切换动画
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        setContentView(R.layout.activity_one);
        ButterKnife.bind(this);


    }

    @OnClick(R.id.button)
    public void onViewClicked() {

        Intent intent=new Intent(this,FourActivity.class);
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());


    }
}
