package com.molitics.molitician.ui.dashboard.voice.adapter;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.molitics.molitician.R;
import com.molitics.molitician.ui.dashboard.voice.viewHolder.ImageVoiceViewHolder;

import java.util.List;

/**
 * Created by rahul on 22/05/18.
 */

public class ExpandVideoRecyclerAdapter extends RecyclerView.Adapter<ImageVoiceViewHolder> {

    ///// large view
    private static final int LAYOUT_RES = R.layout.issue_image;

   private ImageViewPager.VoiceImageListener voiceImageListener;

    private List<String> stringList;
    Context mContext;
    String from;

    public ExpandVideoRecyclerAdapter(Context mContext, String from, List<String> stringList, ImageViewPager.VoiceImageListener voiceImageListener) {
        this.stringList = stringList;
        this.mContext = mContext;
        this.voiceImageListener = voiceImageListener;
        this.from = from;
    }

    @NonNull
    @Override
    public ImageVoiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(LAYOUT_RES, parent, false);
        return new ImageVoiceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageVoiceViewHolder holder, int position) {
        holder.bind(mContext, position, from, stringList.get(position), voiceImageListener);
    }

    @Override
    public int getItemCount() {
        return stringList.size();
    }
}
