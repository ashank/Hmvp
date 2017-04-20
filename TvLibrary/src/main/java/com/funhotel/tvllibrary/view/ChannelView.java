package com.funhotel.tvllibrary.view;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.funhotel.tvllibrary.R;
import com.funhotel.tvllibrary.adapter.CenterListAdapter;
import com.funhotel.tvllibrary.adapter.ChannelViewAdapter;
import com.funhotel.tvllibrary.adapter.ResourceAdapter;
import com.funhotel.tvllibrary.adapter.RightAdapter;
import com.funhotel.tvllibrary.application.Channel;
import com.funhotel.tvllibrary.application.ColumnModel;
import com.funhotel.tvllibrary.application.LookBackModel;
import com.funhotel.tvllibrary.db.ChannelManager;
import com.funhotel.tvllibrary.db.ColumnManager;
import com.funhotel.tvllibrary.db.TableKey;
import com.funhotel.tvllibrary.utils.DateTimeUtil;
import com.funhotel.tvllibrary.utils.DebugUtil;
import com.funhotel.tvllibrary.utils.UserIdUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Title: ChannelView
 * @Description: 频道菜单的view
 * @author: Zhang Yetao
 * @data: 2016/9/29 21:42
 */
public class ChannelView extends RelativeLayout implements View.OnFocusChangeListener {
    private Context context;
    private PlayerMenuListView mListView_left, mListView_center, mListView_right, mListView_source;
    private Boolean isLeft = true;//焦点在leftListView
    private Boolean isCenter = true;//焦点在center上
    private Boolean isResource = true;//焦点在resource上
    private int mFirstPosition = 0;//记录第1列listview的item位置
    private int mSecondPosition = 0;//记录第2列listview的item位置
    private int mThirdPosition = 0;//记录第3列listview的item位置
    private int mFourthPosition = 0;//记录第4列listview的item位置
    private ChannelViewAdapter mAdapter;
    private ResourceAdapter mResourceAdapter;
    private RelativeLayout relativeLayout;
    private RightAdapter mRightAdapter;
    private CenterListAdapter mCenterAdapter;
    private ListViewItemSelected itemSelected;
    private Set<String> set = new HashSet<>();
    private List<String> mCenterDatas = new ArrayList<>();
    private List<LookBackModel> mRightDatas = new ArrayList<>();
    private String stbId;
    private List<ColumnModel> columns = new ArrayList<>();
    private List<Channel> channelList;
    private List<LookBackModel> lookBackList;
    private ExecutorService mThrendPool;
    private String serverTime;
    private List<List<LookBackModel>> allLookBackDatas =new ArrayList<>();
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    notifyFirstDatas();
                    break;
                case 2:
                    notifySeccondDatas();
                    break;
                case 3:
                    notifyThridDatas();
                    notifyFourthDatas();
                    showLookBack();
                    break;
                case 4:
                    notifyFourthDatas();
            }
        }
    };


    public ChannelView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView();
    }

    public ChannelView(Context context) {
        super(context);
        this.context = context;
        initView();
    }
    public void setData() {
        isResource = true;
        isLeft = true;
        isCenter = true;
        mListView_source.setVisibility(VISIBLE);
        mListView_left.setVisibility(VISIBLE);
        mListView_center.setVisibility(GONE);
        mListView_right.setVisibility(GONE);
        mListView_source.requestFocus();
        mListView_source.setSelected(true);
        //确定键按下,显示菜单栏,初始化频道列显示
        if (mListView_left.getChildAt(mSecondPosition)!=null){
            TextView tv_channelCode = (TextView) mListView_left.getChildAt(mSecondPosition).findViewById(R.id.item_channel_id);
            TextView tv_channelName = (TextView) mListView_left.getChildAt(mSecondPosition).findViewById(R.id.item_channel_name);
            tv_channelCode.setTextColor(Color.parseColor("#FFFFFF"));
            tv_channelName.setTextColor(Color.parseColor("#FFFFFF"));
        }
        //TODO : 暂时不上线回看功能
       /* mListView_left.setLayoutParams(new LinearLayout.LayoutParams(
                (int) getResources().getDimension(R.dimen.px600),
                LinearLayout.LayoutParams.WRAP_CONTENT));*/
    }

    public void initView() {
        relativeLayout = (RelativeLayout) LayoutInflater.from(context).inflate(R.layout.view_channel, this);
        mListView_left = (PlayerMenuListView) relativeLayout.findViewById(R.id.lv_01);
        mListView_center = (PlayerMenuListView) findViewById(R.id.lv_02);
        mListView_right = (PlayerMenuListView) findViewById(R.id.lv_03);
        mListView_source = (PlayerMenuListView) findViewById(R.id.lv_04);
        mListView_source.setOnFocusChangeListener(this);
        mListView_left.setOnFocusChangeListener(this);
        mListView_center.setOnFocusChangeListener(this);
        mListView_right.setOnFocusChangeListener(this);
        stbId = UserIdUtils.newInstance(context).getUserId();
        if (null == mThrendPool) {
            mThrendPool = Executors.newSingleThreadExecutor();
        }
        mThrendPool.execute(new ColumnThread());
        initSelected();
        initClick();
    }

    private void notifyFirstDatas() {
        if (null != columns && columns.size() > 0) {
            if (null == mResourceAdapter) {
                mResourceAdapter = new ResourceAdapter(context, columns);
                mResourceAdapter.setItemParams((int) getResources().getDimension(R.dimen.px300), (int) getResources().getDimension(R.dimen.px100));
                mListView_source.setAdapter(mResourceAdapter);
            } else {
                mResourceAdapter.setmDatas(columns);
                mResourceAdapter.setItemParams((int) getResources().getDimension(R.dimen.px300), (int) getResources().getDimension(R.dimen.px100));
                mResourceAdapter.notifyDataSetChanged();
            }
        }
    }

    private void notifySeccondDatas() {
        if (null != channelList && channelList.size() > 0) {
            mListView_left.setVisibility(VISIBLE);
            if (null == mAdapter) {
                mAdapter = new ChannelViewAdapter(context, channelList);
                mListView_left.setFocusable(false);
                mListView_left.setAdapter(mAdapter);
            } else {
//                mAdapter.setPosition(-1, true);
                mAdapter.setmDatas(channelList);
                mAdapter.notifyDataSetChanged();
            }
        } else {
            mListView_left.setVisibility(INVISIBLE);
        }
    }

    private void notifyThridDatas() {
        if (null == mCenterAdapter) {
            mCenterAdapter = new CenterListAdapter(context, mCenterDatas);
            mListView_center.setSelector(R.color.transparent);
            mListView_center.setAdapter(mCenterAdapter);
        } else {
            mListView_center.setSelector(R.color.transparent);
            mCenterAdapter.setDatas(mCenterDatas);
            mCenterAdapter.notifyDataSetChanged();
        }
    }

    private void notifyFourthDatas() {
        if (null == mRightAdapter) {
            mRightAdapter = new RightAdapter(context, mRightDatas);
            mListView_right.setSelector(R.color.transparent);
            mListView_right.setAdapter(mRightAdapter);
        } else {
            mRightAdapter.setDatas(mRightDatas);
            mListView_right.setSelector(R.color.transparent);
            mRightAdapter.notifyDataSetChanged();
        }
    }

    private void initSelected() {
        /**
         * 最外侧的频道资源类型
         */
        mListView_source.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (null != itemSelected) {
                    itemSelected.onItemSelected();
                }
                mFirstPosition = position;
                //取消handler,停止UI刷新
                mHandler.removeMessages(2);
                mThrendPool.execute(new ChannelThread());

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        mListView_left.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!mListView_left.isFocused()) {
                    return;
                }
                if (null != itemSelected) {
                    itemSelected.onItemSelected();
                }
                mSecondPosition = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mListView_center.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {
                if (null != itemSelected) {
                    itemSelected.onItemSelected();
                }
                mThirdPosition = position;
                mThrendPool.execute(new FourthThread());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mListView_right.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!mListView_right.isFocused()) {
                    return;
                }
                if (null != itemSelected) {
                    itemSelected.onItemSelected();
                }
                mFourthPosition = position;

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    private void initClick() {
        mListView_left.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (null != mLookBackListener) {
                    mLookBackListener.onChannelItemClick(channelList.get(position));
                }
            }
        });

        mListView_right.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String beginTime = mRightDatas.get(position).getBegintime();
                String formatServerTime = DateTimeUtil.formatTime(serverTime, "yyyy-MM-dd HH:mm:ss", "HH:mm");
                //设置不能点击
                if (DateTimeUtil.getTimeDelta13(beginTime, formatServerTime) > 0) {
                    view.setClickable(false);
//                    mListView_right.getChildAt(position).setClickable(false);
                } else {
                    if (null != mLookBackListener && channelList !=null && channelList.size()>mSecondPosition) {
                        if(null!=allLookBackDatas && allLookBackDatas.size()>mThirdPosition) {
                            List<Object> lists = new ArrayList<Object>(allLookBackDatas.subList(0, mThirdPosition + 1));
                            Collections.reverse(lists);
                            mLookBackListener.onLookBackItemClick(channelList.get(mSecondPosition), allLookBackDatas, position);
                        }
                    }
                }
            }
        });
    }


    /**
     * 回看列表的点击事件回调
     */
    private OnListViewItemClickListener mLookBackListener;

    public void setOnListViewItemClickListener(OnListViewItemClickListener mLookBackListener) {
        this.mLookBackListener = mLookBackListener;
    }

    public interface OnListViewItemClickListener {
        void onLookBackItemClick(Channel channel, List<List<LookBackModel>> lookBacks, int position);
        void onChannelItemClick(Channel channel);
        void onRightKeyDown(String channelCode);
    }

