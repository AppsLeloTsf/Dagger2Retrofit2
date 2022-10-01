/*
 * Copyright (c) 2017 Nam Nguyen, nam@ene.im
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.molitics.molitician.facebook.timeline;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.molitics.molitician.BasicFragment;
import com.molitics.molitician.R;
import com.molitics.molitician.error.ApiException;
import com.molitics.molitician.facebook.BuildConfig;
import com.molitics.molitician.facebook.core.ScreenHelper;
import com.molitics.molitician.facebook.data.FbVideo;
import com.molitics.molitician.facebook.player.BigPlayerFragment;
import com.molitics.molitician.facebook.playlist.MoreVideosFragment;
import com.molitics.molitician.model.APIResponse;
import com.molitics.molitician.model.Data;
import com.molitics.molitician.ui.dashboard.voice.VoiceHandler;
import com.molitics.molitician.ui.dashboard.voice.VoicePresenter;
import com.molitics.molitician.ui.dashboard.voice.model.VoiceAllModel;

import java.util.List;

import butterknife.BindView;
import im.ene.toro.PlayerSelector;
import im.ene.toro.ToroPlayer;
import im.ene.toro.media.PlaybackInfo;
import im.ene.toro.widget.Container;

import static com.molitics.molitician.MolticsApplication.getAndroid_id;

/**
 * @author | 6/18/17.
 */

