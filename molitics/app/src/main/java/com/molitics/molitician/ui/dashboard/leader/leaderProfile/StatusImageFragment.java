package com.molitics.molitician.ui.dashboard.leader.leaderProfile;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;

import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.molitics.molitician.R;
import com.molitics.molitician.customView.TouchImageView;
import com.molitics.molitician.util.MoliticsAppPermission;
import com.molitics.molitician.util.Util;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by rahul on 7/14/2017.
 */

public class StatusImageFragment extends Fragment {
    @BindView(R.id.status_image)
    TouchImageView status_image;


    private String image_url = "";

    public static Fragment getInstance(String image) {
        Fragment mFragment = new StatusImageFragment();
        Bundle mBundle = new Bundle();
        mBundle.getString("image", image);

        mFragment.setArguments(mBundle);

        return mFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View mView = inflater.inflate(R.layout.image_view, container, false);
        ButterKnife.bind(this, mView);

        Bundle mBundle = getArguments();

        assert mBundle != null;
        image_url = mBundle.getString("image");
        if (!TextUtils.isEmpty(image_url))
            Picasso.with(getContext()).load(image_url).into(status_image);

        mView.setOnTouchListener((v, event) -> true);
        return mView;
    }

    @OnClick(R.id.back)
    public void onBackClick() {
        getActivity().getSupportFragmentManager().popBackStack();
    }

    @OnClick(R.id.image_download)
    public void onImageDownload() {
        if (MoliticsAppPermission.checkWritePermission())
            saveImage(1);
        else
            MoliticsAppPermission.requestReadStoragePermission(getContext());
    }

    @OnClick(R.id.image_share)
    public void onImageShareClick() {
        saveImage(0);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.menu_download_share, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.status_share) {
            return true;
        } else if (item.getItemId() == R.id.status_download) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void saveImage(final int type) {
        Picasso.with(getContext())
                .load(image_url)
                .into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        File file = Util.saveImage(getActivity(), bitmap);
                        if (type == 0) {
                            Intent share = new Intent(Intent.ACTION_SEND);

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

                                Uri photoURI = FileProvider.getUriForFile(getContext(),
                                        "com.molitics.molitician.provider", file);
                                share.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);

                            } else {
                                share.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
                            }
                            share.setType("image/jpeg");
                            share.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            getContext().startActivity(Intent.createChooser(share, getString(R.string.share_image)));
                        } else if (type == 1) {
                            // Show a toast message on successful save
                            if (file.getPath() != null)
                                Toast.makeText(getContext(), getString(R.string.image_saved_to_sd_card),
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
