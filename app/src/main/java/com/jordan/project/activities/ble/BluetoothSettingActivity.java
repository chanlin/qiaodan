package com.jordan.project.activities.ble;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.jordan.project.R;
import com.jordan.project.adapter.BluetoothDeviceListAdapter;
import com.jordan.project.data.BluetoothDeviceInfo;
import com.safari.blelibs.BleManager;
import com.safari.blelibs.IBleManagerCallback;

import java.util.HashMap;

public class BluetoothSettingActivity extends Activity implements IBleManagerCallback, AdapterView.OnItemClickListener{

    private static final int REQUEST_CODE_LE = 1;
    private BleManager mBleManager;

    private ImageView mIVProcess;
    private TextView mTVProcess, mTVBLeDeviceName, mTVBleDeviceStatus;
    private ListView mLVDeviceList;
    private BluetoothDeviceListAdapter mDeviceAdapter;
    private AnimationDrawable mAnimationDrawable;

    private HashMap<String, BluetoothDeviceInfo> mDeviceMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth_setting);
        mIVProcess = (ImageView)findViewById(R.id.bluetooth_settings_process_iv);
        mTVProcess = (TextView)findViewById(R.id.bluetooth_settings_process_tv);
        mAnimationDrawable = (AnimationDrawable) mIVProcess.getDrawable();
        mTVBLeDeviceName = (TextView)findViewById(R.id.bluetooth_settings_bound_device_name);
        mTVBleDeviceStatus = (TextView)findViewById(R.id.bluetooth_settings_bound_device_status);
        mLVDeviceList = (ListView) findViewById(R.id.bluetooth_settings_bound_device_list);
        mLVDeviceList.setOnItemClickListener(this);

        mBleManager = new BleManager(this, this);
        mDeviceMap = new HashMap<>();

        if (checkLePermission()) {
            if (!mBleManager.isLeEnabled()) {
                Intent start_le_enable = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(start_le_enable, REQUEST_CODE_LE);
            } else {
                mBleManager.startManager();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        ((AnimationDrawable) mIVProcess.getDrawable()).start();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
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
        if (REQUEST_CODE_LE == requestCode) {
            if (RESULT_OK == resultCode){
                mBleManager.startManager();
            } else {
                finish();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBleManager.destroyManager();
        mBleManager = null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bluetooth_settings, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem start_menu = menu.findItem(R.id.menu_bluetooth_settings_start);
        MenuItem sync_menu = menu.findItem(R.id.menu_bluetooth_settings_sync);
        start_menu.setEnabled(mIsOpenSuccess);
        sync_menu.setEnabled(mIsOpenSuccess);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_bluetooth_settings_start:
                startSport();
                break;
            case R.id.menu_bluetooth_settings_sync:
                syncSportData();
                break;
        }
        return true;
    }

    private boolean checkLePermission(){
        if (PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_CODE_LE);
            return false;
        }
        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        mAnimationDrawable.start();
        BluetoothDeviceInfo info = (BluetoothDeviceInfo) mDeviceAdapter.getItem(position);
        mBleManager.setDeviceInfo(info.getDeviceName(), info.getDeviceAddress());
        mBleManager.connectToDevice(info.getDeviceName(), info.getDeviceAddress());
        mTVProcess.setText("正在连接设备");
        mTVBleDeviceStatus.setText("连接中");
    }

    @Override
    public void onBleManagerReady(boolean is_ready) {
        outputLog("onBleManagerReady::info= " + is_ready);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mTVProcess.setText("正在查找设备");
            }
        });
        mBleManager.startScanLeDevice();
    }

    @Override
    public void onStartScanDevice(boolean is_success) {
        outputLog("onStartScanDevice::info= " + is_success);
        if (null != mDeviceMap) {
            mDeviceMap.clear();
            mDeviceMap = null;
        }
        mDeviceMap = new HashMap<>();
    }

    @Override
    public void onStopScanDevice(boolean is_found){
        outputLog("onStopScanDevice==============");
        outputLog("onDeviceFound::info= " + is_found);
        if (is_found) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mTVProcess.setText("正在连接设备");
                    mDeviceAdapter = new BluetoothDeviceListAdapter(BluetoothSettingActivity.this, mDeviceMap);
                    mLVDeviceList.setAdapter(mDeviceAdapter);
                    mTVBLeDeviceName.setText(mBleManager.getDeviceName());
                    mTVBleDeviceStatus.setText("连接中");
                }
            });
        }else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mAnimationDrawable.stop();
                    mTVProcess.setText("请选择需要连接的芯片");
                    mDeviceAdapter = new BluetoothDeviceListAdapter(BluetoothSettingActivity.this, mDeviceMap);
                    mLVDeviceList.setAdapter(mDeviceAdapter);
                    mTVBLeDeviceName.setText(TextUtils.isEmpty(mBleManager.getDeviceName()) ? "UnSettings" : mBleManager.getDeviceName());
                    mTVBleDeviceStatus.setText("");
                }
            });
        }
    }

    @Override
    public void onScanDevice(String device_address, String device_name, String device_class, byte[] broadcast_record) {
        outputLog("onScanDevice::address= " + device_address + " name= " + device_name + " class= " + device_class);
        BluetoothDeviceInfo current_device_info = new BluetoothDeviceInfo(device_name, device_address, device_class);
        mDeviceMap.put(device_address, current_device_info);
    }

    @Override
    public void onConnectDevice(boolean is_success) {
        outputLog("onConnectDevice::info= " + is_success);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mTVBLeDeviceName.setText(TextUtils.isEmpty(mBleManager.getDeviceName()) ? "UnSettings" : mBleManager.getDeviceName());
                mTVProcess.setText("已连接");
            }
        });
    }

    private Handler mMainHandler = new Handler();

    @Override
    public void onInitialNotification(boolean is_success) {
        outputLog("onInitialNotification::info= " + is_success);
        if (is_success) {
            mMainHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    openSwitcher();
                }
            }, 5000);
        }
    }

    @Override
    public void onReadCharacteristic(boolean is_success, String ch_uuid, byte[] ble_value) {
        outputLog("onReadCharacteristic::info= " + String.valueOf(is_success) + " uuid= " + ch_uuid );
    }

    @Override
    public void onCharacteristicChange(boolean is_success, String ch_uuid, byte[] ble_value) {
        outputLog("onCharacteristicChange::info= " + String.valueOf(is_success) + " uuid= " + ch_uuid );
    }

    @Override
    public void onWriteCharacteristic(boolean is_success, String ch_uuid, byte[] ble_value) {
        outputLog("onWriteCharacteristic::info= " + String.valueOf(is_success) + " uuid= " + ch_uuid );
    }

    @Override
    public void onDescriptorWrite(boolean is_success, String ch_uuid) {
        outputLog("onDescriptorWrite::info= " + String.valueOf(is_success) + " uuid= " + ch_uuid );
    }

    private static final String COMMAND_CH_UUID = "0883b03e-8535-b5a0-7140-a304d2495cba";//只写（命令）
    private static final String SWITCH_CH_UUID = "0883b03e-8535-b5a0-7140-a304d2495cb9";//开关通信前发1

    private static final String COMMAND_OPEN_SWITCHER = "0x01";
    private static final String COMMAND_START_SPORT = "0xff01";
    private static final String COMMAND_START_SPORT_REAL = "0xff08";
    private static final String COMMAND_STOP_SPORT = "0xff07";
    private static final String COMMAND_SYNC_SPORT_DATA = "0xffff01";

    private boolean mIsOpenSuccess = false;
    private void openSwitcher(){
        mIsOpenSuccess = mBleManager.writeUUid(SWITCH_CH_UUID, COMMAND_OPEN_SWITCHER);
        outputLog("openSwitcher::result= " + mIsOpenSuccess);
        invalidateOptionsMenu();
    }

    private void startSport(){
        mBleManager.writeUUid(COMMAND_CH_UUID, COMMAND_START_SPORT);
    }

    private void syncSportData(){
        mBleManager.writeUUid(COMMAND_CH_UUID, COMMAND_SYNC_SPORT_DATA);
    }

    private void stopSport(){
        mBleManager.writeUUid(COMMAND_CH_UUID, COMMAND_STOP_SPORT);
    }

    private void startSportReal(){
        mBleManager.writeUUid(COMMAND_CH_UUID, COMMAND_START_SPORT_REAL);
    }

    private static final boolean IS_ENABLE = true;
    private static final String TAG = "ble_settings";
    private void outputLog(String message) {
        if (IS_ENABLE)
            android.util.Log.e(TAG, message);
    }

}
