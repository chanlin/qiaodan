package com.jordan.project.activities.ble;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.safari.blelibs.BleManager;
import com.safari.blelibs.IBleManagerCallback;
import com.jordan.commonlibrary.CommonManager;
import com.safari.httplibs.config.InnerMessageConfig;
import com.jordan.project.JordanApplication;
import com.jordan.project.R;
import com.jordan.project.activities.adapter.BluetoothScanListAdapter;
import com.jordan.project.database.DatabaseService;
import com.jordan.project.entity.BluetoothData;
import com.jordan.project.utils.ActivityUtils;
import com.jordan.project.utils.JsonFalseUtils;
import com.jordan.project.utils.LogUtils;
import com.jordan.project.utils.SettingSharedPerferencesUtil;
import com.jordan.project.utils.ToastUtils;
import com.jordan.project.widget.LoadingProgressDialog;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;

public class BluetoothScanListActivity extends Activity implements IBleManagerCallback {


    ArrayList<BluetoothData> mBluetoothList = new ArrayList<BluetoothData>();
    ListView mLvBluetooth;
    BluetoothScanListAdapter mBluetoothScanListAdapter;
    private static final int N0_PERMISSION = 21;
    private static final int REQUEST_CODE_LE = 1;
    private HashMap<String, String> mDeviceMap;
    private BleManager mBleManager;
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case N0_PERMISSION:
                    ToastUtils.shortToast(BluetoothScanListActivity.this, R.string.please_open_permission);
                    break;
                case InnerMessageConfig.COMMON_BLUETOOTH_BIND_MESSAGE_SUCCESS:
                    LogUtils.showLog("Result", "COMMON_BLUETOOTH_BIND_MESSAGE_SUCCESS result:" + (String) msg.obj);
                    ToastUtils.shortToast(BluetoothScanListActivity.this, "蓝牙绑定成功");
                    String snAndMac = mBluetoothScanListAdapter.sn+"|"+mBluetoothScanListAdapter.mac.replace(":","");
                    SettingSharedPerferencesUtil.SetChoiesBluetoothValue(BluetoothScanListActivity.this
                            ,JordanApplication.getUsername(BluetoothScanListActivity.this),snAndMac);
                    //提示蓝牙列表界面可以刷新
                    JordanApplication.isUpdateBluetoothList = true;
                    LoadingProgressDialog.Dissmiss();
                    finish();
                    //shoesBind(shoesSN);
                    break;
                case InnerMessageConfig.COMMON_BLUETOOTH_BIND_MESSAGE_EXCEPTION:
                    ToastUtils.shortToast(BluetoothScanListActivity.this, getResources().getString(R.string.http_exception));
                    LoadingProgressDialog.Dissmiss();
                    break;
                case InnerMessageConfig.COMMON_BLUETOOTH_BIND_MESSAGE_FALSE:
                    try {

                        String errorMsg = JsonFalseUtils.onlyErrorCodeResult((String) msg.obj);
                        if (errorMsg != null)
                            ToastUtils.shortToast(BluetoothScanListActivity.this, errorMsg);
                        LoadingProgressDialog.Dissmiss();
                    } catch (JSONException e) {
                        mHandler.sendEmptyMessage(InnerMessageConfig.COMMON_GET_CODE_MESSAGE_EXCEPTION);
                        e.printStackTrace();
                    }

