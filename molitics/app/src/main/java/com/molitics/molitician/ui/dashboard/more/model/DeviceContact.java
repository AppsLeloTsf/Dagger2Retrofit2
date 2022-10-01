package com.molitics.molitician.ui.dashboard.more.model;

import android.net.Uri;

import java.util.HashSet;
import java.util.Set;

public class DeviceContact {

    /**
     * The unique id of this contact.
     */
    public final long id;

    /**
     * Flag indicating if this contact should be visible in any user interface.
     */
    public int inVisibleGroup;

    /**
     * The display name of this contact.
     */
    public String displayName;

    /**
     * Flag indicating if this contact is a favorite contact.
     */
    public boolean starred;

    /**
     * The URI of the full-size photo of this contact.
     */
    public Uri photo;

    /**
     * The URI of the thumbnail of the photo of this contact.
     */
    public Uri thumbnail;

    /**
     * The email addresses of this contact.
     */
    public Set<String> emails = new HashSet<>();

    /**
     * The phone numbers of this contact.
     */
    public Set<String> phoneNumbers = new HashSet<>();

    /**
     * Creates a new contact with the specified id.
     * @param id The id of the contact.
     */
    public DeviceContact (long id) {
        this.id = id;
    }

    @Override
    public int hashCode () {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public boolean equals (Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DeviceContact contact = (DeviceContact) o;
        return id == contact.id;
    }
}
