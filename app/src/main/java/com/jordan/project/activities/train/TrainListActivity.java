package com.jordan.project.activities.train;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.safari.httplibs.config.InnerMessageConfig;
import com.jordan.project.R;
import com.jordan.project.activities.adapter.TrainListAdapter;
import com.jordan.project.data.TrainListData;
import com.jordan.project.utils.JsonFalseUtils;
import com.jordan.project.utils.JsonSuccessUtils;
import com.jordan.project.utils.LogUtils;
import com.jordan.project.utils.ToastUtils;
import com.jordan.usersystemlibrary.UserManager;

import org.json.JSONException;

import java.util.ArrayList;

public class TrainListActivity extends Activity {
    private ListView mLvTrainList;
    private TrainListAdapter mTrainListAdapter;
    private ArrayList<TrainListData> list = new ArrayList<TrainListData>();
    private static final String PAGE_NO = "1";
    private static final String PAGE_SIZE ="1000";
    private String id;
    private UserManager userManager;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case InnerMessageConfig.USER_TRAIN_LIST_MESSAGE_SUCCESS:
                    //解析
                    LogUtils.showLog("Result", "USER_TRAIN_LIST_MESSAGE_SUCCESS result:"+(String)msg.obj);
                    try {
                        list = JsonSuccessUtils.getTrainListData((String)msg.obj);
                        mTrainListAdapter.updateList(list);
                    }catch (Exception e){
                        mHandler.sendEmptyMessage(InnerMessageConfig.USER_TRAIN_LIST_MESSAGE_EXCEPTION);
                        e.printStackTrace();
                    }
                    break;
                case InnerMessageConfig.USER_TRAIN_LIST_MESSAGE_EXCEPTION:
                    ToastUtils.shortToast(TrainListActivity.this, getResources().getString(R.string.http_exception));
                    break;
                case InnerMessageConfig.USER_TRAIN_LIST_MESSAGE_FALSE:
                    try {
                        String errorMsg = JsonFalseUtils.onlyErrorCodeResult((String) msg.obj);
                        if (errorMsg != null)
                            ToastUtils.shortToast(TrainListActivity.this, errorMsg);
                    } catch (JSONException e) {
                        mHandler.sendEmptyMessage(InnerMessageConfig.USER_TRAIN_LIST_MESSAGE_EXCEPTION);
                        e.printStackTrace();
                    }
                    break;
                case InnerMessageConfig.USER_TRAIN_COUNT_MESSAGE_SUCCESS:
                    trainList();
                    break;
            }

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_train_list);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.common_back_title);
        userManager = new UserManager(TrainListActivity.this, mHandler);
        id=getIntent().getStringExtra("id");
        TextView mTvTitle = (TextView) findViewById(R.id.title_text);
        mTvTitle.setText(getResources().getString(R.string.common_train_list));
        RelativeLayout mRLBack=(RelativeLayout)findViewById(R.id.title_back);
        mRLBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        setView();
        trainList();

    }

    private void setView() {
        mLvTrainList=(ListView)findViewById(R.id.train_list);
        mTrainListAdapter=new TrainListAdapter(TrainListActivity.this,list);
        mLvTrainList.setAdapter(mTrainListAdapter);
        mLvTrainList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(TrainListActivity.this,TrainDetailActivity.class);
                intent.putExtra("id",list.get(position).getId());
                intent.putExtra("link",list.get(position).getLink());
                intent.putExtra("title",list.get(position).getTitle());
                trainCount(list.get(position).getId());
                startActivity(intent);
            }
        });
    }
    private void trainList() {
        userManager.trainList(id,PAGE_NO,PAGE_SIZE);
    }
    private void trainCount(String id) {
        userManager.trainCount(id);
    }
}
