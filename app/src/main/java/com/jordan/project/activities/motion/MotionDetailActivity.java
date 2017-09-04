package com.jordan.project.activities.motion;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.safari.httplibs.config.InnerMessageConfig;
import com.jordan.project.JordanApplication;
import com.jordan.project.R;
import com.jordan.project.activities.adapter.MainFragmentPagerAdapter;
import com.jordan.project.activities.fragment.MotionDataFragment;
import com.jordan.project.activities.fragment.MotionOverviewFragment;
import com.jordan.project.activities.fragment.MotionPicTableFragment;
import com.jordan.project.data.MotionDetailData;
import com.jordan.project.database.DatabaseService;
import com.jordan.project.entity.UserInfoData;
import com.jordan.project.utils.JsonFalseUtils;
import com.jordan.project.utils.JsonSuccessUtils;
import com.jordan.project.utils.LogUtils;
import com.jordan.project.utils.ToastUtils;
import com.jordan.project.widget.LoadingProgressDialog;
import com.jordan.usersystemlibrary.UserManager;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONException;

import java.math.BigDecimal;
import java.util.ArrayList;

public class MotionDetailActivity extends FragmentActivity implements View.OnClickListener{

    private ViewPager viewPager;
    private MainFragmentPagerAdapter adapter;
    int currentPageIndex=0;


    private RelativeLayout mRLOverView,mRLData,mRLPicTable;
    private TextView mTVOverView,mTVData,mTVPicTable;
    private ImageView mIVOverView,mIVData,mIVPicTable;
    private TextView tvName;
    private TextView tvPosition;
    private TextView tvTime;
    private ImageView mIVHead;
    private String time="";

