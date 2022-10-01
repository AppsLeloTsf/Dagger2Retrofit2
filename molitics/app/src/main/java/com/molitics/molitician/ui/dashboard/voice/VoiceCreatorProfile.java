package com.molitics.molitician.ui.dashboard.voice;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.molitics.molitician.BaseActivity;
import com.molitics.molitician.R;
import com.molitics.molitician.databinding.ActivityUserProfileEditBinding;
import com.molitics.molitician.ui.dashboard.dashboard.dashboardView.DashBoardViewModel;
import com.molitics.molitician.ui.dashboard.userProfile.UserProfileEditFragment;
import com.molitics.molitician.ui.dashboard.userProfile.UserProfileFragment;
import com.molitics.molitician.util.Constant;

/**
 * Created by rahul on 01/12/17.
 */

public class VoiceCreatorProfile extends BaseActivity<ActivityUserProfileEditBinding, DashBoardViewModel> {

    ActivityUserProfileEditBinding userProfileEditBinding;

    @Override
    public int getBindingVariable() {
        return 0;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_user_profile_edit;
    }

    @Override
    public DashBoardViewModel getViewModel() {
        return null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userProfileEditBinding = getViewDataBinding();

        setSupportActionBar(userProfileEditBinding.toolbar);

        Intent mIntent = getIntent();

        Bundle bundle = new Bundle();
        bundle.putInt(Constant.IntentKey.USER_ID, mIntent.getIntExtra(Constant.IntentKey.USER_ID, 0));
        showHeader(true, getString(R.string.profile));
        replaceFragment(new UserProfileFragment(), bundle, false, false);
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

    public void goBack() {

        FragmentManager fragmentManager = getSupportFragmentManager();
        int count = fragmentManager.getBackStackEntryCount();
        if (count > 0) {
            if (getFragmentStart() instanceof UserProfileEditFragment) {
                finish();
            }/* else if (getFragmentStart() instanceof EditInfoFragment || getFragmentStart() instanceof PartListView) {
                showHeader(true, "User Profile");
            }*/
            getSupportFragmentManager().popBackStack();
        } else {
            finish();
        }
    }


    public void showHeader(Boolean showHeader, String headerText) {
        if (showHeader) {
            userProfileEditBinding.toolbar.setVisibility(View.VISIBLE);

            userProfileEditBinding.toolbar.setTitleTextColor(ContextCompat.getColor(this,R.color.follow_leader_white));

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setTitle(headerText);
            //toolbar_account.setNavigationIcon(getResources().getDrawable(R.drawable.toolbar_back));
            userProfileEditBinding.toolbar.setNavigationContentDescription("BACK");

            userProfileEditBinding.toolbar.setNavigationOnClickListener(v -> goBack());


        } else {
            userProfileEditBinding.toolbar.setVisibility(View.GONE);
        }
    }

    public Fragment getFragmentStart() {
        return getSupportFragmentManager().findFragmentById(R.id.home_container);
    }

    @Override
    public void onBackPressed() {
        goBack();
    }

}