//    /**
//     *  回看列表数据获取接口
//     */
//    private OnRightKeyDownListener mRightKeyDown;
//    public void setOnRightKeyDownListener(OnRightKeyDownListener listener){
//        this.mRightKeyDown=listener;
//    }
//    public interface OnRightKeyDownListener{
//        void onRightKeyDown(String channelCode);
//    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        int id = v.getId();
        if (id == R.id.lv_01) {
            if (hasFocus){
                mListView_left.setSelector(R.drawable.home_item_selector);
            }else {
                mListView_left.setSelector(R.color.transparent);
            }
           // mAdapter.setPosition(mSecondPosition, hasFocus);
            //mAdapter.notifyDataSetChanged();
        } else if (id == R.id.lv_02) {
            if (hasFocus){
                mListView_center.setSelector(R.drawable.bg_date_selector);
            }else {
                mListView_center.setSelector(R.color.transparent);
            }
            mCenterAdapter.setItemPosition(mThirdPosition, hasFocus);
            mCenterAdapter.notifyDataSetChanged();
        } else if (id == R.id.lv_03) {
            if (hasFocus){
                mListView_right.setSelector(R.drawable.home_item_selector);
            }else {
                mListView_right.setSelector(R.color.transparent);
            }
        } else if (id == R.id.lv_04) {
            mResourceAdapter.changeItemFocus(mFirstPosition, hasFocus);
            mResourceAdapter.notifyDataSetChanged();
        }
    }


    /**
     * 按下左键时的处理
     */
    public void leftKeyDown() {
        if (isResource && !isLeft && !isCenter) {
            mListView_source.setFocusable(true);
//            mAdapter.setPosition(0,false);
            isResource = false;
            isLeft = true;
            isCenter = true;
        } else if (!isResource && !isLeft && !isCenter) {
            DebugUtil.e(">>>>>显示栏目列表");
            mListView_left.setFocusable(true);
            showResource();
            isResource = true;
            isLeft = false;
            isCenter = false;
        } else if (!isResource && !isLeft && isCenter) {
            isResource = false;
            isLeft = false;
            isCenter = false;
        }
        DebugUtil.e("isResource = " + isResource + " isLeft = " + isLeft + " isCenter = " + isCenter);
    }


    /**
     * 按下右键时的处理,焦点默认在栏目上
     */
    public void rightKeyDown() {
        if(mListView_left!=null && mListView_left.isFocused()){
            return;
        }
        if (isResource && isLeft && isCenter) {
            //第一次按下右
            mListView_left.setFocusable(true);
            if (null != channelList && channelList.size() > 0) {
                isResource = true;
                isLeft = true;
                isCenter = true;
//                isLeft = false;
//                isCenter = false;
                mListView_left.setSelection(0);
            }
        }else if (isResource && !isLeft && !isCenter) {
            //第二次按下右键,通过频道号获取回看数据
//            lookBackList.clear();
            /*if (null!=channelList&&channelList.size()>mSecondPosition){
                String channelCode = channelList.get(mSecondPosition).getChannelcode();
                if (!TextUtils.isEmpty(channelCode)){
                    if (null!=mLookBackListener){
                        mLookBackListener.onRightKeyDown(channelCode);
                    }
                }
            }*/

            if (null != lookBackList && lookBackList.size() > 0) {
                mThrendPool.execute(new LookBackThread());
                isCenter = false;
                isLeft = false;
                isResource = false;
                DebugUtil.e("显示回看列表");
            } else {
                isCenter = true;
                isLeft = true;
                isResource = true;
            }

        }else if (!isResource && isLeft && isCenter) {
            isResource = true;
            isLeft = false;
            isCenter = false;
        } else if (!isResource && !isLeft && !isCenter) {
            mListView_right.setFocusable(true);
            isResource = false;
            isLeft = false;
            isCenter = true;
        }
        DebugUtil.e("isResource = " + isResource + " isLeft = " + isLeft + " isCenter = " + isCenter);
    }

    /**
     * 显示栏目跟频道
     */
    private void showResource() {
        mListView_source.setVisibility(VISIBLE);
        mListView_left.setVisibility(VISIBLE);
        mListView_left.requestFocus();
//        mAdapter.setPosition(mSecondPosition,true);
        mListView_center.setVisibility(GONE);
        mListView_right.setVisibility(GONE);

        if (mListView_left.getChildAt(mSecondPosition)!=null){
            TextView tv_channelCode = (TextView) mListView_left.getChildAt(mSecondPosition).findViewById(R.id.item_channel_id);
            TextView tv_channelName = (TextView) mListView_left.getChildAt(mSecondPosition).findViewById(R.id.item_channel_name);
            tv_channelCode.setTextColor(Color.parseColor("#FFFFFF"));
            tv_channelName.setTextColor(Color.parseColor("#FFFFFF"));
        }
        //TODO : 暂时不上线回看功能
        /*mListView_left.setLayoutParams(new LinearLayout.LayoutParams(
                (int) getResources().getDimension(R.dimen.px600),
                LinearLayout.LayoutParams.WRAP_CONTENT));*/
    }

    /**
     *  显示频道,回看
     */
    public void showLookBack() {
        //先做显示隐藏,再做焦点设置,否则有问题
        mListView_source.setVisibility(GONE);
        mListView_left.setVisibility(VISIBLE);
        mListView_center.setVisibility(View.VISIBLE);
        mListView_right.setVisibility(View.VISIBLE);

        mListView_center.requestFocus();
        mListView_left.setFocusable(false);
        mListView_center.setSelected(true);
        mListView_center.setSelection(0);

        //TODO : 暂时不上线回看功能
//        //left的回看影藏
//        if (null!=newView){
//            TextView textView = (TextView)newView.findViewById(R.id.tv_reback);
//            textView.setVisibility(GONE);
//        }

        //解决onFocusChange时 adapter刷新时卡顿
        if (mListView_left.getChildAt(mSecondPosition)!=null){
            TextView tv_channelCode = (TextView) mListView_left.getChildAt(mSecondPosition).findViewById(R.id.item_channel_id);
            TextView tv_channelName = (TextView) mListView_left.getChildAt(mSecondPosition).findViewById(R.id.item_channel_name);
            tv_channelCode.setTextColor(Color.parseColor("#FF8833"));
            tv_channelName.setTextColor(Color.parseColor("#FF8833"));

        }
        //设置listView的宽度
        mListView_left.setLayoutParams(new LinearLayout.LayoutParams(
                (int) getResources().getDimension(R.dimen.px400),
                LinearLayout.LayoutParams.WRAP_CONTENT));
    }


    public interface ListViewItemSelected {
        void onItemSelected();
    }

    public void setItemSelected(ListViewItemSelected itemSelected) {
        this.itemSelected = itemSelected;
    }

    private List<List<Channel>> allChannelLists = new ArrayList<>();

    /**
     * 查询栏目数据
     */
    private class ColumnThread implements Runnable {
        @Override
        public void run() {
            //数据库查询栏目数据
            List<ColumnModel> columnList = ColumnManager.getColumn(context, stbId);
//            Collections.reverse(columnList);
            //排序功能
            if (null != columnList && columnList.size() > 0) {
                for (int i=0;i<columnList.size();i++){
                    if (i==0){
                        columns.add(columnList.get(columnList.size()-1));
                    }else {
                        columns.add(columnList.get(i-1));
                    }
                    //根据栏目数据将频道数据分类,为防止滑动卡顿,使用双重list嵌套
                    String code_colum = columns.get(i).getColumncode();
                    channelList = ChannelManager.getChannelData(context, stbId, TableKey.COLUMNCODE, code_colum);
                    allChannelLists.add(channelList);
                }

            }
            //发送消息,刷新栏目数据
            mHandler.sendEmptyMessage(1);
        }
    }

    /**
     * 添加频道数据
     */
    private class ChannelThread implements Runnable{
        @Override
        public void run() {
            //刷新频道数据
            if (null != columns && columns.size() > mFirstPosition) {
                channelList=allChannelLists.get(mFirstPosition);
                mHandler.sendEmptyMessage(2);
            }
        }
    }

    /**
     * 添加回看数据
     */
    private class LookBackThread implements Runnable {
        @Override
        public void run() {
            if (null!=lookBackList&&lookBackList.size()>0){
                setThirdListDatas();
                setFourthDatas();
                mHandler.sendEmptyMessage(3);
            }
        }
    }

    /**
     *  添加最后一列listView的数据
     */
    private class FourthThread implements Runnable{
        @Override
        public void run() {
            mHandler.removeMessages(4);
            mRightDatas = allLookBackDatas.get(mThirdPosition);
            if (null!=mCenterDatas && mCenterDatas.size()>mThirdPosition){
                String currentDate = mCenterDatas.get(mThirdPosition);
                if (!TextUtils.isEmpty(currentDate)){
                    mRightAdapter.setSystemTime(serverTime,currentDate);
                }
            }
            mHandler.sendEmptyMessage(4);
        }
    }


    /**
     *  传递频道号给MainActivity加载数据
     * @return 频道Code
     */
    public String selectChannelCode(){
        if (null != channelList && channelList.size() > mSecondPosition) {
            String channelCode = channelList.get(mSecondPosition).getChannelcode();
            if (!TextUtils.isEmpty(channelCode)){
                return channelList.get(mSecondPosition).getChannelcode();
            }
        }
        return null;
    }
    /**
     *  获取回看数据与系统时间,channel-->iViewController-->player-->main
     */
    public void setLookBackDatas(List<LookBackModel> lists,String currentTime){
//        Toast.makeText(context,"回看数据的的大小 = "+lists.size(),Toast.LENGTH_LONG).show();
        this.lookBackList=lists;
        this.serverTime=currentTime;
//        Toast.makeText(context,"View的回看数据 :" +lookBackList.size(),Toast.LENGTH_LONG).show();
    }

    /**
     * 添加回看的日期
     */
    private void setThirdListDatas() {
        //数据先清空
        mCenterDatas.clear();
        if (null != lookBackList && lookBackList.size() > 0) {
            for (int i = 0; i < lookBackList.size(); i++) {
                //通过直播的开始时间,将回看数据根据天分类,set集合去重
                String beginTime = lookBackList.get(i).getBegintime();
                if (!TextUtils.isEmpty(beginTime) && beginTime.length()==19) {
                    String formatDate=beginTime.substring(5,7)+"月"+beginTime.substring(8,10)+"日";
                    set.add(formatDate);
                }
            }
            Iterator iter = set.iterator();
            while (iter.hasNext()) {
                String str = (String) iter.next();
                mCenterDatas.add(str);
            }
            if (null!=mCenterDatas && mCenterDatas.size()>0){
                //排序
                Collections.sort(mCenterDatas);
                Collections.reverse(mCenterDatas);
                DebugUtil.e("mCenterDatas = " + mCenterDatas.size());
            }
        }
    }
    /**
     *  添加回看的时间与节目
     */
    private void setFourthDatas() {
        mRightDatas.clear();
        LookBackModel mode=null;
        String beginTime =null;
        String endTime =null;
        String formatDate=null;
        String beginHourMinute=null;
        String endHourMinute=null;
        String time=null;
        if (null == mCenterDatas || mCenterDatas.size()==0
                ||null==lookBackList||lookBackList.size()==0) {
            return;
        }
        for (int j = 0; j < mCenterDatas.size(); j++) {
            //根据回看的日期,查询所有回看数据,得到当天的详细信息
            List<LookBackModel> tempList=null ;//因为添加之前要清空,所以用临时变量存储
            time = mCenterDatas.get(j);
            if (!TextUtils.isEmpty(time)) {
                tempList = new ArrayList<>();
                for (int i = 0; i < lookBackList.size(); i++) {
                    beginTime = lookBackList.get(i).getBegintime();
                    endTime = lookBackList.get(i).getEndtime();
                    if (beginTime.length()==19){
                        formatDate=beginTime.substring(5,7)+"月"+beginTime.substring(8,10)+"日";
                    }else {
                        formatDate=beginTime;
                    }
                    if (!TextUtils.isEmpty(formatDate)){
                        if (formatDate.equals(time)) {
                            mode= lookBackList.get(i);
                            //将开始时间跟结束时间格式化,避免滑动时刷新adapter造成卡顿
                            beginHourMinute=beginTime.substring(beginTime.indexOf(" ")+1,beginTime.length()-3);
                            endHourMinute=endTime.substring(endTime.indexOf(" ")+1,endTime.length()-3);
                            mode.setBegintime(beginHourMinute);
                            mode.setEndtime(endHourMinute);
                            tempList.add(mode);
                        }
                    }
                }
            }
            allLookBackDatas.add(tempList);
        }
    }

}
