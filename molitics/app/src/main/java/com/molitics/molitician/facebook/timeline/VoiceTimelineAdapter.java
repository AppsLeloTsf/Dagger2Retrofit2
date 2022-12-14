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

import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.molitics.molitician.facebook.data.FbItem;
import com.molitics.molitician.ui.dashboard.voice.model.VoiceAllModel;

import java.util.ArrayList;
import java.util.List;

import im.ene.toro.CacheManager;


/**
 * @author | 6/18/17.
 */

@SuppressWarnings({"unused", "WeakerAccess"}) //
public class VoiceTimelineAdapter extends RecyclerView.Adapter<TimelineViewHolder>
        implements CacheManager {

    private static final String TAG = "Molitics:Fb:Adapter";

    static final int TYPE_OTHER = 1;
    static final int TYPE_VIDEO = 2;

    private final List<VoiceAllModel> items = new ArrayList<>();

    private final long initTimeStamp;
    @Nullable
    private Callback callback;

    VoiceTimelineAdapter(long initTimeStamp) {
        super();
        this.initTimeStamp = initTimeStamp;
        setHasStableIds(true);
    }

    public void addItem(List<VoiceAllModel> items) {
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    public void setCallback(@Nullable Callback callback) {
        this.callback = callback;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public TimelineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final TimelineViewHolder viewHolder = TimelineViewHolder.createViewHolder(parent, viewType);
        viewHolder.setClickListener(v -> {
            int pos = viewHolder.getAdapterPosition();
            if (callback != null && pos != RecyclerView.NO_POSITION) {
                callback.onItemClick(viewHolder, v, getItem(pos), pos);
            }
        });
        return viewHolder;
    }

    @Override
    public int getItemViewType(int position) {
        ////  FbItem item = getItem(position);

        /////return item instanceof FbVideo ? TYPE_VIDEO : TYPE_OTHER;
        return TYPE_VIDEO;
    }

    @Override
    public void onBindViewHolder(@NonNull TimelineViewHolder holder, int position) {
        holder.bind(this, getItem(position), null);
    }

    @Override
    public void onViewRecycled(@NonNull TimelineViewHolder holder) {
        holder.onRecycled();
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    /*public FbItem getItem(int position) {
        if (position >= items.size()) {
            for (int i = items.size(); i <= position; i++) {
                items.add(FbVideo.getItem(i, i, initTimeStamp + i * 60_000));
            }
        }

        return items.get(position);
    }*/
    public VoiceAllModel getItem(int position) {
        return items.get(position);
    }


    static abstract class Callback {

        abstract void onItemClick(@NonNull TimelineViewHolder viewHolder, @NonNull View view,
                                  @NonNull VoiceAllModel item, int position);
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
        return key instanceof FbItem ? items.indexOf(key) : null;
    }
}
