package com.jordan.commonlibrary.data;

import com.jordan.commonlibrary.config.CommonSystemConfig;
import com.safari.httplibs.utils.data.JsonInfo;

import org.json.JSONObject;

/**
 * Created by icean on 2017/2/1.
 */

public class BluetoothUnBindInfo extends JsonInfo {

    private String mIDS;

    public BluetoothUnBindInfo(String ids) {
        mIDS = ids;
    }

    @Override
    public String toJsonStr() {
        try {
            JSONObject json_object = new JSONObject();
            json_object.put(CommonSystemConfig.BluetoothUnBindCofig.JSON_REQUEST_KEY_IDS, mIDS);
            String result_json = json_object.toString();
            return  result_json;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String toString() {
        return "BluetoothUnBindInfo{" +
                "mIDS='" + mIDS + '\'' +
                '}';
    }
}
