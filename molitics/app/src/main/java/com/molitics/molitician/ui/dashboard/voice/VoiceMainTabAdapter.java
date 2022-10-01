package com.molitics.molitician.ui.dashboard.voice;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rahul on 02/01/18.
 */

public class VoiceMainTabAdapter extends FragmentPagerAdapter {

    List<String> mFragmentTitle = new ArrayList<>();
    List<Integer> mUrl = new ArrayList<>();

    public VoiceMainTabAdapter(FragmentManager fm, List<String> mFragmentTitle, List<Integer> mUrl) {
        super(fm);
        this.mFragmentTitle = mFragmentTitle;
        this.mUrl = mUrl;
    }

    @Override
    public Fragment getItem(int position) {
        return VoiceFragment.getInstance(mUrl.get(position));
    }

    @Override
    public int getCount() {
        return mFragmentTitle.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitle.get(position);
    }
}
