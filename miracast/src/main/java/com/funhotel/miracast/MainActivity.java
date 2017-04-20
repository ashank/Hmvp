package com.funhotel.miracast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.display.DisplayManager;
import android.media.Image;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.net.wifi.WpsInfo;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.funhotel.miracast.screenrecorder.RecorderActivity;
import com.funhotel.mvp.module.image.ImageLoder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity
        implements WifiP2pManager.PeerListListener,
        WifiP2pManager.ActionListener,
        WifiP2pManager.ConnectionInfoListener,
        WifiDevicesAdapter.OnItemClickListener{

    @BindView(R.id.textView)
    TextView mTextView;
    @BindView(R.id.progressBar)
    ProgressBar mProgressBar;
    @BindView(R.id.reclerview1)
    RecyclerView mReclerview;
    @BindView(R.id.device_name)
    TextView mDeviceName;
    @BindView(R.id.device_status)
    TextView mDeviceStatus;
    @BindView(R.id.button)
    FloatingActionButton mButton;
    @BindView(R.id.img)
    ImageView  img;


    protected static final int CHOOSE_FILE_RESULT_CODE = 20;
    private final static String TAG = "RecorderActivity";
    WifiP2pManager wifiP2PManager;
    WiFiDirectBroadcastReceiver receiver;
    WifiP2pManager.Channel channel;

    private IntentFilter intentFilter;
    List<WifiP2pDevice> peers = new ArrayList();
    private boolean isWifiEnabled;
    private boolean isConnect;
    private WifiDevicesAdapter mWifiDevicesAdapter;
    private DisplayManager mDisplayManager;
    private WifiManager mWifiManager;

    private WifiP2pDevice mLocalDevice;
    private String resultString;
    private InetAddress address = null;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    mWifiDevicesAdapter.setWifiP2pDevices(peers);
                    mWifiDevicesAdapter.notifyDataSetChanged();
                    break;

                case 1:
                    updateStatus(resultString);
                    break;
            }
            super.handleMessage(msg);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        img=(ImageView) findViewById(R.id.img);
        mButton=(FloatingActionButton)findViewById(R.id.button);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(MainActivity.this, RecorderActivity.class);
                startActivity(intent);
            }
        });
        //配置IntenFilter
        setupIntenFilter();

        receiver = new WiFiDirectBroadcastReceiver();
        registerReceiver(receiver, intentFilter);

        //如果没有打开wifi则打开
        mWifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        if (mWifiManager.getWifiState() != WifiManager.WIFI_STATE_ENABLED) {
            mWifiManager.setWifiEnabled(true);
        }
        //wifi发现
        wifiP2PManager = (WifiP2pManager) getSystemService(WIFI_P2P_SERVICE);
        channel = wifiP2PManager.initialize(this, getMainLooper(), null);
        //发现对等点
        wifiP2PManager.discoverPeers(channel, this);

        //wifi列表
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mReclerview = (RecyclerView) findViewById(R.id.reclerview1);
        mReclerview.setLayoutManager(linearLayoutManager);
        mWifiDevicesAdapter = new WifiDevicesAdapter(peers, MainActivity.this);
        mWifiDevicesAdapter.setOnItemClickListener(this);
        mReclerview.setAdapter(mWifiDevicesAdapter);


        mDisplayManager = (DisplayManager) getSystemService(DISPLAY_SERVICE);
        Display[] displays = mDisplayManager.getDisplays();

    }

    private void setupIntenFilter() {
        intentFilter = new IntentFilter();
        //  Indicates a change in the Wi-Fi P2P status.
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        // Indicates a change in the list of available peers.
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        // Indicates the state of Wi-Fi P2P connectivity has changed.
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        // Indicates this device's details have changed.
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_DISCOVERY_CHANGED_ACTION);
    }


    private void updateStatus(String text) {
        mTextView=(TextView)findViewById(R.id.textView4) ;
        mTextView.setText(text);
    }

    //打开设置
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
     * Update UI for this device.
     * @param device WifiP2pDevice object
     */
    public void updateThisDevice(WifiP2pDevice device) {
        this.mLocalDevice = device;
        mDeviceName=(TextView)findViewById(R.id.device_name);
        mDeviceStatus=(TextView)findViewById(R.id.device_status);
        mDeviceName.setText(device.deviceName+"-"+device.deviceAddress+"\n"+device.isGroupOwner());
        mDeviceStatus.setText(getDeviceStatus(device.status));

        mDeviceName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent serviceIntent = new Intent(MainActivity.this, FileTransferService.class);
                serviceIntent.setAction(FileTransferService.ACTION_SEND_FILE);
                serviceIntent.putExtra(FileTransferService.EXTRAS_FILE_PATH,
                        new File("/storage/emulated/0/DCIM/Camera/IMG_20161231_175528.jpg").toString());
                serviceIntent.putExtra(FileTransferService.EXTRAS_GROUP_OWNER_ADDRESS,
                        address.getHostAddress());
                serviceIntent.putExtra(FileTransferService.EXTRAS_GROUP_OWNER_PORT, 7236);
                startService(serviceIntent);
            }
        });
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

    /**
     * register the BroadcastReceiver with the intent values to be matched
     */
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
        if (mLocalDevice.status==WifiP2pDevice.CONNECTED){
            wifiP2PManager.removeGroup(channel,this);
        }else if (mLocalDevice.status==WifiP2pDevice.INVITED||
                mLocalDevice.status==WifiP2pDevice.AVAILABLE){
            wifiP2PManager.cancelConnect(channel,this);
        }
        super.onDestroy();
    }


    /*连接完成，获取管理员的IP,跳转界面*/
    @Override
    public void onConnectionInfoAvailable(final WifiP2pInfo info) {
        Log.e(TAG, "onConnectionInfoAvailable: " );
        if (info.groupFormed && info.isGroupOwner) {
            address = info.groupOwnerAddress;
            new FileServerAsyncTask(MainActivity.this)
                    .execute();
        } else if (info.groupFormed) {
            address = info.groupOwnerAddress;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                resultString=info.toString();
                mHandler.sendEmptyMessage(1);
            }
        }).start();


        if (null != address) {
                    /*Intent preIntent = getIntent();
                    preIntent.putExtra("address", address.getHostAddress());
                    preIntent.putExtra("isGroupOwner", isGroupOwner);
                     Fragment fragment=null;
                     setCurrentFragment(fragment);*/
            isConnect = true;
        }
    }

    @Override
    public void onSuccess() {
        Log.e(TAG, "onSuccess: " + "初始化设备成功");
    }

    @Override
    public void onFailure(int reason) {
        Log.e(TAG, "onFailure: " + "初始化设备失败");
        if (reason == WifiP2pManager.P2P_UNSUPPORTED) {
            updateStatus("Failed to get devices. Device not supported");
        } else if (reason == WifiP2pManager.BUSY) {
            updateStatus("Failed to get devices. Busy");
        } else {
            updateStatus("Failed to get devices. Error");
        }
    }

    /*发现周围设备了,使用listView 显示*/
    @Override
    public void onPeersAvailable(final WifiP2pDeviceList peerList) {
        Log.e(TAG, "onPeersAvailable: size " + peerList.getDeviceList().size());
        new Thread(new Runnable() {
            @Override
            public void run() {
                peers.clear();
                peers.addAll(peerList.getDeviceList());
                if (peers.size() == 0) {
                    Log.d(TAG, "No devices found");
                    mHandler.sendEmptyMessage(0);
                    return;
                }
                mHandler.sendEmptyMessage(0);
            }
        }).start();
    }


    @Override
    public void onItemClick(View view, int position) {
        //点击事件
        Toast.makeText(MainActivity.this, "position=" + position, Toast.LENGTH_SHORT).show();
        if (peers.get(position).status==WifiP2pDevice.CONNECTED){
            return;
        }
        WifiP2pConfig config=new WifiP2pConfig();
        config.deviceAddress=peers.get(position).deviceAddress;
        config.wps.setup = WpsInfo.PBC;
        wifiP2PManager.connect(channel,config,new WifiP2pManager.ActionListener(){
            @Override
            public void onSuccess() {
                Toast.makeText(MainActivity.this, "Connect success",
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int reason) {
                Toast.makeText(MainActivity.this, "Connect failed. Retry.",
            Toast.LENGTH_SHORT).show();
        }
        });

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // User has picked an image. Transfer it to group owner i.e peer using
        // FileTransferService.
        Uri uri = data.getData();
        Log.d(TAG, "Intent----------- " + uri);

        Intent serviceIntent = new Intent(MainActivity.this, FileTransferService.class);
        serviceIntent.setAction(FileTransferService.ACTION_SEND_FILE);
        serviceIntent.putExtra(FileTransferService.EXTRAS_FILE_PATH, uri.toString());
        serviceIntent.putExtra(FileTransferService.EXTRAS_GROUP_OWNER_ADDRESS,
                address.getHostAddress());
        serviceIntent.putExtra(FileTransferService.EXTRAS_GROUP_OWNER_PORT, 7236);
        startService(serviceIntent);
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
            /*Log.e("TAG", "onReceive:action>>>>"+action);*/
            if (WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION.equals(action)) {
                //当启用或禁用设备上的Wi-Fi Direct时，发出这个广播。
                Log.e("TAG", "onReceive: WIFI_P2P_STATE_CHANGED_ACTION");
                int state = intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE, -1);
                if (state == WifiP2pManager.WIFI_P2P_STATE_ENABLED) {
                    setIsWifiP2pEnabled(true);
                    Log.e("TAG", "onReceive: " + "p2p启动");
                    if (wifiP2PManager != null) {
                        wifiP2PManager.requestPeers(channel, MainActivity.this);
                    }
                } else {
                    setIsWifiP2pEnabled(false);
                    Log.e("TAG", "onReceive: " + "p2p禁用");
                }
            } else if (WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION.equals(action)) {
                //在调用discoverPeers()方法时，发出这个广播，如果你要在应用程序中处理这个Intent，
                // 通常是希望调用requestPeers()方法来获取对等设备的更新列表。
                Log.e("TAG", "onReceive: WIFI_P2P_PEERS_CHANGED_ACTION");
                if (wifiP2PManager != null) {
                    wifiP2PManager.requestPeers(channel, MainActivity.this);
                }

            } else if (WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION.equals(action)) {
                //在设备的Wi-Fi连接状态变化时，发出这个广播。
                Log.e("TAG", "onReceive: WIFI_P2P_CONNECTION_CHANGED_ACTION");
                if (wifiP2PManager == null) {
                    return;
                }
                NetworkInfo networkInfo = (NetworkInfo) intent
                        .getParcelableExtra(WifiP2pManager.EXTRA_NETWORK_INFO);
                if (networkInfo.isConnected()) {
                    // we are connected with the other device, request connection
                    // info to find group owner IP
                    wifiP2PManager.requestConnectionInfo(channel, MainActivity.this);
                } else {
                    //链接失败获取断开
                    // It's a disconnect
                    Log.e(TAG, "onReceive: networkInfo>>>>"+ networkInfo.isConnected());
                }

            } else if (WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION.equals(action)) {
                //当设备的细节（如设备的名称）发生变化时，发出这个广播。
                Log.e("TAG", "onReceive: WIFI_P2P_THIS_DEVICE_CHANGED_ACTION");
                updateThisDevice((WifiP2pDevice) intent.getParcelableExtra(
                    WifiP2pManager.EXTRA_WIFI_P2P_DEVICE));

            }
        }
    }


    /**
     * A simple server socket that accepts connection and writes some data on
     * the stream.
     */
    public static class FileServerAsyncTask extends AsyncTask<Void, Void, String> {

        private Context context;

        /**
         * @param context
         */
        public FileServerAsyncTask(Context context) {
            this.context = context;
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                ServerSocket serverSocket = new ServerSocket(7236);
                Log.e(TAG, "Server: Socket opened");
                Socket client = serverSocket.accept();
                Log.e(TAG, "Server: connection done");
                final File f = new File(Environment.getExternalStorageDirectory() + "/"
                        + "MI" + "/wifip2pshared"
                        + ".jpg");

                File dirs = new File(f.getParent());
                if (!dirs.exists())
                    dirs.mkdirs();
                f.createNewFile();
                Log.d(TAG, "server: copying files " + f.toString());
                InputStream inputstream = client.getInputStream();
                copyFile(inputstream, new FileOutputStream(f));
                serverSocket.close();
                return f.getAbsolutePath();
            } catch (IOException e) {
                Log.e(TAG, e.getMessage());
                return null;
            }
        }

        /*
         * (non-Javadoc)
         * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
         */
        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
                Toast.makeText(context, "result=="+result, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.setAction(android.content.Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.parse(result), "image/*");
                context.startActivity(intent);
            }
        }

        /*
         * (non-Javadoc)
         * @see android.os.AsyncTask#onPreExecute()
         */
        @Override
        protected void onPreExecute() {
            Toast.makeText(context, "open==", Toast.LENGTH_SHORT).show();
        }
    }

    public static boolean copyFile(InputStream inputStream, OutputStream out) {
        byte buf[] = new byte[1024];
        int len;
        try {
            while ((len = inputStream.read(buf)) != -1) {
                out.write(buf, 0, len);
            }
            out.close();
            inputStream.close();
        } catch (IOException e) {
            Log.d(TAG, e.toString());
            return false;
        }
        return true;
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode!=KeyEvent.KEYCODE_BACK) {
            Intent serviceIntent = new Intent(MainActivity.this, FileTransferService.class);
            serviceIntent.setAction(FileTransferService.ACTION_SEND_FILE);
            serviceIntent.putExtra(FileTransferService.EXTRAS_FILE_PATH,
                    new File("/sdcard/IPTV/homeResourse/templete_background.png").toString());
            serviceIntent.putExtra(FileTransferService.EXTRAS_GROUP_OWNER_ADDRESS,
                    address.getHostAddress());
            serviceIntent.putExtra(FileTransferService.EXTRAS_GROUP_OWNER_PORT, 7236);
            startService(serviceIntent);
        }
        return super.onKeyDown(keyCode, event);
    }
}

