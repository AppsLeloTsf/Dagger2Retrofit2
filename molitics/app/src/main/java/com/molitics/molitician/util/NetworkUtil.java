package com.molitics.molitician.util;

import android.content.Context;
import android.net.ConnectivityManager;

/**
 * Created by rahul on 1/30/2017.
 */

public class NetworkUtil {

    public static boolean isNetworkConnected(Context mContext) {
        ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);

        assert cm != null;
        return cm.getActiveNetworkInfo() != null;
    }
}
