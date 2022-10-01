package com.molitics.molitician.ui.dashboard.voice.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by rahul on 16/11/17.
 */

public class VoiceCreateModel {

    @SerializedName("id")
    @Expose
    int id;

    @SerializedName("title")
    @Expose
    String title;
    @SerializedName("content")
    @Expose
    String content;
    @SerializedName("leaders")
    @Expose
    List<Integer> leaders;
    @SerializedName("tag")
    @Expose
    Integer tag;

    @SerializedName("shared_image_url")
    String sharedImageUrl;

    @SerializedName("shared_url")
    private String sharedUrl;

    @SerializedName("url_source")
    private String urlSource;


    @SerializedName("uploaded_images_url")
    @Expose
    private List<String> uploadedImagesUrl;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSharedUrl() {
        return sharedUrl;
    }

    public void setSharedUrl(String sharedUrl) {
        this.sharedUrl = sharedUrl;
    }

    public String getUrlSource() {
        return urlSource;
    }

    public void setUrlSource(String urlSource) {
        this.urlSource = urlSource;
    }

    public String getSharedImageUrl() {
        return sharedImageUrl;
    }

    public void setSharedImageUrl(String sharedImageUrl) {
        this.sharedImageUrl = sharedImageUrl;
    }

    public Integer getTag() {
        return tag;
    }

    public List<String> getUploadedImagesUrl() {
        return uploadedImagesUrl;
    }

    public void setUploadedImagesUrl(List<String> uploadedImagesUrl) {
        this.uploadedImagesUrl = uploadedImagesUrl;
    }

    public void setTag(Integer tag) {
        this.tag = tag;
    }

    public Integer getHot_topic_id() {
        return tag;
    }

    public void setHot_topic_id(Integer tag) {
        this.tag = tag;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<Integer> getLeaders() {
        return leaders;
    }

    public void setLeaders(List<Integer> leaders) {
        this.leaders = leaders;
    }
}
