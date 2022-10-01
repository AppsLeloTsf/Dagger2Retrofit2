package com.molitics.molitician.GoogleAnalytics;

import android.app.Activity;

public class GAEventTracking {
    public static final String USER_ID = "User Id :";
    public static final String IDENTIFIER = "Identifier";

    public static void googleEventTracker(Activity mContext, String category, String action) {

     /*   int userID = PrefUtil.getInt(Constant.PreferenceKey.USER_ID);
        if (userID != -1) {
            MolticsApplication application = (MolticsApplication) mContext.getApplication();
            Tracker tracker = application.getDefaultTracker();

            tracker.send(new HitBuilders.ScreenViewBuilder().build());
            // Build and send an Event.
            tracker.send(new HitBuilders.EventBuilder()
                    .setCategory(category)
                    .setAction(action)
                    .setLabel(USER_ID + userID)
                    .build());

            String dimensionValue = String.valueOf(userID);
            tracker.set(Constant.GoogleAnalyticsCustomDimension.CUSTOM_USER_ID, dimensionValue);
        }*/
    }

    public static void googleEventTrackerCustomId(Activity mContext, String identifier, String category, String action) {


       /* MolticsApplication application = (MolticsApplication) mContext.getApplication();
        Tracker tracker = application.getDefaultTracker();

        // Build and send an Event.
        tracker.send(new HitBuilders.EventBuilder()
                .setCategory(category)
                .setAction(action)
                .setLabel(IDENTIFIER + identifier)
                .build());

        String dimensionValue = String.valueOf(identifier);
        tracker.set(Constant.GoogleAnalyticsCustomDimension.CUSTOM_USER_ID, dimensionValue);*/
    }
}
