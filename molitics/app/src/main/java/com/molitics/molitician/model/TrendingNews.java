package com.molitics.molitician.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class TrendingNews {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("heading")
    @Expose
    private String heading;
    @SerializedName("content")
    @Expose
    private String content;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("source")
    @Expose
    private String source;
    @SerializedName("source_logo")
    @Expose
    private String sourceLogo;
    @SerializedName("survey_id")
    @Expose
    private String surveyId;
    @SerializedName("display_type")
    @Expose
    private String displayType;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("approved_at")
    @Expose
    private String approvedAt;
    @SerializedName("time")
    @Expose
    private String time;

    /**
     *
     * @return
     * The id
     */
    public Integer getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The heading
     */
    public String getHeading() {
        return heading;
    }

    /**
     *
     * @param heading
     * The heading
     */
    public void setHeading(String heading) {
        this.heading = heading;
    }

    /**
     *
     * @return
     * The content
     */
    public String getContent() {
        return content;
    }

    /**
     *
     * @param content
     * The content
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     *
     * @return
     * The image_small_icon
     */
    public String getImage() {
        return image;
    }

    /**
     *
     * @param image
     * The image_small_icon
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     *
     * @return
     * The createdAt
     */
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     *
     * @param createdAt
     * The created_at
     */
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    /**
     *
     * @return
     * The source
     */
    public String getSource() {
        return source;
    }

    /**
     *
     * @param source
     * The source
     */
    public void setSource(String source) {
        this.source = source;
    }

    /**
     *
     * @return
     * The sourceLogo
     */
    public String getSourceLogo() {
        return sourceLogo;
    }

    /**
     *
     * @param sourceLogo
     * The source_logo
     */
    public void setSourceLogo(String sourceLogo) {
        this.sourceLogo = sourceLogo;
    }

    /**
     *
     * @return
     * The surveyId
     */
    public String getSurveyId() {
        return surveyId;
    }

    /**
     *
     * @param surveyId
     * The survey_id
     */
    public void setSurveyId(String surveyId) {
        this.surveyId = surveyId;
    }

    /**
     *
     * @return
     * The displayType
     */
    public String getDisplayType() {
        return displayType;
    }

    /**
     *
     * @param displayType
     * The display_type
     */
    public void setDisplayType(String displayType) {
        this.displayType = displayType;
    }

    /**
     *
     * @return
     * The updatedAt
     */
    public String getUpdatedAt() {
        return updatedAt;
    }

    /**
     *
     * @param updatedAt
     * The updated_at
     */
    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     *
     * @return
     * The approvedAt
     */
    public String getApprovedAt() {
        return approvedAt;
    }

    /**
     *
     * @param approvedAt
     * The approved_at
     */
    public void setApprovedAt(String approvedAt) {
        this.approvedAt = approvedAt;
    }

    /**
     *
     * @return
     * The time
     */
    public String getTime() {
        return time;
    }

    /**
     *
     * @param time
     * The time
     */
    public void setTime(String time) {
        this.time = time;
    }

}