package com.molitics.molitician.ui.dashboard.survey.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by lenovo on 1/9/2017.
 */

public class SurveyDetailModel {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("survey_type")
    @Expose
    private String surveyType;
    @SerializedName("survey_question")
    @Expose
    private String surveyQuestion;
    @SerializedName("survey_details")
    @Expose
    private Object surveyDetails;
    @SerializedName("valid_till")
    @Expose
    private String validTill;
    @SerializedName("options")
    @Expose
    private List<Option> options = null;
    @SerializedName("can_reply")
    @Expose
    private Integer canReply;
    @SerializedName("my_response")
    @Expose
    private MyResponse myResponse;
    @SerializedName("total_response")
    @Expose
    private Integer totalResponse;

    public Integer getTotalResponse() {
        return totalResponse;
    }

    public void setTotalResponse(Integer totalResponse) {
        this.totalResponse = totalResponse;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSurveyType() {
        return surveyType;
    }

    public void setSurveyType(String surveyType) {
        this.surveyType = surveyType;
    }

    public String getSurveyQuestion() {
        return surveyQuestion;
    }

    public void setSurveyQuestion(String surveyQuestion) {
        this.surveyQuestion = surveyQuestion;
    }

    public Object getSurveyDetails() {
        return surveyDetails;
    }

    public void setSurveyDetails(Object surveyDetails) {
        this.surveyDetails = surveyDetails;
    }

    public String getValidTill() {
        return validTill;
    }

    public void setValidTill(String validTill) {
        this.validTill = validTill;
    }

    public List<Option> getOptions() {
        return options;
    }

    public void setOptions(List<Option> options) {
        this.options = options;
    }

    public Integer getCanReply() {
        return canReply;
    }

    public void setCanReply(Integer canReply) {
        this.canReply = canReply;
    }

    public MyResponse getMyResponse() {
        return myResponse;
    }

    public void setMyResponse(MyResponse myResponse) {
        this.myResponse = myResponse;
    }
}
