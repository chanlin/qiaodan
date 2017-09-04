package com.jordan.usersystemlibrary.data;

import com.safari.httplibs.utils.data.JsonInfo;
import com.jordan.usersystemlibrary.config.UserSystemConfig;

import org.json.JSONObject;

/**
 * Created by icean on 2017/2/2.
 */

public final class CreateReachInfo extends JsonInfo {
    private String mTime;
    private String mDuration;
    private String mPeople;
    private String mType;
    private String mPicture;
    private String mLongitude;
    private String mLatitude;
    private String mProvince;
    private String mCity;
    private String mDistrict;
    private String mStreet;
    private String mAddress;
    private String mSlogan;
    private String mRemarks;

    public CreateReachInfo(String time,String duration,String people,String type,
                           String picture,String longitude,String latitude,String province,
                           String city,String district,String street,String address,
                           String slogan,String remarks) {
        mTime = time;
        mDuration = duration;
        mPeople = people;
        mType = type;
        mPicture = picture;
        mLongitude = longitude;
        mLatitude = latitude;
        mProvince = province;
        mCity = city;
        mDistrict = district;
        mStreet = street;
        mAddress = address;
        mSlogan = slogan;
        mRemarks = remarks;
    }

    public String getmTime() {
        return mTime;
    }

    public String getmDuration() {
        return mDuration;
    }

    public String getmPeople() {
        return mPeople;
    }

    public String getmType() {
        return mType;
    }

    public String getmPicture() {
        return mPicture;
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

    public String getmStreet() {
        return mStreet;
    }

    public String getmAddress() {
        return mAddress;
    }

    public String getmRemarks() {
        return mRemarks;
    }

    public String getmSlogan() {
        return mSlogan;
    }

    @Override
    public String toJsonStr() {
        try {
            JSONObject json_object = new JSONObject();
            json_object.put(UserSystemConfig.GetCreateReachConfig.JSON_REQUEST_TIME, mTime);
            json_object.put(UserSystemConfig.GetCreateReachConfig.JSON_REQUEST_DURATION, mDuration);
            json_object.put(UserSystemConfig.GetCreateReachConfig.JSON_REQUEST_PEOPLE, mPeople);
            json_object.put(UserSystemConfig.GetCreateReachConfig.JSON_REQUEST_TYPE, mType);
            json_object.put(UserSystemConfig.GetCreateReachConfig.JSON_REQUEST_PICTURE, mPicture);
            json_object.put(UserSystemConfig.GetCreateReachConfig.JSON_REQUEST_LONGITUDE, mLongitude);
            json_object.put(UserSystemConfig.GetCreateReachConfig.JSON_REQUEST_LATITUDE, mLatitude);
            json_object.put(UserSystemConfig.GetCreateReachConfig.JSON_REQUEST_PROVINCE, mProvince);
            json_object.put(UserSystemConfig.GetCreateReachConfig.JSON_REQUEST_CITY, mCity);
            json_object.put(UserSystemConfig.GetCreateReachConfig.JSON_REQUEST_DISTRICT, mDistrict);
            json_object.put(UserSystemConfig.GetCreateReachConfig.JSON_REQUEST_STREET, mStreet);
            json_object.put(UserSystemConfig.GetCreateReachConfig.JSON_REQUEST_ADDRESS, mAddress);
            json_object.put(UserSystemConfig.GetCreateReachConfig.JSON_REQUEST_SLOGAN, mSlogan);
            json_object.put(UserSystemConfig.GetCreateReachConfig.JSON_REQUEST_REMARKS, mRemarks);
            String result_json = json_object.toString();
            return  result_json;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String toString() {
        return "CreateReachInfo{" +
                "mTime='" + mTime + '\'' +
                ", mDuration='" + mDuration + '\'' +
                ", mPeople='" + mPeople + '\'' +
                ", mType='" + mType + '\'' +
                ", mPicture='" + mPicture + '\'' +
                ", mLongitude='" + mLongitude + '\'' +
                ", mLatitude='" + mLatitude + '\'' +
                ", mProvince='" + mProvince + '\'' +
                ", mCity='" + mCity + '\'' +
                ", mDistrict='" + mDistrict + '\'' +
                ", mStreet='" + mStreet + '\'' +
                ", mAddress='" + mAddress + '\'' +
                ", mSlogan='" + mSlogan + '\'' +
                ", mRemarks='" + mRemarks + '\'' +
                '}';
    }
}
