package com.molitics.molitician.ui.dashboard.voice.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.molitics.molitician.R;
import com.molitics.molitician.ui.dashboard.leader.AllLeaderModel;
import com.molitics.molitician.ui.dashboard.voice.viewHolder.LeaderListViewHolder;
import com.molitics.molitician.util.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rahul on 25/05/18.
 */

public class LeaderListAdapter extends RecyclerView.Adapter<LeaderListViewHolder> {

    private static final int LAYOUT_RES = R.layout.adapter_leader_list_view;

    private List<AllLeaderModel> leaderModelList = new ArrayList<>();

    private List<AllLeaderModel> selected_leader_list = new ArrayList<>();
    private Context mContext;

    public void addLeaderList(List<AllLeaderModel> stringList) {
        this.leaderModelList = stringList;
        notifyDataSetChanged();
    }

    public void addSelectedLeaders(List<AllLeaderModel> leader_id) {
        this.selected_leader_list = leader_id;
        notifyDataSetChanged();
    }

    public List<AllLeaderModel> getSelectedLeaders() {
        return selected_leader_list;
    }

    @NonNull
    @Override
    public LeaderListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LeaderListViewHolder leaderListViewHolder = new LeaderListViewHolder(LayoutInflater.from(parent.getContext()).
                inflate(LAYOUT_RES, parent, false));
        mContext = parent.getContext();
        leaderListViewHolder.setClickListener(v -> {
            int pos = leaderListViewHolder.getAdapterPosition();
            if (pos != RecyclerView.NO_POSITION) {
                if (getItem(pos).getSelectedLeader()) {
                    removeSelectedLeader(pos);
                    getItem(pos).setSelectedLeader(false);
                } else {
                    if (selected_leader_list.size() < 5)
                        insertSelectedLeader(pos);
                    else {
                        Util.toastLong(parent.getContext(), mContext.getString(R.string.you_can_only_tag_five));
                    }
                }
            }
        });
        return leaderListViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull LeaderListViewHolder holder, int position) {
        AllLeaderModel allLeaderModel = leaderModelList.get(position);
        for (AllLeaderModel id : selected_leader_list) {
            if (allLeaderModel.getId() == id.getId()) {
                allLeaderModel.setSelectedLeader(true);
                break;
            } else
                allLeaderModel.setSelectedLeader(false);
        }
        holder.bind(mContext, position, allLeaderModel);
    }

    private AllLeaderModel getItem(int position) {
        return leaderModelList.get(position);
    }

    @Override
    public int getItemCount() {
        return leaderModelList.size();
    }


    private void insertSelectedLeader(int position) {
        selected_leader_list.add(leaderModelList.get(position));
        notifyDataSetChanged();
    }

    private void removeSelectedLeader(int position) {
        for (AllLeaderModel model : selected_leader_list) {
            if (model.getId() == leaderModelList.get(position).getId()) {
                selected_leader_list.remove(model);
                break;
            }
        }
        notifyDataSetChanged();
    }
}
