package com.jordan.commonlibrary.data;

import com.jordan.commonlibrary.config.CommonSystemConfig;
import com.safari.httplibs.utils.data.JsonInfo;

import org.json.JSONObject;

/**
 * Created by icean on 2017/2/1.
 */

public class BluetoothBindInfo extends JsonInfo {

    private String mName,mSn, mMac;

    public BluetoothBindInfo(String sn, String mac) {
        mName = "qiaodan";
        mSn = sn;
        mMac = mac;
    }

    @Override
    public String toJsonStr() {
        try {
            JSONObject json_object = new JSONObject();
            json_object.put(CommonSystemConfig.BluetoothBindCofig.JSON_REQUEST_KEY_NAME, mName);
            json_object.put(CommonSystemConfig.BluetoothBindCofig.JSON_REQUEST_KEY_SN, mSn);
            json_object.put(CommonSystemConfig.BluetoothBindCofig.JSON_REQUEST_KEY_MAC, mMac);
            String result_json = json_object.toString();
            return  result_json;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getmName() {
        return mName;
    }

    public String getmMac() {
        return mMac;
    }

    @Override
    public String toString() {
        return "BluetoothBindInfo{" +
                "mName='" + mName + '\'' +
                ", mMac='" + mMac + '\'' +
                '}';
    }
}
