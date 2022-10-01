package com.molitics.molitician.ui.dashboard.newsDetail.news_survey;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.molitics.molitician.ui.dashboard.survey.model.Response;
import com.molitics.molitician.ui.dashboard.survey.model.MyResponse;

import java.util.List;

/**
 * Created by rahul on 12/23/2016.
 */
public class SurveyModel implements Parcelable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("survey_question")
    @Expose
    private String surveyQuestion;
    @SerializedName("survey_details")
    @Expose
    private Integer surveyDetails;
    @SerializedName("valid_till")
    @Expose
    private String validTill;
    @SerializedName("my_response")
    @Expose
    private MyResponse myResponse;
    @SerializedName("total_response")
    @Expose
    private Integer totalResponse;
    @SerializedName("response")
    @Expose
    private List<Response> response = null;
    public final static Parcelable.Creator<SurveyModel> CREATOR = new Creator<SurveyModel>() {


        @SuppressWarnings({
                "unchecked"
        })
        public SurveyModel createFromParcel(Parcel in) {
            SurveyModel instance = new SurveyModel();
            instance.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
            instance.surveyQuestion = ((String) in.readValue((String.class.getClassLoader())));
            instance.surveyDetails = ((Integer) in.readValue((Integer.class.getClassLoader())));
            instance.validTill = ((String) in.readValue((String.class.getClassLoader())));
            instance.totalResponse = ((Integer) in.readValue((Integer.class.getClassLoader())));
            in.readList(instance.response, (Response.class.getClassLoader()));
            return instance;
        }

        public SurveyModel[] newArray(int size) {
            return (new SurveyModel[size]);
        }

    };

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

    public void setSurveyDetails(Integer surveyDetails) {
        this.surveyDetails = surveyDetails;
    }

    public String getValidTill() {
        return validTill;
    }

    public void setValidTill(String validTill) {
        this.validTill = validTill;
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

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(surveyQuestion);
        dest.writeValue(surveyDetails);
        dest.writeValue(validTill);
        dest.writeValue(myResponse);
        dest.writeValue(totalResponse);
        dest.writeList(response);
    }

    public int describeContents() {
        return 0;
    }

}
