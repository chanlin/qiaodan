package com.jordan.project.activities.ble;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jordan.commonlibrary.CommonManager;
import com.safari.httplibs.config.InnerMessageConfig;
import com.jordan.project.JordanApplication;
import com.jordan.project.R;
import com.jordan.project.activities.adapter.BluetoothListAdapter;
import com.jordan.project.content.BluetoothObserver;
import com.jordan.project.content.UserDataObserver;
import com.jordan.project.database.DatabaseObject;
import com.jordan.project.database.DatabaseProvider;
import com.jordan.project.database.DatabaseService;
import com.jordan.project.entity.BluetoothData;
import com.jordan.project.utils.JsonFalseUtils;
import com.jordan.project.utils.JsonSuccessUtils;
import com.jordan.project.utils.LogUtils;
import com.jordan.project.utils.SettingSharedPerferencesUtil;
import com.jordan.project.utils.ToastUtils;
import com.jordan.project.widget.LoadingProgressDialog;
import com.jordan.project.widget.UnBindDialog;
import com.jordan.project.widget.slidedatetimepicker.ChooesBallTypeDialog;

import org.json.JSONException;

import java.util.ArrayList;

public class BluetoothListActivity extends Activity {


    ArrayList<BluetoothData> mBluetoothList = new ArrayList<BluetoothData>();
    ListView mLvBluetooth;
    BluetoothListAdapter mBluetoothListAdapter;
    private String mIDS;
    private String mBluetoothName;

    private boolean mIsGrant;
    private BluetoothObserver mBluetoothObserver;

    private CommonManager commonManager;
    private static final int N0_PERMISSION = 21;
    private Handler mHandler = new Handler(){
        public void handleMessage(android.os.Message msg) {
            switch (msg.what){
                case N0_PERMISSION:
                    ToastUtils.shortToast(BluetoothListActivity.this, R.string.please_open_permission);
                    break;
                case InnerMessageConfig.COMMON_BLUETOOTH_LIST_MESSAGE_SUCCESS:
                    LogUtils.showLog("Result", "COMMON_BLUETOOTH_LIST_MESSAGE_SUCCESS result:" + (String) msg.obj);
                    //存储到数据库
                    JordanApplication.isUpdateBluetoothList=false;
                    try {
                        JsonSuccessUtils.getBluetoothList((String)msg.obj,BluetoothListActivity.this);
                        mBluetoothList= DatabaseService.findBluetoothList(JordanApplication.getUsername(BluetoothListActivity.this));
                        mBluetoothListAdapter.updateList(mBluetoothList,getChoiesBluetooth());
                    } catch (JSONException e) {
                        mHandler.sendEmptyMessage(InnerMessageConfig.COMMON_GET_CODE_MESSAGE_EXCEPTION);
                        e.printStackTrace();
                    }
                    break;
                case InnerMessageConfig.COMMON_BLUETOOTH_LIST_MESSAGE_EXCEPTION:
                    ToastUtils.shortToast(BluetoothListActivity.this, getResources().getString(R.string.http_exception));
                    break;
                case InnerMessageConfig.COMMON_BLUETOOTH_LIST_MESSAGE_FALSE:
                    try {
                        String errorMsg = JsonFalseUtils.onlyErrorCodeResult((String) msg.obj);
                        if(errorMsg!=null)
                            ToastUtils.shortToast(BluetoothListActivity.this, errorMsg);
                    } catch (JSONException e) {
                        mHandler.sendEmptyMessage(InnerMessageConfig.COMMON_GET_CODE_MESSAGE_EXCEPTION);
                        e.printStackTrace();
                    }

                    break;
                case UserDataObserver.DATABASE_UPDATE:
                    mBluetoothList= DatabaseService.findBluetoothList(JordanApplication.getUsername(BluetoothListActivity.this));
                    mBluetoothListAdapter.updateList(mBluetoothList,getChoiesBluetooth());
                    break;
                case InnerMessageConfig.COMMON_BLUETOOTH_UNBIND_MESSAGE_SUCCESS:
                    LoadingProgressDialog.Dissmiss();
                    //删除数据库 刷新List
                    DatabaseService.deleteBluetoothListByID(mIDS,JordanApplication.getUsername(BluetoothListActivity.this));
                    mBluetoothList= DatabaseService.findBluetoothList(JordanApplication.getUsername(BluetoothListActivity.this));
                    mBluetoothListAdapter.updateList(mBluetoothList,getChoiesBluetooth());
                    break;
                case InnerMessageConfig.COMMON_BLUETOOTH_UNBIND_MESSAGE_EXCEPTION:
                    LoadingProgressDialog.Dissmiss();
                    ToastUtils.shortToast(BluetoothListActivity.this, getResources().getString(R.string.http_exception));
                    break;
                case InnerMessageConfig.COMMON_BLUETOOTH_UNBIND_MESSAGE_FALSE:
                    try {
                        String errorMsg = JsonFalseUtils.onlyErrorCodeResult((String) msg.obj);
                        if(errorMsg!=null)
                            ToastUtils.shortToast(BluetoothListActivity.this, errorMsg);
                    } catch (JSONException e) {
                        mHandler.sendEmptyMessage(InnerMessageConfig.COMMON_BLUETOOTH_UNBIND_MESSAGE_EXCEPTION);
                        e.printStackTrace();
                    }
                    LoadingProgressDialog.Dissmiss();
                    break;
            }
        };
    };

