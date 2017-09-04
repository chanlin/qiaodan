package com.jordan.usersystemlibrary.data;

import com.safari.httplibs.utils.data.JsonInfo;
import com.jordan.usersystemlibrary.config.UserSystemConfig;

import org.json.JSONObject;

/**
 * Created by icean on 2017/2/2.
 */

public final class ModifyPasswordInfo extends JsonInfo {
    private String mOldPsd, mNewPsd;

    public ModifyPasswordInfo(String old_password, String new_password) {
        mOldPsd = old_password;
        mNewPsd = new_password;
    }

    public String getOldPsd() {
        return mOldPsd;
    }

    public String getNewPsd() {
        return mNewPsd;
    }

    @Override
    public String toJsonStr() {
        try {
            JSONObject json_object = new JSONObject();
            json_object.put(UserSystemConfig.ModifyPasswordMessageConfig.JSON_REQUEST_OLD_PASSWORD, mOldPsd);
            json_object.put(UserSystemConfig.ModifyPasswordMessageConfig.JSON_REQUEST_NEW_PASSWORD, mNewPsd);
            String result_json = json_object.toString();
            return  result_json;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String toString() {
        return "ModifyPasswordInfo{" +
                "mOldPsd='" + mOldPsd + '\'' +
                ", mNewPsd='" + mNewPsd + '\'' +
                '}';
    }
}
