package com.jordan.project.activities.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jordan.project.R;
import com.jordan.project.entity.MoveListData;

import java.math.BigDecimal;
import java.util.ArrayList;


public class MotionListAdapter extends BaseAdapter {
	Context mContext;
	LayoutInflater layoutInflater;
	int choiesPosition = -1;

	ArrayList<MoveListData> list = new ArrayList<MoveListData>();
	public MotionListAdapter(Context context, ArrayList<MoveListData> list) {
		mContext = context;
		layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.list = list;
//		options = new DisplayImageOptions.Builder()
//		.showImageOnLoading(R.drawable.title_one)
//		.showImageForEmptyUri(R.drawable.title_one)
//		.showImageOnFail(R.drawable.title_one).cacheInMemory(true)
//		.cacheOnDisk(true).considerExifParams(true).build();
	}

	public void updateList(ArrayList<MoveListData> list) {
		this.choiesPosition=-1;
		this.list = list;
		notifyDataSetChanged();
	}
	public void updateList(ArrayList<MoveListData> list,int choiesPosition) {
		this.choiesPosition=choiesPosition;
		this.list = list;
		notifyDataSetChanged();
	}
	public void updatePosition(int choiesPosition) {
		this.choiesPosition=choiesPosition;
		notifyDataSetChanged();
	}
	public ArrayList<MoveListData> getList() {
		return list;
	}


	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	class ViewHolder {
		TextView tvHour;
		TextView tvYear;
		RelativeLayout rLMotion;
		TextView tvDistance;
		TextView tvTime;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		final ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			view = layoutInflater.inflate(R.layout.motion_list_item, parent, false);
			holder.tvHour = (TextView)view.findViewById(R.id.time_hour);
			holder.tvYear = (TextView)view.findViewById(R.id.time_year);
			holder.rLMotion = (RelativeLayout)view.findViewById(R.id.rl_motion_list_item);
			holder.tvDistance = (TextView)view.findViewById(R.id.motion_distance);
			holder.tvTime = (TextView)view.findViewById(R.id.motion_time);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		MoveListData moveListData=list.get(position);
		holder.tvHour.setText(moveListData.getTimeHour());
		holder.tvYear.setText(moveListData.getTimeYear());
		if(position==choiesPosition){
			holder.rLMotion.setBackgroundResource(R.color.motion_list_choies_item);
		}else{
			holder.rLMotion.setBackground(null);
		}
		//设置距离和时长
		holder.tvDistance.setText(new BigDecimal((Double.valueOf(moveListData.getTotalDist()) / 1000))
				.setScale(2, BigDecimal.ROUND_DOWN).toString()+"km");
		if (moveListData.getTotalTime().equals(""))
			moveListData.setTotalTime("0");
		double totalTime = Double.valueOf(moveListData.getTotalTime());
		int hour = (int) (totalTime / 3600);
		int min = (int) (totalTime % 3600 / 60);
		int s = (int) (totalTime % 60);
		String hours = "00";
		String mins = "00";
		String ss = "00";
		if(hour<10){
			hours = "0"+hour;
		}else{
			hours = String.valueOf(hour);
		}
		if(min<10){
			mins = "0"+min;
		}else{
			mins = String.valueOf(min);
		}
		if(s<10){
			ss = "0"+s;
		}else{
			ss = String.valueOf(s);
		}
		holder.tvTime.setText(hours+":"+mins + ":" + ss);
		return view;
	}


}