    private int getChoiesBluetooth(){
        int choiesBluetooth = 0;
        boolean hasBluetooth = false;
        for(int i=0;i<mBluetoothList.size();i++){
            String sn = SettingSharedPerferencesUtil.GetChoiesBluetoothConfig(BluetoothListActivity.this,JordanApplication.getUsername(BluetoothListActivity.this));
            if(sn.contains("|")){
                sn = sn.substring(0,sn.indexOf("|"));
                if(sn.equals(mBluetoothList.get(i).getSn())){
                    choiesBluetooth=i;
                    hasBluetooth=true;
                }
            }
        }
        if(!hasBluetooth) {
            if(mBluetoothList.size()!=0) {
                String snAndMac = mBluetoothList.get(choiesBluetooth).getSn()
                        + "|" + mBluetoothList.get(choiesBluetooth).getMac();
                SettingSharedPerferencesUtil.SetChoiesBluetoothValue(BluetoothListActivity.this,
                        JordanApplication.getUsername(BluetoothListActivity.this), snAndMac);
            }else{
                SettingSharedPerferencesUtil.SetChoiesBluetoothValue(BluetoothListActivity.this,
                        JordanApplication.getUsername(BluetoothListActivity.this), "");
            }
        }
        LogUtils.showLog("getChoiesBluetooth","choiesBluetooth:"+choiesBluetooth);
        return choiesBluetooth;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_bluetooth_list);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.bluetooth_list_title);
        registerContentObservers();
        commonManager = new CommonManager(BluetoothListActivity.this, mHandler);
        TextView mTvTitle = (TextView) findViewById(R.id.title_text);
        mTvTitle.setText(getResources().getString(R.string.common_bluetooth_list));
        RelativeLayout mRLAdd = (RelativeLayout) findViewById(R.id.bluetooth_add);
        mRLAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    checkCameraPermission();
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
        mIsGrant = false;

    }
    private void registerContentObservers() {
        mBluetoothObserver=new BluetoothObserver(mHandler);
        Uri uri= Uri.parse("content://" + DatabaseProvider.AUTHOR + "/" + DatabaseObject.BLUETOOTH_LIST);
        getContentResolver().registerContentObserver(uri, true,
                mBluetoothObserver);
    }
    private void bluetoothList() {
        commonManager.bluetoothList();


    }


    private void checkCameraPermission(){
        int is_granted = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        if (PackageManager.PERMISSION_GRANTED != is_granted) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 1000);
        } else {
            Intent intent = new Intent(BluetoothListActivity.this, BindBluetoothActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (PackageManager.PERMISSION_GRANTED == grantResults[0]) {
            Intent intent = new Intent(BluetoothListActivity.this, BindBluetoothActivity.class);
            startActivity(intent);
        }else{
            mHandler.sendEmptyMessage(N0_PERMISSION);
        }
    }

    private void setView() {

        mLvBluetooth = (ListView)findViewById(R.id.bluetooth_list);
        mBluetoothList= DatabaseService.findBluetoothList(JordanApplication.getUsername(BluetoothListActivity.this));
        mBluetoothListAdapter = new BluetoothListAdapter(BluetoothListActivity.this,mBluetoothList,getChoiesBluetooth());
        mLvBluetooth.setAdapter(mBluetoothListAdapter);
        //bluetoothList();

    }

    private void setListener() {
        mLvBluetooth.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String snAndMac = mBluetoothList.get(position).getSn()+"|"+mBluetoothList.get(position).getMac();
                SettingSharedPerferencesUtil.SetChoiesBluetoothValue(BluetoothListActivity.this,JordanApplication.getUsername(BluetoothListActivity.this),snAndMac);
                mBluetoothListAdapter.updateList(mBluetoothList,getChoiesBluetooth());
            }
        });
        mLvBluetooth.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                mIDS=mBluetoothList.get(position).getId();
                mBluetoothName=mBluetoothList.get(position).getName();
                showChoiesBluetoothDialog();
//                showHeadDialogs();
                return true;
            }
        });
    }

    private void showHeadDialogs() {
        final Dialog dialog = new UnBindDialog(BluetoothListActivity.this,
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
                LoadingProgressDialog.show(BluetoothListActivity.this, false, true, 30000);
                commonManager.bluetoothUnBind(mIDS);
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

    @Override
    protected void onResume() {
        super.onResume();
        if(JordanApplication.isUpdateBluetoothList){
            bluetoothList();
        }
    }
    private void showChoiesBluetoothDialog() {
        final Dialog dialog = new ChooesBallTypeDialog(BluetoothListActivity.this,
                R.style.chooes_dialog_style);
        dialog.show();
        Window window = dialog.getWindow();
//        WindowManager.LayoutParams lp = window.getAttributes();
//        lp.alpha = 0.8f;
//        window.setAttributes(lp);
        RelativeLayout rlEdit = (RelativeLayout) window.findViewById(R.id.rl_chooes_dialog_all);
        TextView tvEdit = (TextView) window.findViewById(R.id.tv_chooes_dialog_all);
        tvEdit.setText("编辑");
        rlEdit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent =new Intent(BluetoothListActivity.this,InputBluetoothNameActivity.class);
                intent.putExtra("id",mIDS);
                intent.putExtra("name",mBluetoothName);
                startActivity(intent);
                dialog.dismiss();
            }
        });
        RelativeLayout rlDelete = (RelativeLayout) window.findViewById(R.id.rl_chooes_dialog_half);
        TextView tvDelete = (TextView) window.findViewById(R.id.tv_chooes_dialog_half);
        tvDelete.setText("删除");
        rlDelete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                LoadingProgressDialog.show(BluetoothListActivity.this, false, true, 30000);
                commonManager.bluetoothUnBind(mIDS);
                dialog.dismiss();
            }
        });
        RelativeLayout rlCancel = (RelativeLayout) window.findViewById(R.id.rl_chooes_dialog_cancel);
        rlCancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });

    }
}
