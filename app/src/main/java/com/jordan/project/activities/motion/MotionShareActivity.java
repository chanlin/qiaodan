package com.jordan.project.activities.motion;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.jordan.commonlibrary.CommonManager;
import com.safari.httplibs.config.InnerMessageConfig;
import com.safari.httplibs.utils.CommonUtils;
import com.jordan.project.JordanApplication;
import com.jordan.project.R;
import com.jordan.project.activities.adapter.PathOfParticleAdapter;
import com.jordan.project.database.DatabaseService;
import com.jordan.project.entity.PathOfParticleData;
import com.jordan.project.entity.SpeedData;
import com.jordan.project.entity.UserInfoData;
import com.jordan.project.utils.HexadecimalUtils;
import com.jordan.project.utils.JsonFalseUtils;
import com.jordan.project.utils.JsonSuccessUtils;
import com.jordan.project.utils.LogUtils;
import com.jordan.project.utils.Mat;
import com.jordan.project.utils.PictureUtils;
import com.jordan.project.utils.Point;
import com.jordan.project.utils.ToastUtils;
import com.jordan.project.utils.TypeUtils;
import com.jordan.project.utils.UploadPictureHasZoomUtils;
import com.jordan.project.widget.LoadingProgressDialog;
import com.jordan.project.widget.PathOfParticleGridView;
import com.jordan.project.widget.ShareDialog;
import com.jordan.usersystemlibrary.UserManager;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.umeng.socialize.UMShareAPI;

