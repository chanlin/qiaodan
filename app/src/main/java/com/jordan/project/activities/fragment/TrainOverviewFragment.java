package com.jordan.project.activities.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.safari.httplibs.config.InnerMessageConfig;
import com.jordan.project.JordanApplication;
import com.jordan.project.R;
import com.jordan.project.activities.train.TrainDetailActivity;
import com.jordan.project.data.MotionRedarData;
import com.jordan.project.entity.TitleValueEntity;
import com.jordan.project.utils.JsonFalseUtils;
import com.jordan.project.utils.JsonSuccessUtils;
import com.jordan.project.utils.LogUtils;
import com.jordan.project.utils.ToastUtils;
import com.jordan.project.widget.SpiderWebChart;
import com.jordan.usersystemlibrary.UserManager;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class TrainOverviewFragment extends Fragment {
    SpiderWebChart spiderwebchart;
    private MotionRedarData mMotionRedarData;

    private ImageView mIvBG;
    private TextView mTvTitle;
    private TextView mTvIntro;
    private TextView mTvCount;
    private TextView mTvTrainTitle;
    private RelativeLayout mRlTrainDamageRiskAssessment;
    private RelativeLayout mRlTrain;
    private UserManager userManager;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case InnerMessageConfig.USER_MOVE_REDAR_MESSAGE_SUCCESS:
                    //解析{"addSpurt":"100.00","lateralShearDirection":"0.04","verJump":"38.20","agile":"309.26","explosiveForce":"0.78"}
                    //赋值mMotionRedarData
                    LogUtils.showLog("Result", "USER_MOVE_REDAR_MESSAGE_SUCCESS result:" + (String) msg.obj);
                    try {
                        mMotionRedarData = JsonSuccessUtils.getMotionRedar((String) msg.obj);
                        LogUtils.showLog("redar", "mMotionRedarData:" + mMotionRedarData.toString());
                        List<TitleValueEntity> data1 = new ArrayList<TitleValueEntity>();
                        if (mMotionRedarData.getVerJump() > 100)
                            mMotionRedarData.setVerJump(100);
                        data1.add(new TitleValueEntity(getResources().getString(
                                R.string.spider_web_chart_one), mMotionRedarData.getVerJump()));
                        if (mMotionRedarData.getAgile() > 100)
                            mMotionRedarData.setAgile(100);
                        data1.add(new TitleValueEntity(getResources().getString(
                                R.string.spider_web_chart_two), mMotionRedarData.getAgile()));
                        if (mMotionRedarData.getExplosiveForce() > 100)
                            mMotionRedarData.setExplosiveForce(100);
                        data1.add(new TitleValueEntity(getResources().getString(
                                R.string.spider_web_chart_three), mMotionRedarData.getExplosiveForce()));
                        if (mMotionRedarData.getAddSpurt() > 100)
                            mMotionRedarData.setAddSpurt(100);
                        data1.add(new TitleValueEntity(getResources().getString(
                                R.string.spider_web_chart_four), mMotionRedarData.getAddSpurt()));
                        if (mMotionRedarData.getLateralShearDirection() > 100)
                            mMotionRedarData.setLateralShearDirection(100);
                        data1.add(new TitleValueEntity(getResources().getString(
                                R.string.spider_web_chart_five), mMotionRedarData.getLateralShearDirection()));
                        List<List<TitleValueEntity>> data = new ArrayList<List<TitleValueEntity>>();
                        data.add(data1);
                        spiderwebchart.setData(data);
                        spiderwebchart.setLatitudeNum(5);
                        spiderwebchart.invalidate();
                        initData();
                    } catch (Exception e) {
                        mHandler.sendEmptyMessage(InnerMessageConfig.USER_MOVE_REDAR_MESSAGE_EXCEPTION);
                        e.printStackTrace();
                    }
                    break;
                case InnerMessageConfig.USER_MOVE_REDAR_MESSAGE_EXCEPTION:
                    try {
                        ToastUtils.shortToast(getActivity(), getResources().getString(R.string.http_exception));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case InnerMessageConfig.USER_MOVE_REDAR_MESSAGE_FALSE:
                    try {
                        String errorMsg = JsonFalseUtils.onlyErrorCodeResult((String) msg.obj);
                        if (errorMsg != null)
                            ToastUtils.shortToast(getActivity(), errorMsg);
                    } catch (JSONException e) {
                        mHandler.sendEmptyMessage(InnerMessageConfig.USER_LOGIN_MESSAGE_EXCEPTION);
                        e.printStackTrace();
                    }
                    break;
                case InnerMessageConfig.USER_TRAIN_COUNT_MESSAGE_SUCCESS:
                    moveRedar();
                    break;
            }
            ;
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        userManager = new UserManager(getActivity(), mHandler);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_train_over_view, null);
        setView(view);
        moveRedar();
        initSpiderWebChart(view);
        return view;
    }

    private void setView(View view) {
        mIvBG = (ImageView) view.findViewById(R.id.train_list_item_bg);
        mTvTitle = (TextView) view.findViewById(R.id.train_list_item_title);
        mTvIntro = (TextView) view.findViewById(R.id.train_list_item_intro);
        mTvCount = (TextView) view.findViewById(R.id.train_list_item_count);
        mTvTrainTitle = (TextView) view.findViewById(R.id.train_over_view_item_train_tv);
        mRlTrain = (RelativeLayout) view.findViewById(R.id.train_over_view_item_train);
        mRlTrainDamageRiskAssessment = (RelativeLayout) view.findViewById(R.id.train_damage_risk_assessment);
        mRlTrainDamageRiskAssessment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TrainDetailActivity.class);
                intent.putExtra("id", "1");
                intent.putExtra("link", "http://47.90.55.236:8080/jordan-web/view/tip.html");
                intent.putExtra("title", "损伤风险评估");
                startActivity(intent);
            }
        });
        LogUtils.showLog("redar", "initData 2222222222");
    }

    private void initData() {
        LogUtils.showLog("redar", "initData mMotionRedarData:" + mMotionRedarData.toString());
        if (mMotionRedarData.isHasData()) {
            mRlTrain.setVisibility(View.VISIBLE);
            mTvTrainTitle.setVisibility(View.VISIBLE);
            mTvTitle.setText(mMotionRedarData.getTitle());
            mTvIntro.setText(mMotionRedarData.getIntro());
            mTvCount.setText(mMotionRedarData.getCount() + getResources().getString(R.string.common_train_list_item_count_hint));
            //ImageLoader导入图片
            if (!mMotionRedarData.getThumb().equals("null") && !mMotionRedarData.getThumb().equals("")) {
                DisplayImageOptions options = new DisplayImageOptions.Builder()
                        .showImageOnLoading(R.mipmap.motion_detail_default_bg)
                        .showImageForEmptyUri(R.mipmap.motion_detail_default_bg)
                        .showImageOnFail(R.mipmap.motion_detail_default_bg).cacheInMemory(true)
                        .cacheOnDisk(true).considerExifParams(true).build();
                ImageLoader.getInstance().displayImage(mMotionRedarData.getThumb(), mIvBG, options);
            }
            mIvBG.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    trainCount(mMotionRedarData.getId());
                    Intent intent = new Intent(getActivity(), TrainDetailActivity.class);
                    intent.putExtra("id", mMotionRedarData.getId());
                    intent.putExtra("link", mMotionRedarData.getLink());
                    intent.putExtra("title", mMotionRedarData.getTitle());
                    startActivity(intent);
                }
            });
        } else {
            mRlTrain.setVisibility(View.GONE);
            mTvTrainTitle.setVisibility(View.GONE);
        }
    }

    private void initSpiderWebChart(View view) {
        LogUtils.showLog("redar", "initSpiderWebChart 0");
        this.spiderwebchart = (SpiderWebChart) view.findViewById(R.id.spiderwebchart);
        LogUtils.showLog("redar", "initSpiderWebChart 1");
        List<TitleValueEntity> data1 = new ArrayList<TitleValueEntity>();
        data1.add(new TitleValueEntity(getResources().getString(R.string.spider_web_chart_one), 0));
        data1.add(new TitleValueEntity(getResources().getString(R.string.spider_web_chart_two), 0));
        data1.add(new TitleValueEntity(getResources().getString(R.string.spider_web_chart_three), 0));
        data1.add(new TitleValueEntity(getResources().getString(R.string.spider_web_chart_four), 0));
        data1.add(new TitleValueEntity(getResources().getString(R.string.spider_web_chart_five), 0));
        List<List<TitleValueEntity>> data = new ArrayList<List<TitleValueEntity>>();
        data.add(data1);
        LogUtils.showLog("redar", "initSpiderWebChart 2");
        spiderwebchart.setData(data);
        LogUtils.showLog("redar", "initSpiderWebChart 3");
        spiderwebchart.setLatitudeNum(5);
        LogUtils.showLog("redar", "initSpiderWebChart 4");
    }

    private void moveRedar() {
        userManager.moveRedar(JordanApplication.getVipID(getActivity()));
    }
    private void trainCount(String id) {
        userManager.trainCount(id);
    }
}
