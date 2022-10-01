package com.molitics.molitician.model;

/**
 * Created by rahul on 20/11/17.
 */

public class CommonKeyModel {


    String key;
    int value;

    public CommonKeyModel() {

    }

    public CommonKeyModel(String key, int value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}

