package com.molitics.molitician.ui.dashboard.cartoon;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;

import androidx.core.content.FileProvider;

import android.widget.ImageView;
import android.widget.Toast;

import com.molitics.molitician.R;
import com.molitics.molitician.util.Constant;
import com.molitics.molitician.util.Util;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;

/**
 * Created by rahul on 15/06/18.
 */

public class MoliticsImageUtils {

    public static final int TYPE_SHARE = 0;
    public static final int TYPE_DOWNLOAD = 1;

    public static void saveImage(Context mContext, final int type, String image_url) {
        Picasso.with(mContext)
                .load(image_url)
                .into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        File file = Util.saveImage(mContext, bitmap);
                        if (type == TYPE_SHARE) {
                            Intent share = new Intent(Intent.ACTION_SEND);
                            share.setType("image/*");

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

                                Uri photoURI = FileProvider.getUriForFile(mContext,
                                        "com.molitics.molitician.provider", file);
                                share.putExtra(Intent.EXTRA_STREAM, photoURI);

                            } else {
                                share.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
                            }
                            share.putExtra(Intent.EXTRA_TEXT, Constant.MOLITICS_BRANCHLINK);

                            share.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            mContext.startActivity(Intent.createChooser(share, mContext.getString(R.string.share_image)));

                        } else if (type == TYPE_DOWNLOAD) {
                            if (file.getPath() != null)
                                Toast.makeText(mContext, mContext.getString(R.string.image_saved_to_sd_card), Toast.LENGTH_SHORT).show();
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

    public static void displayLocalImage(ImageView imageView, String file) {
        File imgFile = new File(file);
        if (imgFile.exists()) {

            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            imageView.setImageBitmap(myBitmap);
        }
    }

}
