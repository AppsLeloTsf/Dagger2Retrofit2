package com.molitics.molitician.ui.dashboard.voice.model;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.molitics.molitician.db.creator.feed.FeedActionCreator;
import com.molitics.molitician.db.creator.feed.FeedImageCreator;
import com.molitics.molitician.db.creator.feed.LeaderListCreator;
import com.molitics.molitician.db.creator.feed.UserListCreator;

import java.util.ArrayList;

/**
 * Created by Rakesh Kumar on 14/01/2021.
 */

@Entity
public class FeedModel implements Parcelable {
    public FeedModel() {
    }
    @PrimaryKey(autoGenerate = true)
    private int primaryKey;

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("content")
    @Expose
    private String content = "";

    @SerializedName("like_count")
    @Expose
    private int likes;

    @TypeConverters(UserListCreator.class)
    @SerializedName("user")
    @Expose
    @ColumnInfo
    private UsersFeedModel userModel;

    @SerializedName("dislike_count")
    @Expose
    private int dislikes;

    @SerializedName("comment_count")
    @Expose
    private int comments;

    @TypeConverters(LeaderListCreator.class)
    @SerializedName("leaders")
    @Expose
    @ColumnInfo
    private ArrayList<LeaderModel> leaderModelArrayList;

    @TypeConverters(FeedImageCreator.class)
    @SerializedName("media")
    @Expose
    @ColumnInfo
    private ArrayList<FeedImageModel> images = new ArrayList<>();

    @TypeConverters(FeedActionCreator.class)
    @SerializedName("user_feed_action")
    @Expose
    @ColumnInfo
    private FeedActionModel feedActionModel;

    @SerializedName("user_followed_author")
    @Expose
    private boolean user_followed_author;

/*    @SerializedName("hash_tag_string")
    @Expose
    private List<String> hash_tag_string;*/

    @SerializedName("created_at")
    @Expose
    private String created_at;

    @SerializedName("time_elapsed")
    @Expose
    private String timeElapsed;

    @SerializedName("view_count")
    @Expose
    private String viewCount;

    @SerializedName("context")
    @Expose
    private String context;


    protected FeedModel(Parcel in) {
        primaryKey = in.readInt();
        id = in.readInt();
        content = in.readString();
        likes = in.readInt();
        dislikes = in.readInt();
        comments = in.readInt();
        created_at = in.readString();
        timeElapsed = in.readString();
        viewCount = in.readString();
        context = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(primaryKey);
        dest.writeInt(id);
        dest.writeString(content);
        dest.writeInt(likes);
        dest.writeInt(dislikes);
        dest.writeInt(comments);
        dest.writeString(created_at);
        dest.writeString(timeElapsed);
        dest.writeString(viewCount);
        dest.writeString(context);
    }

    public static final Creator<FeedModel> CREATOR = new Creator<FeedModel>() {
        @Override
        public FeedModel createFromParcel(Parcel in) {
            return new FeedModel(in);
        }

        @Override
        public FeedModel[] newArray(int size) {
            return new FeedModel[size];
        }
    };


    public int getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(int primaryKey) {
        this.primaryKey = primaryKey;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object obj) {
        FeedModel o = (FeedModel) obj;
        return this.id == o.id;
    }


   /* public int getUser_feed_action() {
        return user_feed_action;
    }

    public void setUser_feed_action(int user_feed_action) {
        this.user_feed_action = user_feed_action;
    }*/

    public boolean isUser_followed_author() {
        return user_followed_author;
    }

    public void setUser_followed_author(boolean user_followed_author) {
        this.user_followed_author = user_followed_author;
    }

/*    public List<String> getHash_tag_string() {
        return hash_tag_string;
    }

    public void setHash_tag_string(List<String> hash_tag_string) {
        this.hash_tag_string = hash_tag_string;
    }*/

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getTimeElapsed() {
        return timeElapsed;
    }

    public void setTimeElapsed(String timeElapsed) {
        this.timeElapsed = timeElapsed;
    }

    public String getViewCount() {
        return viewCount;
    }

    public void setViewCount(String viewCount) {
        this.viewCount = viewCount;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
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

    public int getComments() {
        return comments;
    }

    public void setComments(int comments) {
        this.comments = comments;
    }

    public UsersFeedModel getUserModel() {
        return userModel;
    }

    public void setUserModel(UsersFeedModel userModel) {
        this.userModel = userModel;
    }

    public ArrayList<LeaderModel> getLeaderModelArrayList() {
        return leaderModelArrayList;
    }

    public void setLeaderModelArrayList(ArrayList<LeaderModel> leaderModelArrayList) {
        this.leaderModelArrayList = leaderModelArrayList;
    }

    public ArrayList<FeedImageModel> getImages() {
        return images;
    }

    public void setImages(ArrayList<FeedImageModel> images) {
        this.images = images;
    }

    public FeedActionModel getFeedActionModel() {
        return feedActionModel;
    }

    public void setFeedActionModel(FeedActionModel feedActionModel) {
        this.feedActionModel = feedActionModel;
    }
}
