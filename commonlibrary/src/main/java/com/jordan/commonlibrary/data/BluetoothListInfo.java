package com.jordan.commonlibrary.data;

import com.safari.httplibs.utils.data.JsonInfo;

import org.json.JSONObject;

/**
 * Created by icean on 2017/2/1.
 */

public class BluetoothListInfo extends JsonInfo {


    public BluetoothListInfo() {

    }

    @Override
    public String toJsonStr() {
        try {
            JSONObject json_object = new JSONObject();
            String result_json = json_object.toString();
            return  result_json;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
