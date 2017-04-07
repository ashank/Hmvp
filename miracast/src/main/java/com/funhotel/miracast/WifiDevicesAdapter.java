package com.funhotel.miracast;

import android.content.Context;
import android.net.wifi.p2p.WifiP2pDevice;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

    public WifiDevicesAdapter(List<WifiP2pDevice> mWifiP2pDevices, Context context) {

        this.mWifiP2pDevices = mWifiP2pDevices;
        this.mContext = context;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_device, parent);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        ((MyViewHolder)holder).mTextView2.setText(mWifiP2pDevices.get(position).deviceName);


    }

    @Override
    public int getItemCount() {
        return mWifiP2pDevices.size();
    }


    private class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.textView2)
        TextView mTextView2;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }

        @Override
        public String toString() {
            return super.toString();
        }
    }

}