public class VoiceTimelineFragment extends BasicFragment implements MoreVideosFragment.Callback,
        BigPlayerFragment.Callback, VoicePresenter.VoiceActionUi, VoicePresenter.VoiceUI {

    private static final String STATE_KEY_FB_VIDEO = "fb:timeline:state:video";
    private static final String STATE_KEY_ACTIVE_ORDER = "fb:timeline:state:order";
    private static final String STATE_KEY_PLAYBACK_STATE = "fb:timeline:state:playback_info";

    private static final String STATE_KEY_BIG_PLAYER_BUNDLE = "fb:timeline:state:player:bundle";
    VoiceHandler voiceHandler;

    @SuppressWarnings("unused")
    public static VoiceTimelineFragment newInstance() {
        Bundle args = new Bundle();
        VoiceTimelineFragment fragment = new VoiceTimelineFragment();
        fragment.setArguments(args);
        return fragment;
    }

    // View setup
    @BindView(R.id.recycler_view)
    Container container;

    LinearLayoutManager layoutManager;
    VoiceTimelineAdapter adapter;
    VoiceTimelineAdapter.Callback adapterCallback;

    // Orientation helper stuff
    private WindowManager windowManager;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // !IMPORTANT: don't remove these lines.
        this.TAG = "Fb:VoiceTimelineFragment";
        windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
    }

    @Override
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        voiceHandler = new VoiceHandler(this, this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle bundle) {
        getAllVoiceData(0, 0);
        return inflater.inflate(R.layout.fragment_facebook_timeline, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle bundle) {
        super.onViewCreated(view, bundle);
        //// toolbarLayout.setTitle(getString(R.string.title_timeline));

        adapter = new VoiceTimelineAdapter(System.currentTimeMillis());
        layoutManager = new LinearLayoutManager(getContext());
        container.setAdapter(adapter);
        container.setLayoutManager(layoutManager);
        container.setCacheManager(adapter);
        adapterCallback = new VoiceTimelineAdapter.Callback() {
            @Override
            void onItemClick(@NonNull TimelineViewHolder viewHolder, @NonNull View view,
                             @NonNull VoiceAllModel item, int position) {
//////////////////////// check feed have video url or not
                if (viewHolder instanceof ToroPlayer && item.getImage() != null) {
                    PlaybackInfo info = ((ToroPlayer) viewHolder).getCurrentPlaybackInfo();
                    MoreVideosFragment moreVideos =
                            MoreVideosFragment.newInstance(position, item, info);
                    moreVideos.show(getChildFragmentManager(), MoreVideosFragment.TAG);
                }
            }
        };
        adapter.setCallback(adapterCallback);

        // FIXME Only use the following workaround when using this Fragment in ViewPager.
        if (viewPagerMode) {
            container.setPlayerSelector(null);
            // Using TabLayout has a downside: once we click to a tab to change page, there will be no animation,
            // which will cause our setup doesn't work well. We need a delay to make things work.
            handler.postDelayed(() -> {
                if (container != null) container.setPlayerSelector(selector);
            }, 200);
        } else {
            container.setPlayerSelector(selector);
        }
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle bundle) {
        super.onViewStateRestored(bundle);
        if (bundle == null) {
            // User come here from empty, we keep current behaviour.
            return;
        }
        // Bundle != null, which is a hint that we come here from a config change.
        //
        // There is a chance that this Fragment is recreated after a config change. In that case,
        // if in previous 'life', there was a BigPlayerFragment created, it will store a bundle
        // containing its latest playback state that was provided by this Fragment.
        // Here we restore it to continue the last playback.
        Bundle playerBundle = bundle.getBundle(STATE_KEY_BIG_PLAYER_BUNDLE);
        if (playerBundle != null) {
            int order = playerBundle.getInt(BigPlayerFragment.BUNDLE_KEY_ORDER);
            PlaybackInfo info = playerBundle.getParcelable(BigPlayerFragment.BUNDLE_KEY_INFO);
            if (info == null) info = new PlaybackInfo();
            this.container.savePlaybackInfo(order, info);
        }

        if (ScreenHelper.shouldUseBigPlayer(windowManager.getDefaultDisplay())) {
            // Since we come here from a config change, if previous state (portrait mode),
            // there was a on-playing Big Player, we should have a saved state of its playback.
            // Let's retrieve it and then do stuff.

            // 1. Obtain the Video object and its order.
            FbVideo video = bundle.getParcelable(STATE_KEY_FB_VIDEO); // can be null.
            if (video != null) {
                // Device in landscape mode, and there is on-going Video saved from previous state.
                // Here we use PlayerSelector.NONE to disable the auto playback and bring the big player in.
                container.setPlayerSelector(PlayerSelector.NONE);
                int order = bundle.getInt(STATE_KEY_ACTIVE_ORDER);
                // 2. Get saved playback info. We know the adapter is also a state manager though.
                PlaybackInfo info = bundle.getParcelable(STATE_KEY_PLAYBACK_STATE);
                // 3. Prepare video Uri, open a full screen playback dialog.
                BigPlayerFragment playerFragment = BigPlayerFragment.newInstance(order, video, info);
                playerFragment.show(getChildFragmentManager(), BigPlayerFragment.FRAGMENT_TAG);
            }
        }
    }

    // Memo: This method is called before children Fragments' onSaveInstanceState.
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        // If there is DialogFragment showing, we save stuff from it here.
        Fragment playerFragment =
                getChildFragmentManager().findFragmentByTag(BigPlayerFragment.FRAGMENT_TAG);
        if (playerFragment instanceof BigPlayerFragment) {
            Bundle playerBundle = ((BigPlayerFragment) playerFragment).getCurrentState();
            outState.putBundle(STATE_KEY_BIG_PLAYER_BUNDLE, playerBundle);
        }

        // Save stuff here.
        List<ToroPlayer> activePlayers = container.filterBy(Container.Filter.PLAYING);
        if (activePlayers.isEmpty()) return;
        ToroPlayer firstPlayer = activePlayers.get(0);  // get the first one only.
        // We will store the Media object, playback state.
        VoiceAllModel item = adapter.getItem(firstPlayer.getPlayerOrder());
        if (item == null) {
            if (BuildConfig.DEBUG) {  // debug only.
                throw new IllegalStateException("Video is null for active Player: " + firstPlayer);
            }
        }

        // Save this and restore later to open on a BigPlayer if need.
        //////// check feed contaion video
        if (item instanceof VoiceAllModel) {
            outState.putInt(STATE_KEY_ACTIVE_ORDER, firstPlayer.getPlayerOrder());
            outState.putParcelable(STATE_KEY_FB_VIDEO, item);
            outState.putParcelable(STATE_KEY_PLAYBACK_STATE, firstPlayer.getCurrentPlaybackInfo());
        } else {
            // Real practice should not face the following issue, only for debugging.
            if (BuildConfig.DEBUG) {
                throw new IllegalStateException("Found wrong type of FbItem for Player: " + item);
            }
        }
    }

    @Override
    public void onDestroyView() {
        handler.removeCallbacksAndMessages(null);
        adapter.setCallback(null);
        adapterCallback = null;
        adapter = null;
        layoutManager = null;
        super.onDestroyView();
    }

    // Implement MoreVideosFragment callback

    PlayerSelector selector = PlayerSelector.DEFAULT;  // backup current selector.

    @Override
    public void onPlaylistCreated() {
        container.setPlayerSelector(PlayerSelector.NONE);
    }

    @Override
    public void onPlaylistDestroyed(int basePosition, VoiceAllModel baseItem, PlaybackInfo latestInfo) {
        container.savePlaybackInfo(basePosition, latestInfo);
        container.setPlayerSelector(selector);
    }

    // Implement BigPlayerFragment callback

    @Override
    public void onBigPlayerCreated() {
        container.setPlayerSelector(PlayerSelector.NONE);
    }

    @Override
    public void onBigPlayerDestroyed(int videoOrder, FbVideo baseItem, PlaybackInfo latestInfo) {
        container.savePlaybackInfo(videoOrder, latestInfo);
        container.setPlayerSelector(selector);
    }

    // Deal with setUserVisibleHint/ViewPager
    final Handler handler = new Handler();  // post a delay due to the visibility change

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        Log.d(TAG, "setUserVisibleHint() called with: isVisibleToUser = [" + isVisibleToUser + "]");
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            selector = PlayerSelector.DEFAULT;
        } else {
            selector = PlayerSelector.NONE;
        }

        // Using TabLayout has a downside: once we click to a tab to change page, there will be no animation,
        // which will cause our setup doesn't work well. We need a delay to make things work.
        handler.postDelayed(() -> {
            if (container != null) container.setPlayerSelector(selector);
        }, 200);
    }

    private void getAllVoiceData(int count, int state_id) {
        voiceHandler.getAllVoice(0, 0, 0, getAndroid_id);
    }


    @Override
    public void onVoiceResponse(APIResponse apiResponse) {
        Data mData = apiResponse.getData();

        adapter.addItem(mData.getVoiceAllModels());

    }

    @Override
    public void onVoiceApiError(ApiException apiexception) {

    }

    @Override
    public void onUserImageResponse(APIResponse apiResponse) {

    }

    @Override
    public void onUserImageError(ApiException apiException) {

    }

    @Override
    public void onLikeDislikeResponse(APIResponse apiResponse) {

    }

    @Override
    public void onLikeDislikeError(ApiException apiException) {

    }

    @Override
    public void onFollowResponse(APIResponse apiResponse) {

    }

    @Override
    public void onFollowError(ApiException apiException) {

    }

    @Override
    public void onUnFollowResponse(APIResponse apiResponse) {

    }

    @Override
    public void onUnFollowError(ApiException apiException) {

    }

    @Override
    public void onDeleteIssueResponse(APIResponse apiResponse) {

    }

    @Override
    public void onDeleteIssueError(ApiException apiException) {

    }
}
