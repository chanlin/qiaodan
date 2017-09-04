package com.jordan.project.activities.motion;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.safari.blelibs.BleManager;
import com.safari.blelibs.IBleManagerCallback;
import com.safari.httplibs.config.InnerMessageConfig;
import com.jordan.project.JordanApplication;
import com.jordan.project.R;
import com.jordan.project.data.BluetoothDeviceInfo;
import com.jordan.project.data.MotionUploadData;
import com.jordan.project.database.DatabaseService;
import com.jordan.project.entity.BluetoothData;
import com.jordan.project.entity.JumpPointData;
import com.jordan.project.entity.TrakPointData;
import com.jordan.project.entity.UserInfoData;
import com.jordan.project.utils.FileUtils;
import com.jordan.project.utils.HexadecimalUtils;
import com.jordan.project.utils.JsonFalseUtils;
import com.jordan.project.utils.LogUtils;
import com.jordan.project.utils.NoDoubleClickListener;
import com.jordan.project.utils.SettingSharedPerferencesUtil;
import com.jordan.project.utils.ToastUtils;
import com.jordan.usersystemlibrary.UserManager;
import com.jordan.usersystemlibrary.data.MoveUploadInfo;

import org.json.JSONException;

import java.lang.ref.WeakReference;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;

public class MotionUploadActivity extends Activity implements IBleManagerCallback {
    private ImageView mIVEnd;
    private TextView mTVEndHint;//motion_ing_end_tv
    private TextView mTVTopHint;//motion_ing_state_hint
    private TextView mTVLinkHint;//motion_ing_link
    private String mSN, mMAC, mDeviceName, mMacAddress;
    ;

    private RelativeLayout mRlUploadBG;//motion_upload_bg
    private LinearLayout mLLUploadBottom;//motion_update_bottom
    private ImageView mIvUploadLogo;//motion_update_logo
    private AnimationDrawable mUploadLogoAnimationDrawable;
    private TextView mTvState;//motion_update_text
    private Button mBtnUploadAbondom;//motion_update_abondom
    private Button mBtnUploadUpload;//motion_update_upload
    private boolean isFinish = false;

    private static final int REQUEST_CODE_LE = 1;
    private BleManager mBleManager;
    private HashMap<String, BluetoothDeviceInfo> mDeviceMap;
    private boolean isFindBluetooth = false;

    private int mMotionstate = 0;
    private static final int MESSAGE_MOTION_STATE_CONNECTTION = 0;//连接
    private static final int MESSAGE_MOTION_STATE_CONNECTTIONING = 1;//连接设备连接中
    private static final int MESSAGE_MOTION_STATE_CONNECTTION_OK = 2;//连接成功
    private static final int MESSAGE_MOTION_STATE_CONNECTTION_NO_BIND = 3;//用户暂时未绑定蓝牙设备
    private static final int MESSAGE_MOTION_STATE_CONNECTTION_GET_BLE_FALSE = 4;//周边没有可以用的蓝牙设备
    private static final int MESSAGE_MOTION_STATE_START = 5;//准备开始运动
    private static final int MESSAGE_MOTION_STATE_START_UPLOAD_DATA = 6;//发现芯片有数据同步数据

    private static final int MESSAGE_MOTION_STATE_SYNCHRO_DATA = 15;//同步数据
    private static final int MESSAGE_MOTION_STATE_SYNCHRO_DATA_ING = 16;//同步数据中
    private static final int MESSAGE_MOTION_STATE_UPLOAD_DATA = 17;//上传数据中
    private static final int MESSAGE_MOTION_STATE_OVER = 18;//最终结束
    private static final int MESSAGE_MOTION_STATE_OVER_FALSE = 19;//最终失败

    private static final String DEFAULT_RIGHT_FOOTER = "R";
    private static final String DEFAULT_LEFT_FOOTER = "L";
    private boolean mIsGrant;
    private boolean isCorrect = false;
    private HashMap<String, MotionUploadData> motionUploadDatas = new HashMap<String, MotionUploadData>();

    private static final int MESSAGE_CHANGE_END_IMAGE = 1;
    private WeakReference<Drawable> mCurrentEndDrawable;
    private int mCurrentEndIndex;
    private static final int[] DRAWABLE_END_LIST = new int[]{
            R.mipmap.motion_end_000, R.mipmap.motion_end_001, R.mipmap.motion_end_002, R.mipmap.motion_end_003,
            R.mipmap.motion_end_004, R.mipmap.motion_end_005, R.mipmap.motion_end_006, R.mipmap.motion_end_007,
            R.mipmap.motion_end_008, R.mipmap.motion_end_009, R.mipmap.motion_end_010, R.mipmap.motion_end_011,
            R.mipmap.motion_end_012, R.mipmap.motion_end_013, R.mipmap.motion_end_014, R.mipmap.motion_end_015,
            R.mipmap.motion_end_016, R.mipmap.motion_end_017, R.mipmap.motion_end_018, R.mipmap.motion_end_019,
            R.mipmap.motion_end_020, R.mipmap.motion_end_021, R.mipmap.motion_end_022, R.mipmap.motion_end_023,
            R.mipmap.motion_end_024, R.mipmap.motion_end_025, R.mipmap.motion_end_026, R.mipmap.motion_end_027,
            R.mipmap.motion_end_028, R.mipmap.motion_end_029, R.mipmap.motion_end_030, R.mipmap.motion_end_031,
            R.mipmap.motion_end_032, R.mipmap.motion_end_033, R.mipmap.motion_end_034, R.mipmap.motion_end_035,
            R.mipmap.motion_end_036, R.mipmap.motion_end_037, R.mipmap.motion_end_038, R.mipmap.motion_end_039};

    private ImageView mIVBleLogo;
    private AnimationDrawable mBleLogoAnimationDrawable;
//    private static final int MESSAGE_CHANGE_BLE_LOGO_IMAGE = 2;
//    private WeakReference<Drawable> mCurrentBleLogoDrawable;
//    private int mCurrentBleLogoIndex;
//    private static final int[] DRAWABLE_BLE_LOGO_LIST = new int[]{
//            R.mipmap.ble_logo_000, R.mipmap.ble_logo_001, R.mipmap.ble_logo_002, R.mipmap.ble_logo_003,
//            R.mipmap.ble_logo_004, R.mipmap.ble_logo_005, R.mipmap.ble_logo_006, R.mipmap.ble_logo_007,
//            R.mipmap.ble_logo_008, R.mipmap.ble_logo_009, R.mipmap.ble_logo_010, R.mipmap.ble_logo_011,
//            R.mipmap.ble_logo_012, R.mipmap.ble_logo_013, R.mipmap.ble_logo_014, R.mipmap.ble_logo_015,
//            R.mipmap.ble_logo_016, R.mipmap.ble_logo_017, R.mipmap.ble_logo_018, R.mipmap.ble_logo_019,
//            R.mipmap.ble_logo_020, R.mipmap.ble_logo_021, R.mipmap.ble_logo_022, R.mipmap.ble_logo_023,
//            R.mipmap.ble_logo_024, R.mipmap.ble_logo_025, R.mipmap.ble_logo_026, R.mipmap.ble_logo_027,
//            R.mipmap.ble_logo_028, R.mipmap.ble_logo_029, R.mipmap.ble_logo_030, R.mipmap.ble_logo_031,
//            R.mipmap.ble_logo_032, R.mipmap.ble_logo_033, R.mipmap.ble_logo_034, R.mipmap.ble_logo_035,
//            R.mipmap.ble_logo_036, R.mipmap.ble_logo_037, R.mipmap.ble_logo_038, R.mipmap.ble_logo_039};

    private static final int N0_PERMISSION = 21;
    private String mStartData = "";
    private String startTime, endTime;
    private String mStadiumPicture = "";//球场图片路径
    private String mHandle = "2";
    private String mAddress = "";
    private HashMap<String, TrakPointData> mTrakMap = new HashMap<String, TrakPointData>();
    private HashMap<String, JumpPointData> mJumpMap = new HashMap<String, JumpPointData>();
    private ArrayList<String> mAllArray = new ArrayList<String>();
    private Button mBtnRepeat;//repeat_scan_bluetooth_btn
    private byte[] mDateByte = new byte[]{};

