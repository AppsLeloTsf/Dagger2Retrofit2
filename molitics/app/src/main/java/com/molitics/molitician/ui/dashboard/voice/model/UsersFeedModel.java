package com.molitics.molitician.ui.dashboard.voice.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.molitics.molitician.db.creator.feed.UserListCreator;

/**
 * Created by Rakesh Kumar on 17/09/2021.
 */
@Entity
@TypeConverters(UserListCreator.class)
public class UsersFeedModel implements Parcelable {
    public UsersFeedModel() {
    }

    @PrimaryKey(autoGenerate = true)
    private int primaryKey;

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("is_verify")
    @Expose
    private int isVerify;

    @SerializedName("image")
    @Expose
    private String image;

    protected UsersFeedModel(Parcel in) {
        primaryKey = in.readInt();
        id = in.readInt();
        name = in.readString();
        isVerify = in.readInt();
        image = in.readString();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIsVerify() {
        return isVerify;
    }

    public void setIsVerify(int isVerify) {
        this.isVerify = isVerify;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public static final Creator<UsersFeedModel> CREATOR = new Creator<UsersFeedModel>() {
        @Override
        public UsersFeedModel createFromParcel(Parcel in) {
            return new UsersFeedModel(in);
        }

        @Override
        public UsersFeedModel[] newArray(int size) {
            return new UsersFeedModel[size];
        }
    };

    public int getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(int primaryKey) {
        this.primaryKey = primaryKey;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(primaryKey);
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeInt(isVerify);
        parcel.writeString(image);
    }

}