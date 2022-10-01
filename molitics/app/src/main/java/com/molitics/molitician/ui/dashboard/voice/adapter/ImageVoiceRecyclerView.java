package com.molitics.molitician.ui.dashboard.voice.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.molitics.molitician.R;
import com.molitics.molitician.ui.dashboard.voice.model.VoiceImageModel;
import com.molitics.molitician.ui.dashboard.voice.viewHolder.ImageVoiceViewHolder;

import java.util.List;

import im.ene.toro.ToroPlayer;

/**
 * Created by om on 21/05/18.
 */

public class ImageVoiceRecyclerView extends RecyclerView.Adapter<ImageVoiceViewHolder> {

    private static final int LAYOUT_RES = R.layout.voice_image_adapter;

    private List<VoiceImageModel> stringList;
    private Context mContext;
    private ImageViewPager.VoiceImageListener voiceImageListener;
    String from;
    private ToroPlayer.EventListener eventListener;


    public ImageVoiceRecyclerView(Context mContext, String from, List<VoiceImageModel> stringList,
                                  ImageViewPager.VoiceImageListener voiceImageListener, ToroPlayer.EventListener eventListener) {
        this.stringList = stringList;
        this.mContext = mContext;
        this.voiceImageListener = voiceImageListener;
        this.from = from;
        this.eventListener = eventListener;
    }


    @NonNull
    @Override
    public ImageVoiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(ImageVoiceRecyclerView.LAYOUT_RES, parent, false);

        return new ImageVoiceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageVoiceViewHolder holder, int position) {
        if (eventListener != null)
            holder.setEventListener(eventListener);
        holder.bind(mContext, position, from, stringList.get(position).getImage(), voiceImageListener);
    }

    @Override
    public int getItemCount() {
        return stringList.size();
    }

}
