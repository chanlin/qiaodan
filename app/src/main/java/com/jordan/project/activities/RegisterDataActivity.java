package com.jordan.project.activities;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jordan.commonlibrary.CommonManager;
import com.safari.httplibs.config.InnerMessageConfig;
import com.jordan.project.BuildConfig;
import com.jordan.project.JordanApplication;
import com.jordan.project.R;
import com.jordan.project.config.ActivityActionConfig;
import com.jordan.project.database.DatabaseService;
import com.jordan.project.entity.UserInfoData;
import com.jordan.project.utils.FileUtils;
import com.jordan.project.utils.JsonFalseUtils;
import com.jordan.project.utils.JsonSuccessUtils;
import com.jordan.project.utils.LogUtils;
import com.jordan.project.utils.SettingSharedPerferencesUtil;
import com.jordan.project.utils.ToastUtils;
import com.jordan.project.utils.TypeUtils;
import com.jordan.project.utils.UploadPictureHasZoomUtils;
import com.jordan.project.widget.ChooesDialog;
import com.jordan.project.widget.LoadingProgressDialog;
import com.jordan.project.widget.PickerView;
import com.jordan.project.widget.PickerViewDialog;
import com.jordan.project.widget.TwoPickerViewDialog;
import com.jordan.usersystemlibrary.UserManager;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class RegisterDataActivity extends Activity implements View.OnClickListener {
    private EditText mETNick, mETHeight, mETWeight, mETAgo, mETStadiumPosition;
    private ImageView mIVHead;
    String file_full_path = "";
    String img = "";
    private static final int NO_PIC = 0;
    private static final int N0_PERMISSION = 1;
    private static final int UPDATE_EDITTEXT = 2;
    private boolean mIsGrant;
    public static final int COME_FROM_REGISTER = 1;
    public static final int COME_FROM_USER_DATA = 2;
    private int come;
    Bitmap photo;

    private Handler mRegisterDataHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case NO_PIC:
                    ToastUtils.shortToast(RegisterDataActivity.this, getResources().getString(R.string.no_photo));
                    break;
                case InnerMessageConfig.USER_MODIFY_USER_DATA_ALL_MESSAGE_SUCCESS:
                    LoadingProgressDialog.Dissmiss();
                    //解析正确参数-内容在msg.obj
                    //成功替用户登录
                    if (come == COME_FROM_REGISTER) {
                        handleResultIfSuccess();
                        finish();
                    } else {
                        JordanApplication.isCreatePlayBallUpdateJoin=true;
                        doGetUserDataTask();
                    }
                    break;
                case InnerMessageConfig.USER_MODIFY_USER_DATA_ALL_MESSAGE_EXCEPTION:
                    LoadingProgressDialog.Dissmiss();
                    ToastUtils.shortToast(RegisterDataActivity.this, getResources().getString(R.string.http_exception));
                    break;
                case InnerMessageConfig.USER_MODIFY_USER_DATA_ALL_MESSAGE_FALSE:
                    LoadingProgressDialog.Dissmiss();
                    try {
                        String errorMsg = JsonFalseUtils.onlyErrorCodeResult((String) msg.obj);
                        if (errorMsg != null)
                            ToastUtils.shortToast(RegisterDataActivity.this, errorMsg);
                    } catch (JSONException e) {
                        mRegisterDataHandler.sendEmptyMessage(InnerMessageConfig.COMMON_GET_CODE_MESSAGE_EXCEPTION);
                        e.printStackTrace();
                    }

                    break;
                case InnerMessageConfig.COMMON_UPLOAD_MEDIA_MESSAGE_SUCCESS:
                    //解析正确参数-内容在msg.obj
                    try {
                        img = JsonSuccessUtils.getImgId((String) msg.obj);
                        if(photo!=null)
                        mIVHead.setImageBitmap(photo);
                        LoadingProgressDialog.Dissmiss();
                        ToastUtils.shortToast(RegisterDataActivity.this,R.string.upload_photo_success);
                    } catch (JSONException e) {
                        mRegisterDataHandler.sendEmptyMessage(InnerMessageConfig.COMMON_GET_CODE_MESSAGE_EXCEPTION);
                        e.printStackTrace();
                    }
                    //成功提示用户头像上传成功
                    break;
                case InnerMessageConfig.COMMON_UPLOAD_MEDIA_MESSAGE_EXCEPTION:
                    LoadingProgressDialog.Dissmiss();
                    //ToastUtils.shortToast(RegisterDataActivity.this, getResources().getString(R.string.http_exception));
                    ToastUtils.shortToast(RegisterDataActivity.this,R.string.upload_photo_false);
                    break;
                case InnerMessageConfig.COMMON_UPLOAD_MEDIA_MESSAGE_FALSE:
                    try {
                        String errorMsg = JsonFalseUtils.onlyErrorCodeResult((String) msg.obj);
                        if (errorMsg != null)
                            ToastUtils.shortToast(RegisterDataActivity.this, errorMsg);
                        LoadingProgressDialog.Dissmiss();
                    } catch (JSONException e) {
                        mRegisterDataHandler.sendEmptyMessage(InnerMessageConfig.COMMON_GET_CODE_MESSAGE_EXCEPTION);
                        e.printStackTrace();
                    }

                    break;
                case N0_PERMISSION:
                    ToastUtils.shortToast(RegisterDataActivity.this, getResources().getString(R.string.please_open_permission));
                    break;
                case InnerMessageConfig.USER_GET_USER_DATA_MESSAGE_SUCCESS:
                    //解析正确参数-内容在msg.obj
                    //保存登录返回数据
                    LogUtils.showLog("Result", "USER_GET_USER_DATA_MESSAGE_SUCCESS result:" + (String) msg.obj);
                    //保存用户信息
                    try {
                        UserInfoData userInfoData = JsonSuccessUtils.getUserData((String) msg.obj);
                        DatabaseService.createUserInfo(userInfoData.getUsername(), userInfoData.getName(), userInfoData.getNick(),
                                userInfoData.getGender(), userInfoData.getAge(), userInfoData.getBirthday(), userInfoData.getPosition(),
                                userInfoData.getWeight(), userInfoData.getHeight(), userInfoData.getQq(), userInfoData.getMobile(),
                                userInfoData.getEmail(), userInfoData.getImg(),userInfoData.getImgId());
                    } catch (JSONException e) {
                        mRegisterDataHandler.sendEmptyMessage(InnerMessageConfig.USER_LOGIN_MESSAGE_EXCEPTION);
                        e.printStackTrace();
                    }
                    finish();
                    break;
                case InnerMessageConfig.USER_GET_USER_DATA_MESSAGE_EXCEPTION:
                    JordanApplication.isUpdateUserData = true;
                    //ToastUtils.shortToast(UserDataActivity.this,getResources().getString(R.string.http_exception));
                    finish();
                    break;
                case InnerMessageConfig.USER_GET_USER_DATA_MESSAGE_FALSE:
                    JordanApplication.isUpdateUserData = true;
//                    try {
//                        String errorMsg= JsonFalseUtils.onlyErrorCodeResult((String)msg.obj);
//                        if(errorMsg!=null)
//                            ToastUtils.shortToast(UserDataActivity.this,errorMsg);
//                    } catch (JSONException e) {
//                        mMainHandler.sendEmptyMessage(InnerMessageConfig.USER_LOGIN_MESSAGE_EXCEPTION);
//                        e.printStackTrace();
//                    }
                    finish();
                    break;
                case UPDATE_EDITTEXT:
                    switch (whoShowDialog) {
                        case DIALOG_HEIGHT:
                            mETHeight.setText(dialogValue);
                            break;
                        case DIALOG_WEIGHT:
                            mETWeight.setText(dialogValue);
                            break;
                        case DIALOG_AGO:
                            mETAgo.setText(dialogValue);
                            break;
                        case DIALOG_POSITION:
                            mETStadiumPosition.setText(dialogValue);
                            break;
                    }
                    break;
            }

        }
    };
    private UserManager userManager;
    private CommonManager commonManager;
    private int whoShowDialog = 0;
    public static final int DIALOG_HEIGHT = 1;
    public static final int DIALOG_WEIGHT = 2;
    public static final int DIALOG_AGO = 3;
    public static final int DIALOG_POSITION = 4;
    private String dialogValue = "";
    private String dialogOneValue = "";
    private String dialogTwoValue = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_register_data);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.common_text_more_title);
        userManager = new UserManager(RegisterDataActivity.this, mRegisterDataHandler);
        commonManager = new CommonManager(RegisterDataActivity.this, mRegisterDataHandler);
        commonManager.setUserToken(userManager.getUserToken());
        come = getIntent().getIntExtra("come", COME_FROM_REGISTER);
        setView();
        setListener();
        mIsGrant = false;
        checkCameraPermission();
