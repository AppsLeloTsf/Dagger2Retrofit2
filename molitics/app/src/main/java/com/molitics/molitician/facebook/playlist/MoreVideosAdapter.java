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

package com.molitics.molitician.facebook.playlist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.molitics.molitician.facebook.data.FbVideo;
import com.molitics.molitician.ui.dashboard.voice.model.VoiceAllModel;

import java.util.ArrayList;
import java.util.List;

import im.ene.toro.CacheManager;
import im.ene.toro.ToroPlayer;

/**
 * @author eneim | 6/19/17.
 */

@SuppressWarnings("Range")
public class MoreVideosAdapter extends RecyclerView.Adapter<MoreVideoItemViewHolder> implements CacheManager {

   /// @NonNull
  ///  private final VoiceAllModel baseItem;
  ///  private final long initTimeStamp;
    private final List<VoiceAllModel> items = new ArrayList<>();
    ////////// passed FeedVideo array

    OnCompleteCallback onCompleteCallback;

    public void setOnCompleteCallback(OnCompleteCallback onCompleteCallback) {
        this.onCompleteCallback = onCompleteCallback;
    }

    public MoreVideosAdapter(@NonNull VoiceAllModel baseItem) {
        super();
        setHasStableIds(true);
      ///  this.initTimeStamp = initTimeStamp;
        this.items.add(baseItem);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /*  public FbVideo getItem(@IntRange(from = 0) int position) {
        if (position == 0) return baseItem;
        int posInList = position - 1; // shift by 1.
        if (posInList >= items.size()) {
          for (int i = items.size(); i <= posInList; i++) {
            items.add(FbVideo.getItem(i + 1, i + 1, initTimeStamp + (i + 1) * 60_000));
          }
        }

        return items.get(posInList);
      }
      */

    public VoiceAllModel getItem(int position) {
        return items.get(position);

    }

    @Override
    public MoreVideoItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(MoreVideoItemViewHolder.LAYOUT_RES, parent, false);
        MoreVideoItemViewHolder viewHolder = new MoreVideoItemViewHolder(view);
        viewHolder.setEventListener(new ToroPlayer.EventListener() {
            @Override
            public void onFirstFrameRendered() {

            }

            @Override
            public void onBuffering() {

            }

            @Override
            public void onPlaying() {

            }

            @Override
            public void onPaused() {

            }

            @Override
            public void onCompleted() {
               /* if (onCompleteCallback != null)
                    onCompleteCallback.onCompleted(viewHolder);*/
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MoreVideoItemViewHolder holder, int position) {
        holder.bind(this, getItem(position), null);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    // Implement the CacheManager;

    @NonNull
    @Override
    public Object getKeyForOrder(int order) {
        return getItem(order);
    }

    @Nullable
    @Override
    public Integer getOrderForKey(@NonNull Object key) {
        return key instanceof FbVideo ? items.indexOf(key) : null;
    }

    // on complete stuff
    int findNextPlayerPosition(int base) {
        return base + 1;
    }

    static abstract class OnCompleteCallback {

        abstract void onCompleted(ToroPlayer player);
    }
}
