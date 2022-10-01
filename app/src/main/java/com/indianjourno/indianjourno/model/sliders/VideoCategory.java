package com.indianjourno.indianjourno.model.sliders;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VideoCategory {
    @SerializedName("slidder_id")
    @Expose
    private String slidderId;
    @SerializedName("slider_name")
    @Expose
    private String sliderName;
    @SerializedName("slider_image")
    @Expose
    private String sliderImage;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("created_date")
    @Expose
    private String createdDate;

    public String getSlidderId() {
        return slidderId;
    }

    public void setSlidderId(String slidderId) {
        this.slidderId = slidderId;
    }

    public String getSliderName() {
        return sliderName;
    }

    public void setSliderName(String sliderName) {
        this.sliderName = sliderName;
    }

    public String getSliderImage() {
        return sliderImage;
    }

    public void setSliderImage(String sliderImage) {
        this.sliderImage = sliderImage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

}
