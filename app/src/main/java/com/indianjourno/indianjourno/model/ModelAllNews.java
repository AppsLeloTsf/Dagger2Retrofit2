package com.indianjourno.indianjourno.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.indianjourno.indianjourno.utils.DateUtils;

public class ModelAllNews{
    @SerializedName("news_id")
    @Expose
    private String newsId;
    @SerializedName("cat_id")
    @Expose
    private String catId;
    @SerializedName("news_title")
    @Expose
    private String newsTitle;
    @SerializedName("news_schedule")
    @Expose
    private String newsSchedule;
    @SerializedName("news_reporter")
    @Expose
    private String newsReporter;
    @SerializedName("news_photo")
    @Expose
    private String newsPhoto;
    @SerializedName("news_content")
    @Expose
    private String newsContent;

    public String getNewsId() {
        return newsId;
    }

    public void setNewsId(String newsId) {
        this.newsId = newsId;
    }

    public String getCatId() {
        return catId;
    }

    public void setCatId(String catId) {
        this.catId = catId;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    public String getNewsSchedule() {
//        yyyy_MM_dd_HH_mm_ss
        return DateUtils.yyyy_MM_dd_HH_mm_ss(newsSchedule);
    }

    public void setNewsSchedule(String newsSchedule) {
        this.newsSchedule = newsSchedule;
    }

    public String getNewsReporter() {
        return newsReporter;
    }

    public void setNewsReporter(String newsReporter) {
        this.newsReporter = newsReporter;
    }

    public String getNewsPhoto() {
        return newsPhoto;
    }

    public void setNewsPhoto(String newsPhoto) {
        this.newsPhoto = newsPhoto;
    }

    public String getNewsContent() {
        return newsContent;
    }

    public void setNewsContent(String newsContent) {
        this.newsContent = newsContent;
    }
}
