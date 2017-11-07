package com.ashank.animation;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.funhotel.mvp.module.image.ImageLoder;

import java.util.List;

import static com.funhotel.tvllibrary.view.JazzyViewPager.TAG;

/**
 * Created by zhiyahan on 2017/4/1.
 */

public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.MyViewHolder> {



    private MyViewHolder viewHolder;
    private List<String> mWifiP2pDevices;
    private View mView;
    private Context mContext;


    public interface OnItemClickListener {
        void onItemClick(View view, ImageView img,int position);
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public GroupAdapter(List<String> mWifiP2pDevices, Context context) {

        this.mWifiP2pDevices = mWifiP2pDevices;
        this.mContext = context;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_device, null);
        Log.e(TAG, "onCreateViewHolder: ");
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        Log.e(TAG, "onBindViewHolder: ");

        holder.itemText.setText("你是我的眼");

        ImageLoder.newInstance(mContext).
                load("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1493056013316&di=c6c4439f59d8cd56794c72494d27cb27&imgtype=0&src=http%3A%2F%2Fimg01.taopic.com%2F150329%2F240420-15032Z91F694.jpg",
                        holder.imgioiio);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mOnItemClickListener) {
                    mOnItemClickListener.onItemClick(holder.itemView,holder.imgioiio, position);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mWifiP2pDevices.size();
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {


        View itemView;

        ImageView imgioiio;
        TextView itemText;
        LinearLayout itemRoot;

        public MyViewHolder(final View itemView) {
            super(itemView);
            this.itemView = itemView;
            imgioiio=(ImageView)itemView.findViewById(R.id.imgioioioi);
            itemText=(TextView)itemView.findViewById(R.id.item_text);

        }
    }



}
