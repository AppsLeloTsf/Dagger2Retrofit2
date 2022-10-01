package com.molitics.molitician.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;


import android.view.View;

import androidx.core.content.FileProvider;
import androidx.fragment.app.DialogFragment;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

import static com.facebook.FacebookSdk.getCacheDir;
import static com.molitics.molitician.util.VideoExpoPlayer.generateUUID;

/**
 * Created by Rahul on 10/28/2016.
 */

public class ShareScreenShot {
    public static void takeDialogScreenshot(DialogFragment mDialog, Activity mContext, String displayText, String share_link) {
        Date now = new Date();
        android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);

        try {
            String mPath = Environment.getExternalStorageDirectory().toString() + "/" + now + ".jpeg";


            View v1 = mDialog.getDialog().getWindow().getDecorView().getRootView();
            v1.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(v1.getDrawingCache());
            v1.setDrawingCacheEnabled(false);

            File imageFile = new File(mPath);

            FileOutputStream outputStream = new FileOutputStream(imageFile);
            int quality = 100;
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
            outputStream.flush();
            outputStream.close();
            shareNews(mContext, imageFile, displayText, share_link);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public static void takeViewScreenshot(Context mContext, View view, String url_link) {
        try {
            // image naming and path  to include sd card  appending name you choose for file
            File outputDir = getCacheDir(); // context being the Activity pointer
            // create bitmap screen capture

            //View v1 = mContext.getWindow().getDecorView().getRootView();
            view.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
            view.setDrawingCacheEnabled(false);

            File imageFile = File.createTempFile(generateUUID(), ".jpg", outputDir);

            FileOutputStream outputStream = new FileOutputStream(imageFile);
            int quality = 100;
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
            outputStream.flush();
            outputStream.close();
            shareNews(mContext, imageFile, "", url_link);
            //shareNews(mContext, imageFile, displayText);
            //   openScreenshot(mContext, imageFile);
        } catch (Throwable e) {
            // Several error may come out with file handling or OOM
            e.printStackTrace();
        }
    }


    public static void takeScreenshot(Context mContext, String displayText, String share_link) {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        String headLine;
        headLine = displayText + "-" + "Molitics";
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, headLine);
        shareIntent.putExtra(Intent.EXTRA_TITLE, "Molitics");
        String shareText = headLine + "\n" + share_link + "\n" + "via @MoliticsIndia";
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareText);
        mContext.startActivity(Intent.createChooser(shareIntent, "Share link using"));
    }

    private static void shareNews(Context mContext, File imageFile, String displayText, String link) {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        String app_link = displayText + link;
        Uri uri = FileProvider.getUriForFile(mContext, mContext.getApplicationContext().getPackageName() + ".provider", imageFile);
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, app_link);
        shareIntent.putExtra(Intent.EXTRA_TEXT, app_link);
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        shareIntent.setType("image/*");
        mContext.startActivity(Intent.createChooser(shareIntent, "Share link using"));
    }
}
