package com.jordan.project.activities.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jordan.project.JordanApplication;
import com.jordan.project.R;
import com.jordan.project.entity.PlayBallListData;
import com.jordan.project.utils.DistanceUtils;
import com.jordan.project.utils.LogUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class PlayBallListAdapter extends BaseAdapter {
    Context mContext;
    LayoutInflater layoutInflater;
    private SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm");

    ArrayList<PlayBallListData> list = new ArrayList<PlayBallListData>();

    public PlayBallListAdapter(Context context, ArrayList<PlayBallListData> list) {
        mContext = context;
        layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.list = list;
//		options = new DisplayImageOptions.Builder()
//		.showImageOnLoading(R.drawable.title_one)
//		.showImageForEmptyUri(R.drawable.title_one)
//		.showImageOnFail(R.drawable.title_one).cacheInMemory(true)
//		.cacheOnDisk(true).considerExifParams(true).build();
    }

    public void updateList(ArrayList<PlayBallListData> list) {
        this.list = list;
        notifyDataSetChanged();
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

        ImageView ivHead;
        TextView tvTime;
        TextView tvPlayBallName;
        TextView tvDistance;
        TextView tvType;
        TextView tvAddress;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            view = layoutInflater.inflate(R.layout.play_ball_list_item, parent, false);
            holder.ivHead = (ImageView) view.findViewById(R.id.main_play_ball_create_people_head);
            holder.tvTime = (TextView) view.findViewById(R.id.main_play_ball_time);
            holder.tvPlayBallName = (TextView) view.findViewById(R.id.main_play_ball_name);
            holder.tvDistance = (TextView) view.findViewById(R.id.main_play_ball_distance);
            holder.tvType = (TextView) view.findViewById(R.id.main_play_ball_play_type);
            holder.tvAddress = (TextView) view.findViewById(R.id.main_play_ball_address);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        PlayBallListData playBallListData = list.get(position);
        LogUtils.showLog("playBallListData", position + ":" + playBallListData.toString());
        if(playBallListData.getTime().length()<13) {
            holder.tvTime.setText(sdf.format(new Date(Long.parseLong(playBallListData.getTime()) * 1000)));
        }else{
            holder.tvTime.setText(sdf.format(new Date(Long.parseLong(playBallListData.getTime()))));
        }
        if (playBallListData.getType().equals("1")) {
            holder.tvType.setText(mContext.getResources().getString(R.string.common_play_ball_type_half_court));
        } else if (playBallListData.getType().equals("2")) {
            holder.tvType.setText(mContext.getResources().getString(R.string.common_play_ball_type_full_court));
        }
        if (playBallListData.getDistance() != null && !playBallListData.getDistance().equals("") && !playBallListData.getDistance().equals("null")) {
            try {
                double distance = Double.parseDouble(playBallListData.getDistance()) * 1000;
                LogUtils.showLog("PlayBallListAdapter","=============================================================");
                String dis = "";
                if (distance > 1000) {
                    int d = (int) distance / 1000;
                    dis = d + "km";
                } else {
                    int d = (int) distance;
                    dis = d + "m";
                }
                holder.tvDistance.setText(dis);
            }catch (Exception e){
                if (JordanApplication.nowLatitude == 0 && JordanApplication.nowLongitude == 0) {
                    holder.tvDistance.setText("");
                } else {
                    double dLongitude = Double.parseDouble(playBallListData.getLongitude());
                    double dLatitude = Double.parseDouble(playBallListData.getLatitude());
                    holder.tvDistance.setText(DistanceUtils.getDistanceUnit(JordanApplication.nowLongitude, JordanApplication.nowLatitude, dLongitude, dLatitude));//需要自己计算距离
                }
            }
        } else {
            LogUtils.showLog("PlayBallListAdapter","=============================================================");
            LogUtils.showLog("PlayBallListAdapter","JordanApplication.nowLatitude:"+JordanApplication.nowLatitude);
            LogUtils.showLog("PlayBallListAdapter","JordanApplication.nowLongitude:"+JordanApplication.nowLongitude);
            LogUtils.showLog("PlayBallListAdapter","playBallListData.getLongitude():"+playBallListData.getLongitude());
            LogUtils.showLog("PlayBallListAdapter","playBallListData.getLatitude():"+playBallListData.getLatitude());
            if (JordanApplication.nowLatitude == 0 && JordanApplication.nowLongitude == 0) {
                holder.tvDistance.setText("");
            } else {
                double dLongitude = Double.parseDouble(playBallListData.getLongitude());
                double dLatitude = Double.parseDouble(playBallListData.getLatitude());
                holder.tvDistance.setText(DistanceUtils.getDistanceUnit(JordanApplication.nowLongitude, JordanApplication.nowLatitude, dLongitude, dLatitude));//需要自己计算距离
            }
        }
        holder.tvPlayBallName.setText(playBallListData.getSlogan());
        holder.tvAddress.setText(playBallListData.getAddress());
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.default_1)
                .showImageForEmptyUri(R.mipmap.default_1)
                .showImageOnFail(R.mipmap.default_1).cacheInMemory(true)
                .cacheOnDisk(true).considerExifParams(true).build();
        ImageLoader.getInstance().displayImage(playBallListData.getVipImg(), holder.ivHead, options);
        return view;
    }


}