    private boolean isCanConnection = true;
    private static final int HAS_DELAY_TIME = 25;
    private static final int DELAY_TIME = 20000;
    private UserManager userManager;
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case HAS_DELAY_TIME:
                    isCanConnection = false;
                    break;
                case N0_PERMISSION:
                    ToastUtils.shortToast(MotionUploadActivity.this, R.string.please_open_permission);
                    break;
                case MESSAGE_CHANGE_END_IMAGE:
                    mCurrentEndIndex = mCurrentEndIndex + 1;
                    if (mCurrentEndIndex >= DRAWABLE_END_LIST.length) {
                        mCurrentEndIndex = 0;
                    }
                    int resource_id = DRAWABLE_END_LIST[mCurrentEndIndex];
                    //android.util.Log.e("SlashInfo", "index= " + mCurrentEndIndex + " total= " + DRAWABLE_END_LIST.length + " resource_id= " + resource_id);
                    mIVEnd.setBackgroundResource(resource_id);
                    sendEmptyMessageDelayed(MESSAGE_CHANGE_END_IMAGE, 3);
                    break;
//                case MESSAGE_CHANGE_BLE_LOGO_IMAGE:
//                    mCurrentBleLogoIndex = mCurrentBleLogoIndex + 1;
//                    if (mCurrentBleLogoIndex >= DRAWABLE_BLE_LOGO_LIST.length) {
//                        mCurrentBleLogoIndex = 0;
//                    }
//                    resource_id = DRAWABLE_BLE_LOGO_LIST[mCurrentBleLogoIndex];
//                    //android.util.Log.e("SlashInfo", "index= " + mCurrentBleLogoIndex + " total= " + DRAWABLE_BLE_LOGO_LIST.length + " resource_id= " + resource_id);
//                    //mIVBleLogo.setBackgroundResource(resource_id);
//                    sendEmptyMessageDelayed(MESSAGE_CHANGE_BLE_LOGO_IMAGE, 3);
//                    break;
                case InnerMessageConfig.USER_MOVE_UPLOADS_MESSAGE_SUCCESS:
                    LogUtils.showLog("Result", "USER_MOVE_UPLOADS_MESSAGE_SUCCESS");
                    //解析正确参数-内容在msg.obj
                    //保存登录返回数据
                    LogUtils.showLog("Result", "USER_MOVE_UPLOAD_MESSAGE_SUCCESS result:" + (String) msg.obj);

