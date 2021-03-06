package com.jordan.project.activities.adapter;

import java.util.ArrayList;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class MainFragmentPagerAdapter extends FragmentPagerAdapter{
    ArrayList<Fragment> list;
	public MainFragmentPagerAdapter(FragmentManager fm,ArrayList<Fragment> list) {
		super(fm);
		this.list=list;
	}

	@Override
	public Fragment getItem(int position) {
		return list.get(position);
	}

	@Override
	public int getCount() {
		return list.size();
	}
}
