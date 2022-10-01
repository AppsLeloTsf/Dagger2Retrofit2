package com.molitics.molitician.ui.dashboard.voice.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by rahul on 21/11/17.
 */

public class
CommentModel implements Parcelable {


    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("user_id")
    @Expose
    private int userId;
    @SerializedName("comment")
    @Expose
    private String comment;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("time")
    @Expose
    private String time;

    @SerializedName("is_edited")
    @Expose
    private int edited;

    public int getLeaderReply() {
        return leaderReply;
    }

    public void setLeaderReply(int leaderReply) {
        this.leaderReply = leaderReply;
    }

    @SerializedName("leader_reply")
    @Expose

    private int leaderReply;



    public static Creator<CommentModel> getCREATOR() {
        return CREATOR;
    }

    public final static Parcelable.Creator<CommentModel> CREATOR = new Creator<CommentModel>() {


        @SuppressWarnings({
                "unchecked"
        })
        public CommentModel createFromParcel(Parcel in) {
            return new CommentModel(in);
        }

        public CommentModel[] newArray(int size) {
            return (new CommentModel[size]);
        }

    };

    protected CommentModel(Parcel in) {
        this.id = ((int) in.readValue((int.class.getClassLoader())));
        this.userId = ((int) in.readValue((int.class.getClassLoader())));
        this.comment = ((String) in.readValue((String.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.image = ((String) in.readValue((String.class.getClassLoader())));
        this.time = ((String) in.readValue((String.class.getClassLoader())));
        this.edited = ((int) in.readValue(int.class.getClassLoader()));
        this.leaderReply = ((int) in.readValue(int.class.getClassLoader()));
    }

    public CommentModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getEdited() {
        return edited;
    }

    public void setEdited(int edited) {
        this.edited = edited;
    }


    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(userId);
        dest.writeValue(comment);
        dest.writeValue(name);
        dest.writeValue(image);
        dest.writeValue(time);
        dest.writeInt(edited);
        dest.writeInt(leaderReply);
    }

    public int describeContents() {
        return 0;
    }
}
