package com.molitics.molitician.util;


import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
///import android.support.annotation.NonNull;

import androidx.annotation.NonNull;

import java.io.File;

import rx.Observable;
import rx.Subscriber;

import static com.facebook.FacebookSdk.getApplicationContext;


public class RxContactsGet {

    private static final String[] PROJECTION = {
            ContactsContract.Data.CONTACT_ID,
            ContactsContract.Data.DISPLAY_NAME_PRIMARY,
            ContactsContract.Data.STARRED,
            ContactsContract.Data.PHOTO_URI,
            ContactsContract.Data.PHOTO_THUMBNAIL_URI,
            ContactsContract.Data.DATA1,
            ContactsContract.Data.MIMETYPE,
            ContactsContract.Data.IN_VISIBLE_GROUP
    };

    private ContentResolver resolver;

    /**
     * Fetches all contacts from the contacts apps and social networking apps.
     *
     * @param context The context.
     * @return Observable that emits contacts on success.
     */
    public static Observable<File> fetch(@NonNull final Context context) {
        return Observable.unsafeCreate(subscriber -> new RxContactsGet(context).fetch(subscriber));
    }

    private RxContactsGet(@NonNull Context context) {
        resolver = context.getContentResolver();
    }

    private void fetch(Subscriber<? super File> subscriber) {

        // Emit the contacts
        File file = Util.getContactFromDevice(getApplicationContext());
        subscriber.onNext(file);

        subscriber.onCompleted();
    }
}
