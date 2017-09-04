package com.jordan.usersystemlibrary.data;

import com.safari.httplibs.utils.data.JsonInfo;
import com.jordan.usersystemlibrary.config.UserSystemConfig;

import org.json.JSONObject;

/**
 * Created by icean on 2017/2/2.
 */

public final class MoveListInfo extends JsonInfo {
    private String mBeginTime;
    private String mEndTime;
    private String mPageNo;
    private String mPageSize;

    public MoveListInfo(String beginTime, String endTime,String pageNo, String pageSize) {
        mBeginTime=beginTime;
        mEndTime=endTime;
        mPageNo=pageNo;
        mPageSize=pageSize;
    }

    public String getmBeginTime() {
        return mBeginTime;
    }

    public String getmEndTime() {
        return mEndTime;
    }

    public String getmPageNo() {
        return mPageNo;
    }

    public String getmPageSize() {
        return mPageSize;
    }

    @Override
    public String toJsonStr() {
        try {
            JSONObject json_object = new JSONObject();
            json_object.put(UserSystemConfig.GetMoveListConfig.JSON_REQUEST_PAGE_NO, mPageNo);
            json_object.put(UserSystemConfig.GetMoveListConfig.JSON_REQUEST_PAGE_SIZE, mPageSize);
            json_object.put(UserSystemConfig.GetMoveListConfig.JSON_REQUEST_PAGE_BEGIN_TIME, mBeginTime);
            json_object.put(UserSystemConfig.GetMoveListConfig.JSON_REQUEST_PAGE_END_TIME, mEndTime);
            String result_json = json_object.toString();
            return  result_json;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String toString() {
        return "MoveListInfo{" +
                "mBeginTime='" + mBeginTime + '\'' +
                ", mEndTime='" + mEndTime + '\'' +
                ", mPageNo='" + mPageNo + '\'' +
                ", mPageSize='" + mPageSize + '\'' +
                '}';
    }
}
