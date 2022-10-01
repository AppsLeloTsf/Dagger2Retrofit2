package com.molitics.molitician.ui.dashboard.nationalNews.homeSurvey;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;

import com.molitics.molitician.R;
import com.molitics.molitician.error.ApiException;
import com.molitics.molitician.error.ServerException;
import com.molitics.molitician.interfaces.MOnClickListener;
import com.molitics.molitician.model.APIResponse;
import com.molitics.molitician.ui.dashboard.adapter.SurveyQuAdapter;
import com.molitics.molitician.ui.dashboard.branch_deep_link.BranchShareClass;
import com.molitics.molitician.ui.dashboard.nationalNews.interfacess.HomeSurveyNextInterface;
import com.molitics.molitician.ui.dashboard.survey.model.AllSurveyModel;
import com.molitics.molitician.ui.dashboard.survey.SurveyActivity;
import com.molitics.molitician.ui.dashboard.survey.TakeSurveyHandler;
import com.molitics.molitician.ui.dashboard.survey.TakeSurveyPresenter;
import com.molitics.molitician.ui.dashboard.survey.model.Response;
import com.molitics.molitician.util.Constant;
import com.molitics.molitician.util.ShareScreenShot;
import com.molitics.molitician.util.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;


public class HomeSurveyAdapter extends PagerAdapter implements
        MOnClickListener, TakeSurveyPresenter.TakeSurveyView, BranchShareClass.DeepLinkCallBack {

    private TakeSurveyHandler takeSurveyHandler;

    private List<AllSurveyModel> homeNewsSurveyList = new ArrayList<>();

    private Context mContext;
    private List<Response> response_list = new ArrayList<>();
    private int vote = -1;
    private SurveyQuAdapter surveyAdapter;
    private TextView takeSurveyButton;
    private HomeSurveyNextInterface homeSurveyNextInterface;
    private int position;

    @BindView(R.id.take_survey_button)
    TextView take_survey_button;
    @BindView(R.id.survey_recycler)
    RecyclerView survey_recycler;
    @BindView(R.id.survey_question_txt)
    TextView survey_question_txt;
    @BindView(R.id.see_all_survey)
    TextView see_all_survey;
    @BindView(R.id.response_count)
    TextView response_count;
    @BindView(R.id.end_time_count)
    TextView end_time_count;
    @BindView(R.id.survey_count_view)
    TextView survey_count_view;
    @BindView(R.id.survey_share_icon)
    ImageView survey_share_icon;

    public HomeSurveyAdapter(Context mContext, HomeSurveyNextInterface homeSurveyNextInterface) {
        this.mContext = mContext;
        takeSurveyHandler = new TakeSurveyHandler(this);
        this.homeSurveyNextInterface = homeSurveyNextInterface;
    }

    public void addSurveyList(List<AllSurveyModel> homeNewsSurvey) {
        this.homeNewsSurveyList = homeNewsSurvey;
        notifyDataSetChanged();
    }

    @Override
    public void onMClick(int position) {
        vote = response_list.get(position - 1).getId();
        surveyAdapter.setSelected(position);
    }

    @Override
    public void onSurveyDetail(APIResponse apiResponse) {
        if (apiResponse.getStatus() == 2000) {
            AllSurveyModel surveyDetailModel = apiResponse.getData().getNewsSurveyModel();
            handleUi(surveyDetailModel);
        }
    }

    @Override
    public void onSurveyApiException(ApiException apiException) {

    }

    @Override
    public void onSubmitSurveyResponse(APIResponse apiResponse) {
        if (apiResponse.getStatus() == 2000) {
            handleResponseUi(apiResponse.getData().getNewsSurveyModel());
        }
    }

    @Override
    public void onSubmitSurveyApiException(ApiException apiException) {

    }

    @Override
    public void onSubmitSurveyFormValidation(Map<String, String> errors) {

    }

    @Override
    public void onSubmitSurveyServerException(ServerException serverException) {
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.home_news_survey_layout, container, false);
        ButterKnife.bind(this, layout);

        this.position = position;
        vote = -1;

        AllSurveyModel homeNewsSurvey = homeNewsSurveyList.get(position);
        survey_question_txt.setText(homeNewsSurvey.getSurveyQuestion());

        surveyAdapter = new SurveyQuAdapter(mContext, this, Constant.AppTheme.WHITE_TEXT);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false);
        survey_recycler.setLayoutManager(linearLayoutManager);
        survey_recycler.setAdapter(surveyAdapter);

        boolean canReply = true;
        canReply = homeNewsSurvey.getCanReply() != 0;
        response_list = homeNewsSurvey.getResponse();
        if (homeNewsSurvey.getMyResponse() != null && homeNewsSurvey.getMyResponse().getResponse() != null) {
            surveyAdapter.setMyResponse(homeNewsSurvey.getMyResponse().getResponse());
        }
        surveyAdapter.clearSurveyList();
        surveyAdapter.addSurveyList(homeNewsSurvey.getResponse(), canReply, homeNewsSurvey.getTotalResponse());
        if (homeNewsSurvey.getTotalResponse() == 0 || homeNewsSurvey.getTotalResponse() == 1) {
            response_count.setText(homeNewsSurvey.getTotalResponse() + " Response");
        } else
            response_count.setText(homeNewsSurvey.getTotalResponse() + " Responses");


        takeSurveyButton = take_survey_button;
        if (homeNewsSurvey.getCanReply() == 0)
            if (position + 1 == getCount())
                take_survey_button.setVisibility(View.GONE);
            else
                take_survey_button.setText(R.string.next_survey);
        else take_survey_button.setText(R.string.take_survey);

        survey_count_view.setText((position + 1) + "/" + homeNewsSurveyList.size());

        take_survey_button.setOnClickListener(v -> {
            AllSurveyModel allSurveyModel = homeNewsSurveyList.get(position);
            if (surveyAdapter.getCanReply()) {
                if (vote != -1)
                    takeSurveyHandler.submitSurvey(allSurveyModel.getId(), vote);
                else Util.toastLong(mContext, mContext.getString(R.string.select_survey_option));
            } else
                homeSurveyNextInterface.onNextClick(position);
        });
        see_all_survey.setOnClickListener(v -> {
            Intent mIntent = new Intent(mContext, SurveyActivity.class);
            mIntent.putExtra(Constant.IntentKey.PAST_STATE_ID, ((Double) homeNewsSurvey.getState()).intValue());
            mContext.startActivity(mIntent);
        });
        survey_share_icon.setOnClickListener(v ->
                BranchShareClass.generateShareLink(mContext, this, "Molitics Survey", "",
                        Constant.ShareCampaign.SURVEY, Constant.ShareLinkTag.SURVEY, String.valueOf(homeNewsSurvey.getId()), Constant.ShareCampaign.SURVEY)
        );

        container.addView(layout, 0);
        return layout;
    }


    @Override
    public int getCount() {
        return homeNewsSurveyList.size();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((ConstraintLayout) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == (object);
    }


    private void handleUi(AllSurveyModel surveyDetailModel) {
        boolean canReply = true;
        if (surveyDetailModel.getCanReply() == 0) {
            canReply = false;
        }
        surveyAdapter.clearSurveyList();
        response_list = surveyDetailModel.getResponse();
        surveyAdapter.addSurveyList(surveyDetailModel.getResponse(), canReply, surveyDetailModel.getTotalResponse());
    }

    private void handleResponseUi(AllSurveyModel surveyDetailModel) {
        vote = -1;
        homeNewsSurveyList.set(position, surveyDetailModel);
        boolean canReply = false;
        ///  submit_result.setVisibility(View.GONE);
        if (surveyDetailModel.getTotalResponse() == 0 || surveyDetailModel.getTotalResponse() == 1) {
            response_count.setText(surveyDetailModel.getTotalResponse() + " Response");
        } else
            response_count.setText(surveyDetailModel.getTotalResponse() + " Responses");
        if (surveyDetailModel.getMyResponse() != null && surveyDetailModel.getMyResponse().getResponse() != null) {
            surveyAdapter.setMyResponse(surveyDetailModel.getMyResponse().getResponse());
        }
        surveyAdapter.clearSurveyList();
        surveyAdapter.addSurveyList(surveyDetailModel.getResponse(), canReply, surveyDetailModel.getTotalResponse());
        surveyAdapter.notifyDataSetChanged();
        if (position + 1 == homeNewsSurveyList.size())
            takeSurveyButton.setVisibility(View.GONE);
        else
            takeSurveyButton.setText(mContext.getString(R.string.next_survey));
    }

    @Override
    public void onGenerateShareLink(String full_txt, String url) {
        if (!TextUtils.isEmpty(full_txt))
            ShareScreenShot.takeScreenshot(mContext, mContext.getString(R.string.media_of_politics) + full_txt, url);
        else
            ShareScreenShot.takeScreenshot(mContext, mContext.getString(R.string.media_of_politics), url);
    }
}

