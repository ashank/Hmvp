package com.ashank.animation.contenttransitions;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;

import com.ashank.animation.GroupAdapter;
import com.ashank.animation.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ContentShareOneActivity extends AppCompatActivity {

    @BindView(R.id.reclerview1)
    RecyclerView reclerview1;

    List<String> peers = new ArrayList();
    private GroupAdapter mWifiDevicesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_share_one);
        ButterKnife.bind(this);
        initRecyleView();


    }

    private void initRecyleView(){
        Animation animation= AnimationUtils.loadAnimation(this,android.R.anim.slide_in_left);
        //正常组动画
        LayoutAnimationController controller=new LayoutAnimationController(animation);
        controller.setDelay(0.1f);
        controller.setOrder(LayoutAnimationController.ORDER_NORMAL);
        reclerview1.setLayoutAnimation(controller);

        //配置列表
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        peers = new ArrayList();
        peers.add(0,"sd");
        peers.add(1,"sd");
        peers.add(2,"sd");
        peers.add(3,"sd");
        peers.add(4,"sd");
        peers.add(5,"sd");
        peers.add(6,"sd");
        reclerview1.setLayoutManager(linearLayoutManager);
        mWifiDevicesAdapter = new GroupAdapter(peers, this);
        mWifiDevicesAdapter.setOnItemClickListener(new GroupAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, ImageView img ,int position) {
                Log.d("", "onItemClick: ");
                Intent intent =new Intent();
                intent.setClass(ContentShareOneActivity.this,ContentShareTwoActivity.class);

                //主要的语句
                //通过makeSceneTransitionAnimation传入多个Pair
                //每个Pair将一个当前Activity的View和目标Activity中的一个Key绑定起来
                //在目标Activity中会调用这个Key
                ActivityOptionsCompat activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        ContentShareOneActivity.this,
                        img,"img");
                // ActivityCompat是android支持库中用来适应不同android版本的
                ActivityCompat.startActivity(ContentShareOneActivity.this, intent, activityOptions.toBundle());

                getWindow().setSharedElementEnterTransition(new Fade());
                getWindow().setSharedElementExitTransition(new Explode());
                getWindow().setSharedElementReenterTransition(new Slide());
                getWindow().setSharedElementReturnTransition(new Fade());
            }
        });
        reclerview1.setAdapter(mWifiDevicesAdapter);


    }
}
