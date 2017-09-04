package com.jordan.usersystemlibrary.data;

import com.safari.httplibs.utils.data.JsonInfo;
import com.jordan.usersystemlibrary.config.UserSystemConfig;

import org.json.JSONObject;

/**
 * Created by icean on 2017/2/2.
 */

public final class ReachListInfo extends JsonInfo {
    private String mBeginTime;
    private String mEndTime;
    private String mType;
    private String mLongitude;
    private String mLatitude;
    private String mProvince;
    private String mCity;
    private String mDistrict;
    private String mLimited;
    private String mPageNo;
    private String mPageSize;
    private String mSort;

    public ReachListInfo(String beginTime,String endTime,String type,String longitude,
                         String latitude,String province,String city,String district,
                         String limited,String pageNo,String pageSize,String sort) {
        mBeginTime=beginTime;
        mEndTime=endTime;
        mType=type;
        mLongitude=longitude;
        mLatitude=latitude;
        mProvince=province;
        mCity=city;
        mDistrict=district;
        mLimited=limited;
        mPageNo=pageNo;
        mPageSize=pageSize;
        mSort=sort;
    }

    public String getmBeginTime() {
        return mBeginTime;
    }

    public String getmType() {
        return mType;
    }

    public String getmLongitude() {
        return mLongitude;
    }

    public String getmLatitude() {
        return mLatitude;
    }

    public String getmProvince() {
        return mProvince;
    }

    public String getmCity() {
        return mCity;
    }

    public String getmDistrict() {
        return mDistrict;
    }

    public String getmLimited() {
        return mLimited;
    }

    public String getmPageNo() {
        return mPageNo;
    }

    public String getmPageSize() {
        return mPageSize;
    }

    public String getmSort() {
        return mSort;
    }

    @Override
    public String toJsonStr() {
        try {
            JSONObject json_object = new JSONObject();
            json_object.put(UserSystemConfig.GetReachListConfig.JSON_REQUEST_BEING_TIME, mBeginTime);
            json_object.put(UserSystemConfig.GetReachListConfig.JSON_REQUEST_END_TIME, mEndTime);
            json_object.put(UserSystemConfig.GetReachListConfig.JSON_REQUEST_TYPE, mType);
            json_object.put(UserSystemConfig.GetReachListConfig.JSON_REQUEST_LONGITUDE, mLongitude);
            json_object.put(UserSystemConfig.GetReachListConfig.JSON_REQUEST_LATITUDE, mLatitude);
            json_object.put(UserSystemConfig.GetReachListConfig.JSON_REQUEST_PROVINCE, mProvince);
            json_object.put(UserSystemConfig.GetReachListConfig.JSON_REQUEST_CITY, mCity);
            json_object.put(UserSystemConfig.GetReachListConfig.JSON_REQUEST_DISTRICT, mDistrict);
            json_object.put(UserSystemConfig.GetReachListConfig.JSON_REQUEST_LIMITED, mLimited);
            json_object.put(UserSystemConfig.GetReachListConfig.JSON_REQUEST_PAGE_NO, mPageNo);
            json_object.put(UserSystemConfig.GetReachListConfig.JSON_REQUEST_PAGE_SIZE, mPageSize);
            json_object.put(UserSystemConfig.GetReachListConfig.JSON_REQUEST_SORT, mSort);
            String result_json = json_object.toString();
            return  result_json;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String toString() {
        return "ReachListInfo{" +
                "mBeginTime='" + mBeginTime + '\'' +
                ", mEndTime='" + mEndTime + '\'' +
                ", mType='" + mType + '\'' +
                ", mLongitude='" + mLongitude + '\'' +
                ", mLatitude='" + mLatitude + '\'' +
                ", mProvince='" + mProvince + '\'' +
                ", mCity='" + mCity + '\'' +
                ", mDistrict='" + mDistrict + '\'' +
                ", mLimited='" + mLimited + '\'' +
                ", mPageNo='" + mPageNo + '\'' +
                ", mPageSize='" + mPageSize + '\'' +
                ", mSort='" + mSort + '\'' +
                '}';
    }
}
