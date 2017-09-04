package com.jordan.usersystemlibrary.data;

import com.safari.httplibs.utils.data.JsonInfo;
import com.jordan.usersystemlibrary.config.UserSystemConfig;

import org.json.JSONObject;

/**
 * Created by icean on 2017/2/2.
 */

public final class ShoesListInfo extends JsonInfo {
    private String mPageNo;
    private String mPageSize;

    public ShoesListInfo(String pageNo, String pageSize) {
        mPageNo=pageNo;
        mPageSize=pageSize;
    }


    @Override
    public String toJsonStr() {
        try {
            JSONObject json_object = new JSONObject();
            json_object.put(UserSystemConfig.GetShoesListConfig.JSON_REQUEST_PAGE_NO, mPageNo);
            json_object.put(UserSystemConfig.GetShoesListConfig.JSON_REQUEST_PAGE_SIZE, mPageSize);
            json_object.put(UserSystemConfig.GetShoesListConfig.JSON_REQUEST_STYLE_NUMBER, "");
            json_object.put(UserSystemConfig.GetShoesListConfig.JSON_REQUEST_MARKET_TIME , "");
            json_object.put(UserSystemConfig.GetShoesListConfig.JSON_REQUEST_COLOR , "");
            json_object.put(UserSystemConfig.GetShoesListConfig.JSON_REQUEST_SIZE , "");
            json_object.put(UserSystemConfig.GetShoesListConfig.JSON_REQUEST_FOR_PEOPLE , "");
            json_object.put(UserSystemConfig.GetShoesListConfig.JSON_REQUEST_FOR_SPACE , "");
            json_object.put(UserSystemConfig.GetShoesListConfig.JSON_REQUEST_FOR_POSITION , "");
            json_object.put(UserSystemConfig.GetShoesListConfig.JSON_REQUEST_STYLE , "");
            json_object.put(UserSystemConfig.GetShoesListConfig.JSON_REQUEST_FUNCTION , "");
            json_object.put(UserSystemConfig.GetShoesListConfig.JSON_REQUEST_MIN_PRICE , "");
            json_object.put(UserSystemConfig.GetShoesListConfig.JSON_REQUEST_MAX_PRICE , "");
            String result_json = json_object.toString();
            return  result_json;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
