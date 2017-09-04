package com.jordan.project.activities.main;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jordan.commonlibrary.CommonManager;
import com.safari.httplibs.config.InnerMessageConfig;
import com.safari.httplibs.utils.CommonUtils;
import com.jordan.project.R;
import com.jordan.project.activities.train.TrainDetailActivity;
import com.jordan.project.utils.JsonFalseUtils;
import com.jordan.project.utils.JsonSuccessUtils;
import com.jordan.project.utils.LogUtils;
import com.jordan.project.utils.ToastUtils;
import com.jordan.project.utils.TypeUtils;

import org.json.JSONException;

import java.lang.ref.WeakReference;

public class SoftVersionActivity extends Activity implements View.OnClickListener{
    private ImageView mIVEnd;
    private static final int MESSAGE_CHANGE_END_IMAGE = 1;
    private WeakReference<Drawable> mCurrentEndDrawable;
    private int mCurrentEndIndex;
    private static final int[] DRAWABLE_END_LIST = new int[]{
            R.mipmap.motion_end_000,  R.mipmap.motion_end_001, R.mipmap.motion_end_002, R.mipmap.motion_end_003,
            R.mipmap.motion_end_004,  R.mipmap.motion_end_005, R.mipmap.motion_end_006, R.mipmap.motion_end_007,
            R.mipmap.motion_end_008,  R.mipmap.motion_end_009, R.mipmap.motion_end_010, R.mipmap.motion_end_011,
            R.mipmap.motion_end_012,  R.mipmap.motion_end_013, R.mipmap.motion_end_014, R.mipmap.motion_end_015,
            R.mipmap.motion_end_016,  R.mipmap.motion_end_017, R.mipmap.motion_end_018, R.mipmap.motion_end_019,
            R.mipmap.motion_end_020, R.mipmap.motion_end_021,R.mipmap.motion_end_022,  R.mipmap.motion_end_023,
            R.mipmap.motion_end_024, R.mipmap.motion_end_025,R.mipmap.motion_end_026,  R.mipmap.motion_end_027,
            R.mipmap.motion_end_028, R.mipmap.motion_end_029,R.mipmap.motion_end_030, R.mipmap.motion_end_031,
            R.mipmap.motion_end_032,  R.mipmap.motion_end_033, R.mipmap.motion_end_034, R.mipmap.motion_end_035,
            R.mipmap.motion_end_036,  R.mipmap.motion_end_037, R.mipmap.motion_end_038, R.mipmap.motion_end_039};
    private TextView mTVVersion;
    private TextView mTVTerms;//terms_and_conditions
    private TextView mTVPrivacy;//privacy_statement
    private CommonManager commonManager;
    private String newVersion = "";
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_CHANGE_END_IMAGE:
                    mCurrentEndIndex = mCurrentEndIndex + 1;
                    if(mCurrentEndIndex >= DRAWABLE_END_LIST.length){
                        mCurrentEndIndex=0;
                    }
                    int resource_id = DRAWABLE_END_LIST[mCurrentEndIndex];
                    //android.util.Log.e("SlashInfo", "index= " + mCurrentEndIndex + " total= " + DRAWABLE_END_LIST.length + " resource_id= " + resource_id);
                    mIVEnd.setBackgroundResource(resource_id);
                    sendEmptyMessageDelayed(MESSAGE_CHANGE_END_IMAGE, 3);
                    break;
                case InnerMessageConfig.COMMON_CHECK_VERSION_MESSAGE_SUCCESS:
                    //解析正确参数-内容在msg.obj
                    try {
                        LogUtils.showLog("Result", "COMMON_CHECK_VERSION_MESSAGE_SUCCESS result:"+(String)msg.obj);
                        newVersion = JsonSuccessUtils.getVersion((String) msg.obj);
                        if(newVersion.equals(CommonUtils.getAppVersion(SoftVersionActivity.this))){
                            mTVVersion.setText(getResources().getString(R.string.common_version_number)+CommonUtils.getAppVersion(SoftVersionActivity.this)+"   "+getResources().getString(R.string.now_is_new_version));
                        }else{
                            mTVVersion.setText(getResources().getString(R.string.common_version_number)+ CommonUtils.getAppVersion(SoftVersionActivity.this));
                            //跳转webview
                            Intent intent = new Intent(SoftVersionActivity.this,TrainDetailActivity.class);
                            intent.putExtra("id","1");
                            intent.putExtra("link",JsonSuccessUtils.getVersionLink((String) msg.obj));
                            LogUtils.showLog("Result", "link:"+JsonSuccessUtils.getVersionLink((String) msg.obj));
                            intent.putExtra("title","版本更新");
                            startActivity(intent);
                        }
                    } catch (JSONException e) {
                        mHandler.sendEmptyMessage(InnerMessageConfig.COMMON_CHECK_VERSION_MESSAGE_EXCEPTION);
                        e.printStackTrace();
                    }
                    //成功提示用户头像上传成功
                    break;
                case InnerMessageConfig.COMMON_CHECK_VERSION_MESSAGE_EXCEPTION:
                    ToastUtils.shortToast(SoftVersionActivity.this, getResources().getString(R.string.http_exception));
                    break;
                case InnerMessageConfig.COMMON_CHECK_VERSION_MESSAGE_FALSE:
                    try {
                        String errorMsg = JsonFalseUtils.onlyErrorCodeResult((String) msg.obj);
                        if(errorMsg!=null)
                            ToastUtils.shortToast(SoftVersionActivity.this, errorMsg);
                    } catch (JSONException e) {
                        mHandler.sendEmptyMessage(InnerMessageConfig.COMMON_CHECK_VERSION_MESSAGE_EXCEPTION);
                        e.printStackTrace();
                    }

