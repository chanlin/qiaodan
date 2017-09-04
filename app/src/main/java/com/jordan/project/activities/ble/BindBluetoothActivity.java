package com.jordan.project.activities.ble;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.FeatureInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.DecodeHintType;
import com.google.zxing.Result;
import com.jordan.commonlibrary.CommonManager;
import com.safari.httplibs.config.InnerMessageConfig;
import com.jordan.project.JordanApplication;
import com.jordan.project.R;
import com.jordan.project.activities.ble.handle.CaptureActivityHandler;
import com.jordan.project.activities.ble.manager.CameraManager;
import com.jordan.project.activities.ble.manager.InactivityTimer;
import com.jordan.project.activities.ble.view.ViewfinderView;
import com.jordan.project.utils.JsonFalseUtils;
import com.jordan.project.utils.LogUtils;
import com.jordan.project.utils.SettingSharedPerferencesUtil;
import com.jordan.project.utils.ToastUtils;
import com.jordan.usersystemlibrary.UserManager;

import org.json.JSONException;

import java.io.IOException;
import java.util.Map;
import java.util.Vector;

public class BindBluetoothActivity extends Activity implements Callback {

    private CaptureActivityHandler handler;
    private ViewfinderView viewfinderView;
    private boolean hasSurface;
    private Vector<BarcodeFormat> decodeFormats;
    private Map<DecodeHintType, ?> decodeHints;
    private String characterSet;
    private InactivityTimer inactivityTimer;
    private MediaPlayer mediaPlayer;
    private boolean playBeep;
    private static final float BEEP_VOLUME = 0.10f;
    private boolean vibrate;
    private int state = 0;
    private CommonManager commonManager;
    private UserManager userManager;
    private String shoesSN = "";
    private String shoesMAC = "";
    private static final int N0_PERMISSION = 21;
    private boolean mIsGrant;
    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case N0_PERMISSION:
                    ToastUtils.shortToast(BindBluetoothActivity.this, R.string.please_open_permission);
                    break;
                case 0:
                    ToastUtils.shortToast(BindBluetoothActivity.this, getResources().getString(R.string.common_please_scan_app_context));
                    finish();
                    break;
                case InnerMessageConfig.COMMON_BLUETOOTH_BIND_MESSAGE_SUCCESS:
                    String snAndMac = shoesSN+"|"+shoesMAC;
                    SettingSharedPerferencesUtil.SetChoiesBluetoothValue(BindBluetoothActivity.this
                            ,JordanApplication.getUsername(BindBluetoothActivity.this),snAndMac);
                    LogUtils.showLog("Result", "COMMON_BLUETOOTH_BIND_MESSAGE_SUCCESS result:" + (String) msg.obj);
                    ToastUtils.shortToast(BindBluetoothActivity.this, "蓝牙绑定成功");
                    //提示蓝牙列表界面可以刷新
                    JordanApplication.isUpdateBluetoothList = true;
                    finish();
                    //shoesBind(shoesSN);
                    break;
                case InnerMessageConfig.COMMON_BLUETOOTH_BIND_MESSAGE_EXCEPTION:
                    ToastUtils.shortToast(BindBluetoothActivity.this, getResources().getString(R.string.http_exception));
                    finish();
                    break;
                case InnerMessageConfig.COMMON_BLUETOOTH_BIND_MESSAGE_FALSE:
                    try {

                        String errorMsg = JsonFalseUtils.onlyErrorCodeResult((String) msg.obj);
                        if (errorMsg != null)
                            ToastUtils.shortToast(BindBluetoothActivity.this, errorMsg);
                        //为了测试
                        JordanApplication.isUpdateBluetoothList = true;
                        finish();
                    } catch (JSONException e) {
                        mHandler.sendEmptyMessage(InnerMessageConfig.COMMON_GET_CODE_MESSAGE_EXCEPTION);
                        e.printStackTrace();
                    }

                    break;
                case InnerMessageConfig.USER_SHOES_BIND_MESSAGE_SUCCESS:
                    LogUtils.showLog("Result", "COMMON_BLUETOOTH_BIND_MESSAGE_SUCCESS result:" + (String) msg.obj);
                    ToastUtils.shortToast(BindBluetoothActivity.this, "蓝牙绑定成功");
                    //提示蓝牙列表界面可以刷新
                    finish();
                    break;
                case InnerMessageConfig.USER_SHOES_BIND_MESSAGE_EXCEPTION:
                    ToastUtils.shortToast(BindBluetoothActivity.this, getResources().getString(R.string.http_exception));
                    finish();
                    break;
                case InnerMessageConfig.USER_SHOES_BIND_MESSAGE_FALSE:
                    try {
                        String errorMsg = JsonFalseUtils.onlyErrorCodeResult((String) msg.obj);
                        if (errorMsg != null)
                            ToastUtils.shortToast(BindBluetoothActivity.this, errorMsg);
                        finish();
                    } catch (JSONException e) {
                        mHandler.sendEmptyMessage(InnerMessageConfig.COMMON_GET_CODE_MESSAGE_EXCEPTION);
                        e.printStackTrace();
                    }

