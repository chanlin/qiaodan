package com.jordan.usersystemlibrary.data;

import com.safari.httplibs.utils.data.JsonInfo;
import com.jordan.usersystemlibrary.config.UserSystemConfig;

import org.json.JSONObject;

/**
 * Created by icean on 2017/2/2.
 */

public final class TrainUploadInfo extends JsonInfo {
    private String mSource;
    private String mType;
    private String mPlatform;
    private String mSay;
    private String mImg;
    private String mUrls;

    public TrainUploadInfo(String source,String type,String platform,String say,
                           String img,String urls) {
        mSource = source;
        mType = type;
        mPlatform = platform;
        mSay = say;
        mImg = img;
        mUrls = urls;
    }


    @Override
    public String toJsonStr() {
        try {
            JSONObject json_object = new JSONObject();
            json_object.put(UserSystemConfig.GetTrainUploadConfig.JSON_REQUEST_ID, "");
            json_object.put(UserSystemConfig.GetTrainUploadConfig.JSON_REQUEST_SOURCE, mSource);
            json_object.put(UserSystemConfig.GetTrainUploadConfig.JSON_REQUEST_TYPE, mType);
            json_object.put(UserSystemConfig.GetTrainUploadConfig.JSON_REQUEST_PLATFORM, mPlatform);
            json_object.put(UserSystemConfig.GetTrainUploadConfig.JSON_REQUEST_SAY, mSay);
            json_object.put(UserSystemConfig.GetTrainUploadConfig.JSON_REQUEST_IMG, mImg);
            json_object.put(UserSystemConfig.GetTrainUploadConfig.JSON_REQUEST_URL, mUrls);
            String result_json = json_object.toString();
            return  result_json;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String toString() {
        return "TrainDetailInfo{" +
                "mSource='" + mSource + '\'' +
                '}';
    }
}
