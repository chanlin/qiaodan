package com.jordan.project.activities.main;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jordan.commonlibrary.CommonManager;
import com.safari.httplibs.config.InnerMessageConfig;
import com.jordan.project.JordanApplication;
import com.jordan.project.R;
import com.jordan.project.activities.LoginActivity;
import com.jordan.project.activities.adapter.PathOfParticleAdapter;
import com.jordan.project.activities.adapter.PlayBallListAdapter;
import com.jordan.project.activities.ball.NearbyStadiumActivity;
import com.jordan.project.activities.ball.PlayBallDetailActivity;
import com.jordan.project.activities.ball.PlayBallListActivity;
import com.jordan.project.activities.ble.BindBluetoothActivity;
import com.jordan.project.activities.motion.MotionListActivity;
import com.jordan.project.activities.motion.MotionUploadActivity;
import com.jordan.project.activities.motion.MotioningActivity;
import com.jordan.project.activities.train.TrainActivity;
import com.jordan.project.content.PlayBallObserver;
import com.jordan.project.data.MotionCountData;
import com.jordan.project.data.MotionDetailData;
import com.jordan.project.database.DatabaseObject;
import com.jordan.project.database.DatabaseProvider;
import com.jordan.project.database.DatabaseService;
import com.jordan.project.entity.BluetoothData;
import com.jordan.project.entity.PathOfParticleData;
import com.jordan.project.entity.PlayBallListData;
import com.jordan.project.entity.SpeedData;
import com.jordan.project.entity.UserInfoData;
import com.jordan.project.utils.BaiduLocationUtils;
import com.jordan.project.utils.HexadecimalUtils;
import com.jordan.project.utils.JsonFalseUtils;
import com.jordan.project.utils.JsonSuccessUtils;
import com.jordan.project.utils.LogUtils;
import com.jordan.project.utils.Mat;
import com.jordan.project.utils.NoDoubleClickListener;
import com.jordan.project.utils.Point;
import com.jordan.project.utils.SettingSharedPerferencesUtil;
import com.jordan.project.utils.ToastUtils;
import com.jordan.project.utils.WindosUtils;
import com.jordan.project.widget.PathOfParticleGridView;
import com.jordan.project.widget.slidedatetimepicker.ChooesBallTypeDialog;
import com.jordan.usersystemlibrary.UserManager;

