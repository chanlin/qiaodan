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
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
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
import com.jordan.project.entity.MapAddressData;
import com.jordan.project.utils.BaiduLocationUtils;
import com.jordan.project.utils.DistanceUtils;
import com.jordan.project.utils.LogUtils;
import com.jordan.project.utils.MapViewUtil;
import com.jordan.project.utils.ToastUtils;
import com.jordan.project.utils.baidu.PoiOverlay;

import java.util.ArrayList;
import java.util.List;

public class MapAddressActivity extends Activity implements
        OnGetPoiSearchResultListener ,OnGetGeoCoderResultListener{
    private MapView mMapView;
    private BaiduMap mBaiduMap;

    private PoiSearch poiSearch;

    private String address = null;
    private boolean isVoiceNavigation = false;
    private int pageNum = 1;
    private int pageSize = 10;

    private EditText mETAddress;
    private LinearLayout mLLNavi;
    private TextView mTVWhere, mTVDistance, mTVName;
    private ImageView mIVNavi;
    private Button mBtnChoiesMap;
    private Button mBtnSearch;
    PoiOverlay overlay;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    ToastUtils.shortToast(MapAddressActivity.this, R.string.common_no_find_stadium);
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_map_address);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.common_back_title);
        TextView mTvTitle = (TextView) findViewById(R.id.title_text);
        mTvTitle.setText(getResources().getString(R.string.common_choies_map_address));
        RelativeLayout mRLBack = (RelativeLayout) findViewById(R.id.title_back);
        mRLBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        setView();
        //ViewUtils.inject(this);
        searchPoi();
        VoiceNavigation();
    }

    private void setView() {
        mETAddress = (EditText) findViewById(R.id.map_address_search_et);
        //获取地图控件引用
        mMapView = (MapView) findViewById(R.id.map_address_map_view);
        mBaiduMap = mMapView.getMap();
        //普通地图
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        mBaiduMap.setMyLocationEnabled(true);
        MapViewUtil.goneMap(mMapView);
        poiSearch = PoiSearch.newInstance();
        poiSearch.setOnGetPoiSearchResultListener(this);
        overlay = new MyPoiOverlay(mBaiduMap);
        // LinearLayout下面的
        mLLNavi = (LinearLayout) findViewById(R.id.ll_navi);
        mTVWhere = (TextView) findViewById(R.id.tv_where);
        mIVNavi = (ImageView) findViewById(R.id.iv_navi);
        mTVDistance = (TextView) findViewById(R.id.tv_distance);
        mTVName = (TextView) findViewById(R.id.tv_name);
        mBaiduMap.setOnMapLongClickListener(mapLongClickListener);
        mBtnChoiesMap = (Button) findViewById(R.id.common_choies_map_address_btn);
        mBtnChoiesMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (JordanApplication.mMapAddressData != null) {
                    finish();
                }
            }
        });
        mBtnSearch = (Button) findViewById(R.id.map_address_search_btn);
        mBtnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchkey = mETAddress.getText().toString().trim();
                // 附近搜索发起
                searchNavi(searchkey);
                backKeybord(MapAddressActivity.this, mETAddress);
            }
        });
        //如果已经有权限了直接初始化，否则再请求一次权限，并且成功的时候触发此方法
        LatLng latLng = BaiduLocationUtils.locationMyself(this, mBaiduMap);
        searchNavi(latLng, getResources().getString(R.string.common_ball_stadium));
