package com.molitics.molitician.ui.dashboard.voice.adapter;

import android.content.Context;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.molitics.molitician.R;
import com.molitics.molitician.ui.dashboard.leader.AllLeaderModel;
import com.molitics.molitician.ui.dashboard.voice.createVoice.CreateVoiceInterface;
import com.molitics.molitician.ui.dashboard.voice.createVoice.CreateVoiceViewHolder;
import com.molitics.molitician.ui.dashboard.voice.createVoice.VoiceVideoViewHolder;
import com.molitics.molitician.ui.dashboard.voice.model.VoiceAllModel;
import com.molitics.molitician.util.Constant;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rahul on 16/11/17.
 */

public class VoiceAddImageAdapter extends RecyclerView.Adapter<VoiceVideoViewHolder> implements CreateVoiceInterface {

    private Context mContext;
    private int CREATE_VOICE = 2;
    private int IMAGE_VIEW = 3;
    private VoiceAllModel voiceAllModel;

    public String STATIC_VALUE = "static";

    public VoiceAddImageAdapter(Context mContext) {
        this.mContext = mContext;
        voiceAllModel = new VoiceAllModel();
    }

    public void tagLeaderList(ArrayList<AllLeaderModel> selected_leader_list) {
        voiceAllModel.setCandidateLeader(selected_leader_list);
        notifyDataSetChanged();
    }

    public List<String> getVoiceImageList() {
        return voiceAllModel.getImages();
    }

    public void addVoiceModel(VoiceAllModel voiceAllModel) {
        this.voiceAllModel = voiceAllModel;
        if (voiceAllModel.getImages() != null)
            voiceAllModel.getImages().add(Constant.ZERO, STATIC_VALUE);
        notifyDataSetChanged();
    }

    public void setVoiceImage(String image) {
        List<String> image_list = voiceAllModel.getImages();
        image_list.add(image_list.size(), image);
        notifyItemChanged(image_list.size());
    }

    public VoiceAllModel getVoiceAllModel() {
        return voiceAllModel;
    }

    private void setMainLayoutPattern(final ViewGroup viewGroup, final int viewType, final View mView) {
        mView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                final ViewGroup.LayoutParams lp = mView.getLayoutParams();
                if (lp instanceof StaggeredGridLayoutManager.LayoutParams) {
                    StaggeredGridLayoutManager.LayoutParams sglp =
                            (StaggeredGridLayoutManager.LayoutParams) lp;

                    if (viewType == IMAGE_VIEW) {
                        sglp.setFullSpan(false);
                    } else {
                        sglp.setFullSpan(true);
                    }
                    mView.setLayoutParams(sglp);
                    final StaggeredGridLayoutManager lm =
                            (StaggeredGridLayoutManager) ((RecyclerView) viewGroup).getLayoutManager();
                    lm.invalidateSpanAssignments();
                }
                mView.getViewTreeObserver().removeOnPreDrawListener(this);
                return true;
            }
        });
    }

    @NonNull
    @Override
    public VoiceVideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView;
        VoiceVideoViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (viewType == CREATE_VOICE) {
            mView = inflater.inflate(R.layout.include_create_layout, parent, false);
            viewHolder = new CreateVoiceViewHolder(mView);
        } else {
            mView = inflater.inflate(R.layout.adapter_voice_add_image, parent, false);
            viewHolder = new VoiceVideoViewHolder(mView);
        }

        setMainLayoutPattern(parent, viewType, mView);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull VoiceVideoViewHolder holder, int position) {
        holder.bind(mContext, position, voiceAllModel, this);
    }


    @Override
    public int getItemViewType(int position) {
        if (position == Constant.ZERO) {
            return CREATE_VOICE;
        } else {
            return IMAGE_VIEW;
        }
    }

    @Override
    public int getItemCount() {
        return voiceAllModel.getImages().size();
    }

    @Override
    public void notifyAdapter(int position) {
        notifyDataSetChanged();
    }

    @Override
    public void addLinkImage(String image) {
        setVoiceImage(image);
    }
}
