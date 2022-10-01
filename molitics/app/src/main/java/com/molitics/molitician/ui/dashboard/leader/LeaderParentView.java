package com.molitics.molitician.ui.dashboard.leader;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.molitics.molitician.MolticsApplication;
import com.molitics.molitician.R;
import com.molitics.molitician.util.Util;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by rahul on 6/20/2017.
 */

public class LeaderParentView extends Fragment /*implements LeaderView.LoadLeaderData*/ {

    @BindView(R.id.tab_layout)
    TabLayout tab_layout;
    @BindView(R.id.leader_view_pager)
    ViewPager leader_view_pager;

    static FloatingActionButton filter_float_btn;
    private View mView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_leader_parent_view, container, false);
        ButterKnife.bind(this, mView);
        getToolBar();

        filter_float_btn = mView.findViewById(R.id.filter_float_btn);

        LeaderParentAdapter leaderParentAdapter = new LeaderParentAdapter(getChildFragmentManager(), getLeaderFragmentTitle(), Util.getLeaderDisplayType());
        leader_view_pager.setAdapter(leaderParentAdapter);
        //leaderParentAdapter.setFragment(Util.getLeaderFragmentTitle(), Util.getLeaderDisplayType());
        leader_view_pager.setOffscreenPageLimit(2);
        tab_layout.setupWithViewPager(leader_view_pager);

        leader_view_pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        // leader_view_pager.setCurrentItem(2);

        return mView;
    }

    private void getToolBar() {
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        RelativeLayout center_toolbar_rl = toolbar.findViewById(R.id.center_toolbar_rl);
        if (center_toolbar_rl != null)
            center_toolbar_rl.removeAllViews();
    }

    @OnClick(R.id.filter_float_btn)
    public void onFilterClick() {
        leader_view_pager.setCurrentItem(1, true);
    }

    public ViewPager getViewPager() {
        if (null != mView) {
            leader_view_pager = mView.findViewById(R.id.leader_view_pager);
        }
        return leader_view_pager;
    }

/*    @Override
    public void onLeaderDataResponse(ArrayList<AllLeaderModel> allLeaderModel) {

        //  detailNewsAdapter.addNews(newsDetailModel);

        Fragment current_fragment = getActivity().getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.leader_view_pager + ":" + leader_view_pager.getCurrentItem());
        // based on the current position you can then cast the page to the correct
        // class and call the method:
        if (current_fragment != null) {
            ((LeaderView) current_fragment).setDataResponse(allLeaderModel);
        }
    }*/

    public  ArrayList<String> getLeaderFragmentTitle() {
        ArrayList<String> fragment_title = new ArrayList<>();
        fragment_title.add(getString(R.string.national_leaders));
        fragment_title.add(getString(R.string.local_leaders));
        fragment_title.add(getString(R.string.state_leaders));
        return fragment_title;
    }
}
