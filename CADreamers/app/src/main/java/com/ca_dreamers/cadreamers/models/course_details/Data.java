package com.ca_dreamers.cadreamers.models.course_details;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Data {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("youtube_url")
    @Expose
    private String youtubeUrl;
    @SerializedName("product_type")
    @Expose
    private String productType;
    @SerializedName("product_mode")
    @Expose
    private String productMode;
    @SerializedName("short_desc")
    @Expose
    private String shortDesc;
    @SerializedName("duration")
    @Expose
    private String duration;
    @SerializedName("language")
    @Expose
    private String language;
    @SerializedName("language_id")
    @Expose
    private String languageId;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("thumb_url")
    @Expose
    private String thumbUrl;
    @SerializedName("off_price")
    @Expose
    private Integer offPrice;
    @SerializedName("rating")
    @Expose
    private String rating;
    @SerializedName("author")
    @Expose
    private String author;
    @SerializedName("enrolled")
    @Expose
    private String enrolled;
    @SerializedName("wish_list")
    @Expose
    private String wishList;
    @SerializedName("chapters")
    @Expose
    private List<Chapter> chapters = null;

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

    public String getProductMode() {
        return productMode;
    }

    public void setProductMode(String productMode) {
        this.productMode = productMode;
    }

    public String getShortDesc() {
        return shortDesc;
    }

    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getLanguageId() {
        return languageId;
    }

    public void setLanguageId(String languageId) {
        this.languageId = languageId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getThumbUrl() {
        return thumbUrl;
    }

    public void setThumbUrl(String thumbUrl) {
        this.thumbUrl = thumbUrl;
    }

    public Integer getOffPrice() {
        return offPrice;
    }

    public void setOffPrice(Integer offPrice) {
        this.offPrice = offPrice;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getEnrolled() {
        return enrolled;
    }

    public void setEnrolled(String enrolled) {
        this.enrolled = enrolled;
    }

    public String getWishList() {
        return wishList;
    }

    public void setWishList(String wishList) {
        this.wishList = wishList;
    }

    public List<Chapter> getChapters() {
        return chapters;
    }

    public void setChapters(List<Chapter> chapters) {
        this.chapters = chapters;
    }
}
