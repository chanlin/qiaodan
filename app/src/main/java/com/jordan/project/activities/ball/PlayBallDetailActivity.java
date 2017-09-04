package com.jordan.project.activities.ball;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.safari.httplibs.config.InnerMessageConfig;
import com.jordan.project.JordanApplication;
import com.jordan.project.R;
import com.jordan.project.database.DatabaseService;
import com.jordan.project.entity.PlayBallListData;
import com.jordan.project.entity.ReachDetailData;
import com.jordan.project.utils.BaiduLocationUtils;
import com.jordan.project.utils.DistanceUtils;
import com.jordan.project.utils.JsonFalseUtils;
import com.jordan.project.utils.JsonSuccessUtils;
import com.jordan.project.utils.LogUtils;
import com.jordan.project.utils.MapViewUtil;
import com.jordan.project.utils.ToastUtils;
import com.jordan.project.widget.LoadingProgressDialog;
import com.jordan.usersystemlibrary.UserManager;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Date;

public class PlayBallDetailActivity extends Activity {

    ArrayList<PlayBallListData> mPlayBallList = new ArrayList<PlayBallListData>();
    private String createListPageNo = "1";
    private String createListPageSize = "1000";
    private MapView mMapView;
    private BaiduMap mBaiduMap;
    private String id = "1";
    ReachDetailData reachDetailData;
    private Button mBtnJoinPlayBall;//join_play_ball
    private TextView mTVAddress; //; // play_ball_detail_address
    private TextView mTVDistance; // play_ball_detail_distance
    private TextView mTVName; // user_data_name_text
    private TextView mTVTime; // user_data_time_text
    private TextView mTVRemarks; // user_data_remarks_text
    private TextView mTVAllPeople; // user_data_all_people_text
    private TextView mTVJoinPeople; // user_data_join_people_text
    private TextView mTVBallType; // user_data_ball_type_text
    private TextView mTVMobile; // user_data_mobile_text
    private TextView mTVStartTime; // user_data_start_time_text
    private ImageView mIVHead; //  user_data_head_iv
    private ImageView mIVSex; // user_data_sex_iv
    private TextView mTVRemark;//user_data_remark_text
    private TextView mTVSlogan;
    private String picture;

    private ImageView[] mIVUserHead = new ImageView[12];
    private TextView[] mTVUserName = new TextView[12];
    private TextView[] mTVUserMobile = new TextView[12];
    //联系人头像及其手机号 名字 信息？
    //请求用户请求信息
    private Handler mMainHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case InnerMessageConfig.USER_REACH_DETAIL_MESSAGE_SUCCESS:
                    //解析正确参数-内容在msg.obj
                    //保存登录返回数据
                    LogUtils.showLog("Result", "USER_REACH_DETAIL_MESSAGE_SUCCESS result:" + (String) msg.obj);

                    LoadingProgressDialog.Dissmiss();
                    //请求接口获取数据
                    try {
                        reachDetailData = JsonSuccessUtils.getReachDetail((String) msg.obj);
                    } catch (JSONException e) {
                        mMainHandler.sendEmptyMessage(InnerMessageConfig.USER_LOGIN_MESSAGE_EXCEPTION);
                        e.printStackTrace();
                    }
                    initData();

                    break;
                case InnerMessageConfig.USER_REACH_DETAIL_MESSAGE_EXCEPTION:
                    ToastUtils.shortToast(PlayBallDetailActivity.this, getResources().getString(R.string.http_exception));
                    break;
                case InnerMessageConfig.USER_REACH_DETAIL_MESSAGE_FALSE:
                    try {
                        String errorMsg = JsonFalseUtils.onlyErrorCodeResult((String) msg.obj);
                        if (errorMsg != null)
                            ToastUtils.shortToast(PlayBallDetailActivity.this, errorMsg);
                    } catch (JSONException e) {
                        mMainHandler.sendEmptyMessage(InnerMessageConfig.USER_LOGIN_MESSAGE_EXCEPTION);
                        e.printStackTrace();
                    }
                    break;


