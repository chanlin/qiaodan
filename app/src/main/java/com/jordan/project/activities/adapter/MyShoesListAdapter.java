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


public class MyShoesListAdapter extends BaseAdapter {
    Context mContext;
    LayoutInflater layoutInflater;
    private String isShowMyShoes = "";
    private String isShowShopShoes = "";
    private int color = 0;

    ArrayList<MyShoesData> list = new ArrayList<MyShoesData>();

    public MyShoesListAdapter(Context context, ArrayList<MyShoesData> list) {
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
        isShowMyShoes = "";
        isShowShopShoes = "";
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
        ImageView mIVItemTop;//my_shoes_list_item_top_iv
        ImageView mIVItemBg;//my_shoes_list_item_bg_iv
        ImageView mIVShoesBg;//my_shoes_list_item_shoe_bg_iv
        ImageView mIVShoes;//my_shoes_list_item_shoe_iv
        TextView mTVItemTop;//my_shoes_list_item_top_text
        TextView mTVTextName;//my_shoes_list_item_shoe_name_text
        TextView mTVTextNumber;//my_shoes_list_item_shoe_number_text
        TextView mTVTextHint;//my_shoes_list_item_shoe_hint_text
        RelativeLayout mRLTop;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            view = layoutInflater.inflate(R.layout.my_shoes_list_item, parent, false);
            holder.mIVItemTop = (ImageView) view.findViewById(R.id.my_shoes_list_item_top_iv);
            holder.mIVItemBg = (ImageView) view.findViewById(R.id.my_shoes_list_item_bg_iv);
            holder.mIVShoesBg = (ImageView) view.findViewById(R.id.my_shoes_list_item_shoe_bg_iv);
            holder.mIVShoes = (ImageView) view.findViewById(R.id.my_shoes_list_item_shoe_iv);
            holder.mTVItemTop = (TextView) view.findViewById(R.id.my_shoes_list_item_top_text);
            holder.mTVTextName = (TextView) view.findViewById(R.id.my_shoes_list_item_shoe_name_text);
            holder.mTVTextNumber = (TextView) view.findViewById(R.id.my_shoes_list_item_shoe_number_text);
            holder.mTVTextHint = (TextView) view.findViewById(R.id.my_shoes_list_item_shoe_hint_text);
            holder.mRLTop = (RelativeLayout) view.findViewById(R.id.my_shoes_list_item_top_rl);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        LogUtils.showLog("Result", "list.size:"+list.size());
        LogUtils.showLog("Result", "position:"+position);
        MyShoesData myShoesData = list.get(position);
        if (myShoesData.getType()==1) {
            LogUtils.showLog("Result", "isShowMyShoes:"+isShowMyShoes);
            LogUtils.showLog("Result", "myShoesData.getId():"+myShoesData.getId());
            if(isShowMyShoes.equals("")||isShowMyShoes.equals(myShoesData.getId())){
                isShowMyShoes=myShoesData.getId();
                holder.mRLTop.setVisibility(View.VISIBLE);
                LogUtils.showLog("Result", "isShowMyShoes:VISIBLE");
            }else{
                holder.mRLTop.setVisibility(View.GONE);
                LogUtils.showLog("Result", "isShowMyShoes:GONE");
            }
            holder.mIVItemTop.setVisibility(View.VISIBLE);
            holder.mTVItemTop.setTextColor(mContext.getResources().getColor(R.color.my_shoes_text));
            holder.mTVItemTop.setText(R.string.my_ball_shoes);
            holder.mIVItemBg.setVisibility(View.VISIBLE);
            holder.mIVShoesBg.setVisibility(View.GONE);
            //ImageView mIVShoes;
            //TextView mTVTextName;
            holder.mTVTextNumber.setTextColor(mContext.getResources().getColor(R.color.my_shoes_text));
            holder.mTVTextHint.setTextColor(mContext.getResources().getColor(R.color.my_shoes_text));
            //holder.mIVShoes.setBackgroundResource(R.mipmap.shoes_one);
            holder.mTVTextName.setText(myShoesData.getName());
            holder.mTVTextNumber.setText("货号："+myShoesData.getCode());
            holder.mTVTextHint.setText(myShoesData.getIntro());
        } else {
            LogUtils.showLog("Result", "isShowShopShoes:"+isShowShopShoes);
            if(isShowShopShoes.equals("")||isShowShopShoes.equals(myShoesData.getId())){
                isShowShopShoes=myShoesData.getId();
                holder.mRLTop.setVisibility(View.VISIBLE);
                LogUtils.showLog("Result", "isShowShopShoes:VISIBLE");
            }else{
                holder.mRLTop.setVisibility(View.GONE);
                LogUtils.showLog("Result", "isShowShopShoes:GONE");
            }
            holder.mIVItemTop.setVisibility(View.GONE);
            holder.mTVItemTop.setTextColor(mContext.getResources().getColor(R.color.white));
            holder.mTVItemTop.setText(R.string.recommend_shoes);
            holder.mIVItemBg.setVisibility(View.GONE);
            holder.mIVShoesBg.setVisibility(View.VISIBLE);
            //ImageView mIVShoes;
            //TextView mTVTextName;
            holder.mTVTextNumber.setTextColor(mContext.getResources().getColor(R.color.white));
            holder.mTVTextHint.setTextColor(mContext.getResources().getColor(R.color.white));
            if (position%2==0) {
                holder.mIVShoesBg.setBackgroundResource(R.mipmap.shop_shoes_bg_one);
                //holder.mIVShoes.setBackgroundResource(R.mipmap.shoes_two);
            } else {
                holder.mIVShoesBg.setBackgroundResource(R.mipmap.shop_shoes_bg_two);
                //holder.mIVShoes.setBackgroundResource(R.mipmap.shoes_three);

            }
            holder.mTVTextName.setText(myShoesData.getName());
            holder.mTVTextNumber.setText("货号："+myShoesData.getCode());
            holder.mTVTextHint.setText(myShoesData.getIntro());
        }
        if (!myShoesData.getPicture().equals("null")) {
            DisplayImageOptions options = new DisplayImageOptions.Builder()
                    .showImageOnLoading(R.mipmap.shoes_one)
                    .showImageForEmptyUri(R.mipmap.shoes_two)
                    .showImageOnFail(R.mipmap.shoes_three).cacheInMemory(true)
                    .cacheOnDisk(true).considerExifParams(true).build();
            ImageLoader.getInstance().displayImage(myShoesData.getPicture(), holder.mIVShoes, options);
        }
        return view;
    }


}
