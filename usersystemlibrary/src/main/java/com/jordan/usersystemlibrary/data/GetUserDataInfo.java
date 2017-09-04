package com.jordan.usersystemlibrary.data;

import com.safari.httplibs.utils.data.JsonInfo;
import com.jordan.usersystemlibrary.config.UserSystemConfig;

import org.json.JSONObject;

/**
 * Created by icean on 2017/2/2.
 */

public final class GetUserDataInfo extends JsonInfo {
    private String mUserAccount;

    public GetUserDataInfo(String user_account) {
        mUserAccount = user_account;
    }

    public String getUserAccount() {
        return mUserAccount;
    }

    @Override
    public String toJsonStr() {
        try {
            JSONObject json_object = new JSONObject();
            json_object.put(UserSystemConfig.GetPersonalMessageConfig.JSON_REQUEST_KEY_ACCOUNT, mUserAccount);
            String result_json = json_object.toString();
            return  result_json;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String toString() {
        return "GetUserDataInfo{" +
                "mUserAccount='" + mUserAccount + '\'' +
                '}';
    }
}
