package com.jordan.usersystemlibrary.data;

import com.safari.httplibs.utils.data.JsonInfo;
import com.jordan.usersystemlibrary.config.UserSystemConfig;

import org.json.JSONObject;

/**
 * Created by icean on 2017/2/2.
 */

public final class CreateListInfo extends JsonInfo {
    private String mPageNo;
    private String mPageSize;

    public CreateListInfo(String pageNo, String pageSize) {
        mPageNo=pageNo;
        mPageSize=pageSize;
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
            json_object.put(UserSystemConfig.GetCreateListConfig.JSON_REQUEST_PAGE_NO, mPageNo);
            json_object.put(UserSystemConfig.GetCreateListConfig.JSON_REQUEST_PAGE_SIZE, mPageSize);
            String result_json = json_object.toString();
            return  result_json;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String toString() {
        return "CreateListInfo{" +
                "mPageNo='" + mPageNo + '\'' +
                ", mPageSize='" + mPageSize + '\'' +
                '}';
    }
}
