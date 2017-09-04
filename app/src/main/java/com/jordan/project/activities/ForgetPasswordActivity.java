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
import com.jordan.project.R;
import com.jordan.project.config.ActivityActionConfig;
import com.jordan.project.utils.ActivityUtils;
import com.jordan.project.utils.JsonFalseUtils;
import com.jordan.project.utils.LogUtils;
import com.jordan.project.utils.ToastUtils;
import com.jordan.project.utils.TypeUtils;
import com.jordan.project.widget.LoadingProgressDialog;
import com.jordan.usersystemlibrary.UserManager;

import org.json.JSONException;

import java.util.Timer;
import java.util.TimerTask;

public class ForgetPasswordActivity extends Activity implements View.OnClickListener{

    private EditText mETPhone, mETCode, mETPassword, mETRePassword;
    private String mCurrentPhone;

    private Handler mForgetPasswordHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case InnerMessageConfig.USER_FORGET_PASSWORD_MESSAGE_SUCCESS:
                    //解析正确参数-内容在msg.obj
                    //成功返回登录界面
                    LoadingProgressDialog.Dissmiss();
                    Intent intent = new Intent(ForgetPasswordActivity.this,LoginActivity.class);
                    startActivity(intent);
                    break;
                case InnerMessageConfig.USER_FORGET_PASSWORD_MESSAGE_EXCEPTION:
                    LoadingProgressDialog.Dissmiss();
                    ToastUtils.shortToast(ForgetPasswordActivity.this,getResources().getString(R.string.http_exception));
                    break;
                case InnerMessageConfig.USER_FORGET_PASSWORD_MESSAGE_FALSE:
                    LoadingProgressDialog.Dissmiss();
                    try {
                        String errorMsg= JsonFalseUtils.onlyErrorCodeResult((String)msg.obj);
                        if(errorMsg!=null)
                        ToastUtils.shortToast(ForgetPasswordActivity.this,errorMsg);
                    } catch (JSONException e) {
                        mForgetPasswordHandler.sendEmptyMessage(InnerMessageConfig.COMMON_GET_CODE_MESSAGE_EXCEPTION);
                        e.printStackTrace();
                    }

                    break;
                case InnerMessageConfig.COMMON_GET_CODE_MESSAGE_SUCCESS:
                    LogUtils.showLog("getcode", "COMMON_GET_CODE_MESSAGE_EXCEPTION result:" + (String) msg.obj);
                    //解析正确参数-内容在msg.obj
                    ToastUtils.shortToast(ForgetPasswordActivity.this, "获取验证码成功");
                    //不提示用户
                    break;
                case InnerMessageConfig.COMMON_GET_CODE_MESSAGE_EXCEPTION:
                    LogUtils.showLog("getcode", "COMMON_GET_CODE_MESSAGE_EXCEPTION");
                    ToastUtils.shortToast(ForgetPasswordActivity.this,getResources().getString(R.string.http_exception));
                    break;
                case InnerMessageConfig.COMMON_GET_CODE_MESSAGE_FALSE:
                    LogUtils.showLog("getcode", "COMMON_GET_CODE_MESSAGE_FALSE");
                    try {
                        String errorMsg= JsonFalseUtils.onlyErrorCodeResult((String)msg.obj);
                        if(errorMsg!=null)
                        ToastUtils.shortToast(ForgetPasswordActivity.this,errorMsg);
                    } catch (JSONException e) {
                        mForgetPasswordHandler.sendEmptyMessage(InnerMessageConfig.COMMON_GET_CODE_MESSAGE_EXCEPTION);
                        e.printStackTrace();
                    }

