package com.jordan.commonlibrary.data;

import com.jordan.commonlibrary.config.CommonSystemConfig;
import com.safari.httplibs.utils.data.JsonInfo;

import org.json.JSONObject;

/**
 * Created by icean on 2017/2/1.
 */

public class BluetoothUpdateInfo extends JsonInfo {

    private String mName,mID;

    public BluetoothUpdateInfo(String id, String name) {
        mName = name;
        mID = id;
    }

    @Override
    public String toJsonStr() {
        try {
            JSONObject json_object = new JSONObject();
            json_object.put(CommonSystemConfig.BluetoothUpdateBindCofig.JSON_REQUEST_KEY_NAME, mName);
            json_object.put(CommonSystemConfig.BluetoothUpdateBindCofig.JSON_REQUEST_KEY_ID, mID);
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

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmID() {
        return mID;
    }

    public void setmID(String mID) {
        this.mID = mID;
    }

    @Override
    public String toString() {
        return "BluetoothUpdateInfo{" +
                "mName='" + mName + '\'' +
                ", mID='" + mID + '\'' +
                '}';
    }
}
