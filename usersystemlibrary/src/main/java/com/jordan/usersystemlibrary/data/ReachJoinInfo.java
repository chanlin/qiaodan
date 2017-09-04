package com.jordan.usersystemlibrary.data;

import com.safari.httplibs.utils.data.JsonInfo;
import com.jordan.usersystemlibrary.config.UserSystemConfig;

import org.json.JSONObject;

/**
 * Created by icean on 2017/2/2.
 */

public final class ReachJoinInfo extends JsonInfo {
    private String mID;

    public ReachJoinInfo(String id) {
        mID = id;
    }


    @Override
    public String toJsonStr() {
        try {
            JSONObject json_object = new JSONObject();
            json_object.put(UserSystemConfig.GetReachJoinConfig.JSON_REQUEST_ID, mID);
            String result_json = json_object.toString();
            return  result_json;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String toString() {
        return "ReachJoinInfo{" +
                "mID='" + mID + '\'' +
                '}';
    }
}
