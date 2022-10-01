package com.molitics.molitician.ui.dashboard.voice.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by rahul on 24/11/17.
 */

public class ImageDeleteModel {


    @SerializedName("index")
    @Expose
    public List<String> index;

    public ImageDeleteModel(List<String> imageList) {
        index = imageList;
    }

    public List<String> getIndex() {
        return index;
    }

    public void setIndex(List<String> index) {
        this.index = index;
    }
}
