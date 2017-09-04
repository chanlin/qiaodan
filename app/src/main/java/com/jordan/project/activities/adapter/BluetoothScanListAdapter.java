package com.jordan.project.activities.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.jordan.commonlibrary.CommonManager;
import com.jordan.project.R;
import com.jordan.project.entity.BluetoothData;
import com.jordan.project.utils.LogUtils;
import com.jordan.project.widget.LoadingProgressDialog;

import java.util.ArrayList;


public class BluetoothScanListAdapter extends BaseAdapter {
	Context mContext;
	LayoutInflater layoutInflater;
	CommonManager mCommonManager;

	ArrayList<BluetoothData> list = new ArrayList<BluetoothData>();
	public BluetoothScanListAdapter(Context context, ArrayList<BluetoothData> list,CommonManager commonManager) {
		mCommonManager = commonManager;
		mContext = context;
		layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.list = list;
//		options = new DisplayImageOptions.Builder()
//		.showImageOnLoading(R.drawable.title_one)
//		.showImageForEmptyUri(R.drawable.title_one)
//		.showImageOnFail(R.drawable.title_one).cacheInMemory(true)
//		.cacheOnDisk(true).considerExifParams(true).build();
	}

	public void updateList(ArrayList<BluetoothData> list) {
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
		TextView tvSN;
		TextView tvMAC;
		Button btnBind;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		final ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			view = layoutInflater.inflate(R.layout.bluetooth_scan_list_item, parent, false);
            holder.tvSN=(TextView)view.findViewById(R.id.bluetooth_list_sn);
			holder.tvMAC=(TextView)view.findViewById(R.id.bluetooth_list_mac);
			holder.btnBind=(Button)view.findViewById(R.id.bind_bluetooth);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		final BluetoothData bluetoothData = list.get(position);
		holder.tvSN.setText("SN: "+bluetoothData.getSn());
		holder.tvMAC.setText("MAC: "+bluetoothData.getMac());
		LogUtils.showLog("Result", "adapter getView BluetoothData:" + bluetoothData.toString());
		holder.btnBind.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(!bluetoothData.isBind()) {
					bluetoothBind(bluetoothData.getSn(), bluetoothData.getMac());
				}
			}
		});
		if(bluetoothData.isBind()) {
			holder.btnBind.setBackgroundResource(R.color.common_has_bind);
			holder.btnBind.setText(R.string.has_bind);
		}else{
			holder.btnBind.setBackgroundResource(R.mipmap.login_button);
			holder.btnBind.setText(R.string.bind);
		}
		return view;
	}
    public String sn="";
	public String mac="";

	private void bluetoothBind(String sn, String mac) {
		this.sn = sn;
		this.mac = mac;
		LoadingProgressDialog.show(mContext, false, true, 30000);
		mCommonManager.bluetoothBind(sn, mac.replace(":",""));
	}
}
