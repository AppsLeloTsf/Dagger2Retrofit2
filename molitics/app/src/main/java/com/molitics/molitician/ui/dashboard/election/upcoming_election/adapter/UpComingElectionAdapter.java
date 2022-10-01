package com.molitics.molitician.ui.dashboard.election.upcoming_election.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.molitics.molitician.R;
import com.molitics.molitician.interfaces.MOnClickListener;
import com.molitics.molitician.ui.dashboard.election.past_election.model.AllPastElectionList;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by rahul on 1/9/2017.
 */

public class UpComingElectionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private MOnClickListener listener;
    private List<AllPastElectionList> allPastElectionLists = new ArrayList<>();

    public UpComingElectionAdapter(Context mContext, MOnClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_past_election, parent, false);
        return new UpComingElectionHolder(mView);
    }

    public void addElectionList(List<AllPastElectionList> allPastElectionLists) {
        this.allPastElectionLists.addAll(allPastElectionLists);
        notifyDataSetChanged();
    }

    public int getElectionListSize() {
        return allPastElectionLists.size();
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        final AllPastElectionList pastElectionList = allPastElectionLists.get(position);
        UpComingElectionHolder mHolder = (UpComingElectionHolder) holder;
        mHolder.state_name.setText(pastElectionList.getState());
      // mHolder.know_more.setOnClickListener(v -> listener.onMClick(pastElectionList.getId()));
    }

    @Override
    public int getItemCount() {
        return allPastElectionLists.size();
    }

    public class UpComingElectionHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.state_name)
        TextView state_name;

      /*  @BindView(R.id.know_more)
        TextView know_more;*/

        public UpComingElectionHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