import org.json.JSONException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends Activity {

    ArrayList<PlayBallListData> mPlayBallList = new ArrayList<PlayBallListData>();
    ArrayList<PlayBallListData> mPlayBallShowList = new ArrayList<PlayBallListData>();
    ListView mLvPlayBall;
    PlayBallListAdapter mPlayBallListAdapter;
    private String createListPageNo = "1";
    private String createListPageSize = "1000";

    private RelativeLayout mRLBluetoothUpload;
    private RelativeLayout mRLBluetoothScan;//main_bluetooth_scan
    RelativeLayout mRLUserData;
    private ImageView mIVUserData;

    private RadioButton mRBPlayBall;
    private RadioButton mRBTrain;
    private RadioButton mRBStadium;
    private RadioButton mRBData;
    private ImageView mIVCenterBg;
    private RelativeLayout mRLStartMotion;
    private TextView mTVPlayBallCount;//main_play_ball_count
    private TextView mTVDistance;//main_state_distance
    private TextView mTVAvgSpeed;//main_state_avg_speed
    private TextView mTVCalorie;//main_state_calorie
    private TextView mTVTotalTime;//main_state_total_time
    private TextView mTVPlayBallTime;//pic_table_path_of_particle_time


//    private AnimationDrawable mHeadBgAnimationDrawable;
//    private ImageView mIVHeadBgFrame;
//    private static final int MESSAGE_CHANGE_HEAD_BG_FRAME_IMAGE = 1;
//    private WeakReference<Drawable> mCurrentHeadBgFrameDrawable;
//    private int mCurrentHeadBgFrameIndex;
//    private static final int[] DRAWABLE_HEAD_BG_FRAME_LIST = new int[]{
//            R.mipmap.a_000, R.mipmap.a_001, R.mipmap.a_002, R.mipmap.a_003,
//            R.mipmap.a_004, R.mipmap.a_005, R.mipmap.a_006, R.mipmap.a_007,
//            R.mipmap.a_008, R.mipmap.a_009, R.mipmap.a_010, R.mipmap.a_011,
//            R.mipmap.a_012, R.mipmap.a_013, R.mipmap.a_014, R.mipmap.a_015,
//            R.mipmap.a_016, R.mipmap.a_017, R.mipmap.a_018, R.mipmap.a_019,
//            R.mipmap.a_020, R.mipmap.a_021, R.mipmap.a_022, R.mipmap.a_023,
//            R.mipmap.a_024, R.mipmap.a_025, R.mipmap.a_026, R.mipmap.a_027,
//            R.mipmap.a_028, R.mipmap.a_029, R.mipmap.a_030, R.mipmap.a_031,
//            R.mipmap.a_032, R.mipmap.a_033, R.mipmap.a_034, R.mipmap.a_035,
//            R.mipmap.a_036, R.mipmap.a_037, R.mipmap.a_038, R.mipmap.a_039,
//            R.mipmap.a_040, R.mipmap.a_041,
//            R.mipmap.a_042, R.mipmap.a_043, R.mipmap.a_044, R.mipmap.a_045,
//            R.mipmap.a_046, R.mipmap.a_047, R.mipmap.a_048, R.mipmap.a_049,
//            R.mipmap.a_050, R.mipmap.a_051,
//            R.mipmap.a_052, R.mipmap.a_053, R.mipmap.a_054, R.mipmap.a_055,
//            R.mipmap.a_056, R.mipmap.a_057, R.mipmap.a_058, R.mipmap.a_059,
//            R.mipmap.a_060, R.mipmap.a_061,
//            R.mipmap.a_062, R.mipmap.a_063, R.mipmap.a_064, R.mipmap.a_065,
//            R.mipmap.a_066, R.mipmap.a_067, R.mipmap.a_068, R.mipmap.a_069,
//            R.mipmap.a_070, R.mipmap.a_071,
//            R.mipmap.a_072, R.mipmap.a_073, R.mipmap.a_074, R.mipmap.a_075,
//            R.mipmap.a_076, R.mipmap.a_077, R.mipmap.a_078, R.mipmap.a_079};

//    private AnimationDrawable mPlayBallBgAnimationDrawable;
//    private ImageView mIVPlayBallBg;
//    private static final int MESSAGE_CHANGE_PLAY_BALL_BG_IMAGE = 2;
//    private WeakReference<Drawable> mCurrentPlayBallBgDrawable;
//    private int mCurrentPlayBallBgIndex;
//    private static final int[] DRAWABLE_PLAY_BALL_BG_LIST = new int[]{
//            R.mipmap.main_line_000, R.mipmap.main_line_001, R.mipmap.main_line_002, R.mipmap.main_line_003,
//            R.mipmap.main_line_004, R.mipmap.main_line_005, R.mipmap.main_line_006, R.mipmap.main_line_007,
//            R.mipmap.main_line_008, R.mipmap.main_line_009, R.mipmap.main_line_010, R.mipmap.main_line_011,
//            R.mipmap.main_line_012, R.mipmap.main_line_013, R.mipmap.main_line_014, R.mipmap.main_line_015,
//            R.mipmap.main_line_016, R.mipmap.main_line_017, R.mipmap.main_line_018, R.mipmap.main_line_019,
//            R.mipmap.main_line_020, R.mipmap.main_line_021, R.mipmap.main_line_022, R.mipmap.main_line_023,
//            R.mipmap.main_line_024, R.mipmap.main_line_025, R.mipmap.main_line_026, R.mipmap.main_line_027,
//            R.mipmap.main_line_028, R.mipmap.main_line_029, R.mipmap.main_line_030, R.mipmap.main_line_031,
//            R.mipmap.main_line_032, R.mipmap.main_line_033, R.mipmap.main_line_034, R.mipmap.main_line_035,
//            R.mipmap.main_line_036, R.mipmap.main_line_037, R.mipmap.main_line_038, R.mipmap.main_line_039,
//            R.mipmap.main_line_040, R.mipmap.main_line_041,
//            R.mipmap.main_line_042, R.mipmap.main_line_043, R.mipmap.main_line_044, R.mipmap.main_line_045,
//            R.mipmap.main_line_046, R.mipmap.main_line_047, R.mipmap.main_line_048, R.mipmap.main_line_049};

    private Handler mMainHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
//                case MESSAGE_CHANGE_PLAY_BALL_BG_IMAGE:
//                    mCurrentPlayBallBgIndex = mCurrentPlayBallBgIndex + 1;
//                    if (mCurrentPlayBallBgIndex >= DRAWABLE_PLAY_BALL_BG_LIST.length) {
//                        mCurrentPlayBallBgIndex = 0;
//                    }
//                    int resource_id = DRAWABLE_PLAY_BALL_BG_LIST[mCurrentPlayBallBgIndex];
//                    //android.util.Log.e("SlashInfo", "index= " + mCurrentPlayBallBgIndex + " total= " + DRAWABLE_PLAY_BALL_BG_LIST.length + " resource_id= " + resource_id);
//                    //mRlPlayBallBg.setBackgroundResource(resource_id);
//                    sendEmptyMessageDelayed(MESSAGE_CHANGE_PLAY_BALL_BG_IMAGE, 10);
//                    break;
//                case MESSAGE_CHANGE_HEAD_BG_FRAME_IMAGE:
//                    mCurrentHeadBgFrameIndex = mCurrentHeadBgFrameIndex + 1;
//                    if (mCurrentHeadBgFrameIndex >= DRAWABLE_HEAD_BG_FRAME_LIST.length) {
//                        mCurrentHeadBgFrameIndex = 0;
//                    }
//                    resource_id = DRAWABLE_HEAD_BG_FRAME_LIST[mCurrentHeadBgFrameIndex];
//                    //android.util.Log.e("SlashInfo", "index= " + mCurrentHeadBgFrameIndex + " total= " + DRAWABLE_HEAD_BG_FRAME_LIST.length + " resource_id= " + resource_id);
//                    mIVHeadBgFrame.setBackgroundResource(resource_id);
//                    sendEmptyMessageDelayed(MESSAGE_CHANGE_HEAD_BG_FRAME_IMAGE, 10);
//                    break;
                case InnerMessageConfig.USER_GET_USER_DATA_MESSAGE_SUCCESS:
                    //解析正确参数-内容在msg.obj
                    //保存登录返回数据
                    LogUtils.showLog("Result", "USER_GET_USER_DATA_MESSAGE_SUCCESS result:" + (String) msg.obj);
                    //保存用户信息
                    try {
                        UserInfoData userInfoData = JsonSuccessUtils.getUserData((String) msg.obj);
                        DatabaseService.createUserInfo(userInfoData.getUsername(), userInfoData.getName(), userInfoData.getNick(),
                                userInfoData.getGender(), userInfoData.getAge(), userInfoData.getBirthday(), userInfoData.getPosition(),
                                userInfoData.getWeight(), userInfoData.getHeight(), userInfoData.getQq(), userInfoData.getMobile(),
                                userInfoData.getEmail(), userInfoData.getImg(), userInfoData.getImgId());
                        JordanApplication.isUpdateUserData = false;
                    } catch (JSONException e) {
                        mMainHandler.sendEmptyMessage(InnerMessageConfig.USER_LOGIN_MESSAGE_EXCEPTION);
                        e.printStackTrace();
                    }

                    //update用户信息
                    initUserInfo();
                    break;
                case InnerMessageConfig.USER_GET_USER_DATA_MESSAGE_EXCEPTION:
                    ToastUtils.shortToast(MainActivity.this, R.string.http_exception);
                    break;
                case InnerMessageConfig.USER_GET_USER_DATA_MESSAGE_FALSE:
                    try {
                        String errorMsg = JsonFalseUtils.onlyErrorCodeResult((String) msg.obj);
                        if (errorMsg != null)
                            ToastUtils.shortToast(MainActivity.this, errorMsg);
                    } catch (JSONException e) {
                        mMainHandler.sendEmptyMessage(InnerMessageConfig.USER_LOGIN_MESSAGE_EXCEPTION);
                        e.printStackTrace();
                    }
                    break;
                case InnerMessageConfig.USER_JOIN_LIST_MESSAGE_SUCCESS:
                    //解析正确参数-内容在msg.obj
                    //保存登录返回数据
                    LogUtils.showLog("Result", "USER_JOIN_LIST_MESSAGE_SUCCESS result:" + (String) msg.obj);

                    LogUtils.showLog("Result", "USER_JOIN_LIST_MESSAGE_SUCCESS");
                    //保存比赛信息
                    try {
                        LogUtils.showLog("Result", "getJoinList");
                        JordanApplication.isCreatePlayBallUpdateJoin = false;
                        mPlayBallList = JsonSuccessUtils.getJoinList((String) msg.obj);
                        //刷新list
                        //mPlayBallListAdapter.updateList(mPlayBallList);
                        //存储数据库
                        for (int i = 0; i < mPlayBallList.size(); i++) {
                            LogUtils.showLog("Result", "createReachDetailInfo");
                            //保存我参与
                            PlayBallListData playBallListData = mPlayBallList.get(i);
                            DatabaseService.createReachDetailInfo(playBallListData.getId(), playBallListData.getCourtId(),
                                    playBallListData.getVipId(), playBallListData.getTime(),
                                    playBallListData.getDuration(), playBallListData.getPeople(), playBallListData.getType(),
                                    playBallListData.getPicture(), playBallListData.getLongitude(),
                                    playBallListData.getLatitude(), playBallListData.getProvince(), playBallListData.getCity(),
                                    playBallListData.getDistrict(), playBallListData.getStreet(),
                                    playBallListData.getAddress(), playBallListData.getMobile(), playBallListData.getContact(),
                                    playBallListData.getJoin(), playBallListData.getDistance(), playBallListData.getRemarks(),
                                    JordanApplication.getUsername(MainActivity.this), playBallListData.getVipImg(), playBallListData.getSlogan());
                        }

                    } catch (JSONException e) {
                        LogUtils.showLog("Result", "JSONException");
                        mMainHandler.sendEmptyMessage(InnerMessageConfig.USER_LOGIN_MESSAGE_EXCEPTION);
                        e.printStackTrace();
                    }
                    break;
                case InnerMessageConfig.USER_JOIN_LIST_MESSAGE_EXCEPTION:
                    ToastUtils.shortToast(MainActivity.this, getResources().getString(R.string.http_exception));
                    break;
                case InnerMessageConfig.USER_JOIN_LIST_MESSAGE_FALSE:
                    try {
                        String errorMsg = JsonFalseUtils.onlyErrorCodeResult((String) msg.obj);
                        if (errorMsg != null)
                            ToastUtils.shortToast(MainActivity.this, errorMsg);
                    } catch (JSONException e) {
                        mMainHandler.sendEmptyMessage(InnerMessageConfig.USER_JOIN_LIST_MESSAGE_EXCEPTION);
                        e.printStackTrace();
                    }
                    break;
                case PlayBallObserver.DATABASE_UPDATE:
                    LogUtils.showLog("Result", "DATABASE_UPDATE");
                    mPlayBallList = DatabaseService.findNowPlayBallList(JordanApplication.getUsername(MainActivity.this));
                    mPlayBallShowList = new ArrayList<PlayBallListData>();
                    if (mPlayBallList.size() > 0)
                        mPlayBallShowList.add(mPlayBallList.get(0));
                    mPlayBallList = new ArrayList<PlayBallListData>();
                    mPlayBallList = mPlayBallShowList;
                    LogUtils.showLog("Result", "findNowPlayBallList:" + mPlayBallList.size());
                    mPlayBallListAdapter.updateList(mPlayBallList);

                    break;
                case InnerMessageConfig.COMMON_BLUETOOTH_LIST_MESSAGE_SUCCESS:
                    LogUtils.showLog("Result", "COMMON_BLUETOOTH_LIST_MESSAGE_SUCCESS result:" + (String) msg.obj);
                    //存储到数据库
                    JordanApplication.isUpdateBluetoothList = false;
                    try {
                        JsonSuccessUtils.getBluetoothList((String) msg.obj, MainActivity.this);
                        //刷新缓存默认值
                        getChoiesBluetooth();
                    } catch (JSONException e) {
                        mMainHandler.sendEmptyMessage(InnerMessageConfig.COMMON_GET_CODE_MESSAGE_EXCEPTION);
                        e.printStackTrace();
                    }
                    break;
                case InnerMessageConfig.COMMON_BLUETOOTH_LIST_MESSAGE_EXCEPTION:
                    break;
                case InnerMessageConfig.COMMON_BLUETOOTH_LIST_MESSAGE_FALSE:
                    try {
                        String errorMsg = JsonFalseUtils.onlyErrorCodeResult((String) msg.obj);
                    } catch (JSONException e) {
                        mMainHandler.sendEmptyMessage(InnerMessageConfig.COMMON_GET_CODE_MESSAGE_EXCEPTION);
                        e.printStackTrace();
                    }
                    break;
                case N0_PERMISSION:
                    ToastUtils.shortToast(MainActivity.this, R.string.please_open_permission);
                    break;

                case InnerMessageConfig.USER_MOVE_LIST_MESSAGE_SUCCESS:
                    try {
                        //解析正确参数-内容在msg.obj
                        LogUtils.showLog("Result", "USER_MOVE_LIST_MESSAGE_SUCCESS result:" + (String) msg.obj);
                        //得到总数
                        MotionCountData motionCountData = JsonSuccessUtils.getMoveCount((String) msg.obj);
                        mTVPlayBallCount.setText(motionCountData.getCount().toString() + "次");
                        if (motionCountData.getTime() != null && !motionCountData.getTime().equals("")) {
                            //设置时间
                            mTVPlayBallTime.setText(motionCountData.getTime());
                        }
                        if (motionCountData.getId() != null && !motionCountData.getId().equals("")) {
                            //通过ID继续查询详情
                            getDetail(motionCountData.getId());
                        }
                    } catch (JSONException e) {
                        ToastUtils.shortToast(MainActivity.this, getResources().getString(R.string.http_exception));
                        e.printStackTrace();
                    }
                    break;
                case InnerMessageConfig.USER_MOVE_LIST_MESSAGE_EXCEPTION:
                    ToastUtils.shortToast(MainActivity.this, getResources().getString(R.string.http_exception));
                    break;
                case InnerMessageConfig.USER_MOVE_LIST_MESSAGE_FALSE:
                    try {
                        String errorMsg = JsonFalseUtils.onlyErrorCodeResult((String) msg.obj);
                        if (errorMsg != null)
                            ToastUtils.shortToast(MainActivity.this, errorMsg);
                    } catch (JSONException e) {
                        mMainHandler.sendEmptyMessage(InnerMessageConfig.USER_MOTION_DETAIL_MESSAGE_EXCEPTION);
                        e.printStackTrace();
                    }
                    break;
                case InnerMessageConfig.USER_MOTION_DETAIL_MESSAGE_SUCCESS:
                    //解析正确参数-内容在msg.obj
                    LogUtils.showLog("Result", "USER_MOTION_DETAIL_MESSAGE_SUCCESS result:" + (String) msg.obj);
                    try {
                        MotionDetailData motionDetailData = JsonSuccessUtils.getMotionDetail((String) msg.obj);
                        //存储数据库
                        DatabaseService.createMotionDetail(motionDetailData.getServiceID(), motionDetailData.getLongitude(), motionDetailData.getLatitude(),
                                motionDetailData.getAddress(), motionDetailData.getBeginTime(), motionDetailData.getSpend(), motionDetailData.getPicture(),
                                motionDetailData.getEndTime(), motionDetailData.getTotalDist(), motionDetailData.getTotalStep(), motionDetailData.getTotalHorDist(),
                                motionDetailData.getTotalVerDist(), motionDetailData.getTotalTime(), motionDetailData.getTotalActiveTime(), motionDetailData.getActiveRate(),
                                motionDetailData.getAvgSpeed(), motionDetailData.getMaxSpeed(), motionDetailData.getSpurtCount(), motionDetailData.getBreakinCount(),
                                motionDetailData.getBreakinAvgTime(), motionDetailData.getVerJumpCount(), motionDetailData.getVerJumpAvgHigh(), motionDetailData.getAvgHoverTime(),
                                motionDetailData.getAvgTouchAngle(), motionDetailData.getTouchType(), motionDetailData.getPerfRank(), motionDetailData.getRunRank(),
                                motionDetailData.getBreakRank(), motionDetailData.getBounceRank(), motionDetailData.getAvgShotDist(), motionDetailData.getMaxShotDist(),
                                motionDetailData.getHandle(), motionDetailData.getCrlorie(), motionDetailData.getTrail(), motionDetailData.getVerJumpPoint(),
                                motionDetailData.getFooter(), motionDetailData.getBallType(), motionDetailData.getStadiumType());
                        initData(motionDetailData);
                    } catch (JSONException e) {
                        mMainHandler.sendEmptyMessage(InnerMessageConfig.USER_MOTION_DETAIL_MESSAGE_EXCEPTION);
                        e.printStackTrace();
                    }
                    break;
                case InnerMessageConfig.USER_MOTION_DETAIL_MESSAGE_EXCEPTION:
                    ToastUtils.shortToast(MainActivity.this, getResources().getString(R.string.http_exception));
                    break;
                case InnerMessageConfig.USER_MOTION_DETAIL_MESSAGE_FALSE:
                    try {
                        String errorMsg = JsonFalseUtils.onlyErrorCodeResult((String) msg.obj);
                        if (errorMsg != null)
                            ToastUtils.shortToast(MainActivity.this, errorMsg);
                    } catch (JSONException e) {
                        mMainHandler.sendEmptyMessage(InnerMessageConfig.USER_MOTION_DETAIL_MESSAGE_EXCEPTION);
                        e.printStackTrace();
                    }
                    break;
            }
        }
    };

    private void getChoiesBluetooth() {
        int choiesBluetooth = 0;
        boolean hasBluetooth = false;
        ArrayList<BluetoothData> mBluetoothList = new ArrayList<BluetoothData>();
        mBluetoothList = DatabaseService.findBluetoothList(JordanApplication.getUsername(MainActivity.this));
        for (int i = 0; i < mBluetoothList.size(); i++) {
            String sn = SettingSharedPerferencesUtil.GetChoiesBluetoothConfig(MainActivity.this,
                    JordanApplication.getUsername(MainActivity.this));
            if (sn.contains("|")) {
                sn = sn.substring(0, sn.indexOf("|"));
                if (sn.equals(mBluetoothList.get(i).getSn())) {
                    choiesBluetooth = i;
                    hasBluetooth = true;
                }
            }
        }
        if (!hasBluetooth) {
            if (mBluetoothList.size() != 0) {
                String snAndMac = mBluetoothList.get(choiesBluetooth).getSn()
                        + "|" + mBluetoothList.get(choiesBluetooth).getMac();
                SettingSharedPerferencesUtil.SetChoiesBluetoothValue(MainActivity.this,
                        JordanApplication.getUsername(MainActivity.this), snAndMac);
                LogUtils.showLog("getChoiesBluetooth", "snAndMac:" + snAndMac);
            } else {
                SettingSharedPerferencesUtil.SetChoiesBluetoothValue(MainActivity.this,
                        JordanApplication.getUsername(MainActivity.this), "");
                LogUtils.showLog("getChoiesBluetooth", "snAndMac:null");
            }
        }
        LogUtils.showLog("getChoiesBluetooth", "choiesBluetooth:" + choiesBluetooth);
    }

    private PlayBallObserver mPlayBallObserver;
    private UserManager userManager;
    private CommonManager commonManager;
    private static final int N0_PERMISSION = 21;
    private boolean mIsGrant;

    private void registerContentObservers() {
        mPlayBallObserver = new PlayBallObserver(mMainHandler);
        Uri uri = Uri.parse("content://" + DatabaseProvider.AUTHOR + "/" + DatabaseObject.REACH_DETAIL);
        getContentResolver().registerContentObserver(uri, true,
                mPlayBallObserver);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LogUtils.showLog("MainActivity", "========================onCreate start======================");
        userManager = new UserManager(MainActivity.this, mMainHandler);
        commonManager = new CommonManager(MainActivity.this, mMainHandler);
        registerContentObservers();
        //如果已经有权限了直接初始化，否则再请求一次权限，并且成功的时候触发此方法
        BaiduLocationUtils.initLocation(MainActivity.this);

//        checkPhoneStatePermission();
//        checkGPSPermission();
//        checkWriteExternalStoragePermission();
//        checkGetAccountsPermission();
//        if (!mIsGrant) {
//            mMainHandler.sendEmptyMessage(N0_PERMISSION);
//            finish();
//        }
        LogUtils.showLog("MainActivity", "========================setView ======================");
        setView();
        LogUtils.showLog("MainActivity", "========================setListener ======================");
        setListener();
        LogUtils.showLog("MainActivity", "========================initUserInfo ======================");
        initUserInfo();
        LogUtils.showLog("MainActivity", "========================doGetUserDataTask ======================");
        doGetUserDataTask();
        LogUtils.showLog("MainActivity", "========================doJoinListTask ======================");
        doJoinListTask();
        LogUtils.showLog("MainActivity", "========================bluetoothList ======================");
        bluetoothList();
        doDetail("-1", "-1");
        WindosUtils.getWindowsWidth(MainActivity.this);
        if (SettingSharedPerferencesUtil.GetFristLoginValueConfig(MainActivity.this).equals("")) {
            SettingSharedPerferencesUtil.SetFristLoginValue(MainActivity.this, "true");
            //跳转到指南页面
            Intent intent = new Intent(MainActivity.this, FingerpostActivity.class);
            startActivity(intent);
        }

        LogUtils.showLog("MainActivity", "========================onCreate over======================");

    }