import org.json.JSONException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MotionShareActivity extends Activity {
    private String mTrail = "";
    private String mBallType = "1";
    private String mFooter = "R";
    private String mVerJumpPoint = "";
    private String mStartTime;
    private long startTime = System.currentTimeMillis();
    private TextView mTVName;
    private TextView mTVTimes;
    private ImageView mIVHead;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");

    private TextView mTVVerTicalJumpAvgValue;//share_vertical_jump_avg_value
    private TextView mTVAverageVerticalJumpTime;//share_average_vertical_jump_time
    private TextView mTVAvgrageChangeTouchDownTime;//share_average_change_touch_down_time
    private TextView mTVAboutHandler;//share_about_handler
    private TextView mTVStadiumType;//share_stadium_type
    private TextView mTVRunDistance;//share_run_distance
    private TextView mTVAvgSpeed;//share_avg_speed
    private TextView mTVPlayTime;//share_play_time
    private TextView mTVCalorie;//share_calorie
    private TextView mTVTouchDownAngle;//share_touch_down_angle
    private TextView mTVTouchType;//share_touch_type
    private ScrollView mShareView;

    private String verTicalJumpAvgValue;//纵跳均值
    private String averageVerticalJumpTime;//平均滞空
    private String avgrageChangeTouchDownTime;//变向反应时间
    private String aboutHandler;//手感如何
    private String stadiumType;//球场材质
    private String runDistance;//跑动距离
    private String avgSpeed;//跑到均速
    private String playTime;//打球时间
    private String calorie;//燃烧卡路里
    private String touchDownAngle;//平均翻转角度
    private String touchType;//翻转类型

    private Button mBtnShare;
    private Button mBtnNotShare;
    private String img;
    private String id = "";

    public static final String SHARE_QQ = "1";
    public static final String SHARE_WX = "2";
    public static final String SHARE_WB = "3";
    private boolean isUploadImage = false;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    ToastUtils.shortToast(MotionShareActivity.this, "请保证网络连接通畅");
                    break;
                case 1:
                    LoadingProgressDialog.Dissmiss();
                    isUploadImage=true;
                    mBtnShare.setText(R.string.common_share);
                    //上传成功再说;
                    JordanApplication.mSharePic = mShareBitmap;
                    ShareDialog share = new ShareDialog(MotionShareActivity.this, userManager, id, img);
                    if (MotionShareActivity.this == null || MotionShareActivity.this.isDestroyed() || MotionShareActivity.this.isFinishing()) {
                        LogUtils.showLog("motioning", "activity.isDestroyed():"+MotionShareActivity.this.isDestroyed());
                        LogUtils.showLog("motioning", "activity.isFinishing():"+MotionShareActivity.this.isFinishing());
                        return;
                    }
                    share.show();
                    break;
                case InnerMessageConfig.COMMON_UPLOAD_MEDIA_MESSAGE_SUCCESS:
                    //解析正确参数-内容在msg.obj
                    try {
                        img = JsonSuccessUtils.getImgId((String) msg.obj);
                        ToastUtils.shortToast(MotionShareActivity.this, R.string.upload_photo_success);
                        mHandler.sendEmptyMessage(1);
                    } catch (JSONException e) {
                        mHandler.sendEmptyMessage(InnerMessageConfig.COMMON_GET_CODE_MESSAGE_EXCEPTION);
                        e.printStackTrace();
                    }
                    //成功提示用户头像上传成功
                    break;
                case InnerMessageConfig.COMMON_UPLOAD_MEDIA_MESSAGE_EXCEPTION:
                    LoadingProgressDialog.Dissmiss();
                    //ToastUtils.shortToast(RegisterDataActivity.this, getResources().getString(R.string.http_exception));
                    ToastUtils.shortToast(MotionShareActivity.this, R.string.upload_photo_false);
                    break;
                case InnerMessageConfig.COMMON_UPLOAD_MEDIA_MESSAGE_FALSE:
                    try {
                        String errorMsg = JsonFalseUtils.onlyErrorCodeResult((String) msg.obj);
                        if (errorMsg != null)
                            ToastUtils.shortToast(MotionShareActivity.this, errorMsg);
                        LoadingProgressDialog.Dissmiss();
                    } catch (JSONException e) {
                        mHandler.sendEmptyMessage(InnerMessageConfig.COMMON_GET_CODE_MESSAGE_EXCEPTION);
                        e.printStackTrace();
                    }


                case InnerMessageConfig.USER_TRAIN_UPLOAD_MESSAGE_SUCCESS:
                    LoadingProgressDialog.Dissmiss();
                    //解析正确参数-内容在msg.obj
                    //成功提示用户头像上传成功
                    break;
                case InnerMessageConfig.USER_TRAIN_UPLOAD_MESSAGE_EXCEPTION:
                    LoadingProgressDialog.Dissmiss();
                    //ToastUtils.shortToast(RegisterDataActivity.this, getResources().getString(R.string.http_exception));
                    ToastUtils.shortToast(MotionShareActivity.this, R.string.upload_photo_false);
                    break;
                case InnerMessageConfig.USER_TRAIN_UPLOAD_MESSAGE_FALSE:
                    try {
                        String errorMsg = JsonFalseUtils.onlyErrorCodeResult((String) msg.obj);
                        if (errorMsg != null)
                            ToastUtils.shortToast(MotionShareActivity.this, errorMsg);
                        LoadingProgressDialog.Dissmiss();
                    } catch (JSONException e) {
                        mHandler.sendEmptyMessage(InnerMessageConfig.USER_TRAIN_UPLOAD_MESSAGE_EXCEPTION);
                        e.printStackTrace();
                    }

                    break;
            }
        }
    };

    private CommonManager commonManager;
    private UserManager userManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_motion_share);
        commonManager = new CommonManager(MotionShareActivity.this, mHandler);
        userManager = new UserManager(MotionShareActivity.this, mHandler);
        initIntentData();
        setView();
        initData();
        setPathOfParticleView();
        setJumpPicView();
        initPathOfParticle();
        initJumpPic();
    }
    Bitmap mShareBitmap;
    private void setView() {
        mShareView = (ScrollView) findViewById(R.id.motion_share_view);
        mTVName = (TextView) findViewById(R.id.user_data_name);
        mTVTimes = (TextView) findViewById(R.id.user_data_time);
        mIVHead = (ImageView) findViewById(R.id.user_data_head_iv);

        mTVVerTicalJumpAvgValue = (TextView) findViewById(R.id.share_vertical_jump_avg_value);
        mTVAverageVerticalJumpTime = (TextView) findViewById(R.id.share_average_vertical_jump_time);
        mTVAvgrageChangeTouchDownTime = (TextView) findViewById(R.id.share_average_change_touch_down_time);
        mTVAboutHandler = (TextView) findViewById(R.id.share_about_handler);
        mTVStadiumType = (TextView) findViewById(R.id.share_stadium_type);
        mTVRunDistance = (TextView) findViewById(R.id.share_run_distance);
        mTVAvgSpeed = (TextView) findViewById(R.id.share_avg_speed);
        mTVPlayTime = (TextView) findViewById(R.id.share_play_time);
        mTVCalorie = (TextView) findViewById(R.id.share_calorie);
        mTVTouchDownAngle = (TextView) findViewById(R.id.share_touch_down_angle);
        mTVTouchType = (TextView) findViewById(R.id.share_touch_type);

        mBtnShare = (Button) findViewById(R.id.share_submit);
        mBtnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CommonUtils.isNetworkAvailable(MotionShareActivity.this)) {
                    if(isUploadImage){
                        ShareDialog share = new ShareDialog(MotionShareActivity.this, userManager, id, img);
                        share.show();
                    }else {
                        //上传媒体接口
                        mShareBitmap = PictureUtils.getBitmapByView(mShareView);
                        LogUtils.showLog("getViewBitmap", "mShareBitmao.getAllocationByteCount():"
                                + mShareBitmap.getAllocationByteCount());
                        //上传接口
                        uploadMediaData(mShareBitmap);
                        //然后得到结果（如果上传失败则不提示）
                    }
                } else {
                    mHandler.sendEmptyMessage(0);
                }
            }
        });
        mBtnNotShare = (Button) findViewById(R.id.share_cancel);
        mBtnNotShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
