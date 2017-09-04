package com.jordan.project.activities.ball;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
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
import com.jordan.project.JordanApplication;
import com.jordan.project.R;
import com.jordan.project.activities.adapter.PlayBallListAdapter;
import com.jordan.project.content.PlayBallObserver;
import com.jordan.project.database.DatabaseObject;
import com.jordan.project.database.DatabaseProvider;
import com.jordan.project.database.DatabaseService;
import com.jordan.project.entity.PlayBallListData;
import com.jordan.project.utils.BaiduLocationUtils;
import com.jordan.project.utils.JsonFalseUtils;
import com.jordan.project.utils.JsonSuccessUtils;
import com.jordan.project.utils.LogUtils;
import com.jordan.project.utils.ToastUtils;
import com.jordan.usersystemlibrary.UserManager;

import org.json.JSONException;

import java.util.ArrayList;

public class JoinPlayBallListActivity extends Activity {

    ArrayList<PlayBallListData> mPlayBallList = new ArrayList<PlayBallListData>();
    ListView mLvPlayBall;
    PlayBallListAdapter mPlayBallListAdapter;
    private String createListPageNo = "1";
    private String createListPageSize = "1000";
    private Handler mMainHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case InnerMessageConfig.USER_JOIN_LIST_MESSAGE_SUCCESS:
                    //解析正确参数-内容在msg.obj
                    //保存登录返回数据
                    LogUtils.showLog("Result", "USER_JOIN_LIST_MESSAGE_SUCCESS result:" + (String) msg.obj);

                    //保存比赛信息
                    try {
                        mPlayBallList = JsonSuccessUtils.getJoinList((String) msg.obj);
                        //刷新list
                        //mPlayBallListAdapter.updateList(mPlayBallList);
                        if(mPlayBallList.size()>0){
                            DatabaseService.deleteReachDetailByUsername(JordanApplication.getUsername(JoinPlayBallListActivity.this));
                        }
                        //存储数据库
                        for (int i = 0; i < mPlayBallList.size(); i++) {
                            //先删除约球列表
                            //DatabaseService.deleteReachDetailInfo();
                            //保存我参与
                            PlayBallListData playBallListData = mPlayBallList.get(i);
                            DatabaseService.createReachDetailInfo(playBallListData.getId(), playBallListData.getCourtId(),
                                    playBallListData.getVipId(), playBallListData.getTime(),
                                    playBallListData.getDuration(), playBallListData.getPeople(), playBallListData.getType(),
                                    playBallListData.getPicture(), playBallListData.getLongitude(),
                                    playBallListData.getLatitude(), playBallListData.getProvince(), playBallListData.getCity(),
                                    playBallListData.getDistrict(), playBallListData.getStreet(),
                                    playBallListData.getAddress(), playBallListData.getMobile(), playBallListData.getContact(),
                                    playBallListData.getJoin(), playBallListData.getDistance(), playBallListData.getRemarks(),
                                    JordanApplication.getUsername(JoinPlayBallListActivity.this), playBallListData.getVipImg(),playBallListData.getSlogan());
                        }

                    } catch (JSONException e) {
                        mMainHandler.sendEmptyMessage(InnerMessageConfig.USER_LOGIN_MESSAGE_EXCEPTION);
                        e.printStackTrace();
                    }
                    break;
                case InnerMessageConfig.USER_JOIN_LIST_MESSAGE_FALSE:
                    ToastUtils.shortToast(JoinPlayBallListActivity.this, getResources().getString(R.string.http_exception));
                    break;
                case InnerMessageConfig.USER_JOIN_LIST_MESSAGE_EXCEPTION:
                    try {
                        String errorMsg = JsonFalseUtils.onlyErrorCodeResult((String) msg.obj);
                        if (errorMsg != null)
                            ToastUtils.shortToast(JoinPlayBallListActivity.this, errorMsg);
                    } catch (JSONException e) {
                        mMainHandler.sendEmptyMessage(InnerMessageConfig.USER_JOIN_LIST_MESSAGE_EXCEPTION);
                        e.printStackTrace();
                    }
                    break;
                case PlayBallObserver.DATABASE_UPDATE:
                    mPlayBallList = DatabaseService.findPlayBallList(JordanApplication.getUsername(JoinPlayBallListActivity.this));
                    mPlayBallListAdapter.updateList(mPlayBallList);

                    break;
            }
            ;
        }
    };
    private PlayBallObserver mPlayBallObserver;
    private UserManager userManager;

    private void registerContentObservers() {
        mPlayBallObserver = new PlayBallObserver(mMainHandler);
        Uri uri = Uri.parse("content://" + DatabaseProvider.AUTHOR + "/" + DatabaseObject.REACH_DETAIL);
        getContentResolver().registerContentObserver(uri, true,
                mPlayBallObserver);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_join_play_ball_list);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.common_back_title);
        userManager = new UserManager(JoinPlayBallListActivity.this, mMainHandler);
        BaiduLocationUtils.initLocation(JoinPlayBallListActivity.this);
        registerContentObservers();
        setView();
        doJoinListTask();
    }

    private void doJoinListTask() {
        userManager.joinList(createListPageNo, createListPageSize);
    }


    private void setView() {
        TextView mTVTitle = (TextView) findViewById(R.id.title_text);
        mTVTitle.setText(getResources().getString(R.string.common_join_info));
        RelativeLayout mRLBack = (RelativeLayout) findViewById(R.id.title_back);
        mRLBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mLvPlayBall = (ListView) findViewById(R.id.join_play_ball_list);
        mPlayBallList = DatabaseService.findPlayBallList(JordanApplication.getUsername(JoinPlayBallListActivity.this));
//        mPlayBallList.add(new PlayBallListData());
//        mPlayBallList.add(new PlayBallListData());
//        mPlayBallList.add(new PlayBallListData());

        mPlayBallListAdapter = new PlayBallListAdapter(JoinPlayBallListActivity.this, mPlayBallList);
        mLvPlayBall.setAdapter(mPlayBallListAdapter);
        mLvPlayBall.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(JoinPlayBallListActivity.this, PlayBallDetailActivity.class);
                intent.putExtra("id", mPlayBallList.get(position).getId());
                intent.putExtra("picture", mPlayBallList.get(position).getVipImg());
                startActivity(intent);
            }
        });
    }
}
