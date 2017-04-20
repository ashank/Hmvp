package com.funhotel.miracast;

import android.content.Context;
import android.net.wifi.p2p.WifiP2pDevice;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zhiyahan on 2017/4/1.
 */

public class WifiDevicesAdapter extends RecyclerView.Adapter {


    private List<WifiP2pDevice> mWifiP2pDevices;
    private View mView;
    private Context mContext;

    public interface OnItemClickListener{
        void onItemClick(View view ,int position);
    }
    private OnItemClickListener mOnItemClickListener;
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public WifiDevicesAdapter(List<WifiP2pDevice> mWifiP2pDevices, Context context) {

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
        viewHolder.mDeviceName.setText(mWifiP2pDevices.get(position).deviceName+"-"+mWifiP2pDevices.get(position).deviceAddress);
        viewHolder.mDeviceStatus.setText(getDeviceStatus(mWifiP2pDevices.get(position).status));
        viewHolder.mDeviceDetail.setText(mWifiP2pDevices.get(position).toString());
        viewHolder.mItemRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener!=null){
                    mOnItemClickListener.onItemClick(viewHolder.itemView,position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mWifiP2pDevices.size();
    }


    public void setWifiP2pDevices(List<WifiP2pDevice> wifiP2pDevices) {
        mWifiP2pDevices = wifiP2pDevices;
    }

    private class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.device_name)
        TextView mDeviceName;
        @BindView(R.id.device_status)
        TextView mDeviceStatus;
        @BindView(R.id.item_root)
        LinearLayout mItemRoot;

        @BindView(R.id.device_detail)
        TextView mDeviceDetail;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mDeviceName = (TextView) itemView.findViewById(R.id.device_name);
            mDeviceStatus = (TextView) itemView.findViewById(R.id.device_status);
            mItemRoot = (LinearLayout) itemView.findViewById(R.id.item_root);
            mDeviceDetail = (TextView) itemView.findViewById(R.id.device_detail);
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
