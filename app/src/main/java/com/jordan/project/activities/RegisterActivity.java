package com.jordan.project.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jordan.commonlibrary.CommonManager;
import com.safari.httplibs.config.InnerMessageConfig;
import com.safari.httplibs.utils.CommonUtils;
import com.jordan.project.JordanApplication;
import com.jordan.project.R;
import com.jordan.project.utils.JsonFalseUtils;
import com.jordan.project.utils.LogUtils;
import com.jordan.project.utils.ToastUtils;
import com.jordan.project.utils.TypeUtils;
import com.jordan.project.widget.LoadingProgressDialog;
import com.jordan.usersystemlibrary.UserManager;

import org.json.JSONException;

import java.util.Timer;
import java.util.TimerTask;

public class RegisterActivity extends Activity implements View.OnClickListener {

    private EditText mETPhoneNumber, mETAuthCode, mETPassword, mETRePassword;
    String current_phone;
    String current_password;
    String current_code;

    private Handler mRegisterHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case InnerMessageConfig.USER_REGISTER_MESSAGE_SUCCESS:
                    LoadingProgressDialog.Dissmiss();
                    //解析正确参数-内容在msg.obj
                    //成功跳到完善资料界面
                    userManager.setUserToken(userManager.getOfflineUserToken());
                    JordanApplication.setUsername(current_phone);
                    Intent intent = new Intent(RegisterActivity.this, RegisterDataActivity.class);
                    intent.putExtra("come",RegisterDataActivity.COME_FROM_REGISTER);
                    intent.putExtra("phone", current_phone);
                    intent.putExtra("password", current_password);
                    startActivity(intent);
                    finish();
                    break;
                case InnerMessageConfig.USER_REGISTER_MESSAGE_EXCEPTION:
                    LoadingProgressDialog.Dissmiss();
                    ToastUtils.shortToast(RegisterActivity.this, getResources().getString(R.string.http_exception));
                    break;
                case InnerMessageConfig.USER_REGISTER_MESSAGE_FALSE:
                    LoadingProgressDialog.Dissmiss();
                    try {
                        String errorMsg = JsonFalseUtils.onlyErrorCodeResult((String) msg.obj);
                        if(errorMsg!=null)
                        ToastUtils.shortToast(RegisterActivity.this, errorMsg);
                    } catch (JSONException e) {
                        mRegisterHandler.sendEmptyMessage(InnerMessageConfig.USER_REGISTER_MESSAGE_EXCEPTION);
                        e.printStackTrace();
                    }

                    break;
                case InnerMessageConfig.COMMON_GET_CODE_MESSAGE_SUCCESS:
                    LogUtils.showLog("getcode", "do Get Code COMMON_GET_CODE_MESSAGE_SUCCESS");
                    //解析正确参数-内容在msg.obj
                    ToastUtils.shortToast(RegisterActivity.this, "获取验证码成功");
                    //不提示用户
                    break;
                case InnerMessageConfig.COMMON_GET_CODE_MESSAGE_EXCEPTION:
                    LogUtils.showLog("getcode", "do Get Code COMMON_GET_CODE_MESSAGE_EXCEPTION");
                    ToastUtils.shortToast(RegisterActivity.this, getResources().getString(R.string.http_exception));
                    codeTime=0;
                    break;
                case InnerMessageConfig.COMMON_GET_CODE_MESSAGE_FALSE:
                    LogUtils.showLog("getcode", "do Get Code COMMON_GET_CODE_MESSAGE_FALSE");
                    try {
                        String errorMsg = JsonFalseUtils.onlyErrorCodeResult((String) msg.obj);
                        if(errorMsg!=null)
                        ToastUtils.shortToast(RegisterActivity.this, errorMsg);
                        codeTime=0;
                    } catch (JSONException e) {
                        mRegisterHandler.sendEmptyMessage(InnerMessageConfig.COMMON_GET_CODE_MESSAGE_EXCEPTION);
                        e.printStackTrace();
                    }

                    break;
            }
            ;
        }
    };
    private UserManager userManager;
    private CommonManager commonManager;
    Button get_code_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_register);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.common_back_title);
        userManager = new UserManager(RegisterActivity.this, mRegisterHandler);
        commonManager = new CommonManager(RegisterActivity.this, mRegisterHandler);
        mETPhoneNumber = (EditText) findViewById(R.id.register_phone_number_et);
        mETAuthCode = (EditText) findViewById(R.id.register_author_code_et);
        mETPassword = (EditText) findViewById(R.id.register_password_et);
        mETRePassword = (EditText) findViewById(R.id.register_re_password_et);

        get_code_btn = (Button) findViewById(R.id.register_get_code_btn);
        get_code_btn.setOnClickListener(this);
        Button confirm_btn = (Button) findViewById(R.id.register_confirm_btn);
        confirm_btn.setOnClickListener(this);
