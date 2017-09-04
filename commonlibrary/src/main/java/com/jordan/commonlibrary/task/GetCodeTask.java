package com.jordan.commonlibrary.task;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.jordan.commonlibrary.config.CommonSystemConfig;
import com.jordan.commonlibrary.utils.CommonSystemUtils;
import com.safari.core.protocol.RequestMessage;
import com.safari.httplibs.BaseTask;
import com.safari.httplibs.HttpUtils;
import com.safari.httplibs.config.InnerMessageConfig;
import com.safari.httplibs.utils.CommonUtils;

/**
 * Created by icean on 2017/2/1.
 */

public final class GetCodeTask extends BaseTask {
    private String mAccount, mType, mGenre;
    private String mGetCodeUri;

    public GetCodeTask(Context ctx, String remote_address, Handler main_handler,
                           String user_account, String file_type, String genre, String user_token,
                           boolean is_granted) {
        super(ctx, remote_address, user_token, main_handler, is_granted);
        mType = file_type;
        mAccount = user_account;
        mType = file_type;
        mGenre = genre;
        mGetCodeUri = mRemoteServerAddress + CommonSystemConfig.URI_SEND_CODE;
    }
    @Override
    public String doTask() {
        String main_json_str = CommonSystemUtils.createGetCodeMainRequest(mAccount, mType, mGenre);
        RequestMessage.Request request_proto = CommonUtils.createRequest(mContext, main_json_str, mUserToken, mIsGranted);
        String result_json = HttpUtils.sendHttpRequest(mGetCodeUri, request_proto);
        return result_json;
    }

    @Override
    public void onSuccess(String result_json) {
        if (null != mMainHandler) {
            Message success_message = mMainHandler.obtainMessage(InnerMessageConfig.COMMON_GET_CODE_MESSAGE_SUCCESS);
            success_message.obj = result_json;
            success_message.sendToTarget();
        }
    }

    @Override
    public void onFalse(String false_json) {
        if (null != mMainHandler) {
            Message success_message = mMainHandler.obtainMessage(InnerMessageConfig.COMMON_GET_CODE_MESSAGE_FALSE);
            success_message.obj = false_json;
            success_message.sendToTarget();
        }
    }

    @Override
    public void onException() {
        if (null != mMainHandler) {
            Message success_message = mMainHandler.obtainMessage(InnerMessageConfig.COMMON_GET_CODE_MESSAGE_EXCEPTION);
            success_message.sendToTarget();
        }
    }
}
