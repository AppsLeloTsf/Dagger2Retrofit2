package com.indianjourno.indianjourno.model.video_cat;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VideoCategory {
    @SerializedName("vid_cat_id")
    @Expose
    private String vidCatId;
    @SerializedName("vid_cat_name")
    @Expose
    private String vidCatName;
    @SerializedName("vid_cat_status")
    @Expose
    private String vidCatStatus;
    @SerializedName("vid_cat_icon")
    @Expose
    private String vidCatIcon;
    @SerializedName("vid_cat_image")
    @Expose
    private String vidCatImage;

    public String getVidCatId() {
        return vidCatId;
    }

    public void setVidCatId(String vidCatId) {
        this.vidCatId = vidCatId;
    }

    public String getVidCatName() {
        return vidCatName;
    }

    public void setVidCatName(String vidCatName) {
        this.vidCatName = vidCatName;
    }

    public String getVidCatStatus() {
        return vidCatStatus;
    }

    public void setVidCatStatus(String vidCatStatus) {
        this.vidCatStatus = vidCatStatus;
    }

    public String getVidCatIcon() {
        return vidCatIcon;
    }

    public void setVidCatIcon(String vidCatIcon) {
        this.vidCatIcon = vidCatIcon;
    }

    public String getVidCatImage() {
        return vidCatImage;
    }

    public void setVidCatImage(String vidCatImage) {
        this.vidCatImage = vidCatImage;
    }

}
