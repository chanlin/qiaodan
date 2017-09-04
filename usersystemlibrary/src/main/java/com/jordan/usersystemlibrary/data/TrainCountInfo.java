package com.jordan.usersystemlibrary.data;

import com.safari.httplibs.utils.data.JsonInfo;
import com.jordan.usersystemlibrary.config.UserSystemConfig;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by icean on 2017/2/2.
 */

public final class TrainCountInfo extends JsonInfo {
    private String mID;

    public TrainCountInfo(String id) {
        mID = id;
    }


    @Override
    public String toJsonStr() {
        try {
            JSONObject json_object = new JSONObject();
            JSONArray json_array = new JSONArray();
            JSONObject json_reads = new JSONObject();
            json_reads.put(UserSystemConfig.GetTrainCountConfig.JSON_REQUEST_ID, mID);
            json_reads.put(UserSystemConfig.GetTrainCountConfig.JSON_REQUEST_COUNT, "1");
            json_array.put(json_reads);
            json_object.put(UserSystemConfig.GetTrainCountConfig.JSON_REQUEST_READS, json_array);
            String result_json = json_object.toString();
            return  result_json;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String toString() {
        return "TrainListInfo{" +
                "mID='" + mID + '\'' +
                '}';
    }
}
