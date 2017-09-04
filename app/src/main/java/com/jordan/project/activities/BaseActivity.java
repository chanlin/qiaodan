package com.jordan.project.activities;

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

import com.safari.blelibs.BleManager;
import com.safari.blelibs.IBleManagerCallback;

import java.util.ArrayList;

/**
 * Created by icean on 2017/2/10.
 */

public abstract class BaseActivity extends Activity implements IBleManagerCallback{

    protected static final int REQUEST_BLUETOOTH_SETTING = 1100;
    protected static final int REQUEST_REQUEST_PERMISSION = 1200;

    private BleManager mBleManager;
    private boolean mIsNeedBluetooth, mIsNeedExtraPermission;
    private Handler mMainThreadHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBleManager = new BleManager(this, this);
        mIsNeedBluetooth = isNeedBluetooth();
        mIsNeedExtraPermission = isNeedExtraPermission();

        if (mIsNeedBluetooth) {
            if (!mBleManager.isLeEnabled()) {
                turnOnBluetooth();
            }
        }

        if (mIsNeedExtraPermission) {
            requestPermission();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_BLUETOOTH_SETTING:
                onEnableBTResult(resultCode);
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_REQUEST_PERMISSION:
                onRequestPermission(handleRequestResult(grantResults));
                break;
        }
    }

    protected BleManager getLeManager(){
        return mBleManager;
    }

    protected Handler getMainThreadHandler(){
        return mMainThreadHandler;
    }

    private void turnOnBluetooth(){
        Intent enable_bt_intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        startActivityForResult(enable_bt_intent, REQUEST_BLUETOOTH_SETTING);
    }

    private void requestPermission(){
        String[] all_request_permissions = getAllExtraPermissions();
        if (null == all_request_permissions || all_request_permissions.length == 0) {
            return;
        }

        ArrayList<String> need_request_permission = new ArrayList<>();
        for (int index = 0; index < all_request_permissions.length; index ++) {
            String current_permission = all_request_permissions[index];
            int check_result = ContextCompat.checkSelfPermission(this, current_permission);
            if (!(PackageManager.PERMISSION_GRANTED == check_result)) {
                need_request_permission.add(current_permission);
            }
        }
        if (null != need_request_permission && need_request_permission.size()>0) {
            String[] request_permissions = (String[])need_request_permission.toArray();
            ActivityCompat.requestPermissions(this, request_permissions, REQUEST_REQUEST_PERMISSION);
        }
    }

    private boolean handleRequestResult(int[] all_permission_status) {
        boolean check_result =  false;
        for (int index = 0; index<all_permission_status.length; index++) {
            int current_result = all_permission_status[index];
            if (!(PackageManager.PERMISSION_GRANTED == current_result)) {
                return false;
            } else {
                check_result = true;
            }
        }
        return check_result;
    }

    /**
     * @return Return true, if this activity need communicate with bluetooth
     */
    abstract boolean isNeedBluetooth();

    /**
     * Call when switch the bluetooth
     * @param result_code
     * @return
     */
    abstract boolean onEnableBTResult(int result_code);

    /**
     * @return Return true, if this activity need some extra permission
     */
    abstract boolean isNeedExtraPermission();

    abstract String[] getAllExtraPermissions();

    abstract void onRequestPermission(boolean is_granted);
}
