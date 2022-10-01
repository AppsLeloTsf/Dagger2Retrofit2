package com.molitics.molitician.model.raisevoice;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by rahul on 14/11/17.
 */

public class DBVoiceAllModel extends RealmObject {

    private String title;
    private String content = "";
    @PrimaryKey
    private Integer userId;
   // private ArrayList<AllLeaderModel> candidateLeader = null;
    //private ArrayList<String> images = null;
    private String video;
    private String userName;
    private String image;
    private Integer likes;
    private Integer dislikes;
    private Integer myAction;
    private Integer comments;
    private Integer following;
    private String time;
    private Integer isFollowing;
    private String tagName;
    private Integer tag;
    private String location;

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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }


    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getDislikes() {
        return dislikes;
    }

    public void setDislikes(int dislikes) {
        this.dislikes = dislikes;
    }

    public int getMyAction() {
        return myAction;
    }

    public void setMyAction(int myAction) {
        this.myAction = myAction;
    }

    public int getComments() {
        return comments;
    }

    public void setComments(int comments) {
        this.comments = comments;
    }

    public int getFollowing() {
        return following;
    }

    public void setFollowing(int following) {
        this.following = following;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getIsFollowing() {
        return isFollowing;
    }

    public void setIsFollowing(int isFollowing) {
        this.isFollowing = isFollowing;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public Integer getTag() {
        return tag;
    }

    public void setTag(Integer tag) {
        this.tag = tag;
    }

    public String getLocation() {
        return location;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public void setDislikes(Integer dislikes) {
        this.dislikes = dislikes;
    }

    public void setMyAction(Integer myAction) {
        this.myAction = myAction;
    }

    public void setComments(Integer comments) {
        this.comments = comments;
    }

    public void setFollowing(Integer following) {
        this.following = following;
    }

    public void setIsFollowing(Integer isFollowing) {
        this.isFollowing = isFollowing;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
