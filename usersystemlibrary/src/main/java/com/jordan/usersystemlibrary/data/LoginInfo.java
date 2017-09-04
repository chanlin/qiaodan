package com.jordan.usersystemlibrary.data;

import com.safari.httplibs.utils.data.JsonInfo;
import com.jordan.usersystemlibrary.config.UserSystemConfig;

import org.json.JSONObject;

/**
 * Created by icean on 2017/2/2.
 */

public class LoginInfo extends JsonInfo {

    private String mAccount, mLoginType, mPassword, mCode;

    public LoginInfo(String user_account, String password, String login_type, String code) {
        mAccount = user_account;
        mPassword = password;
        mLoginType = login_type;
        mCode = code;
    }

    @Override
    public String toJsonStr() {
        try {
            JSONObject json_object = new JSONObject();
            json_object.put(UserSystemConfig.LoginMessageConfig.JSON_REQUEST_KEY_ACCOUNT, mAccount);
            json_object.put(UserSystemConfig.LoginMessageConfig.JSON_REQUEST_KEY_LOGIN_TYPE, mLoginType);
            json_object.put(UserSystemConfig.LoginMessageConfig.JSON_REQUEST_KEY_PASSWORD, mPassword);
            json_object.put(UserSystemConfig.LoginMessageConfig.JSON_REQUEST_KEY_CODE, mCode);
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

    public String getLoginType() {
        return mLoginType;
    }

    public String getCode() {
        return mCode;
    }

    @Override
    public String toString() {
        return "LoginInfo{" +
                "mAccount='" + mAccount + '\'' +
                ", mPassword='" + mPassword + '\'' +
                ", mAccountType='" + mLoginType + '\'' +
                ", mCode='" + mCode + '\'' +
                '}';
    }
}
