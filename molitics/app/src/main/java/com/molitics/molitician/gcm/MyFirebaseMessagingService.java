package com.molitics.molitician.gcm;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.molitics.molitician.R;
import com.molitics.molitician.database.Database;
import com.molitics.molitician.ui.dashboard.DashBoardActivity;
import com.molitics.molitician.util.Constant;
import com.molitics.molitician.util.PrefUtil;
import com.molitics.molitician.util.Util;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    JSONObject notification_json_payload;

    static final String channel_id = "molitics_1111";
    private final String TAG = MyFirebaseMessagingService.class.getSimpleName();

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Map<String, String> data = remoteMessage.getData();

        notification_json_payload = new JSONObject();

        if (!data.isEmpty()) {
            String message = data.get("message");
            String notification_id = data.get("notification_id");
            String notification_type = data.get("notification_type");

            int value = Integer.valueOf(notification_type);
            String title = data.get("title");
            String large_image = data.get("notification_image");
            notification_json_payload = new JSONObject();

            if (PrefUtil.getInt(Constant.PreferenceKey.NOTIFICATION_ACTIVE) == 1) {
                try {
                    switch (value) {
                        case 1:
                            notification_json_payload.put("", "");
                            break;

                        case Constant.NotificationType.NEWS_NOTI:
                            notification_json_payload.put(Constant.NotificationPayLoad.NOTIFICATION_NEWS_ID, data.get("news"));
                            insertNotificationData(message, notification_type, notification_id);
                            break;

                        case 3:
                            notification_json_payload.put(Constant.NotificationPayLoad.NOTIFICATION_CANDIDATE_ID, data.get(Constant.NotificationPayLoad.NOTIFICATION_CANDIDATE_ID));
                            insertNotificationData(message, notification_type, notification_id);
                            break;

                        case 4:
                            notification_json_payload.put("", "");
                            break;

                        case Constant.NotificationType.SURVEY_NOTI:
                            setSurveyNotification();
                            notification_json_payload.put(Constant.NotificationPayLoad.NOTIFICATION_SURVEY_ID, data.get(Constant.NotificationPayLoad.NOTIFICATION_SURVEY_ID));
                            insertNotificationData(message, notification_type, notification_id);
                            break;

                        case Constant.NotificationType.LEADER_NOTI:
                            notification_json_payload.put(Constant.NotificationPayLoad.LEADER_NAME, data.get(Constant.NotificationPayLoad.LEADER_NAME));
                            notification_json_payload.put(Constant.NotificationPayLoad.LEADER_ID, data.get(Constant.NotificationPayLoad.LEADER_ID));
                            insertNotificationData(message, notification_type, notification_id);
                            break;
                        case Constant.NotificationType.ARTICLE_NOTI:
                            notification_json_payload.put(Constant.NotificationPayLoad.NOTIFICATION_NEWS_ID, data.get("news"));
                            insertNotificationData(message, notification_type, notification_id);
                            break;
                        case Constant.NotificationType.VOICE_NOTI:
                            notification_json_payload.put(Constant.NotificationPayLoad.VOICE_ID, data.get(Constant.NotificationPayLoad.VOICE_ID));
                            insertNotificationData(message, notification_type, notification_id);
                            break;
                        case Constant.NotificationType.CARTOON_NOTI:
                            PrefUtil.putBoolean(Constant.PreferenceKey.NOTIFICATION_CARTOON, true);
                            notification_json_payload.put(Constant.NotificationPayLoad.NOTIFICATION_NEWS_ID, data.get(Constant.NotificationPayLoad.CARTOON_ID));
                            notification_json_payload.put(Constant.NotificationPayLoad.CARTOON_IMAGE, large_image);
                            insertNotificationData(message, notification_type, notification_id);
                            break;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                HashMap<String, String> notiData = new HashMap<String, String>(data);
                sendNotification(title, message, notification_id, large_image, value, notiData);
            } else {
                System.out.println("off_notification");
            }
        }
    }

    private void insertNotificationData(String message, String notification_type, String notification_id) {
        int temp_count = 0;
        temp_count = PrefUtil.getInt(Constant.PreferenceKey.NOTIFICATION_COUNT) + 1;
        PrefUtil.putInt(Constant.PreferenceKey.NOTIFICATION_COUNT, temp_count);
        Database.saveNotification(notification_id, message, getNotificationTime(), getNotificationDate(), notification_type, notification_json_payload.toString());
    }

    private void setSurveyNotification() {
        int temp_count = 0;
        temp_count = PrefUtil.getInt(Constant.PreferenceKey.NOTIFICATION_SURVEY_COUNT) + 1;
        PrefUtil.putInt(Constant.PreferenceKey.NOTIFICATION_SURVEY_COUNT, temp_count);
    }

    private String getNotificationDate() {
        Calendar c = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat df1 = new SimpleDateFormat("dd/MM/yyyy");
        return df1.format(c.getTime());
    }

    private String getNotificationTime() {
        String am_pm;
        Calendar c = Calendar.getInstance();
        int seconds = c.get(Calendar.SECOND);
        int minute = c.get(Calendar.MINUTE);
        int hours = c.get(Calendar.HOUR);
        int hours_of_day = c.get(Calendar.HOUR_OF_DAY);

        if (hours == 0) {
            am_pm = "P.M";
        } else if (hours_of_day > 0 && hours_of_day < 13)
            am_pm = " A.M";
        else am_pm = " P.M";

        String output = String.format("%02d:%02d", hours, minute);
        /// return hours + ":" + minute + " " + am_pm;
        return output + am_pm;
    }

    @Override
    public void onNewToken(String token) {
        super.onNewToken(token);
        Util.showLog("Fcm-token:", token);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private NotificationManager createNotificationChannel() {
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        CharSequence channelName = "Molitics";
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel notificationChannel = new NotificationChannel(channel_id, channelName, importance);
        notificationChannel.enableLights(true);
        notificationChannel.setLightColor(Color.RED);
        notificationChannel.enableVibration(true);
        notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
        notificationManager.createNotificationChannel(notificationChannel);
        return notificationManager;
    }

    private void sendNotification(String title, String message, String notification_id, String large_image_url, int notification_type, HashMap<String, String> sendBundle) {
        Intent intent;

        if (notification_type == 10) {
            final String appPackageName = getPackageName();

            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName));
        } else {
            intent = new Intent(this, DashBoardActivity.class);
            intent.putExtra(Constant.IntentKey.NOTIFICATION_DATA, sendBundle);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        }

        PendingIntent pendingIntent = PendingIntent.getActivity(this, Integer.valueOf(notification_id), intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        Drawable myDrawable = getResources().getDrawable(R.mipmap.ic_launcher);
        Bitmap LargeIconImage = ((BitmapDrawable) myDrawable).getBitmap();


        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, channel_id)
                .setLargeIcon(LargeIconImage)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setTicker(message)
                .setDefaults(2)
                .setContentIntent(pendingIntent)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(message));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            notificationBuilder.setSmallIcon(R.drawable.small_logo);
            notificationBuilder.setColor(getResources().getColor(R.color.theme));
        } else {
            notificationBuilder.setSmallIcon(R.drawable.ic_launcher);
        }

        if (large_image_url != null && !large_image_url.isEmpty()) {
            try {
                URL url = new URL(large_image_url);
                Bitmap mLarge_image = BitmapFactory.decodeStream(url.openStream());
                notificationBuilder.setStyle(new NotificationCompat.BigPictureStyle().setSummaryText(message)
                        .bigPicture(mLarge_image));
            } catch (IOException e) {
                System.out.println(e);
            }
        }

        if (notification_type == 2) {
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            String headLine = "";
            headLine = message + "-" + "Molitics";
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, headLine);
            shareIntent.putExtra(Intent.EXTRA_TITLE, "Molitics");
            String url = "http://www.molitics.net/news/news-web/" + sendBundle.get("news");
            String shareText = headLine + "\n" + url + "\n" + "via @MoliticsIndia";
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareText);
            PendingIntent sharePendingIntent = PendingIntent.getActivity(this, 0  /* Request code */, Intent.createChooser(shareIntent, "Share link using"),
                    PendingIntent.FLAG_UPDATE_CURRENT);

            notificationBuilder.addAction(R.drawable.share, "Share News", sharePendingIntent);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel().notify(Integer.parseInt(notification_id) /* ID of notification */,
                    notificationBuilder.build());
        } else {
            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            assert notificationManager != null;
            notificationManager.notify(Integer.valueOf(notification_id) /* ID of notification */, notificationBuilder.build());
        }

    }
}
