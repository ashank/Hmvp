package com.funhotel.tvllibrary.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.funhotel.tvllibrary.R;
import com.funhotel.tvllibrary.application.Channel;

import java.util.List;


/**
 * @Title: LookBackListAdapter
 * @Description: 回看界面的listview 的 adapter
 * @author: Zhang Yetao
 * @data: 2016/9/22 10:48
 */
public class ChannelViewAdapter extends BaseAdapter {
    private Context mContext;
    private List<Channel> mDatas;
    private LayoutInflater inflater;
    private int mPos = -1;//刷新的position;
    private boolean isFocus = true;//焦点是否在listview上


    public ChannelViewAdapter(Context context, List<Channel>datas){
        this.mDatas =datas;
        this.mContext=context;
        if (null!=context){
            this.inflater = LayoutInflater.from(context);
        }
    }

//    public void setPosition(int position, boolean isFocus){
//        this.mPos=position;
//        this.isFocus = isFocus;
//    }

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
            convertView = inflater.inflate(R.layout.item_channel_listview, null);
            viewHolder.tv_id = (TextView) convertView.findViewById(R.id.item_channel_id);
            viewHolder.tv_name = (TextView) convertView.findViewById(R.id.item_channel_name);
//            viewHolder.tv_reback = (TextView) convertView.findViewById(R.id.tv_reback);
            viewHolder.relayout_channel = (RelativeLayout) convertView.findViewById(R.id.relayout_channel);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        String num = mDatas.get(position).getMixno();
        viewHolder.tv_name.setText(mDatas.get(position).getChannelname());
        viewHolder.tv_id.setText(dealWithChannelNum(num));

/*            if (mPos==position) {
//                viewHolder.tv_reback.setVisibility(View.VISIBLE);
                if (isFocus) {
                    viewHolder.tv_reback.setVisibility(View.VISIBLE);
                    viewHolder.relayout_channel.setBackgroundResource(R.drawable.home_item_selector);
                    viewHolder.tv_name.setTextColor(Color.parseColor("#ffffff"));
                    viewHolder.tv_id.setTextColor(Color.parseColor("#ffffff"));
                } else {
                    viewHolder.tv_reback.setVisibility(View.GONE);
                    viewHolder.relayout_channel.setBackgroundResource(R.drawable.relayout_shape);
                    viewHolder.tv_name.setTextColor(Color.parseColor("#FF8833"));
                    viewHolder.tv_id.setTextColor(Color.parseColor("#FF8833"));
                }
        }else {
            viewHolder.tv_reback.setVisibility(View.GONE);
            viewHolder.relayout_channel.setBackgroundResource(R.drawable.home_item_selector);
            viewHolder.tv_name.setTextColor(Color.parseColor("#ffffff"));
            viewHolder.tv_id.setTextColor(Color.parseColor("#ffffff"));
        }*/

//        viewHolder.tv_reback.setVisibility(View.GONE);
//        viewHolder.relayout_channel.setBackgroundResource(R.drawable.home_item_selector);
//        viewHolder.tv_name.setTextColor(Color.parseColor("#ffffff"));
//        viewHolder.tv_id.setTextColor(Color.parseColor("#ffffff"));
        return convertView;
    }



    class ViewHolder {
        public TextView tv_id;
        public TextView tv_name;
        public TextView tv_reback;
        public RelativeLayout relayout_channel;
    }

    public void setmDatas(List<Channel> mDatas) {
        this.mDatas = mDatas;
    }

    private String dealWithChannelNum(String num) {
        if (!TextUtils.isEmpty(num) && num.length() < 3) {
            return "000".substring(0, 3 - num.length()) + num;
        }
        return num;
    }

}
