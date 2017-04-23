package com.ashank.animation.contenttransitions;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

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

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        peers.add(0,"sd");
        peers.add(1,"sd");
        peers.add(2,"sd");
        peers.add(3,"sd");
        peers.add(4,"sd");
        peers.add(5,"sd");
        peers.add(6,"sd");
        reclerview1.setLayoutManager(linearLayoutManager);
        mWifiDevicesAdapter = new GroupAdapter(peers, this);
        reclerview1.setAdapter(mWifiDevicesAdapter);







    }
}
