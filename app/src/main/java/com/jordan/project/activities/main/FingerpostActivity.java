package com.jordan.project.activities.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import com.jordan.project.R;
import com.jordan.project.activities.adapter.MainFragmentPagerAdapter;
import com.jordan.project.activities.fragment.FingerpostFragment;

import java.util.ArrayList;

public class FingerpostActivity extends FragmentActivity {

    private ViewPager viewPager;
    private MainFragmentPagerAdapter adapter;
    int currentPageIndex=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fingerpost);
        setView();
    }

    /**
     * 设置介绍界面
     */
    private void setView() {
        viewPager=(ViewPager)findViewById(R.id.viewPager_main);
        ArrayList<Fragment> list=new ArrayList<Fragment>();
        list.add(new FingerpostFragment(1));
        list.add(new FingerpostFragment(2));
        list.add(new FingerpostFragment(3));
        list.add(new FingerpostFragment(4));
        //list.add(new FourthFragment());
        adapter=new MainFragmentPagerAdapter(getSupportFragmentManager(), list);
        viewPager.setAdapter(adapter);
    }

}
