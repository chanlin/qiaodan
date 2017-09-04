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

public final class ShoesListTask extends BaseTask {
    private String mPageNo;
    private String mPageSize;
    private String mUrl;


    public ShoesListTask(Context ctx, String remote_address, Handler main_handler,
                         String pageNo,String pageSize, String user_token,
                         boolean is_granted) {
        super(ctx, remote_address, user_token, main_handler, is_granted);
        mPageNo=pageNo;
        mPageSize=pageSize;
        mUrl = mRemoteServerAddress + UserSystemConfig.URI_SHOES_LIST;
    }

    @Override
    public String doTask() {
        String main_json_str = UserSystemUtils.createShoesListMainRequest(mPageNo,mPageSize);
        RequestMessage.Request request_proto = CommonUtils.createRequest(mContext, main_json_str, mUserToken, mIsGranted);
        String result_json = HttpUtils.sendHttpRequest(mUrl, request_proto);
        return result_json;
    }

    @Override
    public void onSuccess(String result_json) {
        if (null != mMainHandler) {
            Message success_message = mMainHandler.obtainMessage(InnerMessageConfig.USER_SHOES_LIST_MESSAGE_SUCCESS);
            success_message.obj = result_json;
            success_message.sendToTarget();
        }
    }

    @Override
    public void onFalse(String false_json) {
        if (null != mMainHandler) {
            Message success_message = mMainHandler.obtainMessage(InnerMessageConfig.USER_SHOES_LIST_MESSAGE_FALSE);
            success_message.obj = false_json;
            success_message.sendToTarget();
        }
    }

    @Override
    public void onException() {
        if (null != mMainHandler) {
            Message success_message = mMainHandler.obtainMessage(InnerMessageConfig.USER_SHOES_LIST_MESSAGE_EXCEPTION);
            success_message.sendToTarget();
        }
    }
}
