package com.jordan.project.activities.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jordan.project.R;
import com.jordan.project.data.TrainListData;
import com.jordan.project.entity.PlayBallListData;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;


public class TrainListAdapter extends BaseAdapter {
	Context mContext;
	LayoutInflater layoutInflater;

	ArrayList<TrainListData> list = new ArrayList<TrainListData>();
	public TrainListAdapter(Context context, ArrayList<TrainListData> list) {
		mContext = context;
		layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.list = list;
//		options = new DisplayImageOptions.Builder()
//		.showImageOnLoading(R.drawable.title_one)
//		.showImageForEmptyUri(R.drawable.title_one)
//		.showImageOnFail(R.drawable.title_one).cacheInMemory(true)
//		.cacheOnDisk(true).considerExifParams(true).build();
	}

	public void updateList(ArrayList<TrainListData> list) {
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
        ImageView ivBG;
        TextView tvTitle;
        TextView tvIntro;
        TextView tvCount;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		final ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			view = layoutInflater.inflate(R.layout.train_list_item, parent, false);
            holder.ivBG = (ImageView)view.findViewById(R.id.train_list_item_bg);
            holder.tvTitle = (TextView)view.findViewById(R.id.train_list_item_title);
            holder.tvIntro = (TextView)view.findViewById(R.id.train_list_item_intro);
            holder.tvCount = (TextView)view.findViewById(R.id.train_list_item_count);
            view.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		//adpater使用list数据展示
        TrainListData trainListData = list.get(position);
        holder.tvTitle.setText(trainListData.getTitle());
        holder.tvIntro.setText(trainListData.getIntro());
        holder.tvCount.setText(trainListData.getCount()+mContext.getResources().getString(R.string.common_train_list_item_count_hint));
        //ImageLoader导入图片
        if (!trainListData.getThumb().equals("null")) {
            DisplayImageOptions options = new DisplayImageOptions.Builder()
                    .showImageOnLoading(R.mipmap.motion_detail_default_bg)
                    .showImageForEmptyUri(R.mipmap.motion_detail_default_bg)
                    .showImageOnFail(R.mipmap.motion_detail_default_bg).cacheInMemory(true)
                    .cacheOnDisk(true).considerExifParams(true).build();
            ImageLoader.getInstance().displayImage(trainListData.getThumb(), holder.ivBG, options);
        }
		return view;
	}


}
