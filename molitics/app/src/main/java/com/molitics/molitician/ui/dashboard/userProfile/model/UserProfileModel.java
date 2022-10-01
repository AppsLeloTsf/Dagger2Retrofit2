package com.molitics.molitician.ui.dashboard.userProfile.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by rahul on 1/13/2017.
 */

public class UserProfileModel implements Parcelable {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("mla")
    @Expose
    private String mla;
    @SerializedName("district")
    @Expose
    private String district;
    @SerializedName("political_orientation")
    @Expose
    private String politicalOrientation;
    @SerializedName("mp")
    @Expose
    private String mp;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("gender")
    @Expose
    private int gender;
    @SerializedName("state_id")
    @Expose
    private int stateId;
    @SerializedName("mla_id")
    @Expose
    private int mlaId;
    @SerializedName("mp_id")
    @Expose
    private int mpId;
    @SerializedName("district_id")
    @Expose
    private int districtId;
    @SerializedName("political_orientation_id")
    @Expose
    private int politicalOrientationId;
    @SerializedName("following")
    @Expose
    private int following;
    @SerializedName("issues")
    @Expose
    private int issues;

    @SerializedName("followers")
    @Expose
    private int followers;

    @SerializedName("is_user_following")
    @Expose
    private int isUserFollowing;

    @SerializedName("is_verify")
    @Expose
    private int isVerify;
    @SerializedName("profession")
    @Expose
    private int profession;
    @SerializedName("profession_name")
    @Expose
    private String professionName;
    @SerializedName("bio")
    @Expose
    private String userBio;

    @SerializedName("username")
    @Expose
    private String userName;

    public int getProfession() {
        return profession;
    }

    public void setProfession(int profession) {
        this.profession = profession;
    }

    public String getProfessionName() {
        return professionName;
    }

    public void setProfessionName(String professionName) {
        this.professionName = professionName;
    }

    public int getIsVerify() {
        return isVerify;
    }

    public void setIsVerify(int isVerify) {
        this.isVerify = isVerify;
    }

    public static Creator<UserProfileModel> getCREATOR() {
        return CREATOR;
    }

    public int getIsUserFollowing() {
        return isUserFollowing;
    }

    public void setIsUserFollowing(int isUserFollowing) {
        this.isUserFollowing = isUserFollowing;
    }

    public final static Parcelable.Creator<UserProfileModel> CREATOR = new Creator<UserProfileModel>() {

        @SuppressWarnings({
                "unchecked"
        })
        public UserProfileModel createFromParcel(Parcel in) {
            return new UserProfileModel(in);
        }

        public UserProfileModel[] newArray(int size) {
            return (new UserProfileModel[size]);
        }

    };

    protected UserProfileModel(Parcel in) {
        this.id = ((int) in.readValue((int.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.image = ((String) in.readValue((String.class.getClassLoader())));
        this.mla = ((String) in.readValue((String.class.getClassLoader())));
        this.district = ((String) in.readValue((String.class.getClassLoader())));
        this.politicalOrientation = ((String) in.readValue((String.class.getClassLoader())));
        this.mp = ((String) in.readValue((String.class.getClassLoader())));
        this.state = ((String) in.readValue((String.class.getClassLoader())));
        this.mobile = ((String) in.readValue((String.class.getClassLoader())));
        this.email = ((String) in.readValue((String.class.getClassLoader())));
        this.gender = ((int) in.readValue((int.class.getClassLoader())));
        this.stateId = ((int) in.readValue((int.class.getClassLoader())));
        this.mlaId = ((int) in.readValue((int.class.getClassLoader())));
        this.mpId = ((int) in.readValue((int.class.getClassLoader())));
        this.districtId = ((int) in.readValue((int.class.getClassLoader())));
        this.politicalOrientationId = ((int) in.readValue((Object.class.getClassLoader())));
        this.following = ((int) in.readValue((int.class.getClassLoader())));
        this.issues = ((int) in.readValue((int.class.getClassLoader())));
        this.followers = ((int) in.readValue((int.class.getClassLoader())));
        this.isUserFollowing = ((int) in.readValue((int.class.getClassLoader())));
        this.isVerify = ((int) in.readValue((int.class.getClassLoader())));
        this.profession = ((int) in.readValue((int.class.getClassLoader())));
        this.professionName = ((String) in.readValue((String.class.getClassLoader())));
        this.userBio = ((String) in.readValue((String.class.getClassLoader())));
        this.userName = ((String) in.readValue((String.class.getClassLoader())));
    }

    public UserProfileModel() {
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getMla() {
        return mla;
    }

    public void setMla(String mla) {
        this.mla = mla;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getPoliticalOrientation() {
        return politicalOrientation;
    }

    public void setPoliticalOrientation(String politicalOrientation) {
        this.politicalOrientation = politicalOrientation;
    }

    public String getMp() {
        return mp;
    }

    public void setMp(String mp) {
        this.mp = mp;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getStateId() {
        return stateId;
    }

    public void setStateId(int stateId) {
        this.stateId = stateId;
    }

    public int getMlaId() {
        return mlaId;
    }

    public void setMlaId(int mlaId) {
        this.mlaId = mlaId;
    }

    public int getMpId() {
        return mpId;
    }

    public void setMpId(int mpId) {
        this.mpId = mpId;
    }

    public int getDistrictId() {
        return districtId;
    }

    public void setDistrictId(int districtId) {
        this.districtId = districtId;
    }

    public int getPoliticalOrientationId() {
        return politicalOrientationId;
    }

    public void setPoliticalOrientationId(int politicalOrientationId) {
        this.politicalOrientationId = politicalOrientationId;
    }

    public String getUserBio() {
        return userBio;
    }

    public void setUserBio(String userBio) {
        this.userBio = userBio;
    }

    public int getFollowing() {
        return following;
    }

    public void setFollowing(int following) {
        this.following = following;
    }

    public int getFollowers() {
        return followers;
    }

    public void setFollowers(int followers) {
        this.followers = followers;
    }

    public int getIssues() {
        return issues;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setIssues(int issues) {
        this.issues = issues;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(name);
        dest.writeValue(image);
        dest.writeValue(mla);
        dest.writeValue(district);
        dest.writeValue(politicalOrientation);
        dest.writeValue(mp);
        dest.writeValue(state);
        dest.writeValue(mobile);
        dest.writeValue(email);
        dest.writeValue(gender);
        dest.writeValue(stateId);
        dest.writeValue(mlaId);
        dest.writeValue(mpId);
        dest.writeValue(districtId);
        dest.writeValue(politicalOrientationId);
        dest.writeValue(following);
        dest.writeValue(issues);
        dest.writeValue(followers);
        dest.writeValue(isUserFollowing);
        dest.writeValue(isVerify);
        dest.writeValue(profession);
        dest.writeValue(professionName);
        dest.writeValue(userBio);
        dest.writeValue(userName);
    }

    public int describeContents() {
        return 0;
    }

}