// 初始化搜索模块，注册事件监听
        mSearch = GeoCoder.newInstance();
        mSearch.setOnGetGeoCodeResultListener(this);
    }

    private GeoCoder mSearch = null; // 搜索模块
    BaiduMap.OnMapLongClickListener mapLongClickListener = new BaiduMap.OnMapLongClickListener() {
        /**
         * 地图长按事件监听回调函数
         * @param point 长按的地理坐标
         */
        public void onMapLongClick(LatLng point) {
            LogUtils.showLog("MyPoiOverlay", "onMapLongClick longitude:" + point.longitude);
            LogUtils.showLog("MyPoiOverlay", "onMapLongClick latitude:" + point.latitude);
            //searchNavi(point, getResources().getString(R.string.common_ball_stadium));
            // 反Geo搜索
            mSearch.reverseGeoCode(new ReverseGeoCodeOption().location(point));
            LogUtils.showLog("MyPoiOverlay", "反Geo搜索");
            mBaiduMap.clear();
            mBaiduMap.setOnMarkerClickListener(overlay);
            //构建Marker图标
            BitmapDescriptor bitmap = BitmapDescriptorFactory
                    .fromResource(R.mipmap.ic_map);
            //构建MarkerOption，用于在地图上添加Marker
            OverlayOptions option = new MarkerOptions()
                    .position(point)
                    .icon(bitmap);
            //在地图上添加Marker，并显示
            mBaiduMap.addOverlay(option);
        }
    };

    @Override
    public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {

    }

    @Override
    public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
        if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
            return;
        }
        LogUtils.showLog("MyPoiOverlay", "onGetReverseGeoCodeResult result:" + result.getAddress());
        LogUtils.showLog("MyPoiOverlay", "onGetReverseGeoCodeResult result.getPoiList().size():" + result.getPoiList().size());
        if(result.getPoiList().size()>0) {
            PoiInfo poiInfo = result.getPoiList().get(0);
            mLLNavi.setVisibility(View.VISIBLE);
            mTVName.setText(poiInfo.name);
            mTVWhere.setText(poiInfo.address);
            if (JordanApplication.nowLatitude == 0 && JordanApplication.nowLongitude == 0) {
                mTVDistance.setText("");
            } else {
                double dLongitude = poiInfo.location.longitude;
                double dLatitude = poiInfo.location.latitude;
                mTVDistance.setText(DistanceUtils.getDistanceUnit(JordanApplication.nowLongitude, JordanApplication.nowLatitude, dLongitude, dLatitude));//需要自己计算距离
            }
            LatLng latLng = poiInfo.location;
            mIVNavi.setOnClickListener(new NaviOnClickListener(latLng));

            String address = poiInfo.address;
            String city = poiInfo.address;
            String district = poiInfo.address;
            String province = poiInfo.address;
            String street = poiInfo.address;
            if (poiInfo.address.contains("省")) {
                province = address.substring(0, address.indexOf("省"));
                //address=address.substring(address.indexOf("省")+1);
            }
            if (poiInfo.address.contains("市")) {
                city = address.substring(0, address.indexOf("市"));
                //address=address.substring(address.indexOf("市")+1);
            }
            if (poiInfo.address.contains("区")) {
                district = address.substring(0, address.indexOf("区"));
                //address=address.substring(address.indexOf("区")+1);
            }
            if (poiInfo.address.contains("县")) {
                district = address.substring(0, address.indexOf("县"));
                //address=address.substring(address.indexOf("县")+1);
            }
            if (poiInfo.address.contains("街")) {
                street = address.substring(0, address.indexOf("街"));
                //address=address.substring(address.indexOf("街")+1);
            }
            JordanApplication.mMapAddressData = new MapAddressData();
            JordanApplication.mMapAddressData.setAddress(poiInfo.name);
            JordanApplication.mMapAddressData.setCity(city);
            JordanApplication.mMapAddressData.setDistrict(district);
            JordanApplication.mMapAddressData.setLatitude(String.valueOf(poiInfo.location.latitude));
            JordanApplication.mMapAddressData.setLongitude(String.valueOf(poiInfo.location.longitude));
            JordanApplication.mMapAddressData.setProvince(province);
            JordanApplication.mMapAddressData.setStreet(street);
        }
    }
    //    BaiduMap.OnMapClickListener mapClickListener = new BaiduMap.OnMapClickListener() {
