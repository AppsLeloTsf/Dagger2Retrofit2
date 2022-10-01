package com.molitics.molitician.database;


import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Rahul on 2/20/2017.
 */

public class HomeFragmentsModel extends RealmObject {


    String title;
    Integer position;

    @PrimaryKey
    Integer type;

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
