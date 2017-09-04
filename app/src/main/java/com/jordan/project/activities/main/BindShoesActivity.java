package com.jordan.project.activities.main;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jordan.project.JordanApplication;
import com.jordan.project.R;
import com.jordan.project.activities.adapter.BindShoesListAdapter;
import com.jordan.project.entity.MyShoesData;
import com.jordan.project.utils.JsonFalseUtils;
import com.jordan.project.utils.JsonSuccessUtils;
import com.jordan.project.utils.LogUtils;
import com.jordan.project.utils.ToastUtils;
import com.jordan.project.widget.LoadingProgressDialog;
import com.jordan.usersystemlibrary.UserManager;
import com.safari.httplibs.config.InnerMessageConfig;

import org.json.JSONException;

import java.util.ArrayList;

public class BindShoesActivity extends Activity implements View.OnClickListener{
    private ArrayList<MyShoesData> list = new ArrayList<MyShoesData>();
    private BindShoesListAdapter mBindShoesListAdapter;
    private ListView lvShoes;
    private static final String PAGE_NO = "1";
    private static final String PAGE_SIZE ="1000";
    private static final int PLEASE_INPUT_SHOES_CODE=1;
    private static final int PLEASE_CHOISE_SHOES=2;
    private static final String BIND_SHOES = "1";
    private Button mBtnCancel;
    private Button mBtnSubmit;
    private EditText mEtShoesCode;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case PLEASE_INPUT_SHOES_CODE:
                    ToastUtils.shortToast(BindShoesActivity.this,R.string.common_please_input_shoes_code);
                    break;
                case PLEASE_CHOISE_SHOES:
                    ToastUtils.shortToast(BindShoesActivity.this,R.string.common_please_choies_shoes);
                    break;
                case InnerMessageConfig.USER_SHOES_BIND_MESSAGE_SUCCESS:
                    LoadingProgressDialog.Dissmiss();
                    LogUtils.showLog("Result", "USER_SHOES_BIND_MESSAGE_SUCCESS result:" + (String) msg.obj);
                    //解析我的球鞋
                    JordanApplication.isBindShoes=true;
                    finish();
//                    try {
//                        list = new ArrayList<MyShoesData>();
//                        list=JsonSuccessUtils.getShoesList((String) msg.obj);
//                        mBindShoesListAdapter.updateList(list);
//                    } catch (JSONException e) {
//                        mHandler.sendEmptyMessage(InnerMessageConfig.USER_SHOES_BIND_MESSAGE_EXCEPTION);
//                        e.printStackTrace();
//                    }
                    break;
                case InnerMessageConfig.USER_SHOES_BIND_MESSAGE_EXCEPTION:
                    LoadingProgressDialog.Dissmiss();
                    ToastUtils.shortToast(BindShoesActivity.this,getResources().getString(R.string.http_exception));
                    break;
                case InnerMessageConfig.USER_SHOES_BIND_MESSAGE_FALSE:
                    LoadingProgressDialog.Dissmiss();
                    try {
                        String errorMsg= JsonFalseUtils.onlyErrorCodeResult((String)msg.obj);
                        if(errorMsg!=null)
                            ToastUtils.shortToast(BindShoesActivity.this,errorMsg);
                    } catch (JSONException e) {
                        mHandler.sendEmptyMessage(InnerMessageConfig.USER_SHOES_BIND_MESSAGE_EXCEPTION);
                        e.printStackTrace();
                    }
                    break;
                case InnerMessageConfig.USER_SHOES_LIST_MESSAGE_SUCCESS:
                    LogUtils.showLog("Result", "USER_SHOES_LIST_MESSAGE_SUCCESS result:" + (String) msg.obj);
                    //解析球鞋列表
                    try {
                        list = new ArrayList<MyShoesData>();
                        list=JsonSuccessUtils.getShoesList((String) msg.obj);
                        mBindShoesListAdapter.updateList(list);
                    } catch (JSONException e) {
                        mHandler.sendEmptyMessage(InnerMessageConfig.USER_SHOES_BIND_MESSAGE_EXCEPTION);
                        e.printStackTrace();
                    }
                    break;
                case InnerMessageConfig.USER_SHOES_LIST_MESSAGE_EXCEPTION:
                    ToastUtils.shortToast(BindShoesActivity.this,getResources().getString(R.string.http_exception));
                    break;
                case InnerMessageConfig.USER_SHOES_LIST_MESSAGE_FALSE:
                    try {
                        String errorMsg= JsonFalseUtils.onlyErrorCodeResult((String)msg.obj);
                        if(errorMsg!=null)
                            ToastUtils.shortToast(BindShoesActivity.this,errorMsg);
                    } catch (JSONException e) {
                        mHandler.sendEmptyMessage(InnerMessageConfig.USER_SHOES_LIST_MESSAGE_EXCEPTION);
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
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_bind_shoes);
        userManager=new UserManager(BindShoesActivity.this,mHandler);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.common_back_title);
        TextView mTvTitle = (TextView) findViewById(R.id.title_text);
        mTvTitle.setText(getResources().getString(R.string.common_bind_shoes));
        RelativeLayout mRLBack=(RelativeLayout)findViewById(R.id.title_back);
        mRLBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        setView();
        setListener();
        shoesList();
    }


    private void shoesList(){
        userManager.shoesList(PAGE_NO,PAGE_SIZE);
    }
    private void shoesBind(String type,String shoesId,String code,String name,
                           String price,String color,String size,String style,
                           String picture,String buyTime){
        LoadingProgressDialog.show(BindShoesActivity.this, false, true, 30000);
        userManager.shoesBind(type,shoesId,code,name,
                price,color,size,style,
                picture,buyTime);
    }
    private void setView() {
        lvShoes = (ListView)findViewById(R.id.bind_shoes_list);
        mBindShoesListAdapter = new BindShoesListAdapter(BindShoesActivity.this,list);
        lvShoes.setAdapter(mBindShoesListAdapter);
        mEtShoesCode = (EditText)findViewById(R.id.shoes_code_et);
        mBtnCancel = (Button)findViewById(R.id.cancel);
        mBtnSubmit = (Button)findViewById(R.id.submit);
    }
    private void setListener() {
        mBtnCancel.setOnClickListener(this);
        mBtnSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cancel:
                finish();
                break;
            case R.id.submit:
                //检查码数是否输入
                if(mEtShoesCode.getText().toString().equals("")){
                    mHandler.sendEmptyMessage(PLEASE_INPUT_SHOES_CODE);
                    return;
                }
                //检测是否选择了球鞋
                if(mBindShoesListAdapter.getCheckPosition()<0){
                    mHandler.sendEmptyMessage(PLEASE_CHOISE_SHOES);
                    return;
                }
                MyShoesData myShoesData = list.get(mBindShoesListAdapter.getCheckPosition());
//                shoesBind(BIND_SHOES,myShoesData.getId(),myShoesData.getCode(),myShoesData.getName(),
//                        myShoesData.getPrice(),myShoesData.getColor(),mEtShoesCode.getText().toString(),myShoesData.getStyle(),
//                        myShoesData.getPicture(),String.valueOf(new Date().getTime() / 1000));
                shoesBind(BIND_SHOES,myShoesData.getId(),"","",
                        "","",mEtShoesCode.getText().toString(),"",
                        "","");
                break;
        }
    }
}
