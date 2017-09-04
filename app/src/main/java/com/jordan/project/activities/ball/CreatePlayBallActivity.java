package com.jordan.project.activities.ball;

import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import com.safari.httplibs.HttpUtils;
import com.safari.httplibs.config.InnerMessageConfig;
import com.jordan.project.JordanApplication;
import com.jordan.project.R;
import com.jordan.project.entity.MapAddressData;
import com.jordan.project.utils.BaiduLocationUtils;
import com.jordan.project.utils.JsonFalseUtils;
import com.jordan.project.utils.LogUtils;
import com.jordan.project.utils.MapViewUtil;
import com.jordan.project.utils.ToastUtils;
import com.jordan.project.utils.TypeUtils;
import com.jordan.project.widget.LoadingProgressDialog;
import com.jordan.project.widget.slidedatetimepicker.SlideDateTimeListener;
import com.jordan.project.widget.slidedatetimepicker.SlideDateTimePicker;
import com.jordan.usersystemlibrary.UserManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;


public class CreatePlayBallActivity extends FragmentActivity implements View.OnClickListener {
    private TextView mTVMore, mTVTitle;
    private Button mBtnCreatePlayBall;
    private EditText mETAddress, mETPeopleCount, mETStartTime, mEtSlogan, mEtRemark;
    private RelativeLayout mRLStartTime;
    private ImageView mIVAddress;
    private String createListPageNo = "1";
    private String createListPageSize = "1000";
    StringBuffer stringBuilder;
    private RadioGroup radioGroup;
    private RadioButton allRadioButton;
    private RadioButton halfRadioButton;
    private String type = TypeUtils.STADIUM_TYPE_HALF;

    private String provider;//位置提供器
    private LocationManager locationManager;//位置服务
    private Location location;
    String longitude = "";//                longitude	经度
    String latitude = "";//                latitude	纬度

    private ImageView mIVTime;
    private MapView mMapView;
    private BaiduMap mBaiduMap;

