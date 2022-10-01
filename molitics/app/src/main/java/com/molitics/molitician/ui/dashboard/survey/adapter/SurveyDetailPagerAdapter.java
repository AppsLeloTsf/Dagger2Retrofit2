package com.molitics.molitician.ui.dashboard.survey.adapter;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;

import com.molitics.molitician.GoogleAnalytics.GAEventTracking;
import com.molitics.molitician.R;
import com.molitics.molitician.UiMediator.Loader;
import com.molitics.molitician.error.ApiException;
import com.molitics.molitician.error.ServerException;
import com.molitics.molitician.interfaces.MOnClickListener;
import com.molitics.molitician.interfaces.PagerAdapterInterface;
import com.molitics.molitician.model.APIResponse;
import com.molitics.molitician.ui.dashboard.adapter.SurveyQuAdapter;
import com.molitics.molitician.ui.dashboard.survey.model.AllSurveyModel;
import com.molitics.molitician.ui.dashboard.survey.TakeSurveyHandler;
import com.molitics.molitician.ui.dashboard.survey.TakeSurveyPresenter;
import com.molitics.molitician.util.Constant;
import com.molitics.molitician.util.DialogClass;
import com.molitics.molitician.util.Util;

import java.util.ArrayList;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by rahul on 6/7/2017.
 */

public class SurveyDetailPagerAdapter extends PagerAdapter implements MOnClickListener, TakeSurveyPresenter.TakeSurveyView {

    Context mContext;
    SurveyQuAdapter surveyAdapter;
    ArrayList<AllSurveyModel> allSurveyModels = new ArrayList<>();
    TakeSurveyHandler takeSurveyHandler;
    int vote = -1;

    @BindView(R.id.close_button)
    ImageView close_button;
    @BindView(R.id.survey_name_txt)
    TextView survey_name_txt;
    @BindView(R.id.valid_till)
    TextView valid_till;

    RecyclerView survey_recycler;
    PagerAdapterInterface.CurrentInstance currentInstance;
    LinearLayoutManager linearLayoutManager;
    View layout;

    public void addSurveyArray(ArrayList<AllSurveyModel> allSurveyModels) {
        this.allSurveyModels = allSurveyModels;
        notifyDataSetChanged();
    }

    public SurveyDetailPagerAdapter(Context mContext, PagerAdapterInterface.CurrentInstance currentInstance) {
        this.mContext = mContext;
        this.currentInstance = currentInstance;
        takeSurveyHandler = new TakeSurveyHandler(this);
    }

