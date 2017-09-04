package com.jordan.project.activities.train;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jordan.project.JordanApplication;
import com.jordan.project.R;
import com.jordan.project.activities.adapter.MainFragmentPagerAdapter;
import com.jordan.project.activities.fragment.TrainMenuFragment;
import com.jordan.project.activities.fragment.TrainOverviewFragment;
import com.jordan.project.database.DatabaseService;
import com.jordan.project.entity.UserInfoData;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

public class TrainActivity extends FragmentActivity implements View.OnClickListener{

    private TextView mTVName;
    private TextView mTVPosition;
    private ImageView mIVHead;
    private ViewPager viewPager;
    private MainFragmentPagerAdapter adapter;
    int currentPageIndex=0;
    private RelativeLayout mRLOverView,mRLMenu;
    private TextView mTVOverView,mTVMenu;
    private ImageView mIVOverView,mIVMenu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_train);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.common_back_title);
        TextView mTvTitle = (TextView) findViewById(R.id.title_text);
        mTvTitle.setText(getResources().getString(R.string.common_train));
        RelativeLayout mRLBack=(RelativeLayout)findViewById(R.id.title_back);
        mRLBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        initData();
        setView();
        setListener();

    }
    private void initData() {
        mTVName=(TextView)findViewById(R.id.user_data_name);
        mTVPosition=(TextView)findViewById(R.id.user_data_position);
        mIVHead=(ImageView)findViewById(R.id.user_data_head_iv);
        //获取数据库用户信息
        UserInfoData userInfoData = DatabaseService.findUserInfo(JordanApplication.getUsername(TrainActivity.this));
        if(userInfoData!=null) {
            if (!userInfoData.getNick().equals("null"))
                mTVName.setText(getResources().getString(R.string.common_unit_nick)+userInfoData.getNick());
            if (!userInfoData.getPosition().equals("null"))
                mTVPosition.setText(getResources().getString(R.string.common_unit_position)+userInfoData.getPosition());


            //ImageLoader导入图片
            if (!userInfoData.getImg().equals("null")) {
                DisplayImageOptions options = new DisplayImageOptions.Builder()
                        .showImageOnLoading(R.mipmap.img_jordan_logo)
                        .showImageForEmptyUri(R.mipmap.img_jordan_logo)
                        .showImageOnFail(R.mipmap.img_jordan_logo).cacheInMemory(true)
                        .cacheOnDisk(true).considerExifParams(true).build();
                ImageLoader.getInstance().displayImage(userInfoData.getImg(), mIVHead, options);
            }
        }
    }
    private void setView() {
        viewPager=(ViewPager)findViewById(R.id.train_view_pager);
        ArrayList<Fragment> list=new ArrayList<Fragment>();
        list.add(new TrainOverviewFragment());
        list.add(new TrainMenuFragment());
        //list.add(new FourthFragment());
        adapter=new MainFragmentPagerAdapter(getSupportFragmentManager(), list);
        viewPager.setAdapter(adapter);

        mRLOverView = (RelativeLayout)findViewById(R.id.train_to_over_view_rl);
        mRLMenu = (RelativeLayout)findViewById(R.id.train_to_menu_rl);
        mTVOverView = (TextView) findViewById(R.id.train_to_over_view_text);
        mTVMenu = (TextView) findViewById(R.id.train_to_menu_text);
        mIVOverView = (ImageView) findViewById(R.id.train_to_over_view_iv);
        mIVMenu = (ImageView) findViewById(R.id.train_to_menu_iv);

        viewPager.setCurrentItem(0);
        currentPageIndex = 0;
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {
                viewPager.setCurrentItem(arg0);
                currentPageIndex = arg0;
                updateBtnColor();

            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
                // TODO Auto-generated method stub

            }
        });
    }
    private void setListener() {
        mRLOverView.setOnClickListener(this);
        mRLMenu.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.train_to_over_view_rl:
                viewPager.setCurrentItem(0);
                currentPageIndex = 0;
                updateBtnColor();
                break;
            case R.id.train_to_menu_rl:
                viewPager.setCurrentItem(1);
                currentPageIndex = 1;
                updateBtnColor();
                break;
        }
    }
    private void updateBtnColor(){
        switch (currentPageIndex) {
            case 0:
                mIVOverView.setVisibility(View.VISIBLE);
                mIVMenu.setVisibility(View.GONE);
                break;
            case 1:
                mIVOverView.setVisibility(View.GONE);
                mIVMenu.setVisibility(View.VISIBLE);
                break;
            case 2:
                mIVOverView.setVisibility(View.GONE);
                mIVMenu.setVisibility(View.GONE);
                break;

            default:
                break;
        }
    }
}
