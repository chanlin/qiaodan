package com.jordan.usersystemlibrary.data;

import com.safari.httplibs.utils.data.JsonInfo;
import com.jordan.usersystemlibrary.config.UserSystemConfig;

import org.json.JSONObject;

/**
 * Created by icean on 2017/2/2.
 */

public class ModifySingleUserDataInfo extends JsonInfo {
    private String mKey, mValue;

    public ModifySingleUserDataInfo(String key, String value) {
        mKey = key;
        mValue = value;
    }

    public String getKey() {
        return mKey;
    }

    public String getValue() {
        return mValue;
    }

    @Override
    public String toJsonStr() {
        try {
            JSONObject json_object = new JSONObject();
            json_object.put(UserSystemConfig.ModifyInfoSingleMessageConfig.JSON_REQUEST_KEY_KEY, mKey);
            json_object.put(UserSystemConfig.ModifyInfoSingleMessageConfig.JSON_REQUEST_KEY_VALUE, mValue);
            String result_json = json_object.toString();
            return  result_json;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String toString() {
        return "ModifySingleUserDataInfo{" +
                "mKey='" + mKey + '\'' +
                ", mValue='" + mValue + '\'' +
                '}';
    }
}
