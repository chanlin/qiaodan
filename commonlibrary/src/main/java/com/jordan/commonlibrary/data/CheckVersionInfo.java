package com.jordan.commonlibrary.data;

import com.jordan.commonlibrary.config.CommonSystemConfig;
import com.safari.httplibs.utils.data.JsonInfo;

import org.json.JSONObject;

/**
 * Created by icean on 2017/2/1.
 */

public final class CheckVersionInfo extends JsonInfo{

    private String mDeviceType;

    public CheckVersionInfo(String device_type) {
        mDeviceType = device_type;
    }

    public String getDeviceType() {
        return mDeviceType;
    }

    @Override
    public String toJsonStr() {
        try {
            JSONObject json_object = new JSONObject();
            json_object.put(CommonSystemConfig.CheckAppUpdateMessageConfig.JSON_REQUEST_KEY_TYPE, mDeviceType);
            String check_version_str = json_object.toString();
            return check_version_str;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String toString() {
        return "CheckVersionInfo{" +
                "mDeviceType='" + mDeviceType + '\'' +
                '}';
    }
}