//    private void checkGetAccountsPermission() {
//        int is_granted = ContextCompat.checkSelfPermission(this, Manifest.permission.GET_ACCOUNTS);
//        if (PackageManager.PERMISSION_GRANTED != is_granted) {
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.GET_ACCOUNTS}, 1000);
//        } else {
//            mIsGrant = true;
//        }
//    }
//    private void checkWriteExternalStoragePermission() {
//        int is_granted = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
//        if (PackageManager.PERMISSION_GRANTED != is_granted) {
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1000);
//        } else {
//            mIsGrant = true;
//        }
//    }
//    private void checkGPSPermission() {
//        int is_granted = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
//        if (PackageManager.PERMISSION_GRANTED != is_granted) {
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1000);
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1000);
//        } else {
//            mIsGrant = true;
//        }
//    }
//    private void checkPhoneStatePermission() {
//        int is_granted = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);
//        if (PackageManager.PERMISSION_GRANTED != is_granted) {
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, 1000);
//        } else {
//            mIsGrant = true;
//        }
//    }
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        LogUtils.showLog("motioning", "onRequestPermissionsResult");
//        mPermissionUtil.requestResult(MainActivity.this,requestCode,permissions,grantResults,MainActivity.this);
//
//    }


    private void initUserInfo() {
        //获取数据库用户信息
        UserInfoData userInfoData = DatabaseService.findUserInfo(JordanApplication.getUsername(MainActivity.this));
        if (userInfoData != null) {
            //展示
//            if (!userInfoData.getNick().equals("null"))
//                mTVName.setText(userInfoData.getNick());
//            if (!userInfoData.getPosition().equals("null"))
//                mTvPosition.setText(userInfoData.getPosition());
//            //ImageLoader导入图片
//            if (!userInfoData.getImg().equals("null")) {
//                DisplayImageOptions options = new DisplayImageOptions.Builder()
//                        .showImageOnLoading(R.mipmap.img_jordan_logo)
//                        .showImageForEmptyUri(R.mipmap.img_jordan_logo)
//                        .showImageOnFail(R.mipmap.img_jordan_logo).cacheInMemory(true)
//                        .cacheOnDisk(true).considerExifParams(true).build();
//                ImageLoader.getInstance().displayImage(userInfoData.getImg(), mIVHead, options);
//            }

            LogUtils.showLog("Photo", "userInfoData.getImg() Path:" + userInfoData.getImg());
        }
    }

    private void doGetUserDataTask() {
        userManager.getUserData(JordanApplication.getUsername(MainActivity.this));
    }

    private void doJoinListTask() {
        userManager.joinList(createListPageNo, createListPageSize);
    }

    private void setView() {

        mLvPlayBall = (ListView) findViewById(R.id.main_play_ball_list);

        mPlayBallList = DatabaseService.findNowPlayBallList(JordanApplication.getUsername(MainActivity.this));
        mPlayBallShowList = new ArrayList<PlayBallListData>();
        if (mPlayBallList.size() > 0)
            mPlayBallShowList.add(mPlayBallList.get(0));
        mPlayBallList = new ArrayList<PlayBallListData>();
        mPlayBallList = mPlayBallShowList;
        LogUtils.showLog("Result", "findNowPlayBallList:" + mPlayBallList.size());

        mPlayBallListAdapter = new PlayBallListAdapter(MainActivity.this, mPlayBallList);
        mLvPlayBall.setAdapter(mPlayBallListAdapter);
//        mIVHead = (ImageView) findViewById(R.id.main_user_head_iv);
//        mTVName = (TextView) findViewById(R.id.main_user_name);
//        mTvPosition = (TextView) findViewById(R.id.main_user_position);
        mRLUserData = (RelativeLayout) findViewById(R.id.main_user_data);
        mIVUserData = (ImageView) findViewById(R.id.main_user_data_iv);
        mRLBluetoothUpload = (RelativeLayout) findViewById(R.id.main_bluetooth_upload);
        mRLBluetoothScan = (RelativeLayout) findViewById(R.id.main_bluetooth_scan);

        mRLStartMotion = (RelativeLayout) findViewById(R.id.main_center_button);
        mIVCenterBg = (ImageView) findViewById(R.id.main_center_button_click_bg);

        mRBPlayBall = (RadioButton) findViewById(R.id.main_button_play_ball);
        mRBTrain = (RadioButton) findViewById(R.id.main_button_train);
        mRBStadium = (RadioButton) findViewById(R.id.main_button_court);
        mRBData = (RadioButton) findViewById(R.id.main_button_data);
        mTVPlayBallCount = (TextView) findViewById(R.id.main_play_ball_count);
        mTVDistance = (TextView) findViewById(R.id.main_state_distance);
        mTVAvgSpeed = (TextView) findViewById(R.id.main_state_avg_speed);
        mTVCalorie = (TextView) findViewById(R.id.main_state_calorie);
        mTVTotalTime = (TextView) findViewById(R.id.main_state_total_time);
        mTVPlayBallTime = (TextView) findViewById(R.id.pic_table_path_of_particle_time);
        setPathOfParticleView();

//        mIVHeadBgFrame = (ImageView) findViewById(R.id.user_data_head_bg_frame);
//        mHeadBgAnimationDrawable = (AnimationDrawable) mIVHeadBgFrame.getBackground();
//        mCurrentHeadBgFrameIndex = 0;
//        mCurrentHeadBgFrameDrawable = new WeakReference<Drawable>(getResources().getDrawable(DRAWABLE_HEAD_BG_FRAME_LIST[mCurrentHeadBgFrameIndex]));
//        mIVHeadBgFrame.setBackgroundResource(DRAWABLE_HEAD_BG_FRAME_LIST[mCurrentHeadBgFrameIndex]);
//        mMainHandler.sendEmptyMessageDelayed(MESSAGE_CHANGE_HEAD_BG_FRAME_IMAGE, 10);

//        mIVPlayBallBg = (ImageView) findViewById(R.id.main_bottom_play_ball_bg_iv);
//        mPlayBallBgAnimationDrawable = (AnimationDrawable) mIVPlayBallBg.getBackground();
//        mCurrentPlayBallBgIndex = 0;
//        mCurrentPlayBallBgDrawable = new WeakReference<Drawable>(getResources().getDrawable(DRAWABLE_PLAY_BALL_BG_LIST[mCurrentPlayBallBgIndex]));
//        mRlPlayBallBg.setBackgroundResource(DRAWABLE_PLAY_BALL_BG_LIST[mCurrentPlayBallBgIndex]);
//        mMainHandler.sendEmptyMessageDelayed(MESSAGE_CHANGE_PLAY_BALL_BG_IMAGE, 10);
    }

    private RelativeLayout mRlPathOfParticle;
    private PathOfParticleGridView mGvPathOfParticle;//轨迹图
    private PathOfParticleAdapter mPathOfParticleAdapter;
    ArrayList<Map<Integer, SpeedData>> mapList = new ArrayList<Map<Integer, SpeedData>>();

    private int maxX = 5;
    private double maxS = 0;
    private double minS = 10;

    /**
     * 初始化轨迹图数据
     */
    private void setPathOfParticleView() {
        LogUtils.showLog("particlelist", "setPathOfParticleView");
        ArrayList<PathOfParticleData> list = new ArrayList<PathOfParticleData>();
        mRlPathOfParticle = (RelativeLayout) findViewById(R.id.pic_table_path_of_particle_rl);
        mGvPathOfParticle = (PathOfParticleGridView) findViewById(R.id.pic_table_path_of_particle_gv);

        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        mRlPathOfParticle.measure(w, h);
        int width = mRlPathOfParticle.getMeasuredWidth();
        int height = mRlPathOfParticle.getMeasuredHeight();
        LogUtils.showLog("particlelist", "mRlPathOfParticle.getMeasuredHeight():" + height);
        mPathOfParticleAdapter = new PathOfParticleAdapter(MainActivity.this, list, width, height, maxX, maxS, minS);
        mGvPathOfParticle.setAdapter(mPathOfParticleAdapter);
        LogUtils.showLog("particlelist", "setPathOfParticleView over");

    }

    /**
     * 赋值轨迹图数据
     */
    private void initPathOfParticle(MotionDetailData motionDetailData) {
        LogUtils.showLog("particlelists", "initPathOfParticle");
        mapList = new ArrayList<Map<Integer, SpeedData>>();
        for (int i = 0; i < 28 * 15; i++) {
            Map<Integer, SpeedData> map = new HashMap<Integer, SpeedData>();
            mapList.add(map);
        }
        LogUtils.showLog("particlelists", "setPathOfParticleView mapList:" + mapList.size());
        ArrayList<PathOfParticleData> list = new ArrayList<PathOfParticleData>();
        //取真实数据tark转换成数组数据
        String tark = motionDetailData.getTrail();
//        String tark = "7472616bc0e9b13d80adbfbf0000403faeb52e00," +
//                "7472616bde2a483f544819c03c4e113f3ed42e00," +
//                "7472616bc329843fa6037abfc55e383f55d62e00," +
//                "7472616bb5d8ac3f88bdc93ef618343f7ad82e00," +
//                "7472616b7fbc1c40758b923fd4542a3fbfda2e00," +
//                "7472616bc45f79407898903f2949393fd3dc2e00," +
//                "7472616b5283a9409ac1483fe6ac383fe9de2e00," +
//                "7472616bb3d9d04060f5673d20d8363f05e12e00," +
//                "7472616b6087cb406621a5bf3fb02d3f3fe32e00," +
//                "7472616bd1b0bb406dc52fc011e2443f2de52e00," +
//                "7472616bde68ae40c02e84c007fb383f42e72e00," +
//                "7472616bc2b297409f97afc0aef7433f33e92e00," +
//                "7472616b86218e40c6b1ddc073563c3f3deb2e00," +
//                "7472616b207185401f1c07c13230453f2aed2e00," +
//                "7472616b36796a4053061ec10971423f20ef2e00," +
//                "7472616b550f4840e88234c1429c403f1cf12e00," +
//                "7472616b0c581a40824449c1f78e3d3f22f32e00," +
//                "7472616b5e747e3f23214fc1106c3b3f2ff52e00," +
//                "7472616bc8f2a8be6e4d45c1cdcf3a3f3ef72e00," +
//                "7472616bbc79bfbf7ae637c183c2373f57f92e00," +
//                "7472616b44fcd3bf58e21fc18438413f51fb2e00," +
//                "7472616b9c4091bf138b09c17cc73e3f53fd2e00," +
//                "7472616b9f6c1bbff319e9c05903353f75ff2e00," +
//                "7472616bd0ca8fbd6109bcc063ea403f70012f00," +
//                "7472616b40d9df3e81a790c02949393f84032f00," +
//                "7472616b55e6523fa48148c0c55e383f9b052f00";
        LogUtils.showLog("particlelists", "setPathOfParticleView tark:" + tark);
        if (tark.contains(",")) {
            String[] tarks = tark.split(",");
            LogUtils.showLog("particlelists", "setPathOfParticleView tarks:" + tarks.length);
            LogUtils.showLog("particlePic", "start");

            ArrayList<Point> xy = new ArrayList<Point>();
            ArrayList<Point> pxy = new ArrayList<Point>();
            if (tarks.length >= 4) {
                for (int a = 0; a < tarks.length; a++) {
                    String as = tarks[a];
                    float trakxf2 = HexadecimalUtils.formatFloatData(as.substring(8, 16));
                    float trakYf = HexadecimalUtils.formatFloatData(as.substring(16, 24));
                    Point p = new Point();
                    p.setX(trakxf2);
                    p.setY(trakYf);
                    xy.add(p);
                }
//            for(int k = 0 ; k < xy.size();k++){
//                System.out.println(xy.get(k).getX()+"	"+xy.get(k).getY());
//            }
                for (int i = 0; i < xy.size(); i++) {
                    Point pi = new Point();
                    pxy.add(pi);
                }
                if (xy != null && xy.size() >= 4) {
                    try {
                        //,full 参数 1-全场,0-半场
                        int full = 1;
                        if (motionDetailData.getBallType().equals("2")) {
                            full = 0;
                        }
                        Mat.data_analysis_v3(xy, xy.size(), pxy, full);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    pxy = xy;
                }
                System.out.println("转换后的坐标----------------------");
                for (int j = 0; j < pxy.size(); j++) {
                    System.out.println(pxy.get(j).getX() + "	" + pxy.get(j).getY());
                }
            }
            for (int i = 1; i < tarks.length; i++) {
                int x = 0;
                int y = 0;
                if (tarks.length >= 4 && pxy.size() >= 4) {
                    x = (int) pxy.get(i).getX();
                    y = (int) pxy.get(i).getY();
                    x = Math.abs(x);
                    y = Math.abs(y);
                } else {
                    int startX = (int) HexadecimalUtils.formatFloatData(tarks[i].substring(8, 16));
                    int startY = (int) HexadecimalUtils.formatFloatData(tarks[i].substring(16, 24));
                    float floatX = HexadecimalUtils.formatFloatData(tarks[i].substring(8, 16));
                    float floatY = HexadecimalUtils.formatFloatData(tarks[i].substring(16, 24));
                    x = Math.abs(startX);
                    y = Math.abs(startY);
                }
//                int startX = (int) HexadecimalUtils.formatFloatData(tarks[m].substring(8, 16));
//                int startY = (int) HexadecimalUtils.formatFloatData(tarks[m].substring(16, 24));
//                float floatX = HexadecimalUtils.formatFloatData(tarks[m].substring(8, 16));
//                float floatY = HexadecimalUtils.formatFloatData(tarks[m].substring(16, 24));
//                LogUtils.showLog("particlePic", "i:"+i+"=====floatX:" + floatX + "|" + "floatY:" + floatY);
//                //if ((startX * startY) > 0) {
//                    //坐标归类
                if (x > 28) x = 28;
                if (y > 15) y = 14;
                int number = y * 28 + x;
                float step = HexadecimalUtils.formatFloatData(tarks[i].substring(24, 32));
                float aftertime = HexadecimalUtils.formatLongData(tarks[i].substring(32, 40)) * 1000 / 512;
                float beforetime = HexadecimalUtils.formatLongData(tarks[i - 1].substring(32, 40)) * 1000 / 512;
                LogUtils.showLog("particlelist", "setPathOfParticleView number:" + number);
                LogUtils.showLog("particlelist", "setPathOfParticleView mapList:" + mapList.size());
                Map<Integer, SpeedData> map = mapList.get(number);
                SpeedData speedData = new SpeedData();
                speedData.setStep(step);
                speedData.setTime(aftertime - beforetime);
                LogUtils.showLog("particlelist", "setPathOfParticleView speedData:" + speedData.toString());
                map.put(map.size(), speedData);
                mapList.set(number, map);
                //}
            }
            LogUtils.showLog("particlePic", "over");
            LogUtils.showLog("particlelist", "setPathOfParticleView mapList:" + mapList.size());
            //转换ArrayList<PathOfParticleData> list
            for (int i = 0; i < mapList.size(); i++) {
                PathOfParticleData pathOfParticleData = new PathOfParticleData();
                Map<Integer, SpeedData> map = mapList.get(i);
                pathOfParticleData.setCount(map.size());
                float step = 0;
                float time = 0;
                for (int key : map.keySet()) {
                    SpeedData speedData = map.get(key);
                    step = step + speedData.getStep();
                    time = time + speedData.getTime();
                }
                double speed = 0;
                if (time != 0) {
                    speed = step * 2 * 3.6 * 1000 / time;
                }
                if (maxX < map.size()) {
                    maxX = map.size();
                }
                if (maxS < speed) {
                    maxS = speed;
                    LogUtils.showLog("particlelists", "maxS:" + maxS);
                }
                if (minS > speed && speed != 0) {
                    minS = speed;
                    LogUtils.showLog("particlelists", "minS:" + minS);
                }
                pathOfParticleData.setAvgSpeed(speed);
                list.add(pathOfParticleData);
                LogUtils.showLog("particlelist", "setPathOfParticleView pathOfParticleData:" + pathOfParticleData.toString());
            }
            LogUtils.showLog("particlelist", "setPathOfParticleView list:" + list.size());
//        for (int i = 0; i < 28 * 15; i++) {
//            PathOfParticleData pathOfParticleData=new PathOfParticleData();
//            if(i%5==0) {
//                pathOfParticleData.setAvgSpeed(1d);
//                pathOfParticleData.setCount(1);
//                list.add(pathOfParticleData);
//            }else if(i%5==1) {
//                pathOfParticleData.setAvgSpeed(5d);
//                pathOfParticleData.setCount(2);
//                list.add(pathOfParticleData);
//            }else if(i%5==2) {
//                pathOfParticleData.setAvgSpeed(9d);
//                pathOfParticleData.setCount(3);
//                list.add(pathOfParticleData);
//            }else if(i%5==3) {
//                pathOfParticleData.setAvgSpeed(14d);
//                pathOfParticleData.setCount(4);
//                list.add(pathOfParticleData);
//            }else if(i%5==4) {
//                pathOfParticleData.setAvgSpeed(17d);
//                pathOfParticleData.setCount(5);
//                list.add(pathOfParticleData);
//            }
//        }
            LogUtils.showLog("particlelist", "initPathOfParticle list.size:" + list.size());
            mPathOfParticleAdapter.updateList(list, 0, maxX, maxS, minS);

        } else {
            mPathOfParticleAdapter.updateList(list, 0, maxX, maxS, minS);
        }

    }


    private String motionListPageNo = "1";
    private String motionListPageSize = "1";

    private void doDetail(String beginTime, String endTime) {
        userManager.moveList(beginTime, endTime, motionListPageNo, motionListPageSize);
    }

    private void initData(MotionDetailData motionDetailData) {
        if (motionDetailData != null) {
            //设置本场跑动距离
            //设置平均速度
            //设置卡路里
            //设置总时长
            mTVDistance.setText(new BigDecimal((Double.valueOf(motionDetailData.getTotalDist()) / 1000)).setScale(2, BigDecimal.ROUND_DOWN).toString() + "km");
            mTVAvgSpeed.setText(new BigDecimal((Double.valueOf(motionDetailData.getAvgSpeed()) * 3.6)).setScale(2, BigDecimal.ROUND_DOWN).toString() + "km/h");
            mTVCalorie.setText(motionDetailData.getCrlorie());
            if (motionDetailData.getTotalTime().equals(""))
                motionDetailData.setTotalTime("0");
            double totalTime = Double.valueOf(motionDetailData.getTotalTime());
            int hour = (int) (totalTime / 3600);
            totalTime = totalTime % 3600;
            int min = (int) (totalTime / 60);
            int second = (int) (totalTime % 60);
            String h = String.valueOf(hour);
            if (hour < 10)
                h = "0" + h;
            String m = String.valueOf(min);
            if (min < 10)
                m = "0" + m;
            String s = String.valueOf(second);
            if (second < 10)
                s = "0" + s;
            mTVTotalTime.setText(h + ":" + m + ":" + s);
            //设置轨迹图
            initPathOfParticle(motionDetailData);
        }
    }


    private void getDetail(String id) {
        userManager.moveDetail(id);
    }

    private void setListener() {
        mRLUserData.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //mIVCenterBg;
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        //按下
                        mIVUserData.setBackgroundResource(R.mipmap.main_person_center_press);
                        mRLUserData.setBackgroundResource(R.mipmap.main_person_press);
                        break;
                    case MotionEvent.ACTION_UP:
                        //抬起
                        mIVUserData.setBackgroundResource(R.mipmap.main_person_center_normal);
                        mRLUserData.setBackground(null);
                        break;

                }

                return false;
            }
        });
        mRLStartMotion.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                ArrayList<BluetoothData> mBluetoothList = DatabaseService.findBluetoothList(JordanApplication.getUsername(MainActivity.this));
                if (mBluetoothList.size() == 0) {
                    ToastUtils.shortToast(MainActivity.this, getResources().getString(R.string.common_please_bind_bluetooth_to_person_data));
                } else {
                    showChooesBallTypeDialog();
                }
            }
        });
        mRLStartMotion.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //mIVCenterBg;
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        //按下
                        mIVCenterBg.setVisibility(View.VISIBLE);
                        break;
                    case MotionEvent.ACTION_UP:
                        //抬起
                        mIVCenterBg.setVisibility(View.GONE);
                        break;

                }

                return false;
            }
        });
        mRLBluetoothUpload.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                ArrayList<BluetoothData> mBluetoothList = DatabaseService.findBluetoothList(JordanApplication.getUsername(MainActivity.this));
                if (mBluetoothList.size() == 0) {
                    ToastUtils.shortToast(MainActivity.this, getResources().getString(R.string.common_please_bind_bluetooth_to_person_data));
                } else {
                    Intent intent = new Intent(MainActivity.this, MotionUploadActivity.class);
                    startActivity(intent);
                }
            }
        });
        mRLBluetoothScan.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                checkCameraPermission();
            }
        });
        mRBPlayBall.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                Intent intent = new Intent(MainActivity.this, PlayBallListActivity.class);
                startActivity(intent);
            }
        });
        mRBTrain.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                Intent intent = new Intent(MainActivity.this, TrainActivity.class);
                startActivity(intent);
            }
        });
        mRBStadium.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                Intent intent = new Intent(MainActivity.this, NearbyStadiumActivity.class);
                startActivity(intent);
            }
        });
        mRBData.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                Intent intent = new Intent(MainActivity.this, MotionListActivity.class);
                startActivity(intent);
            }
        });
        mRLUserData.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                Intent intent = new Intent(MainActivity.this, UserDataActivity.class);
                startActivity(intent);
            }
        });
        mLvPlayBall.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, PlayBallDetailActivity.class);
                intent.putExtra("id", mPlayBallList.get(position).getId());
                intent.putExtra("picture", mPlayBallList.get(position).getVipImg());
                startActivity(intent);
            }
        });

    }

    private void checkCameraPermission() {
        int is_granted = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        if (PackageManager.PERMISSION_GRANTED != is_granted) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 1000);
        } else {
            Intent intent = new Intent(MainActivity.this, BindBluetoothActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (PackageManager.PERMISSION_GRANTED == grantResults[0]) {
            Intent intent = new Intent(MainActivity.this, BindBluetoothActivity.class);
            startActivity(intent);
        } else {
            mMainHandler.sendEmptyMessage(N0_PERMISSION);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (JordanApplication.isUpdateUserData) {
            doGetUserDataTask();
        }
        if (JordanApplication.isCreatePlayBallUpdateJoin) {
            doJoinListTask();
        }
        if (JordanApplication.islogout) {
            JordanApplication.islogout = false;
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
        if (JordanApplication.isUpdateBluetoothList) {
            JordanApplication.isUpdateBluetoothList = false;
            bluetoothList();
        }
        if (JordanApplication.isUpdateMotion) {
            JordanApplication.isUpdateMotion = false;
            doDetail("-1", "-1");
        }
        //mHeadBgAnimationDrawable.start();
        //mPlayBallBgAnimationDrawable.start();
        initUserInfo();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //mHeadBgAnimationDrawable.stop();
        //mPlayBallBgAnimationDrawable.stop();
    }

    private void bluetoothList() {
        commonManager.bluetoothList();


    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent home = new Intent(Intent.ACTION_MAIN);

            home.addCategory(Intent.CATEGORY_HOME);

            startActivity(home);

        }
        return super.onKeyDown(keyCode, event);
    }

    private void showChooesBallTypeDialog() {
        final Dialog dialog = new ChooesBallTypeDialog(MainActivity.this,
                R.style.chooes_dialog_style);
        dialog.show();
        Window window = dialog.getWindow();
//        WindowManager.LayoutParams lp = window.getAttributes();
//        lp.alpha = 0.8f;
//        window.setAttributes(lp);
        RelativeLayout rlAll = (RelativeLayout) window.findViewById(R.id.rl_chooes_dialog_all);
        rlAll.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, MotioningActivity.class);
                intent.putExtra("ballType", 1);
                startActivity(intent);
                dialog.dismiss();
            }
        });
        RelativeLayout rlHalf = (RelativeLayout) window.findViewById(R.id.rl_chooes_dialog_half);
        rlHalf.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, MotioningActivity.class);
                intent.putExtra("ballType", 2);
                startActivity(intent);
                dialog.dismiss();
            }
        });
        RelativeLayout rlCancel = (RelativeLayout) window.findViewById(R.id.rl_chooes_dialog_cancel);
        rlCancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });

    }
}
