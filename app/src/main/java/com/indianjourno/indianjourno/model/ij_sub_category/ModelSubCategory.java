package com.indianjourno.indianjourno.model.ij_sub_category;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ModelSubCategory {
    @SerializedName("sub_category")
    @Expose
    private List<SubCategory> subCategory = null;

    public List<SubCategory> getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(List<SubCategory> subCategory) {
        this.subCategory = subCategory;
    }

}
