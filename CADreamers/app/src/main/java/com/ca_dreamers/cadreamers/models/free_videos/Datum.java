package com.ca_dreamers.cadreamers.models.free_videos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Datum {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("sub_category_name")
    @Expose
    private String subCategoryName;
    @SerializedName("free_videos")
    @Expose
    private List<FreeVideo> freeVideos = null;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSubCategoryName() {
        return subCategoryName;
    }

    public void setSubCategoryName(String subCategoryName) {
        this.subCategoryName = subCategoryName;
    }

    public List<FreeVideo> getFreeVideos() {
        return freeVideos;
    }

    public void setFreeVideos(List<FreeVideo> freeVideos) {
        this.freeVideos = freeVideos;
    }
}
