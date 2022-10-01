package com.molitics.molitician.ui.dashboard.election.upcoming_election.upcomig_parties;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.View;

import com.molitics.molitician.BasicAcivity;
import com.molitics.molitician.R;
import com.molitics.molitician.util.Constant;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by rahul on 1/12/2017.
 */

public class UpcomingPartyActivity extends BasicAcivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upcomining_party);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        Intent mIntent = getIntent();
        int election_id = mIntent.getIntExtra(Constant.IntentKey.ELECTION_ID, 0);
        Bundle mBundle = new Bundle();
        mBundle.putInt(Constant.IntentKey.ELECTION_ID, election_id);
        showHeader(true, getString(R.string.participating_parties));
        replaceFragment(new UpcomingPartyFragment(), mBundle, true, false);
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
            toolbar.setTitleTextColor(ContextCompat.getColor(this,R.color.hard_white));
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setTitle(headerText);
            //toolbar_account.setNavigationIcon(getResources().getDrawable(R.drawable.toolbar_back));
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
            if (getFragmentView() instanceof UpcomingPartyFragment) {
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

    public Fragment getFragmentView() {
        return getSupportFragmentManager().findFragmentById(R.id.home_container);
    }

}
