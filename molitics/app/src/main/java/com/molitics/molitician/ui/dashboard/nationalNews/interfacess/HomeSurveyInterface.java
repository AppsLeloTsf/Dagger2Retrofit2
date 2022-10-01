package com.molitics.molitician.ui.dashboard.nationalNews.interfacess;

import com.molitics.molitician.ui.dashboard.survey.model.AllSurveyModel;

import java.util.ArrayList;

/**
 * Created by om on 16/02/18.
 */

public interface HomeSurveyInterface {
    void onSurveyClick(int position, ArrayList<AllSurveyModel> allSurveyModels);

    void shareSurvey(int survey_id, String title);
}
