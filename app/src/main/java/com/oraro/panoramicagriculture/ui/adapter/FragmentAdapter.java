package com.oraro.panoramicagriculture.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;


public class FragmentAdapter extends FragmentPagerAdapter {

	private ArrayList<Fragment> list;
	FragmentManager fm;

	public FragmentAdapter(FragmentManager fm, ArrayList<Fragment> list) {
		super(fm);
		this.fm = fm;
		this.list = list;
	}

	@Override
	public Fragment getItem(int arg0) {
		return list.get(arg0);
	}

	@Override
	public int getCount() {
		return list.size();
	}



}
