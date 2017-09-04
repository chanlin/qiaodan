package com.jordan.project.activities.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jordan.project.R;
import com.jordan.project.data.TrainDictData;

import java.util.ArrayList;


public class TrainMenuAdapter extends BaseAdapter {
    Context mContext;
    LayoutInflater layoutInflater;

    ArrayList<TrainDictData> list = new ArrayList<TrainDictData>();
    public TrainMenuAdapter(Context context, ArrayList<TrainDictData> list) {
        mContext = context;
        layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.list = list;
    }

    public void updateList(ArrayList<TrainDictData> list) {
        this.list = list;
        notifyDataSetChanged();
    }
    public ArrayList<TrainDictData> getList() {
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
        TextView tvName;
        RelativeLayout rlBG;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            view = layoutInflater.inflate(R.layout.train_menu_item, parent, false);
            holder.tvName = (TextView) view.findViewById(R.id.train_menu_tv);
            holder.rlBG = (RelativeLayout)view.findViewById(R.id.train_menu_rl);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        TrainDictData trainDictData = list.get(position);
        holder.tvName.setText(trainDictData.getName());

        return view;
    }


}
