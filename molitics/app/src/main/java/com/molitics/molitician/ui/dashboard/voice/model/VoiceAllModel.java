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
public class VoiceAllModel implements Parcelable {

    public VoiceAllModel() {
    }

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
    @SerializedName("is_user_following")
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

    @SerializedName("first_img_size")
    @Expose
    private String imageSizes;

    @SerializedName("views_count")
    @Expose
    private int viewsCount;

    protected VoiceAllModel(Parcel in) {
        primaryKey = in.readInt();
        id = in.readInt();
        title = in.readString();
        content = in.readString();
        userId = in.readInt();
        in.readList(this.candidateLeader, (AllLeaderModel.class.getClassLoader()));
        images = in.createStringArrayList();
        video = in.readString();
        userName = in.readString();
        image = in.readString();
        likes = in.readInt();
        dislikes = in.readInt();
        myAction = in.readInt();
        comments = in.readInt();
        following = in.readInt();
        time = in.readString();
        isFollowing = in.readInt();
        tagName = in.readString();
        if (in.readByte() == 0) {
            tag = null;
        } else {
            tag = in.readInt();
        }
        location = in.readString();
        sharedImageUrl = in.readString();
        sharedUrl = in.readString();
        urlSource = in.readString();
        imageSizes = in.readString();
        viewsCount = in.readInt();
        from = in.readString();
        deleted_image = in.createStringArrayList();
        uploadedPercent = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(primaryKey);
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(content);
        dest.writeInt(userId);
        dest.writeList(candidateLeader);
        dest.writeStringList(images);
        dest.writeString(video);
        dest.writeString(userName);
        dest.writeString(image);
        dest.writeInt(likes);
        dest.writeInt(dislikes);
        dest.writeInt(myAction);
        dest.writeInt(comments);
        dest.writeInt(following);
        dest.writeString(time);
        dest.writeInt(isFollowing);
        dest.writeString(tagName);
        if (tag == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(tag);
        }
        dest.writeString(location);
        dest.writeString(sharedImageUrl);
        dest.writeString(sharedUrl);
        dest.writeString(urlSource);
        dest.writeString(imageSizes);
        dest.writeInt(viewsCount);
        dest.writeString(from);
        dest.writeStringList(deleted_image);
        dest.writeInt(uploadedPercent);
    }

    public static final Creator<VoiceAllModel> CREATOR = new Creator<VoiceAllModel>() {
        @Override
        public VoiceAllModel createFromParcel(Parcel in) {
            return new VoiceAllModel(in);
        }

        @Override
        public VoiceAllModel[] newArray(int size) {
            return new VoiceAllModel[size];
        }
    };

    public int getViewsCount() {
        return viewsCount;
    }

    public void setViewsCount(int viewsCount) {
        this.viewsCount = viewsCount;
    }

    private String from = "";

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    ///@TypeConverters(StringConverter.class)
    private ArrayList<String> deleted_image = new ArrayList<>();

    int uploadedPercent = -1;


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

    public int getUploadedPercent() {
        return uploadedPercent;
    }

    public void setUploadedPercent(int uploadedPercent) {
        this.uploadedPercent = uploadedPercent;
    }

    public String getImageSizes() {
        return imageSizes;
    }

    public void setImageSizes(String imageSizes) {
        this.imageSizes = imageSizes;
    }

    public String getUrlSource() {
        return urlSource;
    }

    public void setUrlSource(String urlSource) {
        this.urlSource = urlSource;
    }


    public ArrayList<String> getDeleted_image() {
        return deleted_image;
    }

    public void setDeleted_image(ArrayList<String> deleted_image) {
        this.deleted_image = deleted_image;
    }

    public int getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(int primaryKey) {
        this.primaryKey = primaryKey;
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

    public int describeContents() {
        return 0;
    }

    @Override
    public boolean equals(Object obj) {
        VoiceAllModel o = (VoiceAllModel) obj;
        return this.id == o.id;
    }
}