//        if (Build.VERSION.SDK_INT >= 23) {
//            String[] mPermissionList = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                    Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CALL_PHONE,
//                    Manifest.permission.READ_LOGS, Manifest.permission.READ_PHONE_STATE,
//                    Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.SET_DEBUG_APP,
//                    Manifest.permission.SYSTEM_ALERT_WINDOW, Manifest.permission.GET_ACCOUNTS,
//                    Manifest.permission.WRITE_APN_SETTINGS};
//            ActivityCompat.requestPermissions(this, mPermissionList, 123);
//        }
    }
//    @Override
//    public void onRequestPermissionsResult(int requestCode,
//                                           String permissions[], int[] grantResults) {
//        if(requestCode==123){
//
//        }
//
//    }
    private void uploadMediaData(Bitmap bitmap) {
        LoadingProgressDialog.show(MotionShareActivity.this, false, true, 30000);
        //Do upload media
        String file_full_path = "";
        try {
            file_full_path = saveMyBitmap(bitmap);
            commonManager.uploadMedia(TypeUtils.UPLOAD_MEDIA_TYPE_HEAD_PIC, file_full_path);
        } catch (IOException e) {
            mHandler.sendEmptyMessage(InnerMessageConfig.COMMON_UPLOAD_MEDIA_MESSAGE_EXCEPTION);
            e.printStackTrace();
        }
    }

    public String saveMyBitmap(Bitmap bitmap) throws IOException {
        String path = UploadPictureHasZoomUtils.IMAGE_FILE_PATH;
        File f = new File(path);
        f.createNewFile();
        FileOutputStream fOut = null;
        try {
            fOut = new FileOutputStream(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
        try {
            fOut.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return path;
    }

    private void initData() {
        //赋值Data
        //获取数据库用户信息
        UserInfoData userInfoData = DatabaseService.findUserInfo(JordanApplication.getUsername(MotionShareActivity.this));
        if (userInfoData != null) {
            if (!userInfoData.getNick().equals("null"))
                mTVName.setText(userInfoData.getNick());

            if (mStartTime != null) {
                startTime = Long.valueOf(mStartTime) * 1000;
            }
            mTVTimes.setText(sdf.format(new Date(startTime)));


            //ImageLoader导入图片
            if (!userInfoData.getImg().equals("null")) {
                LogUtils.showLog("IMAGEID", "userInfoData.getImgId():" + userInfoData.getImgId());
                DisplayImageOptions options = new DisplayImageOptions.Builder()
                        .showImageOnLoading(R.mipmap.img_jordan_logo)
                        .showImageForEmptyUri(R.mipmap.img_jordan_logo)
                        .showImageOnFail(R.mipmap.img_jordan_logo).cacheInMemory(true)
                        .cacheOnDisk(true).considerExifParams(true).build();
                ImageLoader.getInstance().displayImage(userInfoData.getImg(), mIVHead, options);
            }
        }
        mTVVerTicalJumpAvgValue.setText(verTicalJumpAvgValue+" cm");//纵跳均值
        mTVAverageVerticalJumpTime.setText(averageVerticalJumpTime+" ms");//平均滞空
        mTVAvgrageChangeTouchDownTime.setText(avgrageChangeTouchDownTime+" ms");//变向反应时间
        mTVAboutHandler.setText(aboutHandler);//手感如何
        mTVStadiumType.setText(stadiumType);//球场材质
        mTVRunDistance.setText(runDistance+" km");//跑动距离
        mTVAvgSpeed.setText(avgSpeed+" km/h");//跑到均速
        mTVPlayTime.setText(playTime);//打球时间
        mTVCalorie.setText(calorie+" kCal");//燃烧卡路里
        mTVTouchDownAngle.setText(touchDownAngle);//平均翻转角度
        mTVTouchType.setText(touchType);//翻转类型
    }

    private void initIntentData() {
        id = getIntent().getStringExtra("source");
        mTrail = getIntent().getStringExtra("trail");
        mBallType = getIntent().getStringExtra("ballType");
        mFooter = getIntent().getStringExtra("footer");
        mVerJumpPoint = getIntent().getStringExtra("verJumpPoint");
        mStartTime = getIntent().getStringExtra("startTime");
        verTicalJumpAvgValue = getIntent().getStringExtra("verTicalJumpAvgValue");//纵跳均值
        averageVerticalJumpTime = getIntent().getStringExtra("averageVerticalJumpTime");//平均滞空
        avgrageChangeTouchDownTime = getIntent().getStringExtra("avgrageChangeTouchDownTime");//变向反应时间
        aboutHandler = getIntent().getStringExtra("aboutHandler");//手感如何
        stadiumType = getIntent().getStringExtra("stadiumType");//球场材质
        runDistance = getIntent().getStringExtra("runDistance");//跑动距离
        avgSpeed = getIntent().getStringExtra("avgSpeed");//跑到均速
        playTime = getIntent().getStringExtra("playTime");//打球时间
        calorie = getIntent().getStringExtra("calorie");//燃烧卡路里
        touchDownAngle = getIntent().getStringExtra("touchDownAngle");//平均翻转角度
        touchType = getIntent().getStringExtra("touchType");//翻转类型
    }

    private TextView mTvAbroadAngle;
    private TextView mTvNormalAngle;
    private TextView mTvWithinAngle;
    private ImageView mIvAbroadAngle;
    private ImageView mIvNormalAngle;
    private ImageView mIvWithinAngle;
    private TextView mTvNowBluetoothFoot;

    /**
     * 初始化纵跳分析图片
     */
    private void setJumpPicView() {
        mTvAbroadAngle = (TextView) findViewById(R.id.foot_abroad_angle);
        mTvNormalAngle = (TextView) findViewById(R.id.foot_normal_angle);
        mTvWithinAngle = (TextView) findViewById(R.id.foot_within_angle);
        mIvAbroadAngle = (ImageView) findViewById(R.id.foot_abroad_iv);
        mIvNormalAngle = (ImageView) findViewById(R.id.foot_normal_iv);
        mIvWithinAngle = (ImageView) findViewById(R.id.foot_within_iv);
        mTvNowBluetoothFoot = (TextView) findViewById(R.id.tv_now_bluetooth_foot);


    }

    /**
     * 赋值纵跳分析图片
     */
    private void initJumpPic() {
        if (mFooter.equals("R")) {
            mTvNowBluetoothFoot.setText("(图中为右脚)");
            mIvAbroadAngle.setBackgroundResource(R.mipmap.foot_right_abroad);
            mIvNormalAngle.setBackgroundResource(R.mipmap.foot_right_normal);
            mIvWithinAngle.setBackgroundResource(R.mipmap.foot_right_within);
        } else {
            mTvNowBluetoothFoot.setText("(图中为左脚)");
            mIvAbroadAngle.setBackgroundResource(R.mipmap.foot_left_abroad);
            mIvNormalAngle.setBackgroundResource(R.mipmap.foot_left_normal);
            mIvWithinAngle.setBackgroundResource(R.mipmap.foot_left_within);
        }
        int abroad = 0;
        int normal = 0;
        int within = 0;
        int all = 0;
        if (mVerJumpPoint.contains(",")) {
            LogUtils.showLog("progresses:", "contains");
            String[] jumps = null;
            if (mVerJumpPoint.substring(0, 1).equals(",")) {
                jumps = mVerJumpPoint.substring(1).split(",");
            } else {
                jumps = mVerJumpPoint.split(",");
            }
            LogUtils.showLog("progresses:", "jumps.length:" + jumps.length);
            for (int i = 0; i < jumps.length; i++) {
                String key = jumps[i];
                LogUtils.showLog("progresses:", "key:" + key);
                if (HexadecimalUtils.formatFloatData(key.substring(24, 32)) > 0) {
                    abroad = abroad + 1;
                    LogUtils.showLog("progresses:", "abroad:" + abroad);
                } else if (HexadecimalUtils.formatFloatData(key.substring(24, 32)) < 0) {
                    within = within + 1;
                    LogUtils.showLog("progresses:", "within:" + within);
                } else {
                    normal = normal + 1;
                    LogUtils.showLog("progresses:", "normal:" + normal);
                }
                all = all + 1;
                LogUtils.showLog("progresses:", "all:" + all);
            }
            if (mFooter.equals("R")) {
                LogUtils.showLog("progresses:", "mTvAbroadAngle");
                mTvAbroadAngle.setText((abroad * 100 / all) + "%");
                LogUtils.showLog("progresses:", "mTvNormalAngle");
                mTvNormalAngle.setText((normal * 100 / all) + "%");
                LogUtils.showLog("progresses:", "mTvWithinAngle");
                mTvWithinAngle.setText((within * 100 / all) + "%");
            } else {
                LogUtils.showLog("progresses:", "mTvAbroadAngle");
                mTvAbroadAngle.setText((within * 100 / all) + "%");
                LogUtils.showLog("progresses:", "mTvNormalAngle");
                mTvNormalAngle.setText((normal * 100 / all) + "%");
                LogUtils.showLog("progresses:", "mTvWithinAngle");
                mTvWithinAngle.setText((abroad * 100 / all) + "%");
            }
        } else if (!mVerJumpPoint.equals("")) {
            String key = mVerJumpPoint;
            LogUtils.showLog("progresses:", "key:" + key);
            if (HexadecimalUtils.formatFloatData(key.substring(24, 32)) > 0) {
                abroad = abroad + 1;
                LogUtils.showLog("progresses:", "abroad:" + abroad);
            } else if (HexadecimalUtils.formatFloatData(key.substring(24, 32)) < 0) {
                within = within + 1;
                LogUtils.showLog("progresses:", "within:" + within);
            } else {
                normal = normal + 1;
                LogUtils.showLog("progresses:", "normal:" + normal);
            }
            all = all + 1;
            LogUtils.showLog("progresses:", "all:" + all);
            if (mFooter.equals("R")) {
                LogUtils.showLog("progresses:", "mTvAbroadAngle");
                mTvAbroadAngle.setText((abroad * 100 / all) + "%");
                LogUtils.showLog("progresses:", "mTvNormalAngle");
                mTvNormalAngle.setText((normal * 100 / all) + "%");
                LogUtils.showLog("progresses:", "mTvWithinAngle");
                mTvWithinAngle.setText((within * 100 / all) + "%");
            } else {
                LogUtils.showLog("progresses:", "mTvAbroadAngle");
                mTvAbroadAngle.setText((within * 100 / all) + "%");
                LogUtils.showLog("progresses:", "mTvNormalAngle");
                mTvNormalAngle.setText((normal * 100 / all) + "%");
                LogUtils.showLog("progresses:", "mTvWithinAngle");
                mTvWithinAngle.setText((abroad * 100 / all) + "%");
            }
        }
    }


    ArrayList<Map<Integer, SpeedData>> mapList = new ArrayList<Map<Integer, SpeedData>>();
    private RelativeLayout mRlPathOfParticle;
    private PathOfParticleGridView mGvPathOfParticle;//轨迹图
    private PathOfParticleAdapter mPathOfParticleAdapter;
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
        int height = mRlPathOfParticle.getMeasuredHeight();
        int width = mRlPathOfParticle.getMeasuredWidth();
        LogUtils.showLog("particlelist", "mRlPathOfParticle.getMeasuredHeight():" + height);
        mPathOfParticleAdapter = new PathOfParticleAdapter(MotionShareActivity.this, list,width, height, maxX, maxS, minS);
        mGvPathOfParticle.setAdapter(mPathOfParticleAdapter);
        LogUtils.showLog("particlelist", "setPathOfParticleView over");

    }

    /**
     * 赋值轨迹图数据
     */
    private void initPathOfParticle() {
        LogUtils.showLog("particlelists", "initPathOfParticle");
        mapList = new ArrayList<Map<Integer, SpeedData>>();
        for (int i = 0; i < 28 * 15; i++) {
            Map<Integer, SpeedData> map = new HashMap<Integer, SpeedData>();
            mapList.add(map);
        }
        LogUtils.showLog("particlelists", "setPathOfParticleView mapList:" + mapList.size());
        ArrayList<PathOfParticleData> list = new ArrayList<PathOfParticleData>();
        //取真实数据tark转换成数组数据
        String tark = mTrail;
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
                    LogUtils.showLog("particlelistStart", "x:" + trakxf2 + "|y" + trakYf);
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
                        if (mBallType.equals("2")) {
                            full = 0;
                        }
                        Mat.data_analysis_v3(xy, xy.size(), pxy, full);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    pxy = xy;
                }
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
                    LogUtils.showLog("particlelistOver", "x:" + pxy.get(i).getX() + "|y" + pxy.get(i).getY());
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
        Log.i("UMShareAPI", "requestCode:" + requestCode);
        Log.i("UMShareAPI", "resultCode:" + resultCode);
    }
}
