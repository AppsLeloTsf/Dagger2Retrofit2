package com.molitics.molitician.ui.dashboard.voice.model;


import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.molitics.molitician.db.creator.voice.AllLeaderListCreator;
import com.molitics.molitician.db.creator.voice.StringConverter;
import com.molitics.molitician.ui.dashboard.leader.AllLeaderModel;

import java.util.ArrayList;

/**
 * Created by rahul on 14/11/17.
 */
@Entity
public class LeaderVoiceAllModel implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private int primaryKey;

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("content")
    @Expose
    private String content = "";
    @SerializedName("user_id")
    @Expose
    private int userId;

    @TypeConverters(AllLeaderListCreator.class)
    @SerializedName("candidate_leader")
    @Expose
    @ColumnInfo
    private ArrayList<AllLeaderModel> candidateLeader = new ArrayList<AllLeaderModel>();

    @TypeConverters(StringConverter.class)
    @SerializedName("images")
    @Expose
    @ColumnInfo
    private ArrayList<String> images = new ArrayList<>();
    @SerializedName("video_small_theme")
    @Expose
    private String video;
    @SerializedName("user_name")
    @Expose
    private String userName;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("likes")
    @Expose
    private int likes;
    @SerializedName("dislikes")
    @Expose
    private int dislikes;
    @SerializedName("my_action")
    @Expose
    private int myAction;
    @SerializedName("comments")
    @Expose
    private int comments;
    @SerializedName("followers")
    @Expose
    private int following;
    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("is_following")
    @Expose
    private int isFollowing;
    @SerializedName("tag_name")
    @Expose
    private String tagName;
    @SerializedName("tag")
    @Expose
    private Integer tag;
    @SerializedName("location")
    @Expose
    private String location;

    @SerializedName("shared_image_url")
    private String sharedImageUrl;

    @SerializedName("shared_url")
    private String sharedUrl;

    @SerializedName("url_source")
    private String urlSource;
    @SerializedName("image_sizes")
    @Expose
    private String imageSizes;

    @TypeConverters(StringConverter.class)
    private ArrayList<String> deleted_image = new ArrayList<>();

    @SerializedName("views_count")
    @Expose
    private int viewsCount;

    public int getViewsCount() {
        return viewsCount;
    }

    public void setViewsCount(int viewsCount) {
        this.viewsCount = viewsCount;
    }

    public ArrayList<String> getDeleted_image() {
        return deleted_image;
    }

    public void setDeleted_image(ArrayList<String> deleted_image) {
        this.deleted_image = deleted_image;
    }

    public String getSharedImageUrl() {
        return sharedImageUrl;
    }

    public void setSharedImageUrl(String sharedImageUrl) {
        this.sharedImageUrl = sharedImageUrl;
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

    public int getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(int primaryKey) {
        this.primaryKey = primaryKey;
    }

    public static Creator<LeaderVoiceAllModel> getCREATOR() {
        return CREATOR;
    }


    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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

    public final static Creator<LeaderVoiceAllModel> CREATOR = new Creator<LeaderVoiceAllModel>() {

        @SuppressWarnings({
                "unchecked"
        })
        public LeaderVoiceAllModel createFromParcel(Parcel in) {
            return new LeaderVoiceAllModel(in);
        }

        public LeaderVoiceAllModel[] newArray(int size) {
            return (new LeaderVoiceAllModel[size]);
        }

    };

    protected LeaderVoiceAllModel(Parcel in) {
        this.id = ((int) in.readValue((int.class.getClassLoader())));
        this.title = ((String) in.readValue((String.class.getClassLoader())));
        this.content = ((String) in.readValue((String.class.getClassLoader())));
        this.userId = ((int) in.readValue((String.class.getClassLoader())));
        in.readList(this.candidateLeader, (AllLeaderModel.class.getClassLoader()));
        in.readList(this.images, (String.class.getClassLoader()));
        this.video = ((String) in.readValue((String.class.getClassLoader())));
        this.userName = ((String) in.readValue((String.class.getClassLoader())));
        this.image = ((String) in.readValue((String.class.getClassLoader())));
        this.likes = ((int) in.readValue((int.class.getClassLoader())));
        this.dislikes = ((int) in.readValue((int.class.getClassLoader())));
        this.myAction = ((int) in.readValue((int.class.getClassLoader())));
        this.comments = ((int) in.readValue((int.class.getClassLoader())));
        this.following = ((int) in.readValue((int.class.getClassLoader())));
        this.time = ((String) in.readValue((String.class.getClassLoader())));
        this.isFollowing = ((int) in.readValue(int.class.getClassLoader()));
        this.tagName = ((String) in.readValue(String.class.getClassLoader()));
        this.tag = ((Integer) in.readValue(int.class.getClassLoader()));
        this.location = ((String) in.readValue(String.class.getClassLoader()));
        this.sharedImageUrl = ((String) in.readValue(String.class.getClassLoader()));
        this.sharedUrl = ((String) in.readValue(String.class.getClassLoader()));
        this.urlSource = ((String) in.readValue(String.class.getClassLoader()));
        viewsCount = in.readInt();
    }

    public LeaderVoiceAllModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public ArrayList<AllLeaderModel> getCandidateLeader() {
        return candidateLeader;
    }

    public void setCandidateLeader(ArrayList<AllLeaderModel> candidateLeader) {
        this.candidateLeader = candidateLeader;
    }

    public ArrayList<String> getImages() {
        return images;
    }

    public void setImages(ArrayList<String> images) {
        this.images = images;
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

    public int getIsFollowing() {
        return isFollowing;
    }

    public void setIsFollowing(int isFollowing) {
        this.isFollowing = isFollowing;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getImageSizes() {
        return imageSizes;
    }

    public void setImageSizes(String imageSizes) {
        this.imageSizes = imageSizes;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(title);
        dest.writeValue(content);
        dest.writeValue(userId);
        dest.writeList(candidateLeader);
        dest.writeList(images);
        dest.writeValue(video);
        dest.writeValue(userName);
        dest.writeValue(image);
        dest.writeValue(likes);
        dest.writeValue(dislikes);
        dest.writeValue(myAction);
        dest.writeValue(comments);
        dest.writeValue(following);
        dest.writeValue(time);
        dest.writeValue(isFollowing);
        dest.writeValue(tagName);
        dest.writeValue(tag);
        dest.writeValue(location);
        dest.writeValue(sharedImageUrl);
        dest.writeValue(sharedUrl);
        dest.writeValue(urlSource);
        dest.writeInt(viewsCount);

    }

    public int describeContents() {
        return 0;
    }
}
