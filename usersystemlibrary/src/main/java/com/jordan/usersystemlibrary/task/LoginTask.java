package com.jordan.usersystemlibrary.task;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.jordan.usersystemlibrary.config.UserSystemConfig;
import com.jordan.usersystemlibrary.utils.UserSystemUtils;
import com.safari.core.protocol.RequestMessage;
import com.safari.httplibs.BaseTask;
import com.safari.httplibs.HttpUtils;
import com.safari.httplibs.config.InnerMessageConfig;
import com.safari.httplibs.utils.CommonUtils;

import org.json.JSONObject;

/**
 * Created by icean on 2017/2/2.
 */

public final class LoginTask extends BaseTask {
    private String mAccount, mLoginType, mLoginCode, mPassword;
    private String mUrl;

    public LoginTask(Context ctx, String remote_address, Handler main_handler,
                           String user_account, String login_type, String login_password, String login_code, String user_token,
                           boolean is_granted) {
        super(ctx, remote_address, user_token, main_handler, is_granted);
        mAccount = user_account;
        mLoginType = login_type;
        mPassword = login_password;
        mLoginCode = login_code;
        mUrl = mRemoteServerAddress + UserSystemConfig.URI_LOGIN;
    }

    @Override
    public String doTask() {
        String main_json_str = UserSystemUtils.createLoginMainRequest(mAccount, mLoginType, mPassword, mLoginCode);
        RequestMessage.Request request_proto = CommonUtils.createRequest(mContext, main_json_str, mUserToken, mIsGranted);
        String result_json = HttpUtils.sendHttpRequest(mUrl, request_proto);
        return result_json;
    }

    @Override
    public void onSuccess(String result_json) {
        if (null != mMainHandler) {
            saveUserInfo(result_json);
            Message success_message = mMainHandler.obtainMessage(InnerMessageConfig.USER_LOGIN_MESSAGE_SUCCESS);
            success_message.obj = result_json;
            success_message.sendToTarget();
        }
    }

    @Override
    public void onFalse(String false_json) {
        if (null != mMainHandler) {
            Message success_message = mMainHandler.obtainMessage(InnerMessageConfig.USER_LOGIN_MESSAGE_FALSE);
            success_message.obj = false_json;
            success_message.sendToTarget();
        }
    }

    @Override
    public void onException() {
        if (null != mMainHandler) {
            Message success_message = mMainHandler.obtainMessage(InnerMessageConfig.USER_LOGIN_MESSAGE_EXCEPTION);
            success_message.sendToTarget();
        }
    }

    private void saveUserInfo(String user_data_json) {
        try {
            JSONObject user_json_obj = new JSONObject(user_data_json);
            String user_token = user_json_obj.getString(UserSystemConfig.LoginMessageConfig.JSON_RESPONSE_ROOT_TOKEN);
            String user_info = user_json_obj.getString(UserSystemConfig.LoginMessageConfig.JSON_RESPONSE_ROOT_INFO);
            Log.i("token","saveUserInfo user_token:"+user_token);
            SharedPreferences.Editor user_editor = mContext.getSharedPreferences(UserSystemConfig.OFFLINE_USER_FILE, Context.MODE_PRIVATE).edit();
            user_editor.putString(UserSystemConfig.OFFLINE_USER_TOKEN, user_token);
            user_editor.putString(UserSystemConfig.OFFLINE_USER_INFO, user_info);
            user_editor.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
