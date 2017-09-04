package com.jordan.project.config;

/**
 * Created by icean on 2017/1/21.
 */

public final class ActivityActionConfig {
    public static final String ACTION_TO_LOGIN = "com.jordan.project.LOGIN_ACTION";
    public static final String ACTION_TO_REGISTER = "com.jordan.project.REGISTER_ACTION";
    public static final String ACTION_TO_FORGET_PASSWORD = "com.jordan.project.FORGET_PASSWORD_ACTION";

    public static final int REQUEST_CODE_LOGIN = 10000;
    public static final int RESULT_CODE_LOGIN_SUCCESS = 10000;
    public static final int RESULT_CODE_LOGIN_FALSE = -10000;

    public static final int REQUEST_CODE_REGISTER = 20000;
    public static final int RESULT_CODE_REGISTER_SUCCESS = 20000;
    public static final int RESULT_CODE_REGISTER_FALSE = -20000;
    public static final int RESULT_CODE_REGISTER_CANCEL = -20001;


    public static final int REQUEST_CODE_FORGET_PASSWORD = 30000;
    public static final int RESULT_CODE_FORGET_PASSWORD_SUCCESS = 30000;
    public static final int RESULT_CODE_FORGET_PASSWORD_FALSE = -30000;
    public static final int RESULT_CODE_FORGET_PASSWORD_CANCEL = -30001;


    public static final String KEY_LOGIN_USER_NAME = "user_name";
    public static final String KEY_LOGIN_USER_PASSWORD = "user_password";
}
