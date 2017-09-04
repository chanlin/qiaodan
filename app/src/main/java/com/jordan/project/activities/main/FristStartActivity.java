package com.jordan.project.activities.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.os.Bundle;

import com.jordan.project.R;
import com.jordan.project.activities.adapter.MainFragmentPagerAdapter;
import com.jordan.project.activities.fragment.FristStartFragment;

import java.util.ArrayList;

public class FristStartActivity extends FragmentActivity {

    private ViewPager viewPager;
    private MainFragmentPagerAdapter adapter;
    int currentPageIndex=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frist_start);
        setView();
    }

    /**
     * 设置介绍界面
     */
    private void setView() {
        viewPager=(ViewPager)findViewById(R.id.viewPager_main);
        ArrayList<Fragment> list=new ArrayList<Fragment>();
        list.add(new FristStartFragment(1));
        list.add(new FristStartFragment(2));
        list.add(new FristStartFragment(3));
        list.add(new FristStartFragment(4));
        list.add(new FristStartFragment(5));
        //list.add(new FourthFragment());
        adapter=new MainFragmentPagerAdapter(getSupportFragmentManager(), list);
        viewPager.setAdapter(adapter);
    }

}
