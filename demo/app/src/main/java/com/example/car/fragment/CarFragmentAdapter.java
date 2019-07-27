package com.example.car.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class CarFragmentAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> fragments ;

    public CarFragmentAdapter(FragmentManager fm, ArrayList<Fragment> framgents) {
        super(fm);
        this.fragments = framgents;
    }

    @Override
    public Fragment getItem(int i) {
        return fragments.get(i);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
