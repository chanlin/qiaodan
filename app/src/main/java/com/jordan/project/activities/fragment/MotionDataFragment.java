package com.jordan.project.activities.fragment;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jordan.project.R;
import com.jordan.project.content.MotionDetailObserver;
import com.jordan.project.data.MotionDetailData;
import com.jordan.project.database.DatabaseObject;
import com.jordan.project.database.DatabaseProvider;
import com.jordan.project.database.DatabaseService;
import com.jordan.project.utils.LogUtils;

import java.math.BigDecimal;

public class MotionDataFragment extends Fragment {

	String serviceId;
	private TextView mTVTotalDistance;
	private TextView mTVmTVTotalNumberSteps;
	private TextView mTVTotalLongitudinal_distance;
	private TextView mTVTotalTransverseDistance;

	private TextView mTVTotalTime;
	private TextView mTVActiveTime;
	private TextView mTVActiveTimeProportion;

	private TextView mTVAverageMovingSpeed;
	private TextView mTVMaxMovingSpeed;
	private TextView mTVSprintCount;
	private TextView mTVChangeCount;
	private TextView mTVAverageChangeTouchDownTime;

	private TextView mTVVerticalJumpCount;
	private TextView mTVAverageVerticalJumpHeight;
	private TextView mTVAverageVerticalJumpTime;
	private TextView mTVAverageTouchDownAngle;
	private TextView mTVTouchDownType;
	private MotionDetailObserver mMotionDetailObserver;
	private LinearLayout mLLBG;
	private Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case MotionDetailObserver.DATABASE_UPDATE:
					LogUtils.showLog("btnClick", "MotionDetailObserver.DATABASE_UPDATE");
					//读取数据库
					initData();
					break;

