package com.jordan.usersystemlibrary.data;

import com.safari.httplibs.utils.data.JsonInfo;
import com.jordan.usersystemlibrary.config.UserSystemConfig;

import org.json.JSONObject;

/**
 * Created by icean on 2017/2/2.
 */

public class CheckAccountInfo extends JsonInfo {

    private String mAccount, mAccountType;

    public CheckAccountInfo(String user_account, String account_type) {
        mAccount = user_account;
        mAccountType = account_type;
    }

    public String getAccount() {
        return mAccount;
    }

    public String getAccountType() {
        return mAccountType;
    }

    @Override
    public String toJsonStr() {
        try {
            JSONObject json_object = new JSONObject();
            json_object.put(UserSystemConfig.CheckAccountMessageConfig.JSON_REQUEST_KEY_ACCOUNT, mAccount);
            json_object.put(UserSystemConfig.CheckAccountMessageConfig.JSON_REQUEST_KEY_ACCOUNT_TYPE, mAccountType);
            String result_json = json_object.toString();
            return  result_json;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String toString() {
        return "CheckAccountInfo{" +
                "mAccount='" + mAccount + '\'' +
                ", mAccountType='" + mAccountType + '\'' +
                '}';
    }
}
