package com.funhotel.miracast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.IBinder;
import android.util.Log;

/**
 * Created by zhiyahan on 2017/3/30.
 */

public class WiFiDirectBroadcastReceiver extends BroadcastReceiver {


    WifiP2pManager wifiP2PManager;
    WifiP2pManager.Channel channel;
    private Context mContext;
    private WifiP2pManager.PeerListListener mPeerListListener;

    public WiFiDirectBroadcastReceiver(WifiP2pManager wifiP2PManager, WifiP2pManager.Channel channel, Context context, WifiP2pManager.PeerListListener listListener) {
        this.wifiP2PManager = wifiP2PManager;
        this.channel = channel;
        this.mContext=context;
        this.mPeerListListener=listListener;
    }

    @Override
    public IBinder peekService(Context myContext, Intent service) {
        return super.peekService(myContext, service);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION.equals(action)) {
            Log.e("TAG", "onReceive: WIFI_P2P_STATE_CHANGED_ACTION" );
            // Determine if Wifi P2P mode is enabled or not, alert
            // the Activity.
            int state = intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE, -1);
            if (state == WifiP2pManager.WIFI_P2P_STATE_ENABLED) {
//                context.setIsWifiP2pEnabled(true);
                Log.e("TAG", "onReceive: "+"p2p可以使用" );
            } else {
//                context.setIsWifiP2pEnabled(false);
                Log.e("TAG", "onReceive: "+"p2p不可以使用" );
            }
        } else if (WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION.equals(action)) {

            // The peer list has changed!  We should probably do something about
            // that.WIFI_P2P_STATE_CHANGED_ACTION

            Log.e("TAG", "onReceive: WIFI_P2P_PEERS_CHANGED_ACTION" );

            if (wifiP2PManager != null) {
                wifiP2PManager.requestPeers(channel, mPeerListListener);
            }

        } else if (WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION.equals(action)) {

            // Connection state changed!  We should probably do something about
            // that.
            Log.e("TAG", "onReceive: WIFI_P2P_CONNECTION_CHANGED_ACTION" );

        } else if (WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION.equals(action)) {
            /*DeviceListFragment fragment = (DeviceListFragment) activity.getFragmentManager()
                    .findFragmentById(R.id.frag_list);
            fragment.updateThisDevice((WifiP2pDevice) intent.getParcelableExtra(
                    WifiP2pManager.EXTRA_WIFI_P2P_DEVICE));*/

            Log.e("TAG", "onReceive: WIFI_P2P_THIS_DEVICE_CHANGED_ACTION" );

        }
    }
}
