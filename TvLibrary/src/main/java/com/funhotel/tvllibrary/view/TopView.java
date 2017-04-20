package com.funhotel.tvllibrary.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.funhotel.tvllibrary.R;

/**
 * Created by zhiyahan on 16/1/30.
 * 头部Logo和时间的视图
 * */
public class TopView extends RelativeLayout {

    private  Context context;
    private TextView tvDate,tvTime;
    private View loadView;
    private LinearLayout llDate;
    private ImageView imageView;

    public TopView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context=context;
        initView();
    }

    public TopView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
        initView();
    }

    public TopView(Context context) {
        super(context);
        this.context=context;
        initView();
    }


    /**
     * @Title: initLoadingView
     * @Description: TODO 初始化加载模块
     */
    public void initView() {
        // TODO Auto-generated method stub
        loadView=LayoutInflater.from(context).inflate(R.layout.view_top, this);
        tvDate=(TextView)findViewById(R.id.tv_date);
        tvTime=(TextView)findViewById(R.id.tv_time);
        llDate=(LinearLayout)findViewById(R.id.ll_date);
        imageView=(ImageView) findViewById(R.id.img_logo);
    }


    /**
     * 显示或者隐藏时间视图
     */
    public void setDateViewVisible(int visible) {
        // TODO Auto-generated method stub
        llDate.setVisibility(visible);
    }


    /**
     * 设置时间和日期
     * @param date
     * @param time
     */
    public void setUpDate(String date,String time) {
        tvDate.setText(date);
        tvTime.setText(time);
    }


    /**
     * 设置Logo
     * @param resId 图片资源
     */
    public void setLogo(int resId){
        imageView.setImageResource(resId);
    }



    /**
     * 设置Logo显示
     * @param drawable 图片资源
     */
    public void setLogo(Drawable drawable){
        imageView.setImageDrawable(drawable);
    }


    /**
     * 设置Logo显示
     * @param bitmap 图片资源
     */
    public void setLogo(Bitmap bitmap){
        imageView.setImageBitmap(bitmap);
    }


}
