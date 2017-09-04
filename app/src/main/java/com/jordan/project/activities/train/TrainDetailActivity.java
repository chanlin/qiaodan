package com.jordan.project.activities.train;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.webkit.DownloadListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.safari.httplibs.config.InnerMessageConfig;
import com.jordan.project.R;
import com.jordan.project.utils.JsonFalseUtils;
import com.jordan.project.utils.LogUtils;
import com.jordan.project.utils.ToastUtils;
import com.jordan.usersystemlibrary.UserManager;

import org.json.JSONException;

public class TrainDetailActivity extends Activity {

    private String id, link, title;
    private WebView wv;
    private UserManager userManager;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case InnerMessageConfig.USER_TRAIN_DETAIL_MESSAGE_SUCCESS:
                    //解析
                    LogUtils.showLog("Result", "USER_TRAIN_DETAIL_MESSAGE_SUCCESS result:" + (String) msg.obj);
                    try {

                    } catch (Exception e) {
                        mHandler.sendEmptyMessage(InnerMessageConfig.USER_TRAIN_DETAIL_MESSAGE_EXCEPTION);
                        e.printStackTrace();
                    }
                    break;
                case InnerMessageConfig.USER_TRAIN_DETAIL_MESSAGE_EXCEPTION:
                    ToastUtils.shortToast(TrainDetailActivity.this, getResources().getString(R.string.http_exception));
                    break;
                case InnerMessageConfig.USER_TRAIN_DETAIL_MESSAGE_FALSE:
                    try {
                        String errorMsg = JsonFalseUtils.onlyErrorCodeResult((String) msg.obj);
                        if (errorMsg != null)
                            ToastUtils.shortToast(TrainDetailActivity.this, errorMsg);
                    } catch (JSONException e) {
                        mHandler.sendEmptyMessage(InnerMessageConfig.USER_TRAIN_DETAIL_MESSAGE_EXCEPTION);
                        e.printStackTrace();
                    }
                    break;
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_train_detail);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.common_back_title);
        userManager = new UserManager(TrainDetailActivity.this, mHandler);
        id = getIntent().getStringExtra("id");
        link = getIntent().getStringExtra("link");
        title = getIntent().getStringExtra("title");
        TextView mTvTitle = (TextView) findViewById(R.id.title_text);
        mTvTitle.setText(title);
        RelativeLayout mRLBack = (RelativeLayout) findViewById(R.id.title_back);
        mRLBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        setView();
        //webview打开link

        //trainDetail();

    }

    private void trainDetail() {
        userManager.trainDetail(id);
    }

    ProgressDialog progressFirst;

    private void setView() {
        wv = (WebView) findViewById(R.id.web_view);
        WebSettings webSettings = wv.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowFileAccess(true);
        //webSettings.setBuiltInZoomControls(true);
        wv.setBackgroundColor(0x00111111);
        wv.setBackgroundResource(R.color.register_bg);
        //第一次进入程序时，加载URL显示加载提示框
        //progressFirst = ProgressDialog.show(TrainDetailActivity.this, null, "请稍后,正在加载.....");

        wv.loadUrl(link);
        wv.setWebViewClient(new webViewClient());
        wv.setDownloadListener(new MyWebViewDownLoadListener());
    }

    private class MyWebViewDownLoadListener implements DownloadListener {

        @Override
        public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype,
                                    long contentLength) {
            LogUtils.showLog("Result", "onDownloadStart url :" + url);
            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }

    }

    private class webViewClient extends WebViewClient {
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
//            if (progressFirst.isShowing()) {//加载URL完成后，进度加载提示框消失
//                progressFirst.dismiss();
//            }
            return true;
        }
    }

    @Override
    protected void onResume() {
        if (wv != null) {
            wv.onResume();
        }
        super.onResume();
    }

    @Override
    protected void onPause() {
        if (wv != null) {
            wv.onPause();
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        if (wv != null) {
            //wv.getSettings().setBuiltInZoomControls(true);
            wv.setVisibility(View.GONE);
            wv.destroy();
            wv = null;
        }
        super.onDestroy();
    }

    public void onPageBack() {
        finish();
    }

    ;
}
