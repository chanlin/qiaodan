package com.jordan.project.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.view.Window;

import com.safari.httplibs.config.InnerMessageConfig;
import com.jordan.project.JordanApplication;
import com.jordan.project.R;
import com.jordan.project.activities.main.FristStartActivity;
import com.jordan.project.activities.main.MainActivity;
import com.jordan.project.utils.JsonFalseUtils;
import com.jordan.project.utils.JsonSuccessUtils;
import com.jordan.project.utils.LogUtils;
import com.jordan.project.utils.PermissionUtil;
import com.jordan.project.utils.SettingSharedPerferencesUtil;
import com.jordan.project.utils.ToastUtils;
import com.jordan.project.utils.TypeUtils;
import com.jordan.usersystemlibrary.UserManager;

import org.json.JSONException;

public class NavigationActivity extends Activity implements PermissionUtil.PermissionCallBack {
    String mUsername, mPassword;

    private Handler mLoginHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case InnerMessageConfig.USER_LOGIN_MESSAGE_SUCCESS:
                    //解析正确参数-内容在msg.obj
                    //保存登录返回数据
                    userManager.setUserToken(userManager.getOfflineUserToken());
                    //登录
                    JordanApplication.setUsername(mUsername);
                    try {
                        LogUtils.showLog("vipId", "USER_LOGIN_MESSAGE_SUCCESS result:"+(String)msg.obj);
                        JordanApplication.setVipID(JsonSuccessUtils.getVipId((String) msg.obj));
                        LogUtils.showLog("vipId", "USER_LOGIN_MESSAGE_SUCCESS vipId:"+JordanApplication.getVipID(NavigationActivity.this));
                        if(!JordanApplication.getVipID(NavigationActivity.this).equals("")&&JordanApplication.getVipID(NavigationActivity.this)!=null){
                            SettingSharedPerferencesUtil.SetVipIdValue(NavigationActivity.this,JordanApplication.getVipID(NavigationActivity.this));
                        }else{
                            JordanApplication.setVipID(SettingSharedPerferencesUtil.GetVipIdValueConfig(NavigationActivity.this));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    Intent intent = new Intent(NavigationActivity.this, MainActivity.class);
                    startActivity(intent);

                    finish();
                    break;
                case InnerMessageConfig.USER_LOGIN_MESSAGE_EXCEPTION:
                    ToastUtils.shortToast(NavigationActivity.this, getResources().getString(R.string.http_exception));
                    intent = new Intent(NavigationActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                case InnerMessageConfig.USER_LOGIN_MESSAGE_FALSE:
                    try {
                        String errorMsg = JsonFalseUtils.onlyErrorCodeResult((String) msg.obj);
                        if (errorMsg != null)
                            ToastUtils.shortToast(NavigationActivity.this, errorMsg);
                        intent = new Intent(NavigationActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    } catch (JSONException e) {
                        mLoginHandler.sendEmptyMessage(InnerMessageConfig.USER_LOGIN_MESSAGE_EXCEPTION);
                        e.printStackTrace();
                    }
                    break;
            }
            ;
        }
    };

    private UserManager userManager;
    protected PermissionUtil mPermissionUtil;
    private static final int PERMISSION_CODE=100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_navigation);
        mUsername = SettingSharedPerferencesUtil.GetLoginUsernameValueConfig(NavigationActivity.this);
        mPassword = SettingSharedPerferencesUtil.GetPasswordUsernameValueConfig(NavigationActivity.this);
        mPermissionUtil = PermissionUtil.getInstance();
        if(!mPermissionUtil.requestPermissions(NavigationActivity.this,PERMISSION_CODE)){
            login();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        LogUtils.showLog("motioning", "onRequestPermissionsResult");
        mPermissionUtil.requestResult(NavigationActivity.this,requestCode,permissions,grantResults,NavigationActivity.this);

    }
    @Override
    public void onPermissionGetSuccess() {
        login();
    }
    private void login(){
        if(SettingSharedPerferencesUtil.GetFristStartValueConfig(NavigationActivity.this).equals("")){
            //开启引导页流程
            SettingSharedPerferencesUtil.SetFristStartValue(NavigationActivity.this,"true");
            Intent intent = new Intent(NavigationActivity.this, FristStartActivity.class);
            startActivity(intent);
            finish();
        }else {
            if (mUsername.equals("") || mPassword.equals("")) {
                Intent intent = new Intent(NavigationActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            } else {
                userManager = new UserManager(NavigationActivity.this, mLoginHandler);
                userManager.login(mUsername, mPassword, TypeUtils.LOGIN_TYPE_PASSWORD, "");
            }
        }
    }
    @Override
    public void onPermissionGetFail() {
        //mPermissionUtil.requestPermissions(NavigationActivity.this,PERMISSION_CODE);
        finish();
    }
}
