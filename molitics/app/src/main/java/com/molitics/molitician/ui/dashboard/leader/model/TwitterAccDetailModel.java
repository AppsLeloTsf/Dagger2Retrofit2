package com.molitics.molitician.ui.dashboard.leader.model;

/**
 * Created by rahul on 8/22/2017.
 */

public class TwitterAccDetailModel {

    String name = "";
    Long id = -1L;
    Long userId = -1L;

    public TwitterAccDetailModel(String name, Long userId, Long id) {
        this.name = name;
        this.id = id;
        this.userId = userId;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
