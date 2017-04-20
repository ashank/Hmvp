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
import com.funhotel.tvllibrary.application.ColumnModel;

import java.util.List;


/**
 * @Title: LookBackListAdapter
 * @Description: 回看界面的listview 的 adapter
 * @author: Zhang Yetao
 * @data: 2016/9/22 10:48
 */
public class ResourceAdapter extends BaseAdapter {
    private Context mContext;
    private List<ColumnModel> mDatas;
    private LayoutInflater inflater;
    private int mHeight=0;
    private int mWidth=0;
    private int mCurrent=-1;
    private boolean isFocus = true;//焦点是否在listview上

    public ResourceAdapter(Context context, List<ColumnModel>datas){
        this.mDatas =datas;
        this.mContext=context;
        if (null!=context){
            this.inflater = LayoutInflater.from(context);
        }
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
            convertView = inflater.inflate(R.layout.resource_listview, null);
            viewHolder.tv_name = (TextView) convertView.findViewById(R.id.resource_name);
            viewHolder.relayout_resource = (RelativeLayout) convertView.findViewById(R.id.relayout_resource);
            viewHolder.resource_arrow = (ImageView) convertView.findViewById(R.id.resource_arrow);
            viewHolder.relayout_resource.setBackgroundResource(R.drawable.home_item_selector);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tv_name.setText(mDatas.get(position).getColumnname());
        if (mHeight!=0 && mWidth!=0){
            viewHolder.tv_name.setHeight(mHeight);
            viewHolder.tv_name.setWidth(mWidth);
        }
            if (isFocus) {
                viewHolder.relayout_resource.setBackgroundResource(R.drawable.home_item_selector);
                viewHolder.tv_name.setTextColor(Color.WHITE);
                viewHolder.resource_arrow.setVisibility(View.GONE);
            } else {
                if(mCurrent==position) {
                    viewHolder.relayout_resource.setBackgroundColor(Color.TRANSPARENT);
                    viewHolder.resource_arrow.setVisibility(View.VISIBLE);
                    viewHolder.tv_name.setTextColor(Color.parseColor("#FF8833"));
                }else{
                    viewHolder.relayout_resource.setBackgroundColor(Color.TRANSPARENT);
                    viewHolder.tv_name.setTextColor(Color.WHITE);
                    viewHolder.resource_arrow.setVisibility(View.GONE);
                }
            }
        return convertView;
    }

    public void setItemParams(int winth ,int height){
        this.mWidth=winth;
        this.mHeight=height;
    }



    class ViewHolder {
        TextView tv_name;
        ImageView resource_arrow;
        RelativeLayout relayout_resource;
    }


    public void changeItemFocus(int currentItem,boolean isFocus){
        this.mCurrent=currentItem;
        this.isFocus = isFocus;
    }

    public void setmDatas(List<ColumnModel> mDatas) {
        this.mDatas = mDatas;
    }

}