                    break;
            };
        }
    };
    private UserManager userManager;
    private CommonManager commonManager;
    Button get_code_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_forget_password);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.common_back_title);
        userManager=new UserManager(ForgetPasswordActivity.this,mForgetPasswordHandler);
        commonManager=new CommonManager(ForgetPasswordActivity.this,mForgetPasswordHandler);
        mCurrentPhone = ActivityUtils.getInputStrInfo(getIntent(), ActivityActionConfig.KEY_LOGIN_USER_NAME);

        mETPhone = (EditText)findViewById(R.id.fp_phone_number_et);
        if (TextUtils.isEmpty(mCurrentPhone)) {
            mETPhone.setText(mCurrentPhone);
        }
        mETCode = (EditText)findViewById(R.id.fp_author_code_et);
        mETPassword = (EditText)findViewById(R.id.fp_password_et);
        mETRePassword = (EditText)findViewById(R.id.fp_re_password_et);

        Button confirm_btn = (Button) findViewById(R.id.fp_confirm_btn);
        confirm_btn.setOnClickListener(this);
//        Button cancel_btn = (Button) findViewById(R.id.fp_cancel_btn);
//        cancel_btn.setOnClickListener(this);

        get_code_btn = (Button) findViewById(R.id.fp_get_code_btn);
        get_code_btn.setOnClickListener(this);
        TextView mTvTitle = (TextView)findViewById(R.id.title_text);
        mTvTitle.setText(getResources().getString(R.string.common_forget_password));
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
            case R.id.fp_confirm_btn:
                if (checkForgetPasswordInfo()){
                    doForgetPassword();
                }
                break;
//            case R.id.fp_cancel_btn:
//                setResult(ActivityActionConfig.RESULT_CODE_FORGET_PASSWORD_CANCEL);
//                finish();
//                break;
            case R.id.fp_get_code_btn:
                if (checkGetCodeInfo()) {
                    LogUtils.showLog("getcode", "do Get Code start");
                    doGetCode();
                    LogUtils.showLog("getcode", "do Get Code over");
                }
                break;
            default:
                throw new RuntimeException("nonsupport operation");
        }
    }
    private boolean checkForgetPasswordInfo(){
        String current_phone = mETPhone.getText().toString();
        if (TextUtils.isEmpty(current_phone)) {
            Toast.makeText(this, R.string.info_empty_phone, Toast.LENGTH_LONG).show();
            return false;
        }

        if (!CommonUtils.isCellPhone(current_phone)) {
            Toast.makeText(this, R.string.info_wrong_phone, Toast.LENGTH_LONG).show();
            return false;
        }

        String current_code = mETCode.getText().toString();
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
        if (!current_password.equals(current_re_password)) {
            Toast.makeText(this, R.string.info_password_not_equals, Toast.LENGTH_LONG).show();
            return false;
        }
        if(current_password.length()<6&&current_re_password.length()<6){
            Toast.makeText(this, R.string.info_length_password, Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

    private void doForgetPassword(){
        LogUtils.showLog("getcode", "doForgetPassword start");
        String current_phone = mETPhone.getText().toString();
        String current_password = mETPassword.getText().toString();
        String current_code = mETCode.getText().toString();
        LoadingProgressDialog.show(ForgetPasswordActivity.this, false, true, 30000);
        //Do register
        userManager.forgetPassword(current_phone,current_password, TypeUtils.REGISTER_TYPE_PHONE,current_code);
        LogUtils.showLog("getcode", "doForgetPassword over");
    }

    Timer timer;
    private int codeTime = 60;
    private MyTimerTask task;
    private boolean isGetVerification=false;
    private boolean checkGetCodeInfo() {
        String current_phone = mETPhone.getText().toString();
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
            Toast.makeText(ForgetPasswordActivity.this, "请稍后获取", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
    private void doGetCode() {
        String current_phone = mETPhone.getText().toString();
        if (!TextUtils.isEmpty(current_phone)){
            //do get code process;
            LogUtils.showLog("getcode", "doGetCode start");
            commonManager.getCode(current_phone,TypeUtils.SMS_CODE_TYPE_FORGET_PASSWORD,TypeUtils.SMS_CODE_GENRE_TYPE_PHONE);
            LogUtils.showLog("getcode", "doGetCode over");
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
