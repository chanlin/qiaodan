package com.jordan.project.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.safari.httplibs.config.InnerMessageConfig;
import com.jordan.project.JordanApplication;
import com.jordan.project.R;
import com.jordan.project.activities.main.MainActivity;
import com.jordan.project.config.ActivityActionConfig;
import com.jordan.project.utils.ActivityUtils;
import com.jordan.project.utils.JsonFalseUtils;
import com.jordan.project.utils.JsonSuccessUtils;
import com.jordan.project.utils.LogUtils;
import com.jordan.project.utils.SettingSharedPerferencesUtil;
import com.jordan.project.utils.ToastUtils;
import com.jordan.project.utils.TypeUtils;
import com.jordan.project.widget.LoadingProgressDialog;
import com.jordan.usersystemlibrary.UserManager;

import org.json.JSONException;

public class LoginActivity extends Activity implements View.OnClickListener {


    private EditText mETPhone, mETPassword;

    private String mCurrentPhone, mCurrentPassword;
    private RelativeLayout mRlRemarkPassword;
    private ImageView mIVRemarkPassword;
    private boolean mIsRemark=false;
    private String remarkPassword;

    private Handler mLoginHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case InnerMessageConfig.USER_LOGIN_MESSAGE_SUCCESS:
                    //解析正确参数-内容在msg.obj
                    //保存登录返回数据
                    userManager.setUserToken(userManager.getOfflineUserToken());
                    //缓存用户登录用户名和密码
                    SettingSharedPerferencesUtil.SetLoginUsernameValue(LoginActivity.this, mCurrentPhone);
                    SettingSharedPerferencesUtil.SetEditLoginUsernameValue(LoginActivity.this, mCurrentPhone);
                    SettingSharedPerferencesUtil.SetPasswordUsernameValue(LoginActivity.this, mCurrentPassword);
                    if(mIsRemark)
                    SettingSharedPerferencesUtil.SetRemarkPasswordValue(LoginActivity.this, mCurrentPassword);
                    //登录
                    JordanApplication.setUsername(mCurrentPhone);
                    try {
                        LogUtils.showLog("vipId", "USER_LOGIN_MESSAGE_SUCCESS result:" + (String) msg.obj);
                        JordanApplication.setVipID(JsonSuccessUtils.getVipId((String) msg.obj));
                        LogUtils.showLog("vipId", "USER_LOGIN_MESSAGE_SUCCESS vipId:" + JordanApplication.getVipID(LoginActivity.this));
                        if (!JordanApplication.getVipID(LoginActivity.this).equals("") && JordanApplication.getVipID(LoginActivity.this) != null) {
                            SettingSharedPerferencesUtil.SetVipIdValue(LoginActivity.this, JordanApplication.getVipID(LoginActivity.this));
                        } else {
                            JordanApplication.setVipID(SettingSharedPerferencesUtil.GetVipIdValueConfig(LoginActivity.this));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    LoadingProgressDialog.Dissmiss();
                    finish();
                    break;
                case InnerMessageConfig.USER_LOGIN_MESSAGE_EXCEPTION:
                    LoadingProgressDialog.Dissmiss();
                    ToastUtils.shortToast(LoginActivity.this, getResources().getString(R.string.http_exception));
                    break;
                case InnerMessageConfig.USER_LOGIN_MESSAGE_FALSE:
                    LoadingProgressDialog.Dissmiss();
                    try {
                        String errorMsg = JsonFalseUtils.onlyErrorCodeResult((String) msg.obj);
                        if (errorMsg != null)
                            ToastUtils.shortToast(LoginActivity.this, errorMsg);
                    } catch (JSONException e) {
                        mLoginHandler.sendEmptyMessage(InnerMessageConfig.USER_LOGIN_MESSAGE_EXCEPTION);
                        e.printStackTrace();
                    }
                    break;
            }
        }
    };

    private UserManager userManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userManager = new UserManager(LoginActivity.this, mLoginHandler);

        mCurrentPhone = ActivityUtils.getInputStrInfo(getIntent(), ActivityActionConfig.KEY_LOGIN_USER_NAME);
        mCurrentPassword = ActivityUtils.getInputStrInfo(getIntent(), ActivityActionConfig.KEY_LOGIN_USER_PASSWORD);

        mETPhone = (EditText) findViewById(R.id.login_account_et);
        if (!TextUtils.isEmpty(mCurrentPhone))
            mETPhone.setText(mCurrentPhone);
        String editUserName = SettingSharedPerferencesUtil.GetEditLoginUsernameValueConfig(LoginActivity.this);
        if (!TextUtils.isEmpty(editUserName))
            mETPhone.setText(editUserName);

        mETPassword = (EditText) findViewById(R.id.login_password_et);
        if (!TextUtils.isEmpty(mCurrentPassword))
            mETPassword.setText(mCurrentPassword);
        mETPhone.setOnFocusChangeListener(new android.view.View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    // 此处为得到焦点时的处理内容
                    mETPhone.setHint("");
                } else {
                    // 此处为失去焦点时的处理内容
                    mETPhone.setHint(R.string.common_account);
                }
            }
        });
        mETPassword.setOnFocusChangeListener(new android.view.View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    // 此处为得到焦点时的处理内容
                    mETPassword.setHint("");
                } else {
                    // 此处为失去焦点时的处理内容
                    mETPassword.setHint(R.string.common_password);
                }
            }
        });
        mIVRemarkPassword = (ImageView) findViewById(R.id.login_remark_password_iv);
        remarkPassword=SettingSharedPerferencesUtil.GetRemarkPasswordValueConfig(LoginActivity.this);
        //读取缓存赋值boolean类型
        //根据boolean类型是否隐藏还是显示
        mRlRemarkPassword = (RelativeLayout) findViewById(R.id.login_remark_password_rl);
        mRlRemarkPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //将boolean类型变更
                if(mIsRemark){
                    mIsRemark=false;
                    mIVRemarkPassword.setVisibility(View.GONE);
                    //如果是false清空数据
                    SettingSharedPerferencesUtil.SetRemarkPasswordValue(LoginActivity.this,"");
                    LogUtils.showLog("remarkPassword","清空remarkPassword");
                }else{
                    mIsRemark=true;
                    mIVRemarkPassword.setVisibility(View.VISIBLE);
                }
            }
        });
        LogUtils.showLog("remarkPassword","remarkPassword:"+remarkPassword);
        if(remarkPassword.equals("")){
            mIsRemark=false;
            mIVRemarkPassword.setVisibility(View.GONE);
        }else{
            mETPassword.setText(remarkPassword);
            mIsRemark=true;
            mIVRemarkPassword.setVisibility(View.VISIBLE);
        }
        Button login_btn = (Button) findViewById(R.id.login_btn);
        login_btn.setOnClickListener(this);
        Button register_btn = (Button) findViewById(R.id.login_register_btn);
        register_btn.setOnClickListener(this);
        Button forget_btn = (Button) findViewById(R.id.login_forget_btn);
        forget_btn.setOnClickListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        LogUtils.showLog("Result", "onActivityResult requestCode:" + requestCode);
        LogUtils.showLog("Result", "onActivityResult resultCode:" + resultCode);
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case ActivityActionConfig.REQUEST_CODE_FORGET_PASSWORD:

                break;
            case ActivityActionConfig.REQUEST_CODE_REGISTER:
                handleRegisterResult(data, requestCode);
                break;
            default:
                throw new RuntimeException("nonsupport request code");

        }
    }

    @Override
    public void onClick(View v) {
        int view_id = v.getId();
        switch (view_id) {
            case R.id.login_btn:
                doLogin();
                break;
            case R.id.login_forget_btn:
                doForgetPassword();
                break;
            case R.id.login_register_btn:
                doRegister();
                break;
            default:
                throw new RuntimeException("nonsupport operation");
        }
    }

    private void doLogin() {
        String current_phone = mETPhone.getText().toString();
        String current_password = mETPassword.getText().toString();
        if (TextUtils.isEmpty(current_phone) || TextUtils.isEmpty(current_password)) {
            Toast.makeText(this, R.string.info_wrong_input, Toast.LENGTH_LONG).show();
            return;
        }else if(current_password.length()<6){
            Toast.makeText(this, R.string.info_length_password, Toast.LENGTH_LONG).show();
            return;
        } else {
            LoadingProgressDialog.show(LoginActivity.this, false, true, 30000);
            mCurrentPhone = current_phone;
            mCurrentPassword = current_password;
            //begin to login
            userManager.login(current_phone, current_password, TypeUtils.LOGIN_TYPE_PASSWORD, "");
        }
    }

    private void doForgetPassword() {
        if (!ActivityUtils.startForgetPasswordActivitySafe(this, mCurrentPhone)) {
            Toast.makeText(this, R.string.info_start_activity_false, Toast.LENGTH_SHORT).show();
        }
    }

    private void doRegister() {
        if (!ActivityUtils.startRegisterActivitySafe(this)) {
            Toast.makeText(this, R.string.info_start_activity_false, Toast.LENGTH_SHORT).show();
        }
    }

    private void handleRegisterResult(Intent result_intent, int result_code) {
        LogUtils.showLog("Result", "handleRegisterResult result_code:" + result_code);
        switch (result_code) {
            case ActivityActionConfig.RESULT_CODE_REGISTER_SUCCESS:
                if (JordanApplication.isRegister) {
                    JordanApplication.isRegister = false;
                    LogUtils.showLog("Result", "handleRegisterResult RESULT_CODE_REGISTER_SUCCESS");
                    //mCurrentPhone = result_intent.getStringExtra(ActivityActionConfig.KEY_LOGIN_USER_NAME);
                    //mCurrentPassword = result_intent.getStringExtra(ActivityActionConfig.KEY_LOGIN_USER_PASSWORD);
                    mCurrentPhone = SettingSharedPerferencesUtil.GetLoginUsernameValueConfig(LoginActivity.this);
                    mCurrentPassword = SettingSharedPerferencesUtil.GetPasswordUsernameValueConfig(LoginActivity.this);
                    LogUtils.showLog("Result", "handleRegisterResult mCurrentPhone:" + mCurrentPhone);
                    LogUtils.showLog("Result", "handleRegisterResult mCurrentPassword:" + mCurrentPassword);
                    updateUI();
                    if (getResources().getBoolean(R.bool.config_auto_login_after_register)) {
                        mETPhone.setText(mCurrentPhone);
                        mETPassword.setText(mCurrentPassword);
                        doLogin();
                    }
                }
                break;
            case ActivityActionConfig.RESULT_CODE_REGISTER_FALSE:
            case ActivityActionConfig.RESULT_CODE_REGISTER_CANCEL:
                break;
            default:
                throw new RuntimeException("nonsupport result code");
        }
    }

    private void updateUI() {
        if (!TextUtils.isEmpty(mCurrentPhone)) {
            mETPhone.setText(mCurrentPhone);
        } else {
            mETPhone.setText("");
        }

        if (!TextUtils.isEmpty(mCurrentPassword)) {
            mETPassword.setText(mCurrentPassword);
        } else {
            mETPassword.setText("");
        }
    }
}
