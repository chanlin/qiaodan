package com.jordan.project.activities.adapter;

import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;

import com.jordan.project.R;
import com.jordan.project.entity.PathOfParticleData;
import com.jordan.project.utils.LogUtils;
import com.jordan.project.widget.CircleImageView;

import java.util.ArrayList;


public class PathOfParticleAdapter extends BaseAdapter {
    Context mContext;
    LayoutInflater layoutInflater;
    int height = 0;
    int width = 0;
    int maxM;
    double maxS, minS;

    ArrayList<PathOfParticleData> list = new ArrayList<PathOfParticleData>();

    public PathOfParticleAdapter(Context context, ArrayList<PathOfParticleData> list,int width, int height,
                                 int maxM, double maxS, double minS) {
        this.maxM = maxM;
        this.maxS = maxS;
        this.minS = minS;
        LogUtils.showLog("particlelist", "PathOfParticleAdapter height:" + height);
        LogUtils.showLog("particlelist", "PathOfParticleAdapter width:" + width);
        this.height = height;
        this.width = width;
        mContext = context;
        layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.list = list;
//		options = new DisplayImageOptions.Builder()
//		.showImageOnLoading(R.drawable.title_one)
//		.showImageForEmptyUri(R.drawable.title_one)
//		.showImageOnFail(R.drawable.title_one).cacheInMemory(true)
//		.cacheOnDisk(true).considerExifParams(true).build();
    }

    public void updateList(ArrayList<PathOfParticleData> list, int height,int maxM, double maxS, double minS) {
        LogUtils.showLog("particlelist", "updateList height:" + height);
        if (height != 0)
            this.height = height;
        this.list = list;
        this.maxM = maxM;
        this.maxS = maxS;
        this.minS = minS;
        notifyDataSetChanged();
    }

    public ArrayList<PathOfParticleData> getList() {
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
        CircleImageView ivPoint;
        RelativeLayout rlBG;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            view = layoutInflater.inflate(R.layout.path_of_particle_item, parent, false);
            holder.ivPoint = (CircleImageView) view.findViewById(R.id.main_path_of_particle_iv);
            holder.rlBG = (RelativeLayout) view.findViewById(R.id.main_path_of_particle_rl);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        RelativeLayout.LayoutParams param = (RelativeLayout.LayoutParams) holder.rlBG.getLayoutParams();
        //获取当前控件的布局对象
        param.height = height / 15;//设置当前控件布局的高度
        param.width = width / 28;//设置当前控件布局的高度
        holder.rlBG.setLayoutParams(param);//将设置好的布局参数应用到控件中
        LogUtils.showLog("particlelist", "getView height:" + height);

        PathOfParticleData pathOfParticleData = list.get(position);
        //通过属性设置点
        //pathOfParticleData.getAvgSpeed();
        LogUtils.showLog("particlelistss", "最大平均速度maxS:" + maxS);
        LogUtils.showLog("particlelistss", "最小平均速度minS:" + minS);
        LogUtils.showLog("particlelist", "最大密度maxM:" + maxM);
        LogUtils.showLog("particlelists", "pathOfParticleData.getCount():" + pathOfParticleData.getCount());
        if (pathOfParticleData.getCount() != 0) {
            LogUtils.showLog("particlelistss", "pathOfParticleData.getAvgSpeed():" + pathOfParticleData.getAvgSpeed());
            double s = (maxS - minS);
            LogUtils.showLog("particlelistss", "maxS - minS:" + s);
            LogUtils.showLog("particlelistss", "minS + s / 10:" + (minS + s / 10));
            LogUtils.showLog("particlelistss", "minS + s * 2 / 10:" + (minS + s * 2/ 10));
            if (pathOfParticleData.getAvgSpeed() < 0.9) {
                holder.ivPoint.setAlpha(0.1F);
                holder.ivPoint.setBackgroundResource(R.mipmap.pic_table_path_of_particle_speed_level_one);
            } else if (pathOfParticleData.getAvgSpeed() <1.8) {
                holder.ivPoint.setAlpha(0.2F);
                holder.ivPoint.setBackgroundResource(R.mipmap.pic_table_path_of_particle_speed_level_two);
            } else if (pathOfParticleData.getAvgSpeed() < 2.7) {
                holder.ivPoint.setAlpha(0.3F);
                holder.ivPoint.setBackgroundResource(R.mipmap.pic_table_path_of_particle_speed_level_three);
            } else if (pathOfParticleData.getAvgSpeed() < 3.6) {
                holder.ivPoint.setAlpha(0.4F);
                holder.ivPoint.setBackgroundResource(R.mipmap.pic_table_path_of_particle_speed_level_four);
            } else if (pathOfParticleData.getAvgSpeed() < 4.5) {
                holder.ivPoint.setAlpha(0.5F);
                holder.ivPoint.setBackgroundResource(R.mipmap.pic_table_path_of_particle_speed_level_five);
            } else if (pathOfParticleData.getAvgSpeed() < 5.4) {
                holder.ivPoint.setAlpha(0.6F);
                holder.ivPoint.setBackgroundResource(R.mipmap.pic_table_path_of_particle_speed_level_six);
            } else if (pathOfParticleData.getAvgSpeed() < 6.3) {
                holder.ivPoint.setAlpha(0.7F);
                holder.ivPoint.setBackgroundResource(R.mipmap.pic_table_path_of_particle_speed_level_seven);
            } else if (pathOfParticleData.getAvgSpeed() < 7.2) {
                holder.ivPoint.setAlpha(0.8F);
                holder.ivPoint.setBackgroundResource(R.mipmap.pic_table_path_of_particle_speed_level_eight);
            } else if (pathOfParticleData.getAvgSpeed() < 8.1) {
                holder.ivPoint.setAlpha(0.9F);
                holder.ivPoint.setBackgroundResource(R.mipmap.pic_table_path_of_particle_speed_level_nine);
            } else {
                holder.ivPoint.setAlpha(1F);
                holder.ivPoint.setBackgroundResource(R.mipmap.pic_table_path_of_particle_speed_level_ten);
            }
        }
        LogUtils.showLog("particlelist", "getView pathOfParticleData.getAvgSpeed():" + pathOfParticleData.getAvgSpeed());
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) holder.ivPoint.getLayoutParams();
        //获取当前控件的布局对象
        params.height = height / 15;//设置当前控件布局的高度
        LogUtils.showLog("particlelist", "getView height:" + height);

