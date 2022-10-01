package com.molitics.molitician.ui.dashboard.leader;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

import static com.molitics.molitician.util.Constant.From.VISIT_LEADER;

/**
 * Created by rahul on 6/20/2017.
 */

public class LeaderParentAdapter extends FragmentPagerAdapter {

    private List<String> mFragmentTitle;
    private List<Integer> mUrl;

    public LeaderParentAdapter(FragmentManager fm, List<String> mFragmentTitle, List<Integer> mUrl) {
        super(fm);
        this.mFragmentTitle = mFragmentTitle;
        this.mUrl = mUrl;
    }

    @Override
    public Fragment getItem(int position) {
        return LeaderView.getInstance(mUrl.get(position),VISIT_LEADER);
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
