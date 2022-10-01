package com.indianjourno.indianjourno.model.category;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CategoryMessage {
    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("NewsCategory")
    @Expose
    private List<NewsCategory> newsCategory = null;

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<NewsCategory> getNewsCategory() {
        return newsCategory;
    }

    public void setNewsCategory(List<NewsCategory> newsCategory) {
        this.newsCategory = newsCategory;
    }
}
