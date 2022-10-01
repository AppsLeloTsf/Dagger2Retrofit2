package com.molitics.molitician.ui.dashboard.userProfile.issue_following;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.molitics.molitician.R;
import com.molitics.molitician.ui.dashboard.voice.adapter.VoiceAllAdapter;
import com.molitics.molitician.ui.dashboard.voice.model.VoiceAllModel;
import com.molitics.molitician.ui.dashboard.voice.viewHolder.VoiceAllViewHolder;
import com.molitics.molitician.util.ExtraUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rahul on 17/01/18.
 */

public class IssueFollowingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements
        ExtraUtils.NotifyCustomDataSetChanged {
    String TAG = "IssueFollowingAdapter";
    List<VoiceAllModel> voiceAllModels = new ArrayList<>();
    VoiceAllAdapter.VoiceInterFace voiceInterFace;
    Context mContext;
    Boolean is_create_visible;
    int image_position = 0;

    IssueFollowingAdapter(Context mContext, VoiceAllAdapter.VoiceInterFace voiceInterFace, Boolean is_create_visible) {
        this.mContext = mContext;
        this.voiceInterFace = voiceInterFace;
        this.is_create_visible = is_create_visible;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_voice_all, parent, false);

        return new VoiceAllViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        VoiceAllViewHolder mViewHolder = (VoiceAllViewHolder) holder;
        VoiceAllModel voiceModel = voiceAllModels.get(position);
        if (voiceModel != null) {
            mViewHolder.bindView(mContext, TAG, mViewHolder, voiceModel, holder.getAdapterPosition(),
                    position, voiceInterFace, this, image_position);
        }
    }

    @Override
    public int getItemCount() {
        return voiceAllModels.size();
    }


    public void addVoiceList(List<VoiceAllModel> voiceAllModels) {
        this.voiceAllModels.addAll(voiceAllModels);
        notifyDataSetChanged();
    }

    public void updateVoiceModel(VoiceAllModel singleVoiceModel, int position) {
        voiceAllModels.set(position, singleVoiceModel);
        notifyDataSetChanged();
    }


    public List<VoiceAllModel> getVoiceList() {
        return voiceAllModels;
    }

    public int getVoiceListSize() {
        return voiceAllModels.size();
    }

    public void clearVoiceList() {
        voiceAllModels.clear();
        notifyDataSetChanged();
    }

    public void deleteVoice(int position) {
        voiceAllModels.remove(position);
        notifyDataSetChanged();
    }

    public void updateLikeDislike(int position, int like_count, int dislike_count, int my_action) {
        VoiceAllModel voiceAllModel = voiceAllModels.get(position);
        voiceAllModel.setLikes(like_count);
        voiceAllModel.setDislikes(dislike_count);
        voiceAllModel.setMyAction(my_action);
        voiceAllModels.set(position, voiceAllModel);
    }

    public void onUnFollowResponse(int position, int count) {
        VoiceAllModel voiceAllModel = voiceAllModels.get(position);
        voiceAllModel.setFollowing(count);
        voiceAllModel.setIsFollowing(0);
        voiceAllModels.set(position, voiceAllModel);
    }

    public void onFollowResponse(int position, int count) {
        VoiceAllModel voiceAllModel = voiceAllModels.get(position);
        voiceAllModel.setFollowing(count);
        voiceAllModel.setIsFollowing(1);
        voiceAllModels.set(position, voiceAllModel);
    }

    @Override
    public void notifyCustom(int image_position) {
    }
}
