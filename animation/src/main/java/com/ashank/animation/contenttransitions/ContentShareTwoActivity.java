package com.ashank.animation.contenttransitions;

import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.ashank.animation.R;
import com.funhotel.mvp.module.image.ImageLoder;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ContentShareTwoActivity extends AppCompatActivity {

    @BindView(R.id.img)
    ImageView img;
    @BindView(R.id.item_text)
    TextView itemText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       /* getWindow().setSharedElementEnterTransition(new Fade());
        getWindow().setSharedElementExitTransition(new Explode());
        getWindow().setSharedElementReenterTransition(new Slide());
        getWindow().setSharedElementReturnTransition(new Fade());*/
        setContentView(R.layout.activity_content_share_two);
        ButterKnife.bind(this);


        ImageLoder.newInstance(this).load(
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1493056013316&di=c6c4439f59d8cd56794c72494d27cb27&imgtype=0&src=http%3A%2F%2Fimg01.taopic.com%2F150329%2F240420-15032Z91F694.jpg",
                img);
        //主要的语句---将当前Activity的View和自己定义的Key绑定起来
        ViewCompat.setTransitionName(img, "img");

    }
}
