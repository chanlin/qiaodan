package com.jordan.project.activities.main;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jordan.project.JordanApplication;
import com.jordan.project.R;
import com.jordan.project.activities.adapter.MyShoesListAdapter;
import com.jordan.project.entity.MyShoesData;
import com.jordan.project.utils.JsonFalseUtils;
import com.jordan.project.utils.JsonSuccessUtils;
import com.jordan.project.utils.LogUtils;
import com.jordan.project.utils.ToastUtils;
import com.jordan.project.widget.LoadingProgressDialog;
import com.jordan.project.widget.UnBindDialog;
import com.jordan.usersystemlibrary.UserManager;
import com.safari.httplibs.config.InnerMessageConfig;

import org.json.JSONException;

import java.util.ArrayList;

public class MyShoesActivity extends Activity {
    private ListView mLvMyShoesList;
    private MyShoesListAdapter mMyShoesListAdapter;
    private ArrayList<MyShoesData> list = new ArrayList<MyShoesData>();
    private ArrayList<MyShoesData> boxList = new ArrayList<MyShoesData>();
    private ArrayList<MyShoesData> recoList = new ArrayList<MyShoesData>();
    private String mIDS;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case InnerMessageConfig.USER_SHOES_BOX_MESSAGE_SUCCESS:
                    LogUtils.showLog("Result", "USER_SHOES_BOX_MESSAGE_SUCCESS result:" + (String) msg.obj);
                    //解析我的球鞋
                    try {
                        JordanApplication.isBindShoes=false;
                        boxList = new ArrayList<MyShoesData>();
                        boxList=JsonSuccessUtils.getBoxShoesList((String) msg.obj);
                        list = new ArrayList<MyShoesData>();
                        list.addAll(boxList);
                        list.addAll(recoList);
                        mMyShoesListAdapter.updateList(list);
                    } catch (JSONException e) {
                        mHandler.sendEmptyMessage(InnerMessageConfig.USER_SHOES_BOX_MESSAGE_EXCEPTION);
                        e.printStackTrace();
                    }
                    break;
                case InnerMessageConfig.USER_SHOES_BOX_MESSAGE_EXCEPTION:
                    ToastUtils.shortToast(MyShoesActivity.this,getResources().getString(R.string.http_exception));
                    break;
                case InnerMessageConfig.USER_SHOES_BOX_MESSAGE_FALSE:
                    try {
                        String errorMsg= JsonFalseUtils.onlyErrorCodeResult((String)msg.obj);
                        if(errorMsg!=null)
                            ToastUtils.shortToast(MyShoesActivity.this,errorMsg);
                    } catch (JSONException e) {
                        mHandler.sendEmptyMessage(InnerMessageConfig.USER_SHOES_BOX_MESSAGE_EXCEPTION);
                        e.printStackTrace();
                    }
                    break;
                case InnerMessageConfig.USER_SHOES_RECO_MESSAGE_SUCCESS:
                    LogUtils.showLog("Result", "USER_SHOES_RECO_MESSAGE_SUCCESS result:" + (String) msg.obj);
                    //解析推荐球鞋
                    try {
                        recoList = new ArrayList<MyShoesData>();
                        recoList=JsonSuccessUtils.getRecoShoesList((String) msg.obj);
                        list = new ArrayList<MyShoesData>();
                        list.addAll(boxList);
                        list.addAll(recoList);
                        mMyShoesListAdapter.updateList(list);
                    } catch (JSONException e) {
                        mHandler.sendEmptyMessage(InnerMessageConfig.USER_SHOES_RECO_MESSAGE_EXCEPTION);
                        e.printStackTrace();
                    }
                    break;
                case InnerMessageConfig.USER_SHOES_RECO_MESSAGE_EXCEPTION:
                    ToastUtils.shortToast(MyShoesActivity.this,getResources().getString(R.string.http_exception));
                    break;
                case InnerMessageConfig.USER_SHOES_RECO_MESSAGE_FALSE:
                    try {
                        String errorMsg= JsonFalseUtils.onlyErrorCodeResult((String)msg.obj);
                        if(errorMsg!=null)
                            ToastUtils.shortToast(MyShoesActivity.this,errorMsg);
                    } catch (JSONException e) {
                        mHandler.sendEmptyMessage(InnerMessageConfig.USER_SHOES_RECO_MESSAGE_EXCEPTION);
                        e.printStackTrace();
                    }
                    break;
                case InnerMessageConfig.USER_SHOES_UNBIND_MESSAGE_SUCCESS:
                    LoadingProgressDialog.Dissmiss();
                    shoesBox();
                    shoesReco();
                    break;
                case InnerMessageConfig.USER_SHOES_UNBIND_MESSAGE_EXCEPTION:
                    LoadingProgressDialog.Dissmiss();
                    ToastUtils.shortToast(MyShoesActivity.this, getResources().getString(R.string.http_exception));
                    break;
                case InnerMessageConfig.USER_SHOES_UNBIND_MESSAGE_FALSE:
                    try {
                        String errorMsg = JsonFalseUtils.onlyErrorCodeResult((String) msg.obj);
                        if(errorMsg!=null)
                            ToastUtils.shortToast(MyShoesActivity.this, errorMsg);
                    } catch (JSONException e) {
                        mHandler.sendEmptyMessage(InnerMessageConfig.USER_SHOES_UNBIND_MESSAGE_EXCEPTION);
                        e.printStackTrace();
                    }
                    LoadingProgressDialog.Dissmiss();
                    break;
            }
        }
    };
    private UserManager userManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_my_shoes);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.bluetooth_list_title);
        userManager=new UserManager(MyShoesActivity.this,mHandler);
        TextView mTvTitle = (TextView) findViewById(R.id.title_text);
        mTvTitle.setText(getResources().getString(R.string.my_shoe));
        RelativeLayout mRLAdd = (RelativeLayout) findViewById(R.id.bluetooth_add);
        mRLAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyShoesActivity.this,BindShoesActivity.class);
                startActivity(intent);
            }
        });
        RelativeLayout mRLDelete = (RelativeLayout) findViewById(R.id.bluetooth_delete);
        mRLDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        mRLDelete.setVisibility(View.GONE);
        RelativeLayout mRLBack=(RelativeLayout)findViewById(R.id.title_back);
        mRLBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        setView();
        setListener();
        shoesBox();
        shoesReco();
    }
    private void shoesBox(){
        userManager.shoesBox();
    }
    private void shoesReco(){
        userManager.shoesReco();
    }

    private void setView() {
        mLvMyShoesList=(ListView)findViewById(R.id.my_shoes_list);
        mMyShoesListAdapter=new MyShoesListAdapter(MyShoesActivity.this,list);
        mLvMyShoesList.setAdapter(mMyShoesListAdapter);
        mLvMyShoesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
        mLvMyShoesList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if(list.get(position).getType()==1) {
                    mIDS = list.get(position).getId();
                    showHeadDialogs();
                }
                return true;
            }
        });
    }
    private void showHeadDialogs() {
        final Dialog dialog = new UnBindDialog(MyShoesActivity.this,
                R.style.chooes_dialog_style);
        dialog.show();
        Window window = dialog.getWindow();
//        WindowManager.LayoutParams lp = window.getAttributes();
//        lp.alpha = 0.8f;
//        window.setAttributes(lp);
        Button btnSubmit = (Button) window.findViewById(R.id.register_data_chooes_submit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                LoadingProgressDialog.show(MyShoesActivity.this, false, true, 30000);
                userManager.shoesUnBind(mIDS);
                dialog.dismiss();
            }
        });
        Button btnCancel = (Button) window.findViewById(R.id.register_data_chooes_cancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }
    private void setListener() {
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(JordanApplication.isBindShoes=true){
            shoesBox();
        }
    }
}
