package com.molitics.molitician.ui.dashboard.survey.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.molitics.molitician.ui.dashboard.newsDetail.news_survey.SurveyModel;
import com.molitics.molitician.ui.dashboard.survey.model.MyResponse;
import com.molitics.molitician.ui.dashboard.survey.model.Response;

import java.io.Serializable;
import java.util.List;

/**
 * Created by rahul on 12/20/2016.
 */

public class AllSurveyModel implements Serializable {

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
    private SurveyModel surveyModelDetails;
    @SerializedName("country")
    @Expose
    private Object country;
    @SerializedName("state")
    @Expose
    private Object state;
    @SerializedName("city")
    @Expose
    private Object city;
    @SerializedName("valid_till")
    @Expose
    private String validTill;
    @SerializedName("can_reply")
    @Expose
    private int canReply;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("my_response")
    @Expose
    private MyResponse my_response;
    @SerializedName("valid_till_ms")
    @Expose
    private Long valid_till_ms;
    @SerializedName("total_response")
    @Expose
    private Integer totalResponse;

    @SerializedName("response")
    @Expose
    private List<Response> response;
    @SerializedName("is_expired")
    @Expose
    private Integer isExpired;

    public MyResponse getMy_response() {
        return my_response;
    }

    public void setMy_response(MyResponse my_response) {
        this.my_response = my_response;
    }

    public Integer getIsExpired() {
        return isExpired;
    }

    public void setIsExpired(Integer isExpired) {
        this.isExpired = isExpired;
    }

    public void setResponse(List<Response> response) {
        this.response = response;
    }

    public List<Response> getResponse() {
        return response;
    }


    public Integer getTotalResponse() {
        return totalResponse;
    }

    public void setTotalResponse(Integer totalResponse) {
        this.totalResponse = totalResponse;
    }

    public Long getValid_till_ms() {
        return valid_till_ms;
    }

    public void setValid_till_ms(Long valid_till_ms) {
        this.valid_till_ms = valid_till_ms;
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

    public SurveyModel getSurveyModelDetails() {
        return surveyModelDetails;
    }

    public void setSurveyModelDetails(SurveyModel surveyModelDetails) {
        this.surveyModelDetails = surveyModelDetails;
    }

    public Object getCountry() {
        return country;
    }

    public void setCountry(Object country) {
        this.country = country;
    }

    public Object getState() {
        return state;
    }

    public void setState(Object state) {
        this.state = state;
    }

    public Object getCity() {
        return city;
    }

    public void setCity(Object city) {
        this.city = city;
    }

    public String getValidTill() {
        return validTill;
    }

    public void setValidTill(String validTill) {
        this.validTill = validTill;
    }

    public int getCanReply() {
        return canReply;
    }

    public void setCanReply(int canReply) {
        this.canReply = canReply;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public MyResponse getMyResponse() {
        return my_response;
    }

    public void setMyResponse(MyResponse response) {
        this.my_response = my_response;
    }

}
