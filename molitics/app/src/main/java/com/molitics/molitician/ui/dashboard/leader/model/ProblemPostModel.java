package com.molitics.molitician.ui.dashboard.leader.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by rahul on 1/18/2017.
 */

public class ProblemPostModel {

    @SerializedName("name")
    @Expose
    String name;
    @SerializedName("contact")
    @Expose
    String contact;
    @SerializedName("subject")
    @Expose
    String subject;

    @SerializedName("description")
    @Expose
    String description;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }



}
