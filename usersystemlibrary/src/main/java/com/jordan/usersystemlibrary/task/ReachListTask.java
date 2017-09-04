package com.jordan.usersystemlibrary.task;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

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

public final class ReachListTask extends BaseTask {
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
    private String mUrl;

    public ReachListTask(Context ctx, String remote_address, Handler main_handler,
                         String beginTime,String endTime,String type,String longitude,
                         String latitude,String province,String city,String district,
                         String limited,String pageNo,String pageSize,String sort, String user_token,
                         boolean is_granted) {
        super(ctx, remote_address, user_token, main_handler, is_granted);
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
        mUrl = mRemoteServerAddress + UserSystemConfig.URI_REACH_LIST;
    }

    @Override
    public String doTask() {
        String main_json_str = UserSystemUtils.createReachListMainRequest(mBeginTime,mEndTime,mType,
                mLongitude,mLatitude,mProvince,mCity,mDistrict,mLimited,mPageNo,mPageSize,mSort);
        Log.i("Result", "main_json_str:" + main_json_str);
        RequestMessage.Request request_proto = CommonUtils.createRequest(mContext, main_json_str, mUserToken, mIsGranted);
        String result_json = HttpUtils.sendHttpRequest(mUrl, request_proto);
        Log.i("Result", "result_json:" + result_json);
        return result_json;
    }

    @Override
    public void onSuccess(String result_json) {
        if (null != mMainHandler) {
            Message success_message = mMainHandler.obtainMessage(InnerMessageConfig.USER_REACH_LIST_MESSAGE_SUCCESS);
            success_message.obj = result_json;
            success_message.sendToTarget();
        }
    }

    @Override
    public void onFalse(String false_json) {
        if (null != mMainHandler) {
            Message success_message = mMainHandler.obtainMessage(InnerMessageConfig.USER_REACH_LIST_MESSAGE_FALSE);
            success_message.obj = false_json;
            success_message.sendToTarget();
        }
    }

    @Override
    public void onException() {
        if (null != mMainHandler) {
            Message success_message = mMainHandler.obtainMessage(InnerMessageConfig.USER_REACH_LIST_MESSAGE_EXCEPTION);
            success_message.sendToTarget();
        }
    }
}
