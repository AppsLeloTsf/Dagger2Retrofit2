package com.molitics.molitician.ui.dashboard.survey.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by rahul on 1/9/2017.
 */

public class SurveyResult {


    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("survey_question")
    @Expose
    private String surveyQuestion;
    @SerializedName("survey_details")
    @Expose
    private Object surveyDetails;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("city")
    @Expose
    private Object city;
    @SerializedName("valid_till")
    @Expose
    private String validTill;
    @SerializedName("survey_type")
    @Expose
    private String surveyType;
    @SerializedName("my_response")
    @Expose
    private String myResponse;
    @SerializedName("total_response")
    @Expose
    private Integer totalResponse;
    @SerializedName("location")
    @Expose
    private String location;

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

    public Object getSurveyDetails() {
        return surveyDetails;
    }

    public void setSurveyDetails(Object surveyDetails) {
        this.surveyDetails = surveyDetails;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
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

    public String getSurveyType() {
        return surveyType;
    }

    public void setSurveyType(String surveyType) {
        this.surveyType = surveyType;
    }

    public String getMyResponse() {
        return myResponse;
    }

    public void setMyResponse(String myResponse) {
        this.myResponse = myResponse;
    }

    public Integer getTotalResponse() {
        return totalResponse;
    }

    public void setTotalResponse(Integer totalResponse) {
        this.totalResponse = totalResponse;
    }


    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

}