//        Button cancel_btn = (Button) findViewById(R.id.register_cancel_btn);
//        cancel_btn.setOnClickListener(this);

        TextView mTvTitle = (TextView) findViewById(R.id.title_text);
        mTvTitle.setText(getResources().getString(R.string.common_register));
        RelativeLayout mRLBack=(RelativeLayout)findViewById(R.id.title_back);
        mRLBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register_confirm_btn:
                if (checkRegisterInfo()) {
                    doRegister();
                }
                break;
//            case R.id.register_cancel_btn:
//                setResult(ActivityActionConfig.RESULT_CODE_REGISTER_CANCEL);
//                finish();
//                break;
            case R.id.register_get_code_btn:
                if (checkGetCodeInfo()) {
                    LogUtils.showLog("getcode", "do Get Code start");
                    doGetCode();
                    LogUtils.showLog("getcode", "do Get Code over");
                }
                break;

        }
    }

    private boolean checkRegisterInfo() {
        String current_phone = mETPhoneNumber.getText().toString();
        if (TextUtils.isEmpty(current_phone)) {
            Toast.makeText(this, R.string.info_empty_phone, Toast.LENGTH_LONG).show();
            return false;
        }

        if (!CommonUtils.isCellPhone(current_phone)) {
            Toast.makeText(this, R.string.info_wrong_phone, Toast.LENGTH_LONG).show();
            return false;
        }

        String current_code = mETAuthCode.getText().toString();
        if (TextUtils.isEmpty(current_code)) {
            Toast.makeText(this, R.string.info_empty_code, Toast.LENGTH_LONG).show();
            return false;
        }
        if (current_code.length()<6) {
            Toast.makeText(this, R.string.info_code_short, Toast.LENGTH_LONG).show();
            return false;
        }

        String current_password = mETPassword.getText().toString();
        String current_re_password = mETRePassword.getText().toString();
        if (TextUtils.isEmpty(current_password) || TextUtils.isEmpty(current_re_password)) {
            Toast.makeText(this, R.string.info_empty_password, Toast.LENGTH_LONG).show();
            return false;
        }

        if (current_password.length()<6|| current_re_password.length()<6) {
            Toast.makeText(this, R.string.info_length_password, Toast.LENGTH_LONG).show();
            return false;
        }

        if (!current_password.equals(current_re_password)) {
            Toast.makeText(this, R.string.info_password_not_equals, Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

    Timer timer;
    private int codeTime = 60;
    private MyTimerTask task;
    private boolean isGetVerification=false;
    private boolean checkGetCodeInfo() {
        String current_phone = mETPhoneNumber.getText().toString();
        if (TextUtils.isEmpty(current_phone)) {
            Toast.makeText(this, R.string.info_empty_phone, Toast.LENGTH_LONG).show();
            return false;
        }

        if (!CommonUtils.isCellPhone(current_phone)) {
            Toast.makeText(this, R.string.info_wrong_phone, Toast.LENGTH_LONG).show();
            return false;
        }
        if (!isGetVerification) {
            isGetVerification=true;
            timer = new Timer();
            if (timer != null && task != null) {
                task.cancel();
            }
            codeTime = 60;
            task = new MyTimerTask();
            timer.schedule(task, 1000, 1000);
        } else {
            Toast.makeText(RegisterActivity.this, "请稍后获取", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void doRegister() {
        current_phone = mETPhoneNumber.getText().toString();
        current_password = mETPassword.getText().toString();
        current_code = mETAuthCode.getText().toString();
        LoadingProgressDialog.show(RegisterActivity.this, false, true, 30000);
        //Do register
        userManager.register(current_phone, current_password, TypeUtils.REGISTER_TYPE_PHONE, current_code);
    }

    private void doGetCode() {
        current_phone = mETPhoneNumber.getText().toString();
        if (!TextUtils.isEmpty(current_phone)) {
            //do get code process;
            LogUtils.showLog("getcode", "do Get Code commonManager.getCode start");
            commonManager.getCode(current_phone, TypeUtils.SMS_CODE_TYPE_REGISTER, TypeUtils.SMS_CODE_GENRE_TYPE_PHONE);
            LogUtils.showLog("getcode", "do Get Code commonManager.getCode over");
        }
    }


    class MyTimerTask extends TimerTask {
        @Override
        public void run() {
            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    codeTime--;
                    get_code_btn.setText(codeTime + "s");
                    if (codeTime <= 0) {
                        if (timer != null) {
                            timer.cancel();
                        }
                        get_code_btn.setText("获取验证码");
                        isGetVerification=false;
                    }
                }
            });
        }

    }
}
