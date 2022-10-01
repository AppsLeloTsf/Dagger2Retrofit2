package com.molitics.molitician.ui.dashboard.newsDetail.news_survey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.molitics.molitician.ui.dashboard.survey.model.Response;
import com.molitics.molitician.ui.dashboard.survey.model.MyResponse;

import java.io.Serializable;
import java.util.List;

/**
 * Created by rahul on 12/26/2016.
 */

public class NewsSurveyModel implements Serializable {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("survey_question")
    @Expose
    private String surveyQuestion;

    @SerializedName("valid_till")
    @Expose
    private String validTill;
    @SerializedName("survey_type")
    @Expose
    private String surveyType;
    @SerializedName("my_response")
    @Expose
    private MyResponse myResponse;
    @SerializedName("total_response")
    @Expose
    private Integer totalResponse;
    @SerializedName("can_reply")
    @Expose
    private Integer can_reply;

    @SerializedName("response")
    @Expose
    private List<Response> response = null;

    @SerializedName("is_expired")
    @Expose
    private Integer isExpired;

    public Integer getIsExpired() {
        return isExpired;
    }

    public void setIsExpired(Integer isExpired) {
        this.isExpired = isExpired;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSurveyQuestion() {
        return surveyQuestion;
    }

    public void setSurveyQuestion(String surveyQuestion) {
        this.surveyQuestion = surveyQuestion;
    }

    public String getValidTill() {
        return validTill;
    }

    public void setValidTill(String validTill) {
        this.validTill = validTill;
    }

    public String getSurveyType() {
        return surveyType;
    }

    public void setSurveyType(String surveyType) {
        this.surveyType = surveyType;
    }

    public MyResponse getMyResponse() {
        return myResponse;
    }

    public void setMyResponse(MyResponse myResponse) {
        this.myResponse = myResponse;
    }

    public Integer getTotalResponse() {
        return totalResponse;
    }

    public void setTotalResponse(Integer totalResponse) {
        this.totalResponse = totalResponse;
    }

    public List<Response> getResponse() {
        return response;
    }

    public void setResponse(List<Response> response) {
        this.response = response;
    }

    public Integer getCan_reply() {
        return can_reply;
    }

    public void setCan_reply(Integer can_reply) {
        this.can_reply = can_reply;
    }

}
