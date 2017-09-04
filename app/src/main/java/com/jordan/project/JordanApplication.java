package com.jordan.project;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.jordan.project.entity.MapAddressData;
import com.jordan.project.utils.LogUtils;
import com.jordan.project.utils.SettingSharedPerferencesUtil;
import com.jordan.project.utils.UnceHandler;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by icean on 2017/2/8.
 */

public class JordanApplication extends Application {
    private static String username = "";
    private static String vipId = "";
    public static boolean isRegister = false;
    public static boolean isUpdateUserData = false;
    public static boolean isCreatePlayBall = false;
    public static boolean isCreatePlayBallUpdateJoin = false;
    public static boolean isBindShoes = false;
    public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    public static SimpleDateFormat noyearsdf = new SimpleDateFormat("MM-dd HH:mm");
    public static SimpleDateFormat datenamesdf = new SimpleDateFormat("yyyyMMddHHmmss");
    private static LocationClient client;
    public static double nowLatitude = 0;
    public static double nowLongitude = 0;
    public static boolean isUpdateBluetoothList = false;
    public static boolean isUpdateMotion = false;
    public static boolean islogout = false;
    public static BDLocation bdLocation;

    public static String getUsername(Context context) {
        if(username==null||username.equals("")) {
            username = SettingSharedPerferencesUtil.GetLoginUsernameValueConfig(context);
            LogUtils.showLog("database", "findUserInfo username null:"+username);
        }else{
            LogUtils.showLog("database", "findUserInfo username !=null:"+username);
        }
        LogUtils.showLog("database", "getUsername:"+username);
        return username;
    }

    public static void setUsername(String username) {
        JordanApplication.username = username;
    }

    public static String getVipID(Context context){
        if (vipId.equals("")) {
            vipId = SettingSharedPerferencesUtil.GetVipIdValueConfig(context);
        }
        return vipId;
    }
    public static String setVipID(String VipID){
        vipId=VipID;
        return vipId;
    }

    public static MapAddressData mMapAddressData;

    public static Bitmap mSharePic;
    @Override
    public void onCreate() {
        super.onCreate();
        UMShareAPI.get(this);
        initSocialSDK();
        SDKInitializer.initialize(getApplicationContext());
        init();
        initImageLoader(this);
        client = new LocationClient(this);
        //client.registerLocationListener( myListener );    //注册监听函数
        initLocation();
        client.start();
    }
    private void initSocialSDK() {
        // TODO Auto-generated method stub
//		String appid = "wx3d3d38909a3eb68d";
//		String secret = "2354832608020509f81ec774ee107993";
//		PlatformConfig.setWeixin(appid, secret);
//		appid = "1106339836";
//		secret = "KEYuE1F6VNeeyx5Or1e";
//		PlatformConfig.setQQZone(appid, secret);
        PlatformConfig.setWeixin("wxe0c9cb2f0bf4618b","04b17082a106a5d08f6de16cf21ba7e1");
        PlatformConfig.setQQZone("1106339836","KEYuE1F6VNeeyx5Or1e");
        PlatformConfig.setSinaWeibo("","","");
    }


    ArrayList<Activity> list = new ArrayList<Activity>();

    public void init() {
        //设置该CrashHandler为程序的默认处理器    
        UnceHandler catchExcep = new UnceHandler(this);
        Thread.setDefaultUncaughtExceptionHandler(catchExcep);
    }

    /**
     * Activity关闭时，删除Activity列表中的Activity对象
     */
    public void removeActivity(Activity a) {
        list.remove(a);
    }

    /**
     * 向Activity列表中添加Activity对象
     */
    public void addActivity(Activity a) {
        list.add(a);
    }

    /**
     * 关闭Activity列表中的所有Activity
     */
    public void finishActivity() {
        for (Activity activity : list) {
            if (null != activity) {
                activity.finish();
            }
        }
        //杀死该应用进程  
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    private void initImageLoader(Context context) {
        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
        config.threadPriority(Thread.NORM_PRIORITY - 2);
        config.denyCacheImageMultipleSizesInMemory();
        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        config.diskCacheSize(50 * 1024 * 1024); // 50 MiB
        config.tasksProcessingOrder(QueueProcessingType.LIFO);
        config.writeDebugLogs(); // Remove for release app

        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config.build());
    }

    public BDLocationListener mBDListener = new MyLocationListenner();

    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy
        );//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        //int span = 1000;
        //option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        client.setLocOption(option);
        client.registerLocationListener(mBDListener);
    }

    /**
     * 获取百度的Client
     */
    public static LocationClient getBDClient() {
        return client;
    }

public class MyLocationListenner implements BDLocationListener {

    @Override
    public void onReceiveLocation(BDLocation location) {
        android.util.Log.e("SlashInfo", "JordanApplication::onReceiveLocation::info= " + location.toString());
        if (location == null)
            return;
        bdLocation = location;
        if(bdLocation!=null){
            client.stop();
        }
    }


    @Override
    public void onConnectHotSpotMessage(String s, int i) {

    }

    public void onReceivePoi(BDLocation poiLocation) {
    }
}
}
