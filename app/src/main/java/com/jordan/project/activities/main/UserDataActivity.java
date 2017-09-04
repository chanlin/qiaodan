package com.jordan.project.activities.main;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.safari.httplibs.config.InnerMessageConfig;
import com.jordan.project.JordanApplication;
import com.jordan.project.R;
import com.jordan.project.activities.RegisterDataActivity;
import com.jordan.project.activities.ble.BluetoothListActivity;
import com.jordan.project.content.BluetoothObserver;
import com.jordan.project.content.UserDataObserver;
import com.jordan.project.database.DatabaseObject;
import com.jordan.project.database.DatabaseProvider;
import com.jordan.project.database.DatabaseService;
import com.jordan.project.entity.BluetoothData;
import com.jordan.project.entity.UserInfoData;
import com.jordan.project.utils.JsonSuccessUtils;
import com.jordan.project.utils.LogUtils;
import com.jordan.project.utils.SettingSharedPerferencesUtil;
import com.jordan.project.utils.ToastUtils;
import com.jordan.project.widget.PickerView;
import com.jordan.project.widget.PickerViewDialog;
import com.jordan.project.widget.UnBindDialog;
import com.jordan.usersystemlibrary.UserManager;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class UserDataActivity extends Activity implements View.OnClickListener{
    private TextView mTVName;
    private TextView mTVWeight;
    private TextView mTVHeight;
    private TextView mTVAgo;
    private TextView mTVPosition;
    private TextView mTVBleState;
    private ImageView mIVHead;
    private ImageView mIVEdit;
    private TextView mTVBluetoothFootState;//user_data_bluetooth_foot_state
    private LinearLayout mLLBluetoothFoot;//user_data_bluetooth_foot_ll
    private RelativeLayout mRlMyShoe;
    private RelativeLayout mRlMyShare;
    private RelativeLayout mRlMyBle;
    private RelativeLayout mRlSoftwareVersion;
    private RelativeLayout mRLMyDataUpdate;//my_data_update
    private RelativeLayout mRLMyScan;//my_scan
    private RelativeLayout mRLMyUserAnaly;//my_user_analy
    private Button mBtnLogout;
    private String imgs="";
    private static final int UPDATE_BLUETOOTH_FOOT = 2;

    private UserDataObserver mUserDataObserver;
    private BluetoothObserver mBluetoothObserver;

    //请求用户请求信息
    private Handler mMainHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case UPDATE_BLUETOOTH_FOOT:
                    mTVBluetoothFootState.setText(dialogValue);
                    SettingSharedPerferencesUtil.SetBluetoothFootValue(UserDataActivity.this,JordanApplication.getUsername(UserDataActivity.this),dialogValue);
                    break;
                case InnerMessageConfig.USER_GET_USER_DATA_MESSAGE_SUCCESS:
                    //解析正确参数-内容在msg.obj
                    //保存登录返回数据
                    LogUtils.showLog("Result", "USER_GET_USER_DATA_MESSAGE_SUCCESS result:"+(String)msg.obj);
                    //保存用户信息
                    try {
                        UserInfoData userInfoData = JsonSuccessUtils.getUserData((String)msg.obj);
                        LogUtils.showLog("Result", "USER_GET_USER_DATA_MESSAGE_SUCCESS userInfoData:"+userInfoData);
                        DatabaseService.createUserInfo(userInfoData.getUsername(),userInfoData.getName(),userInfoData.getNick(),
                                userInfoData.getGender(),userInfoData.getAge(),userInfoData.getBirthday(),userInfoData.getPosition(),
                                userInfoData.getWeight(),userInfoData.getHeight(),userInfoData.getQq(),userInfoData.getMobile(),
                                userInfoData.getEmail(),userInfoData.getImg(),userInfoData.getImgId());
                        LogUtils.showLog("Result", "USER_GET_USER_DATA_MESSAGE_SUCCESS createUserInfo over");
                    } catch (JSONException e) {
                        LogUtils.showLog("Result", "USER_GET_USER_DATA_MESSAGE_SUCCESS JSONException");
                        mMainHandler.sendEmptyMessage(InnerMessageConfig.USER_LOGIN_MESSAGE_EXCEPTION);
                        e.printStackTrace();
                    }

                    //update用户信息
                    initData();
                    break;
                case InnerMessageConfig.USER_GET_USER_DATA_MESSAGE_EXCEPTION:
                    //ToastUtils.shortToast(UserDataActivity.this,getResources().getString(R.string.http_exception));
                    break;
                case InnerMessageConfig.USER_GET_USER_DATA_MESSAGE_FALSE:
//                    try {
//                        String errorMsg= JsonFalseUtils.onlyErrorCodeResult((String)msg.obj);
//                        if(errorMsg!=null)
//                            ToastUtils.shortToast(UserDataActivity.this,errorMsg);
//                    } catch (JSONException e) {
//                        mMainHandler.sendEmptyMessage(InnerMessageConfig.USER_LOGIN_MESSAGE_EXCEPTION);
//                        e.printStackTrace();
//                    }
                    break;
                case UserDataObserver.DATABASE_UPDATE:
                    initData();
                    break;
                case BluetoothObserver.DATABASE_UPDATE:
                    initBluetooth();
                    break;
            };
        }
    };
    private UserManager userManager;
    private void registerContentObservers() {
        mUserDataObserver=new UserDataObserver(mMainHandler);
        Uri uri= Uri.parse("content://" + DatabaseProvider.AUTHOR + "/" + DatabaseObject.USER_INFO);
        getContentResolver().registerContentObserver(uri, true,
                mUserDataObserver);
        mBluetoothObserver=new BluetoothObserver(mMainHandler);
        uri= Uri.parse("content://" + DatabaseProvider.AUTHOR + "/" + DatabaseObject.BLUETOOTH_LIST);
        getContentResolver().registerContentObserver(uri, true,
                mBluetoothObserver);
    }
    //监听数据库
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_user_data);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.common_back_title);
        registerContentObservers();
        userManager=new UserManager(UserDataActivity.this,mMainHandler);
        setView();
        setListener();
        initData();
        LogUtils.showLog("database", "createUserInfo onCreate doGetUserDataTask");
        doGetUserDataTask();
    }
    private void initBluetooth(){
        if(DatabaseService.findBluetoothList(JordanApplication.getUsername(UserDataActivity.this)).size()>0){
            mTVBleState.setText(getResources().getString(R.string.common_has_bind_bluetooth));
        }else{
            mTVBleState.setText(getResources().getString(R.string.common_no_bind_bluetooth));
        }
    }

    private void doGetUserDataTask() {
        userManager.getUserData(JordanApplication.getUsername(UserDataActivity.this));
    }
    private void initData() {
        //赋值Data
        //获取数据库用户信息
        UserInfoData userInfoData = DatabaseService.findUserInfo(JordanApplication.getUsername(UserDataActivity.this));
        if(userInfoData!=null) {
            if (!userInfoData.getNick().equals("null"))
            mTVName.setText(userInfoData.getNick());
            if (!userInfoData.getWeight().equals("null"))
            mTVWeight.setText(userInfoData.getWeight()+getResources().getString(R.string.common_unit_kg));
            if (!userInfoData.getHeight().equals("null"))
            mTVHeight.setText(userInfoData.getHeight()+getResources().getString(R.string.common_unit_cm));
            if (!userInfoData.getAge().equals("null"))
            mTVAgo.setText(userInfoData.getAge()+getResources().getString(R.string.ago_year));
            if (!userInfoData.getPosition().equals("null"))
            mTVPosition.setText(userInfoData.getPosition());


            //ImageLoader导入图片
            if (!userInfoData.getImg().equals("null")) {
                imgs=userInfoData.getImgId();
                LogUtils.showLog("IMAGEID", "userInfoData.getImgId():" + userInfoData.getImgId());
                DisplayImageOptions options = new DisplayImageOptions.Builder()
                        .showImageOnLoading(R.mipmap.img_jordan_logo)
                        .showImageForEmptyUri(R.mipmap.img_jordan_logo)
                        .showImageOnFail(R.mipmap.img_jordan_logo).cacheInMemory(true)
                        .cacheOnDisk(true).considerExifParams(true).build();
                ImageLoader.getInstance().displayImage(userInfoData.getImg(), mIVHead, options);
            }
        }
        else{
            LogUtils.showLog("database", "createUserInfo initData doGetUserDataTask");
            doGetUserDataTask();
        }
    }

    private void setView() {
        TextView mTVTitle=(TextView)findViewById(R.id.title_text);
        mTVTitle.setText(getResources().getString(R.string.common_user_center));
        RelativeLayout mRLBack=(RelativeLayout)findViewById(R.id.title_back);
        mRLBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        mTVName=(TextView)findViewById(R.id.user_data_name);
        mTVWeight=(TextView)findViewById(R.id.user_data_weight);
        mTVHeight=(TextView)findViewById(R.id.user_data_height);
        mTVAgo=(TextView)findViewById(R.id.user_data_ago);
        mTVPosition=(TextView)findViewById(R.id.user_data_position);
        mTVBleState=(TextView)findViewById(R.id.user_data_ble_state);
        mIVHead=(ImageView)findViewById(R.id.user_data_head_iv);
        mIVEdit=(ImageView)findViewById(R.id.user_data_edit);
        mRlMyShoe=(RelativeLayout) findViewById(R.id.my_shoe);
        mRlMyShare=(RelativeLayout)findViewById(R.id.my_share);
        mRlMyBle=(RelativeLayout)findViewById(R.id.my_ble);
        mRlSoftwareVersion=(RelativeLayout)findViewById(R.id.software_version);
        mBtnLogout=(Button)findViewById(R.id.btn_logout);
        mRLMyDataUpdate=(RelativeLayout) findViewById(R.id.my_data_update);
        mRLMyScan=(RelativeLayout) findViewById(R.id.my_scan);
        mRLMyUserAnaly=(RelativeLayout) findViewById(R.id.my_user_analy);
        mTVBluetoothFootState=(TextView)findViewById(R.id.user_data_bluetooth_foot_state);
        String bluetoothFoot=SettingSharedPerferencesUtil.GetBluetoothFootValueConfig(UserDataActivity.this,JordanApplication.getUsername(UserDataActivity.this));
        mTVBluetoothFootState.setText(bluetoothFoot);
        mLLBluetoothFoot=(LinearLayout)findViewById(R.id.user_data_bluetooth_foot_ll);

        initBluetooth();
    }
    private void setListener() {
        mIVEdit.setOnClickListener(this);
        mTVBleState.setOnClickListener(this);
        mRlMyShare.setOnClickListener(this);
        mRlMyShoe.setOnClickListener(this);
        mRlMyBle.setOnClickListener(this);
        mRlSoftwareVersion.setOnClickListener(this);
        mBtnLogout.setOnClickListener(this);
        mRLMyDataUpdate.setOnClickListener(this);
        mRLMyScan.setOnClickListener(this);
        mRLMyUserAnaly.setOnClickListener(this);
        mLLBluetoothFoot.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.user_data_edit:
                Intent intent = new Intent(UserDataActivity.this, RegisterDataActivity.class);
                intent.putExtra("come",RegisterDataActivity.COME_FROM_USER_DATA);
                LogUtils.showLog("IMAGEID", "putExtra img:" + imgs);
                intent.putExtra("img",imgs);
                startActivity(intent);
                break;
            case R.id.user_data_ble_state:
                intent = new Intent(UserDataActivity.this, BluetoothListActivity.class);
                startActivity(intent);
                break;
            case R.id.my_share:
                intent = new Intent(UserDataActivity.this, UserShareActivity.class);
                startActivity(intent);
                break;
            case R.id.my_shoe:
                intent = new Intent(UserDataActivity.this, MyShoesActivity.class);
                startActivity(intent);
                break;
            case R.id.my_ble:
                ArrayList<BluetoothData> mBluetoothList = DatabaseService.findBluetoothList(JordanApplication.getUsername(UserDataActivity.this));
                if(mBluetoothList.size()==0){
                    ToastUtils.shortToast(UserDataActivity.this, getResources().getString(R.string.common_please_bind_bluetooth_to_person_data));
                }else {
                intent = new Intent(UserDataActivity.this, OTAVersionActivity.class);
                startActivity(intent);
                }
                break;
            case R.id.software_version:
                intent = new Intent(UserDataActivity.this, SoftVersionActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_logout:
                showExitDialogs();
                break;
            case R.id.my_data_update:
                intent = new Intent(UserDataActivity.this, RegisterDataActivity.class);
                intent.putExtra("come",RegisterDataActivity.COME_FROM_USER_DATA);
                LogUtils.showLog("IMAGEID", "putExtra img:" + imgs);
                intent.putExtra("img",imgs);
                startActivity(intent);
                break;
            case R.id.my_scan:
                intent = new Intent(UserDataActivity.this, BluetoothListActivity.class);
                startActivity(intent);
                break;
            case R.id.my_user_analy:
                //跳转到指南页面
                intent = new Intent(UserDataActivity.this, FingerpostActivity.class);
                startActivity(intent);
                break;
            case R.id.user_data_bluetooth_foot_ll:
                //打开默认脚设置Dialog
                showPickerViewDialog("");
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(JordanApplication.isUpdateUserData){
            JordanApplication.isUpdateUserData=false;
            LogUtils.showLog("database", "createUserInfo onResume doGetUserDataTask");
            doGetUserDataTask();
        }
    }
    private void showExitDialogs() {
        final Dialog dialog = new UnBindDialog(UserDataActivity.this,
                R.style.chooes_dialog_style);
        dialog.show();
        Window window = dialog.getWindow();
//        WindowManager.LayoutParams lp = window.getAttributes();
//        lp.alpha = 0.8f;
//        window.setAttributes(lp);
        Button btnSubmit = (Button) window.findViewById(R.id.register_data_chooes_submit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                SettingSharedPerferencesUtil.SetLoginUsernameValue(UserDataActivity.this,"");
                SettingSharedPerferencesUtil.SetPasswordUsernameValue(UserDataActivity.this,"");
                JordanApplication.islogout=true;
                finish();
            }
        });
        TextView tvText = (TextView) window.findViewById(R.id.rl_chooes_text);
        tvText.setText("是否要退出此账号");
        Button btnCancel = (Button) window.findViewById(R.id.register_data_chooes_cancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }
    private String dialogValue = "";
    private void showPickerViewDialog(String text) {
        final Dialog dialog = new PickerViewDialog(UserDataActivity.this,
                R.style.chooes_dialog_style);
        dialog.show();
        Window window = dialog.getWindow();
//        WindowManager.LayoutParams lp = window.getAttributes();
//        lp.alpha = 0.8f;
//        window.setAttributes(lp);
        PickerView pickerView = (PickerView) window.findViewById(R.id.register_data_picker_view);
        List<String> data = new ArrayList<String>();
        data.add("右脚");
        data.add("左脚");
        //读取缓存脚
        String bluetoothFoot=SettingSharedPerferencesUtil.GetBluetoothFootValueConfig(UserDataActivity.this,JordanApplication.getUsername(UserDataActivity.this));
        dialogValue = bluetoothFoot;
        int position=0;
        if(dialogValue.equals("右脚")){
            position=0;
        }else{
            position=1;
        }
        LogUtils.showLog("bluetoothfoot","dialogValue"+dialogValue);
        LogUtils.showLog("bluetoothfoot","position"+position);
        //然后设置position
        pickerView.setData(data,position);
        pickerView.setOnSelectListener(new PickerView.onSelectListener() {
            @Override
            public void onSelect(String text) {
                LogUtils.showLog("bluetoothfoot","text"+text);
                dialogValue = text;
            }
        });
        Button btnCancel = (Button) window.findViewById(R.id.register_data_chooes_cancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });
        Button btnSubmit = (Button) window.findViewById(R.id.register_data_chooes_submit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //写入缓存，更新脚text
                mMainHandler.sendEmptyMessage(UPDATE_BLUETOOTH_FOOT);
                dialog.dismiss();
            }
        });
        TextView tvHint = (TextView) window.findViewById(R.id.pick_view_hint);
        tvHint.setText(text);

    }
}
