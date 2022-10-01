package com.molitics.molitician;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by rohitkumar on 9/30/16.
 */
public class MoliticsPreference {

    public static final String MYPREF = "moliticspreference";
    public static final String MYCONSTANTPREF = "moliticsConstantPreference";

    static SharedPreferences myPreference;
    static SharedPreferences myConstantPreference;

    public static SharedPreferences getInstance(Context context) {
        if (myPreference != null) {
            return myPreference;
        } else {
            myPreference = context.getSharedPreferences(MYPREF, Context.MODE_PRIVATE);
            return myPreference;
        }
    }

    public static SharedPreferences getConstantInstance(Context context) {
        if (myConstantPreference != null) {
            return myConstantPreference;
        } else {
            myConstantPreference = context.getSharedPreferences(MYCONSTANTPREF, Context.MODE_PRIVATE);
            return myConstantPreference;
        }
    }
}
