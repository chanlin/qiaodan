package com.jordan.usersystemlibrary.task;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.safari.httplibs.BaseTask;
import com.safari.httplibs.HttpUtils;
import com.safari.httplibs.config.InnerMessageConfig;
import com.safari.httplibs.utils.CommonUtils;
import com.jordan.usersystemlibrary.config.UserSystemConfig;
import com.jordan.usersystemlibrary.utils.UserSystemUtils;
import com.safari.core.protocol.RequestMessage;

/**
 * Created by icean on 2017/2/2.
 */

public final class CreateReachTask extends BaseTask {
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
    private String mUrl;


    public CreateReachTask(Context ctx, String remote_address, Handler main_handler,
                           String time,String duration,String people,String type,
                           String picture,String longitude,String latitude,String province,
                           String city,String district,String street,String address,
                           String slogan,String remarks,String user_token,boolean is_granted) {
        super(ctx, remote_address, user_token, main_handler, is_granted);
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
        mUrl = mRemoteServerAddress + UserSystemConfig.URI_CREATE_REACH;
    }

    @Override
    public String doTask() {
        String main_json_str = UserSystemUtils.createCreateReachMainRequest(mTime,mDuration,mPeople,mType,
        mPicture,mLongitude,mLatitude,mProvince,mCity,mDistrict,mStreet,mAddress,mSlogan,mRemarks);
        RequestMessage.Request request_proto = CommonUtils.createRequest(mContext, main_json_str, mUserToken, mIsGranted);
        String result_json = HttpUtils.sendHttpRequest(mUrl, request_proto);
        return result_json;
    }

    @Override
    public void onSuccess(String result_json) {
        if (null != mMainHandler) {
            Message success_message = mMainHandler.obtainMessage(InnerMessageConfig.USER_CREATE_REACH_MESSAGE_SUCCESS);
            success_message.obj = result_json;
            success_message.sendToTarget();
        }
    }

    @Override
    public void onFalse(String false_json) {
        if (null != mMainHandler) {
            Message success_message = mMainHandler.obtainMessage(InnerMessageConfig.USER_CREATE_REACH_MESSAGE_FALSE);
            success_message.obj = false_json;
            success_message.sendToTarget();
        }
    }

    @Override
    public void onException() {
        if (null != mMainHandler) {
            Message success_message = mMainHandler.obtainMessage(InnerMessageConfig.USER_CREATE_REACH_MESSAGE_EXCEPTION);
            success_message.sendToTarget();
        }
    }
}
