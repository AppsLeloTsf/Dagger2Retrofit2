package com.molitics.molitician.ui.dashboard.leader.leaderFilter.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by rahul on 12/30/2016.
 */

public class LeaderFilter implements Parcelable {

    String page = "";
    String state = "";
    String district = "";
    String mp_constituency = "";
    String mla_constituency = "";
    String party = "";
    String post = "";
    Integer tab_serial = 0;

    public Integer getTab_serial() {
        return tab_serial;
    }

    public void setTab_serial(Integer national_leaders) {
        this.tab_serial = national_leaders;
    }


    public LeaderFilter() {
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getMp_constituency() {
        return mp_constituency;
    }

    public String getParty() {
        return party;
    }

    public void setParty(String party) {
        this.party = party;
    }

    public void setMp_constituency(String mp_constituency) {
        this.mp_constituency = mp_constituency;
    }

    public String getMla_constituency() {
        return mla_constituency;
    }

    public void setMla_constituency(String mla_constituency) {
        this.mla_constituency = mla_constituency;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    protected LeaderFilter(Parcel in) {
        page = in.readString();
        state = in.readString();
        district = in.readString();
        mp_constituency = in.readString();
        mla_constituency = in.readString();
        party = in.readString();
        post = in.readString();
        tab_serial = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(page);
        dest.writeString(state);
        dest.writeString(district);
        dest.writeString(mp_constituency);
        dest.writeString(mla_constituency);
        dest.writeString(party);
        dest.writeString(post);
        dest.writeInt(tab_serial);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<LeaderFilter> CREATOR = new Parcelable.Creator<LeaderFilter>() {
        @Override
        public LeaderFilter createFromParcel(Parcel in) {
            return new LeaderFilter(in);
        }

        @Override
        public LeaderFilter[] newArray(int size) {
            return new LeaderFilter[size];
        }
    };
}