        if (pathOfParticleData.getCount() == 0) {
//            params.width = 0;//设置当前控件布局的高度
//            params.height = 0;//设置当前控件布局的高度
            params.width=((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0,
                    mContext.getResources().getDisplayMetrics()));
            params.height=((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0,
                    mContext.getResources().getDisplayMetrics()));
        } else if (pathOfParticleData.getCount() >= 1 && pathOfParticleData.getCount() <= maxM / 5) {
            LogUtils.showLog("particlelists", "pathOfParticleData.getCount() >= 1");
//            params.width = 2;
//            params.height = 2;//设置当前控件布局的高度
            params.width = ((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2,
                    mContext.getResources().getDisplayMetrics()));
            params.height = ((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2,
                    mContext.getResources().getDisplayMetrics()));
        } else if (pathOfParticleData.getCount() >= 2 && pathOfParticleData.getCount() <= maxM * 2 / 5) {
//            params.width = 4;//设置当前控件布局的高度
//            params.height = 4;//设置当前控件布局的高度
            params.width = ((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 3,
                    mContext.getResources().getDisplayMetrics()));
            params.height = ((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 3,
                    mContext.getResources().getDisplayMetrics()));
        } else if (pathOfParticleData.getCount() >= 3 && pathOfParticleData.getCount() <= maxM * 3 / 5) {
//            params.width = 6;//设置当前控件布局的高度
//            params.height = 6;//设置当前控件布局的高度
            params.width = ((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4,
                    mContext.getResources().getDisplayMetrics()));
            params.height = ((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4,
                    mContext.getResources().getDisplayMetrics()));
        } else if (pathOfParticleData.getCount() >= 4 && pathOfParticleData.getCount() <= maxM * 4 / 5) {
//            params.width = 8;//设置当前控件布局的高度
//            params.height = 8;//设置当前控件布局的高度
            params.width = ((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5,
                    mContext.getResources().getDisplayMetrics()));
            params.height = ((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5,
                    mContext.getResources().getDisplayMetrics()));
        } else if (pathOfParticleData.getCount() > maxM * 4 / 5) {
//            params.width = 8;//设置当前控件布局的高度
//            params.height = 8;//设置当前控件布局的高度
            params.width = ((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 6,
                    mContext.getResources().getDisplayMetrics()));
            params.height = ((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 6,
                    mContext.getResources().getDisplayMetrics()));
        } else {
//            params.width = 8;//设置当前控件布局的高度
//            params.height = 8;//设置当前控件布局的高度
            params.width = ((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 6,
                    mContext.getResources().getDisplayMetrics()));
            params.height = ((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 6,
                    mContext.getResources().getDisplayMetrics()));
        }
        holder.ivPoint.setLayoutParams(params);//将设置好的布局参数应用到控件中


        return view;
    }


}
