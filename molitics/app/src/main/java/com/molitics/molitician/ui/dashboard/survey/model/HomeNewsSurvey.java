package com.molitics.molitician.ui.dashboard.survey.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by rahul on 16/02/18.
 */

public class HomeNewsSurvey {

    @SerializedName("survey_question")
    @Expose
    String survey;
    @SerializedName("id")
    @Expose
    int survey_id;
    @SerializedName("valid_till")
    @Expose
    String valid_till;
    @SerializedName("can_reply")
    @Expose
    Integer can_reply;
    @SerializedName("response")
    @Expose
    Response response;

    public String getValid_till() {
        return valid_till;
    }

    public void setValid_till(String valid_till) {
        this.valid_till = valid_till;
    }

    public Integer getCan_reply() {
        return can_reply;
    }

    public void setCan_reply(Integer can_reply) {
        this.can_reply = can_reply;
    }

    public String getSurvey() {
        return survey;
    }

    public void setSurvey(String survey) {
        this.survey = survey;
    }

    public int getSurvey_id() {
        return survey_id;
    }

    public void setSurvey_id(int survey_id) {
        this.survey_id = survey_id;
    }
}