    @Override
    public int getCount() {
        return allSurveyModels.size();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        layout = inflater.inflate(R.layout.adapter_survey_detail, container, false);
        ButterKnife.bind(this, layout);

        AllSurveyModel surveyModel = allSurveyModels.get(position);
        final int survey_id = surveyModel.getId();
        takeSurveyHandler.getSurveyDetail(survey_id);
        close_button.setOnClickListener(v -> currentInstance.closeDialog());
        if (!TextUtils.isEmpty(allSurveyModels.get(position).getSurveyQuestion()))
            survey_name_txt.setText(allSurveyModels.get(position).getSurveyQuestion());

        if (surveyModel.getCanReply() == 0) {
            if (surveyModel.getResponse() == null) {
                valid_till.setText(surveyModel.getValidTill());
            }
        } else
            valid_till.setText(surveyModel.getValidTill());
        layout.setTag(Constant.ID + survey_id);
        container.addView(layout, 0);
        return layout;
    }


    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((RelativeLayout) object);
    }

    @Override
    public void onMClick(int position) {
        vote = position;
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
        Util.validateError(mContext, apiException, null, null, null);
    }

    @Override
    public void onSubmitSurveyResponse(APIResponse apiResponse) {
        Loader.dismissProgressDialog();
        if (apiResponse.getStatus() == 2000) {
            currentInstance.surveyTaken(true);
            handleUi(apiResponse.getData().getNewsSurveyModel());
        }
    }

    private void getAdapterInstance(RecyclerView recyclerView) {
        SurveyQuAdapter loaclSurveyQuAdapter = (SurveyQuAdapter) recyclerView.getAdapter();

        loaclSurveyQuAdapter.setMyResponse(-1);
        loaclSurveyQuAdapter.setSelected(-1);
        loaclSurveyQuAdapter.setCanResponse(true);
    }

    private void handleUi(AllSurveyModel surveyDetailModel) {
        boolean canReply = true;

        View tag_view = currentInstance.getCurrentInstance(surveyDetailModel.getId());
        if (tag_view != null) {

            TextView tag_valid_till = tag_view.findViewById(R.id.valid_till);
            TextView tag_total_taken_view = tag_view.findViewById(R.id.total_taken_view);
            TextView submit_result = tag_view.findViewById(R.id.submit_result);
            TextView survey_question = tag_view.findViewById(R.id.survey_name_txt);
            ImageView share_survey = tag_view.findViewById(R.id.share_survey);
            survey_question.setText(surveyDetailModel.getSurveyQuestion());

            submit_result.setVisibility(View.VISIBLE);

            if (surveyDetailModel.getCanReply() == 0) {
                canReply = false;
                submit_result.setText(mContext.getString(R.string.change_opinion));
            } else {
                canReply = true;
                submit_result.setText(mContext.getString(R.string.submit));
            }

            String total_response_text = mContext.getString(R.string.total_response) + " : " + surveyDetailModel.getTotalResponse();
            tag_total_taken_view.setText(total_response_text);
            tag_valid_till.setText(surveyDetailModel.getValidTill());

            share_survey.setOnClickListener(v -> currentInstance.shareSurvey(surveyDetailModel.getId(), surveyDetailModel.getSurveyQuestion()));
            //set adapter
            survey_recycler = tag_view.findViewById(R.id.survey_recycler);
            linearLayoutManager = new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false);
            survey_recycler.setLayoutManager(linearLayoutManager);
            surveyAdapter = new SurveyQuAdapter(mContext, this, Constant.AppTheme.DEFAULT_TEXT);
            survey_recycler.setAdapter(surveyAdapter);
            if (surveyDetailModel.getMyResponse().getResponse() != null) {
                surveyAdapter.setMyResponse(surveyDetailModel.getMyResponse().getResponse());
            }
            surveyAdapter.addSurveyList(surveyDetailModel.getResponse(), canReply, surveyDetailModel.getTotalResponse());

            if (surveyDetailModel.getIsExpired() != null && surveyDetailModel.getIsExpired() == 0) {
                submit_result.setVisibility(View.VISIBLE);
            } else {
                submit_result.setVisibility(View.GONE);
            }
            submit_result.setOnClickListener(v -> {
                if (surveyDetailModel.getCanReply() == 0) {
                    vote = -1;
                    View local_tag_view = currentInstance.getCurrentInstance(currentInstance.getCurrentTag());
                    if (local_tag_view != null) {
                        getAdapterInstance(local_tag_view.findViewById(R.id.survey_recycler));

                    }
                    submit_result.setText(mContext.getString(R.string.submit));
                    surveyDetailModel.setCanReply(1);
                    /// change opinion click
                    setGAEvent(Constant.GoogleAnalyticsKey.SURVEY_CHANGE_OPINION + "  " + currentInstance.getCurrentTag());
                } else {
                    if (vote == -1) {
                        Toast.makeText(mContext, mContext.getString(R.string.select_one_option), Toast.LENGTH_LONG).show();
                    } else {
                        takeSurveyHandler.submitSurvey(currentInstance.getCurrentTag(), vote);
                        ///// submit survey click
                        setGAEvent(Constant.GoogleAnalyticsKey.SURVEY_SUBMIT + "  " + currentInstance.getCurrentTag());
                    }
                }
            });
        }
    }

    @Override
    public void onSubmitSurveyApiException(ApiException apiException) {
        DialogClass.showAlert(mContext, apiException.getMessage());
    }

    @Override
    public void onSubmitSurveyFormValidation(Map<String, String> errors) {

    }

    @Override
    public void onSubmitSurveyServerException(ServerException serverException) {

    }

    private void setGAEvent(String actionWithId) {
        if (mContext != null)
            GAEventTracking.googleEventTracker((Activity) mContext, Constant.GoogleAnalyticsKey.HOME_SURVEY, actionWithId);
    }
}
