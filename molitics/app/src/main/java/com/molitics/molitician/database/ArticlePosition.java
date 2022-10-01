package com.molitics.molitician.database;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by lenovo on 7/27/2017.
 */

public class ArticlePosition extends RealmObject {
    @PrimaryKey
    Integer position;

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }
}
