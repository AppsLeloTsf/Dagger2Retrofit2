package com.molitics.molitician.ui.dashboard.constantData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by rahul on 12/15/2016.
 */

public class ConstantModel {

    @SerializedName("key")
    @Expose
    private String key;
    @SerializedName("value")
    @Expose
    private int value;
    @SerializedName("is_check")
    @Expose
    private boolean isCheck = false;

    public ConstantModel(String key, boolean isCheck) {
        this.key = key;
        this.isCheck = isCheck;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }


}
