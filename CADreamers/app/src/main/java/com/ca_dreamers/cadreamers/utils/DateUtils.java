package com.ca_dreamers.cadreamers.utils;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtils {


    public static long dateToMiliSeconds(String strDate){
        String myDate = strDate;
        SimpleDateFormat sdf = new SimpleDateFormat(Constant.DATE_FORMAT_dd_MMMM_yyyy, Locale.UK);
        Date date = null;
        try {
            date = sdf.parse(myDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.getTime();

    }
    public static long dateToMiliSecondsYyyy(String strDate){
        String myDate = strDate;
        SimpleDateFormat sdf = new SimpleDateFormat(Constant.DATE_FORMAT_yyyy__MM__dd, Locale.UK);
        Date date = null;
        try {
            date = sdf.parse(myDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.getTime();

    }

    public static String getTodayDate() {
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat(Constant.DATE_FORMAT_dd_MMMM_yyyy, Locale.UK);
        return df.format(c);
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String getFirstDateOfMonthOreo() {

        LocalDate todaydate = LocalDate.now();
        System.out.println("Months first date in yyyy-mm-dd: " +todaydate.withDayOfMonth(1));
        return String.valueOf(todaydate.withDayOfMonth(1));
    }
    public static String getFirstDateOfMonth() {
        Calendar aCalendar = Calendar.getInstance();

        aCalendar.set(Calendar.DATE, 1);
        aCalendar.set(Calendar.HOUR_OF_DAY, 0);
        aCalendar.set(Calendar.MINUTE, 0);
        aCalendar.set(Calendar.SECOND, 0);

        Date firstDateOfCurrentMonth = aCalendar.getTime();

        SimpleDateFormat sdf = new SimpleDateFormat(Constant.DATE_FORMAT_dd_MMMM_yyyy, Locale.UK);
       // sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
//        String dayFirst = sdf.format(firstDateOfCurrentMonth);
//        System.out.println(dayFirst);
        return sdf.format(firstDateOfCurrentMonth);
    }

    public static String getCurrentTime() {

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat(Constant.TIME_FORMAT_hh_mm, Locale.getDefault());
        String currentTime = df.format(c);
        return currentTime;

    }
    public static Boolean compareDate(String valid_until) {

        SimpleDateFormat sdf = new SimpleDateFormat(Constant.DATE_FORMAT_dd_MMMM_yyyy, Locale.UK);
        Date strDate = null;
        try {
            strDate = sdf.parse(valid_until);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return System.currentTimeMillis() > strDate.getTime();
    }
    public static Boolean compareDateEqual(String valid_until) {
        SimpleDateFormat sdf = new SimpleDateFormat(Constant.DATE_FORMAT_dd_MMMM_yyyy, Locale.UK);
        Date strDate = null;
        try {
            strDate = sdf.parse(valid_until);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return System.currentTimeMillis() < strDate.getTime();
    }
    public static Boolean compareDateBefore(String valid_until) {
        SimpleDateFormat sdf = new SimpleDateFormat(Constant.DATE_FORMAT_dd_MMMM_yyyy, Locale.UK);
        Date strDate = null;
        try {
            strDate = sdf.parse(valid_until);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return System.currentTimeMillis() < strDate.getTime();
    }


    public static String getDeliveryDate(String dateToday) {

        SimpleDateFormat sdf = new SimpleDateFormat(Constant.DATE_FORMAT_DD_MMM_YYYY, Locale.UK);
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(sdf.parse(dateToday));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //Number of Days to add
        c.add(Calendar.DAY_OF_MONTH, 2);

        return sdf.format(c.getTime());
    }
    public static String getPresentDay() {

        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        return new SimpleDateFormat(Constant.DATE_FORMAT_DD_MMM_YYYY, Locale.ENGLISH).format(date.getTime());
    }
    public static String timeDiff(String dateStart, String dateStop) {
        String dH = "";
        try {
            SimpleDateFormat format = new SimpleDateFormat(Constant.DATE_FORMAT_DD_MMM_YYYY, Locale.UK);

            Date d1 = format.parse(dateStart);
            Date d2 = format.parse(dateStop);
            long diff = d2.getTime() - d1.getTime();

            long diffMinutes = diff / (60 * 1000) % 60;
            long diffHours = diff / (60 * 60 * 1000) % 24;
            long diffDays = diff / (24 * 60 * 60 * 1000);
            if (diffDays > 0) {
                dH = String.valueOf(diffDays + " d " + (diffHours) + " hr " + (diffMinutes) + " min");
            } else if (diffHours > 0) {
                dH = String.valueOf((diffHours) + " hr " + (diffMinutes) + " min");
            } else {
                dH = String.valueOf((diffMinutes) + " min");
            }


        } catch (Exception e) {
            Log.e(Constant.TAG,e.toString());
        }
        return dH;
    }
    public static String date3Months() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -3);
        Date date = cal.getTime();
        return new SimpleDateFormat(Constant.DATE_FORMAT_dd_MMMM_yyyy, Locale.ENGLISH).format(date.getTime());

    }
    public static String date6Months() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -6);
        Date date = cal.getTime();
        return new SimpleDateFormat(Constant.DATE_FORMAT_dd_MMMM_yyyy, Locale.ENGLISH).format(date.getTime());

    }
    public static String date9Months() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -9);
        Date date = cal.getTime();
        return new SimpleDateFormat(Constant.DATE_FORMAT_dd_MMMM_yyyy, Locale.ENGLISH).format(date.getTime());

    }
    public static String date12Months() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -12);
        Date date = cal.getTime();
        return new SimpleDateFormat(Constant.DATE_FORMAT_dd_MMMM_yyyy, Locale.ENGLISH).format(date.getTime());

    }

    public static String compareCurrent(){
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(Constant.DATE_FORMAT_DD_MMM_YYYY, Locale.UK);
        String getCurrentDateTime = sdf.format(c.getTime());
        return getCurrentDateTime;
    }

    public static String convertFormat(String strOldDate){

        try {
            Date date=new SimpleDateFormat(Constant.DATE_FORMAT_dd_MMMM_yyyy,Locale.UK).parse(strOldDate);
            SimpleDateFormat formatter = new SimpleDateFormat(Constant.DATE_FORMAT_yyyy__MM__dd, Locale.UK);
            return formatter.format(date);

        } catch (ParseException e) {
            e.printStackTrace();
        }
       return null;
    }
    public static String dateFormatDdMmmYyyy(String strOldDate){

        try {
            Date date=new SimpleDateFormat(Constant.yyyy_MM_dd_HH_mm_ss, Locale.UK).parse(strOldDate);
            SimpleDateFormat formatter = new SimpleDateFormat(Constant.DATE_FORMAT_DD_MMM_YYYY, Locale.UK);
            return formatter.format(date);

        } catch (ParseException e) {
            e.printStackTrace();
        }
       return null;
    }
    public static String convertFormatDateTime(String strOldDate){

        try {
            Date date=new SimpleDateFormat(Constant.yyyy_MM_dd_HH_mm_ss,Locale.UK).parse(strOldDate);
            SimpleDateFormat formatter = new SimpleDateFormat(Constant.DATE_FORMAT_DD_MMM_YYYY_hh_mm, Locale.UK);
            return formatter.format(date);

        } catch (ParseException e) {
            e.printStackTrace();
        }
       return null;
    }
    public static String yyyy_MM_dd_HH_mm_ss(String strOldDate){

        try {
            Date date=new SimpleDateFormat(Constant.yyyy_MM_dd_HH_mm_ss,Locale.UK).parse(strOldDate);
            SimpleDateFormat formatter = new SimpleDateFormat(Constant.DATE_FORMAT_dd_MMMM_yyyy, Locale.UK);
            return formatter.format(date);

        } catch (ParseException e) {
            e.printStackTrace();
        }
       return null;
    }
    public static String convertYyyyToDd(String strOldDate){

        try {
            Date date=new SimpleDateFormat(Constant.DATE_FORMAT_yyyy__MM__dd,Locale.UK).parse(strOldDate);
            SimpleDateFormat formatter = new SimpleDateFormat(Constant.DATE_FORMAT_dd_MMMM_yyyy, Locale.UK);
            return formatter.format(date);

        } catch (ParseException e) {
            e.printStackTrace();
        }
       return null;
    }
    public static String convertDdToYyyy(String strOldDate){

        try {
            Date date=new SimpleDateFormat(Constant.DATE_FORMAT_dd_M_yyyy,Locale.UK).parse(strOldDate);
            SimpleDateFormat formatter = new SimpleDateFormat(Constant.DATE_FORMAT_DD_MMM_YYYY, Locale.UK);
            return formatter.format(date);

        } catch (ParseException e) {
            e.printStackTrace();
        }
       return null;
    }


}
