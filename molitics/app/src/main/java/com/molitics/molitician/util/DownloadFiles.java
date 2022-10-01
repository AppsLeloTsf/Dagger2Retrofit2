package com.molitics.molitician.util;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;

import androidx.core.content.FileProvider;

import android.widget.Toast;

import com.molitics.molitician.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.ResponseBody;

/**
 * Created by om on 21/06/18.
 */

public class DownloadFiles {

    public static boolean writeResponseBodyToDisk(ResponseBody body) {
        try {
            File futureStudioIconFile = Util.getVideoAlbumDir();

            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                byte[] fileReader = new byte[4096];

                inputStream = body.byteStream();
                outputStream = new FileOutputStream(futureStudioIconFile);

                while (true) {
                    int read = inputStream.read(fileReader);

                    if (read == -1) {
                        break;
                    }
                    outputStream.write(fileReader, 0, read);
                }

                outputStream.flush();

                return true;
            } catch (IOException e) {
                System.out.print(e.getMessage());
                e.getStackTrace();
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            System.out.print(e.getMessage());
            e.getStackTrace();
            return false;
        }
    }

    public static int SHARE_TYPE = 0;
    public static int DOWNLOAD_TYPE = 1;

    public static void saveImage(Context mContext, final int type, String image_url) {
        Picasso.with(mContext)
                .load(image_url)
                .placeholder(R.drawable.image_placeholder)
                .into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        File file = Util.saveImage(mContext, bitmap);
                        if (type == SHARE_TYPE) {
                            Intent share = new Intent(Intent.ACTION_SEND);
                            share.setType("image*//*");

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

                                Uri photoURI = FileProvider.getUriForFile(mContext,
                                        "com.molitics.molitician.provider", file);
                                share.putExtra(Intent.EXTRA_STREAM, photoURI);

                            } else {
                                share.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
                            }
                            share.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            mContext.startActivity(Intent.createChooser(share, "Share Image"));

                        } else if (type == DOWNLOAD_TYPE) {
                            if (file.getPath() != null)
                                Toast.makeText(mContext, "Image Saved to SD Card",
                                        Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onBitmapFailed(Drawable errorDrawable) {
                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {
                    }
                });
    }

}