                case InnerMessageConfig.USER_REACH_JOIN_MESSAGE_SUCCESS:
                    //解析正确参数-内容在msg.obj
                    LogUtils.showLog("Result", "USER_REACH_JOIN_MESSAGE_SUCCESS result:" + (String) msg.obj);
                    //请求接口
                    doReachDetailTask();
                    //成功后请求列表
                    doJoinListTask();
                    break;
                case InnerMessageConfig.USER_REACH_JOIN_MESSAGE_EXCEPTION:
                    LoadingProgressDialog.Dissmiss();
                    ToastUtils.shortToast(PlayBallDetailActivity.this, getResources().getString(R.string.http_exception));
                    break;
                case InnerMessageConfig.USER_REACH_JOIN_MESSAGE_FALSE:
                    LoadingProgressDialog.Dissmiss();
                    try {
                        String errorMsg = JsonFalseUtils.onlyErrorCodeResult((String) msg.obj);
                        if (errorMsg != null)
                            ToastUtils.shortToast(PlayBallDetailActivity.this, errorMsg);
                    } catch (JSONException e) {
                        mMainHandler.sendEmptyMessage(InnerMessageConfig.USER_REACH_JOIN_MESSAGE_EXCEPTION);
                        e.printStackTrace();
                    }
                    break;


                case InnerMessageConfig.USER_JOIN_LIST_MESSAGE_SUCCESS:
                    //解析正确参数-内容在msg.obj
                    //保存登录返回数据
                    LogUtils.showLog("Result", "USER_JOIN_LIST_MESSAGE_SUCCESS result:" + (String) msg.obj);

                    //保存比赛信息
                    try {
                        mPlayBallList = JsonSuccessUtils.getJoinList((String) msg.obj);
                        //刷新list
                        //mPlayBallListAdapter.updateList(mPlayBallList);
                        //存储数据库
                        for (int i = 0; i < mPlayBallList.size(); i++) {
                            //先删除约球列表
                            //DatabaseService.deleteReachDetailInfo();
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
                                    JordanApplication.getUsername(PlayBallDetailActivity.this), playBallListData.getVipImg(),playBallListData.getSlogan());
                        }

                    } catch (JSONException e) {
                        mMainHandler.sendEmptyMessage(InnerMessageConfig.USER_LOGIN_MESSAGE_EXCEPTION);
                        e.printStackTrace();
                    }
                    ToastUtils.shortToast(PlayBallDetailActivity.this, getResources().getString(R.string.common_join_success));
                    break;
                case InnerMessageConfig.USER_JOIN_LIST_MESSAGE_FALSE:
                    //ToastUtils.shortToast(JoinPlayBallListActivity.this,getResources().getString(R.string.http_exception));
                    ToastUtils.shortToast(PlayBallDetailActivity.this, getResources().getString(R.string.common_join_success));
                    break;
                case InnerMessageConfig.USER_JOIN_LIST_MESSAGE_EXCEPTION:
