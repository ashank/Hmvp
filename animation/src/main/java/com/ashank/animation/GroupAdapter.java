package com.ashank.animation;

import android.content.Context;
import android.net.wifi.p2p.WifiP2pDevice;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by zhiyahan on 2017/4/1.
 */

public class GroupAdapter extends RecyclerView.Adapter {


    private List<String> mWifiP2pDevices;
    private View mView;
    private Context mContext;

    public interface OnItemClickListener{
        void onItemClick(View view, int position);
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
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_device, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final MyViewHolder viewHolder=((MyViewHolder) holder);

    }

    @Override
    public int getItemCount() {
        return mWifiP2pDevices.size();
    }


    public void setWifiP2pDevices(List<String> wifiP2pDevices) {
        mWifiP2pDevices = wifiP2pDevices;
    }

    private class MyViewHolder extends RecyclerView.ViewHolder {



        public MyViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public String toString() {
            return super.toString();
        }
    }


    private static String getDeviceStatus(int deviceStatus) {
        Log.d("TAG", "Peer status :" + deviceStatus);
        switch (deviceStatus) {
            case WifiP2pDevice.AVAILABLE:
                return "Available";
            case WifiP2pDevice.INVITED:
                return "Invited";
            case WifiP2pDevice.CONNECTED:
                return "Connected";
            case WifiP2pDevice.FAILED:
                return "Failed";
            case WifiP2pDevice.UNAVAILABLE:
                return "Unavailable";
            default:
                return "Unknown";
        }
    }

}
