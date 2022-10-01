package com.molitics.molitician.util;

import java.util.concurrent.TimeUnit;

public class MTimeUtils {


    public static long getToHour(long millisUntilFinished) {
        return (TimeUnit.MILLISECONDS.toHours(millisUntilFinished) - (TimeUnit.MILLISECONDS.toDays(millisUntilFinished) * 24));
    }

    public static long getToMin(long millisUntilFinished) {
        return (TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(
                TimeUnit.MILLISECONDS.toHours(millisUntilFinished)));
    }

    public static long getToSec(long millisUntilFinished) {
        return (TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
                TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)));

    }

}