//        if (android.os.Build.VERSION.SDK_INT > 9) {
//            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
//                    .permitAll().build();
//            StrictMode.setThreadPolicy(policy);
//        }

    }

    private void doGetUserDataTask() {
        userManager.getUserData(JordanApplication.getUsername(RegisterDataActivity.this));
    }


    private void checkCameraPermission() {
        int is_granted = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (PackageManager.PERMISSION_GRANTED != is_granted) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1000);
        } else {
            mIsGrant = true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (PackageManager.PERMISSION_GRANTED == grantResults[0]) {
            mIsGrant = true;
        }
    }

    private void setView() {
        mETNick = (EditText) findViewById(R.id.register_nick_name_et);
        mETHeight = (EditText) findViewById(R.id.register_height_et);
        mETWeight = (EditText) findViewById(R.id.register_weight_et);
        mETAgo = (EditText) findViewById(R.id.register_ago_et);
        mETStadiumPosition = (EditText) findViewById(R.id.register_stadium_position_et);

        mIVHead = (ImageView) findViewById(R.id.register_data_head_phone);
        mIVHead.setOnClickListener(this);

        TextView mTvTitle = (TextView) findViewById(R.id.register_title_text);
        mTvTitle.setText(getResources().getString(R.string.common_perfect_data));
        RelativeLayout mRLBack=(RelativeLayout)findViewById(R.id.title_back);
        mRLBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        TextView mTvMore = (TextView) findViewById(R.id.register_title_more);
        mTvMore.setOnClickListener(this);
        if (come == COME_FROM_REGISTER) {
            mTvMore.setVisibility(View.VISIBLE);
        }else{
            mTvMore.setVisibility(View.GONE);
        }


        Button register_data_cancel = (Button) findViewById(R.id.register_data_cancel);
        register_data_cancel.setOnClickListener(this);
        Button register_data_submit = (Button) findViewById(R.id.register_data_submit);
        register_data_submit.setOnClickListener(this);
        if (come == COME_FROM_USER_DATA) {
            initData();
            img=getIntent().getStringExtra("img");
            LogUtils.showLog("IMAGEID", "getIntent img:" + img);
        }
    }

    private void initData() {
        //赋值Data
        //获取数据库用户信息
        UserInfoData userInfoData = DatabaseService.findUserInfo(JordanApplication.getUsername(RegisterDataActivity.this));
        if (userInfoData != null) {
            if (!userInfoData.getNick().equals("null"))
                mETNick.setText(userInfoData.getNick());
            if (!userInfoData.getWeight().equals("null"))
                mETWeight.setText(userInfoData.getWeight());
            if (!userInfoData.getHeight().equals("null"))
                mETHeight.setText(userInfoData.getHeight());
            if (!userInfoData.getAge().equals("null"))
                mETAgo.setText(userInfoData.getAge());
            if (!userInfoData.getPosition().equals("null"))
                mETStadiumPosition.setText(userInfoData.getPosition());

            //ImageLoader导入图片
            if (!userInfoData.getImg().equals("null")) {
                DisplayImageOptions options = new DisplayImageOptions.Builder()
                        .showImageOnLoading(R.mipmap.add_phone)
                        .showImageForEmptyUri(R.mipmap.add_phone)
                        .showImageOnFail(R.mipmap.add_phone).cacheInMemory(true)
                        .cacheOnDisk(true).considerExifParams(true).build();
                ImageLoader.getInstance().displayImage(userInfoData.getImg(), mIVHead, options);
            }
        }
    }

    private void setListener() {
        mETWeight.setOnClickListener(this);
        mETHeight.setOnClickListener(this);
        mETAgo.setOnClickListener(this);
        mETStadiumPosition.setOnClickListener(this);
    }

    private void doRegisterData() {
        String current_nick = mETNick.getText().toString();
        String current_height = mETHeight.getText().toString();
        String current_weight = mETWeight.getText().toString();
        String current_ago = mETAgo.getText().toString();
        String current_stadium_position = mETStadiumPosition.getText().toString();
        android.util.Log.e("Photo", "current_nick:" + current_nick + "|img " + img + "|current_ago"
                + current_ago);
        LoadingProgressDialog.show(RegisterDataActivity.this, false, true, 30000);
        //Do register data
        userManager.modifyAllUserData("", current_nick, TypeUtils.REGISTER_GENDER_TYPE_OF_MAN,
                current_ago, "", current_stadium_position, current_weight, current_height, "", img);
    }

    private void uploadMediaData() {
        LoadingProgressDialog.show(RegisterDataActivity.this, false, true, 30000);
        //Do upload media
        commonManager.uploadMedia(TypeUtils.UPLOAD_MEDIA_TYPE_HEAD_PIC, file_full_path);
        LogUtils.showLog("Photo", "uploadMedia Path:" + file_full_path);
    }

    private void handleResultIfSuccess() {
        String current_phone = getIntent().getStringExtra("phone");
        String current_password = getIntent().getStringExtra("password");
        SettingSharedPerferencesUtil.SetLoginUsernameValue(RegisterDataActivity.this, current_phone);
        SettingSharedPerferencesUtil.SetPasswordUsernameValue(RegisterDataActivity.this, current_password);
        JordanApplication.isRegister = true;
        Intent intent = new Intent();
//        intent.putExtra(ActivityActionConfig.KEY_LOGIN_USER_NAME, current_phone);
//        intent.putExtra(ActivityActionConfig.KEY_LOGIN_USER_PASSWORD, current_password);
        setResult(ActivityActionConfig.RESULT_CODE_REGISTER_SUCCESS, intent);
    }

    private void handleResultIfFalse(String result_json) {
        setResult(ActivityActionConfig.RESULT_CODE_REGISTER_FALSE);
        finish();
    }

    private void handleGetCodeResult(String result_json) {

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.register_data_head_phone:
                //调用Dialog 拍照或者相册
                if (mIsGrant) {
                    showHeadDialog();
                } else {
                    mRegisterDataHandler.sendEmptyMessage(N0_PERMISSION);
                }
                break;
            case R.id.register_data_cancel:
                //关闭界面回退登录界面-不替用户登录
                finish();
                break;
            case R.id.register_data_submit:
                //提交信息
                doRegisterData();
                break;
            case R.id.register_title_more:
                //关闭界面回退登录界面-自动登录
                if (come == COME_FROM_REGISTER) {
                    handleResultIfSuccess();
                }
                finish();
                break;
            case R.id.register_height_et:
                whoShowDialog = DIALOG_HEIGHT;
                showPickerViewDialog(getResources().getString(R.string.common_unit_cm));
                break;
            case R.id.register_weight_et:
                whoShowDialog = DIALOG_WEIGHT;
                showPickerViewDialog(getResources().getString(R.string.common_unit_kg));
                break;
            case R.id.register_ago_et:
                whoShowDialog = DIALOG_AGO;
                showPickerViewDialog(getResources().getString(R.string.ago_year));
                break;
            case R.id.register_stadium_position_et:
                whoShowDialog = DIALOG_POSITION;
                showPickerViewDialog("");
                break;
        }
    }

    private void showHeadDialog() {
        final Dialog dialog = new ChooesDialog(RegisterDataActivity.this,
                R.style.chooes_dialog_style);
        dialog.show();
        Window window = dialog.getWindow();
//        WindowManager.LayoutParams lp = window.getAttributes();
//        lp.alpha = 0.8f;
//        window.setAttributes(lp);
        RelativeLayout btnPhoto = (RelativeLayout) window.findViewById(R.id.rl_chooes_dialog_photo);
        btnPhoto.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                android.util.Log.e("Photo", "btnPhoto");
                UploadPictureHasZoomUtils.setPhoto(RegisterDataActivity.this);
                dialog.dismiss();
            }
        });
        RelativeLayout btnImage = (RelativeLayout) window.findViewById(R.id.rl_chooes_dialog_image);
        btnImage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                android.util.Log.e("Photo", "btnImage");
                //UploadPictureHasZoomUtils.setManually(RegisterDataActivity.this);
                UploadPictureHasZoomUtils.callGalleryForInputImage(100,RegisterDataActivity.this);
                dialog.dismiss();
            }
        });
        RelativeLayout btnCancel = (RelativeLayout) window.findViewById(R.id.rl_chooes_dialog_cancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }

    private void showPickerViewDialog(String text) {
        final Dialog dialog = new PickerViewDialog(RegisterDataActivity.this,
                R.style.chooes_dialog_style);
        dialog.show();
        Window window = dialog.getWindow();
//        WindowManager.LayoutParams lp = window.getAttributes();
//        lp.alpha = 0.8f;
//        window.setAttributes(lp);
        PickerView pickerView = (PickerView) window.findViewById(R.id.register_data_picker_view);
        List<String> data = new ArrayList<String>();
        data = getData();
        pickerView.setData(data,getPosition());
        pickerView.setOnSelectListener(new PickerView.onSelectListener() {
            @Override
            public void onSelect(String text) {
                dialogValue = text;
            }
        });
        Button btnCancel = (Button) window.findViewById(R.id.register_data_chooes_cancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });
        Button btnSubmit = (Button) window.findViewById(R.id.register_data_chooes_submit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mRegisterDataHandler.sendEmptyMessage(UPDATE_EDITTEXT);
                dialog.dismiss();
            }
        });
        TextView tvHint = (TextView) window.findViewById(R.id.pick_view_hint);
        tvHint.setText(text);

    }

    private void showTwoPickerViewDialog() {
        final Dialog dialog = new TwoPickerViewDialog(RegisterDataActivity.this,
                R.style.chooes_dialog_style);
        dialog.show();
        Window window = dialog.getWindow();
//        WindowManager.LayoutParams lp = window.getAttributes();
//        lp.alpha = 0.8f;
//        window.setAttributes(lp);
        PickerView pickerViewOne = (PickerView) window.findViewById(R.id.register_data_picker_view_one);
        List<String> dataOne = new ArrayList<String>();
        dataOne = getData();
        pickerViewOne.setData(dataOne,getPosition());
        pickerViewOne.setOnSelectListener(new PickerView.onSelectListener() {
            @Override
            public void onSelect(String text) {
                dialogOneValue = text;
            }
        });

        PickerView pickerViewTwo = (PickerView) window.findViewById(R.id.register_data_picker_view_two);
        List<String> dataTwo = new ArrayList<String>();
        dataTwo = getTwoData();
        pickerViewTwo.setData(dataTwo,0);
        pickerViewTwo.setOnSelectListener(new PickerView.onSelectListener() {
            @Override
            public void onSelect(String text) {
                dialogTwoValue = text;
            }
        });
        Button btnCancel = (Button) window.findViewById(R.id.register_data_chooes_cancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });
        Button btnSubmit = (Button) window.findViewById(R.id.register_data_chooes_submit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mRegisterDataHandler.sendEmptyMessage(UPDATE_EDITTEXT);
                dialog.dismiss();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        android.util.Log.e("Photo", "onActivityResult:" + "resultCode " + resultCode + "|requestCode"
                + requestCode);

        if (resultCode == -1) {
            if (requestCode == UploadPictureHasZoomUtils.REQUEST_IMAGE) { // 相册
                android.util.Log.e("Photo", "REQUEST_IMAGE");
                Uri imageFilePath = data.getData();
                file_full_path = FileUtils.getFileAbsolutePath(RegisterDataActivity.this, imageFilePath);
                UploadPictureHasZoomUtils.startPhotoZoom(imageFilePath, this);//要求截图
//                try {
//                    Uri imageFilePath = data.getData();
//
//                    //file_full_path = imageFilePath.getPath();
//                    android.util.Log.e("Photo", "imageFilePath" + file_full_path);
//                    uploadMediaData();
//                    ContentResolver resolver = getContentResolver();
//                    Bitmap photo = PictureUtils.ScaleToStandard(PictureUtils.getPicFromUri(imageFilePath, resolver));
//                    int degree = PictureUtils.getBitmapDegree(file_full_path);
//                    photo = PictureUtils.rotateBitmapByDegree(photo, degree);
//                    if (photo == null)
//                        android.util.Log.e("Photo", "photo==null");
//                    mIVHead.setImageBitmap(photo);
////                    UploadPictureHasZoomUtils.startPhotoZoom(imageFilePath, this);// 要求截图
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
            }
            if (requestCode == UploadPictureHasZoomUtils.PHOTOHRAPH) {
                try {
                    android.util.Log.e("Photo", "PHOTOHRAPH");
//                    Bitmap photo = null;
                    file_full_path = UploadPictureHasZoomUtils.IMAGE_FILE_PATH;
                    File pictureFile = new File(file_full_path);
                    if (Build.VERSION.SDK_INT < 24) {
                        android.util.Log.e("Photo", "PHOTOHRAPH Build.VERSION.SDK_INT:"+Build.VERSION.SDK_INT);
                        UploadPictureHasZoomUtils.startPhotoZoom(Uri.fromFile(pictureFile),this);
                    }else{
                        android.util.Log.e("Photo", "PHOTOHRAPH Build.VERSION.SDK_INT:"+Build.VERSION.SDK_INT);
                        UploadPictureHasZoomUtils.startPhotoZoom(FileProvider.getUriForFile(RegisterDataActivity.this,
                                BuildConfig.APPLICATION_ID + ".provider",
                                pictureFile),this);
                    }
                    android.util.Log.e("Photo", "PHOTOHRAPH over");
//                    UploadPictureHasZoomUtils.startPhotoZoom(Uri.fromFile(pictureFile),this);
//                    if (data != null) {
//                        LogUtils.showLog("Photo", "data!=null");
//                        Uri uri = data.getData();
//                        if (uri == null) {
//                            LogUtils.showLog("Photo", "uri=null");
//                            //use bundle to get data
//                            Bundle bundle = data.getExtras();
//                            if (bundle != null) {
//                                LogUtils.showLog("Photo", "bundle!=null");
//                                photo = (Bitmap) bundle.get("data"); //get bitmap
//                                if (photo != null) {
//                                    LogUtils.showLog("Photo", "photo!=null");
//                                } else {
//                                    LogUtils.showLog("Photo", "photo==null");
//                                }
//                            } else {
//                                LogUtils.showLog("Photo", "bundle==null");
//                                return;
//                            }
//                        } else {
//                            //to do find the path of pic by uri
//                            LogUtils.showLog("Photo", "uri!=null");
//                            try {
//                                photo = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
//                            } catch (FileNotFoundException e) {
//                                LogUtils.showLog("Photo", "FileNotFoundException");
//                                e.printStackTrace();
//                            } catch (IOException e) {
//                                LogUtils.showLog("Photo", "IOException");
//                                e.printStackTrace();
//                            }
//                        }
//                    } else {
//                        android.util.Log.e("Photo", "PHOTOHRAPH");
//                        File pictureFile = new File(file_full_path);
//                        photo = PictureUtils.ScaleToStandard(PictureUtils.BytetoBitmap(FileUtils.getBytesFromFile(pictureFile)));
//                    }
//                    android.util.Log.e("Photo", "data==null");
//                    int degree = PictureUtils.getBitmapDegree(file_full_path);
//                    photo = PictureUtils.rotateBitmapByDegree(photo, degree);
//                    mIVHead.setImageBitmap(photo);
//                    uploadMediaData();
//                    UploadPictureHasZoomUtils.startPhotoZoom(
//                            Uri.fromFile(pictureFile), this);
                } catch (Exception e) {
                    android.util.Log.e("Photo", "PHOTOHRAPH ex");
                    e.printStackTrace();
                }
            }


            if(requestCode == UploadPictureHasZoomUtils.ZOOMOK)//截图完毕
            {
                android.util.Log.e("Photo", "ZOOMOK");
                Bundle extras = data.getExtras();
                try {
                    Uri imageFilePath = UploadPictureHasZoomUtils.imageUri;
                    file_full_path = FileUtils.getFileAbsolutePath(RegisterDataActivity.this, imageFilePath);
                    Log.e("Photo", "ZOOMOK:"+file_full_path);
                    //需要压缩文件
                    android.util.Log.e("Photo", "ZOOMOK decodeStream");
                    photo = BitmapFactory.decodeStream(getContentResolver().openInputStream(UploadPictureHasZoomUtils.imageUri));
                    android.util.Log.e("Photo", "ZOOMOK setImageBitmap");
                    android.util.Log.e("Photo", "ZOOMOK uploadMediaData");
                    uploadMediaData();
                    android.util.Log.e("Photo", "ZOOMOK over");
                } catch (FileNotFoundException e) {
                    android.util.Log.e("Photo", "ZOOMOK ex");
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        } else {
            mRegisterDataHandler.sendEmptyMessage(NO_PIC);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public int getPosition() {
        int position = 0;
        List<String> list = new ArrayList<String>();
        switch (whoShowDialog) {
            case DIALOG_HEIGHT:
                for (int i = 130; i <= 240; i++) {
                    list.add(String.valueOf(i));
                    if(i==170){
                        position=list.size()-1;
                    }
                }
                dialogValue = list.get(position);
                break;
            case DIALOG_WEIGHT:
                for (int i = 30; i <= 200; i++) {
                    list.add(String.valueOf(i));
                    if(i==65){
                        position=list.size()-1;
                    }
                }
                dialogValue = list.get(position);
                break;
            case DIALOG_AGO:
                for (int i = 1; i <= 100; i++) {
                    list.add(String.valueOf(i));
                    if(i==18){
                        position=list.size()-1;
                    }
                }
                dialogValue = list.get(position);
                break;
            case DIALOG_POSITION:
                position=2;
                list.add("控球后卫");
                list.add("得分后卫");
                list.add("小前锋");
                list.add("大前锋");
                list.add("中锋");
                dialogValue = "小前锋";
                break;
        }
        return position;
    }
    public List<String> getData() {
        List<String> list = new ArrayList<String>();
        switch (whoShowDialog) {
            case DIALOG_HEIGHT:
                for (int i = 130; i <= 240; i++) {
                    list.add(String.valueOf(i));
                }
                dialogValue = String.valueOf(170);
                break;
            case DIALOG_WEIGHT:
                for (int i = 30; i <= 200; i++) {
                    list.add(String.valueOf(i));
                }
                dialogValue = String.valueOf(65);
                break;
            case DIALOG_AGO:
                for (int i = 1; i <= 100; i++) {
                    list.add(String.valueOf(i));
                }
                dialogValue = String.valueOf(18);
                break;
            case DIALOG_POSITION:
                list.add("控球后卫");
                list.add("得分后卫");
                list.add("小前锋");
                list.add("大前锋");
                list.add("中锋");
                dialogValue = "小前锋";
                break;
        }
        return list;
    }

    public List<String> getTwoData() {
        List<String> list = new ArrayList<String>();
        switch (whoShowDialog) {
            case DIALOG_WEIGHT:
                for (int i = 0; i <= 9; i++) {
                    list.add(String.valueOf(i));
                }
                dialogTwoValue = String.valueOf(0);
                break;
        }
        return list;
    }
}
