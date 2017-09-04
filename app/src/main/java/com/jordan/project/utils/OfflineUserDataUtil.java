package com.jordan.project.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.SystemClock;
import android.text.TextUtils;

import com.jordan.project.config.OfflineUserConfig;
import com.jordan.project.data.OfflineUserData;

import org.w3c.dom.Text;

/**
 * Created by icean on 2017/1/20.
 */

public final class OfflineUserDataUtil {

    public static final OfflineUserData getCurrentUserData(Context ctx) {
        SharedPreferences offline_user_data_file = ctx.getSharedPreferences(OfflineUserConfig.OFFLINE_USER_DATA_FILE, Context.MODE_PRIVATE);
        String current_user_token = offline_user_data_file.getString(OfflineUserConfig.KEY_USER_TOKEN, "");
        if (TextUtils.isEmpty(current_user_token)) {
            return null;
        } else {
            String current_user_name = offline_user_data_file.getString(OfflineUserConfig.KEY_USER_NAME, "");
            String current_user_password = offline_user_data_file.getString(OfflineUserConfig.KEY_USER_PASSWORD, "");
            String current_user_phone = offline_user_data_file.getString(OfflineUserConfig.KEY_USER_PHONE, "");
            String current_user_nick = offline_user_data_file.getString(OfflineUserConfig.KEY_USER_NICK, "");
            String current_user_role = offline_user_data_file.getString(OfflineUserConfig.KEY_USER_ROLE, "");
            String current_user_ic_info = offline_user_data_file.getString(OfflineUserConfig.KEY_USER_IC_INFO, "");
            long  current_last_login_datetime = offline_user_data_file.getLong(OfflineUserConfig.KEY_LAST_LOGIN_TIME, -1L);
            OfflineUserData current_user_data =  new OfflineUserData(current_user_name, current_user_token, current_user_password, current_user_phone,
                    current_user_nick, current_user_role, current_user_ic_info, current_last_login_datetime);
            return current_user_data;
        }
    }

    public static final void saveUserData(Context ctx, String user_nick, String user_role, String user_ic_info) {
        SharedPreferences.Editor file_editor = ctx.getSharedPreferences(OfflineUserConfig.OFFLINE_USER_DATA_FILE, Context.MODE_PRIVATE).edit();
        if (! TextUtils.isEmpty(user_nick))
            file_editor.putString(OfflineUserConfig.KEY_USER_NICK, user_nick);

        if (! TextUtils.isEmpty(user_role))
            file_editor.putString(OfflineUserConfig.KEY_USER_ROLE, user_role);

        if (! TextUtils.isEmpty(user_ic_info))
            file_editor.putString(OfflineUserConfig.KEY_USER_IC_INFO, user_ic_info);

        file_editor.putLong(OfflineUserConfig.KEY_LAST_LOGIN_TIME, System.currentTimeMillis());
        file_editor.commit();
    }
}
