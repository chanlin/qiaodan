package com.jordan.project.activities.ball;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.CityInfo;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchOption;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.jordan.project.JordanApplication;
import com.jordan.project.R;
import com.jordan.project.utils.BaiduLocationUtils;
import com.jordan.project.utils.DistanceUtils;
import com.jordan.project.utils.LogUtils;
import com.jordan.project.utils.MapViewUtil;
import com.jordan.project.utils.SettingSharedPerferencesUtil;
import com.jordan.project.utils.ToastUtils;
import com.jordan.project.utils.baidu.PoiOverlay;

import java.util.ArrayList;
import java.util.List;

public class NearbyStadiumActivity extends Activity implements
        OnGetPoiSearchResultListener {
    private MapView mMapView;
    private BaiduMap mBaiduMap;

    private PoiSearch poiSearch;

    private String address = null;
    private boolean isVoiceNavigation = false;
    private int load_Index = 10;

    private EditText mETAddress;
    private LinearLayout mLLNavi;
    private TextView mTVWhere, mTVDistance, mTVName;
    private ImageView mIVNavi, mIVBG;
    //android:id="@+id/common_near_stadium_rl"
    private Button mBtnChoiesMap;
    private Button mBtnSearch;
    private PoiDetailResult mPoiDetailResult = null;
    private int pageNum = 1;
    private int pageSize = 10;
    PoiOverlay overlay;
    private boolean isAddress = false;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    ToastUtils.shortToast(NearbyStadiumActivity.this, R.string.common_no_find_stadium);
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_nearby_stadium);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.common_back_title);
        TextView mTvTitle = (TextView) findViewById(R.id.title_text);
        mTvTitle.setText(getResources().getString(R.string.common_near_stadium));
        RelativeLayout mRLBack = (RelativeLayout) findViewById(R.id.title_back);
        mRLBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        setView();
        searchPoi();
        VoiceNavigation();
    }

    private void setView() {
        mIVBG = (ImageView) findViewById(R.id.frist_bg);
        if (SettingSharedPerferencesUtil.GetFristStadiumValueConfig(NearbyStadiumActivity.this).equals("")) {
            SettingSharedPerferencesUtil.SetFristStadiumValue(NearbyStadiumActivity.this, "true");
            mIVBG.setVisibility(View.VISIBLE);
        }
        mIVBG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIVBG.setVisibility(View.GONE);
            }
        });

        mETAddress = (EditText) findViewById(R.id.near_stadium_search_et);
        //获取地图控件引用
        mMapView = (MapView) findViewById(R.id.near_stadium_map_view);
        mBaiduMap = mMapView.getMap();
        //普通地图
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        mBaiduMap.setMyLocationEnabled(true);
        MapViewUtil.goneMap(mMapView);
        poiSearch = PoiSearch.newInstance();
        poiSearch.setOnGetPoiSearchResultListener(this);
        // LinearLayout下面的
        mLLNavi = (LinearLayout) findViewById(R.id.ll_navi);
        mTVWhere = (TextView) findViewById(R.id.tv_where);
        mIVNavi = (ImageView) findViewById(R.id.iv_navi);
        mTVDistance = (TextView) findViewById(R.id.tv_distance);
        mTVName = (TextView) findViewById(R.id.tv_name);
        overlay = new MyPoiOverlay(mBaiduMap);
        mBaiduMap.setOnMarkerClickListener(overlay);
        mBaiduMap.setOnMapLongClickListener(mapLongClickListener);
        mBtnChoiesMap = (Button) findViewById(R.id.common_near_stadium_btn);
        mBtnChoiesMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获得当前地址的经纬度
                //查找附近的球场
                if (mPoiDetailResult != null) {
                    isAddress = false;
                    LogUtils.showLog("searchNavi", "mPoiDetailResult!=null");
                    LatLng latLng = mPoiDetailResult.getLocation();
                    searchNavi(latLng, getResources().getString(R.string.common_ball_stadium));
                } else {
                    mHandler.sendEmptyMessage(0);
                }
            }
        });
        mBtnSearch = (Button) findViewById(R.id.near_stadium_search_btn);
        mBtnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isAddress = true;
                String searchkey = mETAddress.getText().toString().trim();
                // 附近搜索发起
                searchNavi(searchkey);
                backKeybord(NearbyStadiumActivity.this, mETAddress);
            }
        });
        LatLng latLng = BaiduLocationUtils.locationMyself(this, mBaiduMap);
        searchNavi(latLng, getResources().getString(R.string.common_ball_stadium));
    }

    BaiduMap.OnMapLongClickListener mapLongClickListener = new BaiduMap.OnMapLongClickListener() {
        /**
         * 地图长按事件监听回调函数
         * @param point 长按的地理坐标
         */
        public void onMapLongClick(LatLng point) {
            searchNavi(point, getResources().getString(R.string.common_ball_stadium));
        }
    };

    private void searchPoi() {
        Intent intent = getIntent();
        String poi = intent.getStringExtra("poi");
        if (!TextUtils.isEmpty(poi)) {
            searchNavi(poi);
        }
    }

    private void VoiceNavigation() {
        if (getIntent().hasExtra("address")) {
            address = getIntent().getStringExtra("address");
            if (address != null) {
                isVoiceNavigation = true;
                mETAddress.setText(address);
                //出发click
                // EditText editCity = (EditText) findViewById(R.id.city);
                String searchkey = mETAddress.getText().toString().trim();
                LogUtils.showLog("searchkey", searchkey);
                // 附近搜索发起
                searchNavi(searchkey);
                backKeybord(this, mETAddress);
            } else {
                isVoiceNavigation = false;
            }
        } else {
            isVoiceNavigation = false;
        }
    }

    /**
     * 发起搜索
     *
     * @param str 搜索地点(附近的,暂时未做城市和其他)
     */
    private void searchNavi(String str) {
        mBaiduMap.clear();
        PoiCitySearchOption poiCitySearchOption = new PoiCitySearchOption();
        BDLocation bdLocation = BaiduLocationUtils.getBDLocation();
        if (bdLocation != null) {
            poiCitySearchOption.city(bdLocation.getCity());// 检索半径，单位是米
        }
        // 搜索关键词
        poiCitySearchOption.keyword(str);
        // 第一页显示
        poiCitySearchOption.pageNum(pageNum);
        poiCitySearchOption.pageCapacity(pageSize);
        LogUtils.showLog("search_result", "pageSize" + pageSize);
        try {
            poiSearch.searchInCity(poiCitySearchOption);// 发起附近检索请求
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void searchNavi(LatLng latLng, String str) {
        mBaiduMap.clear();
        mLLNavi.setVisibility(View.GONE);
        LogUtils.showLog("searchNavi", "searchNavi");
        PoiNearbySearchOption nearbySearchOption = new PoiNearbySearchOption();
        BDLocation bdLocation = BaiduLocationUtils.getBDLocation();
        if (bdLocation != null) {
            nearbySearchOption.location(latLng);
        }
        // 搜索关键词
        nearbySearchOption.keyword(str);
        nearbySearchOption.radius(3000);// 检索半径，单位是米
        // 第一页显示
        nearbySearchOption.pageNum(pageNum);
        nearbySearchOption.pageCapacity(pageSize);
        LogUtils.showLog("search_result", "pageNum" + pageNum);
        try {
            LogUtils.showLog("searchNavi", "searchNearby");
            poiSearch.searchNearby(nearbySearchOption);// 发起附近检索请求
        } catch (Exception e) {
            LogUtils.showLog("searchNavi", "searchNearby ex");
            e.printStackTrace();
        }
        // mPoiSearch.searchInCity((new PoiCitySearchOption()).city("深圳")
        // .keyword(searchkey).pageNum(load_Index));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        poiSearch.destroy();
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

    @Override
    public void onGetPoiResult(PoiResult poiResult) {
        LogUtils.showLog("search_result", "onGetPoiResult");

        if (poiResult == null
                || poiResult.error == SearchResult.ERRORNO.RESULT_NOT_FOUND) {
            LogUtils.showLog("search_result", "onGetPoiResult SearchResult.ERRORNO.RESULT_NOT_FOUND");
            // showToast("未找到结果");
            mHandler.sendEmptyMessage(0);
            return;
        }
        if (poiResult.error == SearchResult.ERRORNO.NO_ERROR) {
            LogUtils.showLog("search_result", "onGetPoiResult SearchResult.ERRORNO.NO_ERROR");
            mBaiduMap.clear();
            LogUtils.showLog("search_result", "onGetPoiResult" + poiResult.getTotalPageNum() + ":" + poiResult.getCurrentPageNum());
            overlay.setData(poiResult);
            overlay.addToMap();
            overlay.zoomToSpan();

            if (isVoiceNavigation) {
                if (poiResult.getAllPoi().size() > 0) {
                    LogUtils.showLog("search_result", "onGetPoiResult-getAllPoi().size:" + poiResult.getAllPoi().size());
                    LogUtils.showLog("search_result", "onGetPoiResult-latitude-longitude:" + poiResult.getAllPoi().get(0).location.latitude + ":" + poiResult.getAllPoi().get(0).location.longitude);
                    LogUtils.showLog("search_result", "onGetPoiResult-address-name:" + poiResult.getAllPoi().get(0).address + ":" + poiResult.getAllPoi().get(0).name);
                    //  直接进行导航 + 当前城市名称
                    LatLng latLng = poiResult.getAllPoi().get(0).location;
                    double x = latLng.longitude;
                    double y = latLng.latitude;
                    if (x == 0.0 || y == 0.0) {

                    } else {
                        try {
                            //new NaviTools(BaiduSearchActivity.this).routeplanToNavi(x, y);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            return;
        }
        if (poiResult.error == SearchResult.ERRORNO.AMBIGUOUS_KEYWORD) {
            LogUtils.showLog("search_result", "onGetPoiResult SearchResult.ERRORNO.AMBIGUOUS_KEYWORD");

            // 当输入关键字在本市没有找到，但在其他城市找到时，返回包含该关键字信息的城市列表
            String strInfo = "在";
            for (CityInfo cityInfo : poiResult.getSuggestCityList()) {
                strInfo += cityInfo.city;
                strInfo += ",";
            }
            strInfo += "找到结果";
            // showToast(strInfo);
        }
    }

    @Override
    public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {
        LogUtils.showLog("search_result", "onGetPoiDetailResult");

//        LogUtils.showLog("search_result","onGetPoiDetailResult"+result.error +"|"+ SearchResult.ERRORNO.NO_ERROR);
        if (poiDetailResult.error != SearchResult.ERRORNO.NO_ERROR) {
            ToastUtils.shortToast(NearbyStadiumActivity.this, getResources().getString(R.string.common_sorry_not_find));
        } else {
            if (poiDetailResult == null) {
                ToastUtils.shortToast(NearbyStadiumActivity.this, getResources().getString(R.string.common_sorry_not_find));
                return;
            }
            mPoiDetailResult = null;
            LogUtils.showLog("search_result", "onGetPoiDetailResult" + poiDetailResult.getName() + ": " + poiDetailResult.getAddress());
            mLLNavi.setVisibility(View.VISIBLE);
            mTVName.setText(poiDetailResult.getName());
            mTVWhere.setText(poiDetailResult.getAddress());
            if (JordanApplication.nowLatitude == 0 && JordanApplication.nowLongitude == 0) {
                mTVDistance.setText("");
            } else {
                double dLongitude = poiDetailResult.getLocation().longitude;
                double dLatitude = poiDetailResult.getLocation().latitude;
                mTVDistance.setText(DistanceUtils.getDistanceUnit(JordanApplication.nowLongitude, JordanApplication.nowLatitude, dLongitude, dLatitude));//需要自己计算距离
            }
            LogUtils.showLog("search_result", "poiDetailResult.toString:" + poiDetailResult.toString());
            LatLng latLng = poiDetailResult.getLocation();
            mIVNavi.setOnClickListener(new NaviOnClickListener(latLng));
            //得到需要下一步的参数
            mPoiDetailResult = poiDetailResult;
        }
    }

    @Override
    public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {
        LogUtils.showLog("search_result", "onGetPoiIndoorResult");

    }

    /**
     * 是软件盘下去
     */
    public static void backKeybord(Context context, EditText et) {
        InputMethodManager imm = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(et.getWindowToken(), 0);
    }

    private class NaviOnClickListener implements View.OnClickListener {

        private LatLng latLng;

        public NaviOnClickListener(LatLng latLng) {
            this.latLng = latLng;
        }

        // 点击导航
        @Override
        public void onClick(View v) {
            double x = latLng.longitude;
            double y = latLng.latitude;
            if (x == 0.0 || y == 0.0) {

            } else {
                //new NaviTools(BaiduSearchActivity.this).routeplanToNavi(x, y);
            }
        }

    }

    private class MyPoiOverlay extends PoiOverlay {
        private List<PoiInfo> pois;

        public MyPoiOverlay(BaiduMap baiduMap) {
            super(baiduMap);
        }

        @Override
        public boolean onMarkerClick(Marker marker) {
            onClick(marker.getZIndex());
            return true;
        }

        public boolean onClick(int index) {
            PoiInfo poi = pois.get(index);
            LogUtils.showLog("search_result", "onClick pois.size:" + pois.size());
            //if(poi.hasCaterDetails){
            poiSearch.searchPoiDetail((new PoiDetailSearchOption()).poiUid(poi.uid));
            // }
            return true;
        }

//        @Override
//        public boolean onPoiClick(int index) {
//            super.onPoiClick(index);
//            LogUtils.showLog("search_result","onPoiClick");
//            List<PoiInfo> allPoi = getPoiResult().getAllPoi();
////            List<OverlayOptions> ops = new ArrayList<OverlayOptions>();
//            LogUtils.showLog("search_result",""+allPoi.size());
////            OverlayOptions op = null;
//            PoiInfo poi = allPoi.get(index);
////            BitmapDescriptor bitmap = BitmapDescriptorFactory
////                    .fromResource(R.mipmap.jordan_logo);
////            for (int i = 0; i < allPoi.size(); i++) {
////                    op = new MarkerOptions().position(allPoi.get(i).location).icon(bitmap);
////                ops.add(op);
////                mBaiduMap.addOverlay(op).setZIndex(i);
////            }
//            PoiDetailSearchOption option = new PoiDetailSearchOption();
//            poiSearch.searchPoiDetail(option.poiUid(poi.uid));
//            // }
//            //uid是POI检索中获取的POI ID信息
//            //poiSearch.searchPoiDetail((new PoiDetailSearchOption()).poiUid(poi.uid));
//            return true;
//        }

        @Override
        public List<OverlayOptions> getOverlayOptions() {
            List<OverlayOptions> ops = new ArrayList<OverlayOptions>();
            pois = getPoiResult().getAllPoi();
            LogUtils.showLog("search_result", "getOverlayOptions pois.size:" + pois.size());
            OverlayOptions op = null;
            BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.mipmap.ic_map);
            if (isAddress)
                bitmap = BitmapDescriptorFactory.fromResource(R.mipmap.map_address);
            for (int i = 0; i < pois.size(); i++) {
                op = new MarkerOptions().position(pois.get(i).location).icon(bitmap);
                ops.add(op);
                mBaiduMap.addOverlay(op).setZIndex(i);
            }
            return ops;
        }
    }

}
