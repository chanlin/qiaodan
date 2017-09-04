package com.jordan.project.activities.main;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothGattCharacteristic;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.AnimationDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.safari.blelibs.BleManager;
import com.safari.blelibs.IBleManagerCallback;
import com.jordan.commonlibrary.CommonManager;
import com.jordan.commonlibrary.config.CommonSystemConfig;
import com.safari.httplibs.config.InnerMessageConfig;
import com.safari.httplibs.utils.HttpUtilsConfig;
import com.jordan.project.JordanApplication;
import com.jordan.project.R;
import com.jordan.project.data.BluetoothDeviceInfo;
import com.jordan.project.database.DatabaseService;
import com.jordan.project.entity.BluetoothData;
import com.jordan.project.utils.LogUtils;
import com.jordan.project.utils.OTAFile;
import com.jordan.project.utils.ToastUtils;

import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class OTAVersionActivity extends Activity implements IBleManagerCallback {
    private String mSN, mMAC;
    private boolean isFindBluetooth = false;
    private HashMap<String, BluetoothDeviceInfo> mDeviceMap;

    public static final String BASE_OTA_DIR = Environment.getExternalStorageDirectory() + File.separator + "ota_file";
    public static final String OTA_FILE_NAME = "ota_file.bin";
    private static final int REQUEST_CODE_LE = 1;

    //BLE驱动版本
    private static final String ORG_BLUETOOTH_CHARACTERISTIC_SOFTWARE_REVISION_STRING = "00002a28-0000-1000-8000-00805f9b34fb";
    //OTA状态通知特征
    private static final String SPOTA_SERVICE_STATUS_CHARACTERISTIC_UUID = "5f78df94-798c-46f5-990a-b3eb6a065c88";
    //OTA状态通知特征的开关DESCRIPTOR
    private static final String SPOTA_DESCRIPTOR_UUID = "00002902-0000-1000-8000-00805f9b34fb";
    public static final String SPOTA_MEM_DEV_UUID = "8082caa8-41a6-4021-91c6-56f9b954cc34";
    public static final String SPOTA_GPIO_MAP_UUID = "724249f0-5ec3-4b5f-8804-42345af08651";
    public static final String SPOTA_PATCH_LEN_UUID = "9d84b9a3-000c-49d8-9183-855b673fda31";
    public static final String SPOTA_PATCH_DATA_UUID = "457871e8-d516-4ca1-9116-57d0b17b9cb2";

    private static final int END_SIGNAL = 0xfe000000;
    private static final int REBOOT_SIGNAL = 0xfd000000;

    private ImageView mIVOTA;
    //private static final int MESSAGE_OTA_IMAGE = 1;
    //private WeakReference<Drawable> mOTADrawable;
    //private int mOTAIndex;
    private String mCurrentVersion;
    private AnimationDrawable mAnimationDrawable;
    private BleManager mBleManager;
    private CommonManager mCommonManager;
    private static final int WRITE_SEND_DELAY = 1;

    private Runnable mRebootRunnable = new Runnable() {
        @Override
        public void run() {
            Toast.makeText(OTAVersionActivity.this, R.string.common_ota_success, Toast.LENGTH_LONG).show();
            LogUtils.showLog("OtaToupdate", "mRebootRunnable 硬件升级成功  finish");
            finishing();
        }
    };

    private boolean mHasNotificationOpened = false;

    /**
     * private static final int[] OTA_LIST = new int[]{
     * R.mipmap.ota_update_logo_00,  R.mipmap.ota_update_logo_01, R.mipmap.ota_update_logo_02, R.mipmap.ota_update_logo_03,
     * R.mipmap.ota_update_logo_04,  R.mipmap.ota_update_logo_05, R.mipmap.ota_update_logo_06, R.mipmap.ota_update_logo_07,
     * R.mipmap.ota_update_logo_08,  R.mipmap.ota_update_logo_09, R.mipmap.ota_update_logo_10, R.mipmap.ota_update_logo_11,
     * R.mipmap.ota_update_logo_12,  R.mipmap.ota_update_logo_13, R.mipmap.ota_update_logo_14};
     */
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case InnerMessageConfig.COMMON_CHECK_VERSION_MESSAGE_SUCCESS:
                    String http_result = (String) msg.obj;
                    outputMessage("handleMessage::success::what= " + http_result);
                    try {
                        JSONObject check_version_obj = new JSONObject(http_result);
                        String newest_version = check_version_obj.getString(CommonSystemConfig.CheckAppUpdateMessageConfig.JSON_RESPONSE_KEY_NEWEST_VERSION);
                        String lowest_version = check_version_obj.getString(CommonSystemConfig.CheckAppUpdateMessageConfig.JSON_RESPONSE_KEY_LOWEST_VERSION);
                        String download_link = check_version_obj.getString(CommonSystemConfig.CheckAppUpdateMessageConfig.JSON_RESPONSE_KEY_LINK);
                        double current_version = Double.parseDouble(mCurrentVersion.replace(".", ""));
                        double target_version = Double.parseDouble(newest_version.replace(".", ""));
                        LogUtils.showLog("OtaToupdate", "current_version：" + current_version);
                        LogUtils.showLog("OtaToupdate", "target_version：" + target_version);
                        if (current_version != target_version) {
                            if (null != mDownloadTask) {
                                mDownloadTask.cancel(true);
                                mDownloadTask = null;
                            }
                            LogUtils.showLog("OtaToupdate", "new DownloadTask ");
                            mDownloadTask = new DownloadTask(download_link);
                            mDownloadTask.execute("Begin download");
                        } else {
                            Toast.makeText(OTAVersionActivity.this, R.string.now_is_new_version, Toast.LENGTH_LONG).show();
                            finishing();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    break;
                case InnerMessageConfig.COMMON_CHECK_VERSION_MESSAGE_FALSE:
                    String http_false_result = (String) msg.obj;
                    outputMessage("handleMessage::false::what= " + http_false_result);
                    Toast.makeText(OTAVersionActivity.this, R.string.common_http_false, Toast.LENGTH_LONG).show();
                    break;
                default:
                    break;
            }
        }
    };
    private TextView mTvUpdateHint;//ota_update_hint
    private TextView mTvSeekBar;//tv_seekbar
    private SeekBar mSeekBar;//seekbar
    private boolean isFinish = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_otaversion);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.common_back_title);
        TextView mTvTitle = (TextView) findViewById(R.id.title_text);
        mTvTitle.setText(R.string.common_ota_version);
        RelativeLayout mRLBack=(RelativeLayout)findViewById(R.id.title_back);
        mRLBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        setView();

    }

    private void setView() {
        mIVOTA = (ImageView) findViewById(R.id.ota_update_logo);
        mAnimationDrawable = (AnimationDrawable) mIVOTA.getBackground();
        mBleManager = new BleManager(this, this);
        mCommonManager = new CommonManager(this, mHandler);
        mCurrentVersion = "";

        mDeviceMap = new HashMap<>();
        if (checkPermission()) {
            if (!mBleManager.isLeEnabled()) {//如果蓝牙没有打开，则提示打开
                Intent enable_le_intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enable_le_intent, REQUEST_CODE_LE);
            } else {
                mBleManager.startManager();
            }
        }
        mTvUpdateHint = (TextView) findViewById(R.id.ota_update_hint);
        mTvSeekBar = (TextView) findViewById(R.id.tv_seekbar);
        mSeekBar = (SeekBar) findViewById(R.id.seekbar);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAnimationDrawable.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAnimationDrawable.stop();
        //mBleManager.unBindManagerService();
    }


    @Override
    public void finish() {
        //重置一些設置然後結束
        mBlockCounter = 0;
        mChunkCounter = 0;
        mIsLastBlock = false;
        mHasRepairBlockLength = false;
        mIsEndSingle = false;

        super.finish();
    }
    public void finishing() {
        if (mBleManager.isConnectToDevice()) {
            isFinish = true;
            mBleManager.destroyManager();
        } else {
            mBleManager.unBindManagerService();
            finish();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (PackageManager.PERMISSION_GRANTED == grantResults[0]) {

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
//        if (PackageManager.PERMISSION_GRANTED == grantResults[0]) {//如果授权成功
//            if (mBleManager.isLeEnabled()) {//如果蓝牙没有打开，则提示打开
//                Intent enable_le_intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
//                startActivityForResult(enable_le_intent, REQUEST_CODE_LE);
//            } else {
//                mBleManager.startManager();
//                //mBleManager.startScanLeDevice();
//            }
//        } else {//如果授权失败，结束界面
//            Toast.makeText(this, R.string.common_grant_permission_false, Toast.LENGTH_SHORT).show();
//            LogUtils.showLog("OtaToupdate","onRequestPermissionsResult 授权失败  finish");
//            finishing();
//        }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (Activity.RESULT_OK == resultCode) {//模块打开成功
            mBleManager.startManager();
        } else {
            Toast.makeText(this, R.string.common_open_le_false, Toast.LENGTH_SHORT).show();
            finishing();
        }
    }

    public void onClick(View v) {
        LogUtils.showLog("OtaToupdate", "onClick   finish");
        finishing();
    }

    @Override
    public void onBleManagerReady(boolean is_ready) {
        outputMessage("onBleManagerReady::info= " + is_ready);
        LogUtils.showLog("motioning", "onBleManagerReady isready:" + is_ready);

        if (is_ready) {//管理器已就绪
            if (mBleManager.isConnectToDevice()) {//当前已经有BLE连接,直接读取BLE软件版本
                mTvUpdateHint.setText(R.string.ota_update_hint);
                mBleManager.readUUid(ORG_BLUETOOTH_CHARACTERISTIC_SOFTWARE_REVISION_STRING);
            } else {
                //Toast.makeText(this, "", Toast.LENGTH_LONG).show();
                LogUtils.showLog("OtaToupdate", "当前未有BLE连接,开始重新连接 finish");
                //开始连接流程---------------->
                mBleManager.startScanLeDevice();
                //如果连接任何失败出去
                //成功走上面的流程
            }
        } else {
            Toast.makeText(this, "", Toast.LENGTH_LONG).show();
            LogUtils.showLog("OtaToupdate", "管理器未就绪 finish");
            finishing();
        }
    }

    @Override
    public void onStartScanDevice(boolean is_success) {
        LogUtils.showLog("OtaToupdate", "onStartScanDevice is_success:" + is_success);

        outputMessage("onStartScanDevice::info= " + is_success);
        if (null != mDeviceMap) {
            mDeviceMap.clear();
            mDeviceMap = null;
        }
        mDeviceMap = new HashMap<>();
    }

    @Override
    public void onStopScanDevice(boolean is_found) {
        LogUtils.showLog("OtaToupdate", "onStopScanDevice is_found:" + is_found);

        if (is_found) {

        } else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //查找蓝牙的列表（读取出来）
                    ArrayList<BluetoothData> mBluetoothList = DatabaseService.findBluetoothList(JordanApplication.getUsername(OTAVersionActivity.this));
                    if(mBluetoothList.size()==0){
                        ToastUtils.shortToast(OTAVersionActivity.this, R.string.common_please_bind_bluetooth_to_person_data);
                        finishing();
                        return;
                    }
                    //遍历mDeviceMap
//                    if (mDeviceMap.size() == 0) {
//                        ToastUtils.shortToast(OTAVersionActivity.this, R.string.common_please_bind_bluetooth);
//                        finishing();
//                        return;
//                    }
//                    for (String key : mDeviceMap.keySet()) {
//                        for (int i = 0; i < mBluetoothList.size(); i++) {
//                            LogUtils.showLog("OtaToupdate", "onStopScanDevice key：" + key);
//                            LogUtils.showLog("OtaToupdate", "onStopScanDevice key.replace(\":\",\"\")：" + key.replace(":", ""));
//                            LogUtils.showLog("OtaToupdate", "onStopScanDevice mBluetoothList.get(i).getMac()：" + mBluetoothList.get(i).getMac());
//                            //如果MAC相同，提取名称开始连接
//                            if (key.replace(":", "").equalsIgnoreCase(mBluetoothList.get(i).getMac())) {
//                                mSN = mBluetoothList.get(i).getSn();
//                                mMAC = mBluetoothList.get(i).getMac();
//                                mBleManager.setDeviceInfo(mDeviceMap.get(key).getDeviceName(), mDeviceMap.get(key).getDeviceAddress());
//                                mBleManager.connectToDevice(mDeviceMap.get(key).getDeviceName(), mDeviceMap.get(key).getDeviceAddress());
//                                return;
//                            }
//                        }
//                    }
                    if(!isFindBluetooth) {
                        //未发现到绑定设备
                        ToastUtils.shortToast(OTAVersionActivity.this, R.string.common_please_bind_bluetooth_to_person_data);
                    }
                }
            });
        }

    }

    @Override
    public void onScanDevice(String device_address, String device_name, String device_class, byte[] broadcast_record) {
        LogUtils.showLog("OtaToupdate", "onScanDevice address= " + device_address + " name= " + device_name + " class= " + device_class);
        BluetoothDeviceInfo current_device_info = new BluetoothDeviceInfo(device_name, device_address, device_class);
        if(!isFindBluetooth) {
            ArrayList<BluetoothData> mBluetoothList = DatabaseService.findBluetoothList(JordanApplication.getUsername(OTAVersionActivity.this));
            for (int i = 0; i < mBluetoothList.size(); i++) {
                if (device_address.replace(":", "").equalsIgnoreCase(mBluetoothList.get(i).getMac())) {
                    isFindBluetooth = true;
                    mSN = mBluetoothList.get(i).getSn();
                    mMAC = mBluetoothList.get(i).getMac();
                    mBleManager.setDeviceInfo(current_device_info.getDeviceName(), current_device_info.getDeviceAddress());
                    mBleManager.connectToDevice(current_device_info.getDeviceName(), current_device_info.getDeviceAddress());
                    return;
                }
            }
        }
    }

    @Override
    public void onConnectDevice(boolean is_success) {
        LogUtils.showLog("OtaToupdate", "onConnectDevice is_success= " + is_success);

        if (is_success) {
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mTvUpdateHint.setText(R.string.ota_update_hint);
                    mBleManager.readUUid(ORG_BLUETOOTH_CHARACTERISTIC_SOFTWARE_REVISION_STRING);
                }
            }, 5000);
        } else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (!isFinish) {
                        ToastUtils.shortToast(OTAVersionActivity.this, "蓝牙断开请重新开始");
                        finishing();
                    }else {
                        mBleManager.unBindManagerService();
                        finish();
                    }
                }
            });
        }
    }

    @Override
    public void onInitialNotification(boolean is_success) {

        LogUtils.showLog("OtaToupdate", "onInitialNotification is_success= " + is_success);

        if (is_success) {

        }
    }

    @Override
    public void onReadCharacteristic(boolean is_success, String ch_uuid, byte[] ble_value) {
        LogUtils.showLog("OtaToupdate", "onReadCharacteristic is_success:" + is_success);
        outputMessage("onReadCharacteristic::info::success= " + is_success +
                " uuid= " + ch_uuid + " ble_value= " + Arrays.toString(ble_value));

        if (is_success) {
            if (ORG_BLUETOOTH_CHARACTERISTIC_SOFTWARE_REVISION_STRING.equals(ch_uuid)) {
                mCurrentVersion = new String(ble_value);
                outputMessage("onReadCharacteristic::info::sw= " + mCurrentVersion);
                mCommonManager.checkVersion(HttpUtilsConfig.HARD_WARE_TYPE);
            }
        } else {
            if (ORG_BLUETOOTH_CHARACTERISTIC_SOFTWARE_REVISION_STRING.equals(ch_uuid)) {//读取BLE SW版本失败
                Toast.makeText(this, R.string.common_get_sw_false, Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onCharacteristicChange(boolean is_success, String ch_uuid, byte[] ble_value) {
        LogUtils.showLog("OtaToupdate", "onCharacteristicChange is_success:" + is_success);

    }

    /**
     * 由于OTAFile工具将一个文件分为一个三维数组的数据结构。
     * 1维（由mBlockCounter描述）表示：文件分块的数量，每一块大小为240，最后一块为：文件有效长度/240的余数。
     * Block数量算法：Ceil(文件有效长度/240)
     * 2维（mChunkCounter描述）表示：每一块文件的Chunks数量。每一个Chunk大小为20，最后一块的Block大小为（文件有效长度/240的余数）
     * Chunks数量算法：Block大小/20
     * 3维：每一个Chunks包含的Chunk
     */
    private int mBlockCounter = 0;//文件大块数据
    private int mChunkCounter = 0;//小块数据。
    private boolean mIsLastBlock = false;//最后一块需要重置长度
    private boolean mHasRepairBlockLength = false;//是否正在重置最后一块的长度
    private boolean mIsEndSingle = false;
    private boolean mIsRebootSingle = false;

    @Override
    public void onWriteCharacteristic(boolean is_success, String ch_uuid, byte[] ble_value) {
        LogUtils.showLog("OtaToupdate", "onWriteCharacteristic is_success:" + is_success);
        outputMessage("onWriteCharacteristic::info= " + is_success + " ch_uuid= " + ch_uuid + " value= " + Arrays.toString(ble_value));
        if (is_success) {
            if (SPOTA_MEM_DEV_UUID.equals(ch_uuid)) {//设置MEM_DEV成功，继续设置MAP
                if (mIsRebootSingle) {
                    LogUtils.showLog("OtaToupdate", "mRebootRunnable");
                    mHandler.postDelayed(
                            mRebootRunnable, WRITE_SEND_DELAY);
                } else {
                    LogUtils.showLog("OtaToupdate", "mIsEndSingle:" + mIsEndSingle);
                    if (mIsEndSingle) {//终止信号发送成功后发送重启信号
                        LogUtils.showLog("OtaToupdate", "终止信号发送成功后发送重启信号");
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mBleManager.writeUUidInt(SPOTA_MEM_DEV_UUID, REBOOT_SIGNAL, BluetoothGattCharacteristic.FORMAT_UINT32);
                                mIsRebootSingle = true;
                            }
                        }, WRITE_SEND_DELAY);
                    } else {
                        //打开开关后需要开始第二步设置：设置MAP
                        LogUtils.showLog("OtaToupdate", "打开开关后需要开始第二步设置：设置MAP");
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mBleManager.writeUUidInt(SPOTA_GPIO_MAP_UUID, getDeviceMemParam(), BluetoothGattCharacteristic.FORMAT_UINT32);
                            }
                        }, WRITE_SEND_DELAY);
//                        mBleManager.writeUUidInt(SPOTA_GPIO_MAP_UUID, getDeviceMemParam(), BluetoothGattCharacteristic.FORMAT_UINT32);
                    }
                }
            } else if (SPOTA_GPIO_MAP_UUID.equals(ch_uuid)) {
                LogUtils.showLog("OtaToupdate", "设置MAP后需要开始第三步设置：设置文件长度");
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mBleManager.writeUUidInt(SPOTA_PATCH_LEN_UUID, mOTAFile.getFileBlockSize(), BluetoothGattCharacteristic.FORMAT_UINT16);
                    }
                }, WRITE_SEND_DELAY);
            } else if (SPOTA_PATCH_LEN_UUID.equals(ch_uuid)) {
                LogUtils.showLog("OtaToupdate", "三步设置完成后开始发送数据,初始化一些变量");
                if (!mIsLastBlock) {
                    mBlockCounter = 0;
                    mChunkCounter = 0;
                } else {
                    mHasRepairBlockLength = true;
                }
                LogUtils.showLog("OtaToupdate", "发送第" + mBlockCounter + "大块");
                LogUtils.showLog("OtaToupdate", "发送第" + mChunkCounter + "小块");
                //发送第一块
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        sendBlock();
                    }
                }, WRITE_SEND_DELAY);
            } else if (SPOTA_PATCH_DATA_UUID.equals(ch_uuid)) {
                if (mBlockCounter == 0) {
                    mChunkCounter = mChunkCounter + 1;
                }

                LogUtils.showLog("OtaToupdate", "发送第" + mChunkCounter + "小块");
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        sendBlock();
                    }
                }, WRITE_SEND_DELAY);
                if (mBlockCounter != 0) {
                    mChunkCounter = mChunkCounter + 1;
                }
            }
        } else {//任何步骤失败则退出升级
            if (!mIsRebootSingle) {
                Toast.makeText(this, R.string.common_ota_false, Toast.LENGTH_LONG).show();
                LogUtils.showLog("OtaToupdate", "onWriteCharacteristic finish");
                finishing();//结束界面
            }
        }
    }

    @Override
    public void onDescriptorWrite(boolean is_success, String ch_uuid) {
        LogUtils.showLog("OtaToupdate", "onDescriptorWrite is_success:" + is_success);
        outputMessage("onDescriptorWrite::info::success= " + is_success + " uuid= " + ch_uuid);
        //OTA的第一步，设置通知
        //if (SPOTA_DESCRIPTOR_UUID.equals(ch_uuid)){
        //打开开关后需要开始第一步设置：OTA内存设置
        //mBleManager.writeUUidInt(SPOTA_MEM_DEV_UUID, getSpotaMemDev(), BluetoothGattCharacteristic.FORMAT_UINT32);
        //}
    }

    protected int getSpotaMemDev() {
        LogUtils.showLog("OtaToupdate", "getSpotaMemDev:");
        int memType = (0x13 << 24) | 240;
        return memType;
    }

    private boolean checkPermission() {
        if (PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_CODE_LE);
            return false;
        }
        return true;
    }

    private static final String MISO_GPIO = "0x02";
    private static final String MOSI_GPIO = "0x01";
    private static final String CS_GPIO = "0x05";
    private static final String SCK_GPIO = "0x00";

    private int getDeviceMemParam() {
        LogUtils.showLog("OtaToupdate", "getDeviceMemParam:");
        int miso_gpio_int = Integer.decode(MISO_GPIO);
        int mosi_gpio_int = Integer.decode(MOSI_GPIO);
        int cs_gpio_int = Integer.decode(CS_GPIO);
        int sck_gpio_int = Integer.decode(SCK_GPIO);
        LogUtils.showLog("OtaToupdate", "getDeviceMemParam:" + ((miso_gpio_int << 24) | (mosi_gpio_int << 16) | (cs_gpio_int << 8) | sck_gpio_int));
        return (miso_gpio_int << 24) | (mosi_gpio_int << 16) | (cs_gpio_int << 8) | sck_gpio_int;
    }

    private void sendBlock() {
        LogUtils.showLog("OtaToupdate", "sendBlock:");
        //计算进度。最大为100.-----------------------------------设置进度条
        float progress = ((float) (mBlockCounter + 1) / (float) mOTAFile.getNumberOfBlocks()) * 100;
        int p = (int) progress;
        mTvSeekBar.setText(p+"%");
        mSeekBar.setProgress(p);
        LogUtils.showLog("OtaToupdate", "sendBlock progress:" + progress);
        boolean is_success = false;
        LogUtils.showLog("OtaToupdate", "mBlockCounter：" + mBlockCounter);
        LogUtils.showLog("OtaToupdate", "mOTAFile.getNumberOfBlocks()：" + mOTAFile.getNumberOfBlocks());
        if (mBlockCounter < mOTAFile.getNumberOfBlocks() - 1) {//一般发送
            LogUtils.showLog("OtaToupdate", "一般发送");
            byte[][] send_data_chunk = mOTAFile.getBlock(mBlockCounter);
            int send_data_chunk_length = send_data_chunk.length;
            LogUtils.showLog("OtaToupdate", "mChunkCounter：" + mChunkCounter);
            LogUtils.showLog("OtaToupdate", "send_data_chunk_length：" + send_data_chunk_length);
            if (mChunkCounter < send_data_chunk_length) {
                byte[] send_data = mOTAFile.getBlock(mBlockCounter)[mChunkCounter];
                is_success = mBleManager.writeUUidByte(SPOTA_PATCH_DATA_UUID, send_data);
            } else {//最后一个chuck后Block计数器+1继续SendChunk
                mBlockCounter = mBlockCounter + 1;
                mChunkCounter = 0;
                LogUtils.showLog("OtaToupdate", "发送第" + mBlockCounter + "大块");
                LogUtils.showLog("OtaToupdate", "发送第" + mChunkCounter + "小块");
                if (mBlockCounter != mOTAFile.getNumberOfBlocks() - 1) {
                    byte[] send_data = mOTAFile.getBlock(mBlockCounter)[mChunkCounter];
                    is_success = mBleManager.writeUUidByte(SPOTA_PATCH_DATA_UUID, send_data);
                } else {
                    LogUtils.showLog("OtaToupdate", "最后一块sendBlock");
                    mIsLastBlock = true;
                    int current_length = mOTAFile.getNumberOfBytes() % mOTAFile.getFileBlockSize();//最后一块的数据可能小于设置的数量，需要重置长度
                    is_success = mBleManager.writeUUidInt(SPOTA_PATCH_LEN_UUID, current_length, BluetoothGattCharacteristic.FORMAT_UINT16);

                }
            }
            if (!is_success&&!mIsEndSingle) {//交互失败
                Toast.makeText(this, R.string.common_ota_false, Toast.LENGTH_LONG).show();
                LogUtils.showLog("OtaToupdate", "sendBlock 交互失败  finish111111");
                finishing();
            }
        } else if (mBlockCounter == mOTAFile.getNumberOfBlocks() - 1) {//最后一块
            LogUtils.showLog("OtaToupdate", "最后一块");
            if (mHasRepairBlockLength) {//最后一块长度设定好以后继续发送
                byte[][] last_send_data_chunk = mOTAFile.getBlock(mBlockCounter);
                int last_send_data_chunk_length = last_send_data_chunk.length;
                if (mChunkCounter < last_send_data_chunk_length) {//最后一个Block的最后一个Chunk发送
                    byte[] send_data = mOTAFile.getBlock(mBlockCounter)[mChunkCounter];
                    is_success = mBleManager.writeUUidByte(SPOTA_PATCH_DATA_UUID, send_data);
                } else {
                    mBlockCounter = mBlockCounter + 1;
                    mChunkCounter = 0;
                    LogUtils.showLog("OtaToupdate", "发送第" + mBlockCounter + "大块");
                    LogUtils.showLog("OtaToupdate", "发送第" + mChunkCounter + "小块");
                    sendBlock();
                }
            } else {
                mIsLastBlock = true;
                int current_length = mOTAFile.getNumberOfBytes() % mOTAFile.getFileBlockSize();//最后一块的数据可能小于设置的数量，需要重置长度
                is_success = mBleManager.writeUUidInt(SPOTA_PATCH_LEN_UUID, current_length, BluetoothGattCharacteristic.FORMAT_UINT16);
            }
            if (!is_success&&!mIsEndSingle) {//交互失败
                Toast.makeText(this, R.string.common_ota_false, Toast.LENGTH_LONG).show();
                LogUtils.showLog("OtaToupdate", "sendBlock 交互失败  finish222222222222");
                finishing();
            }
        } else {//最后Block的最后一个
            mIsEndSingle = true;
            LogUtils.showLog("OtaToupdate", "最后Block的最后一个");
            is_success = mBleManager.writeUUidInt(SPOTA_MEM_DEV_UUID, END_SIGNAL, BluetoothGattCharacteristic.FORMAT_UINT32);
            LogUtils.showLog("OtaToupdate", "最后Block的最后一个 is_success：" + is_success);
            if (is_success) {
                mTvSeekBar.setText("OTA升级完成");
                LogUtils.showLog("OtaToupdate", "最后Block的最后一个 return：");
                return;
            }
        }


    }

    /**
     * private void setView() {
     * mIVOTA = (ImageView)findViewById(R.id.ota_update_logo);
     * mOTAIndex = 0;
     * mOTADrawable = new WeakReference<Drawable>(getResources().getDrawable(OTA_LIST[mOTAIndex]));
     * mIVOTA.setBackgroundResource(OTA_LIST[mOTAIndex]);
     * mHandler.sendEmptyMessageDelayed(MESSAGE_OTA_IMAGE, 3);
     * }
     */

    private OTAFile mOTAFile = null;

    private class DownloadTask extends AsyncTask<String, String, Boolean> {
        private String mFileUrl;

        public DownloadTask(String target_url) {
            LogUtils.showLog("OtaToupdate", "DownloadTask:");
            mFileUrl = target_url;
        }

        @Override
        protected Boolean doInBackground(String... params) {
            LogUtils.showLog("OtaToupdate", "DownloadTask doInBackground:");
            boolean is_success = downloadFile(mFileUrl);
            LogUtils.showLog("OtaToupdate", "downloadFile：" + is_success);
            return is_success;
        }

        @Override
        protected void onPostExecute(Boolean download_result) {
            LogUtils.showLog("OtaToupdate", "DownloadTask onPostExecute:");
            super.onPostExecute(download_result);
            try {
                LogUtils.showLog("OtaToupdate", "DownloadTask download_result:" + download_result);
                if (download_result) {
                    //初始化OTA文件，分块
                    mOTAFile = OTAFile.getByFileName(BASE_OTA_DIR, OTA_FILE_NAME);
                    mOTAFile.setType(0);
                    mOTAFile.setFileBlockSize(240);
                    //下载完成后开始打开通知
                    //mBleManager.setNotificationCH(SPOTA_SERVICE_STATUS_CHARACTERISTIC_UUID);
                    //开启进度条
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            //打开开关后需要开始第一步设置：OTA内存设置
                            mBleManager.writeUUidInt(SPOTA_MEM_DEV_UUID, getSpotaMemDev(), BluetoothGattCharacteristic.FORMAT_UINT32);
                        }
                    }, 5000);
                }
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(OTAVersionActivity.this, " OTA FALSE", Toast.LENGTH_LONG).show();
            }
        }

        private boolean downloadFile(String download_link) {
            LogUtils.showLog("OtaToupdate", "DownloadTask downloadFile:" + download_link);
            outputMessage("downloadFile::info= " + download_link);
            boolean is_success = false;
            File ota_dir = new File(BASE_OTA_DIR);
            if (!ota_dir.exists()) {
                ota_dir.mkdirs();
            } else {
                if (!ota_dir.isDirectory()) {
                    ota_dir.delete();
                    ota_dir.mkdirs();
                }
            }
            ota_dir.setReadable(true);
            ota_dir.setWritable(true);

            File oat_file = new File(ota_dir, OTA_FILE_NAME);
            if (oat_file.exists()) {
                oat_file.delete();
            }

            InputStream http_input_stream = null;
            OutputStream local_output_stream = null;
            URLConnection download_connection = null;
            try {
                oat_file.createNewFile();
                download_connection = new URL(download_link).openConnection();
                download_connection.setConnectTimeout(1000 * 10);
                http_input_stream = download_connection.getInputStream();
                local_output_stream = new FileOutputStream(oat_file);
                int bytesRead = 0;
                byte[] buffer = new byte[1024 * 100];
                while ((bytesRead = http_input_stream.read(buffer)) != -1) {
                    local_output_stream.write(buffer, 0, bytesRead);
                }
                is_success = true;
            } catch (Exception e) {
                e.printStackTrace();

            } finally {
                if (null != local_output_stream) {
                    try {
                        local_output_stream.flush();
                        local_output_stream.close();
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }

                if (null != http_input_stream) {
                    try {
                        http_input_stream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            return is_success;
        }
    }

    private DownloadTask mDownloadTask;

    private static boolean ENABLE_LOG = true;

    private static void outputMessage(String output_message) {
        if (ENABLE_LOG)
            android.util.Log.e("OTAVersionActivity", output_message);
    }

    private static final int REQUEST_CODE_OTA = 9999;

    public static void startOTA(Activity source_activity) {
        Intent start_ota_intent = new Intent("com.jordan.project.OTA_VERSION");
        source_activity.startActivityForResult(start_ota_intent, REQUEST_CODE_OTA);
    }
    @Override
    public void onBackPressed() {
        finishing();
        //super.onBackPressed();
    }
}
