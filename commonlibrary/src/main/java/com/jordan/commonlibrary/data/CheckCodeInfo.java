package com.jordan.commonlibrary.data;

import com.jordan.commonlibrary.config.CommonSystemConfig;
import com.safari.httplibs.utils.data.JsonInfo;

import org.json.JSONObject;

/**
 * Created by icean on 2017/2/1.
 */

public class CheckCodeInfo extends JsonInfo {

    private String mAccount, mType, mCode;

    public CheckCodeInfo(String user_account, String apply_type, String check_code) {
        mAccount = user_account;
        mType = apply_type;
        mCode = check_code;
    }

    @Override
    public String toJsonStr() {
        try {
            JSONObject json_object = new JSONObject();
            json_object.put(CommonSystemConfig.CheckCodeMessgaeCofig.JSON_REQUEST_KEY_ACCOUNT, mAccount);
            json_object.put(CommonSystemConfig.CheckCodeMessgaeCofig.JSON_REQUEST_KEY_TYPE, mType);
            json_object.put(CommonSystemConfig.CheckCodeMessgaeCofig.JSON_REQUEST_KEY_CODE, mCode);
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

    public String getType() {
        return mType;
    }

    public String getCode() {
        return mCode;
    }

    @Override
    public String toString() {
        return "CheckCodeInfo{" +
                "mAccount='" + mAccount + '\'' +
                ", mType='" + mType + '\'' +
                ", mCode='" + mCode + '\'' +
                '}';
    }
}
