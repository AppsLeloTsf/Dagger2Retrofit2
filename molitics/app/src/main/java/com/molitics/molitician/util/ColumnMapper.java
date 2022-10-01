package com.molitics.molitician.util;



import android.database.Cursor;
import android.net.Uri;

import com.molitics.molitician.ui.dashboard.more.model.DeviceContact;

public class ColumnMapper {

    // Utility class -> No instances allowed
    private ColumnMapper () {}

    static void mapInVisibleGroup (Cursor cursor, DeviceContact contact, int columnIndex) {
        contact.inVisibleGroup = cursor.getInt(columnIndex);
    }

    static void mapDisplayName (Cursor cursor, DeviceContact contact, int columnIndex) {
        String displayName = cursor.getString(columnIndex);
        if (displayName != null && !displayName.isEmpty()) {
            contact.displayName = displayName;
        }
    }

    static void mapEmail (Cursor cursor, DeviceContact contact, int columnIndex) {
        String email = cursor.getString(columnIndex);
        if (email != null && !email.isEmpty()) {
            contact.emails.add(email);
        }
    }

    static void mapPhoneNumber (Cursor cursor, DeviceContact contact, int columnIndex) {
        String phoneNumber = cursor.getString(columnIndex);
        if (phoneNumber != null && !phoneNumber.isEmpty()) {
            // Remove all whitespaces
            phoneNumber = phoneNumber.replaceAll("\\s+","");
            contact.phoneNumbers.add(phoneNumber);
        }
    }

    static void mapPhoto (Cursor cursor, DeviceContact contact, int columnIndex) {
        String uri = cursor.getString(columnIndex);
        if (uri != null && !uri.isEmpty()) {
            contact.photo = Uri.parse(uri);
        }
    }

    static void mapStarred (Cursor cursor, DeviceContact contact, int columnIndex) {
        contact.starred = cursor.getInt(columnIndex) != 0;
    }

    static void mapThumbnail (Cursor cursor, DeviceContact contact, int columnIndex) {
        String uri = cursor.getString(columnIndex);
        if (uri != null && !uri.isEmpty()) {
            contact.thumbnail = Uri.parse(uri);
        }
    }
}
