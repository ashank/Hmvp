package com.funhotel.tvllibrary.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.funhotel.tvllibrary.R;
import com.funhotel.tvllibrary.application.LookBackModel;
import com.funhotel.tvllibrary.utils.DateTimeUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @Title: RightAdapter
 * @Description: 回看界面的gridview 的 adapter
 * @author: Zhang Yetao
 * @data: 2016/9/22 10:47
 */
public class RightAdapter extends BaseAdapter {
    private List<LookBackModel>mDatas;
    private Context mContext;
    private LayoutInflater inflater;
    private String time;//服务器时间
    private String currentDate;//当前日期
    private String serDate;//服务器日期
    private List<String> listTime = new ArrayList<>();
    private List<String> listDate = new ArrayList<>();


    public RightAdapter(Context context, List<LookBackModel> datas){
        this.mContext=context;
        this.mDatas=datas;
        if (null!=context){
            this.inflater = LayoutInflater.from(context);
        }
    }

    public void setSystemTime(String serverDate,String currentDate){
        this.time = serverDate.substring(serverDate.indexOf(" ")+1,serverDate.length()-3);
        this.serDate = serverDate.substring(5,7)+"月"+serverDate.substring(8,10)+"日";
        this.currentDate = currentDate;
        listDate.clear();
        listDate.add(serDate);
        listDate.add(currentDate);
        Collections.sort(listDate);
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
            convertView = inflater.inflate(R.layout.item_right_adapter, null);
            convertView.setLayoutParams(new GridView.LayoutParams((int) mContext.getResources().getDimension(R.dimen.px600),
                    (int) mContext.getResources().getDimension(R.dimen.px70)));
            viewHolder.tv_time = (TextView) convertView.findViewById(R.id.item_lookback_time);
            viewHolder.tv_context = (TextView) convertView.findViewById(R.id.item_lookback_context);
            viewHolder.tv_online = (TextView) convertView.findViewById(R.id.item_lookback_textview);
            viewHolder.rl_right = (RelativeLayout) convertView.findViewById(R.id.relayout_right);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        String beginTime = mDatas.get(position).getBegintime();
        String endtime = mDatas.get(position).getEndtime();
        listTime.clear();
        listTime.add(time);
        listTime.add(beginTime);
        Collections.sort(listTime);
        //判断是否能点击
        if (null!=listDate&&listDate.size()>0){
            if (!serDate.equals(currentDate)){//不是同一天时
                viewHolder.tv_online.setVisibility(View.GONE);
                if (listDate.get(0).equals(currentDate)){//开始时间小于当前时间
                    viewHolder.tv_time.setTextColor(Color.WHITE);
                    viewHolder.tv_context.setTextColor(Color.WHITE);
                }else {
                    viewHolder.tv_time.setTextColor(Color.parseColor("#696969"));
                    viewHolder.tv_context.setTextColor(Color.parseColor("#696969"));
                }
            }else {//同一天时
                //判断是否是正播
                if (DateTimeUtil.betweenTime2(beginTime,endtime,time)){
                    viewHolder.tv_online.setVisibility(View.VISIBLE);
                }else {
                    viewHolder.tv_online.setVisibility(View.GONE);
                }
                if (listTime.get(0).equals(time)){//开始时间大于当前时间
                    viewHolder.tv_time.setTextColor(Color.parseColor("#696969"));
                    viewHolder.tv_context.setTextColor(Color.parseColor("#696969"));
                }else {
                    viewHolder.tv_time.setTextColor(Color.WHITE);
                    viewHolder.tv_context.setTextColor(Color.WHITE);
                }
            }
        }
        viewHolder.tv_time.setText(beginTime);
        viewHolder.tv_context.setText(mDatas.get(position).getPrevuename());
        return convertView;
    }

    /**
     *  设置数据
     * @param mDatas
     */
    public void setDatas(List<LookBackModel> mDatas){
        this.mDatas=mDatas;
    }

    class ViewHolder {
        public TextView tv_time;
        public TextView tv_context;
        public TextView tv_online;
        public RelativeLayout rl_right;
    }

}