    //请求用户请求信息
    private Handler mMainHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case InnerMessageConfig.USER_CREATE_REACH_MESSAGE_SUCCESS:
                    //解析正确参数-内容在msg.obj
                    LogUtils.showLog("Result", "USER_GET_USER_DATA_MESSAGE_SUCCESS result:" + (String) msg.obj);
                    JordanApplication.isCreatePlayBall = true;
                    JordanApplication.isCreatePlayBallUpdateJoin = true;
                    LoadingProgressDialog.Dissmiss();
                    finish();
                    break;
                case InnerMessageConfig.USER_CREATE_REACH_MESSAGE_EXCEPTION:
                    LoadingProgressDialog.Dissmiss();
                    ToastUtils.shortToast(CreatePlayBallActivity.this, getResources().getString(R.string.http_exception));
                    break;
                case InnerMessageConfig.USER_CREATE_REACH_MESSAGE_FALSE:
                    try {
                        String errorMsg = JsonFalseUtils.onlyErrorCodeResult((String) msg.obj);
                        if (errorMsg != null)
                            ToastUtils.shortToast(CreatePlayBallActivity.this, errorMsg);
                        LoadingProgressDialog.Dissmiss();
                    } catch (JSONException e) {
                        mMainHandler.sendEmptyMessage(InnerMessageConfig.USER_CREATE_REACH_MESSAGE_EXCEPTION);
                        e.printStackTrace();
                    }
                    break;
                case 0:
                    ToastUtils.shortToast(CreatePlayBallActivity.this, getResources().getString(R.string.common_please_choies_map_address));
                    break;
                case 1:
                    ToastUtils.shortToast(CreatePlayBallActivity.this, getResources().getString(R.string.common_choies_time_up_today));
                    break;
                case 2:
                    ToastUtils.shortToast(CreatePlayBallActivity.this, getResources().getString(R.string.common_please_write_slogan));
                    break;
                case 3:
                    ToastUtils.shortToast(CreatePlayBallActivity.this, getResources().getString(R.string.common_please_write_join_people));
                    break;
            }
        }
    };
    private UserManager userManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_create_play_ball);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.common_text_more_title);
        userManager = new UserManager(CreatePlayBallActivity.this, mMainHandler);
        setView();
        setListener();
        initData();
        initLocation();
    }

    private void initLocation() {
        mMapView = (MapView) findViewById(R.id.map_view);
        mBaiduMap = mMapView.getMap();
        //普通地图
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        mBaiduMap.setMyLocationEnabled(true);
        MapViewUtil.goneMap(mMapView);
        String address = BaiduLocationUtils.locationMyselfHasRemark(this, mBaiduMap);
        mETAddress.setText(address);
//        //关联控件
//        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);//获得位置服务
//        provider = judgeProvider(locationManager);
//
//        if (provider != null) {//有位置提供器的情况
//            //为了压制getLastKnownLocation方法的警告
//            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                return;
//            }
//            location = locationManager.getLastKnownLocation(provider);
//            if (location != null) {
//                getLocation(location);//得到当前经纬度并开启线程去反向地理编码
//            } else {
//                ToastUtils.shortToast(CreatePlayBallActivity.this, getResources().getString(R.string.get_address_false));
//            }
//        } else {//不存在位置提供器的情况
//
//        }
    }


    private void doGetCreateReachTask(String time, String duration, String people, String type,
                                      String picture, String longitude, String latitude, String province,
                                      String city, String district, String street, String address, String slogan,
                                      String remarks) {
        userManager.createReach(time, duration, people, type,
                picture, longitude, latitude, province,
                city, district, street, address, slogan, remarks);
    }


    private void setView() {
        mTVMore = (TextView) findViewById(R.id.register_title_more);
        mTVMore.setText(getResources().getString(R.string.common_join_info));
        RelativeLayout mRLBack = (RelativeLayout) findViewById(R.id.title_back);
        mRLBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mTVTitle = (TextView) findViewById(R.id.register_title_text);
        mTVTitle.setText(getResources().getString(R.string.common_create_play_ball));
        mBtnCreatePlayBall = (Button) findViewById(R.id.create_play_ball);
        mETAddress = (EditText) findViewById(R.id.create_play_ball_address_et);
        mETPeopleCount = (EditText) findViewById(R.id.create_play_ball_people_et);
        mETStartTime = (EditText) findViewById(R.id.create_play_ball_time_et);
        mEtSlogan = (EditText) findViewById(R.id.create_play_ball_slogan_et);
        mEtRemark = (EditText) findViewById(R.id.create_play_ball_remark_et);
        mRLStartTime = (RelativeLayout) findViewById(R.id.create_play_ball_time_rl);
        mIVAddress = (ImageView) findViewById(R.id.create_play_ball_address_iv);
        //获取实例
        radioGroup = (RadioGroup) findViewById(R.id.create_play_ball_type);
        allRadioButton = (RadioButton) findViewById(R.id.create_play_ball_type_all);
        halfRadioButton = (RadioButton) findViewById(R.id.create_play_ball_type_half);
        //设置监听
        radioGroup.setOnCheckedChangeListener(new RadioGroupListener());

        mIVTime = (ImageView) findViewById(R.id.create_play_ball_time_iv);


    }
    //定义一个RadioGroup的OnCheckedChangeListener
    //RadioGroup  绑定的是RadioGroup.OnCheckedChangeListener
    //CheckBox    绑定的是CompoundButton.OnCheckedChangeListener 或者 view.OnClickListener

    class RadioGroupListener implements RadioGroup.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == halfRadioButton.getId()) {
                type = TypeUtils.STADIUM_TYPE_HALF;
            } else if (checkedId == allRadioButton.getId()) {
                type = TypeUtils.STADIUM_TYPE_ALL;
            }
        }
    }

    private void setListener() {
        mTVMore.setOnClickListener(this);
        mBtnCreatePlayBall.setOnClickListener(this);
        //点击地址直接跳搜索界面？
        //新建界面？还是新建地图！--可以先做球场界面，然后参照再做一个

        //点击RL时间弹出timepick
        //时间不能选择比之前更早的时间！--可以和球场列表共用
        mIVTime.setOnClickListener(this);
        mIVAddress.setOnClickListener(this);
    }

    private void initTimeDialog() {
        // TODO Auto-generated method stub
//        Calendar c = Calendar.getInstance();
//
//        Dialog dateDialog = new DatePickerDialog(CreatePlayBallActivity.this, new DatePickerDialog.OnDateSetListener() {
//
//            @Override
//            public void onDateSet(DatePicker arg0, final int arg1, final int arg2, int arg3) {
//                // TODO Auto-generated method stub
//                stringBuilder = new StringBuffer("");
//                stringBuilder.append(arg1 + "-" + (arg2 + 1) + "-" + arg3 + " ");
//                Calendar time = Calendar.getInstance();
//                Dialog timeDialog = new TimePickerDialog(CreatePlayBallActivity.this, new TimePickerDialog.OnTimeSetListener() {
//
//                    @Override
//                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
//                        // TODO Auto-generated method stub
//                        stringBuilder.append(hourOfDay + ":" + minute);
//                        //比较时间和现在的时间对比
//                        long start = new Date().getTime();
//                        try {
//                            start = JordanApplication.sdf.parse(stringBuilder.toString()).getTime();
//                        } catch (ParseException e) {
//                            e.printStackTrace();
//                        }
//                        long now = new Date().getTime();
//                        //否则直接填写当前时间-并提示用户
//                        if (start < now) {
//                            mETStartTime.setText(JordanApplication.sdf.format(new Date(now)));
//                            mMainHandler.sendEmptyMessage(1);
//                        } else {
//                            mETStartTime.setText(stringBuilder);
//                        }
//                    }
//                }, time.get(Calendar.HOUR_OF_DAY), time.get(Calendar.MINUTE), true);
//                timeDialog.setTitle("请选择时间");
//                timeDialog.show();
//            }
//
//
//        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
//        dateDialog.setTitle("请选择日期");
//        dateDialog.show();
        Date date = new Date();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.MINUTE, 15);//把日期往后增加一天.整数往后推,负数往前移动
        date = calendar.getTime();   //这个时间就是日期往后推一天的结果
        new SlideDateTimePicker.Builder(getSupportFragmentManager())
                .setListener(listener)
                .setInitialDate(date)
                .setMinDate(new Date())
                //.setMaxDate(maxDate)
                //.setIs24HourTime(true)
                //.setTheme(SlideDateTimePicker.HOLO_DARK)
                //.setIndicatorColor(Color.parseColor("#990000"))
                .build()
                .show();
    }


    private SlideDateTimeListener listener = new SlideDateTimeListener() {

        @Override
        public void onDateTimeSet(Date date) {
            //比较时间和现在的时间对比
            long start = date.getTime();
            long now = new Date().getTime();
            //否则直接填写当前时间-并提示用户
            if (start < now) {
                mETStartTime.setText(JordanApplication.sdf.format(new Date(now)));
                mMainHandler.sendEmptyMessage(1);
            } else {
                mETStartTime.setText(JordanApplication.sdf.format(date));
            }
        }

        // Optional cancel listener
        @Override
        public void onDateTimeCancel() {
//            Toast.makeText(CreatePlayBallActivity.this,
//                    "Canceled", Toast.LENGTH_SHORT).show();
        }
    };

    private void initData() {
        //地点自动选择当前地点
        //http://blog.csdn.net/jc_0203/article/details/51143770
        //自动选择当前时间
        Date date = new Date();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.MINUTE, 15);//把日期往后增加一天.整数往后推,负数往前移动
        date = calendar.getTime();   //这个时间就是日期往后推一天的结果
        mETStartTime.setText(JordanApplication.sdf.format(date));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.create_play_ball_time_iv:
                initTimeDialog();
                break;
            case R.id.register_title_more:
                Intent intent = new Intent(CreatePlayBallActivity.this, JoinPlayBallListActivity.class);
                startActivity(intent);
                break;
            case R.id.create_play_ball_address_iv:
                intent = new Intent(CreatePlayBallActivity.this, MapAddressActivity.class);
                startActivity(intent);
                break;
            case R.id.create_play_ball:
                if (mETPeopleCount.getText().toString() != null && !mETPeopleCount.getText().toString().equals("")) {
                    if (Integer.parseInt(mETPeopleCount.getText().toString()) > 10) {
                        ToastUtils.shortToast(CreatePlayBallActivity.this, R.string.common_max_people);
                        return;
                    }
                    if (mEtSlogan.getText().toString() != null && !mEtSlogan.getText().toString().equals("")) {
                        //得到参数
                        long time = new Date().getTime() / 1000;
                        LogUtils.showLog("database", "time:" + time);
                        try {
                            //time	球赛时间 (时间戳)
                            time = JordanApplication.sdf.parse(mETStartTime.getText().toString()).getTime() / 1000;
                            LogUtils.showLog("database", "time:" + time);
                        } catch (ParseException e) {
                            e.printStackTrace();
                            LogUtils.showLog("database", "time ex");
                        }
                        if (JordanApplication.mMapAddressData != null) {
                            longitude = JordanApplication.mMapAddressData.getLongitude();//                longitude	经度
                            latitude = JordanApplication.mMapAddressData.getLatitude();//
                            if (latitude.contains("0.0") && longitude.contains("0.0")) {
                                ToastUtils.shortToast(CreatePlayBallActivity.this, "获取选择地址经纬度失败");
                                return ;
                            }
                            String duration = "10000";//                    duration	时长
                            String people = mETPeopleCount.getText().toString();//                people	人数
                            String types = type;//type 	类型 (1 半场 2 全场)
                            String picture = "";//picture 	图片             latitude	纬度
                            String province = JordanApplication.mMapAddressData.getProvince();//                province	省份名
                            String city = JordanApplication.mMapAddressData.getCity();//                city	城市名
                            String district = JordanApplication.mMapAddressData.getDistrict();//                district	区县名
                            String street = JordanApplication.mMapAddressData.getStreet();//                street	街道
                            String address = mETAddress.getText().toString();//                address	详细地址
                            String slogan = mEtSlogan.getText().toString();//                slogan	备注
                            String remarks = mEtRemark.getText().toString();//                remarks	备注

                            LoadingProgressDialog.show(CreatePlayBallActivity.this, false, true, 30000);
                            //提交接口请求
                            doGetCreateReachTask(String.valueOf(time), duration, people, types,
                                    picture, longitude, latitude, province,
                                    city, district, street, address, slogan, remarks);
                        } else {
                            mMainHandler.sendEmptyMessage(0);
                        }
                    } else {
                        mMainHandler.sendEmptyMessage(2);
                    }
                } else {
                    mMainHandler.sendEmptyMessage(3);
                }
                break;
        }
    }


    /**
     * 得到当前经纬度并开启线程去反向地理编码
     */
    public void getLocation(Location location) {
        String latitude = location.getLatitude() + "";
        String longitude = location.getLongitude() + "";
        String url = "http://api.map.baidu.com/geocoder/v2/?ak=KGdMNn9tb2GivRZ4SP2BuWy9fkfyLKB6" +
                "&callback=renderReverse&location=" + latitude + "," + longitude + "&output=json&pois=0";
        new MyAsyncTask(url).execute();
    }

    /**
     * 判断是否有可用的内容提供器
     *
     * @return 不存在返回null
     */
    private String judgeProvider(LocationManager locationManager) {
        List<String> prodiverlist = locationManager.getProviders(true);
        if (prodiverlist.contains(LocationManager.NETWORK_PROVIDER)) {
            return LocationManager.NETWORK_PROVIDER;
        } else if (prodiverlist.contains(LocationManager.GPS_PROVIDER)) {
            return LocationManager.GPS_PROVIDER;
        } else {
            ToastUtils.shortToast(CreatePlayBallActivity.this, getResources().getString(R.string.no_address_provider));
        }
        return null;
    }

    class MyAsyncTask extends AsyncTask<Void, Void, Void> {
        String url = null;//要请求的网址
        String str = null;//服务器返回的数据
        String address = null;

        public MyAsyncTask(String url) {
            this.url = url;
        }

        @Override
        protected Void doInBackground(Void... params) {
            str = HttpUtils.getData(url);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            try {
                str = str.replace("renderReverse&&renderReverse", "");
                str = str.replace("(", "");
                str = str.replace(")", "");
                JSONObject jsonObject = new JSONObject(str);
                JSONObject address = jsonObject.getJSONObject("result");
                JSONObject location = jsonObject.getJSONObject("location");
                String city = address.getString("formatted_address");
                String district = address.getString("sematic_description");
                longitude = location.getString("lng");
                latitude = location.getString("lat");
                LogUtils.showLog("json", "jsonObject:" + jsonObject.toString());
                mETAddress.setText(city);
                //插入参数
                LogUtils.showLog("mMapAddressData", "jsonObject:" + jsonObject.toString());

                JordanApplication.mMapAddressData = new MapAddressData();
                JordanApplication.mMapAddressData.setAddress(city);
                JordanApplication.mMapAddressData.setCity(city);
                JordanApplication.mMapAddressData.setDistrict(city);
                JordanApplication.mMapAddressData.setLatitude(String.valueOf(latitude));
                JordanApplication.mMapAddressData.setLongitude(String.valueOf(longitude));
                JordanApplication.mMapAddressData.setProvince(city);
                JordanApplication.mMapAddressData.setStreet(city);
                //刷新地图 //定义Maker坐标点
                LatLng point = new LatLng(
                        Double.parseDouble(JordanApplication.mMapAddressData.getLatitude()), Double.parseDouble(JordanApplication.mMapAddressData.getLongitude()));
                //构建Marker图标
                BitmapDescriptor bitmap = BitmapDescriptorFactory
                        .fromResource(R.mipmap.ic_map);
                //构建MarkerOption，用于在地图上添加Marker
                OverlayOptions option = new MarkerOptions()
                        .position(point)
                        .icon(bitmap);
                //在地图上添加Marker，并显示
                mBaiduMap.addOverlay(option);
                //缩小的到当前位置
                MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(point);
                MapStatusUpdate u = MapStatusUpdateFactory.zoomTo(18);
                try {
                    mBaiduMap.animateMapStatus(u);
                    mBaiduMap.animateMapStatus(msu);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            super.onPostExecute(aVoid);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
        if (JordanApplication.mMapAddressData != null) {
            //刷新edittext
            mETAddress.setText(JordanApplication.mMapAddressData.getAddress());
            LogUtils.showLog("mMapAddressData", "JordanApplication.mMapAddressData:" + JordanApplication.mMapAddressData.toString());
            //刷新地图
            mBaiduMap.clear();
            //定义Maker坐标点
            LatLng point = new LatLng(
                    Double.parseDouble(JordanApplication.mMapAddressData.getLatitude()), Double.parseDouble(JordanApplication.mMapAddressData.getLongitude()));
            //构建Marker图标
            BitmapDescriptor bitmap = BitmapDescriptorFactory
                    .fromResource(R.mipmap.ic_map);
            //构建MarkerOption，用于在地图上添加Marker
            OverlayOptions option = new MarkerOptions()
                    .position(point)
                    .icon(bitmap);
            //在地图上添加Marker，并显示
            mBaiduMap.addOverlay(option);
            //缩小的到当前位置
            MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(point);
            MapStatusUpdate u = MapStatusUpdateFactory.zoomTo(18);
            mBaiduMap.animateMapStatus(u);
            mBaiduMap.animateMapStatus(msu);
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
    }

    @Override
    public void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }
}

