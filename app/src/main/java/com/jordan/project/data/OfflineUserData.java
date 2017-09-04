package com.jordan.project.data;

/**
 * Created by icean on 2017/1/20.
 */

public final class OfflineUserData {

    private String mUserName, mUserToken, mUserPassword, mUserPhone, mUserNick, mUserRole, mUserICInfo;
    private long mLastLoginDateTime;

    public OfflineUserData(String user_name, String user_token, String user_password, String user_phone, String user_nick, String user_role,
                           String user_ic_info, long last_login_datetime) {
        mUserName = user_name;
        mUserToken = user_token;
        mUserPassword = user_password;
        mUserPhone = user_phone;
        mUserNick = user_nick;
        mUserRole = user_role;
        mUserICInfo = user_ic_info;
        mLastLoginDateTime = last_login_datetime;
    }

    public String getUserName() {
        return mUserName;
    }

    public String getUserToken() {
        return mUserToken;
    }

    public String getUserPassword() {
        return mUserPassword;
    }

    public String getUserPhone() {
        return mUserPhone;
    }

    public String getUserNick() {
        return mUserNick;
    }

    public String getUserRole() {
        return mUserRole;
    }

    public String getUserICInfo() {
        return mUserICInfo;
    }

    public long getLastLoginDateTime() {
        return mLastLoginDateTime;
    }

    @Override
    public String toString() {
        return "OfflineUserData{" +
                "mUserName='" + mUserName + '\'' +
                ", mUserToken='" + mUserToken + '\'' +
                ", mUserPassword='" + mUserPassword + '\'' +
                ", mUserPhone='" + mUserPhone + '\'' +
                ", mUserNick='" + mUserNick + '\'' +
                ", mUserRole='" + mUserRole + '\'' +
                ", mUserICInfo='" + mUserICInfo + '\'' +
                ", mLastLoginDateTime=" + mLastLoginDateTime +
                '}';
    }
}
