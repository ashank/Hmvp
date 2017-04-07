package com.funhotel.miracast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.display.DisplayManager;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainActivity extends AppCompatActivity
        implements WifiP2pManager.PeerListListener,
        WifiP2pManager.ActionListener,
        WifiP2pManager.ConnectionInfoListener {

    @BindView(R.id.textView)
    TextView mTextView;
    @BindView(R.id.progressBar)
    ProgressBar mProgressBar;
    @BindView(R.id.reclerview1)
    RecyclerView mReclerview;
    @BindView(R.id.text)
    TextView mText;


    private StringBuilder mStringBuilder;
    private final static String TAG = "MainActivity";
    WifiP2pManager wifiP2PManager;
    WiFiDirectBroadcastReceiver receiver;
    WifiP2pManager.Channel channel;
    private final IntentFilter intentFilter = new IntentFilter();
    List<WifiP2pDevice> peers = new ArrayList();
    private boolean isWifiEnabled;
    private boolean isConnect;
    private WifiDevicesAdapter mWifiDevicesAdapter;
    private DisplayManager mDisplayManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mStringBuilder = new StringBuilder();
        //  Indicates a change in the Wi-Fi P2P status.
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        // Indicates a change in the list of available peers.
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        // Indicates the state of Wi-Fi P2P connectivity has changed.
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        // Indicates this device's details have changed.
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);
        //wifi发现
        wifiP2PManager = (WifiP2pManager) getSystemService(WIFI_P2P_SERVICE);
        channel = wifiP2PManager.initialize(this, getMainLooper(), null);
        wifiP2PManager.discoverPeers(channel, this);
        wifiP2PManager.requestPeers(channel, this);
        wifiP2PManager.requestConnectionInfo(channel, this);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mReclerview = (RecyclerView) findViewById(R.id.reclerview1);
        mReclerview.setLayoutManager(linearLayoutManager);
        mWifiDevicesAdapter = new WifiDevicesAdapter(peers, MainActivity.this);
        mReclerview.setAdapter(mWifiDevicesAdapter);


        mDisplayManager = (DisplayManager) getSystemService(DISPLAY_SERVICE);
        Display[] displays = mDisplayManager.getDisplays();


    }

    private   void setTextMarquee(TextView textView) {
        if (textView != null) {
            textView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
            textView.setSingleLine(true);
            textView.setSelected(true);
            textView.setFocusable(true);
            textView.setFocusableInTouchMode(true);
        }
    }


    private void updateStatus(String text) {
        mTextView.setText(text);
    }

    public void openSettings() {
        /*Intent intent = new Intent(this, SettingsActivity.class);
        intent.putExtra("onCharge", onCharge);
        intent.putExtra("onAlways", onAlways);
        startActivity(intent)*/
        ;
        try {
            Log.d("DEBUG", "open WiFi display settings in HTC");
            startActivity(new Intent("com.htc.wifidisplay.CONFIGURE_MODE_NORMAL"));
        } catch (Exception e) {
            try {
                Log.d("DEBUG", "open WiFi display settings in Samsung");
                startActivity(new Intent("com.samsung.wfd.LAUNCH_WFD_PICKER_DLG"));
            } catch (Exception e2) {
                Log.d("DEBUG", "open WiFi display settings in stock Android");
                startActivity(new Intent("android.settings.WIFI_DISPLAY_SETTINGS"));
            }
        }

    }

    protected void setIsWifiP2pEnabled(boolean isEnabled) {
        isWifiEnabled = isEnabled;
    }

    /**
     * register the BroadcastReceiver with the intent values to be matched
     */
    @Override
    public void onResume() {
        receiver = new WiFiDirectBroadcastReceiver();
        registerReceiver(receiver, intentFilter);
        super.onResume();
    }


    @Override
    public void onPause() {
        unregisterReceiver(receiver);
        super.onPause();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

   /* public void connect() {
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
    }*/


    /*连接完成，获取管理员的IP,跳转界面*/
    @Override
    public void onConnectionInfoAvailable(WifiP2pInfo info) {
        InetAddress address = null;
        boolean isGroupOwner = false;
        if (info.groupFormed && info.isGroupOwner) {
            address = info.groupOwnerAddress;
            isGroupOwner = true;
        } else if (info.groupFormed) {
            address = info.groupOwnerAddress;
            isGroupOwner = false;
        }
        if (null != address) {
                    /*Intent preIntent = getIntent();
                    preIntent.putExtra("address", address.getHostAddress());
                    preIntent.putExtra("isGroupOwner", isGroupOwner);
                     Fragment fragment=null;
                     setCurrentFragment(fragment);*/
            isConnect = true;
        }
    }


    /**
     * 开始发现
     */
    @Override
    public void onSuccess() {
        Log.e(TAG, "onSuccess: " + "发现设备");
    }

    /**
     * 发现失败
     *
     * @param reason
     */
    @Override
    public void onFailure(int reason) {
        Log.e(TAG, "onFailure: " + "没有发现设备");
        if (reason == WifiP2pManager.P2P_UNSUPPORTED) {
            updateStatus("Failed to get devices. Device not supported");
        } else if (reason == WifiP2pManager.BUSY) {
            updateStatus("Failed to get devices. Busy");
        } else {
            updateStatus("Failed to get devices. Error");
        }
    }

    /*发现周围设备了*/
    @Override
    public void onPeersAvailable(WifiP2pDeviceList peerList) {
        Log.e(TAG, "onPeersAvailable: size " + peerList.getDeviceList().size());
        peers.clear();
        peers.addAll(peerList.getDeviceList());
        if (peers.size() == 0) {
            Log.d(TAG, "No devices found");


            return;
        }

        Collection<WifiP2pDevice> list = peerList.getDeviceList();
        Log.e(TAG, "onPeersAvailable: " + list.size());
        for (WifiP2pDevice wifi : list) {
            /*mStringBuilder.append(wifi.deviceName);
            mTextView.setText(mStringBuilder.toString());*/
            Log.e(TAG, "onPeersAvailable: " + wifi.deviceName + wifi.deviceAddress);
        }
        // If an AdapterView is backed by this data, notify it
        // of the change.  For instance, if you have a ListView of available
        // peers, trigger an update.
        mWifiDevicesAdapter.notifyDataSetChanged();
    }

    @OnClick({R.id.textView, R.id.progressBar})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.textView:
                break;
            case R.id.progressBar:
                break;
        }
    }

    @OnClick(R.id.reclerview1)
    public void onViewClicked() {

    }

    public class WiFiDirectBroadcastReceiver extends BroadcastReceiver {

        public WiFiDirectBroadcastReceiver() {
        }

        @Override
        public IBinder peekService(Context myContext, Intent service) {
            return super.peekService(myContext, service);
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if (WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION.equals(action)) {
                /*指示wifi p2p的状态变化*/
                Log.e("TAG", "onReceive: WIFI_P2P_STATE_CHANGED_ACTION");
                int state = intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE, -1);
                if (state == WifiP2pManager.WIFI_P2P_STATE_ENABLED) {
                    setIsWifiP2pEnabled(true);
                    Log.e("TAG", "onReceive: " + "p2p可以使用");
                } else {
                    setIsWifiP2pEnabled(false);
                    Log.e("TAG", "onReceive: " + "p2p不可以使用");
                }
            } else if (WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION.equals(action)) {
                /*指示可用节点列表的变化*/
                Log.e("TAG", "onReceive: WIFI_P2P_PEERS_CHANGED_ACTION");
                if (wifiP2PManager != null) {
                    wifiP2PManager.requestPeers(channel, MainActivity.this);
                }

            } else if (WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION.equals(action)) {
                /*指示wifi p2p的状态变化*/
                // Connection state changed!  We should probably do something about
                // that.
                Log.e("TAG", "onReceive: WIFI_P2P_CONNECTION_CHANGED_ACTION");

            } else if (WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION.equals(action)) {
            /*DeviceListFragment fragment = (DeviceListFragment) activity.getFragmentManager()
                    .findFragmentById(R.id.frag_list);
            fragment.updateThisDevice((WifiP2pDevice) intent.getParcelableExtra(
                    WifiP2pManager.EXTRA_WIFI_P2P_DEVICE));*/

                Log.e("TAG", "onReceive: WIFI_P2P_THIS_DEVICE_CHANGED_ACTION");

            }
        }
    }


}

