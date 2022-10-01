package com.molitics.molitician.ui.dashboard.leader;

import android.content.Context;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.molitics.molitician.R;
import com.molitics.molitician.interfaces.LeaderAdapterInterface;
import com.molitics.molitician.ui.dashboard.leader.model.LeadersIdModel;
import com.molitics.molitician.util.Constant;

import java.util.ArrayList;


/**
 * Created by rahul on 10/13/2016.
 */

public class LeaderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<AllLeaderModel> allLeaderModels = new ArrayList<>();
    private LeaderViewHolder leaderViewHolder;
    private Context mContext;
    private LeaderAdapterInterface adapterInterface;
    private boolean isLoadMore = true;
    private OnLoadMoreResult onLoadMoreResult;
    private LeadersIdModel leadersIdModel = new LeadersIdModel();

    public LeaderAdapter(Context mContext, LeaderAdapterInterface adapterInterface, OnLoadMoreResult onLoadMoreResult) {
        this.mContext = mContext;
        this.adapterInterface = adapterInterface;
        this.onLoadMoreResult = onLoadMoreResult;
    }


    public int getLeaderListSize() {
        return allLeaderModels.size();
    }

    public void addLeaderList(ArrayList<AllLeaderModel> allLeaderModels) {
        int lastPosition = allLeaderModels.size();
        this.allLeaderModels.addAll(allLeaderModels);
        notifyItemChanged(lastPosition, allLeaderModels.size());
    }

    public AllLeaderModel getLeaderModel(int position) {
        return allLeaderModels.get(position);
    }


    public void clearLeaderList() {
        leadersIdModel.deleteLeader_is();
        allLeaderModels.clear();
        notifyDataSetChanged();
    }

    public interface OnLoadMoreResult {
        void onLoadMore(int totalItem);
    }

    public void setLoadMore(boolean isLoadMore) {
        this.isLoadMore = isLoadMore;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View leader_view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_leaders, parent, false);
        return leaderViewHolder = new LeaderViewHolder(leader_view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {

        final LeaderViewHolder mHolder = (LeaderViewHolder) holder;

        final AllLeaderModel leaderModel = allLeaderModels.get(position);
        mHolder.onBind(mContext, holder.getAdapterPosition(), leaderModel, adapterInterface);

        leadersIdModel.setLeader_id(leaderModel.getId());
        if (isLoadMore && position >= 9 && position == (allLeaderModels.size() - 1)) {
            onLoadMoreResult.onLoadMore(allLeaderModels.size());
        }
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }


    @Override
    public int getItemCount() {
        return allLeaderModels.size();
    }


    public LeadersIdModel getLeadersId() {
        return leadersIdModel;
    }


    public void followDone(int mPosition, String follow_count) {
        if (allLeaderModels.size() != 0) {
            AllLeaderModel allLeaderModel = allLeaderModels.get(mPosition);
            allLeaderModel.setIsFollowing(1);
            allLeaderModel.setFollowers(follow_count);
            allLeaderModels.set(mPosition, allLeaderModel);
        }
    }

    public void unFollowDone(int mPosition, String follow_count) {
        if (allLeaderModels.size() != 0) {
            AllLeaderModel allLeaderModel = allLeaderModels.get(mPosition);
            allLeaderModel.setIsFollowing(0);
            allLeaderModel.setFollowers(follow_count);
            allLeaderModels.set(mPosition, allLeaderModel);
        }
    }

    public void onUnSuccessFollow(int mPosition) {
        if (allLeaderModels.size() != 0) {
            AllLeaderModel allLeaderModel = allLeaderModels.get(mPosition);
            allLeaderModel.setIsFollowing(0);
            allLeaderModels.set(mPosition, allLeaderModel);
            notifyItemChanged(mPosition);
        }
    }

    public void onUnSuccessUnFollow(int mPosition) {
        if (allLeaderModels.size() != 0) {
            AllLeaderModel allLeaderModel = allLeaderModels.get(mPosition);
            allLeaderModel.setIsFollowing(1);
            allLeaderModels.set(mPosition, allLeaderModel);
            notifyItemChanged(mPosition);
        }
    }

    public void likeDone(int mPosition, int like, int dislike) {
        AllLeaderModel allLeaderModel = allLeaderModels.get(mPosition);
        allLeaderModel.setUser_vote_action(Constant.Action.LIKE);
        allLeaderModel.setLike_count(like);
        allLeaderModel.setDislike_count(dislike);
        allLeaderModels.set(mPosition, allLeaderModel);
        notifyItemChanged(mPosition);
    }

    public void disLikeDone(int mPosition, int like, int dislike) {
        AllLeaderModel allLeaderModel = allLeaderModels.get(mPosition);
        allLeaderModel.setUser_vote_action(Constant.Action.DISLIKE);
        allLeaderModel.setLike_count(like);
        allLeaderModel.setDislike_count(dislike);
        allLeaderModels.set(mPosition, allLeaderModel);
        notifyItemChanged(mPosition);
    }

    public void deleteDone(int mPosition, int like, int dislike) {
        AllLeaderModel allLeaderModel = allLeaderModels.get(mPosition);

        allLeaderModel.setUser_vote_action(Constant.ZERO);
        allLeaderModel.setLike_count(like);
        allLeaderModel.setDislike_count(dislike);
        allLeaderModels.set(mPosition, allLeaderModel);
        notifyItemChanged(mPosition);
        /// notifyDataSetChanged();
    }
}
