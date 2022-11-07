package com.ca_dreamers.cadreamers.models.my_orders.books;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {
    @SerializedName("orderid")
    @Expose
    private String orderid;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("short_desc")
    @Expose
    private String shortDesc;
    @SerializedName("youtube_url")
    @Expose
    private String youtubeUrl;
    @SerializedName("product_type")
    @Expose
    private String productType;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("product_pay_mode")
    @Expose
    private String productPayMode;
    @SerializedName("select_product_type")
    @Expose
    private String selectProductType;
    @SerializedName("thumb_url")
    @Expose
    private String thumbUrl;
    @SerializedName("rating")
    @Expose
    private String rating;
    @SerializedName("progress")
    @Expose
    private String progress;
    @SerializedName("product_mode")
    @Expose
    private String productMode;

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortDesc() {
        return shortDesc;
    }

    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
    }

    public String getYoutubeUrl() {
        return youtubeUrl;
    }

    public void setYoutubeUrl(String youtubeUrl) {
        this.youtubeUrl = youtubeUrl;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getProductPayMode() {
        return productPayMode;
    }

    public void setProductPayMode(String productPayMode) {
        this.productPayMode = productPayMode;
    }

    public String getSelectProductType() {
        return selectProductType;
    }

    public void setSelectProductType(String selectProductType) {
        this.selectProductType = selectProductType;
    }

    public String getThumbUrl() {
        return thumbUrl;
    }

    public void setThumbUrl(String thumbUrl) {
        this.thumbUrl = thumbUrl;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    public String getProductMode() {
        return productMode;
    }

    public void setProductMode(String productMode) {
        this.productMode = productMode;
    }
}
