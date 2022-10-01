package com.molitics.molitician.interfaces;

import android.view.View;

/**
 * Created by rahul on 6/9/2017.
 */

public class PagerAdapterInterface {


    public interface CurrentInstance {
        View getCurrentInstance(int id);

        int getCurrentTag();

        void closeDialog();

        void shareSurvey(int survey_id, String survey_question);

        void surveyTaken(Boolean taken);

    }
}
