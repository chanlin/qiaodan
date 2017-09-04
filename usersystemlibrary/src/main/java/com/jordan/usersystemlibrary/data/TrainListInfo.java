package com.jordan.usersystemlibrary.data;

import com.safari.httplibs.utils.data.JsonInfo;
import com.jordan.usersystemlibrary.config.UserSystemConfig;

import org.json.JSONObject;

/**
 * Created by icean on 2017/2/2.
 */

public final class TrainListInfo extends JsonInfo {
    private String mID;
    private String mPageNo;
    private String mPageSize;

    public TrainListInfo(String id,String pageNo,String pageSize) {
        mID = id;
        mPageNo=pageNo;
        mPageSize=pageSize;
    }


    @Override
    public String toJsonStr() {
        try {
            JSONObject json_object = new JSONObject();
            json_object.put(UserSystemConfig.GetTrainListConfig.JSON_REQUEST_ID, mID);
            json_object.put(UserSystemConfig.GetTrainListConfig.JSON_REQUEST_PAGE_NO, mPageNo);
            json_object.put(UserSystemConfig.GetTrainListConfig.JSON_REQUEST_PAGE_SIZE, mPageSize);
            json_object.put(UserSystemConfig.GetTrainListConfig.JSON_REQUEST_TYPE, "");
            json_object.put(UserSystemConfig.GetTrainListConfig.JSON_REQUEST_POSITION, "");
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
