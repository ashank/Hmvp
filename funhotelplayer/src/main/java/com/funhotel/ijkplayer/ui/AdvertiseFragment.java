package com.funhotel.ijkplayer.ui;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.funhotel.ijkplayer.R;

import java.util.ArrayList;

/**
 * @Title: AdvertiseFragment
 * @Description: 用于广告轮播
 * @author: Zhang Yetao
 * @data: 2016/8/23 15:23
 */
public class AdvertiseFragment extends Fragment {

    private ViewFlipper mViewFlipper;
    private ArrayList<String> urls;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment RosterFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AdvertiseFragment newInstance() {
        AdvertiseFragment fragment = new AdvertiseFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_advertise, container, false);
        mViewFlipper = (ViewFlipper) view.findViewById(R.id.advertise_viewfliper);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle bundle = getArguments();
        //播放时间
        int time = bundle.getInt("adTime");
        //播放的图片路径
        urls = bundle.getStringArrayList("adUrl");

        initEvent();
        mViewFlipper.setInAnimation(getActivity(), R.anim.fade_in);
        mViewFlipper.setOutAnimation(getActivity(), R.anim.fade_out);
        mViewFlipper.setFlipInterval(time * 1000);//播放时长
        mViewFlipper.setAutoStart(true);//自动播放
        mHandler.sendEmptyMessageDelayed(0, urls.size() * time * 1000);

    }




    /**
     * 动态添加View
     */
    private void initEvent() {
        if (null != urls && urls.size() > 0) {
            for (int i = 0; i < urls.size(); i++) {
                ImageView imageView = new ImageView(getActivity());
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                Glide.with(getActivity()).load(urls.get(i)).diskCacheStrategy(DiskCacheStrategy.ALL)
                        .error(R.drawable.bg_ad_1920_1080)
                        .crossFade()
                        .centerCrop()
                        .into(imageView);
                mViewFlipper.addView(imageView);
            }
        }

    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    mViewFlipper.stopFlipping();
                    if(null!=getActivity()) {
//                        ((MainActivity) getActivity()).removeFragment();
                    }
                    break;
            }
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        if (mViewFlipper.getChildCount() > 0) {
            if (mViewFlipper.isAutoStart() && !mViewFlipper.isFlipping()) {
                mViewFlipper.startFlipping();
            }
        }

    }


    @Override
    public void onPause() {
        if (mViewFlipper.getChildCount() > 0) {
            if (mViewFlipper.isFlipping()) {
                mViewFlipper.stopFlipping();
            }
        }
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();

    }

    @Override
    public void onDestroy() {
        for (int i = 0; i < mViewFlipper.getChildCount(); i++) {
            clearImageView((ImageView) mViewFlipper.getChildAt(i));
        }
        super.onDestroy();
    }

    public void clearImageView(ImageView imageView) {
        Drawable d = imageView.getDrawable();
        if (d != null)
            d.setCallback(null);
        imageView.setImageDrawable(null);
        imageView.setBackgroundDrawable(null);

    }
}
