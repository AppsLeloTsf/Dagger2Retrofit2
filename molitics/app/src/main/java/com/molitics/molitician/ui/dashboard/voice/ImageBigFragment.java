package com.molitics.molitician.ui.dashboard.voice;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;

import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.SnapHelper;

import com.molitics.molitician.R;
import com.molitics.molitician.ui.dashboard.voice.adapter.ExpandVideoRecyclerAdapter;
import com.molitics.molitician.ui.dashboard.voice.adapter.ImageBigViewPager;
import com.molitics.molitician.ui.dashboard.voice.adapter.ImageViewPager;
import com.molitics.molitician.ui.dashboard.voice.adapter.VoiceAllAdapter;
import com.molitics.molitician.ui.dashboard.voice.voiceDetail.DownloadVideoHandler;
import com.molitics.molitician.util.Constant;
import com.molitics.molitician.util.DownloadFiles;
import com.molitics.molitician.util.MoliticsAppPermission;
import com.molitics.molitician.util.Util;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import im.ene.toro.media.PlaybackInfo;
import im.ene.toro.widget.Container;

import static com.molitics.molitician.util.MoliticsAppPermission.checkWritePermission;

/**
 * Created by rahul on 27/11/17.
 */

public class ImageBigFragment extends DialogFragment implements ImageBigViewPager.ImageListener,
        ImageViewPager.VoiceImageListener, DownloadVideoHandler.DownloadVideoResponse {

    public static final String TAG = ImageBigFragment.class.getName();

    @BindView(R.id.image_view_pager)
    Container image_view_pager;

    // Arguments to start this fragment
    private static final String ARG_EXTRA_PLAYBACK_INFO = "molitics:more_videos:playback_info";
    private static final String ARG_EXTRA_BASE_FB_VIDEO = "molitics:more_videos:base_video";
    private static final String ARG_EXTRA_BASE_ORDER = "molitics:more_videos:base_order";
    private static ImageBigFragment imageBigFragment;

    private int MY_REQUEST_CODE = 100;
    private final int height_margin = 80;
    private final int width_margin = 20;
    private int issue_id;
    private Callback callback;
    private PlaybackInfo baseInfo;
    private int baseOrder;
    private int mAction;
    private String mUrl;
    private VoiceAllAdapter.VoiceInterFace voiceInterFace;

    @Override
    public void onVideoResponse() {
        if (getContext() != null)
            Util.toastLong(getContext(), getString(R.string.video_downloaded));
    }


    public interface Callback {
        void onPlaylistCreated();

        void onPlaylistDestroyed(int basePosition, /*VoiceAllModel baseItem,*/ PlaybackInfo latestInfo);
    }


    public static DialogFragment getInstance(int position, int issue_id, ArrayList<String> imageList, @NonNull PlaybackInfo info) {

        imageBigFragment = new ImageBigFragment();

        Bundle mBundle = new Bundle();

        mBundle.putInt(ARG_EXTRA_BASE_ORDER, position);
        mBundle.putInt(Constant.IntentKey.ISSUE_ID, issue_id);
        mBundle.putParcelable(ARG_EXTRA_PLAYBACK_INFO, info);
        mBundle.putInt(Constant.IntentKey.IMAGE_POSITION, position);
        mBundle.putStringArrayList(Constant.IntentKey.IMAGE_LIST, imageList);
        imageBigFragment.setArguments(mBundle);

        return imageBigFragment;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (getParentFragment() != null && getParentFragment() instanceof Callback) {
            this.callback = (Callback) getParentFragment();
        }
        if (getParentFragment() != null && getParentFragment() instanceof VoiceAllAdapter.VoiceInterFace) {
            voiceInterFace = ((VoiceAllAdapter.VoiceInterFace) getParentFragment());
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getDialog().getWindow().getAttributes().windowAnimations = R.style.CommentDialog;
    }


    @Override
    public void onStart() {
        super.onStart();
        Dialog d = getDialog();
        if (d != null) {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            int height = displayMetrics.heightPixels - height_margin;
            int width = displayMetrics.widthPixels - width_margin;
            d.getWindow().setLayout(width, height);
        }
    }

    @Override
    public void show(FragmentManager manager, String tag) {
        try {
            FragmentTransaction ft = manager.beginTransaction();
            ft.add(this, tag);
            ft.commit();
        } catch (IllegalStateException e) {
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            baseInfo = getArguments().getParcelable(ARG_EXTRA_PLAYBACK_INFO);
            baseOrder = getArguments().getInt(ARG_EXTRA_BASE_ORDER);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(getActivity().getResources().getDrawable(R.drawable.image_bg));
        getDialog().getWindow().setWindowAnimations(R.style.CommentDialog);

        View mView = inflater.inflate(R.layout.voice_big_image_view, container, false);
        ButterKnife.bind(this, mView);

        if (callback != null) {
            callback.onPlaylistCreated();
        }

        Bundle mBundle = getArguments();

        if (mBundle != null) {
            issue_id = mBundle.getInt(Constant.IntentKey.ISSUE_ID, 0);
            bindUI(mBundle.getStringArrayList(Constant.IntentKey.IMAGE_LIST), mBundle.getInt(Constant.IntentKey.IMAGE_POSITION));
        }
        return mView;
    }

    private void bindUI(List<String> image_list, int position) {
        ExpandVideoRecyclerAdapter videoRecyclerAdapter = new ExpandVideoRecyclerAdapter(getActivity(), TAG,
                image_list, this);

        image_view_pager.setAdapter(videoRecyclerAdapter);
        if (baseInfo != null)
            image_view_pager.savePlaybackInfo(Constant.ZERO, baseInfo);

        SnapHelper snapHelper = new PagerSnapHelper();
        image_view_pager.setOnFlingListener(null);
        snapHelper.attachToRecyclerView(image_view_pager);

        image_view_pager.scrollToPosition(position);
    }

    @Override
    public void onImageClick(int position, int issue_id, List<String> imageList) {
    }

    @Override
    public void onVideoPLay() {
    }

    @Override
    public void onVideoPause() {

    }

    @Override
    public void onShareClick(String url) {
        if (url.contains(".mp4")) {
            if (voiceInterFace != null)
                voiceInterFace.onShareClick(issue_id, "", "");
        } else {
            //ToDo: share voice icon
            if (checkStoragePermission())
                DownloadFiles.saveImage(getContext(), DownloadFiles.SHARE_TYPE, url);
        }
    }

    @Override
    public void onDownloadClick(String url) {
        if (url.contains(Constant.MP4)) {
            Util.toastLong(getContext(), getString(R.string.download_started));
            new DownloadVideoHandler(this).downloadVideo(url);
        } else {
            if (checkStoragePermission())
                DownloadFiles.saveImage(getContext(), DownloadFiles.DOWNLOAD_TYPE, url);
        }
    }

    @Override
    public void imageAction(int action, String url) {
        mAction = action;
        mUrl = url;

        if (!checkWritePermission()) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_REQUEST_CODE);
        } else {
            DownloadFiles.saveImage(getContext(), action, url);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == MY_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                DownloadFiles.saveImage(getContext(), mAction, mUrl);
            } else {
                Toast.makeText(getContext(), getString(R.string.no_permission), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callback = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (callback != null) {
            callback.onPlaylistDestroyed(baseOrder, image_view_pager.getPlaybackInfo(Constant.ZERO));
        }
    }

    private boolean checkStoragePermission() {
        if (checkWritePermission()) {
            return true;
        } else {
            MoliticsAppPermission.requestStoragePermission(getActivity());
            return false;
        }
    }
}
