package com.molitics.molitician.ui.dashboard.cartoon.detail;

import android.Manifest;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.molitics.molitician.R;
import com.molitics.molitician.customView.TouchImageView;
import com.molitics.molitician.ui.dashboard.branch_deep_link.BranchShareClass;
import com.molitics.molitician.ui.dashboard.cartoon.cartoonModel.AllCartoonModel;
import com.molitics.molitician.util.Constant;
import com.molitics.molitician.util.DownloadFiles;
import com.molitics.molitician.util.MoliticsAppPermission;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.molitics.molitician.ui.dashboard.cartoon.MoliticsImageUtils.TYPE_DOWNLOAD;
import static com.molitics.molitician.ui.dashboard.cartoon.MoliticsImageUtils.TYPE_SHARE;
import static com.molitics.molitician.ui.dashboard.cartoon.MoliticsImageUtils.saveImage;
import static com.molitics.molitician.util.MoliticsAppPermission.STORAGE_PERMISSION;
import static com.molitics.molitician.util.MoliticsAppPermission.checkWritePermission;
import static com.molitics.molitician.util.MoliticsAppPermission.requestReadStoragePermission;

/**
 * Created by rahul on 30/05/18.
 */

public class CartoonDetailHolderFragment extends Fragment {

    @BindView(R.id.cartoon_image_view)
    TouchImageView cartoon_image_view;
    @BindView(R.id.back_image)
    ImageView back_image;
    @BindView(R.id.share_image)
    ImageView share_image;
    @BindView(R.id.download_image)
    ImageView download_image;

    private String image_url = "";

    public static CartoonDetailHolderFragment getInstance(int position, AllCartoonModel data) {
        CartoonDetailHolderFragment viewHolder = new CartoonDetailHolderFragment();

        Bundle mBundle = new Bundle();
        mBundle.putParcelable(Constant.IntentKey.CARTOON_LIST, data);

        viewHolder.setArguments(mBundle);

        return viewHolder;
    }

    @Override
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle bundle) {
        View mView = inflater.inflate(R.layout.fragment_cartoon_detail, container, false);
        ButterKnife.bind(this, mView);

        Bundle mBundle = getArguments();
        AllCartoonModel model = mBundle.getParcelable(Constant.IntentKey.CARTOON_LIST);
        if (model != null) {
            if (!TextUtils.isEmpty(model.getImage())) {
                image_url = model.getImage();
                Picasso.with(getContext()).load(image_url).placeholder(R.drawable.image_placeholder).error(R.drawable.internet_no_cloud).into(cartoon_image_view);
            } else {
                cartoon_image_view.setImageResource(R.drawable.image_placeholder);
            }
        }
        setClickListener();
        return mView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_download_share, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void setClickListener() {
        back_image.setOnClickListener(v -> {
            getActivity().finish();
        });
        share_image.setOnClickListener(v -> {
            shareImage(image_url, DownloadFiles.SHARE_TYPE);
        });
        download_image.setOnClickListener(v -> {
            shareImage(image_url, DownloadFiles.DOWNLOAD_TYPE);
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.status_download) {
            if (checkStoragePermission())
                saveImage(getContext(), TYPE_DOWNLOAD, image_url);
        } else if (item.getItemId() == R.id.status_share) {
            if (checkStoragePermission())
                saveImage(getContext(), TYPE_SHARE, image_url);
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean checkStoragePermission() {
        if (checkWritePermission()) {
            return true;
        } else {
            MoliticsAppPermission.requestStoragePermission(getActivity());
            return false;
        }
    }

    private void shareImage(String url, int action) {
        if (!checkWritePermission()) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, STORAGE_PERMISSION);
        } else {
            DownloadFiles.saveImage(getContext(), action, url);
        }

    }
}