//        @Override
//        public void onMapClick(LatLng latLng) {
//            LogUtils.showLog("MyPoiOverlay", "onMapClick longitude:"+latLng.longitude);
//            LogUtils.showLog("MyPoiOverlay", "onMapClick latitude:"+latLng.latitude);
//            //searchNavi(point, getResources().getString(R.string.common_ball_stadium));
//            mBaiduMap.clear();
//            //构建Marker图标
//            BitmapDescriptor bitmap = BitmapDescriptorFactory
//                    .fromResource(R.mipmap.ic_map);
//            //构建MarkerOption，用于在地图上添加Marker
//            OverlayOptions option = new MarkerOptions()
//                    .position(latLng)
//                    .icon(bitmap);
//            //在地图上添加Marker，并显示
//            mBaiduMap.addOverlay(option);
//        }
//
//        @Override
//        public boolean onMapPoiClick(MapPoi mapPoi) {
//            mLLNavi.setVisibility(View.GONE);
//            mBaiduMap.clear();
//            //构建Marker图标
//            BitmapDescriptor bitmap = BitmapDescriptorFactory
//                    .fromResource(R.mipmap.ic_map);
//            //构建MarkerOption，用于在地图上添加Marker
//            OverlayOptions option = new MarkerOptions()
//                    .position(mapPoi.getPostion())
//                    .icon(bitmap);
//            //在地图上添加Marker，并显示
//            mBaiduMap.addOverlay(option);
//            LogUtils.showLog("MyPoiOverlay", "onMapPoiClick mapPoi getUid:"+mapPoi.getUid());
//            poiSearch.searchPoiDetail((new PoiDetailSearchOption()).poiUid(mapPoi.getUid()));
//            return false;
//        }
//
//    };
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
        // mPoiSearch.searchInCity((new PoiCitySearchOption()).city("深圳")
        // .keyword(searchkey).pageNum(load_Index));
    }

    private void searchNavi(LatLng latLng, String str) {
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
            mBaiduMap.setOnMarkerClickListener(overlay);
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
            ToastUtils.shortToast(MapAddressActivity.this, getResources().getString(R.string.common_sorry_not_find));

        } else {
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
            LogUtils.showLog("search_result", "poiDetailResult.getAddress:" + poiDetailResult.getAddress());
            LogUtils.showLog("search_result", "poiDetailResult.getDetailUrl:" + poiDetailResult.getDetailUrl());
            LogUtils.showLog("search_result", "poiDetailResult.getName:" + poiDetailResult.getName());
            LogUtils.showLog("search_result", "poiDetailResult.getShopHours:" + poiDetailResult.getShopHours());
            LogUtils.showLog("search_result", "poiDetailResult.getTag:" + poiDetailResult.getTag());
            LogUtils.showLog("search_result", "poiDetailResult.getTelephone:" + poiDetailResult.getTelephone());
            LogUtils.showLog("search_result", "poiDetailResult.getType:" + poiDetailResult.getType());
            LogUtils.showLog("search_result", "poiDetailResult.getUid:" + poiDetailResult.getUid());
            LogUtils.showLog("search_result", "poiDetailResult.getCheckinNum:" + poiDetailResult.getCheckinNum());
            LogUtils.showLog("search_result", "poiDetailResult.getCommentNum:" + poiDetailResult.getCommentNum());
            LogUtils.showLog("search_result", "poiDetailResult.getEnvironmentRating:" + poiDetailResult.getEnvironmentRating());
            LogUtils.showLog("search_result", "poiDetailResult.getFacilityRating:" + poiDetailResult.getFacilityRating());
            LogUtils.showLog("search_result", "poiDetailResult.getGrouponNum:" + poiDetailResult.getGrouponNum());
            LogUtils.showLog("search_result", "poiDetailResult.getHygieneRating:" + poiDetailResult.getHygieneRating());
            LogUtils.showLog("search_result", "poiDetailResult.getImageNum:" + poiDetailResult.getImageNum());
            LogUtils.showLog("search_result", "poiDetailResult.getLocation:" + poiDetailResult.getLocation());
            LogUtils.showLog("search_result", "poiDetailResult.getOverallRating:" + poiDetailResult.getOverallRating());
            LogUtils.showLog("search_result", "poiDetailResult.getPrice:" + poiDetailResult.getPrice());
            LogUtils.showLog("search_result", "poiDetailResult.getServiceRating:" + poiDetailResult.getServiceRating());
            LogUtils.showLog("search_result", "poiDetailResult.getTasteRating:" + poiDetailResult.getTasteRating());
            LogUtils.showLog("search_result", "poiDetailResult.getTechnologyRating:" + poiDetailResult.getTechnologyRating());
            LatLng latLng = poiDetailResult.getLocation();
            mIVNavi.setOnClickListener(new NaviOnClickListener(latLng));

            String address = poiDetailResult.getAddress();
            String city = poiDetailResult.getAddress();
            String district = poiDetailResult.getAddress();
            String province = poiDetailResult.getAddress();
            String street = poiDetailResult.getAddress();
            if (poiDetailResult.getAddress().contains("省")) {
                province = address.substring(0, address.indexOf("省"));
                //address=address.substring(address.indexOf("省")+1);
            }
            if (poiDetailResult.getAddress().contains("市")) {
                city = address.substring(0, address.indexOf("市"));
                //address=address.substring(address.indexOf("市")+1);
            }
            if (poiDetailResult.getAddress().contains("区")) {
                district = address.substring(0, address.indexOf("区"));
                //address=address.substring(address.indexOf("区")+1);
            }
            if (poiDetailResult.getAddress().contains("县")) {
                district = address.substring(0, address.indexOf("县"));
                //address=address.substring(address.indexOf("县")+1);
            }
            if (poiDetailResult.getAddress().contains("街")) {
                street = address.substring(0, address.indexOf("街"));
                //address=address.substring(address.indexOf("街")+1);
            }
            JordanApplication.mMapAddressData = new MapAddressData();
            JordanApplication.mMapAddressData.setAddress(poiDetailResult.getName());
            JordanApplication.mMapAddressData.setCity(city);
            JordanApplication.mMapAddressData.setDistrict(district);
            JordanApplication.mMapAddressData.setLatitude(String.valueOf(poiDetailResult.getLocation().latitude));
            JordanApplication.mMapAddressData.setLongitude(String.valueOf(poiDetailResult.getLocation().longitude));
            JordanApplication.mMapAddressData.setProvince(province);
            JordanApplication.mMapAddressData.setStreet(street);
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

        public MyPoiOverlay(BaiduMap baiduMap) {
            super(baiduMap);
        }

        @Override
        public boolean onMarkerClick(Marker marker) {
            LogUtils.showLog("MyPoiOverlay", "onMarkerClick:" + marker.getZIndex());
            LogUtils.showLog("MyPoiOverlay", "onMarkerClick longitude:" + marker.getPosition().longitude);
            LogUtils.showLog("MyPoiOverlay", "onMarkerClick latitude:" + marker.getPosition().latitude);
            onClick(marker.getZIndex());
            return true;
        }

        public boolean onClick(int index) {
            PoiInfo poi = getPoiResult().getAllPoi().get(index);
            LogUtils.showLog("MyPoiOverlay", "onClick poi.uid:" + poi.uid);
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
            List<PoiInfo> pois = getPoiResult().getAllPoi();
            OverlayOptions op = null;
            BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.mipmap.ic_map);
            for (int i = 0; i < pois.size(); i++) {
                op = new MarkerOptions().position(pois.get(i).location).icon(bitmap);
                ops.add(op);
                mBaiduMap.addOverlay(op).setZIndex(i);
            }
            return ops;
        }

    }
}
