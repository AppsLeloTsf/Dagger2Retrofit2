package com.ca_dreamers.cadreamers.models.my_orders.course_details;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("youtube_url")
    @Expose
    private String youtubeUrl;
    @SerializedName("short_desc")
    @Expose
    private String shortDesc;
    @SerializedName("language")
    @Expose
    private String language;
    @SerializedName("thumb_url")
    @Expose
    private String thumbUrl;
    @SerializedName("order_id")
    @Expose
    private String orderId;
    @SerializedName("rating")
    @Expose
    private String rating;
    @SerializedName("author")
    @Expose
    private String author;
    @SerializedName("total_videos")
    @Expose
    private String totalVideos;
    @SerializedName("total_quiz")
    @Expose
    private String totalQuiz;
    @SerializedName("total_document")
    @Expose
    private String totalDocument;
    @SerializedName("enrolled")
    @Expose
    private String enrolled;
    @SerializedName("progress")
    @Expose
    private String progress;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getYoutubeUrl() {
        return youtubeUrl;
    }

    public void setYoutubeUrl(String youtubeUrl) {
        this.youtubeUrl = youtubeUrl;
    }

    public String getShortDesc() {
        return shortDesc;
    }

    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getThumbUrl() {
        return thumbUrl;
    }

    public void setThumbUrl(String thumbUrl) {
        this.thumbUrl = thumbUrl;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTotalVideos() {
        return totalVideos;
    }

    public void setTotalVideos(String totalVideos) {
        this.totalVideos = totalVideos;
    }

    public String getTotalQuiz() {
        return totalQuiz;
    }

    public void setTotalQuiz(String totalQuiz) {
        this.totalQuiz = totalQuiz;
    }

    public String getTotalDocument() {
        return totalDocument;
    }

    public void setTotalDocument(String totalDocument) {
        this.totalDocument = totalDocument;
    }

    public String getEnrolled() {
        return enrolled;
    }

    public void setEnrolled(String enrolled) {
        this.enrolled = enrolled;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

}
