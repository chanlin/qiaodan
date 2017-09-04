package com.jordan.project.activities.main;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.webkit.DownloadListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jordan.project.R;
import com.jordan.project.utils.LogUtils;

public class SecretPolicyActivity extends Activity {

    private WebView wv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_secret_policy);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.common_back_title);
        TextView mTvTitle = (TextView) findViewById(R.id.title_text);
        mTvTitle.setText(R.string.common_secret_policy);
        RelativeLayout mRLBack = (RelativeLayout) findViewById(R.id.title_back);
        mRLBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        setView();
    }
    private void setView() {
        wv = (WebView) findViewById(R.id.web_view);
        WebSettings webSettings = wv.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);
        //webSettings.setBuiltInZoomControls(true);
        wv.setBackgroundColor(0x00111111);
        wv.setBackgroundResource(R.color.register_bg);
        //第一次进入程序时，加载URL显示加载提示框
        //progressFirst = ProgressDialog.show(TrainDetailActivity.this, null, "请稍后,正在加载.....");

        //加载本地asset文件下H5
        wv.loadUrl("file:///android_asset/statement.html");
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

}
