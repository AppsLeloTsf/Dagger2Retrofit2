package com.molitics.molitician.ui.dashboard.survey;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.View;


import com.molitics.molitician.R;
import com.molitics.molitician.error.ApiException;
import com.molitics.molitician.error.ServerException;
import com.molitics.molitician.interfaces.InitializeServerRequest;
import com.molitics.molitician.model.APIResponse;
import com.molitics.molitician.util.Constant;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Rahul on 10/19/2016.
 */

public class SurveyDetailActivity extends AppCompatActivity implements InitializeServerRequest, SurveyDetailPresenter.SurveyDetailsViews {

    @BindView(R.id.my_toolbar)
    Toolbar my_toolbar;

    private int survey_id = 0;
    private SurveyDetailHandler surveyDetailHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_detail);
        ButterKnife.bind(this);
        surveyDetailHandler = new SurveyDetailHandler(this);
        setSupportActionBar(my_toolbar);
        getSupportActionBar().setTitle("");
        Intent my_intent = getIntent();
        survey_id = my_intent.getIntExtra(Constant.IntentKey.SURVEY_ID_INTENT, 0);

        Bundle set_bundle = new Bundle();
        if (survey_id != 0) {
            set_bundle.putInt(Constant.IntentKey.SURVEY_ID_INTENT, survey_id);
            showHeader(true, getString(R.string.survey_take));
            replaceFragment(new TakeSurveyFragment(), set_bundle, true, false);
        } else {
            showHeader(true, getString(R.string.survey));
        }
    }

    //replace fragment
    public void replaceFragment(Fragment fragment, Bundle bundle, boolean addToBackStack, boolean anim) {
        if (bundle != null) fragment.setArguments(bundle);
        String tag = fragment.getClass().getSimpleName();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        ft.replace(R.id.container_frame, fragment, tag);
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
            if (getFragmentStart() instanceof TakeSurveyFragment) {
                if (count == 1) {
                    finish();
                } else {
                    showHeader(true, getString(R.string.survey));
                }
            }
            getSupportFragmentManager().popBackStack();
        } else {
            finish();
        }
    }

    public void showHeader(Boolean showHeader, String headerText) {
        if (showHeader) {
            my_toolbar.setVisibility(View.VISIBLE);
            my_toolbar.setTitleTextColor(ContextCompat.getColor(this,R.color.hard_white));
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setTitle(headerText);
            my_toolbar.setNavigationContentDescription("BACK");
            my_toolbar.setNavigationOnClickListener(v -> goBack());
        } else {
            my_toolbar.setVisibility(View.GONE);
        }
    }

    public Fragment getFragmentStart() {
        return getSupportFragmentManager().findFragmentById(R.id.container_frame);
    }

    @Override
    public void onBackPressed() {
        goBack();
    }

    @Override
    public void createServerRequest(int tag) {

        switch (tag) {
            case Constant.RequestTag.SURVEY_DETAIL:
                surveyDetailHandler.hitSurveyDetailRequest(survey_id);
                break;
        }
    }

    @Override
    public void onSurveyDetailResponse(APIResponse apiResponse) {
    }

    @Override
    public void onSurveyDetailApiException(ApiException apiException) {
    }

    @Override
    public void onSurveyDetailServerException(ServerException apiException) {
    }

    @Override
    public void onSurveyDetailFormValidation(Map<String, String> errors) {
    }
}
