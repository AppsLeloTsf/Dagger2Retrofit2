package com.molitics.molitician.ui.dashboard.election.past_election.past_states;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.view.View;

import com.google.android.material.tabs.TabLayout;
import com.molitics.molitician.BasicAcivity;
import com.molitics.molitician.R;
import com.molitics.molitician.ui.dashboard.election.past_election.about_constituency.AboutConstituencyFragment;
import com.molitics.molitician.ui.dashboard.election.past_election.about_constituency.AboutConstituencyMLAFragment;
import com.molitics.molitician.ui.dashboard.leader.newleaderprofile.NewLeaderProfileAdapter;
import com.molitics.molitician.util.Constant;
import com.molitics.molitician.util.PrefUtil;
import com.molitics.molitician.util.Util;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by rahul on 1/10/2017.
 */

public class ElectionResultDetailActivity extends BasicAcivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.view_pager)
    ViewPager view_pager;
    @BindView(R.id.tab_layout)
    TabLayout tab_layout;

    private int state_id = 0;
    private int election_id = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_state_headquarter);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        showHeader(true, getResources().getString(R.string.election_results_header));
        toolbar.setNavigationOnClickListener(v -> goBack());
        Intent intent = getIntent();
        election_id = intent.getIntExtra(Constant.IntentKey.ELECTION_ID, 0);
        state_id = intent.getIntExtra(Constant.IntentKey.PAST_STATE_ID, 0);
        setUi();
    }

    public ArrayList<Fragment> setLeaderProfileFragment() {
        ArrayList<Fragment> mFragment = new ArrayList<>();
        mFragment.add(DeclaredStateResultFragment.getInstance(election_id, state_id));
        mFragment.add(AboutConstituencyFragment.getInstance(election_id, state_id, PrefUtil.getInt(Constant.PreferenceKey.DEFAULT_MP_VALUE), 0, Constant.IntentKey.PAST_MP));
        if (state_id != 0)
            mFragment.add(AboutConstituencyMLAFragment.getInstance(election_id, state_id, 0, PrefUtil.getInt(Constant.PreferenceKey.DEFAULT_MLA_VALUE), Constant.IntentKey.PAST_MLA));
        return mFragment;
    }

    void setUi() {
        NewLeaderProfileAdapter profileAdapter = new NewLeaderProfileAdapter(getSupportFragmentManager(), setLeaderProfileFragment(), setElectionResultTitle());
        view_pager.setAdapter(profileAdapter);
        tab_layout.setupWithViewPager(view_pager);
        view_pager.setOffscreenPageLimit(2);
    }

    //replace fragment for main pages
    public void replaceFragment(Fragment fragment, Bundle bundle, boolean addToBackStack, boolean anim) {
        if (bundle != null) fragment.setArguments(bundle);
        String tag = fragment.getClass().getSimpleName();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.home_container, fragment, tag);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragment.setRetainInstance(true);
        if (addToBackStack)
            ft.addToBackStack(tag);
        try {
            ft.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            ft.commitAllowingStateLoss();
        }
    }

    public void showHeader(Boolean showHeader, String headerText) {
        if (showHeader) {
            toolbar.setVisibility(View.VISIBLE);
            toolbar.setTitleTextColor(ContextCompat.getColor(this,R.color.follow_leader_white));
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setTitle(headerText);
            toolbar.setNavigationContentDescription(getString(R.string.back));
            toolbar.setNavigationOnClickListener(v -> goBack());
        } else {
            toolbar.setVisibility(View.GONE);
        }
    }

    public void goBack() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        int count = fragmentManager.getBackStackEntryCount();
        if (count > 0) {
            if (getFragmentCandidate() instanceof DeclaredStateResultFragment) {
                finish();
            }
            getSupportFragmentManager().popBackStack();
        } else {
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        goBack();
    }

    public Fragment getFragmentCandidate() {
        return getSupportFragmentManager().findFragmentById(R.id.home_container);
    }


    public ArrayList<String> setElectionResultTitle() {
        ArrayList<String> mTitle = new ArrayList<>();
        mTitle.add(getString(R.string.overall_result));
        mTitle.add(getString(R.string.mp_constituency));
        mTitle.add(getString(R.string.mla_constituency));
        return mTitle;
    }

}

