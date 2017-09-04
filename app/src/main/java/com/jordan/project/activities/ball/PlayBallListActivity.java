package com.jordan.project.activities.ball;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.safari.httplibs.config.InnerMessageConfig;
import com.jordan.project.JordanApplication;
import com.jordan.project.R;
import com.jordan.project.activities.adapter.PlayBallListAdapter;
import com.jordan.project.entity.PlayBallListData;
import com.jordan.project.utils.JsonFalseUtils;
import com.jordan.project.utils.JsonSuccessUtils;
import com.jordan.project.utils.LogUtils;
import com.jordan.project.utils.ToastUtils;
import com.jordan.usersystemlibrary.UserManager;

import org.json.JSONException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class PlayBallListActivity extends Activity implements View.OnClickListener {
    private TextView mTVMore, mTVTitle;
    private Button mBtnCreatePlayBall;
    ArrayList<PlayBallListData> mPlayBallList = new ArrayList<PlayBallListData>();
    ListView mLvPlayBall;
    PlayBallListAdapter mPlayBallListAdapter;
    private String createListPageNo = "1";
    private String createListPageSize = "1000";
    private ImageView mIVTime;
    private TextView mTVTime;
    private StringBuffer stringBuilder;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
    private Handler mMainHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case InnerMessageConfig.USER_REACH_LIST_MESSAGE_SUCCESS:
                    //解析正确参数-内容在msg.obj
                    //保存登录返回数据
                    LogUtils.showLog("Result", "USER_CREATE_LIST_MESSAGE_SUCCESS result:" + (String) msg.obj);

                    //保存比赛信息
                    try {
                        mPlayBallList = JsonSuccessUtils.getCreateList((String) msg.obj);
                        LogUtils.showLog("Result", "mPlayBallList" + mPlayBallList.size());
                        JordanApplication.isCreatePlayBall = false;
                        //刷新list
                        mPlayBallListAdapter.updateList(mPlayBallList);

                    } catch (JSONException e) {
                        mMainHandler.sendEmptyMessage(InnerMessageConfig.USER_LOGIN_MESSAGE_EXCEPTION);
                        e.printStackTrace();
                    }
                    break;
                case InnerMessageConfig.USER_REACH_LIST_MESSAGE_EXCEPTION:
                    ToastUtils.shortToast(PlayBallListActivity.this, getResources().getString(R.string.http_exception));
                    break;
                case InnerMessageConfig.USER_REACH_LIST_MESSAGE_FALSE:
                    try {
                        String errorMsg = JsonFalseUtils.onlyErrorCodeResult((String) msg.obj);
                        if (errorMsg != null)
                            ToastUtils.shortToast(PlayBallListActivity.this, errorMsg);
                    } catch (JSONException e) {
                        mMainHandler.sendEmptyMessage(InnerMessageConfig.USER_CREATE_LIST_MESSAGE_EXCEPTION);
                        e.printStackTrace();
                    }
                    break;
                case 1:
                    ToastUtils.shortToast(PlayBallListActivity.this, getResources().getString(R.string.common_choies_time_up_today));
                    break;
            }
            ;
        }
    };
    private UserManager userManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_play_ball_list);
        //getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.common_text_more_title);
        userManager = new UserManager(PlayBallListActivity.this, mMainHandler);
        setView();
        setListener();
        initTime();
    }

    private void initTime() {
        LocationClient bdClient = JordanApplication.getBDClient();
        BDLocation location = bdClient.getLastKnownLocation();
        if (location == null) {
            Toast.makeText(PlayBallListActivity.this,
                    getResources().getString(R.string.common_gps_false), Toast.LENGTH_SHORT).show();
        } else {
            String beginTime = String.valueOf(new Date().getTime() / 1000);
            Date date = new Date();//取时间
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(date);
            calendar.add(calendar.DATE, 1);//把日期往后增加一天.整数往后推,负数往前移动
            date = calendar.getTime();   //这个时间就是日期往后推一天的结果
            String endTime = String.valueOf(date.getTime() / 1000);
            String end = sdf.format(date);
            LogUtils.showLog("time", "end" + end);
            try {
                //如果日期不等于今天
                String now = sdf.format(new Date());
                String begin = mTVTime.getText().toString();
                LogUtils.showLog("time", "begin" + begin);
                if (!now.equals(begin)) {
                    beginTime = String.valueOf(sdf.parse(mTVTime.getText().toString()).getTime() / 1000);
                    date = sdf.parse(mTVTime.getText().toString());//取时间
                    calendar = new GregorianCalendar();
                    calendar.setTime(date);
                    calendar.add(calendar.DATE, 1);//把日期往后增加一天.整数往后推,负数往前移动
                    date = calendar.getTime();   //这个时间就是日期往后推一天的结果
                    end = sdf.format(date);
                    LogUtils.showLog("time", "end" + end);
                    endTime = String.valueOf(sdf.parse(end).getTime() / 1000);
                } else {
                    endTime = String.valueOf(sdf.parse(end).getTime() / 1000);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
            LogUtils.showLog("time", "beginTime" + beginTime);
            LogUtils.showLog("time", "endTime" + endTime);
            doReachListTask(beginTime, endTime, "",
                    String.valueOf(location.getLongitude()), String.valueOf(location.getLatitude()),
                    location.getAddress().province, location.getAddress().city,
                    location.getAddress().district, "5", "distance");
        }
    }

//    private void doCreateListTask() {
//        userManager.createList(createListPageNo, createListPageSize);
//    }

    private void doReachListTask(String beginTime, String endTime, String type, String longitude,
                                 String latitude, String province, String city, String district,
                                 String limited, String sort) {
        //如果是今天是beginTime当前时间
        //如果是未来就是整天
        userManager.reachList(beginTime, endTime, type, longitude,
                latitude, "", "", "",
                limited, createListPageNo, createListPageSize, sort);
    }


    private void setView() {
        mTVTitle = (TextView) findViewById(R.id.register_title_text);
        mTVTitle.setText(getResources().getString(R.string.common_play_ball_circle));
        mTVMore = (TextView) findViewById(R.id.register_title_more);
        mTVMore.setText(getResources().getString(R.string.common_join_info));
        RelativeLayout mRLBack = (RelativeLayout) findViewById(R.id.title_back);
        mRLBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mBtnCreatePlayBall = (Button) findViewById(R.id.create_play_ball);

        mLvPlayBall = (ListView) findViewById(R.id.play_ball_list);

        //mPlayBallList= DatabaseService.findPlayBallList(JordanApplication.username);
//        mPlayBallList.add(new PlayBallListData());
//        mPlayBallList.add(new PlayBallListData());
//        mPlayBallList.add(new PlayBallListData());

        mPlayBallListAdapter = new PlayBallListAdapter(PlayBallListActivity.this, mPlayBallList);
        mLvPlayBall.setAdapter(mPlayBallListAdapter);
        mIVTime = (ImageView) findViewById(R.id.play_ball_time_iv);
        mTVTime = (TextView) findViewById(R.id.play_ball_time_text);
        mTVTime.setText(sdf.format(new Date()));
    }

    private void setListener() {
        mTVMore.setOnClickListener(this);
        mBtnCreatePlayBall.setOnClickListener(this);
        mIVTime.setOnClickListener(this);
        mLvPlayBall.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(PlayBallListActivity.this, PlayBallDetailActivity.class);
                intent.putExtra("id", mPlayBallList.get(position).getId());
                intent.putExtra("picture", mPlayBallList.get(position).getVipImg());
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register_title_more:
                Intent intent = new Intent(PlayBallListActivity.this, JoinPlayBallListActivity.class);
                startActivity(intent);
                break;
            case R.id.create_play_ball:
                intent = new Intent(PlayBallListActivity.this, CreatePlayBallActivity.class);
                startActivity(intent);
                break;
            case R.id.play_ball_time_iv:
                initTimeDialog();
                break;
        }
    }

    private void initTimeDialog() {
        // TODO Auto-generated method stub
        Calendar c = Calendar.getInstance();

        Dialog dateDialog = new DatePickerDialog(PlayBallListActivity.this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker arg0, final int arg1, final int arg2, int arg3) {
                // TODO Auto-generated method stub
                stringBuilder = new StringBuffer("");
                if(arg2<9){
                    stringBuilder.append(arg1 + "/0" + (arg2 + 1) + "/" + arg3);
                }else {
                    stringBuilder.append(arg1 + "/" + (arg2 + 1) + "/" + arg3);
                }
                //比较时间和现在的时间对比
                String startString = stringBuilder.toString().replace("/", "");
                long start = Long.valueOf(startString);
                Date nowDate = new Date();
                String nowString = sdf.format(new Date()).replace("/", "");
                long now = Long.valueOf(nowString);
                LogUtils.showLog("time", "start" + start);
                LogUtils.showLog("time", "now" + now);
                //否则直接填写当前时间-并提示用户
                if (start < now) {
                    mTVTime.setText(sdf.format(nowDate));
                    mMainHandler.sendEmptyMessage(1);
                } else {
                    mTVTime.setText(stringBuilder);
                }
                initTime();
            }


        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
        dateDialog.setTitle("请选择日期");
        dateDialog.show();
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (JordanApplication.isCreatePlayBall) {
            //doCreateListTask();
            initTime();
        }
    }
}
