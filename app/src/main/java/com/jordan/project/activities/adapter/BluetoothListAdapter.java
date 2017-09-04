package com.jordan.project.activities.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jordan.project.R;
import com.jordan.project.entity.BluetoothData;
import com.jordan.project.utils.LogUtils;

import java.util.ArrayList;


public class BluetoothListAdapter extends BaseAdapter {
	Context mContext;
	LayoutInflater layoutInflater;
	int choiesPostion = 0 ;

	ArrayList<BluetoothData> list = new ArrayList<BluetoothData>();
	public BluetoothListAdapter(Context context, ArrayList<BluetoothData> list,int choiesPostion) {
		this.choiesPostion = choiesPostion;
		mContext = context;
		layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.list = list;
//		options = new DisplayImageOptions.Builder()
//		.showImageOnLoading(R.drawable.title_one)
//		.showImageForEmptyUri(R.drawable.title_one)
//		.showImageOnFail(R.drawable.title_one).cacheInMemory(true)
//		.cacheOnDisk(true).considerExifParams(true).build();
	}

	public void updateList(ArrayList<BluetoothData> list,int choiesPostion) {
		this.list = list;
		this.choiesPostion = choiesPostion;
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
        TextView tvName;
		TextView tvSN;
		TextView tvMAC;
		ImageView ivChoiesBluetooth;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		final ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			view = layoutInflater.inflate(R.layout.bluetooth_list_item, parent, false);
			holder.tvName=(TextView)view.findViewById(R.id.bluetooth_list_time);
            holder.tvSN=(TextView)view.findViewById(R.id.bluetooth_list_sn);
			holder.tvMAC=(TextView)view.findViewById(R.id.bluetooth_list_mac);
			holder.ivChoiesBluetooth=(ImageView) view.findViewById(R.id.choies_bluetooth_iv);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		LogUtils.showLog("getChoiesBluetooth","position:"+position);
		LogUtils.showLog("getChoiesBluetooth","choiesPostion:"+choiesPostion);
		if(position==choiesPostion){
			holder.ivChoiesBluetooth.setImageResource(R.mipmap.choies_bluetooth_press);
		}else{
			holder.ivChoiesBluetooth.setImageResource(R.mipmap.choies_bluetooth_normal);
		}
		BluetoothData bluetoothData = list.get(position);
		String mac = bluetoothData.getMac().substring(0,2)+":";
		mac = mac+bluetoothData.getMac().substring(2,4)+":";
		mac = mac+bluetoothData.getMac().substring(4,6)+":";
		mac = mac+bluetoothData.getMac().substring(6,8)+":";
		mac = mac+bluetoothData.getMac().substring(8,10)+":";
		mac = mac+bluetoothData.getMac().substring(10,12);
		holder.tvName.setText(bluetoothData.getName());
		holder.tvSN.setText("SN: "+bluetoothData.getSn());
		holder.tvMAC.setText("MAC: "+mac);
		LogUtils.showLog("Result", "adapter getView BluetoothData:" + bluetoothData.toString());

		return view;
	}


}
