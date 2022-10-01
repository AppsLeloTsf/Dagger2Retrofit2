package com.molitics.molitician.model;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmModel;
import io.realm.annotations.RealmClass;

/**
 * Created by rahul on 02/11/17.
 */
@RealmClass
public class ArticleModel implements Parcelable, RealmModel {

    public ArticleModel() {
    }

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
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("author_name")
    @Expose
    private String author_name;
    @SerializedName("author_id")
    @Expose
    private Integer authorId;

    public Integer getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Integer authorId) {
        this.authorId = authorId;
    }

    public final static Parcelable.Creator<ArticleModel> CREATOR = new Parcelable.Creator<ArticleModel>() {


        @SuppressWarnings({
                "unchecked"
        })
        public ArticleModel createFromParcel(Parcel in) {
            return new ArticleModel(in);
        }

        public ArticleModel[] newArray(int size) {
            return (new ArticleModel[size]);
        }

    };

    protected ArticleModel(Parcel in) {
        this.id = ((int) in.readValue((int.class.getClassLoader())));
        this.heading = ((String) in.readValue((String.class.getClassLoader())));
        this.content = ((String) in.readValue((String.class.getClassLoader())));
        this.image = ((String) in.readValue((String.class.getClassLoader())));
        this.source = ((String) in.readValue((String.class.getClassLoader())));
        this.sourceLogo = ((String) in.readValue((String.class.getClassLoader())));
        this.surveyId = ((Integer) in.readValue((Intent.class.getClassLoader())));
        this.displayType = ((int) in.readValue((int.class.getClassLoader())));
        this.approvedAt = ((String) in.readValue((String.class.getClassLoader())));
        this.videoLink = ((String) in.readValue((String.class.getClassLoader())));
        this.type = ((int) in.readValue((int.class.getClassLoader())));
        this.time = ((String) in.readValue((String.class.getClassLoader())));
        this.link = ((String) in.readValue((String.class.getClassLoader())));
        this.approvedAtTime = ((int) in.readValue((int.class.getClassLoader())));
        this.location = ((String) in.readValue(String.class.getClassLoader()));
        this.author_name = ((String) in.readValue(String.class.getClassLoader()));
        this.authorId = ((Integer) in.readValue(Integer.class.getClassLoader()));
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

    public void setApprovedAtTime(int approvedAtTime) {
        this.approvedAtTime = approvedAtTime;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(heading);
        dest.writeValue(content);
        dest.writeValue(image);
        dest.writeValue(source);
        dest.writeValue(sourceLogo);
        dest.writeValue(surveyId);
        dest.writeValue(displayType);
        dest.writeValue(approvedAt);
        dest.writeValue(videoLink);
        dest.writeValue(type);
        dest.writeValue(time);
        dest.writeValue(link);
        dest.writeValue(approvedAtTime);
        dest.writeValue(location);
        dest.writeValue(author_name);
        dest.writeValue(authorId);
    }

    public int describeContents() {
        return 0;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAuthor_name() {
        return author_name;
    }

    public void setAuthor_name(String author_name) {
        this.author_name = author_name;
    }

    public static Creator<ArticleModel> getCREATOR() {
        return CREATOR;
    }
}