//                    try {
//                        String errorMsg= JsonFalseUtils.onlyErrorCodeResult((String)msg.obj);
//                        if(errorMsg!=null)
//                            ToastUtils.shortToast(JoinPlayBallListActivity.this,errorMsg);
//                    } catch (JSONException e) {
//                        mMainHandler.sendEmptyMessage(InnerMessageConfig.USER_JOIN_LIST_MESSAGE_EXCEPTION);
//                        e.printStackTrace();
//                    }
                    ToastUtils.shortToast(PlayBallDetailActivity.this, getResources().getString(R.string.common_join_success));
                    break;
            }
            ;
        }
    };
    private UserManager userManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_play_ball_detail);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.common_back_title);
        userManager = new UserManager(PlayBallDetailActivity.this, mMainHandler);
        setView();
        //获取list的ID
        id = getIntent().getStringExtra("id");
        picture = getIntent().getStringExtra("picture");
        //请求接口
        doReachDetailTask();
        //参加比赛动作
    }

    private void initData() {
        //initData
        mTVAddress.setText(reachDetailData.getAddress());
        if (JordanApplication.nowLatitude == 0 && JordanApplication.nowLongitude == 0) {
            mTVDistance.setText("");
        } else {
            double dLongitude = Double.parseDouble(reachDetailData.getLongitude());
            double dLatitude = Double.parseDouble(reachDetailData.getLatitude());
            mTVDistance.setText(DistanceUtils.getDistanceUnit(JordanApplication.nowLongitude, JordanApplication.nowLatitude, dLongitude, dLatitude));//需要自己计算距离
        }
        mTVName.setText(reachDetailData.getContact());
        mTVTime.setText(
                JordanApplication.noyearsdf.format(new Date(Long.parseLong(reachDetailData.getCreateTime()) * 1000)));//时间使用createtime
        mTVRemarks.setText(getResources().getString(R.string.common_remark) + ":" + reachDetailData.getRemarks());
        mTVSlogan.setText(reachDetailData.getSlogan());
        mTVAllPeople.setText(getResources().getString(R.string.common_now_join_people) +
                reachDetailData.getJoin() +
                getResources().getString(R.string.common_people));
        mTVJoinPeople.setText(getResources().getString(R.string.common_all_people) +
                reachDetailData.getPeople() +
                getResources().getString(R.string.common_people));
        if (reachDetailData.getType().equals("1")) {
            mTVBallType.setText(
                    getResources().getString(R.string.common_play_type) +
                            getResources().getString(R.string.common_play_type_half_court));
        } else if (reachDetailData.getType().equals("2")) {
            mTVBallType.setText(
                    getResources().getString(R.string.common_play_type) +
                            getResources().getString(R.string.common_play_type_full_court));
        }
        mTVMobile.setText(
                getResources().getString(R.string.common_people_mobile) + reachDetailData.getMobile());
        mTVStartTime.setText(
                getResources().getString(R.string.common_time_unit) +
                        JordanApplication.sdf.format(new Date(Long.parseLong(reachDetailData.getTime()) * 1000)));


        LogUtils.showLog("reachDetailData", "picture:" + picture);
        //通过picture 或者 list传来的img
        if (!picture.equals("null")) {
            DisplayImageOptions options = new DisplayImageOptions.Builder()
                    .showImageOnLoading(R.mipmap.img_jordan_logo)
                    .showImageForEmptyUri(R.mipmap.img_jordan_logo)
                    .showImageOnFail(R.mipmap.img_jordan_logo).cacheInMemory(true)
                    .cacheOnDisk(true).considerExifParams(true).build();
            ImageLoader.getInstance().displayImage(picture, mIVHead, options);
        }

        //地图定位到提供的地理位置经纬度

        //刷新地图
        mBaiduMap.clear();
        //定义Maker坐标点
        LatLng ll = new LatLng(Double.valueOf(reachDetailData.getLatitude()),
                Double.valueOf(reachDetailData.getLongitude()));
        //构建Marker图标
        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromResource(R.mipmap.ic_map);
        //构建MarkerOption，用于在地图上添加Marker
        OverlayOptions option = new MarkerOptions()
                .position(ll)
                .icon(bitmap);
        //在地图上添加Marker，并显示
        mBaiduMap.addOverlay(option);
        //缩小的到当前位置
        MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(ll);
        MapStatusUpdate u = MapStatusUpdateFactory.zoomTo(18);
        try {
            mBaiduMap.animateMapStatus(u);
            mBaiduMap.animateMapStatus(msu);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //参加按钮判断是否已经参加过
        //mBtnJoinPlayBall=(Button)findViewById(R.id.join_play_ball);

        ArrayList<String> joinMobileList = new ArrayList<String>();
        joinMobileList = reachDetailData.getJoinMobiles();
        for (int i = 0; i < joinMobileList.size(); i++) {
            if (joinMobileList.get(i).equals(JordanApplication.getUsername(PlayBallDetailActivity.this))) {
                mBtnJoinPlayBall.setVisibility(View.GONE);
            }
        }


        LogUtils.showLog("reachDetailData", "reachDetailData:" + reachDetailData.toString());
        ArrayList<String> joinImgslist = reachDetailData.getJoinImgs();
        ArrayList<String> joinNicksList = reachDetailData.getJoinNicks();

        for (int i = 0; i < 10; i++) {
            if (i < joinImgslist.size()) {
                //根据图片的多少设置显示头像的多少
                mIVUserHead[i].setVisibility(View.VISIBLE);
                mTVUserName[i].setText(joinNicksList.get(i));
                mTVUserMobile[i].setText(joinMobileList.get(i));
//                if (!list.get(i).equals("null")||!list.get(i).equals("")) {
//                    DisplayImageOptions options = new DisplayImageOptions.Builder()
//                            .showImageOnLoading(R.mipmap.default_1)
//                            .showImageForEmptyUri(R.mipmap.default_1)
//                            .showImageOnFail(R.mipmap.default_1).cacheInMemory(true)
//                            .cacheOnDisk(true).considerExifParams(true).build();
//                    ImageLoader.getInstance().displayImage(list.get(i), mIVUserHead[i], options);
//                }else{
                DisplayImageOptions options = new DisplayImageOptions.Builder()
                        .showImageOnLoading(R.mipmap.default_1)
                        .showImageForEmptyUri(R.mipmap.default_1)
                        .showImageOnFail(R.mipmap.default_1).cacheInMemory(true)
                        .cacheOnDisk(true).considerExifParams(true).build();
                int no = (i + 1) % 5;
                switch (no) {
                    case 1:
                        break;
                    case 2:
                        options = new DisplayImageOptions.Builder()
                                .showImageOnLoading(R.mipmap.default_2)
                                .showImageForEmptyUri(R.mipmap.default_2)
                                .showImageOnFail(R.mipmap.default_2).cacheInMemory(true)
                                .cacheOnDisk(true).considerExifParams(true).build();
                        break;
                    case 3:
                        options = new DisplayImageOptions.Builder()
                                .showImageOnLoading(R.mipmap.default_3)
                                .showImageForEmptyUri(R.mipmap.default_3)
                                .showImageOnFail(R.mipmap.default_3).cacheInMemory(true)
                                .cacheOnDisk(true).considerExifParams(true).build();
                        break;
                    case 4:
                        options = new DisplayImageOptions.Builder()
                                .showImageOnLoading(R.mipmap.default_4)
                                .showImageForEmptyUri(R.mipmap.default_4)
                                .showImageOnFail(R.mipmap.default_4).cacheInMemory(true)
                                .cacheOnDisk(true).considerExifParams(true).build();
                        break;
                    case 5:
                        options = new DisplayImageOptions.Builder()
                                .showImageOnLoading(R.mipmap.default_5)
                                .showImageForEmptyUri(R.mipmap.default_5)
                                .showImageOnFail(R.mipmap.default_5).cacheInMemory(true)
                                .cacheOnDisk(true).considerExifParams(true).build();
                        break;
                }
                ImageLoader.getInstance().displayImage(joinImgslist.get(i), mIVUserHead[i], options);
                //}
            } else {
                //其他的隐藏
                mIVUserHead[i].setVisibility(View.GONE);
            }
        }

        //并且参加比赛后隐藏比赛按钮。

    }

    private void doReachDetailTask() {
        userManager.reachDetail(id);
    }

    private void doReachJoinTask() {
        userManager.reachJoin(id);
        LoadingProgressDialog.show(PlayBallDetailActivity.this, false, true, 30000);
    }

    private void doJoinListTask() {
        userManager.joinList(createListPageNo, createListPageSize);
    }


    private void setView() {
        TextView mTVTitle = (TextView) findViewById(R.id.title_text);
        mTVTitle.setText(getResources().getString(R.string.common_detail));

        RelativeLayout mRLBack = (RelativeLayout) findViewById(R.id.title_back);
        mRLBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mMapView = (MapView) findViewById(R.id.map_view);
        mBaiduMap = mMapView.getMap();
        //普通地图
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        mBaiduMap.setMyLocationEnabled(true);
        MapViewUtil.goneMap(mMapView);
        //如果已经有权限了直接初始化，否则再请求一次权限，并且成功的时候触发此方法
        BaiduLocationUtils.initLocation(PlayBallDetailActivity.this);
        mBtnJoinPlayBall = (Button) findViewById(R.id.join_play_ball);
        mTVAddress = (TextView) findViewById(R.id.play_ball_detail_address);
        mTVDistance = (TextView) findViewById(R.id.play_ball_detail_distance);
        mTVName = (TextView) findViewById(R.id.user_data_name_text);
        mTVTime = (TextView) findViewById(R.id.user_data_time_text);
        mTVRemarks = (TextView) findViewById(R.id.user_data_remark_text);
        mTVSlogan = (TextView) findViewById(R.id.user_data_slogan_text);
        mTVAllPeople = (TextView) findViewById(R.id.user_data_all_people_text);
        mTVJoinPeople = (TextView) findViewById(R.id.user_data_join_people_text);
        mTVBallType = (TextView) findViewById(R.id.user_data_ball_type_text);
        mTVMobile = (TextView) findViewById(R.id.user_data_mobile_text);
        mTVStartTime = (TextView) findViewById(R.id.user_data_start_time_text);
        mIVHead = (ImageView) findViewById(R.id.user_data_head_iv);
        mIVSex = (ImageView) findViewById(R.id.user_data_sex_iv);
        mIVSex.setVisibility(View.GONE);
        mBtnJoinPlayBall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doReachJoinTask();
            }
        });

        mIVUserHead[0] = (ImageView) findViewById(R.id.user_data_head_iv_001);
        mIVUserHead[1] = (ImageView) findViewById(R.id.user_data_head_iv_002);
        mIVUserHead[2] = (ImageView) findViewById(R.id.user_data_head_iv_003);
        mIVUserHead[3] = (ImageView) findViewById(R.id.user_data_head_iv_004);
        mIVUserHead[4] = (ImageView) findViewById(R.id.user_data_head_iv_005);
        mIVUserHead[5] = (ImageView) findViewById(R.id.user_data_head_iv_006);
        mIVUserHead[6] = (ImageView) findViewById(R.id.user_data_head_iv_007);
        mIVUserHead[7] = (ImageView) findViewById(R.id.user_data_head_iv_008);
        mIVUserHead[8] = (ImageView) findViewById(R.id.user_data_head_iv_009);
        mIVUserHead[9] = (ImageView) findViewById(R.id.user_data_head_iv_010);
        mIVUserHead[10] = (ImageView) findViewById(R.id.user_data_head_iv_011);
        mIVUserHead[11] = (ImageView) findViewById(R.id.user_data_head_iv_012);

        mTVUserName[0] = (TextView) findViewById(R.id.user_data_name_tv_001);
        mTVUserName[1] = (TextView) findViewById(R.id.user_data_name_tv_002);
        mTVUserName[2] = (TextView) findViewById(R.id.user_data_name_tv_003);
        mTVUserName[3] = (TextView) findViewById(R.id.user_data_name_tv_004);
        mTVUserName[4] = (TextView) findViewById(R.id.user_data_name_tv_005);
        mTVUserName[5] = (TextView) findViewById(R.id.user_data_name_tv_006);
        mTVUserName[6] = (TextView) findViewById(R.id.user_data_name_tv_007);
        mTVUserName[7] = (TextView) findViewById(R.id.user_data_name_tv_008);
        mTVUserName[8] = (TextView) findViewById(R.id.user_data_name_tv_009);
        mTVUserName[9] = (TextView) findViewById(R.id.user_data_name_tv_010);
        mTVUserName[10] = (TextView) findViewById(R.id.user_data_name_tv_011);
        mTVUserName[11] = (TextView) findViewById(R.id.user_data_name_tv_012);

        mTVUserMobile[0] = (TextView) findViewById(R.id.user_data_mobile_tv_001);
        mTVUserMobile[1] = (TextView) findViewById(R.id.user_data_mobile_tv_002);
        mTVUserMobile[2] = (TextView) findViewById(R.id.user_data_mobile_tv_003);
        mTVUserMobile[3] = (TextView) findViewById(R.id.user_data_mobile_tv_004);
        mTVUserMobile[4] = (TextView) findViewById(R.id.user_data_mobile_tv_005);
        mTVUserMobile[5] = (TextView) findViewById(R.id.user_data_mobile_tv_006);
        mTVUserMobile[6] = (TextView) findViewById(R.id.user_data_mobile_tv_007);
        mTVUserMobile[7] = (TextView) findViewById(R.id.user_data_mobile_tv_008);
        mTVUserMobile[8] = (TextView) findViewById(R.id.user_data_mobile_tv_009);
        mTVUserMobile[9] = (TextView) findViewById(R.id.user_data_mobile_tv_010);
        mTVUserMobile[10] = (TextView) findViewById(R.id.user_data_mobile_tv_011);
        mTVUserMobile[11] = (TextView) findViewById(R.id.user_data_mobile_tv_012);
        mTVRemark = (TextView) findViewById(R.id.user_data_remark_text);
        mTVRemark.setText("备注:");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }
}
