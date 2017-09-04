package com.jordan.usersystemlibrary.data;

import com.safari.httplibs.utils.data.JsonInfo;
import com.jordan.usersystemlibrary.config.UserSystemConfig;

import org.json.JSONObject;

/**
 * Created by icean on 2017/2/2.
 */

public final class ShoesUnBindInfo extends JsonInfo {
    private String mIDS;

    public ShoesUnBindInfo(String ids) {
        mIDS=ids;
    }



    @Override
    public String toJsonStr() {
        try {
            JSONObject json_object = new JSONObject();
            json_object.put(UserSystemConfig.GetShoesUnBindConfig.JSON_REQUEST_PAGE_IDS,mIDS);
            String result_json = json_object.toString();
            return  result_json;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
