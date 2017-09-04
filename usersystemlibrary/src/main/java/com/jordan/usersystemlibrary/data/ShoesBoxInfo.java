package com.jordan.usersystemlibrary.data;

import com.safari.httplibs.utils.data.JsonInfo;

import org.json.JSONObject;

/**
 * Created by icean on 2017/2/2.
 */

public final class ShoesBoxInfo extends JsonInfo {

    public ShoesBoxInfo() {

    }


    @Override
    public String toJsonStr() {
        try {
            JSONObject json_object = new JSONObject();
            String result_json = json_object.toString();
            return  result_json;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
