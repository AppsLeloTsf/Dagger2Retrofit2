package com.molitics.molitician.ui.dashboard.leader.leaderProfile;

/**
 * Created by rahul on 1/5/2017.
 */

public class TwitterDataResponse {

    String text = "";
    String created_at = "";
    String name = "";
    String image_url = "";
    Long id = 0L;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTweet_image() {
        return tweet_image;
    }

    public void setTweet_image(String tweet_image) {
        this.tweet_image = tweet_image;
    }

    String tweet_image = "";

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}
