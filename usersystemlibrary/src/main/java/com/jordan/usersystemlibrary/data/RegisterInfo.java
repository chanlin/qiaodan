package com.jordan.usersystemlibrary.data;

import com.safari.httplibs.utils.data.JsonInfo;
import com.jordan.usersystemlibrary.config.UserSystemConfig;

import org.json.JSONObject;

/**
 * Created by icean on 2017/2/2.
 */

public final class RegisterInfo extends JsonInfo {

    private String mAccount, mPassword, mAccountType, mCode;

    public RegisterInfo(String user_account, String password, String account_type, String code) {
        mAccount = user_account;
        mPassword = password;
        mAccountType = account_type;
        mCode = code;
    }

    @Override
    public String toJsonStr() {
        try {
            JSONObject json_object = new JSONObject();
            json_object.put(UserSystemConfig.RegisterMessageConfig.JSON_REQUEST_KEY_ACCOUNT, mAccount);
            json_object.put(UserSystemConfig.RegisterMessageConfig.JSON_REQUEST_KEY_ACCOUNT_TYPE, mAccountType);
            json_object.put(UserSystemConfig.RegisterMessageConfig.JSON_REQUEST_KEY_PASSWORD, mPassword);
            json_object.put(UserSystemConfig.RegisterMessageConfig.JSON_REQUEST_KEY_CODE, mCode);
            String result_json = json_object.toString();
            return  result_json;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getAccount() {
        return mAccount;
    }

    public String getPassword() {
        return mPassword;
    }

    public String getAccountType() {
        return mAccountType;
    }

    public String getCode() {
        return mCode;
    }

    @Override
    public String toString() {
        return "RegisterInfo{" +
                "mAccount='" + mAccount + '\'' +
                ", mPassword='" + mPassword + '\'' +
                ", mAccountType='" + mAccountType + '\'' +
                ", mCode='" + mCode + '\'' +
                '}';
    }
}
