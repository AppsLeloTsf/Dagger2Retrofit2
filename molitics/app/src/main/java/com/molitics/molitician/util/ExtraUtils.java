package com.molitics.molitician.util;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;

import com.molitics.molitician.R;
import com.molitics.molitician.model.News;
import com.molitics.molitician.model.NewsVideoModel;

import java.io.File;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import de.hdodenhof.circleimageview.CircleImageView;

import static net.alhazmy13.mediapicker.FileProcessing.isDownloadsDocument;
import static net.alhazmy13.mediapicker.FileProcessing.isExternalStorageDocument;

/**
 * Created by Rahul on 3/3/2017.
 */

public class ExtraUtils {

    public static Typeface getRobotoBold(Context mContext) {
        return Typeface.createFromAsset(mContext.getAssets(), "fonts/roboto_bold.ttf");
    }

    public static Typeface getRobotoLight(Context mContext) {
        return Typeface.createFromAsset(mContext.getAssets(), "fonts/roboto_light.ttf");
    }

    public static Typeface getRobotoMedium(Context mContext) {
        return Typeface.createFromAsset(mContext.getAssets(), "fonts/roboto_medium.ttf");
    }

    public static Typeface getRobotoRegular(Context mContext) {
        return Typeface.createFromAsset(mContext.getAssets(), "fonts/roboto_regular.ttf");
    }

    public static Typeface getLatoBold(Context mContext) {
        return Typeface.createFromAsset(mContext.getAssets(), "fonts/Lato_Bold.ttf");
    }

    public static String getTimeDiff(Long news_time) {
        if (news_time != null) {
            Calendar calender = Calendar.getInstance();
            calender.add(Calendar.HOUR_OF_DAY, 5);
            calender.add(Calendar.MINUTE, 30);

            Long temp_date = calender.getTimeInMillis() / 1000 - news_time;
            //news_date.setTimeInMillis(temp_date * 1000);
            Long time_in_minute = TimeUnit.SECONDS.toMinutes(temp_date);
            Long time_in_hours = TimeUnit.MINUTES.toHours(time_in_minute);
            if (time_in_hours < 1) {
                return "Just now";
            } else if (time_in_hours == 1) {
                return time_in_hours + "hour Ago";
            } else if (time_in_hours > 1 && time_in_hours < 24) {
                return time_in_hours + "hours ago";
            } else if (time_in_hours >= 24 && time_in_hours < 48) {
                return "yesterday";
            } else {
                //   java.util.Date timeStamp = new java.util.Date((long) news_time * 1000);
                String timeStamp = new SimpleDateFormat("EEE MMM dd yyyy").format(news_time * 1000);
                return String.valueOf(timeStamp);
            }
        }
        return "";
    }


    public static int convertDpToPx(Context mContext, int dp) {

        final float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    public static int convertPixelsToDp(int px, Context context) {
        return px / (context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }


    public static String getGender(int gender_id) {
        List<String> genderList = new ArrayList<>();
        genderList.add("N/A");
        genderList.add("Male");
        genderList.add("Female");
        genderList.add("Other");
        if (gender_id < genderList.size())
            return genderList.get(gender_id);
        else
            return genderList.get(0);
    }

    public interface NotifyCustomDataSetChanged {
        void notifyCustom(int image_position);
    }


    public static void setBitmapImage(Context mContext, View mView, String image) {

        File f = new File(image);
        if (f.exists() && !f.isDirectory()) {
            // do something


            int targetW = mView.getWidth();
            int targetH = mView.getHeight();

            /* Get the size of the image */
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            bmOptions.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(image, bmOptions);
            int photoW = bmOptions.outWidth;
            int photoH = bmOptions.outHeight;

            /* Figure out which way needs to be reduced less */
            int scaleFactor = 1;
            if ((targetW > 0) || (targetH > 0)) {
                scaleFactor = Math.min(photoW / targetW, photoH / targetH);
            }

            /* Set bitmap options to scale the image decode target */
            bmOptions.inJustDecodeBounds = false;
            bmOptions.inSampleSize = scaleFactor;
            bmOptions.inPurgeable = true;

            /* Decode the JPEG file into a Bitmap */
            Bitmap bitmap = BitmapFactory.decodeFile(image, bmOptions);

            /* Associate the Bitmap to the ImageView */
            if (mView instanceof ImageView) {
                ImageView imageView = (ImageView) mView;
                imageView.setImageBitmap(bitmap);
            } else {
                Util.showLog("ExtraUtils", "View Should be ImageView");
            }
        } else {
            ((CircleImageView) mView).setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.sample_image));

        }
    }


    public static SpannableString setBoldPinkLastString(Context mContext, String first, String last, Float size) {
        SpannableString spannableString = new SpannableString(first + last);
        StyleSpan bold = new StyleSpan(android.graphics.Typeface.BOLD); // Span to make text bold
        spannableString.setSpan(bold, first.length(), spannableString.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.floating_button)), first.length(), spannableString.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        spannableString.setSpan(new RelativeSizeSpan(size), first.length(), spannableString.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);

