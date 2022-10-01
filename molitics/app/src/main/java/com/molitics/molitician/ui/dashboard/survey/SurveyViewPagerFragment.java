package com.molitics.molitician.ui.dashboard.survey;


import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.molitics.molitician.R;
import com.molitics.molitician.customView.ZoomOutPageTransformer;
import com.molitics.molitician.interfaces.PagerAdapterInterface;
import com.molitics.molitician.ui.dashboard.branch_deep_link.BranchShareClass;
import com.molitics.molitician.ui.dashboard.survey.adapter.SurveyDetailPagerAdapter;
import com.molitics.molitician.ui.dashboard.survey.model.AllSurveyModel;
import com.molitics.molitician.util.Constant;
import com.molitics.molitician.util.ExtraUtils;
import com.molitics.molitician.util.MoliticsAppPermission;
import com.molitics.molitician.util.ShareScreenShot;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.molitics.molitician.util.Constant.IntentKey.SURVEY_DATA;

/**
 * Created by rahul on 6/7/2017.
 */
public class SurveyViewPagerFragment extends DialogFragment implements PagerAdapterInterface.CurrentInstance,
        BranchShareClass.DeepLinkCallBack {

    @BindView(R.id.survey_view_pager)
    ViewPager survey_view_pager;
    @BindView(R.id.preview)
    TextView preview;
    @BindView(R.id.next)
    TextView next;


    private ArrayList<AllSurveyModel> allSurveyModels;
    static SurveyViewPagerFragment dFragment;
    private int survey_position = 0;
    private Boolean isSurveyTaken = false;
    private SurveyDetailPagerAdapter surveyDetailPagerAdapter;


    public static DialogFragment getInstance(int position, ArrayList<AllSurveyModel> allSurveyModels) {

        //right.. if create singleton set current item not working
        dFragment = new SurveyViewPagerFragment();
        Bundle args = new Bundle();
        args.putSerializable(SURVEY_DATA, allSurveyModels);
        args.putInt(Constant.IntentKey.SURVEY_POSITION, position);
        dFragment.setArguments(args);
        return dFragment;
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void onStart() {
        super.onStart();
        Dialog d = getDialog();
        if (d != null) {
            int width = WindowManager.LayoutParams.MATCH_PARENT;
            int height = WindowManager.LayoutParams.MATCH_PARENT;
            d.getWindow().setLayout(width, height);
        }
    }

    @Override
    public void show(FragmentManager manager, String tag) {
        try {
            FragmentTransaction ft = manager.beginTransaction();
            ft.add(this, tag);
            ft.commit();
        } catch (IllegalStateException e) {
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color
                .TRANSPARENT));
        View primaryView = inflater.inflate(R.layout.fragment_survey_viewpager, container, false);
        ButterKnife.bind(this, primaryView);


        next.setTypeface(ExtraUtils.getRobotoBold(getContext()));
        preview.setTypeface(ExtraUtils.getRobotoBold(getContext()));

        Bundle mBundle = getArguments();

        allSurveyModels = (ArrayList<AllSurveyModel>) mBundle.getSerializable(SURVEY_DATA);
        survey_position = mBundle.getInt(Constant.IntentKey.SURVEY_POSITION);

        if (allSurveyModels.size() <= 1) {
            next.setVisibility(View.GONE);
            preview.setVisibility(View.GONE);
        } else {
            next.setVisibility(View.VISIBLE);
            primaryView.setVisibility(View.VISIBLE);
        }
        setViewPager();
        onViewPagerChangeHandler();
        return primaryView;
    }

    @OnClick(R.id.back)
    public void onBackPress() {
        closeDialog();
    }

    private void setViewPager() {
        surveyDetailPagerAdapter = new SurveyDetailPagerAdapter(getActivity(), this);
        survey_view_pager.setAdapter(surveyDetailPagerAdapter);
        survey_view_pager.setPageTransformer(true, new ZoomOutPageTransformer());
        survey_view_pager.setClipToPadding(false);
        survey_view_pager.setPageMargin(ExtraUtils.convertDpToPx(getContext(), -100));
        surveyDetailPagerAdapter.addSurveyArray(allSurveyModels);
        survey_view_pager.setCurrentItem(survey_position, true);
    }

    private void onViewPagerChangeHandler() {
        if (survey_view_pager != null) {
            survey_view_pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                }

                @Override
                public void onPageSelected(int position) {

                    if (position == 0) {
                        preview.setTextColor(getResources().getColor(R.color.preview_inactive));
                    } else {
                        preview.setTextColor(getResources().getColor(R.color.theme));
                    }
                    if (position == allSurveyModels.size() - 1) {
                        next.setTextColor(getResources().getColor(R.color.preview_inactive));
                    } else {
                        next.setTextColor(getResources().getColor(R.color.theme));
                    }
                }

                @Override
                public void onPageScrollStateChanged(int state) {
                }
            });
        }
    }

    @OnClick(R.id.next)
    public void onNextClick() {
        survey_view_pager.setCurrentItem(getItem(+1), true);
        preview.setTextColor(getResources().getColor(R.color.theme));
    }

    @OnClick(R.id.preview)
    public void onPreviewClick() {
        survey_view_pager.setCurrentItem(getPreItem(-1), true);
    }

    private int getItem(int i) {
        return survey_view_pager.getCurrentItem() + i;
    }

    private int getPreItem(int i) {
        if (survey_view_pager.getCurrentItem() != 0)
            return survey_view_pager.getCurrentItem() + i;
        else return 0;
    }

    @Override
    public View getCurrentInstance(int id) {
        return survey_view_pager.findViewWithTag(Constant.ID + id);
    }

    @Override
    public int getCurrentTag() {
        return allSurveyModels.get(survey_view_pager.getCurrentItem()).getId();
    }

    @Override
    public void closeDialog() {
        if (isSurveyTaken && getTargetFragment() != null)
            if (getActivity() != null)
                getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, getActivity().getIntent());
        dismiss();
    }

    @Override
    public void shareSurvey(int survey_id, String survey_question) {
        if (MoliticsAppPermission.checkWritePermission()) {
            BranchShareClass.generateShareLink(getActivity(), this, survey_question, "",
                    Constant.ShareCampaign.SURVEY, Constant.ShareLinkTag.SURVEY, String.valueOf(survey_id), Constant.ShareCampaign.SURVEY);
        } else {
            MoliticsAppPermission.requestStoragePermission(getActivity());
        }
    }

    @Override
    public void surveyTaken(Boolean taken) {
        isSurveyTaken = true;
    }


    @Override
    public void onGenerateShareLink(String full_txt, String url) {
        DialogFragment myFragment = (SurveyViewPagerFragment) getFragmentManager().findFragmentByTag("Dialog Fragment");
        if (myFragment != null) {

            if (!TextUtils.isEmpty(full_txt))
                ShareScreenShot.takeScreenshot(getActivity(), getString(R.string.media_of_politics) + full_txt, url);
            else
                ShareScreenShot.takeScreenshot(getActivity(), getString(R.string.media_of_politics), url);
        }
    }
}


