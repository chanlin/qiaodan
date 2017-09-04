package com.jordan.project.activities.fragment;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.jordan.project.R;
import com.jordan.project.content.MotionDetailObserver;
import com.jordan.project.data.MotionDetailData;
import com.jordan.project.database.DatabaseObject;
import com.jordan.project.database.DatabaseProvider;
import com.jordan.project.database.DatabaseService;
import com.jordan.project.utils.LogUtils;
import com.jordan.project.utils.MapViewUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.math.BigDecimal;

public class MotionOverviewFragment extends Fragment {

    private MapView mMapView;
    private BaiduMap mBaiduMap;
    private String serviceId;
    private TextView mTVTotalDistance;
    private TextView mTVTotalTime;
    private TextView mTVAddress;
    private ImageView mIVPlayBallPic;
    private TextView mTVHandler;
    private TextView mTVActiveRate;
    private TextView mTVAvgSpeed;
    private TextView mTVCalorie;
    private LinearLayout mLLBG;
    private MotionDetailObserver mMotionDetailObserver;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MotionDetailObserver.DATABASE_UPDATE:
                    LogUtils.showLog("btnClick", "MotionDetailObserver.DATABASE_UPDATE");
                    //读取数据库
                    initData();
                    break;

                default:
                    break;
            }
        }

        ;
    };

    private void registerContentObservers() {
        mMotionDetailObserver = new MotionDetailObserver(handler);
        Uri uri = Uri.parse("content://" + DatabaseProvider.AUTHOR + "/" + DatabaseObject.MOTION_DETAIL);
        getActivity().getContentResolver().registerContentObserver(uri, true,
                mMotionDetailObserver);
    }
    @SuppressLint({"NewApi", "ValidFragment"})
    public MotionOverviewFragment(){}
    @SuppressLint({"NewApi", "ValidFragment"})
    public MotionOverviewFragment(String serviceId) {
        this.serviceId = serviceId;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_motion_overview, null);
        mMapView = (MapView) view.findViewById(R.id.map_view);
        mBaiduMap = mMapView.getMap();
        //普通地图
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        mBaiduMap.setMyLocationEnabled(true);
        MapViewUtil.goneMap(mMapView);
        //BaiduLocationUtils.initLocation(getActivity());
        mTVTotalDistance = (TextView) view.findViewById(R.id.motion_overview_total_distance);
        mTVTotalTime = (TextView) view.findViewById(R.id.motion_overview_total_time);
        mTVAddress = (TextView) view.findViewById(R.id.motion_overview_play_ball_address);
        mIVPlayBallPic = (ImageView) view.findViewById(R.id.motion_overview_play_ball_pic);
        mTVHandler = (TextView) view.findViewById(R.id.motion_overview_handler);
        mTVActiveRate = (TextView) view.findViewById(R.id.motion_overview_active_rate);
        mTVAvgSpeed = (TextView) view.findViewById(R.id.motion_overview_avg_speed);
        mTVCalorie = (TextView) view.findViewById(R.id.motion_overview_calorie);
        mLLBG = (LinearLayout) view.findViewById(R.id.ll_bg);
//		 mTVAddress.setText(address);
        registerContentObservers();
        initData();

        return view;
    }

    private void initData() {
        MotionDetailData motionDetailData = DatabaseService.findMotionDetail(serviceId);

        if (motionDetailData != null) {
            //mBaiduMap.clear();
            LogUtils.showLog("MotionOverviewFragment", "motionDetailData:" + motionDetailData.toString());
            if (mBaiduMap != null && !motionDetailData.getLatitude().equals("") && !motionDetailData.getLongitude().equals("")) {
                LogUtils.showLog("MotionOverviewFragment", "进入 if ");
                //地图定位到提供的地理位置经纬度
                //刷新地图
                //定义Maker坐标点
                LatLng ll = new LatLng(Double.valueOf(motionDetailData.getLatitude()),
                        Double.valueOf(motionDetailData.getLongitude()));
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
                    mBaiduMap.animateMapStatus(msu);
                    mBaiduMap.animateMapStatus(u);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            mLLBG.setVisibility(View.VISIBLE);
            mTVTotalDistance.setText(new BigDecimal((Double.valueOf(motionDetailData.getTotalDist()) / 1000)).setScale(2, BigDecimal.ROUND_DOWN).toString());
            if (motionDetailData.getTotalTime().equals(""))
                motionDetailData.setTotalTime("0");
            double totalTime = Double.valueOf(motionDetailData.getTotalTime());
            int min = (int) (totalTime / 60);
            int s = (int) (totalTime % 60);
            if (min > 0) {
                mTVTotalTime.setText(min + "分" + s + "秒");
            } else {
                mTVTotalTime.setText(s + "秒");
            }
            mTVAddress.setText(motionDetailData.getAddress());
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
            mTVHandler.setText("我的手感：" + handles);
            mTVActiveRate.setText((new BigDecimal((Double.valueOf(motionDetailData.getActiveRate())*100))).setScale(2, BigDecimal.ROUND_DOWN).toString() + "%");
            mTVAvgSpeed.setText(new BigDecimal((Double.valueOf(motionDetailData.getAvgSpeed()) * 3.6)).setScale(2, BigDecimal.ROUND_DOWN).toString());
            mTVCalorie.setText(motionDetailData.getCrlorie());
            if (!motionDetailData.getPicture().equals("null") && !motionDetailData.getPicture().equals("")) {
                mIVPlayBallPic.setVisibility(View.VISIBLE);
                DisplayImageOptions options = new DisplayImageOptions.Builder()
                        .showImageOnLoading(R.mipmap.motion_detail_default_bg)
                        .showImageForEmptyUri(R.mipmap.motion_detail_default_bg)
                        .showImageOnFail(R.mipmap.motion_detail_default_bg).cacheInMemory(true)
                        .cacheOnDisk(true).considerExifParams(true).build();
                ImageLoader.getInstance().displayImage(motionDetailData.getPicture(), mIVPlayBallPic, options);
            } else {
                mIVPlayBallPic.setVisibility(View.GONE);
            }
        }
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
