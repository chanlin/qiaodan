package com.jordan.project.activities.motion;

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
import com.jordan.project.JordanApplication;
import com.jordan.project.R;
import com.jordan.project.activities.adapter.MotionListAdapter;
import com.jordan.project.database.DatabaseService;
import com.jordan.project.entity.MoveListData;
import com.jordan.project.utils.JsonFalseUtils;
import com.jordan.project.utils.JsonSuccessUtils;
import com.jordan.project.utils.LogUtils;
import com.jordan.project.utils.ToastUtils;
import com.jordan.project.widget.LoadingProgressDialog;
import com.jordan.usersystemlibrary.UserManager;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class MotionListActivity extends Activity implements View.OnClickListener {

    private RelativeLayout mRLTimeNear, mRlTimeNearWeek, mRlTimeNearMonth, mRlTimeNearAll;
    private TextView mTVTimeNear, mTVTimeNearWeek, mTVTimeNearMonth, mTVTimeNearAll;
    int currentPageIndex = 0;
    ArrayList<MoveListData> mMotionListNear = new ArrayList<MoveListData>();
    ArrayList<MoveListData> mMotionListWeek = new ArrayList<MoveListData>();
    ArrayList<MoveListData> mMotionListMonth = new ArrayList<MoveListData>();
    ArrayList<MoveListData> mMotionListAll = new ArrayList<MoveListData>();
    private boolean isUpdateNear = false;
    private boolean isUpdateWeek = false;
    private boolean isUpdateMonth = false;
    private boolean isUpdateAll = false;
    ListView mLvMotionList;
    MotionListAdapter mMotionListAdapter;
    private String motionListPageNo = "1";
    private String motionListPageSize = "1000";
    private int choiesItem = 0;
    private int choiesPager = 0;


    private UserManager userManager;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case InnerMessageConfig.USER_MOVE_LIST_MESSAGE_SUCCESS:
                    try {
                        //解析正确参数-内容在msg.obj
                        LogUtils.showLog("Result", "USER_MOVE_LIST_MESSAGE_SUCCESS result:" + (String) msg.obj);
                        //对比currentPageIndex 解析list存放
                        //分别赋值到指定的list
                        String data = (String) msg.obj;
                        if (data != null && !data.equals("")) {
                            switch (currentPageIndex) {
                                case 0:
                                    isUpdateNear=true;
                                    mMotionListNear = JsonSuccessUtils.getMoveList((String) msg.obj);
                                    mMotionListAdapter.updateList(mMotionListNear);
                                    saveListInDataBase(mMotionListNear,0);
                                    break;
                                case 1:
                                    isUpdateWeek=true;
                                    mMotionListWeek = JsonSuccessUtils.getMoveList((String) msg.obj);
                                    mMotionListAdapter.updateList(mMotionListWeek);
                                    saveListInDataBase(mMotionListWeek,1);
                                    break;
                                case 2:
                                    isUpdateMonth=true;
                                    mMotionListMonth = JsonSuccessUtils.getMoveList((String) msg.obj);
                                    mMotionListAdapter.updateList(mMotionListMonth);
                                    saveListInDataBase(mMotionListMonth,2);
                                    break;
                                case 3:
                                    isUpdateAll=true;
                                    mMotionListAll = JsonSuccessUtils.getMoveList((String) msg.obj);
                                    mMotionListAdapter.updateList(mMotionListAll);
                                    saveListInDataBase(mMotionListAll,3);
                                    break;

                                default:
                                    break;
                            }
                        }
                            //update list
                        } catch(JSONException e){
                            ToastUtils.shortToast(MotionListActivity.this, getResources().getString(R.string.http_exception));
                            e.printStackTrace();
                        }
                    LoadingProgressDialog.Dissmiss();
                    break;
                case InnerMessageConfig.USER_MOVE_LIST_MESSAGE_EXCEPTION:
                    ToastUtils.shortToast(MotionListActivity.this, getResources().getString(R.string.http_exception));
                    LoadingProgressDialog.Dissmiss();
                    break;
                case InnerMessageConfig.USER_MOVE_LIST_MESSAGE_FALSE:
                    try {
                        String errorMsg = JsonFalseUtils.onlyErrorCodeResult((String) msg.obj);
                        if (errorMsg != null) {
                            ToastUtils.shortToast(MotionListActivity.this, errorMsg);
                            //失败还是update list
                            switch (currentPageIndex) {
                                case 0:
                                    mMotionListAdapter.updateList(mMotionListNear);
                                    break;
                                case 1:
                                    mMotionListAdapter.updateList(mMotionListWeek);
                                    break;
                                case 2:
                                    mMotionListAdapter.updateList(mMotionListMonth);
                                    break;
                                case 3:
                                    mMotionListAdapter.updateList(mMotionListAll);
                                    break;

                                default:
                                    break;
                            }

                        }
                        LoadingProgressDialog.Dissmiss();
                    } catch (JSONException e) {
                        mHandler.sendEmptyMessage(InnerMessageConfig.USER_MOTION_DETAIL_MESSAGE_EXCEPTION);
                        e.printStackTrace();
                    }
                    break;
            }
        }
    };
    private void saveListInDataBase(ArrayList<MoveListData> list,int type){
        if(list.size()>0){
            DatabaseService.deleteMotionListByUsernameAndType(JordanApplication.getUsername(MotionListActivity.this),String.valueOf(type));
        }
         for(int i=0;i<list.size();i++){
             MoveListData moveListData = list.get(i);
             DatabaseService.createMotionList(moveListData.getId(), JordanApplication.getUsername(MotionListActivity.this),
                     moveListData.getTimeYear(), moveListData.getTimeHour(),
                     String.valueOf(type),moveListData.getTotalTime(),moveListData.getTotalDist());
         }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_motion_list);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.common_back_title);
        userManager = new UserManager(MotionListActivity.this, mHandler);

        TextView mTvTitle = (TextView) findViewById(R.id.title_text);
        mTvTitle.setText(getResources().getString(R.string.common_data));
        RelativeLayout mRLBack = (RelativeLayout) findViewById(R.id.title_back);
        mRLBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        setView();
        setListener();
        //获取最近的
        Date date = new Date();//取时间
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.DATE, -3);//把日期往后增加一天.整数往后推,负数往前移动
        doDetail(String.valueOf(calendar.getTime().getTime() / 1000), String.valueOf(date.getTime() / 1000));
    }

    private void doDetail(String beginTime, String endTime) {
        LoadingProgressDialog.show(MotionListActivity.this, false, true, 30000);
        userManager.moveList(beginTime, endTime, motionListPageNo, motionListPageSize);
    }

    private void setListener() {
        mRLTimeNear.setOnClickListener(this);
        mRlTimeNearWeek.setOnClickListener(this);
        mRlTimeNearMonth.setOnClickListener(this);
        mRlTimeNearAll.setOnClickListener(this);
    }

    private void setView() {

        mLvMotionList = (ListView) findViewById(R.id.motion_list);

        mMotionListNear = DatabaseService.findMotionList(JordanApplication.getUsername(MotionListActivity.this),String.valueOf(currentPageIndex));

        mMotionListAdapter = new MotionListAdapter(MotionListActivity.this, mMotionListNear);
        mLvMotionList.setAdapter(mMotionListAdapter);

        mRLTimeNear = (RelativeLayout) findViewById(R.id.common_time_near_rl);
        mRlTimeNearWeek = (RelativeLayout) findViewById(R.id.common_time_near_week_rl);
        mRlTimeNearMonth = (RelativeLayout) findViewById(R.id.common_time_near_month_rl);
        mRlTimeNearAll = (RelativeLayout) findViewById(R.id.common_time_all_rl);
        mTVTimeNear = (TextView) findViewById(R.id.common_time_near_tv);
        mTVTimeNearWeek = (TextView) findViewById(R.id.common_time_near_week_tv);
        mTVTimeNearMonth = (TextView) findViewById(R.id.common_time_near_month_tv);
        mTVTimeNearAll = (TextView) findViewById(R.id.common_time_all_tv);
        mLvMotionList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                choiesItem = position;
                choiesPager = currentPageIndex;
                mMotionListAdapter.updatePosition(position);
                Intent intent = new Intent(MotionListActivity.this, MotionDetailActivity.class);
                intent.putExtra("id", mMotionListAdapter.getList().get(position).getId());
                intent.putExtra("time", mMotionListAdapter.getList().get(position).getTimeYear()+"  "+mMotionListAdapter.getList().get(position).getTimeHour());
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.common_time_near_rl:
                //先去数据库
                currentPageIndex = 0;
                mMotionListNear = DatabaseService.findMotionList(JordanApplication.getUsername(MotionListActivity.this),String.valueOf(currentPageIndex));
                LogUtils.showLog("MotionList", "mMotionListNear:" + mMotionListNear.size());
                LogUtils.showLog("MotionList", "isUpdateNear:" + isUpdateNear);
                updateBtnColor();
                if (mMotionListNear.size() == 0||!isUpdateNear) {
                    //获取最近的
                    Date date = new Date();//取时间
                    Calendar calendar = new GregorianCalendar();
                    calendar.setTime(date);
                    calendar.add(calendar.DATE, -3);//把日期往后增加一天.整数往后推,负数往前移动
                    doDetail(String.valueOf(calendar.getTime().getTime() / 1000), String.valueOf(date.getTime() / 1000));
                }
                if (mMotionListNear.size() != 0){
                    if(choiesPager!=0) {
                        mMotionListAdapter.updateList(mMotionListNear);
                    }else{
                        mMotionListAdapter.updateList(mMotionListNear,choiesItem);
                    }
                }
                break;
            case R.id.common_time_near_week_rl:
                //先去数据库
                currentPageIndex = 1;
                mMotionListWeek = DatabaseService.findMotionList(JordanApplication.getUsername(MotionListActivity.this),String.valueOf(currentPageIndex));
                LogUtils.showLog("MotionList", "mMotionListWeek:" + mMotionListWeek.size());
                LogUtils.showLog("MotionList", "isUpdateWeek:" + isUpdateWeek);

                updateBtnColor();
                if (mMotionListWeek.size() == 0||!isUpdateWeek) {
                    //获取最新的list
                    Date date = new Date();//取时间
                    Calendar calendar = new GregorianCalendar();
                    calendar.setTime(date);
                    calendar.add(calendar.DATE, -7);//把日期往后增加一天.整数往后推,负数往前移动
                    doDetail(String.valueOf(calendar.getTime().getTime() / 1000), String.valueOf(date.getTime() / 1000));
                }
                if (mMotionListWeek.size() != 0){
                    if(choiesPager!=1) {
                        mMotionListAdapter.updateList(mMotionListWeek);
                    }else{
                        mMotionListAdapter.updateList(mMotionListWeek,choiesItem);
                    }
                }

                break;
            case R.id.common_time_near_month_rl:
                //先去数据库
                currentPageIndex = 2;
                mMotionListMonth = DatabaseService.findMotionList(JordanApplication.getUsername(MotionListActivity.this),String.valueOf(currentPageIndex));
                LogUtils.showLog("MotionList", "mMotionListMonth:" + mMotionListMonth.size());
                LogUtils.showLog("MotionList", "isUpdateMonth:" + isUpdateMonth);
                updateBtnColor();
                if (mMotionListMonth.size() == 0||!isUpdateMonth) {
                    //获取最新的list
                    Date date = new Date();//取时间
                    Calendar calendar = new GregorianCalendar();
                    calendar.setTime(date);
                    calendar.add(calendar.MONTH, -1);//把日期往后增加一天.整数往后推,负数往前移动
                    doDetail(String.valueOf(calendar.getTime().getTime() / 1000), String.valueOf(date.getTime() / 1000));
                }
                if (mMotionListMonth.size() != 0){
                    if(choiesPager!=2) {
                        mMotionListAdapter.updateList(mMotionListMonth);
                    }else{
                        mMotionListAdapter.updateList(mMotionListMonth,choiesItem);
                    }
                }
                break;
            case R.id.common_time_all_rl:
                //先去数据库
                currentPageIndex = 3;
                mMotionListAll = DatabaseService.findMotionList(JordanApplication.getUsername(MotionListActivity.this),String.valueOf(currentPageIndex));
                LogUtils.showLog("MotionList", "mMotionListAll:" + mMotionListAll.size());
                LogUtils.showLog("MotionList", "isUpdateAll:" + isUpdateAll);
                updateBtnColor();
                if (mMotionListAll.size() == 0||!isUpdateAll) {
                //获取最新的list
                    Date date = new Date();//取时间
                    doDetail("-1", "-1");
                }
                if (mMotionListAll.size() != 0){
                    if(choiesPager!=3) {
                        mMotionListAdapter.updateList(mMotionListAll);
                    }else{
                        mMotionListAdapter.updateList(mMotionListAll,choiesItem);
                    }
                }
                break;
        }

    }

    private void updateBtnColor() {
        switch (currentPageIndex) {
            case 0:
                mTVTimeNear.setTextColor(getResources().getColor(R.color.viewfinder_laser));
                mTVTimeNearWeek.setTextColor(getResources().getColor(R.color.motion_detail_position_text));
                mTVTimeNearMonth.setTextColor(getResources().getColor(R.color.motion_detail_position_text));
                mTVTimeNearAll.setTextColor(getResources().getColor(R.color.motion_detail_position_text));
                break;
            case 1:
                mTVTimeNear.setTextColor(getResources().getColor(R.color.motion_detail_position_text));
                mTVTimeNearWeek.setTextColor(getResources().getColor(R.color.viewfinder_laser));
                mTVTimeNearMonth.setTextColor(getResources().getColor(R.color.motion_detail_position_text));
                mTVTimeNearAll.setTextColor(getResources().getColor(R.color.motion_detail_position_text));
                break;
            case 2:
                mTVTimeNear.setTextColor(getResources().getColor(R.color.motion_detail_position_text));
                mTVTimeNearWeek.setTextColor(getResources().getColor(R.color.motion_detail_position_text));
                mTVTimeNearMonth.setTextColor(getResources().getColor(R.color.viewfinder_laser));
                mTVTimeNearAll.setTextColor(getResources().getColor(R.color.motion_detail_position_text));
                break;
            case 3:
                mTVTimeNear.setTextColor(getResources().getColor(R.color.motion_detail_position_text));
                mTVTimeNearWeek.setTextColor(getResources().getColor(R.color.motion_detail_position_text));
                mTVTimeNearMonth.setTextColor(getResources().getColor(R.color.motion_detail_position_text));
                mTVTimeNearAll.setTextColor(getResources().getColor(R.color.viewfinder_laser));
                break;

            default:
                break;
        }
    }

    @Override
    protected void onResume() {
        switch (currentPageIndex) {
            case 0:
                if(choiesPager!=0) {
                    mMotionListAdapter.updateList(mMotionListNear);
                }else{
                    mMotionListAdapter.updateList(mMotionListNear,choiesItem);
                }
                break;
            case 1:
                if(choiesPager!=1) {
                    mMotionListAdapter.updateList(mMotionListWeek);
                }else{
                    mMotionListAdapter.updateList(mMotionListWeek,choiesItem);
                }
                break;
            case 2:
                if(choiesPager!=2) {
                    mMotionListAdapter.updateList(mMotionListMonth);
                }else{
                    mMotionListAdapter.updateList(mMotionListMonth,choiesItem);
                }
                break;
            case 3:
                if(choiesPager!=3) {
                    mMotionListAdapter.updateList(mMotionListAll);
                }else{
                    mMotionListAdapter.updateList(mMotionListAll,choiesItem);
                }
                break;
            default:
                break;
        }
        super.onResume();
    }
}
