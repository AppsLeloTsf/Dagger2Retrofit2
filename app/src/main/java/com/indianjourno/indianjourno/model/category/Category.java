package com.indianjourno.indianjourno.model.category;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Category {
    @SerializedName("category_id")
    @Expose
    private String categoryId;
    @SerializedName("category_name")
    @Expose
    private String categoryName;
    @SerializedName("category_slug")
    @Expose
    private String categorySlug;
    @SerializedName("category_status")
    @Expose
    private String categoryStatus;
    @SerializedName("category_icon")
    @Expose
    private String categoryIcon;
    @SerializedName("category_images")
    @Expose
    private String categoryImages;
    @SerializedName("category_created")
    @Expose
    private String categoryCreated;

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategorySlug() {
        return categorySlug;
    }

    public void setCategorySlug(String categorySlug) {
        this.categorySlug = categorySlug;
    }

    public String getCategoryStatus() {
        return categoryStatus;
    }

    public void setCategoryStatus(String categoryStatus) {
        this.categoryStatus = categoryStatus;
    }

    public String getCategoryIcon() {
        return categoryIcon;
    }

    public void setCategoryIcon(String categoryIcon) {
        this.categoryIcon = categoryIcon;
    }

    public String getCategoryImages() {
        return categoryImages;
    }

    public void setCategoryImages(String categoryImages) {
        this.categoryImages = categoryImages;
    }

    public String getCategoryCreated() {
        return categoryCreated;
    }

    public void setCategoryCreated(String categoryCreated) {
        this.categoryCreated = categoryCreated;
    }


}
