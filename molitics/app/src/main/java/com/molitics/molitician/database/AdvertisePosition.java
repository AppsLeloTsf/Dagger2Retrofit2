package com.molitics.molitician.database;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by rahul on 9/1/2017.
 */

public class AdvertisePosition extends RealmObject {
    @PrimaryKey
    Integer position;

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }
}