    private Handler mMotionDetailHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case InnerMessageConfig.USER_MOTION_DETAIL_MESSAGE_SUCCESS:
                    //解析正确参数-内容在msg.obj
                    LogUtils.showLog("Result", "USER_MOTION_DETAIL_MESSAGE_SUCCESS result:"+(String)msg.obj);
                    try {
                        MotionDetailData motionDetailData=JsonSuccessUtils.getMotionDetail((String)msg.obj);
                        //存储数据库
                        DatabaseService.createMotionDetail(motionDetailData.getServiceID(),motionDetailData.getLongitude(),motionDetailData.getLatitude(),
                                motionDetailData.getAddress(),motionDetailData.getBeginTime(),motionDetailData.getSpend(),motionDetailData.getPicture(),
                                motionDetailData.getEndTime(),motionDetailData.getTotalDist(),motionDetailData.getTotalStep(),motionDetailData.getTotalHorDist(),
                                motionDetailData.getTotalVerDist(),motionDetailData.getTotalTime(),motionDetailData.getTotalActiveTime(),motionDetailData.getActiveRate(),
                                motionDetailData.getAvgSpeed(),motionDetailData.getMaxSpeed(),motionDetailData.getSpurtCount(),motionDetailData.getBreakinCount(),
                                motionDetailData.getBreakinAvgTime(),motionDetailData.getVerJumpCount(),motionDetailData.getVerJumpAvgHigh(),motionDetailData.getAvgHoverTime(),
                                motionDetailData.getAvgTouchAngle(),motionDetailData.getTouchType(),motionDetailData.getPerfRank(),motionDetailData.getRunRank(),
                                motionDetailData.getBreakRank(),motionDetailData.getBounceRank(),motionDetailData.getAvgShotDist(),motionDetailData.getMaxShotDist(),
                                motionDetailData.getHandle(),motionDetailData.getCrlorie(),motionDetailData.getTrail(),motionDetailData.getVerJumpPoint(),
                                motionDetailData.getFooter(),motionDetailData.getBallType(),motionDetailData.getStadiumType());
                        LoadingProgressDialog.Dissmiss();
                    } catch (JSONException e) {
                        mMotionDetailHandler.sendEmptyMessage(InnerMessageConfig.USER_MOTION_DETAIL_MESSAGE_EXCEPTION);
                        e.printStackTrace();
                    }
                    break;
                case InnerMessageConfig.USER_MOTION_DETAIL_MESSAGE_EXCEPTION:
                    ToastUtils.shortToast(MotionDetailActivity.this,getResources().getString(R.string.http_exception));
                    LoadingProgressDialog.Dissmiss();
                    break;
                case InnerMessageConfig.USER_MOTION_DETAIL_MESSAGE_FALSE:
                    try {
                        String errorMsg= JsonFalseUtils.onlyErrorCodeResult((String)msg.obj);
                        if(errorMsg!=null)
                            ToastUtils.shortToast(MotionDetailActivity.this,errorMsg);
                        LoadingProgressDialog.Dissmiss();
                    } catch (JSONException e) {
                        mMotionDetailHandler.sendEmptyMessage(InnerMessageConfig.USER_MOTION_DETAIL_MESSAGE_EXCEPTION);
                        e.printStackTrace();
                    }
                    break;
            };
        }
    };
    private UserManager userManager;
    private String serviceId = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_motion_detail);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.motion_detail_title);
        userManager=new UserManager(MotionDetailActivity.this,mMotionDetailHandler);
        serviceId = getIntent().getStringExtra("id");
        time = getIntent().getStringExtra("time");
        TextView mTvTitle = (TextView) findViewById(R.id.title_text);
        mTvTitle.setText(getResources().getString(R.string.common_motion_data));
        RelativeLayout mRLBack=(RelativeLayout)findViewById(R.id.title_back);
        mRLBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ImageView mIVMore=(ImageView)findViewById(R.id.title_more);
        mIVMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MotionDetailData motionDetailData = DatabaseService.findMotionDetail(serviceId);
                if(motionDetailData!=null) {
                    //然后跳到分享内容界面（新Activity)
                    Intent intent = new Intent(MotionDetailActivity.this, MotionShareActivity.class);
                    intent.putExtra("source","2");
                    intent.putExtra("trail", motionDetailData.getTrail());
                    intent.putExtra("ballType", motionDetailData.getBallType());
                    intent.putExtra("footer", motionDetailData.getFooter());
                    intent.putExtra("verJumpPoint", motionDetailData.getVerJumpPoint());
                    intent.putExtra("startTime", motionDetailData.getBeginTime());

                    intent.putExtra("verTicalJumpAvgValue", new BigDecimal((Double.valueOf(
                            motionDetailData.getVerJumpAvgHigh()) * 100)).setScale(2, BigDecimal.ROUND_DOWN).toString());//纵跳均值
                    intent.putExtra("averageVerticalJumpTime", new BigDecimal((Double.valueOf(
                            motionDetailData.getAvgHoverTime()) * 1000)).setScale(2, BigDecimal.ROUND_DOWN).toString());//平均滞空
                    intent.putExtra("avgrageChangeTouchDownTime", new BigDecimal((Double.valueOf(
                            motionDetailData.getBreakinAvgTime()) * 1000)).setScale(2, BigDecimal.ROUND_DOWN).toString());//变向反应时间
                    int handle = Integer.parseInt(motionDetailData.getHandle());
                    String handles = "一般";
                    switch (handle) {
                        case 1:
                            handles = "很糟";
                            break;
                        case 2:
                            handles = "一般";
                            break;
                        case 3:
                            handles = "还好";
                            break;
                        case 4:
                            handles = "非常好";
                            break;
                        case 5:
                            handles = "逆天啦";
                            break;
                    }
                    intent.putExtra("aboutHandler", handles);//手感如何
                    intent.putExtra("stadiumType", motionDetailData.getStadiumType());//球场材质
                    intent.putExtra("runDistance", new BigDecimal((Double.valueOf(
                            motionDetailData.getTotalDist()) / 1000)).setScale(2, BigDecimal.ROUND_DOWN).toString());//跑动距离
                    intent.putExtra("avgSpeed", (new BigDecimal((Double.valueOf(
                            motionDetailData.getAvgSpeed()) * 3.6)).setScale(2, BigDecimal.ROUND_DOWN).toString()));//跑到均速
                    String playTime = "00:00:00";
                    double totalTime = Double.valueOf(motionDetailData.getTotalTime());
                    int hour = (int) (totalTime / 3600);
                    int min = (int) (totalTime / 60);
                    int s = (int) (totalTime % 60);
                    String hours = "00";
                    String mins = "00";
                    String ss = "00";
                    if (hour < 10) {
                        hours = "0" + hour;
                    } else {
                        hours = "" + hour;
                    }
                    if (min < 10) {
                        mins = "0" + min;
                    } else {
                        mins = "" + min;
                    }
                    if (s < 10) {
                        ss = "0" + s;
                    } else {
                        ss = "" + s;
                    }
                    playTime = hours +":"+ mins+":" + ss;
                    intent.putExtra("playTime", playTime);//打球时间
                    intent.putExtra("calorie", motionDetailData.getCrlorie());//燃烧卡路里
                    intent.putExtra("touchDownAngle", new BigDecimal((Double.valueOf(
                            motionDetailData.getAvgTouchAngle()))).setScale(2, BigDecimal.ROUND_DOWN).toString());//平均翻转角度
                    int mTouchType = Integer.parseInt(motionDetailData.getTouchType());
                    String touchDownType = "";
                    if (motionDetailData.getFooter().equals("R")) {
                        if (mTouchType > 0) {
                            touchDownType = "外翻";
                        } else if (mTouchType < 0) {
                            touchDownType = "内翻";
                        } else {
                            touchDownType = "正常";
                        }
                    } else {
                        if (mTouchType > 0) {
                            touchDownType = "内翻";
                        } else if (mTouchType < 0) {
                            touchDownType = "外翻";
                        } else {
                            touchDownType = "正常";
                        }
                    }
                    intent.putExtra("touchType", touchDownType);//翻转类型
                    startActivity(intent);
                }
            }
        });
        setView();
        setListener();
        MotionDetailData motionDetailData = DatabaseService.findMotionDetail(serviceId);
        if (motionDetailData == null) {
            doDetail();
        }
    }

    private void doDetail() {
        LoadingProgressDialog.show(MotionDetailActivity.this, false, true, 30000);
        userManager.moveDetail(serviceId);
    }

    /**
     * 设置介绍界面
     */
    private void setView() {
        viewPager=(ViewPager)findViewById(R.id.motion_view_pager);
        viewPager.setOffscreenPageLimit(2);
        ArrayList<Fragment> list=new ArrayList<Fragment>();
        list.add(new MotionOverviewFragment(serviceId));
        list.add(new MotionDataFragment(serviceId));
        list.add(new MotionPicTableFragment(serviceId));
        //list.add(new FourthFragment());
        adapter=new MainFragmentPagerAdapter(getSupportFragmentManager(), list);
        viewPager.setAdapter(adapter);

        mRLOverView = (RelativeLayout)findViewById(R.id.motion_detail_to_over_view_rl);
        mRLData = (RelativeLayout)findViewById(R.id.motion_detail_to_data_rl);
        mRLPicTable = (RelativeLayout)findViewById(R.id.motion_detail_to_pic_table_rl);
        mTVOverView = (TextView) findViewById(R.id.motion_detail_to_over_view_text);
        mTVData = (TextView) findViewById(R.id.motion_detail_to_data_text);
        mTVPicTable = (TextView) findViewById(R.id.motion_detail_to_pic_table_text);
        mIVOverView = (ImageView) findViewById(R.id.motion_detail_to_over_view_iv);
        mIVData = (ImageView) findViewById(R.id.motion_detail_to_data_iv);
        mIVPicTable = (ImageView) findViewById(R.id.motion_detail_to_pic_table_iv);
        mIVHead = (ImageView) findViewById(R.id.iv_head);
        viewPager.setCurrentItem(0);
        currentPageIndex = 0;
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {
                viewPager.setCurrentItem(arg0);
                currentPageIndex = arg0;
                updateBtnColor();

            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
                // TODO Auto-generated method stub

            }
        });
        tvName=(TextView)findViewById(R.id.tv_name);
        tvPosition=(TextView)findViewById(R.id.tv_position);
        tvTime=(TextView)findViewById(R.id.tv_time);
        tvName.setText(JordanApplication.getUsername(MotionDetailActivity.this));
        initData();
    }
    private void initData() {
        tvTime.setText(time);
        //赋值Data
        //获取数据库用户信息
        UserInfoData userInfoData = DatabaseService.findUserInfo(JordanApplication.getUsername(MotionDetailActivity.this));
        if(userInfoData!=null) {
            if (!userInfoData.getNick().equals("null"))
                tvName.setText(getResources().getString(R.string.common_unit_nick)+userInfoData.getNick());
            if (!userInfoData.getPosition().equals("null"))
                tvPosition.setText(getResources().getString(R.string.common_unit_position)+userInfoData.getPosition());


            //ImageLoader导入图片
            if (!userInfoData.getImg().equals("null")) {
                DisplayImageOptions options = new DisplayImageOptions.Builder()
                        .showImageOnLoading(R.mipmap.img_jordan_logo)
                        .showImageForEmptyUri(R.mipmap.img_jordan_logo)
                        .showImageOnFail(R.mipmap.img_jordan_logo).cacheInMemory(true)
                        .cacheOnDisk(true).considerExifParams(true).build();
                ImageLoader.getInstance().displayImage(userInfoData.getImg(), mIVHead, options);
            }
        }
    }

    private void setListener() {
        mRLOverView.setOnClickListener(this);
        mRLData.setOnClickListener(this);
        mRLPicTable.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.motion_detail_to_over_view_rl:
                viewPager.setCurrentItem(0);
                currentPageIndex = 0;
                updateBtnColor();
                break;
            case R.id.motion_detail_to_data_rl:
                viewPager.setCurrentItem(1);
                currentPageIndex = 1;
                updateBtnColor();
                break;
            case R.id.motion_detail_to_pic_table_rl:
                viewPager.setCurrentItem(2);
                currentPageIndex = 2;
                updateBtnColor();
                break;
        }

    }
    private void updateBtnColor(){
        switch (currentPageIndex) {
            case 0:
                mIVOverView.setVisibility(View.VISIBLE);
                mIVData.setVisibility(View.GONE);
                mIVPicTable.setVisibility(View.GONE);
                break;
            case 1:
                mIVOverView.setVisibility(View.GONE);
                mIVData.setVisibility(View.VISIBLE);
                mIVPicTable.setVisibility(View.GONE);
                break;
            case 2:
                mIVOverView.setVisibility(View.GONE);
                mIVData.setVisibility(View.GONE);
                mIVPicTable.setVisibility(View.VISIBLE);
                break;

            default:
                break;
        }
    }
}
