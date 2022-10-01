package com.molitics.molitician.ui.dashboard.userProfile;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.View;

import com.molitics.molitician.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by rahul on 1/12/2017.
 */

public class EditUserProfileActivity extends AppCompatActivity {


    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile_edit);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        Bundle bundle = new Bundle();
        showHeader(true, getString(R.string.edit_profile));
        replaceFragment(new UserProfileEditFragment(), bundle, true, false);
    }


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
            }
            getSupportFragmentManager().popBackStack();
        } else {
            finish();
        }
    }

    public void showHeader(Boolean showHeader, String headerText) {
        if (showHeader) {
            toolbar.setVisibility(View.VISIBLE);

            toolbar.setTitleTextColor(ContextCompat.getColor(this,R.color.follow_leader_white));

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setTitle(headerText);
            toolbar.setNavigationContentDescription("BACK");

            toolbar.setNavigationOnClickListener(v -> goBack());


        } else {
            toolbar.setVisibility(View.GONE);
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