                    break;

            }

        };
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_soft_version);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.common_back_title_bg_black);
        commonManager = new CommonManager(SoftVersionActivity.this, mHandler);
        TextView mTvTitle = (TextView) findViewById(R.id.title_text);
        mTvTitle.setText(getResources().getString(R.string.about_jordan_soft));
        RelativeLayout mRLBack=(RelativeLayout)findViewById(R.id.title_back);
        mRLBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        setView();
        setListener();
        checkVersion();
    }
    private void checkVersion() {
        //Do upload media
        commonManager.checkVersion(TypeUtils.CHECK_VERSION_TYPE_ANDROID);
    }

    private void setView() {

        mIVEnd = (ImageView)findViewById(R.id.jordan_soft_end_iv);
        mCurrentEndIndex = 0;
        mCurrentEndDrawable = new WeakReference<Drawable>(getResources().getDrawable(DRAWABLE_END_LIST[mCurrentEndIndex]));
        mIVEnd.setBackgroundResource(DRAWABLE_END_LIST[mCurrentEndIndex]);
        mHandler.sendEmptyMessageDelayed(MESSAGE_CHANGE_END_IMAGE, 3);
        mTVVersion = (TextView)findViewById(R.id.jordan_soft_version);
        mTVVersion.setText(getResources().getString(R.string.common_version_number)+CommonUtils.getAppVersion(SoftVersionActivity.this));
        mTVTerms = (TextView)findViewById(R.id.terms_and_conditions);
        mTVPrivacy = (TextView)findViewById(R.id.privacy_statement);
    }

    private void setListener() {
        mTVTerms.setOnClickListener(this);
        mTVPrivacy.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.terms_and_conditions:
                Intent intent = new Intent(SoftVersionActivity.this,SecretPolicyActivity.class);
                startActivity(intent);
                break;
            case R.id.privacy_statement:
                intent = new Intent(SoftVersionActivity.this,SecretPolicyActivity.class);
                startActivity(intent);
                break;
        }
    }
}
