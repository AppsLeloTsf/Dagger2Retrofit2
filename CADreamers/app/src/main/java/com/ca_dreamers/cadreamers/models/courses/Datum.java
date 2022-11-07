package com.ca_dreamers.cadreamers.models.courses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Datum implements Serializable {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("sub_category_name")
    @Expose
    private String subCategoryName;
    @SerializedName("courses")
    @Expose
    private List<Course> courses = null;

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

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

}
