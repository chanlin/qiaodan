package com.jordan.usersystemlibrary.data;

import com.safari.httplibs.utils.data.JsonInfo;
import com.jordan.usersystemlibrary.config.UserSystemConfig;

import org.json.JSONObject;

/**
 * Created by icean on 2017/2/2.
 */

public final class ForgetPasswordInfo extends JsonInfo {
    private String mMobile, mPassword, mVerificationType, mCode;

    public ForgetPasswordInfo(String mobile, String password, String verification_type, String code) {
        mMobile = mobile;
        mPassword = password;
        mVerificationType = verification_type;
        mCode = code;
    }

    public String getMobile() {
        return mMobile;
    }

    public String getPassword() {
        return mPassword;
    }

    public String getVerificationType() {
        return mVerificationType;
    }

    public String getCode() {
        return mCode;
    }

    @Override
    public String toJsonStr() {
        try {
            JSONObject json_object = new JSONObject();
            json_object.put(UserSystemConfig.ForgetPasswordMessageConfig.JSON_REQUEST_MOBILE, mMobile);
            json_object.put(UserSystemConfig.ForgetPasswordMessageConfig.JSON_REQUEST_PASSWORD, mPassword);
            json_object.put(UserSystemConfig.ForgetPasswordMessageConfig.JSON_REQUEST_VERIFICATION_TYPE, mVerificationType);
            json_object.put(UserSystemConfig.ForgetPasswordMessageConfig.JSON_REQUEST_VERIFICATION_CODE, mCode);
            String result_json = json_object.toString();
            return  result_json;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String toString() {
        return "ForgetPasswordInfo{" +
                "mMobile='" + mMobile + '\'' +
                ", mPassword='" + mPassword + '\'' +
                ", mVerificationType='" + mVerificationType + '\'' +
                ", mCode='" + mCode + '\'' +
                '}';
    }
}
