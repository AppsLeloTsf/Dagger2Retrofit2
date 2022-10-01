package com.molitics.molitician.ui.dashboard.voice.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.TypeConverters;

import com.molitics.molitician.db.creator.voice.RYVImageConvertor;

@TypeConverters(RYVImageConvertor.class)
public class VoiceImageModel {

    String image;

    private int columnSpan;
    private int rowSpan;
    private int position;

    public VoiceImageModel(String image_url, int columnSpan, int rowSpan, int position) {
        this.image = image_url;
        this.columnSpan = columnSpan;
        this.rowSpan = rowSpan;
        this.position = position;
    }

    public VoiceImageModel(Parcel in) {
        readFromParcel(in);
    }


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getPosition() {
        return position;
    }


    private void readFromParcel(Parcel in) {
        image = in.readString();
        columnSpan = in.readInt();
        rowSpan = in.readInt();
        position = in.readInt();
    }

    /* Parcelable interface implementation */
    public static final Parcelable.Creator<VoiceImageModel> CREATOR = new Parcelable.Creator<VoiceImageModel>() {
        @Override
        public VoiceImageModel createFromParcel(@NonNull Parcel in) {
            return new VoiceImageModel(in);
        }

        @Override
        @NonNull
        public VoiceImageModel[] newArray(int size) {
            return new VoiceImageModel[size];
        }
    };
}
