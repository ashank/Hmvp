package com.funhotel.tvllibrary.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.funhotel.tvllibrary.R;


/**
 * Created by zhiyahan on 16/1/30.
 * 加载过程和加载失败中的动画视图*/


public class LoadingAnimationView extends LinearLayout {
    private  Context context;
    private ProgressBar bar;
    private TextView tvToast,tvRefresh;
    private View loadView;
    private ImageView imgLoadFail;
    private LinearLayout llLoading;
    private RelativeLayout llError;
    private RelativeLayout rlRelativeLayout;


    public LoadingAnimationView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context=context;
        initLoadingView();
    }

    public LoadingAnimationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
        initLoadingView();

    }

    public LoadingAnimationView(Context context) {
        super(context);
        this.context=context;
        initLoadingView();
    }



    /**
     * @Title: initLoadingView
     * @Description: TODO 初始化加载模块
     */
    public void initLoadingView() {
        // TODO Auto-generated method stub
        loadView=LayoutInflater.from(context).inflate(R.layout.view_load_success_fail, this);
        tvToast=(TextView)findViewById(R.id.tv_toast);
        tvRefresh=(TextView)findViewById(R.id.tv_refresh);
        llLoading=(LinearLayout)findViewById(R.id.ll_loading);
        imgLoadFail=(ImageView)findViewById(R.id.img_load_fail) ;
        rlRelativeLayout=(RelativeLayout)findViewById(R.id.rl_load);
//      llError=(LinearLayout)findViewById(R.id.lliner_error);
        bar=(ProgressBar) findViewById(R.id.pb_loading);
        tvRefresh.setText(Html.fromHtml("<u>" + "刷新" + "</u>"));
        llError=(RelativeLayout)findViewById(R.id.relayout2);
    }


    /**
     * 显示加载视图
     */
    public void showLoadingView() {
        // TODO Auto-generated method stub
        llLoading.setVisibility(View.VISIBLE);
        llError.setVisibility(View.GONE);
        rlRelativeLayout.setOnClickListener(null);
    }


    /**
     * 显示失败视图
     * @param toast 提示文字
     */
    public void showFailedToast(String toast,OnClickListener onClickListener) {
        // TODO Auto-generated method stub
        rlRelativeLayout.setOnClickListener(null);
        llLoading.setVisibility(View.GONE);
        llError.setVisibility(View.VISIBLE);
        llError.setFocusable(false);
        tvToast.setText(toast);
        tvRefresh.requestFocus();
        tvRefresh.setFocusable(true);
        tvRefresh.setFocusableInTouchMode(true);
        tvRefresh.setVisibility(View.VISIBLE);
        tvRefresh.setText(Html.fromHtml("<u>" + "刷新" + "</u>"));
        tvRefresh.setOnClickListener(onClickListener);
    }

    /**
     * 设置加载失败的图片
     * @param resId 图片资源
     */
    public void setLoadFailImage(int resId){
        imgLoadFail.setImageResource(resId);
    }

    public void setBackgroundImage(int resId){
        imgLoadFail.setImageResource(resId);
    }

    /**
     * 设置刷新的按钮的背景
     * @param resID
     */
    public void setRefreshButtonBackground(int resID){
        tvRefresh.setBackgroundResource(resID);
    }

    /**
     * 设置刷新的按钮的文字,默认是刷新
     * @param text  按钮的文字
     */
    public void setRefreshButtonText(String text){
        tvRefresh.setText(text);
    }


    /**
     * 视图
     */
    public void setLoadingViewVisible(int visible) {
        // TODO Auto-generated method stub
        if (loadView.getVisibility()!=visible){
            if (visible==VISIBLE){
                AlphaAnimation animation=new AlphaAnimation(0.0f,1.0f);
                animation.setDuration(100);
                loadView.setAnimation(animation);
                rlRelativeLayout.setFocusable(true);
                rlRelativeLayout.setFocusableInTouchMode(true);
                rlRelativeLayout.setOnClickListener(null);
            }else {
                AlphaAnimation animation=new AlphaAnimation(1.0f,0.0f);
                animation.setDuration(100);
                loadView.setAnimation(animation);
            }
            loadView.setVisibility(visible);
        }
    }


    /**
     * 设置整个加载视图的背景颜色
     * @param context
     * @param color 背景颜色
     */
    public void setBackground(Context context,int color) {
        // TODO Auto-generated method stub
        rlRelativeLayout.setBackgroundColor(context.getResources().getColor(color));
    }

    public void setBackground(int resid){
        rlRelativeLayout.setBackgroundResource(resid);
    }

    //加载中...显示
    public void setTvToastText(String tv) {
        tvToast.setText(tv);
        tvRefresh.setVisibility(View.GONE);
    }

    //加载中...显示
    public void settvRefreshVisibility(int visibility) {
        tvRefresh.setVisibility(visibility);
    }


   public void requestFocusReFreshButton(){
       tvRefresh.requestFocus();
   };

    /**
     * 清理资源
     */
    public void release(){
        Drawable d = imgLoadFail.getDrawable();
        if (d != null) d.setCallback(null);
        imgLoadFail.setImageDrawable(null);
        imgLoadFail.setBackgroundDrawable(null);
    }

}