        return spannableString;
    }

    public static void openPlayStore(Context mContext) {
        final String appPackageName = mContext.getPackageName(); // getPackageName() from Context or Activity object
        try {
            mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));

        } catch (android.content.ActivityNotFoundException anfe) {

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static String getRealPathFromURI_API19(Context context, Uri uri) {
        String filePath = "";

        // ExternalStorageProvider
        if (isExternalStorageDocument(uri)) {
            final String docId = DocumentsContract.getDocumentId(uri);
            final String[] split = docId.split(":");
            final String type = split[0];

            String[] column = {MediaStore.Images.Media.DATA};

            // where id is equal to
            String sel = MediaStore.Images.Media._ID + "=?";

            Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    column, sel, new String[]{type}, null);

            int columnIndex = cursor.getColumnIndex(column[0]);

            if (cursor.moveToFirst()) {
                return cursor.getString(columnIndex);
            } else if ("primary".equalsIgnoreCase(type)) {
                return Environment.getExternalStorageDirectory() + "/" + split[1];
            } else {
                if (Build.VERSION.SDK_INT > 20) {
                    //getExternalMediaDirs() added in API 21
                    File[] extenal = context.getExternalMediaDirs();
                    if (extenal.length > 1) {
                        filePath = extenal[1].getAbsolutePath();
                        filePath = filePath.substring(0, filePath.indexOf("Android")) + split[1];
                    }
                } else {
                    filePath = "/storage/" + type + "/" + split[1];
                }
                return filePath;
            }

        } else if (isDownloadsDocument(uri)) {
            Cursor cursor = null;
            final String column = "_data";
            final String[] projection = {column};

            try {
                cursor = context.getContentResolver().query(uri, projection, null, null, null);
                if (cursor != null && cursor.moveToFirst()) {
                    final int index = cursor.getColumnIndexOrThrow(column);
                    String result = cursor.getString(index);
                    cursor.close();
                    return result;
                }
            } finally {
                if (cursor != null)
                    cursor.close();
            }
        } else if (DocumentsContract.isDocumentUri(context, uri)) {
            // MediaProvider
            String wholeID = DocumentsContract.getDocumentId(uri);

            // Split at colon, use second item in the array
            String[] ids = wholeID.split(":");
            String id;
            String type;
            if (ids.length > 1) {
                id = ids[1];
                type = ids[0];
            } else {
                id = ids[0];
                type = ids[0];
            }

            Uri contentUri = null;
            if ("image".equals(type)) {
                contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            } else if ("video".equals(type)) {
                contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
            } else if ("audio".equals(type)) {
                contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
            }

            final String selection = "_id=?";
            final String[] selectionArgs = new String[]{id};
            final String column = "_data";
            final String[] projection = {column};
            Cursor cursor = context.getContentResolver().query(contentUri,
                    projection, selection, selectionArgs, null);

            if (cursor != null) {
                int columnIndex = cursor.getColumnIndex(column);

                if (cursor.moveToFirst()) {
                    filePath = cursor.getString(columnIndex);
                }
                cursor.close();
            }
            return filePath;
        } else {
            return getFileName(uri);
        }

        return null;
    }

    public static String getFileName(Uri uri) {
        if (uri == null) return null;
        String fileName = "";
        String path = uri.getPath();
        int cut;
        if (path != null) {
            cut = path.indexOf("storage");
            if (cut != -1) {
                fileName = path.substring(cut);
            }
        }
        return fileName;
    }

   /* public static void copy(Context context, Uri srcUri, File dstFile) {
        try {
            InputStream inputStream = context.getContentResolver().openInputStream(srcUri);
            if (inputStream == null) return;
            OutputStream outputStream = new FileOutputStream(dstFile);
            IOUtils.copyStream(inputStream, outputStream);
            inputStream.close();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

    public static NewsVideoModel convertNewsModleToNewsVideoModel(News news_video) {

        NewsVideoModel mNews = new NewsVideoModel();

        mNews.setDisplayType(news_video.getDisplayType());
        mNews.setContent(news_video.getContent());
        mNews.setTime(news_video.getTime());
        mNews.setType(news_video.getType());
        mNews.setSource(news_video.getSource());
        mNews.setHeading(news_video.getHeading());
        mNews.setId(news_video.getId());
        mNews.setImage(news_video.getImage());
        mNews.setLink(news_video.getLink());
        mNews.setSourceLogo(news_video.getSourceLogo());
        mNews.setSurveyId(news_video.getSurveyId());
        mNews.setVideoLink(news_video.getVideoLink());

        return mNews;
    }


    public static ArrayList<News> convertNewsVideoToNewsModule(List<NewsVideoModel> news_video_list) {
        ArrayList<News> newsList = new ArrayList<>();
        for (int i = 0; i < news_video_list.size(); i++) {
            NewsVideoModel news_video = news_video_list.get(i);
            News mNews = new News();
            mNews.setDisplayType(news_video.getDisplayType());
            mNews.setContent(news_video.getContent());
            mNews.setTime(news_video.getTime());
            mNews.setType(news_video.getType());
            mNews.setSource(news_video.getSource());
            mNews.setHeading(news_video.getHeading());
            mNews.setId(news_video.getId());
            mNews.setImage(news_video.getImage());
            mNews.setLink(news_video.getLink());
            mNews.setSourceLogo(news_video.getSourceLogo());
            mNews.setSurveyId(news_video.getSurveyId());
            mNews.setVideoLink(news_video.getVideoLink());
            newsList.add(mNews);
        }
        return newsList;
    }


    public static void setAppLanguage(Context mContext, String language) {
        //// for language

        Locale locale = new Locale(language);
        Resources resources = mContext.getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.locale = locale;
        resources.updateConfiguration(configuration, mContext.getResources().getDisplayMetrics());
    }

    public static boolean isListNullOrEmpty(Collection<?> list){
        return list == null || list.isEmpty();
    }
}

