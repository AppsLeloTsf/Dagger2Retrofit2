package com.molitics.molitician.ui.dashboard.voice.adapter;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.molitics.molitician.R;
import com.molitics.molitician.ui.dashboard.leader.AllLeaderModel;
import com.molitics.molitician.ui.dashboard.voice.model.VoiceAllModel;
import com.molitics.molitician.ui.dashboard.voice.viewHolder.VoiceAllViewHolder;
import com.molitics.molitician.util.ExtraUtils;

import java.util.ArrayList;
import java.util.List;

import im.ene.toro.PlayerSelector;
import im.ene.toro.widget.Container;

import static com.molitics.molitician.util.Util.attachLeaderToLinearView;

/**
 * Created by rahul on 17/01/18.
 */

public class TrendingVoiceAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
        implements ExtraUtils.NotifyCustomDataSetChanged {
    public static String TAG = "TrendingVoiceAdapter";

    private List<VoiceAllModel> voiceAllModels = new ArrayList<>();
    private Context mContext;
    private VoiceAllAdapter.VoiceInterFace voiceInterFace;

    private Container trending_recycler_view;


    TrendingVoiceAdapter(Context mContext, VoiceAllAdapter.VoiceInterFace voiceInterFace, List<VoiceAllModel> voiceAllModels) {
        this.mContext = mContext;
        this.voiceInterFace = voiceInterFace;
        this.voiceAllModels.addAll(voiceAllModels);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        trending_recycler_view = (Container) recyclerView;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.trending_voice_adapter, parent, false);
        return new VoiceAllViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        VoiceAllViewHolder mViewHolder = (VoiceAllViewHolder) holder;
        VoiceAllModel voiceModel = voiceAllModels.get(position);
        if (voiceModel != null) {
            int image_position = 0;
            mViewHolder.bindView(mContext, TAG, mViewHolder, voiceModel, holder.getAdapterPosition(),
                    position, voiceInterFace, this, image_position);

            mViewHolder.attachedLeaderView.setTag(voiceModel.getCandidateLeader());
            visibleAll(mViewHolder);
        }
    }

    @Override
    public int getItemCount() {
        return voiceAllModels.size();
    }


    @Override
    public void notifyCustom(int image_position) {

    }

    @Override
    public void onViewDetachedFromWindow(@NonNull RecyclerView.ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        if (holder instanceof VoiceAllAdapter.TrendingIssueHolder) {
            ((VoiceAllAdapter.TrendingIssueHolder) holder).onDetached();
        }
    }

    @Override
    public void onViewAttachedToWindow(@NonNull RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        if (holder instanceof VoiceAllAdapter.TrendingIssueHolder) {
            ((VoiceAllAdapter.TrendingIssueHolder) holder).onAttached();
        }
    }


    private void visibleAll(VoiceAllViewHolder mViewHolder) {
        mViewHolder.user_name.setVisibility(View.VISIBLE);
        mViewHolder.description_txt.setVisibility(View.VISIBLE);
        mViewHolder.tag_leader_txt.setVisibility(View.GONE);
        mViewHolder.three_dot.setVisibility(View.GONE);
        ArrayList<AllLeaderModel> taggedLeader = (ArrayList<AllLeaderModel>) mViewHolder.attachedLeaderView.getTag();
        attachLeaderToLinearView(mContext, mViewHolder.attachedLeaderView, taggedLeader);

        mViewHolder.description_txt.getViewTreeObserver().addOnPreDrawListener(() -> {

            mViewHolder.continue_reading_txt.setVisibility(View.GONE);
            return true;
        });
        mViewHolder.tag_leader_txt.setLines(1);
    }

    public void attachVideoView() {
        trending_recycler_view.setPlayerSelector(PlayerSelector.DEFAULT);

    }

    public void detachVideoView() {
        trending_recycler_view.setPlayerSelector(PlayerSelector.NONE);
    }
}
