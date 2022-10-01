package com.molitics.molitician.model;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmModel;
import io.realm.annotations.Index;
import io.realm.annotations.RealmClass;

/**
 * Created by rahul on 31/10/17.
 */
@RealmClass
public class NewsVideoModel implements Parcelable, RealmModel {

    public NewsVideoModel() {
    }

    @Index
    @SerializedName("table")
    @Expose
    private String table;

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("heading")
    @Expose

    private String heading;
    @SerializedName("content")
    @Expose
    private String content;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("source")
    @Expose
    private String source;


    @SerializedName("source_id")
    @Expose
    private int source_id;
    @SerializedName("source_logo")
    @Expose
    private String sourceLogo;
    @SerializedName("survey_id")
    @Expose
    private Integer surveyId;
    @SerializedName("display_type")
    @Expose
    private int displayType;
    @SerializedName("approved_at")
    @Expose
    private String approvedAt;
    @SerializedName("video_link")
    @Expose
    private String videoLink;
    @SerializedName("type")
    @Expose
    private int type;
    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("link")
    @Expose
    private String link;
    @SerializedName("approved_at_time")
    @Expose
    private int approvedAtTime;
    public final static Parcelable.Creator<NewsVideoModel> CREATOR = new Creator<NewsVideoModel>() {


        @SuppressWarnings({
                "unchecked"
        })
        public NewsVideoModel createFromParcel(Parcel in) {
            return new NewsVideoModel(in);
        }

        public NewsVideoModel[] newArray(int size) {
            return (new NewsVideoModel[size]);
        }

    };

    protected NewsVideoModel(Parcel in) {
        this.id = ((int) in.readValue((int.class.getClassLoader())));
        this.heading = ((String) in.readValue((String.class.getClassLoader())));
        this.content = ((String) in.readValue((String.class.getClassLoader())));
        this.image = ((String) in.readValue((String.class.getClassLoader())));
        this.source = ((String) in.readValue((String.class.getClassLoader())));
        this.source_id = ((int) in.readValue((int.class.getClassLoader())));
        this.sourceLogo = ((String) in.readValue((String.class.getClassLoader())));
        this.surveyId = ((Integer) in.readValue((Intent.class.getClassLoader())));
        this.displayType = ((int) in.readValue((int.class.getClassLoader())));
        this.approvedAt = ((String) in.readValue((String.class.getClassLoader())));
        this.videoLink = ((String) in.readValue((String.class.getClassLoader())));
        this.type = ((int) in.readValue((int.class.getClassLoader())));
        this.time = ((String) in.readValue((String.class.getClassLoader())));
        this.link = ((String) in.readValue((String.class.getClassLoader())));
        this.approvedAtTime = ((int) in.readValue((int.class.getClassLoader())));
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHeading() {
        return heading;
    }


    public void setHeading(String heading) {
        this.heading = heading;

    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;

    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSourceLogo() {
        return sourceLogo;
    }

    public void setSourceLogo(String sourceLogo) {
        this.sourceLogo = sourceLogo;
    }

    public Integer getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(Integer surveyId) {
        this.surveyId = surveyId;
    }

    public int getDisplayType() {
        return displayType;
    }

    public void setDisplayType(int displayType) {
        this.displayType = displayType;
    }

    public String getApprovedAt() {
        return approvedAt;
    }

    public void setApprovedAt(String approvedAt) {
        this.approvedAt = approvedAt;
    }

    public String getVideoLink() {
        return videoLink;
    }

    public void setVideoLink(String videoLink) {
        this.videoLink = videoLink;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getApprovedAtTime() {
        return approvedAtTime;
    }

    public int getSource_id() {
        return source_id;
    }

    public void setSource_id(int source_id) {
        this.source_id = source_id;
    }


    public void setApprovedAtTime(int approvedAtTime) {
        this.approvedAtTime = approvedAtTime;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(heading);
        dest.writeValue(content);
        dest.writeValue(image);
        dest.writeValue(source);
        dest.writeValue(source_id);
        dest.writeValue(sourceLogo);
        dest.writeValue(surveyId);
        dest.writeValue(displayType);
        dest.writeValue(approvedAt);
        dest.writeValue(videoLink);
        dest.writeValue(type);
        dest.writeValue(time);
        dest.writeValue(link);
        dest.writeValue(approvedAtTime);
    }

    public int describeContents() {
        return 0;
    }
}
