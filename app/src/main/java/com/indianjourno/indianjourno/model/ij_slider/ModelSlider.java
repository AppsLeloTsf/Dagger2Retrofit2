package com.indianjourno.indianjourno.ij_slider;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ModelSlider {
    @SerializedName("slidder")
    @Expose
    private List<Slidder> slidder = null;

    public List<Slidder> getSlidder() {
        return slidder;
    }

    public void setSlidder(List<Slidder> slidder) {
        this.slidder = slidder;
    }
}
