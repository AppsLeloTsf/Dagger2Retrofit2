package com.indianjourno.indianjourno.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelFeatureId {

    @SerializedName("news_type_id")
    @Expose
    private String newsTypeId;
    @SerializedName("news_type_name")
    @Expose
    private String newsTypeName;

    public String getNewsTypeId() {
        return newsTypeId;
    }

    public void setNewsTypeId(String newsTypeId) {
        this.newsTypeId = newsTypeId;
    }

    public String getNewsTypeName() {
        return newsTypeName;
    }

    public void setNewsTypeName(String newsTypeName) {
        this.newsTypeName = newsTypeName;
    }
}
