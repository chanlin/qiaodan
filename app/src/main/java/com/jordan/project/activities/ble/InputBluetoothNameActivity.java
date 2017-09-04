package com.jordan.project.activities.ble;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jordan.commonlibrary.CommonManager;
import com.safari.httplibs.config.InnerMessageConfig;
import com.jordan.project.JordanApplication;
import com.jordan.project.R;
import com.jordan.project.utils.JsonFalseUtils;
import com.jordan.project.utils.ToastUtils;
import com.jordan.project.widget.LoadingProgressDialog;

import org.json.JSONException;

public class InputBluetoothNameActivity extends Activity {

    private EditText mETName;
    private final static int PLEASE_INPUT_NAME=1;
    private String id;
    private CommonManager commonManager;
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case PLEASE_INPUT_NAME:
                    break;
                case InnerMessageConfig.COMMON_BLUETOOTH_UPDATE_MESSAGE_SUCCESS:
                    LoadingProgressDialog.Dissmiss();
                    JordanApplication.isUpdateBluetoothList = true;
                    finish();
                    break;
                case InnerMessageConfig.COMMON_BLUETOOTH_UPDATE_MESSAGE_EXCEPTION:
                    LoadingProgressDialog.Dissmiss();
                    ToastUtils.shortToast(InputBluetoothNameActivity.this, getResources().getString(R.string.http_exception));
                    break;
                case InnerMessageConfig.COMMON_BLUETOOTH_UPDATE_MESSAGE_FALSE:
                    try {
                        String errorMsg = JsonFalseUtils.onlyErrorCodeResult((String) msg.obj);
                        if(errorMsg!=null)
                            ToastUtils.shortToast(InputBluetoothNameActivity.this, errorMsg);
                        LoadingProgressDialog.Dissmiss();
                    } catch (JSONException e) {
                        mHandler.sendEmptyMessage(InnerMessageConfig.COMMON_BLUETOOTH_UPDATE_MESSAGE_EXCEPTION);
                        e.printStackTrace();
                    }

                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_input_bluetooth_name);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.common_text_more_title);
        id = getIntent().getStringExtra("id");
        commonManager = new CommonManager(InputBluetoothNameActivity.this, mHandler);
        TextView mTvTitle = (TextView) findViewById(R.id.register_title_text);
        mTvTitle.setText(getResources().getString(R.string.common_bluetooth_input_name));
        TextView mTVSubmit = (TextView) findViewById(R.id.register_title_more);
        mTVSubmit.setText(getResources().getString(R.string.common_confirm));
        mTVSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //提交修改名称内容
                if(mETName.getText().length()==0){
                    mHandler.sendEmptyMessage(PLEASE_INPUT_NAME);
                }else{
                    bluetoothUpdate(id,mETName.getText().toString());
                }
            }
        });
        RelativeLayout mRLBack = (RelativeLayout) findViewById(R.id.title_back);
        mRLBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mETName = (EditText) findViewById(R.id.input_bluetooth_name_et);
        mETName.setText(getIntent().getStringExtra("name"));


    }
    private void bluetoothUpdate(String id,String name){
        LoadingProgressDialog.show(InputBluetoothNameActivity.this, false, true, 30000);
        commonManager.bluetoothUpdate(id,name);
    }

}
