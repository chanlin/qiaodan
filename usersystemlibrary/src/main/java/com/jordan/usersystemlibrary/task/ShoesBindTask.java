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

public final class ShoesBindTask extends BaseTask {
    private String mUrl;
    private String mType;
    private String mShoesId;
    private String mCode;
    private String mName;
    private String mPrice;
    private String mColor;
    private String mSize;
    private String mStyle;
    private String mPicture;
    private String mBuyTime;


    public ShoesBindTask(Context ctx, String remote_address, Handler main_handler,
                         String type,String shoesId,String code,String name,
                         String price,String color,String size,String style,
                         String picture,String buyTime, String user_token,
                         boolean is_granted) {
        super(ctx, remote_address, user_token, main_handler, is_granted);
        mType=type;
        mShoesId=shoesId;
        mCode=code;
        mName=name;
        mPrice=price;
        mColor=color;
        mSize=size;
        mStyle=style;
        mPicture=picture;
        mBuyTime=buyTime;
        mUrl = mRemoteServerAddress + UserSystemConfig.URI_SHOES_BIND;
    }

    @Override
    public String doTask() {
        String main_json_str = UserSystemUtils.createShoesBindMainRequest(mType,
        mShoesId,mCode,mName,mPrice,mColor,mSize,mStyle,mPicture,mBuyTime);
        RequestMessage.Request request_proto = CommonUtils.createRequest(mContext, main_json_str, mUserToken, mIsGranted);
        String result_json = HttpUtils.sendHttpRequest(mUrl, request_proto);
        return result_json;
    }

    @Override
    public void onSuccess(String result_json) {
        if (null != mMainHandler) {
            Message success_message = mMainHandler.obtainMessage(InnerMessageConfig.USER_SHOES_BIND_MESSAGE_SUCCESS);
            success_message.obj = result_json;
            success_message.sendToTarget();
        }
    }

    @Override
    public void onFalse(String false_json) {
        if (null != mMainHandler) {
            Message success_message = mMainHandler.obtainMessage(InnerMessageConfig.USER_SHOES_BIND_MESSAGE_FALSE);
            success_message.obj = false_json;
            success_message.sendToTarget();
        }
    }

    @Override
    public void onException() {
        if (null != mMainHandler) {
            Message success_message = mMainHandler.obtainMessage(InnerMessageConfig.USER_SHOES_BIND_MESSAGE_EXCEPTION);
            success_message.sendToTarget();
        }
    }
}
