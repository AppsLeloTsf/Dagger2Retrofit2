package com.molitics.molitician.database;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by rohit on 12/12/16.
 */

public class State extends RealmObject{

    String key;
    @PrimaryKey
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
