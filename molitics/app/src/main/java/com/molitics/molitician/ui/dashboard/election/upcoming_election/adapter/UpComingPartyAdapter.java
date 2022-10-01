package com.molitics.molitician.ui.dashboard.election.upcoming_election.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.molitics.molitician.R;
import com.molitics.molitician.interfaces.MOnClickListener;
import com.molitics.molitician.ui.dashboard.election.upcoming_election.model.UpcomingPartyModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by rahul on 1/12/2017.
 */

public class UpComingPartyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private List<UpcomingPartyModel> upcomingPartyModelList = new ArrayList<>();
    private MOnClickListener listener;

    public UpComingPartyAdapter(Context mContext, MOnClickListener listener) {
        this.mContext = mContext;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(mContext).inflate(R.layout.adapter_upcoming_party, parent, false);
        return new PartyHolder(mView);
    }

    public void addPartyList(List<UpcomingPartyModel> upcomingPartyModelList) {
        this.upcomingPartyModelList = upcomingPartyModelList;
    }

    public void deletePartyList() {
        upcomingPartyModelList.clear();
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        final UpcomingPartyModel partyModel = upcomingPartyModelList.get(position);
        PartyHolder mHolder = (PartyHolder) holder;
        mHolder.party_name.setText(partyModel.getPartyName());
        mHolder.state_candidate.setText(partyModel.getCandidates());
        mHolder.RL_main_layout.setOnClickListener(v -> listener.onMClick(partyModel.getId()));

        if (partyModel.getPartyLogo() != null && !TextUtils.isEmpty(partyModel.getPartyLogo()) && partyModel.getPartyLogo().length() > 4) {
            Picasso.with(mContext).load(partyModel.getPartyLogo()).placeholder(R.drawable.internet_no_cloud).error(R.drawable.error_placeholder).into(mHolder.state_image);
        }
    }

    @Override
    public int getItemCount() {
        return upcomingPartyModelList.size();
    }

    public class PartyHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.RL_main_layout)
        RelativeLayout RL_main_layout;
        @BindView(R.id.state_image)
        ImageView state_image;
        @BindView(R.id.state_name_semi_lay)
        LinearLayout state_name_semi_lay;
        @BindView(R.id.party_name)
        TextView party_name;
        @BindView(R.id.state_candidate)
        TextView state_candidate;

        public PartyHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
