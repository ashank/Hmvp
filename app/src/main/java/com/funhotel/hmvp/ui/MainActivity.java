package com.funhotel.hmvp.ui;

import android.content.IntentFilter;
import android.hardware.display.DisplayManager;
import android.net.wifi.WpsInfo;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.ArrayMap;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.funhotel.hmvp.*;
import com.funhotel.mvp.common.BaseModel;
import com.funhotel.mvp.entity.Admodel;
import com.funhotel.mvp.module.http.*;
import com.funhotel.mvp.module.http.FunhotelAPI;
import com.funhotel.mvp.utils.DebugUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 *  /*Call<BaseModel<List<Admodel>>> call=funhotelAPI.getAds("1233","hd_hdframe_031","1");
 call.enqueue(new Callback<BaseModel<List<Admodel>>>() {
@Override
public void onResponse(Call<BaseModel<List<Admodel>>> call,
Response<BaseModel<List<Admodel>>> response) {
DebugUtil.e(">>1111>>>"+response.body().getData());
}

@Override
public void onFailure(Call<BaseModel<List<Admodel>>> call, Throwable t) {

DebugUtil.e(">>1111>>>"+t.getMessage());

}
});


 /*Observable<BaseModel<List<Admodel>>> observable=funhotelAPI.getAds2("1233","hd_hdframe_031","1");
 observable.subscribeOn(Schedulers.newThread())//这里需要注意的是，网络请求在非ui线程。如果返回结果是依赖于Rxjava的，则需要变换线程
 .observeOn(AndroidSchedulers.mainThread())
 .subscribeOn(Schedulers.io())
 .unsubscribeOn(Schedulers.io())
 .subscribe(new Observer<BaseModel<List<Admodel>>>() {
@Override
public void onNext(BaseModel<List<Admodel>> user) {
Log.e(TAG, "onNext: ");
}

@Override
public void onSubscribe(Disposable d) {
Log.e(TAG, "onSubscribe: ");
}

@Override
public void onComplete() {
Log.e(TAG, "onComplete: ");
}

@Override
public void onError(Throwable error) {
// Error handling
Log.e(TAG, "onError: ");

}
});


 */

public class MainActivity extends AppCompatActivity {

    private FunhotelAPI funhotelAPI;
    private  final static String TAG="MainActivity";
    Disposable dis=null;

    WifiP2pManager wifiP2PManager;
    WiFiDirectBroadcastReceiver receiver;
    WifiP2pManager.Channel channel;
    private final IntentFilter intentFilter = new IntentFilter();
    List peers = new ArrayList();
    WifiP2pManager.PeerListListener peerListListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //  Indicates a change in the Wi-Fi P2P status.
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        // Indicates a change in the list of available peers.
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        // Indicates the state of Wi-Fi P2P connectivity has changed.
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        // Indicates this device's details have changed.
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);

        wifiP2PManager=(WifiP2pManager) getSystemService(WIFI_P2P_SERVICE);
        channel=wifiP2PManager.initialize(this, getMainLooper(), null);



        peerListListener = new WifiP2pManager.PeerListListener() {
            @Override
            public void onPeersAvailable(WifiP2pDeviceList peerList) {

                Log.e(TAG, "onPeersAvailable: size " +peerList.getDeviceList().size());
                // Out with the old, in with the new.
                Iterator<WifiP2pDevice> iterator = peerList.getDeviceList().iterator();
                WifiP2pDevice device;
                while (iterator.hasNext()) {
                    device = iterator.next();
                    Log.e(TAG, "onPeersAvailable: " +device.deviceName);
                    WifiP2pConfig config = new WifiP2pConfig();
                    config.deviceAddress = device.deviceAddress;

//                    mWifiP2pManager.connect(mChannel, config, new ActionListener() {
//                        @Override
//                        public void onSuccess() {
//                            Log.w(LOG_TAG, "Connect success!");
//                        }
//
//                        @Override
//                        public void onFailure(int reason) {
//                            Log.w(LOG_TAG, "Connect failure!");
//                        }
//                    });
                    break;
                }




//                peers.clear();
//                peers.addAll(peerList.getDeviceList());
//
//                Collection<WifiP2pDevice> list= peerList.getDeviceList();
//                Log.e(TAG, "onPeersAvailable: " +list.size());
//
//                for (WifiP2pDevice wifi:list){
//                    Log.e(TAG, "onPeersAvailable: "+wifi.deviceName+wifi.deviceAddress );
//                }
//                // If an AdapterView is backed by this data, notify it
//                // of the change.  For instance, if you have a ListView of available
//                // peers, trigger an update.
//                /*((WiFiPeerListAdapter) getListAdapter()).notifyDataSetChanged();*/
//                if (peers.size() == 0) {
//                    Log.d(TAG, "No devices found");
//                    return;
//                }
            }
        };

        receiver = new WiFiDirectBroadcastReceiver(wifiP2PManager, channel, this,peerListListener);
        registerReceiver(receiver, intentFilter);


        wifiP2PManager.discoverPeers(channel, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {
                Log.e(TAG, "onSuccess: " +"发现设备");
            }
            @Override
            public void onFailure(int reason) {
                Log.e(TAG, "onFailure: "+"没有发现设备" );

            }
        });

        wifiP2PManager.requestPeers(channel,peerListListener);
        wifiP2PManager.requestConnectionInfo(channel, new WifiP2pManager.ConnectionInfoListener() {
            @Override
            public void onConnectionInfoAvailable(WifiP2pInfo info) {


                if (info!=null&&info.groupOwnerAddress!=null) {
                    Log.e(TAG, "onConnectionInfoAvailable: "+info.groupOwnerAddress.getHostName() );
                } else {
                    Log.e(TAG, "onConnectionInfoAvailable: ");
                }
            }
        });


/*HttpManager manager=new HttpManager("http://222.68.210.55:33500/");
        manager.getAds2("1233","hd_hdframe_031","1")
                .subscribe(new Observer<BaseModel<List<Admodel>>>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.e(TAG, "onSubscribe: "+d.isDisposed());
                dis=d;
            }

            @Override
            public void onNext(BaseModel<List<Admodel>> value) {
                Log.e(TAG, "onNext: "+value.toString());

                dis.dispose();

            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError: "+e.getMessage());

                dis.dispose();
            }

            @Override
            public void onComplete() {
                Log.e(TAG, "onComplete: ");



                dis.dispose();
            }
        });*/


    }

    /** register the BroadcastReceiver with the intent values to be matched */
    @Override
    public void onResume() {
        super.onResume();



    }

    @Override
    public void onPause() {
        super.onPause();

    }


    @Override
    protected void onDestroy() {
        unregisterReceiver(receiver);
        super.onDestroy();
    }

    public void connect() {
        // Picking the first device found on the network.
        WifiP2pDevice device = (WifiP2pDevice) peers.get(0);

        WifiP2pConfig config = new WifiP2pConfig();
        config.deviceAddress = device.deviceAddress;
        config.wps.setup = WpsInfo.PBC;
        wifiP2PManager.connect(channel, config, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {
                // WiFiDirectBroadcastReceiver will notify us. Ignore for now.


            }

            @Override
            public void onFailure(int reason) {
                Toast.makeText(MainActivity.this, "Connect failed. Retry.",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }


}
