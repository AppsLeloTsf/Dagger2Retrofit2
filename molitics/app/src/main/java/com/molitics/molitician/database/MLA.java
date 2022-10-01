package com.molitics.molitician.database;

import io.realm.RealmObject;

/**
 * Created by rohit on 12/12/16.
 */

public class MLA extends RealmObject{

    String key;
    int value;

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
