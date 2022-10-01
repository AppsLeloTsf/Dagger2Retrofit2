package com.molitics.molitician.ui.dashboard.election.past_election.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.molitics.molitician.R;
import com.molitics.molitician.interfaces.MOnClickListener;
import com.molitics.molitician.ui.dashboard.election.past_election.model.PartyList;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by rahul on 1/10/2017.
 */

public class OtherCandidatesAdapter extends RecyclerView.Adapter<OtherCandidatesAdapter.CandidateViewHolder> {


    private Context mContext;
    private List<PartyList> partyLists = new ArrayList<>();
    private MOnClickListener clickListener;

    public OtherCandidatesAdapter(Context mContext, MOnClickListener clickListener) {
        this.mContext = mContext;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public CandidateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(mContext).inflate(R.layout.adapter_other_candidate, parent, false);
        return new CandidateViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull CandidateViewHolder holder, int position) {
        final PartyList mPartyList = partyLists.get(position);
        holder.party_name_txt.setText(mPartyList.getName());
        holder.votes_win_txt.setText(String.format(mContext.getString(R.string.won), mPartyList.getSeats()));

        if (position == 0) {
            holder.trophy_view.setVisibility(View.VISIBLE);
        } else {
            holder.trophy_view.setVisibility(View.INVISIBLE);
        }
        holder.ll_main.setOnClickListener(v -> clickListener.onMClick(Integer.valueOf(mPartyList.getId())));

        if (mPartyList.getLogo() != null && !TextUtils.isEmpty(mPartyList.getLogo()) && mPartyList.getLogo().length() > 4) {
            Picasso.with(mContext).load(mPartyList.getLogo()).placeholder(R.drawable.internet_no_cloud).error(R.drawable.error_placeholder).into(holder.image_view);
        } else {
            holder.image_view.setImageResource(R.drawable.sample_image);
        }
    }

    public void addPartyList(List<PartyList> partyLists) {
        this.partyLists = partyLists;
        notifyDataSetChanged();
    }

    public void clear() {
        partyLists.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return partyLists.size();
    }

    public class CandidateViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ll_main)
        LinearLayout ll_main;
        @BindView(R.id.image_view)
        ImageView image_view;
        @BindView(R.id.party_name_txt)
        TextView party_name_txt;
        @BindView(R.id.votes_win_txt)
        TextView votes_win_txt;
        @BindView(R.id.trophy_view)
        ImageView trophy_view;

        public CandidateViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