                    break;

            }

        }
    };
    private CommonManager commonManager;
    private boolean mIsGrant;
    private int loadingTime = 10000;
    private HashMap<String,BluetoothData> bindBluetooths;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_bluetooth_scan_list);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.bluetooth_scan_list_title);
        bindBluetooths = DatabaseService.findBluetooths(JordanApplication.getUsername(BluetoothScanListActivity.this));
        commonManager = new CommonManager(BluetoothScanListActivity.this, mHandler);
        mBleManager = new BleManager(this, this,loadingTime);
        mDeviceMap = new HashMap<>();
        TextView mTvTitle = (TextView) findViewById(R.id.title_text);
        mTvTitle.setText(getResources().getString(R.string.common_near_bluetooth_list));
        RelativeLayout mRLAdd = (RelativeLayout) findViewById(R.id.bluetooth_add);
        mRLAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoadingProgressDialog.show(BluetoothScanListActivity.this, false, true, 30000);
                mBleManager.startScanLeDevice();
            }
        });
        RelativeLayout mRLBack=(RelativeLayout)findViewById(R.id.title_back);
        mRLBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        setView();
        setListener();
        //开始调用蓝牙连接
        if (checkLePermission()) {
            if (!mBleManager.isLeEnabled()) {
                Intent start_le_enable = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(start_le_enable, REQUEST_CODE_LE);
            } else {
                mBleManager.startManager();
            }
        }
    }
    private void setView() {

        mLvBluetooth = (ListView)findViewById(R.id.bluetooth_list);
        mBluetoothScanListAdapter = new BluetoothScanListAdapter(BluetoothScanListActivity.this,mBluetoothList,commonManager);
        mLvBluetooth.setAdapter(mBluetoothScanListAdapter);
        //bluetoothList();

    }
    private void setListener() {
        mLvBluetooth.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }

    private boolean checkLePermission() {
        if (PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_CODE_LE);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        LogUtils.showLog("motioning", "onRequestPermissionsResult");
        if (PackageManager.PERMISSION_GRANTED == grantResults[0]) {
            mIsGrant = true;
        }
        if (REQUEST_CODE_LE == requestCode) {
            int index = 0;
            boolean is_found = false;
            for (; index < permissions.length; index++) {
                String current_permission = permissions[index];
                if (Manifest.permission.ACCESS_COARSE_LOCATION.equals(current_permission)) {
                    is_found = true;
                    break;
                }
            }
            if (is_found) {
                int current_result = grantResults[index];
                if (PackageManager.PERMISSION_GRANTED == current_result) {
                    if (!mBleManager.isLeEnabled()) {
                        Intent start_le_enable = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                        startActivityForResult(start_le_enable, REQUEST_CODE_LE);
                    } else {
                        mBleManager.startManager();
                    }
                    return;
                } else {
                    finish();
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        LogUtils.showLog("motioning", "onActivityResult");
        LogUtils.showLog("motioning", "requestCode:" + requestCode);
        LogUtils.showLog("motioning", "resultCode:" + resultCode);
        if (REQUEST_CODE_LE == requestCode) {
            if (RESULT_OK == resultCode) {
                mBleManager.startManager();
            } else {
                mHandler.sendEmptyMessage(N0_PERMISSION);
                finish();
            }
            return;
        }
    }
    @Override
    public void onBleManagerReady(boolean is_ready) {
        LogUtils.showLog("motioning", "onBleManagerReady isready:" + is_ready);
        LoadingProgressDialog.show(BluetoothScanListActivity.this, false, true, 30000);
        mBleManager.startScanLeDevice();
    }

    @Override
    public void onStartScanDevice(boolean is_success) {
        LogUtils.showLog("motioning", "onStartScanDevice is_success:" + is_success);

        if (null != mDeviceMap) {
            mDeviceMap.clear();
            mDeviceMap = null;
        }
        mDeviceMap = new HashMap<>();
    }

    @Override
    public void onStopScanDevice(boolean is_found) {
        LogUtils.showLog("motioning", "onStopScanDevice is_found:" + is_found);
//            runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    mBluetoothList = new ArrayList<BluetoothData>();
//                    LoadingProgressDialog.Dissmiss();
//                    //遍历mDeviceMap
//                    //赋值listview
//                    for(String key:mDeviceMap.keySet()){
//                        if(key.contains("/")) {
//                            BluetoothData bluetoothData = new BluetoothData();
//                            bluetoothData.setSn(key.substring(0,key.indexOf("/")));
//                            bluetoothData.setMac(key.substring(key.indexOf("/")+1));
//                            if(bindBluetooths.containsKey(bluetoothData.getSn())){
//                                bluetoothData.setBind(true);
//                            }else{
//                                bluetoothData.setBind(false);
//                            }
//                            mBluetoothList.add(bluetoothData);
//                        }
//                    }
//                    LogUtils.showLog("motioning", "mBluetoothList:" + mBluetoothList.size());
//                    mBluetoothScanListAdapter.updateList(mBluetoothList);
//                }
//            });

    }

    @Override
    public void onScanDevice(String device_address, String device_name, String device_class, byte[] broadcast_record) {
        if(device_name.contains("qiaodan")||device_name.contains("Basketball")) {
            LogUtils.showLog("motioning", "onScanDevice address= " + device_address + " name= " + device_name + " class= " + device_class);
            String snAndmac = ActivityUtils.getSNFromBroadcastRecord(broadcast_record);
            LogUtils.showLog("motioning", "onScanDevice snAndmac= " + snAndmac);
            if(!snAndmac.equals("")){
                if(!mDeviceMap.containsKey(snAndmac)) {
                    mDeviceMap.put(snAndmac, snAndmac);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mBluetoothList = new ArrayList<BluetoothData>();
                            LoadingProgressDialog.Dissmiss();
                            //遍历mDeviceMap
                            //赋值listview
                            for(String key:mDeviceMap.keySet()){
                                if(key.contains("/")) {
                                    BluetoothData bluetoothData = new BluetoothData();
                                    bluetoothData.setSn(key.substring(0,key.indexOf("/")));
                                    bluetoothData.setMac(key.substring(key.indexOf("/")+1));
                                    if(bindBluetooths.containsKey(bluetoothData.getSn())){
                                        bluetoothData.setBind(true);
                                    }else{
                                        bluetoothData.setBind(false);
                                    }
                                    mBluetoothList.add(bluetoothData);
                                }
                            }
                            LogUtils.showLog("motioning", "mBluetoothList:" + mBluetoothList.size());
                            mBluetoothScanListAdapter.updateList(mBluetoothList);
                        }
                    });
                }
            }
        }
    }

    @Override
    public void onConnectDevice(boolean is_success) {

    }

    @Override
    public void onInitialNotification(boolean is_success) {

    }

    @Override
    public void onReadCharacteristic(boolean is_success, String ch_uuid, byte[] ble_value) {

    }

    @Override
    public void onCharacteristicChange(boolean is_success, String ch_uuid, byte[] ble_value) {

    }

    @Override
    public void onWriteCharacteristic(boolean is_success, String ch_uuid, byte[] ble_value) {

    }

    @Override
    public void onDescriptorWrite(boolean is_success, String ch_uuid) {

    }
}