                    mMotionstate = MESSAGE_MOTION_STATE_OVER;
                    initData();
                    //刪除数据库
                    DatabaseService.deleteMotionBluetoothData();
                    ToastUtils.shortToast(MotionUploadActivity.this, "数据上传成功");
                    JordanApplication.isUpdateMotion = true;
                    finishing();
                    break;
                case InnerMessageConfig.USER_MOVE_UPLOADS_MESSAGE_EXCEPTION:
                    LogUtils.showLog("Result", "USER_MOVE_UPLOADS_MESSAGE_EXCEPTION");
                    ToastUtils.shortToast(MotionUploadActivity.this, R.string.http_exception);
                    mMotionstate = MESSAGE_MOTION_STATE_OVER_FALSE;
                    initData();
                    finishing();
                    break;
                case InnerMessageConfig.USER_MOVE_UPLOADS_MESSAGE_FALSE:
                    LogUtils.showLog("Result", "USER_MOVE_UPLOADS_MESSAGE_FALSE");
                    try {
                        String errorMsg = JsonFalseUtils.onlyErrorCodeResult((String) msg.obj);
                        if (errorMsg != null)
                            ToastUtils.shortToast(MotionUploadActivity.this, errorMsg);
                        mMotionstate = MESSAGE_MOTION_STATE_OVER_FALSE;
                        initData();
                        finishing();
                    } catch (JSONException e) {
                        mHandler.sendEmptyMessage(InnerMessageConfig.USER_MOTION_DETAIL_MESSAGE_EXCEPTION);
                        e.printStackTrace();
                    }
                    break;
//                case InnerMessageConfig.USER_MOVE_UPLOAD_MESSAGE_SUCCESS:
//                    //解析正确参数-内容在msg.obj
//                    //保存登录返回数据
//                    LogUtils.showLog("Result", "USER_MOVE_UPLOAD_MESSAGE_SUCCESS result:" + (String) msg.obj);
//
//                    ToastUtils.shortToast(MotioningActivity.this, R.string.common_motion_over_hint);
//                    finish();
//                    break;
//                case InnerMessageConfig.USER_MOVE_UPLOAD_MESSAGE_EXCEPTION:
//                    ToastUtils.shortToast(MotioningActivity.this, R.string.http_exception);
//                    break;
//                case InnerMessageConfig.USER_MOVE_UPLOAD_MESSAGE_FALSE:
//                    try {
//                        String errorMsg = JsonFalseUtils.onlyErrorCodeResult((String) msg.obj);
//                        if (errorMsg != null)
//                            ToastUtils.shortToast(MotioningActivity.this, errorMsg);
//                    } catch (JSONException e) {
//                        mHandler.sendEmptyMessage(InnerMessageConfig.USER_MOTION_DETAIL_MESSAGE_EXCEPTION);
//                        e.printStackTrace();
//                    }
//                    break;

            }

        }

        ;
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_motion_upload);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.common_back_title);
        TextView mTVTitle = (TextView) findViewById(R.id.title_text);
        mTVTitle.setText(R.string.common_motion_upload);
        RelativeLayout mRLBack = (RelativeLayout) findViewById(R.id.title_back);
        mRLBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        userManager = new UserManager(MotionUploadActivity.this, mHandler);

        setView();
        setListener();
        checkCameraPermission();
        if (!mIsGrant) {
            mHandler.sendEmptyMessage(N0_PERMISSION);
            finishing();
        }
        mHandler.sendEmptyMessageDelayed(HAS_DELAY_TIME, DELAY_TIME);
    }

    private void checkCameraPermission() {
        int is_granted = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (PackageManager.PERMISSION_GRANTED != is_granted) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1000);
        } else {
            mIsGrant = true;
        }
    }

    private boolean checkLePermission() {
        if (PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_CODE_LE);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        LogUtils.showLog("motioning", "onRequestPermissionsResult");
        if (PackageManager.PERMISSION_GRANTED == grantResults[0]) {
            mIsGrant = true;
        }
        if (REQUEST_CODE_LE == requestCode) {
            int index = 0;
            boolean is_found = false;
            for (; index < permissions.length; index++) {
                String current_permission = permissions[index];
                if (Manifest.permission.ACCESS_COARSE_LOCATION.equals(current_permission)) {
                    is_found = true;
                    break;
                }
            }
            if (is_found) {
                int current_result = grantResults[index];
                if (PackageManager.PERMISSION_GRANTED == current_result) {
                    if (!mBleManager.isLeEnabled()) {
                        Intent start_le_enable = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                        startActivityForResult(start_le_enable, REQUEST_CODE_LE);
                    } else {
                        mBleManager.startManager();
                    }
                    return;
                } else {
                    finishing();
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        LogUtils.showLog("motioning", "onActivityResult");
        LogUtils.showLog("motioning", "requestCode:" + requestCode);
        LogUtils.showLog("motioning", "resultCode:" + resultCode);
        if (REQUEST_CODE_LE == requestCode) {
            if (RESULT_OK == resultCode) {
                mBleManager.startManager();
            } else {
                mHandler.sendEmptyMessage(N0_PERMISSION);
                finishing();
            }
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    private void setView() {

        mIVEnd = (ImageView) findViewById(R.id.motion_ing_end_iv);
        mCurrentEndIndex = 0;
        mCurrentEndDrawable = new WeakReference<Drawable>(getResources().getDrawable(DRAWABLE_END_LIST[mCurrentEndIndex]));
        //mIVEnd.setBackgroundResource(DRAWABLE_END_LIST[mCurrentEndIndex]);
        mHandler.sendEmptyMessageDelayed(MESSAGE_CHANGE_END_IMAGE, 3);

        mIVBleLogo = (ImageView) findViewById(R.id.motion_ing_logo);
        mBleLogoAnimationDrawable = (AnimationDrawable) mIVBleLogo.getBackground();
//        mCurrentBleLogoIndex = 0;
//        mCurrentBleLogoDrawable = new WeakReference<Drawable>(getResources().getDrawable(DRAWABLE_BLE_LOGO_LIST[mCurrentBleLogoIndex]));
//        //mIVBleLogo.setBackgroundResource(DRAWABLE_BLE_LOGO_LIST[mCurrentBleLogoIndex]);
//        mHandler.sendEmptyMessageDelayed(MESSAGE_CHANGE_BLE_LOGO_IMAGE, 3);

        //mBtnMotionOver.setVisibility(View.GONE);
        mTVEndHint = (TextView) findViewById(R.id.motion_ing_end_tv);
        mTVTopHint = (TextView) findViewById(R.id.motion_ing_state_hint);
        mTVLinkHint = (TextView) findViewById(R.id.motion_ing_link);
        mBleManager = new BleManager(this, this);
        mDeviceMap = new HashMap<>();

        mRlUploadBG = (RelativeLayout) findViewById(R.id.motion_upload_bg);
        mLLUploadBottom = (LinearLayout) findViewById(R.id.motion_update_bottom);
        mIvUploadLogo = (ImageView) findViewById(R.id.motion_update_logo);
        mUploadLogoAnimationDrawable = (AnimationDrawable) mIvUploadLogo.getBackground();
        mTvState = (TextView) findViewById(R.id.motion_update_text);
        mBtnUploadAbondom = (Button) findViewById(R.id.motion_update_abondom);
        mBtnUploadUpload = (Button) findViewById(R.id.motion_update_upload);

        mBtnRepeat = (Button) findViewById(R.id.repeat_scan_bluetooth_btn);
        initData();
    }

    private void setListener() {
        mBtnUploadAbondom.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                ToastUtils.shortToast(MotionUploadActivity.this, "下次可以在同步中心进行数据同步操作");
                finishing();
            }
        });
        mBtnUploadUpload.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                mMotionstate = MESSAGE_MOTION_STATE_SYNCHRO_DATA_ING;
                initData();
                syncSportData();
            }
        });
        mBtnRepeat.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                //开始连接流程---------------->
                mBtnRepeat.setVisibility(View.GONE);
                mMotionstate = MESSAGE_MOTION_STATE_CONNECTTION;
                initData();
                mBleManager.startScanLeDevice();
            }
        });
    }


    private void initData() {
        LogUtils.showLog("motioning", "initData mMotionstate:" + mMotionstate);
        //连接
        if (mMotionstate == MESSAGE_MOTION_STATE_CONNECTTION) {
            LogUtils.showLog("motioning", "initData MESSAGE_MOTION_STATE_CONNECTTION");
            mTVEndHint.setText("#蓝牙正在连接中，请耐心等待");
            mTVTopHint.setText("正在检索蓝牙");
            mTVLinkHint.setText("乔丹智能芯片尚未连接");
            //开始调用蓝牙连接
            if (checkLePermission()) {
                if (!mBleManager.isLeEnabled()) {
                    Intent start_le_enable = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(start_le_enable, REQUEST_CODE_LE);
                } else {
                    mBleManager.startManager();
                }
            }
            //连接绑定过的蓝牙
            //成功后跳转校准
        }
        //蓝牙设备连接中
        else if (mMotionstate == MESSAGE_MOTION_STATE_CONNECTTIONING) {
            LogUtils.showLog("motioning", "initData MESSAGE_MOTION_STATE_CONNECTTIONING");
            mTVEndHint.setText("#蓝牙正在连接中，请耐心等待");
            mTVTopHint.setText("正在连接蓝牙");
            mTVLinkHint.setText("乔丹智能芯片正在连接中");
        }
        //连接成功
        else if (mMotionstate == MESSAGE_MOTION_STATE_CONNECTTION_OK) {
            LogUtils.showLog("motioning", "initData MESSAGE_MOTION_STATE_CONNECTTION_OK");
            mTVEndHint.setText("#蓝牙连接成功，准备同步数据\nSN:" + mSN);
            mTVTopHint.setText("蓝牙连接成功");
            mTVLinkHint.setText("乔丹智能芯片链接正常");
        }
        //用户暂时未绑定蓝牙设备
        else if (mMotionstate == MESSAGE_MOTION_STATE_CONNECTTION_NO_BIND) {
            LogUtils.showLog("motioning", "initData MESSAGE_MOTION_STATE_CONNECTTION_NO_BIND");
            mTVEndHint.setText("#" + getResources().getString(R.string.common_please_bind_bluetooth_to_person_data));
            mTVTopHint.setText("蓝牙尚未绑定");
            mTVLinkHint.setText("乔丹智芯尚未链接");
            finishing();
        }
        //周边没有可以用的蓝牙设备
        else if (mMotionstate == MESSAGE_MOTION_STATE_CONNECTTION_GET_BLE_FALSE) {
            LogUtils.showLog("motioning", "initData MESSAGE_MOTION_STATE_CONNECTTION_GET_BLE_FALSE");
            mTVEndHint.setText("#设备离手机较远或者电量不足会导致链接失败");
            mTVTopHint.setText("蓝牙连接失败");
            mTVLinkHint.setText("乔丹智芯尚未链接");
            mBtnRepeat.setVisibility(View.VISIBLE);
        }
        //同步数据
        else if (mMotionstate == MESSAGE_MOTION_STATE_SYNCHRO_DATA) {
            LogUtils.showLog("motioning", "initData MESSAGE_MOTION_STATE_SYNCHRO_DATA");
            mRlUploadBG.setVisibility(View.VISIBLE);
            mLLUploadBottom.setVisibility(View.VISIBLE);
            mTvState.setText("是否芯片同步数据");

        } else if (mMotionstate == MESSAGE_MOTION_STATE_SYNCHRO_DATA_ING) {
            mRlUploadBG.setVisibility(View.VISIBLE);
            mLLUploadBottom.setVisibility(View.GONE);
            mTvState.setText("芯片同步数据中");
            //syncSportData();
        } else if (mMotionstate == MESSAGE_MOTION_STATE_UPLOAD_DATA) {
            mTVEndHint.setText("#数据正在上传中，请耐心等待\nSN:" + mSN);
            mTVTopHint.setText("正在上传数据");
            //遍历开始请求上传接口
            goMoveUpload();
        } else if (mMotionstate == MESSAGE_MOTION_STATE_OVER) {
            mTVEndHint.setText("#数据上传结束");
            mTVTopHint.setText("数据上传结束");
        } else if (mMotionstate == MESSAGE_MOTION_STATE_OVER_FALSE) {
            mTVEndHint.setText("#数据上传失败，请退出重新尝试");
            mTVTopHint.setText("数据上传失败");
        }
    }

    @Override
    public void onBleManagerReady(boolean is_ready) {
        LogUtils.showLog("motioning", "onBleManagerReady isready:" + is_ready);
        mBleManager.startScanLeDevice();
    }

    @Override
    public void onStartScanDevice(boolean is_success) {
        LogUtils.showLog("motioning", "onStartScanDevice is_success:" + is_success);
        if (null != mDeviceMap) {
            mDeviceMap.clear();
            mDeviceMap = null;
        }
        mDeviceMap = new HashMap<>();
    }

    @Override
    public void onStopScanDevice(boolean is_found) {
        LogUtils.showLog("motioning", "onStopScanDevice is_found:" + is_found);

        if (is_found) {
//            runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    mTVTopHint.setText("正在连接设备");
//                }
//            });
        } else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //查找蓝牙的列表（读取出来）
                    ArrayList<BluetoothData> mBluetoothList = DatabaseService.findBluetoothList(JordanApplication.getUsername(MotionUploadActivity.this));
                    if (mBluetoothList.size() == 0) {
                        ToastUtils.shortToast(MotionUploadActivity.this, R.string.common_please_bind_bluetooth_to_person_data);
                        mMotionstate = MESSAGE_MOTION_STATE_CONNECTTION_NO_BIND;
                        initData();
                        return;
                    }
                    //遍历mDeviceMap
//                    if (mDeviceMap.size() == 0) {
//                        ToastUtils.shortToast(MotionUploadActivity.this, R.string.common_no_scan_bluetooth);
//                        mMotionstate = MESSAGE_MOTION_STATE_CONNECTTION_GET_BLE_FALSE;
//                        initData();
//                        return;
//                    }
//                    for (String key : mDeviceMap.keySet()) {
//                        for (int i = 0; i < mBluetoothList.size(); i++) {
//                            LogUtils.showLog("motioning", "onStopScanDevice key：" + key);
//                            LogUtils.showLog("motioning", "onStopScanDevice key.replace(\":\",\"\")：" + key.replace(":", ""));
//                            LogUtils.showLog("motioning", "onStopScanDevice mBluetoothList.get(i).getMac()：" + mBluetoothList.get(i).getMac());
//                            //如果MAC相同，提取名称开始连接
//                            if (key.replace(":", "").equalsIgnoreCase(mBluetoothList.get(i).getMac())) {
//                                mMotionstate = MESSAGE_MOTION_STATE_CONNECTTIONING;
//                                initData();
//                                mSN = mBluetoothList.get(i).getSn();
//                                mMAC = mBluetoothList.get(i).getMac();
//                                isFindBluetooth=true;
//                                mBleManager.setDeviceInfo(mDeviceMap.get(key).getDeviceName(), mDeviceMap.get(key).getDeviceAddress());
//                                mBleManager.connectToDevice(mDeviceMap.get(key).getDeviceName(), mDeviceMap.get(key).getDeviceAddress());
//                                return;
//                            }
//                        }
//                    }
                    if (!isFindBluetooth) {
                        //未发现到绑定设备
                        //ToastUtils.shortToast(MotionUploadActivity.this, getResources().getString(R.string.common_no_scan_bluetooth));
                        mMotionstate = MESSAGE_MOTION_STATE_CONNECTTION_GET_BLE_FALSE;
                        initData();
                    }
                }
            });
        }
    }

    @Override
    public void onScanDevice(String device_address, String device_name, String device_class, byte[] broadcast_record) {
        if (!isFindBluetooth) {
            LogUtils.showLog("motioning", "onScanDevice address= " + device_address + " name= " + device_name + " class= " + device_class);
            BluetoothDeviceInfo current_device_info = new BluetoothDeviceInfo(device_name, device_address, device_class);
//            ArrayList<BluetoothData> mBluetoothList = DatabaseService.findBluetoothList(JordanApplication.getUsername(MotionUploadActivity.this));
//            for (int i = 0; i < mBluetoothList.size(); i++) {
//                if (device_address.replace(":", "").equalsIgnoreCase(mBluetoothList.get(i).getMac())) {
//                    isFindBluetooth = true;
//                    mMotionstate = MESSAGE_MOTION_STATE_CONNECTTIONING;
//                    initData();
//                    mDeviceName = current_device_info.getDeviceName();
//                    mMacAddress = current_device_info.getDeviceAddress();
//                    mSN = mBluetoothList.get(i).getSn();
//                    mMAC = mBluetoothList.get(i).getMac();
//                    mBleManager.setDeviceInfo(current_device_info.getDeviceName(), current_device_info.getDeviceAddress());
//                    mBleManager.connectToDevice(current_device_info.getDeviceName(), current_device_info.getDeviceAddress());
//                    return;
//                }
//            }
            String snAndMac = SettingSharedPerferencesUtil.GetChoiesBluetoothConfig(MotionUploadActivity.this, JordanApplication.getUsername(MotionUploadActivity.this));
            if (snAndMac.contains("|")) {
                mSN = snAndMac.substring(0, snAndMac.indexOf("|"));
                mMAC = snAndMac.substring(snAndMac.indexOf("|") + 1);
                if (device_address.replace(":", "").equalsIgnoreCase(mMAC)) {
                    isFindBluetooth = true;
                    mMotionstate = MESSAGE_MOTION_STATE_CONNECTTIONING;
                    initData();
                    mDeviceName = current_device_info.getDeviceName();
                    mMacAddress = current_device_info.getDeviceAddress();
                    mBleManager.setDeviceInfo(current_device_info.getDeviceName(), current_device_info.getDeviceAddress());
                    mBleManager.connectToDevice(current_device_info.getDeviceName(), current_device_info.getDeviceAddress());
                    return;
                }
            }
        }
    }

    @Override
    public void onConnectDevice(boolean is_success) {
        LogUtils.showLog("motioning", "onConnectDevice is_success= " + is_success);
        if (is_success) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //mTVBLeDeviceName.setText(TextUtils.isEmpty(mBleManager.getDeviceName()) ? "UnSettings" : mBleManager.getDeviceName());
                    mMotionstate = MESSAGE_MOTION_STATE_CONNECTTION_OK;
                    initData();
                    //自动开始校准
                    //通过回调
                }
            });
        } else {
            if (mDeviceName != null && !mDeviceName.equals("")
                    && mMacAddress != null && !mMacAddress.equals("") && isCanConnection
                    && mMotionstate < MESSAGE_MOTION_STATE_CONNECTTION_OK) {
                mHandler.sendEmptyMessageDelayed(HAS_DELAY_TIME, DELAY_TIME);
                //开始重连
                //重连方法
                mBleManager.setDeviceInfo(mDeviceName, mMacAddress);
                mBleManager.connectToDevice(mDeviceName, mMacAddress);
            } else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (!isFinish) {
                            ToastUtils.shortToast(MotionUploadActivity.this, "蓝牙断开请重新开始");
                            finishing();
                        } else {
                            mBleManager.unBindManagerService();
                            finish();
                        }
                    }
                });
            }
        }
    }

    @Override
    public void onInitialNotification(boolean is_success) {
        LogUtils.showLog("motioning", "onInitialNotification is_success= " + is_success);

        if (is_success) {
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
//                    openSwitcher();
                    mMotionstate = MESSAGE_MOTION_STATE_START;
                    //开始运动
                    startSport();
                }
            }, 5000);
        } else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ToastUtils.shortToast(MotionUploadActivity.this, "通知注册失败，请重新尝试");
                    finishing();
                }
            });
        }
    }

    @Override
    public void onReadCharacteristic(boolean is_success, String ch_uuid, byte[] ble_value) {

        LogUtils.showLog("motioning", "onReadCharacteristic is_success= "
                + is_success + " uuid= " + ch_uuid + " ble_value= " + Arrays.toString(ble_value)
                + " float_value= " + HexadecimalUtils.encodeHexStr(ble_value));
    }

    private static final String STATE_START_RESULT_HAS_DATA = "ff0301";
    private static final String STATE_START_RESULT_NO_DATA = "ff0300";
    private static final String STATE_CORRECT_RESULT_OK = "ff6903";
    private static final String STATE_CORRECT_RESULT_FALSE = "ff6900";
    private static final String STATE_SEND_USER_ID_OK = "ff02ff";
    private static final String STATE_START_SPORT_OK = "ff08ff";
    private static final String STATE_OVER_SPORT_OK = "ff07ff";
    private static final String STATE_UPLOAD_DATA_OK = "ff09ff";
    private static final String STATE_UPLOAD_DATA_OVER = "656e64";

    @Override
    public void onCharacteristicChange(boolean is_success, String ch_uuid, byte[] ble_value) {

        LogUtils.showLog("motioning", "onReadCharacteristic is_success= "
                + is_success + " uuid= " + ch_uuid + " ble_value= " + Arrays.toString(ble_value)
                + " float_value= " + HexadecimalUtils.encodeHexStr(ble_value));
        if (is_success) {

            if (mMotionstate == MESSAGE_MOTION_STATE_START && !isCorrect) {
                //对比HexadecimalUtils.encodeHexStr(ble_value)值
                LogUtils.showLog("motioning", "MESSAGE_MOTION_STATE_START");
                //ff0300  表示没有数据需要同步
                if (HexadecimalUtils.encodeHexStr(ble_value).equals(STATE_START_RESULT_NO_DATA)) {
                    isCorrect = true;
                    LogUtils.showLog("motioning", "没有数据需要同步");
                    //读取数据库
                    motionUploadDatas = DatabaseService.findAllMotionUploadData();
                    //有数据跳上传步骤
                    if (motionUploadDatas.size() > 0) {
                        LogUtils.showLog("motioning", "数据库中有内容");
                        //开始上传流程
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mMotionstate = MESSAGE_MOTION_STATE_UPLOAD_DATA;
                                initData();
                            }
                        });
                    }
                    //没有直接回退首页
                    else {
                        LogUtils.showLog("motioning", "数据库中没有内容");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ToastUtils.shortToast(MotionUploadActivity.this, "您暂时没有数据需要上传");
                                finishing();
                            }
                        });
                    }
                }
                //ff0301  表示有数据需要同步
                else if (HexadecimalUtils.encodeHexStr(ble_value).equals(STATE_START_RESULT_HAS_DATA)) {
                    LogUtils.showLog("motioning", "有数据需要同步");
                    //开始同步流程--->参照开始运动直接同步流程
                    //开始同步界面提示
                    mMotionstate = MESSAGE_MOTION_STATE_SYNCHRO_DATA;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            initData();
                        }
                    });
                }
            } else if (mMotionstate == MESSAGE_MOTION_STATE_SYNCHRO_DATA_ING) {
                if (HexadecimalUtils.encodeHexStr(ble_value).equals(STATE_UPLOAD_DATA_OK)) {
                    mStartData = "";
                    mDateByte = new byte[]{};
                    mDateByte = FileUtils.byteMerger(mDateByte, ble_value);
                } else {
                    //开始接受同步数据包
                    if (HexadecimalUtils.encodeHexStr(ble_value).equals(STATE_UPLOAD_DATA_OVER)) {
                        mDateByte = FileUtils.byteMerger(mDateByte, ble_value);
                        LogUtils.showLog("motioning", "MESSAGE_MOTION_STATE_UPLOAD_DATA：" + mStartData);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (mStartData.equals(STATE_UPLOAD_DATA_OVER)) {
                                    ToastUtils.shortToast(MotionUploadActivity.this, "您没有运动数据，请重新再试");
                                } else {
                                    mTVTopHint.setText("同步运动数据结束");
                                }
                                //开始计算内容
                                dataFormat(mStartData);
                                //读取数据库
                                motionUploadDatas = DatabaseService.findAllMotionUploadData();
                                //开始上传流程
                                mMotionstate = MESSAGE_MOTION_STATE_UPLOAD_DATA;
                                initData();
                            }
                        });

                    } else {
                        mDateByte = FileUtils.byteMerger(mDateByte, ble_value);
                        mStartData = mStartData + HexadecimalUtils.encodeHexStr(ble_value);
                    }
                }
            }
        }
    }

    @Override
    public void onWriteCharacteristic(boolean is_success, String ch_uuid, byte[] ble_value) {

        LogUtils.showLog("motioning", "onReadCharacteristic is_success= "
                + is_success + " uuid= " + ch_uuid + " ble_value= " + Arrays.toString(ble_value)
                + " float_value= " + HexadecimalUtils.encodeHexStr(ble_value));
        if (is_success) {
            if (ch_uuid.equals(SWITCH_CH_UUID)) {
                LogUtils.showLog("motioning", "ch_uuid.equals(\"SWITCH_CH_UUID\")");
//                mMotionstate = MESSAGE_MOTION_STATE_START;
//                //开始运动
//                startSport();
            }
        }
    }

    @Override
    public void onDescriptorWrite(boolean is_success, String ch_uuid) {

    }

    private static final String COMMAND_CH_UUID = "0883b03e-8535-b5a0-7140-a304d2495cba";//只写（命令）
    private static final String SWITCH_CH_UUID = "0883b03e-8535-b5a0-7140-a304d2495cb9";//开关通信前发1


    private static final String COMMAND_OPEN_SWITCHER = "0x01";
    private static final String COMMAND_START_SPORT = "0xff03";
    private static final String COMMAND_SEND_USERID = "0xff02";
    private static final String COMMAND_CALIBRATION = "0xff69";
    private static final String COMMAND_START_SPORT_REAL = "0xff08";
    private static final String COMMAND_STOP_SPORT = "0xff07";
    private static final String COMMAND_SYNC_SPORT_DATA = "0xff09";
    private boolean mIsOpenSuccess = false;

    /**
     * 打开开关
     */
    private void openSwitcher() {
        mIsOpenSuccess = mBleManager.writeUUid(SWITCH_CH_UUID, COMMAND_OPEN_SWITCHER);
        LogUtils.showLog("motioning", "openSwitcher::result= " + mIsOpenSuccess);
    }

    /**
     * 开始运动
     */
    private void startSport() {
        LogUtils.showLog("motioning", "startSport ");
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mTVTopHint.setText("检测芯片运动数据中");
            }
        });
        mBleManager.writeUUid(COMMAND_CH_UUID, COMMAND_START_SPORT);
        //mBtnMotionOver.setVisibility(View.VISIBLE);
    }

    /**
     * 同步运动数据
     */
    private void syncSportData() {
        LogUtils.showLog("motioning", "syncSportData ");
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mTVTopHint.setText("同步运动数据");
            }
        });
        mBleManager.writeUUid(COMMAND_CH_UUID, COMMAND_SYNC_SPORT_DATA);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mBleLogoAnimationDrawable.start();
        mUploadLogoAnimationDrawable.start();
    }

    @Override
    protected void onDestroy() {
        mBleLogoAnimationDrawable.stop();
        mUploadLogoAnimationDrawable.stop();
        super.onDestroy();
        //mBleManager.unBindManagerService();
    }

    public void finishing() {
        if (mBleManager.isConnectToDevice()) {
            isFinish = true;
            mBleManager.destroyManager();
        } else {
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        finishing();
        //super.onBackPressed();
    }


    /**
     * 数据转化
     */
    private boolean dataFormat(String data) {

        String header = "";
        mTrakMap = new HashMap<String, TrakPointData>();
        mJumpMap = new HashMap<String, JumpPointData>();
        mAllArray = new ArrayList<String>();
        if (data.length() < 200) {
            ToastUtils.shortToast(MotionUploadActivity.this, "芯片没有运动数据，请重新开始运动");
            finishing();
            return false;
        }
        if (data.contains("fffffffffffff")) {
            ToastUtils.shortToast(MotionUploadActivity.this, "芯片运动数据异常，请重新开始运动");
            finishing();
            return false;
        }
        header = data.substring(0, 200);
        LogUtils.showLog("dataFormat", "byteToFile start: ");
        SimpleDateFormat simFor = new SimpleDateFormat("yyyyMMddhhmmss");
        String filePath = Environment.getExternalStorageDirectory().getPath() + "/qiaodan/" +
                JordanApplication.getUsername(MotionUploadActivity.this) + "_" + simFor.format(new Date()) + ".txt";
        LogUtils.showLog("dataFormat", "filePath: " + filePath);
        FileUtils.byteToFile(mDateByte, filePath);
        LogUtils.showLog("dataFormat", "byteToFile over: ");
        //会员ID
        //JordanApplication.vipId;
        String vipID = HexadecimalUtils.hexStr2Str(data.substring(96, 160));
        //String vipID =JordanApplication.vipId;
        LogUtils.showLog("dataFormat", "用户会员ID: " + JordanApplication.getVipID(MotionUploadActivity.this));
        LogUtils.showLog("dataFormat", "芯片会员ID: " + vipID);
        //蓝牙SN号
        //mSN;
        LogUtils.showLog("dataFormat", "蓝牙SN号: " + mSN);
        //左右脚 R右脚 L左脚
        //DEFAULT_FOOTER;
        String foot = DEFAULT_RIGHT_FOOTER;
        String bluetoothFoot = SettingSharedPerferencesUtil.GetBluetoothFootValueConfig(MotionUploadActivity.this, JordanApplication.getUsername(MotionUploadActivity.this));
        if (bluetoothFoot.equals("左脚")) {
            foot = DEFAULT_LEFT_FOOTER;
        } else {
            foot = DEFAULT_RIGHT_FOOTER;
        }
        LogUtils.showLog("dataFormat", "左右脚: " + foot);
        //经度
        //JordanApplication.nowLongitude;
        String longitude = String.valueOf(HexadecimalUtils.getDouble(
                HexadecimalUtils.hex2Byte(data.substring(168, 184))));
        LogUtils.showLog("dataFormat", "经度: " + JordanApplication.nowLongitude);
        LogUtils.showLog("dataFormat", "芯片经度: " + longitude);
        LogUtils.showLog("dataFormat", "芯片经度String: " + data.substring(168, 184));
        //纬度
        //JordanApplication.nowLatitude;
        String latitude = String.valueOf(HexadecimalUtils.getDouble(
                HexadecimalUtils.hex2Byte(data.substring(184, 200))));
        LogUtils.showLog("dataFormat", "纬度: " + JordanApplication.nowLatitude);
        LogUtils.showLog("dataFormat", "芯片纬度: " + latitude);
        LogUtils.showLog("dataFormat", "芯片纬度String: " + data.substring(184, 200));
        //地址
        //mAddress
        LogUtils.showLog("dataFormat", "地址: " + mAddress);
        //开始时间(时间戳)
        //startTime;
        startTime = String.valueOf(HexadecimalUtils.formatLongData(data.substring(160, 168)));
        LogUtils.showLog("dataFormat", "开始时间(时间戳): " + startTime);
        //手感
        //mHandle = "2";
        LogUtils.showLog("dataFormat", "手感: " + mHandle);
        //图片
        //mStadiumPicture = "";
        LogUtils.showLog("dataFormat", "图片: " + mStadiumPicture);
        //结束时间(时间戳)
        //总距离 需要去掉负数~
        float totalDist = Math.abs(HexadecimalUtils.formatFloatData(data.substring(48, 56)));
        LogUtils.showLog("dataFormat", "总距离: " + totalDist);
        //总步数
        long totalStep = HexadecimalUtils.formatLongData(data.substring(0, 8));
        LogUtils.showLog("dataFormat", "总步数: " + totalStep);
        //横向总距离 需要去掉负数~
        float totalHorDist = Math.abs(HexadecimalUtils.formatFloatData(data.substring(56, 64)));
        LogUtils.showLog("dataFormat", "横向距离: " + totalHorDist);
        //纵向总距离
        //总距离-横向距离
        float totalVerDist = totalDist - totalHorDist;
        LogUtils.showLog("dataFormat", "纵向距离: " + totalVerDist);
        //纵跳次数
        long verJumpCount = HexadecimalUtils.formatLongData(data.substring(88, 96));
        LogUtils.showLog("dataFormat", "纵跳次数: " + verJumpCount);
        String lastData = data.substring(200);
        LogUtils.showLog("motioning", "剩余参数: " + lastData);
        for (int i = lastData.length(); lastData.length() >= 40; i = i - 40) {
            LogUtils.showLog("motionings", "i:" + i);
            LogUtils.showLog("motionings", "lastData.length:" + lastData.length());
            //先截取所有的
            mAllArray.add(lastData.substring(0, 40));
            if (lastData.length() == 40) {
                lastData = "";
            } else {
                //然后再分类
                lastData = lastData.substring(40);
            }
        }
        LogUtils.showLog("motionings", "mAllMap:" + mAllArray.size());
        //纵跳点(纵跳的高度的集合，以”,”分隔)
        String jump = "";
        //运动轨迹（运动坐标集合，以”,”分隔）
        String tark = "";
        float beforetime = 0;
        float maxSpeed = 0;
        float tarktime = 0;
        for (int i = 0; i < mAllArray.size(); i++) {
            String key = mAllArray.get(i);
            if (key.contains("6a756d70")) {
                JumpPointData jumpPointData = new JumpPointData();
                jumpPointData.setHangTime(HexadecimalUtils.formatFloatData(key.substring(8, 16)) / 1000);
                jumpPointData.setJumpHegiht(HexadecimalUtils.formatFloatData(key.substring(16, 24)));
                jumpPointData.setFlipAngle(HexadecimalUtils.formatFloatData(key.substring(24, 32)));
                if (HexadecimalUtils.formatFloatData(key.substring(24, 32)) > 0) {
                    jumpPointData.setFlipAngleState(1);
                } else if (HexadecimalUtils.formatFloatData(key.substring(24, 32)) < 0) {
                    jumpPointData.setFlipAngleState(-1);
                } else {
                    jumpPointData.setFlipAngleState(0);
                }
                jumpPointData.setTime(HexadecimalUtils.formatLongData(key.substring(32, 40)) * 1000 / 512);
                LogUtils.showLog("motionings", "jumpPointData:" + jumpPointData.toString());
                mJumpMap.put(key, jumpPointData);
                if (!jump.equals("")) {
                    jump = jump + "," + key;
                } else {
                    jump = key;
                }
            } else if (key.contains("7472616b")) {
                //坐标解析
                TrakPointData trakPointData = new TrakPointData();
                trakPointData.setxCoordinate(HexadecimalUtils.formatFloatData(key.substring(8, 16)));
                trakPointData.setyCoordinate(HexadecimalUtils.formatFloatData(key.substring(16, 24)));
                trakPointData.setStep(HexadecimalUtils.formatFloatData(key.substring(24, 32)));
                trakPointData.setTime(HexadecimalUtils.formatLongData(key.substring(32, 40)) * 1000 / 512);
                float time = 0;
                if (beforetime != 0) {
                    time = (HexadecimalUtils.formatLongData(key.substring(32, 40)) * 1000 / 512) - beforetime;
                    if (time > 1500)
                        time = (float) 1500;
                    if (time < 300)
                        time = (float) 300;
                    if (time > 0) {
                        tarktime = tarktime + time;
                        float speed = HexadecimalUtils.formatFloatData(key.substring(24, 32)) * 2 * 1000 / time;
                        if (maxSpeed < speed) {
                            maxSpeed = speed;
                        }
                    }
                }
                if (time >= 0) {
                    beforetime = HexadecimalUtils.formatLongData(key.substring(32, 40)) * 1000 / 512;
                    LogUtils.showLog("motionings", "HexadecimalUtils.formatLongData(key.substring(32,40)):" + HexadecimalUtils.formatLongData(key.substring(32, 40)));
                    LogUtils.showLog("motionings", "trakPointData:" + trakPointData.toString());
                    mTrakMap.put(key, trakPointData);
                    if (!tark.equals("")) {
                        tark = tark + "," + key;
                    } else {
                        tark = key;
                    }
                }
            }
        }
        LogUtils.showLog("motionings", "mTrakMap:" + mTrakMap.size());
        LogUtils.showLog("motionings", "mJumpMap:" + mJumpMap.size());
        LogUtils.showLog("dataFormat", "tark:" + tark);
        LogUtils.showLog("dataFormat", "jump:" + jump);
        //轨迹点分析
        double step = 0;
        double totalTime = 0;
        double maxTime = 0;
        double minTime = 0;
        for (String key : mTrakMap.keySet()) {
            TrakPointData trakPointData = mTrakMap.get(key);
            double time = trakPointData.getTime();
            step = step + trakPointData.getStep();
            if (time > maxTime) {
                maxTime = time;
            }
            if (minTime == 0 || time < minTime) {
                minTime = time;
            }
        }
        //平均移动速度
        double avgSpeed = 0;
        if (tarktime != 0)
            avgSpeed = step * 2 * 1000 / tarktime;
        LogUtils.showLog("dataFormat", "最大时间: " + maxTime);
        LogUtils.showLog("dataFormat", "最小时间: " + minTime);
        //冲向次数--横向
        long spurtCount = HexadecimalUtils.formatLongData(data.substring(40, 48));
        LogUtils.showLog("dataFormat", "横向冲刺次数: " + spurtCount);
        //运动时长
        //总跑次数*每次跑的单位时间+冲刺数量*每次冲刺的单位时间
        long totalRunCount = HexadecimalUtils.formatLongData(data.substring(8, 16));
        LogUtils.showLog("dataFormat", "跑的次数: " + totalRunCount);
        long totalRunUnitTime = 360;
        long totalSprintCount = HexadecimalUtils.formatLongData(data.substring(16, 24));
        LogUtils.showLog("dataFormat", "冲刺次数: " + totalSprintCount);
        long totalSprintUnitTime = 167;
        double totalRate = (totalRunCount * totalRunUnitTime + totalSprintCount * totalSprintUnitTime) / 1000;
        long totalStepUnitTime = 750;
        double spend = ((totalStep - totalRunCount - totalSprintCount) * totalStepUnitTime) / 1000 + totalRate;

        long spurtStepCount = HexadecimalUtils.formatLongData(data.substring(24, 32));
        //总时间-可能存在芯片终结运动的问题，另外如果换个手机或者清掉了缓存没法计算
        if (endTime == null || endTime.equals("")) {
            LogUtils.showLog("dataFormat", "运动之前的同步");
            //读取缓存
            //如果还是没有直接读取数据
            endTime = SettingSharedPerferencesUtil.GetOverMotionTimeValueConfig
                    (MotionUploadActivity.this, mSN, JordanApplication.getUsername(MotionUploadActivity.this));
            if (endTime == null || endTime.equals("")) {
                //缓存没有运动时间拿芯片时间
                totalTime = (maxTime - minTime) / 1000;
                endTime = String.valueOf(Long.valueOf(startTime) + ((int) spend));
            } else {
                //缓存有时间
                if (Long.valueOf(endTime) <= Long.valueOf(startTime)) {
                    LogUtils.showLog("dataFormat", "结束运动时间不正常");
                    totalTime = (maxTime - minTime) / 1000;
                    endTime = String.valueOf(Long.valueOf(startTime) + ((int) spend));
                } else {
                    LogUtils.showLog("dataFormat", "结束运动时间正常");
                    totalTime = Long.valueOf(endTime) - Long.valueOf(startTime);
                }
            }
        } else {
            totalTime = Long.valueOf(endTime) - Long.valueOf(startTime);
            LogUtils.showLog("dataFormat", "运动之后的同步");
        }
        //String.valueOf(Long.valueOf(endTime)-Long.valueOf(startTime));
        LogUtils.showLog("dataFormat", "总时间: " + totalTime);
        //活跃时间占比
        double activeRate = 0;
        if ((totalStep + mJumpMap.size()) != 0)
            activeRate = Double.valueOf(totalRunCount + totalSprintCount + mJumpMap.size() + spurtStepCount - spurtCount)
                    / (totalStep + mJumpMap.size());
        totalRate = totalTime * activeRate;
        LogUtils.showLog("dataFormat", "跑的次数: " + totalRunCount);
        LogUtils.showLog("dataFormat", "冲刺次数: " + totalSprintCount);
        LogUtils.showLog("dataFormat", "跳的次数: " + mJumpMap.size());
        LogUtils.showLog("dataFormat", "横向步数: " + spurtStepCount);
        LogUtils.showLog("dataFormat", "横冲次数: " + spurtCount);
        LogUtils.showLog("dataFormat", "总步次数: " + totalStep);

        LogUtils.showLog("dataFormat", "活跃时间占比: " + activeRate);
        LogUtils.showLog("dataFormat", "走的次数: " + (totalStep - totalRunCount - totalSprintCount));
        LogUtils.showLog("dataFormat", "运动时长: " + spend);
        //活跃总时间
        //总跑次数*每次跑的单位时间+冲刺数量*每次冲刺的单位时间
        LogUtils.showLog("dataFormat", "活跃总时间: " + totalRate);
        LogUtils.showLog("dataFormat", "平均移动速度: " + avgSpeed);
        //最大移动速度（m/s）
        //float maxSpeed = HexadecimalUtils.formatFloatData(data.substring(64, 72));
        LogUtils.showLog("dataFormat", "最大移动速度: " + maxSpeed);
        //变向次数
        long breakinCount = HexadecimalUtils.formatLongData(data.substring(72, 80));
        LogUtils.showLog("dataFormat", "变向次数: " + breakinCount);
        //变向平均触底时间（秒）
        double breakinAvgTime = Double.valueOf(HexadecimalUtils.formatLongData(data.substring(80, 88))) / 1000;
        LogUtils.showLog("dataFormat", "变向平均触底时间================string: " + data.substring(80, 88));
        LogUtils.showLog("dataFormat", "变向平均触底时间================ms: " + HexadecimalUtils.formatLongData(data.substring(80, 88)));
        LogUtils.showLog("dataFormat", "变向平均触底时间================: " + breakinAvgTime);
        LogUtils.showLog("dataFormat", "变向平均触底时间: " + breakinAvgTime);
        //纵跳点：
        //纵跳来分析
        float jumpHeight = 0;
        float hangTime = 0;
        float zFlipAngle = 0;
        int zFlipAngleSize = 0;
        float fFlipAngle = 0;
        int fFlipAngleSize = 0;
        int mTouchType = 0;
        //纵跳最大高度
        float mVerJumpMaxHigh = 0;
        for (String key : mJumpMap.keySet()) {
            JumpPointData jumpPointData = mJumpMap.get(key);
            jumpHeight += jumpPointData.getJumpHegiht();
            hangTime += jumpPointData.getHangTime();
            LogUtils.showLog("dataFormat", "滞空时间================: " + jumpPointData.getHangTime());
            LogUtils.showLog("dataFormat", "滞空高度================: " + jumpPointData.getJumpHegiht());
            LogUtils.showLog("dataFormat", "反转角================: " + jumpPointData.getFlipAngle());
            mTouchType += jumpPointData.getFlipAngleState();
            LogUtils.showLog("dataFormat", "jumpPointData.toString: " + jumpPointData.toString());
            if (jumpPointData.getFlipAngleState() > 0) {
                zFlipAngle += jumpPointData.getFlipAngle();
                zFlipAngleSize = zFlipAngleSize + 1;
            } else if (jumpPointData.getFlipAngleState() < 0) {
                fFlipAngle += jumpPointData.getFlipAngle();
                fFlipAngleSize = fFlipAngleSize + 1;
            }
            if (jumpPointData.getJumpHegiht() > mVerJumpMaxHigh) {
                mVerJumpMaxHigh = jumpPointData.getJumpHegiht();
            }
        }
        LogUtils.showLog("dataFormat", "zFlipAngle: " + zFlipAngle);
        LogUtils.showLog("dataFormat", "zFlipAngleSize: " + zFlipAngleSize);
        LogUtils.showLog("dataFormat", "fFlipAngle: " + fFlipAngle);
        LogUtils.showLog("dataFormat", "fFlipAngleSize: " + fFlipAngleSize);
        float mVerJumpAvgHigh = 0;//纵跳平均高度
        if (mJumpMap.size() != 0)
            mVerJumpAvgHigh = jumpHeight / mJumpMap.size();
        LogUtils.showLog("dataFormat", "纵跳次数: " + mJumpMap.size());
        LogUtils.showLog("dataFormat", "纵跳平均高度: " + mVerJumpAvgHigh);
        LogUtils.showLog("dataFormat", "纵跳最大高度: " + mVerJumpMaxHigh);
        float mAvgHoverTime = 0;//平均滞空时间
        if (mJumpMap.size() != 0)
            mAvgHoverTime = hangTime / mJumpMap.size();
        LogUtils.showLog("dataFormat", "平均滞空时间: " + mAvgHoverTime);
        float mAvgTouchAngle = 0;//平均着地旋转角
        //暂定是右脚
        LogUtils.showLog("dataFormat", "着地类型: " + mTouchType);
        //着地类型 int 1内翻 -1外翻（右脚）1外翻 -1内翻（左脚）
        if (mTouchType > 0) {
            mTouchType = 1;
            if (zFlipAngleSize != 0)
                mAvgTouchAngle = zFlipAngle / zFlipAngleSize;
            LogUtils.showLog("dataFormat", "着地类型: 内翻");
        } else if (mTouchType < 0) {
            if (fFlipAngleSize != 0)
                mAvgTouchAngle = fFlipAngle / fFlipAngleSize;
            mTouchType = -1;
            LogUtils.showLog("dataFormat", "着地类型: 外翻");
        } else {
            mTouchType = 0;
            LogUtils.showLog("dataFormat", "着地类型: 正常");
        }
        //暂定是右脚
        LogUtils.showLog("dataFormat", "着地类型后: " + mTouchType);
        LogUtils.showLog("dataFormat", "平均着地旋转角: " + mAvgTouchAngle);

        //活跃占比 activeRate*100
        //平均移动速度 avgSpeed*100 / 10m/s
        double activeRateValue = 0;
        if ((totalStep) != 0)
            activeRateValue = Double.valueOf(totalRunCount + totalSprintCount + spurtStepCount - spurtCount)
                    / (totalStep);
        double maxAvgSpeed = 2.8;
        double activeRateS = activeRateValue * 100;
        if (activeRateS > 100) activeRateS = 100;
        double avgSpeedS = avgSpeed * 100 / maxAvgSpeed;
        if (avgSpeedS > 100) avgSpeedS = 100;
        int runRank = (int) ((activeRateS + avgSpeedS) / 2);
        if (runRank > 100) runRank = 100;
        String mRunRank = String.valueOf(runRank);//跑动等级
        LogUtils.showLog("dataFormat", "活跃占比分值: " + activeRateS);
        LogUtils.showLog("dataFormat", "平均移动速度分值: " + avgSpeedS);
        LogUtils.showLog("dataFormat", "跑动等级: " + mRunRank);

        //变向次数/小时 breakinCount * 3600  /总时间totalTime
        double maxBreakinCount = 600;
        double breakinCountS = 0;
        if (totalTime != 0) {
            breakinCountS = (breakinCount * 3600 * 100) / (totalTime * 1000 * maxBreakinCount);
        }
        if (breakinCountS > 100) breakinCountS = 100;
        //变向平触地时间 breakinAvgTime
        double breakinAvgTimeS = 0;
        if (breakinAvgTime <= 0) {
            breakinAvgTimeS = 0;
        } else if (breakinAvgTime <= 0.15) {
            breakinAvgTimeS = 100;
        } else if (breakinAvgTime <= 1) {
            breakinAvgTimeS = ((1 - breakinAvgTime) / 0.85) * 100;
        } else if (breakinAvgTime > 1) {
            breakinAvgTimeS = 0;
        }
        if (breakinAvgTimeS > 100) breakinAvgTimeS = 100;
        int breakRank = (int) ((breakinCountS + breakinAvgTimeS) / 2);
        if (breakRank > 100) breakRank = 100;
        String mBreakRank = String.valueOf(breakRank);//突破等级
        LogUtils.showLog("dataFormat", "变向次数分值: " + breakinCountS);
        LogUtils.showLog("dataFormat", "变向平触地时间分值: " + breakinAvgTimeS);
        LogUtils.showLog("dataFormat", "突破等级: " + mBreakRank);

        //平均滞空时间 mAvgHoverTime
        double maxAvgHoverTime = 1;
        double mAvgHoverTimeS = 0;
        mAvgHoverTimeS = mAvgHoverTime * 100 / maxAvgHoverTime;
        if (mAvgHoverTimeS > 100) mAvgHoverTimeS = 100;
        //平均纵跳高度 mVerJumpAvgHigh
        double maxVerJumpAvgHigh = 1.23;
        double mVerJumpAvgHighS = 0;
        mVerJumpAvgHighS = mVerJumpAvgHigh * 100 / maxVerJumpAvgHigh;
        if (mVerJumpAvgHighS > 100) mVerJumpAvgHighS = 100;
        int bounceRank = (int) ((mAvgHoverTimeS + mVerJumpAvgHighS) / 2);
        if (bounceRank > 100) bounceRank = 100;
        String mBounceRank = String.valueOf(bounceRank);//弹跳等级
        LogUtils.showLog("dataFormat", "平均滞空时间分值: " + mAvgHoverTimeS);
        LogUtils.showLog("dataFormat", "平均纵跳高度分值: " + mVerJumpAvgHighS);
        LogUtils.showLog("dataFormat", "弹跳等级: " + mBounceRank);

        //如何计算
        int perfRank = (runRank + breakRank + bounceRank) / 3;
        String mPerfRank = String.valueOf(perfRank);//本场表现
        LogUtils.showLog("dataFormat", "本场表现: " + mPerfRank);

        //消耗卡路里
        double weight = 60;
        UserInfoData userInfoData = DatabaseService.findUserInfo(JordanApplication.getUsername(MotionUploadActivity.this));
        if (userInfoData != null) {
            if (userInfoData.getWeight() != null && !userInfoData.getWeight().equals("")) {
                weight = Double.valueOf(userInfoData.getWeight());
            }
        }
        //跑步热量（cal）＝体重（kg）×距离（公里）×1036 cal
        double totalRunUnitCalorie = weight * (totalDist / 1000) * 1.036;
        //弹跳热量（cal）＝体重（kg）×弹跳时间×660 cal
        double totalJumpCalorie = weight * (hangTime / 3600) * 660;
        double mCalorie = totalRunUnitCalorie + totalJumpCalorie;
        LogUtils.showLog("dataFormat", "体重: " + weight);
        LogUtils.showLog("dataFormat", "距离（公里）: " + (totalDist / 1000));
        LogUtils.showLog("dataFormat", "跑步热量: " + totalRunUnitCalorie);
        LogUtils.showLog("dataFormat", "弹跳时间(小时): " + (hangTime / 3600));
        LogUtils.showLog("dataFormat", "弹跳热量: " + totalJumpCalorie);
        LogUtils.showLog("dataFormat", "消耗卡路里: " + mCalorie);

        //没法算
        String mAvgShotDist = "5";//平均出手距离
        LogUtils.showLog("dataFormat", "平均出手距离: " + mAvgShotDist);
        String mMaxShotDist = "5";//最大出手距离
        LogUtils.showLog("dataFormat", "最大出手距离: " + mMaxShotDist);
        //存储到数据库
        //接口请求放到外面同步去做
//        moveUpload(JordanApplication.vipId, mSN, DEFAULT_FOOTER, String.valueOf(JordanApplication.nowLongitude),
//                String.valueOf(JordanApplication.nowLatitude), mAddress,
//                startTime, String.valueOf(totalRate), mStadiumPicture,
//                String.valueOf(Long.valueOf(startTime) + totalRate),
//                new BigDecimal(totalDist).setScale(2, BigDecimal.ROUND_DOWN).toString(),
//                String.valueOf(totalStep), new BigDecimal(totalHorDist).setScale(2, BigDecimal.ROUND_DOWN).toString(),
//                new BigDecimal(totalVerDist).setScale(2, BigDecimal.ROUND_DOWN).toString(),
//                new BigDecimal(totalTime).setScale(2, BigDecimal.ROUND_DOWN).toString(),
//                new BigDecimal(totalRate).setScale(2, BigDecimal.ROUND_DOWN).toString(),
//                new BigDecimal(activeRate).setScale(2, BigDecimal.ROUND_DOWN).toString(),
//                new BigDecimal(avgSpeed).setScale(2, BigDecimal.ROUND_DOWN).toString(),
//                new BigDecimal(maxSpeed).setScale(2, BigDecimal.ROUND_DOWN).toString(),
//                String.valueOf(spurtCount), String.valueOf(breakinCount),
//                new BigDecimal(breakinAvgTime).setScale(2, BigDecimal.ROUND_DOWN).toString(),
//                jump, String.valueOf(verJumpCount),
//                new BigDecimal(mVerJumpAvgHigh).setScale(2, BigDecimal.ROUND_DOWN).toString(),
//                new BigDecimal(mVerJumpMaxHigh).setScale(2, BigDecimal.ROUND_DOWN).toString(),
//                new BigDecimal(mAvgHoverTime).setScale(2, BigDecimal.ROUND_DOWN).toString(),
//                new BigDecimal(mAvgTouchAngle).setScale(2, BigDecimal.ROUND_DOWN).toString(),
//                String.valueOf(mTouchType), String.valueOf(mPerfRank), String.valueOf(mRunRank),
//                String.valueOf(mBreakRank), String.valueOf(mBounceRank), String.valueOf(mAvgShotDist),
//                String.valueOf(mMaxShotDist), String.valueOf(mHandle), String.valueOf(mCalorie), tark);
        //endTime;
        //endTime = String.valueOf(Long.valueOf(startTime) +  ((int)spend));
        LogUtils.showLog("dataFormat", "(int)spend): " + (int) spend);
        LogUtils.showLog("dataFormat", "结束时间(时间戳): " + endTime);
        return DatabaseService.createMotionBluetoothData(vipID, mSN, foot, longitude,
                latitude, mAddress,
                startTime, String.valueOf(spend), mStadiumPicture,
                endTime,
                new BigDecimal(totalDist).setScale(2, BigDecimal.ROUND_DOWN).toString(),
                String.valueOf(totalStep), new BigDecimal(totalHorDist).setScale(2, BigDecimal.ROUND_DOWN).toString(),
                new BigDecimal(totalVerDist).setScale(2, BigDecimal.ROUND_DOWN).toString(),
                new BigDecimal(totalTime).setScale(2, BigDecimal.ROUND_DOWN).toString(),
                new BigDecimal(totalRate).setScale(2, BigDecimal.ROUND_DOWN).toString(),
                new BigDecimal(activeRate).setScale(2, BigDecimal.ROUND_DOWN).toString(),
                new BigDecimal(avgSpeed).setScale(2, BigDecimal.ROUND_DOWN).toString(),
                new BigDecimal(maxSpeed).setScale(2, BigDecimal.ROUND_DOWN).toString(),
                String.valueOf(totalSprintCount), String.valueOf(breakinCount),
                new BigDecimal(breakinAvgTime).setScale(2, BigDecimal.ROUND_DOWN).toString(),
                jump, String.valueOf(verJumpCount),
                new BigDecimal(mVerJumpAvgHigh).setScale(2, BigDecimal.ROUND_DOWN).toString(),
                new BigDecimal(mVerJumpMaxHigh).setScale(2, BigDecimal.ROUND_DOWN).toString(),
                new BigDecimal(mAvgHoverTime).setScale(2, BigDecimal.ROUND_DOWN).toString(),
                new BigDecimal(mAvgTouchAngle).setScale(2, BigDecimal.ROUND_DOWN).toString(),
                String.valueOf(mTouchType), String.valueOf(mPerfRank), String.valueOf(mRunRank),
                String.valueOf(mBreakRank), String.valueOf(mBounceRank), String.valueOf(mAvgShotDist),
                String.valueOf(mMaxShotDist), String.valueOf(mHandle),
                new BigDecimal(mCalorie).setScale(2, BigDecimal.ROUND_DOWN).toString(), tark,
                SettingSharedPerferencesUtil.GetBallTypeValueConfig(MotionUploadActivity.this, JordanApplication.getUsername(MotionUploadActivity.this)), header, "水泥");
    }


    public void goMoveUpload() {
        ArrayList<MoveUploadInfo> list = new ArrayList<MoveUploadInfo>();
        if (motionUploadDatas.size() == 0) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ToastUtils.shortToast(MotionUploadActivity.this, "您暂时没有数据需要上传");
                    finishing();
                }
            });
            return;
        }
        for (String key : motionUploadDatas.keySet()) {
            MoveUploadInfo moveUploadInfo = new MoveUploadInfo();
            MotionUploadData motionUploadData = motionUploadDatas.get(key);
            moveUploadInfo.setmActiveRate(motionUploadData.getActiveRate());
            moveUploadInfo.setmVipId(motionUploadData.getVipId());
            moveUploadInfo.setmSn(motionUploadData.getSn());
            moveUploadInfo.setmFooter(motionUploadData.getFooter());
            moveUploadInfo.setmLongitude(motionUploadData.getLongitude());
            moveUploadInfo.setmLatitude(motionUploadData.getLatitude());
            moveUploadInfo.setmAddress(motionUploadData.getAddress());
            moveUploadInfo.setmBeginTime(motionUploadData.getBeginTime());
            moveUploadInfo.setmSpend(motionUploadData.getSpend());
            moveUploadInfo.setmPicture(motionUploadData.getPicture());
            moveUploadInfo.setmEndTime(motionUploadData.getEndTime());
            moveUploadInfo.setmTotalDist(motionUploadData.getTotalDist());
            moveUploadInfo.setmTotalStep(motionUploadData.getTotalStep());
            moveUploadInfo.setmTotalHorDist(motionUploadData.getTotalHorDist());
            moveUploadInfo.setmTotalVerDist(motionUploadData.getTotalVerDist());
            moveUploadInfo.setmTotalTime(motionUploadData.getTotalTime());
            moveUploadInfo.setmTotalActiveTime(motionUploadData.getTotalActiveTime());
            moveUploadInfo.setmActiveRate(motionUploadData.getActiveRate());
            moveUploadInfo.setmAvgSpeed(motionUploadData.getAvgSpeed());
            moveUploadInfo.setmMaxSpeed(motionUploadData.getMaxSpeed());
            moveUploadInfo.setmSpurtCount(motionUploadData.getSpurtCount());
            moveUploadInfo.setmBreakinCount(motionUploadData.getBreakinCount());
            moveUploadInfo.setmBreakinAvgTime(motionUploadData.getBreakinAvgTime());
            moveUploadInfo.setmVerJumpPoint(motionUploadData.getVerJumpPoint());
            moveUploadInfo.setmVerJumpCount(motionUploadData.getVerJumpCount());
            moveUploadInfo.setmVerJumpAvgHigh(motionUploadData.getVerJumpAvgHigh());
            moveUploadInfo.setmVerJumpMaxHigh(motionUploadData.getVerJumpMaxHigh());
            moveUploadInfo.setmAvgHoverTime(motionUploadData.getAvgHoverTime());
            moveUploadInfo.setmAvgTouchAngle(motionUploadData.getAvgTouchAngle());
            moveUploadInfo.setmTouchType(motionUploadData.getTouchType());
            moveUploadInfo.setmPerfRank(motionUploadData.getPerfRank());
            moveUploadInfo.setmRunRank(motionUploadData.getRunRank());
            moveUploadInfo.setmBreakRank(motionUploadData.getBreakRank());
            moveUploadInfo.setmBounceRank(motionUploadData.getBounceRank());
            moveUploadInfo.setmAvgShotDist(motionUploadData.getAvgShotDist());
            moveUploadInfo.setmMaxShotDist(motionUploadData.getMaxShotDist());
            moveUploadInfo.setmHandle(motionUploadData.getHandle());
            moveUploadInfo.setmCalorie(motionUploadData.getCalorie());
            moveUploadInfo.setmTrail(motionUploadData.getTrail());
            moveUploadInfo.setmBallType(motionUploadData.getBallType());
            moveUploadInfo.setmHeader(motionUploadData.getHeader());
            moveUploadInfo.setmStadiumType(motionUploadData.getStadiumType());
            list.add(moveUploadInfo);
        }

        //上传多条
        moveUploads(list);
    }

    private void moveUploads(ArrayList<MoveUploadInfo> list) {
        userManager.moveUploads(list);
    }

}
