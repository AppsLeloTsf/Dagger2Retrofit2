package com.molitics.molitician.ui.dashboard;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by rahul on 12/19/2016.
 */

public class FragmentListModel {

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("position")
    @Expose
    private Integer position;
    @SerializedName("type")
    @Expose
    private Integer type;

    public FragmentListModel(String title, int type) {
        this.title = title;
        this.type = type;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }
}
