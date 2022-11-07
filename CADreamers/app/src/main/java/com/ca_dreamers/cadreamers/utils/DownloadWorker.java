package com.ca_dreamers.cadreamers.utils;

import android.app.DownloadManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.ca_dreamers.cadreamers.R;
import com.ca_dreamers.cadreamers.activity.VimeoPlayerActivity;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.NoSuchPaddingException;

import static android.content.Context.DOWNLOAD_SERVICE;

public class DownloadWorker extends Worker {

    private LiveDataHelper liveDataHelper;
    private int Number;
    private DownloadManager downloadManager;
    private String fileName=null;
    private long downLoadId;

    public DownloadWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        liveDataHelper = LiveDataHelper.getInstance();
    }
    @NonNull
    @Override
    public Result doWork() {
        try {
            String strUrl = "https://vod-progressive.akamaized.net/exp=1633689323~acl=%2Fvimeo-prod-skyfire-std-us%2F01%2F3716%2F24%2F618583504%2F2870582999.mp4~hmac=3fa8337e0bdacc84b2e87cbdd2b80935dbfdc1870bddfcbd0a17bd28f08bd38d/vimeo-prod-skyfire-std-us/01/3716/24/618583504/2870582999.mp4";
            URL u = new URL(strUrl);
            URLConnection c = u.openConnection();
            c.connect();

            int lengthOfFile = c.getContentLength();

          //  double i = (Math.floor(Math.log(lengthOfFile) / Math.log(1024)));
            Log.d("TSF_DOWNLOAD", "Size of file: "+lengthOfFile+"Bytes");
            InputStream in = c.getInputStream();
            byte[] buffer = new byte[1024];
            int len1;
            long total = 0;
            while ((len1 = in.read(buffer)) > 0) {
                total += len1; //total = total + len1
                int percent = (int) ((total * 100) / lengthOfFile);
                liveDataHelper.updatePercentage(percent);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure();
        }

        Log.d("TSF_DOWNLOAD", Result.success().toString());

        return Result.success();
    }

}