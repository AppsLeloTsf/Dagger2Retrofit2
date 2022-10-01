package com.molitics.molitician.ui.dashboard.voice.adapter.nested;

import android.os.Parcel;
import android.util.SparseArray;

import im.ene.toro.media.PlaybackInfo;

/**
 * Created by om on 21/05/18.
 */
@SuppressWarnings("WeakerAccess")
public class ExtraPlaybackInfo extends PlaybackInfo {

   public final SparseArray actualInfo;

    public ExtraPlaybackInfo(SparseArray<PlaybackInfo> actualInfo) {
        this.actualInfo = actualInfo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        //noinspection unchecked
        dest.writeSparseArray(this.actualInfo);
    }

    protected ExtraPlaybackInfo(Parcel in) {
        super(in);
        this.actualInfo = in.readSparseArray(PlaybackInfo.class.getClassLoader());
    }

    public static final Creator<ExtraPlaybackInfo> CREATOR =
            new ClassLoaderCreator<ExtraPlaybackInfo>() {
                @Override
                public ExtraPlaybackInfo createFromParcel(Parcel source) {
                    return new ExtraPlaybackInfo(source);
                }

                @Override
                public ExtraPlaybackInfo createFromParcel(Parcel source, ClassLoader loader) {
                    return new ExtraPlaybackInfo(source);
                }

                @Override
                public ExtraPlaybackInfo[] newArray(int size) {
                    return new ExtraPlaybackInfo[size];
                }
            };

    @Override
    public String toString() {
        return "ExtraInfo{" + "actualInfo=" + actualInfo + '}';
    }
}
