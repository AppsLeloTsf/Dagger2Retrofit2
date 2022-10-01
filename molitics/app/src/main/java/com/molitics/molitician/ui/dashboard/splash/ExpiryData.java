package com.molitics.molitician.ui.dashboard.splash;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by rahul on 4/17/2017.
 */

public class ExpiryData implements Parcelable {

    public ExpiryData() {
    }

    @SerializedName("expiry_date")
    @Expose
    private String expiryDate;
    @SerializedName("current_date")
    @Expose
    private String currentDate;
    @SerializedName("latest_version")
    @Expose
    private Integer latestVersion;
    @SerializedName("is_expired")
    @Expose
    private Integer isExpired;

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

    public Integer getLatestVersion() {
        return latestVersion;
    }

    public void setLatestVersion(Integer latestVersion) {
        this.latestVersion = latestVersion;
    }

    public Integer getIsExpired() {
        return isExpired;
    }

    public void setIsExpired(Integer isExpired) {
        this.isExpired = isExpired;
    }


    @Override
    public int describeContents() {
        return 0;
    }


    protected ExpiryData(Parcel in) {
        expiryDate = in.readString();
        currentDate = in.readString();
        isExpired = in.readInt();
        latestVersion = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(expiryDate);
        dest.writeString(currentDate);
        dest.writeInt(isExpired);
        dest.writeInt(latestVersion);
    }

    public static final Creator<ExpiryData> CREATOR = new Creator<ExpiryData>() {
        @Override
        public ExpiryData createFromParcel(Parcel in) {
            return new ExpiryData(in);
        }

        @Override
        public ExpiryData[] newArray(int size) {
            return new ExpiryData[size];
        }
    };
}