                    break;
            }
        }

        ;
    };
    int screenMode;
    int screenBrightness;
    private ImageView openLight;
    private boolean status = false;
    private TextView mTVScanList;
    private Camera m_Camera;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_bind_bluetooth);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.bluetooth_list_title);
        userManager = new UserManager(BindBluetoothActivity.this, mHandler);
        commonManager = new CommonManager(BindBluetoothActivity.this, mHandler);
        CameraManager.init(this);
        TextView mTvTitle = (TextView) findViewById(R.id.title_text);
        mTvTitle.setText(getResources().getString(R.string.common_bind_bluetooth));
        RelativeLayout mRLAdd = (RelativeLayout) findViewById(R.id.bluetooth_add);
        mRLAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mIsGrant) {
                    Intent intent = new Intent(BindBluetoothActivity.this,BluetoothScanListActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
        mTVScanList = (TextView) findViewById(R.id.tv_scan_list_hint);
        mTVScanList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mIsGrant) {
                    Intent intent = new Intent(BindBluetoothActivity.this,BluetoothScanListActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
        RelativeLayout mRLDelete = (RelativeLayout) findViewById(R.id.bluetooth_delete);
        mRLDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        mRLDelete.setVisibility(View.GONE);
        RelativeLayout mRLBack = (RelativeLayout) findViewById(R.id.title_back);
        mRLBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //ViewUtil.addTopView(getApplicationContext(), this, R.string.scan_card);

        state = getIntent().getIntExtra("state", 0);
        viewfinderView = (ViewfinderView) findViewById(R.id.viewfinder_view);

        hasSurface = false;
        inactivityTimer = new InactivityTimer(this);
        mIsGrant = false;
        checkCameraPermission();
        /* 
                    * 获得当前屏幕亮度的模式  
                    * SCREEN_BRIGHTNESS_MODE_AUTOMATIC=1 为自动调节屏幕亮度 
                    * SCREEN_BRIGHTNESS_MODE_MANUAL=0 为手动调节屏幕亮度 
                    */
        try {
            screenMode = Settings.System.getInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE);
            // 获得当前屏幕亮度值 0--255
            screenBrightness = Settings.System.getInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS);
            //  如果当前的屏幕亮度调节调节模式为自动调节，则改为手动调节屏幕亮度    
            if (screenMode == Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC) {
                setScreenMode(Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
            }

            //  设置屏幕亮度值为最大值255
            setScreenBrightness(255.0F);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
        initLight();

    }

    private void initLight() {
        openLight = (ImageView) findViewById(R.id.openLight);
        openLight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!status) {
                    openLight.setBackgroundResource(R.mipmap.open_light_pressed);
                    status = true;
                    PackageManager pm = BindBluetoothActivity.this.getPackageManager();
                    FeatureInfo[] features = pm.getSystemAvailableFeatures();
                    for (FeatureInfo f : features) {
                        if (PackageManager.FEATURE_CAMERA_FLASH.equals(f.name))    //判断设备是否支持闪光灯
                        {
                            if (null == m_Camera) {
                                if(viewfinderView.getCameraManager().isOpen()){
                                    m_Camera=viewfinderView.getCameraManager().getCamera().getCamera();
                                }else {
                                    m_Camera = Camera.open();
                                }
                            }


                            Camera.Parameters parameters = m_Camera.getParameters();
                            parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                            m_Camera.setParameters(parameters);
//                            m_Camera.startPreview();
                            openLight.setBackgroundResource(R.mipmap.open_light_pressed);
                        }
                    }
                } else {
                    if (m_Camera != null) {
                        Camera.Parameters parameters = m_Camera.getParameters();
                        parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);//关闭
                        m_Camera.setParameters(parameters);
//                        m_Camera.stopPreview();
//                        m_Camera.release();
//                        m_Camera = null;
                    }
                    openLight.setBackgroundResource(R.mipmap.open_light_normal);
                    status = false;
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        inactivityTimer.shutdown();
        //   设置屏幕亮度值为原来的
        setScreenBrightness(screenBrightness);
        //   设置当前屏幕亮度的模式   为原来的
        setScreenMode(screenMode);
        super.onDestroy();
    }

    /**
     * 设置当前屏幕亮度的模式
     * SCREEN_BRIGHTNESS_MODE_AUTOMATIC=1 为自动调节屏幕亮度
     * SCREEN_BRIGHTNESS_MODE_MANUAL=0 为手动调节屏幕亮度
     */
    private void setScreenMode(int value) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (Settings.System.canWrite(BindBluetoothActivity.this)) {
                    Settings.System.putInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE, value);
                } else {
                    Intent intent = new Intent(android.provider.Settings.ACTION_MANAGE_WRITE_SETTINGS);
                    intent.setData(Uri.parse("package:" + getPackageName()));
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            } else {
                Settings.System.putInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE, value);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置当前屏幕亮度值 0--255，并使之生效
     */
    private void setScreenBrightness(float value) {
        Window mWindow = getWindow();
        WindowManager.LayoutParams mParams = mWindow.getAttributes();
        float f = value / 255.0F;
        mParams.screenBrightness = f;
        mWindow.setAttributes(mParams);

        // 保存设置的屏幕亮度值
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (Settings.System.canWrite(BindBluetoothActivity.this)) {
                Settings.System.putInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, (int) value);
            } else {
                Intent intent = new Intent(android.provider.Settings.ACTION_MANAGE_WRITE_SETTINGS);
                intent.setData(Uri.parse("package:" + getPackageName()));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        } else {
            Settings.System.putInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, (int) value);
        }
    }

    private void checkCameraPermission() {
        int is_granted = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        if (PackageManager.PERMISSION_GRANTED != is_granted) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 1000);
        } else {
            mIsGrant = true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (PackageManager.PERMISSION_GRANTED == grantResults[0]) {
            mIsGrant = true;
        }else{
            mHandler.sendEmptyMessage(N0_PERMISSION);
            finish();
        }
    }

    private void bluetoothBind(String sn, String mac) {
        commonManager.bluetoothBind(sn, mac);


    }

    private void shoesBind(String name) {
        userManager.shoesBind("1", "", name, "乔丹智能篮球鞋V1", "1000", "绿色", "43", "", "", "");


    }

    @Override
    protected void onResume() {
        super.onResume();
        SurfaceView surfaceView = (SurfaceView) findViewById(R.id.preview_view);
        SurfaceHolder surfaceHolder = surfaceView.getHolder();
        if (hasSurface) {
            initCamera(surfaceHolder);
        } else {
            surfaceHolder.addCallback(this);
            surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }
        decodeFormats = null;
        characterSet = null;

        playBeep = true;
        AudioManager audioService = (AudioManager) getSystemService(AUDIO_SERVICE);
        if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
            playBeep = false;
        }
        initBeepSound();
        vibrate = true;

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (handler != null) {
            handler.quitSynchronously();
            handler = null;
        }
        CameraManager.get().closeDriver();
    }


    public void handleDecode(Result result, Bitmap barcode) {
        inactivityTimer.onActivity();
        playBeepSoundAndVibrate();
        String resultString = result.getText();
        LogUtils.showLog("bluetoothscan", "result:" + resultString);
        LogUtils.showLog("bluetoothscan", "result.length:" + resultString.length());
        if (resultString != null && resultString.contains("/") && resultString.length() == 28) {
            shoesSN = resultString.substring(0, resultString.indexOf("/"));
            shoesMAC = resultString.substring(resultString.indexOf("/") + 1);
            bluetoothBind(shoesSN, shoesMAC);
        } else {
            //提示用户扫描出来的而结果不对
            mHandler.sendEmptyMessage(0);
        }
    }


    private void initCamera(SurfaceHolder surfaceHolder) {
        try {
            CameraManager.get().openDriver(surfaceHolder);
        } catch (IOException ioe) {
            return;
        } catch (RuntimeException e) {
            return;
        }
        if (handler == null) {
            handler = new CaptureActivityHandler(this, decodeFormats, decodeHints,
                    characterSet, CameraManager.get());
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (!hasSurface) {
            hasSurface = true;
            initCamera(holder);
        }

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        hasSurface = false;

    }

    public ViewfinderView getViewfinderView() {
        return viewfinderView;
    }

    public Handler getHandler() {
        return handler;
    }

    public void drawViewfinder() {
        viewfinderView.drawViewfinder();

    }

    private void initBeepSound() {
        if (playBeep && mediaPlayer == null) {
            // The volume on STREAM_SYSTEM is not adjustable, and users found it
            // too loud,
            // so we now play on the music stream.
            setVolumeControlStream(AudioManager.STREAM_MUSIC);
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setOnCompletionListener(beepListener);

            AssetFileDescriptor file = getResources().openRawResourceFd(
                    R.raw.beep);
            try {
                mediaPlayer.setDataSource(file.getFileDescriptor(),
                        file.getStartOffset(), file.getLength());
                file.close();
                mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
                mediaPlayer.prepare();
            } catch (IOException e) {
                mediaPlayer = null;
            }
        }
    }

    private static final long VIBRATE_DURATION = 200L;

    private void playBeepSoundAndVibrate() {
        if (playBeep && mediaPlayer != null) {
            mediaPlayer.start();
        }
        if (vibrate) {
            Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
            vibrator.vibrate(VIBRATE_DURATION);
        }
    }

    /**
     * When the beep has finished playing, rewind to queue up another one.
     */
    private final MediaPlayer.OnCompletionListener beepListener = new MediaPlayer.OnCompletionListener() {
        public void onCompletion(MediaPlayer mediaPlayer) {
            mediaPlayer.seekTo(0);
        }
    };

    public CameraManager getCameraManager() {
        return CameraManager.get();
    }

}