				default:
					break;
			}
		};
	};
	private void registerContentObservers() {
		mMotionDetailObserver=new MotionDetailObserver(handler);
		Uri uri= Uri.parse("content://" + DatabaseProvider.AUTHOR + "/" + DatabaseObject.MOTION_DETAIL);
		getActivity().getContentResolver().registerContentObserver(uri, true,
				mMotionDetailObserver);
	}
	@SuppressLint({"NewApi", "ValidFragment"})
	public MotionDataFragment(){}
	@SuppressLint({"NewApi", "ValidFragment"})
	public MotionDataFragment(String serviceId) {
		this.serviceId=serviceId;
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

     @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
    		Bundle savedInstanceState) {
		 View view=inflater.inflate(R.layout.fragment_motion_data, null);
		 registerContentObservers();
		 setView(view);
		 initData();
		 return view;
    }

	private void initData() {
		MotionDetailData motionDetailData=DatabaseService.findMotionDetail(serviceId);
		if(motionDetailData!=null) {
			mLLBG.setVisibility(View.VISIBLE);
			mTVTotalDistance.setText(new BigDecimal((Double.valueOf(motionDetailData.getTotalDist())/1000)).setScale(2, BigDecimal.ROUND_DOWN).toString());
			mTVmTVTotalNumberSteps.setText(motionDetailData.getTotalStep());
			mTVTotalLongitudinal_distance.setText(new BigDecimal((Double.valueOf(motionDetailData.getTotalVerDist())/1000)).setScale(2, BigDecimal.ROUND_DOWN).toString());
			mTVTotalTransverseDistance.setText(new BigDecimal((Double.valueOf(motionDetailData.getTotalHorDist())/1000)).setScale(2, BigDecimal.ROUND_DOWN).toString());

			if(motionDetailData.getTotalTime().equals(""))
				motionDetailData.setTotalTime("0");
			double totalTime = Double.valueOf(motionDetailData.getTotalTime());
			int min = (int) (totalTime / 60);
			int s = (int) (totalTime % 60);
			if (min > 0) {
				mTVTotalTime.setText(min + "分" + s + "秒");
			} else {
				mTVTotalTime.setText(s + "秒");
			}
            //mTVTotalTime.setText(new BigDecimal(Double.valueOf(motionDetailData.getTotalTime())).setScale(2, BigDecimal.ROUND_DOWN).toString());

			if(motionDetailData.getTotalActiveTime().equals(""))
				motionDetailData.setTotalActiveTime("0");
			double totalActiveTime = Double.valueOf(motionDetailData.getTotalActiveTime());
			int minActive = (int) (totalActiveTime / 60);
			int sActive = (int) (totalActiveTime % 60);
			if (minActive > 0) {
				mTVActiveTime.setText(minActive + "分" + sActive + "秒");
			} else {
				mTVActiveTime.setText(sActive + "秒");
			}
			//mTVActiveTime.setText(new BigDecimal((Double.valueOf(motionDetailData.getTotalActiveTime()))).setScale(2, BigDecimal.ROUND_DOWN).toString());
            mTVActiveTimeProportion.setText((new BigDecimal((Double.valueOf(motionDetailData.getActiveRate())*100))).setScale(2, BigDecimal.ROUND_DOWN).toString());


            mTVAverageMovingSpeed.setText(new BigDecimal((Double.valueOf(motionDetailData.getAvgSpeed())*3.6)).setScale(2, BigDecimal.ROUND_DOWN).toString());
			mTVMaxMovingSpeed.setText(new BigDecimal((Double.valueOf(motionDetailData.getMaxSpeed())*3.6)).setScale(2, BigDecimal.ROUND_DOWN).toString());
			mTVSprintCount.setText(motionDetailData.getSpurtCount());
			mTVChangeCount.setText(motionDetailData.getBreakinCount());
			mTVAverageChangeTouchDownTime.setText(new BigDecimal((Double.valueOf(motionDetailData.getBreakinAvgTime())*1000)).setScale(2, BigDecimal.ROUND_DOWN).toString());

			mTVVerticalJumpCount.setText(motionDetailData.getVerJumpCount());
            if(!motionDetailData.getVerJumpAvgHigh().equals("NaN"))
			mTVAverageVerticalJumpHeight.setText(new BigDecimal((Double.valueOf(motionDetailData.getVerJumpAvgHigh())*100)).setScale(2, BigDecimal.ROUND_DOWN).toString());
            if(!motionDetailData.getVerJumpAvgHigh().equals("NaN"))
			mTVAverageVerticalJumpTime.setText(new BigDecimal((Double.valueOf(motionDetailData.getAvgHoverTime())*1000)).setScale(2, BigDecimal.ROUND_DOWN).toString());
            if(!motionDetailData.getVerJumpAvgHigh().equals("NaN"))
			mTVAverageTouchDownAngle.setText(new BigDecimal((Double.valueOf(motionDetailData.getAvgTouchAngle()))).setScale(2, BigDecimal.ROUND_DOWN).toString());
            int mTouchType = Integer.parseInt(motionDetailData.getTouchType());
			if(motionDetailData.getFooter().equals("R")) {
				if (mTouchType > 0) {
					mTVTouchDownType.setText("内翻");
				} else if (mTouchType < 0) {
					mTVTouchDownType.setText("外翻");
				} else {
					mTVTouchDownType.setText("正常");
				}
			}else{
				if (mTouchType > 0) {
					mTVTouchDownType.setText("外翻");
				} else if (mTouchType < 0) {
					mTVTouchDownType.setText("内翻");
				} else {
					mTVTouchDownType.setText("正常");
				}
			}
		}
	}

	public void setView(View view) {

		mTVTotalDistance=(TextView)view.findViewById(R.id.motion_detail_total_distance);
		mTVmTVTotalNumberSteps=(TextView)view.findViewById(R.id.motion_detail_total_number_steps);
		mTVTotalLongitudinal_distance=(TextView)view.findViewById(R.id.motion_detail_total_longitudinal_distance);
		mTVTotalTransverseDistance=(TextView)view.findViewById(R.id.motion_detail_total_transverse_distance);

		mTVTotalTime=(TextView)view.findViewById(R.id.motion_detail_total_time);
		mTVActiveTime=(TextView)view.findViewById(R.id.motion_detail_active_time);
		mTVActiveTimeProportion=(TextView)view.findViewById(R.id.motion_detail_active_time_proportion);
		mTVAverageMovingSpeed=(TextView)view.findViewById(R.id.motion_detail_average_moving_speed);
		mTVMaxMovingSpeed=(TextView)view.findViewById(R.id.motion_detail_max_moving_speed);
		mTVSprintCount=(TextView)view.findViewById(R.id.motion_detail_sprint_count);
		mTVChangeCount=(TextView)view.findViewById(R.id.motion_detail_change_count);
		mTVAverageChangeTouchDownTime=(TextView)view.findViewById(R.id.motion_detail_average_change_touch_down_time);

		mTVVerticalJumpCount=(TextView)view.findViewById(R.id.motion_detail_vertical_jump_count);
		mTVAverageVerticalJumpHeight=(TextView)view.findViewById(R.id.motion_detail_average_vertical_jump_height);
		mTVAverageVerticalJumpTime=(TextView)view.findViewById(R.id.motion_detail_average_vertical_jump_time);
		mTVAverageTouchDownAngle=(TextView)view.findViewById(R.id.motion_detail_average_touch_down_angle);
		mTVTouchDownType=(TextView)view.findViewById(R.id.motion_detail_touch_down_type);
		mLLBG=(LinearLayout)view.findViewById(R.id.ll_bg);
	}
}

