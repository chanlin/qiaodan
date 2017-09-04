package com.jordan.commonlibrary.data;

import com.jordan.commonlibrary.config.CommonSystemConfig;
import com.safari.httplibs.utils.data.JsonInfo;

import org.json.JSONObject;

/**
 * Created by icean on 2017/2/1.
 */

public final class SendCodeInfo extends JsonInfo {
    private String mAccount, mType, mGenre;

    public SendCodeInfo(String user_account, String apply_type, String rec_type) {
        mAccount = user_account;
        mType = apply_type;
        mGenre = rec_type;
    }

    @Override
    public String toJsonStr() {
        try {
            JSONObject json_object = new JSONObject();
            json_object.put(CommonSystemConfig.GetCodeMessgaeCofig.JSON_REQUEST_KEY_ACCOUNT, mAccount);
            json_object.put(CommonSystemConfig.GetCodeMessgaeCofig.JSON_REQUEST_KEY_TYPE, mType);
            json_object.put(CommonSystemConfig.GetCodeMessgaeCofig.JSON_REQUEST_KEY_GENRE, mGenre);
            String result_json_str = json_object.toString();
            return  result_json_str;
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

    public String getGenre() {
        return mGenre;
    }

    @Override
    public String toString() {
        return "SendCodeInfo{" +
                "mAccount='" + mAccount + '\'' +
                ", mType='" + mType + '\'' +
                ", mGenre='" + mGenre + '\'' +
                '}';
    }
}
