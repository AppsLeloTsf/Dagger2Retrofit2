package com.molitics.molitician.ui.dashboard.leader.newleaderprofile;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by rahul on 6/27/2017.
 */

public class NewLeaderProfileAdapter extends FragmentPagerAdapter {

    ArrayList<Fragment> mFragment = new ArrayList<>();
    ArrayList<String> mTitle = new ArrayList<>();

    public NewLeaderProfileAdapter(FragmentManager fm, ArrayList<Fragment> mFragment, ArrayList<String> mTitle) {
        super(fm);
        this.mFragment = mFragment;
        this.mTitle = mTitle;
    }

    public Fragment getRegisteredFragment(int position) {
        return mFragment.get(position);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragment.get(position);
    }

    @Override
    public int getCount() {
        return mFragment.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitle.get(position);
    }
}
