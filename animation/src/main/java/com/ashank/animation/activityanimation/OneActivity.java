package com.ashank.animation.activityanimation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Window;
import android.widget.Button;

import com.ashank.animation.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OneActivity extends AppCompatActivity {


    @BindView(R.id.button)
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_one);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.button)
    public void onViewClicked() {

        Intent intent=new Intent(OneActivity.this,TwoActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.scalein,R.anim.scaleout);


    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode==KeyEvent.KEYCODE_BACK){
            finish();
            overridePendingTransition(R.anim.scalein,R.anim.scaleout);
        }
        return super.onKeyDown(keyCode, event);
    }
}
