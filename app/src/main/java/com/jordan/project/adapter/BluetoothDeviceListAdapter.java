package com.jordan.project.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jordan.project.R;
import com.jordan.project.data.BluetoothDeviceInfo;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by icean on 2017/2/27.
 */

public class BluetoothDeviceListAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private ArrayList<BluetoothDeviceInfo> mAllDeviceInfo;

    public BluetoothDeviceListAdapter(Context ctx, HashMap<String, BluetoothDeviceInfo> all_device_info) {
        mContext = ctx;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Iterator itr_map = all_device_info.keySet().iterator();
        mAllDeviceInfo = new ArrayList<>();
        while (itr_map.hasNext()) {
            String current_key = (String) itr_map.next();
            mAllDeviceInfo.add(all_device_info.get(current_key));
        }
    }

    @Override
    public int getCount() {
        return mAllDeviceInfo.size();
    }

    @Override
    public Object getItem(int position) {
        return mAllDeviceInfo.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (null == convertView) {
            convertView = mLayoutInflater.inflate(R.layout.bluetooth_device_info, null);
        }
        TextView tv_name = (TextView) convertView.findViewById(R.id.ble_device_info_name);
        BluetoothDeviceInfo current_device_info = mAllDeviceInfo.get(position);
        tv_name.setText(current_device_info.getDeviceName());
        return convertView;
    }
}
