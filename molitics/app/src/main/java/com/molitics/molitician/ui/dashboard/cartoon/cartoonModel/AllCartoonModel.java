package com.molitics.molitician.ui.dashboard.cartoon.cartoonModel;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by rahul on 19/02/18.
 */

public class AllCartoonModel implements Parcelable {

    @SerializedName("id")
    @Expose
    int id;
    @SerializedName("title")
    @Expose
    String title;

    @SerializedName("image")
    @Expose
    String image;
    @SerializedName("updated_at")
    @Expose
    String updated_at;

    @SerializedName("time")
    @Expose
    String time;
    @SerializedName("source")
    @Expose
    String source;
    @SerializedName("source_id")
    @Expose
    int sourceId;

    public int getSourceId() {
        return sourceId;
    }

    public void setSourceId(int sourceId) {
        this.sourceId = sourceId;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSourceLogo() {
        return sourceLogo;
    }

    public void setSourceLogo(String sourceLogo) {
        this.sourceLogo = sourceLogo;
    }

    public static Creator<AllCartoonModel> getCREATOR() {
        return CREATOR;
    }

    @SerializedName("source_logo")
    @Expose
    String sourceLogo;

    protected AllCartoonModel(Parcel in) {
        id = in.readInt();
        title = in.readString();
        image = in.readString();
        updated_at = in.readString();
        time = in.readString();
        source = in.readString();
        sourceLogo = in.readString();
    }

    public static final Creator<AllCartoonModel> CREATOR = new Creator<AllCartoonModel>() {
        @Override
        public AllCartoonModel createFromParcel(Parcel in) {
            return new AllCartoonModel(in);
        }

        @Override
        public AllCartoonModel[] newArray(int size) {
            return new AllCartoonModel[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(image);
        dest.writeString(updated_at);
        dest.writeString(time);
        dest.writeString(source);
        dest.writeString(sourceLogo);
    }
}
