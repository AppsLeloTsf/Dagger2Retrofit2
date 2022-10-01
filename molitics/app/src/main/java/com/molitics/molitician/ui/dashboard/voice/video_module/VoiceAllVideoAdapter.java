package com.molitics.molitician.ui.dashboard.voice.video_module;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.molitics.molitician.R;
import com.molitics.molitician.ui.dashboard.voice.adapter.VoiceAllAdapter;
import com.molitics.molitician.ui.dashboard.voice.model.VoiceAllModel;

import java.util.ArrayList;
import java.util.List;

import im.ene.toro.ToroPlayer;

public class VoiceAllVideoAdapter extends RecyclerView.Adapter<VoiceVideoViewHolder> {

    private List<VoiceAllModel> voiceAllModels = new ArrayList<>();
    private Context mContext;
    private VoiceAllAdapter.VoiceInterFace voiceInterFace;
    private OnCompleteCallback onCompleteCallback;


    VoiceAllVideoAdapter(Context mContext, VoiceAllAdapter.VoiceInterFace voiceInterFace) {
        this.mContext = mContext;
        this.voiceInterFace = voiceInterFace;

    }

    public void addVoiceVideoList(List<VoiceAllModel> voiceAllModel) {
        this.voiceAllModels.addAll(voiceAllModel);
        notifyDataSetChanged();
    }

    public void addCommentCount(int position, int count) {
        VoiceAllModel voiceAllModel = voiceAllModels.get(position);

        voiceAllModel.setComments(voiceAllModel.getComments() + count);

        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public VoiceVideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.activity_voice_all_video_adapter, parent, false);
        return new VoiceVideoViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull VoiceVideoViewHolder holder, int position) {
        holder.bindView(mContext, voiceAllModels.get(holder.getAdapterPosition()), position, voiceInterFace);
    }

    @Override
    public int getItemCount() {
        return voiceAllModels.size();
    }


    // on complete stuff
    int findNextPlayerPosition(int base) {
        return base + 1;
    }


    public void setOnCompleteCallback(OnCompleteCallback onCompleteCallback) {
        this.onCompleteCallback = onCompleteCallback;
    }

    static abstract class OnCompleteCallback {

        abstract void onCompleted(ToroPlayer player);
    }

    public void updateLikeDislike(int position, int like_count, int dislike_count, int my_action, int image_position) {
        VoiceAllModel voiceAllModel = voiceAllModels.get(position);
        voiceAllModel.setLikes(like_count);
        voiceAllModel.setDislikes(dislike_count);
        voiceAllModel.setMyAction(my_action);

        voiceAllModels.set(position, voiceAllModel);
    }

    public void onFollowResponse(int position, int count, int image_position) {
        VoiceAllModel voiceAllModel = voiceAllModels.get(position);
        voiceAllModel.setIsFollowing(1);
        voiceAllModel.setFollowing(count);
        voiceAllModels.set(position, voiceAllModel);
    }

    public void onUnFollowResponse(int position, int count, int image_position) {
        VoiceAllModel voiceAllModel = voiceAllModels.get(position);
        voiceAllModel.setFollowing(count);
        voiceAllModel.setIsFollowing(0);
        voiceAllModels.set(position, voiceAllModel);
    }

    public void deleteVoice(int position) {
        voiceAllModels.remove(position);
        notifyDataSetChanged();
    }

}
