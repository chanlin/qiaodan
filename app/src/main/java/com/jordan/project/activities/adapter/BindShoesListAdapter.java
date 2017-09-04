package com.jordan.project.activities.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jordan.project.R;
import com.jordan.project.entity.MyShoesData;
import com.jordan.project.utils.LogUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;


public class BindShoesListAdapter extends BaseAdapter {
    Context mContext;
    LayoutInflater layoutInflater;
    private String isShowMyShoes = "";
    private String isShowShopShoes = "";
    private int checkPosition=-1;
    private int color = 0;

    ArrayList<MyShoesData> list = new ArrayList<MyShoesData>();

    public BindShoesListAdapter(Context context, ArrayList<MyShoesData> list) {
        mContext = context;
        layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.list = list;
//		options = new DisplayImageOptions.Builder()
//		.showImageOnLoading(R.drawable.title_one)
//		.showImageForEmptyUri(R.drawable.title_one)
//		.showImageOnFail(R.drawable.title_one).cacheInMemory(true)
//		.cacheOnDisk(true).considerExifParams(true).build();
    }

    public void updateList(ArrayList<MyShoesData> list) {
        this.list = list;
        notifyDataSetChanged();
    }
    public int getCheckPosition(){
        return checkPosition;
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
        ImageView mIVShoesBg;//my_shoes_list_item_shoe_bg_iv
        ImageView mIVShoes;//my_shoes_list_item_shoe_iv
        TextView mTVTextName;//my_shoes_list_item_shoe_name_text
        TextView mTVTextNumber;//my_shoes_list_item_shoe_number_text
        TextView mTVTextHint;//my_shoes_list_item_shoe_hint_text
        ImageView mIVShoesCheck;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            view = layoutInflater.inflate(R.layout.bind_shoes_list_item, parent, false);
            holder.mIVShoesCheck = (ImageView)view.findViewById(R.id.shoe_check);
            holder.mIVShoesBg = (ImageView) view.findViewById(R.id.my_shoes_list_item_bg_iv);
            holder.mIVShoes = (ImageView) view.findViewById(R.id.my_shoes_list_item_shoe_iv);
            holder.mTVTextName = (TextView) view.findViewById(R.id.my_shoes_list_item_shoe_name_text);
            holder.mTVTextNumber = (TextView) view.findViewById(R.id.my_shoes_list_item_shoe_number_text);
            holder.mTVTextHint = (TextView) view.findViewById(R.id.my_shoes_list_item_shoe_hint_text);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        MyShoesData myShoesData = list.get(position);
        holder.mTVTextName.setText(myShoesData.getName());
        holder.mTVTextNumber.setText("货号："+myShoesData.getCode());
        holder.mTVTextHint.setText(myShoesData.getIntro());
        if (!myShoesData.getPicture().equals("null")) {
            DisplayImageOptions options = new DisplayImageOptions.Builder()
                    .showImageOnLoading(R.mipmap.shoes_one)
                    .showImageForEmptyUri(R.mipmap.shoes_two)
                    .showImageOnFail(R.mipmap.shoes_three).cacheInMemory(true)
                    .cacheOnDisk(true).considerExifParams(true).build();
            ImageLoader.getInstance().displayImage(myShoesData.getPicture(), holder.mIVShoes, options);
        }
        if (color == 0) {
            color = 1;
            holder.mIVShoesBg.setBackgroundResource(R.mipmap.shop_shoes_bg_one);
            //holder.mIVShoes.setBackgroundResource(R.mipmap.shoes_two);
        } else {
            color = 0;
            holder.mIVShoesBg.setBackgroundResource(R.mipmap.shop_shoes_bg_two);
            //holder.mIVShoes.setBackgroundResource(R.mipmap.shoes_three);

        }
        if(position==checkPosition){
            holder.mIVShoesCheck.setBackgroundResource(R.mipmap.img_shoes_check);
        }else{
            holder.mIVShoesCheck.setBackgroundResource(R.mipmap.img_shoes_normal);
        }
        holder.mIVShoesCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkPosition==position){
                    checkPosition=-1;
                }else{
                    checkPosition=position;
                }
                notifyDataSetChanged();
            }
        });
        return view;
    }


}
