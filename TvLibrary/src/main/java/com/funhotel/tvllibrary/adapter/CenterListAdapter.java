package com.funhotel.tvllibrary.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.funhotel.tvllibrary.R;

import java.util.List;

/**
 * @Title: LookBackListAdapter
 * @Description: 回看界面的listview 的 adapter
 * @author: Zhang Yetao
 * @data: 2016/9/22 10:48
 */
public class CenterListAdapter extends BaseAdapter {
    private Context mContext;
    private List<String> mDatas;
    private LayoutInflater inflater;
    private int mHeight=0;
    private int mWidth=0;
    private int mPos=-1;
    private boolean isFocus = true;//焦点是否在listview上


    public CenterListAdapter(Context context, List<String>datas){
        this.mDatas =datas;
        this.mContext=context;
        if (null!=context){
            this.inflater = LayoutInflater.from(context);
        }
    }

    public void setDatas(List<String> mDatas){
        this.mDatas=mDatas;
    }

    public void setItemPosition(int pos,boolean isFocus){
        this.mPos=pos;
        this.isFocus = isFocus;
    }

    @Override
    public int getCount() {
        if (null!=mDatas){
          return mDatas.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_center_listview, null);
            viewHolder.tv_time = (TextView) convertView.findViewById(R.id.item_center_time);
            viewHolder.iv_arrow = (ImageView) convertView.findViewById(R.id.center_arrow);
            viewHolder.rl_center = (RelativeLayout) convertView.findViewById(R.id.rl_center);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (mDatas.size()>position){
            viewHolder.tv_time.setText(mDatas.get(position));
        }

        if (mHeight!=0 && mWidth!=0){
            viewHolder.tv_time.setHeight(mHeight);
            viewHolder.tv_time.setWidth(mWidth);
        }

            if (isFocus) {
//                viewHolder.rl_center.setBackgroundResource(R.drawable.home_item_selector);
                viewHolder.iv_arrow.setVisibility(View.GONE);
                viewHolder.tv_time.setTextColor(Color.parseColor("#FFFFFF"));
            } else {
                if(mPos==position) {
//                    viewHolder.rl_center.setBackgroundColor(Color.TRANSPARENT);
                    viewHolder.iv_arrow.setVisibility(View.VISIBLE);
                    viewHolder.tv_time.setTextColor(Color.parseColor("#FF8833"));
                }else{
//                    viewHolder.rl_center.setBackgroundColor(Color.TRANSPARENT);
                    viewHolder.tv_time.setTextColor(Color.WHITE);
                    viewHolder.iv_arrow.setVisibility(View.GONE);
                }
            }


        return convertView;
    }


    class ViewHolder {
        public TextView tv_time;
        public ImageView iv_arrow;
        public RelativeLayout rl_center;
    }

}
