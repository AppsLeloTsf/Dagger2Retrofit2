package com.molitics.molitician.ui.dashboard.election.past_election.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.molitics.molitician.R;
import com.molitics.molitician.interfaces.MOnClickListener;
import com.molitics.molitician.ui.dashboard.election.past_election.model.OtherCandidate;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by rahul on 1/12/2017.
 */

public class AboutOtherCandidateAdapter extends RecyclerView.Adapter<AboutOtherCandidateAdapter.CandidateViewHolder> {


    private Context mContext;
    private List<OtherCandidate> otherCandidateList = new ArrayList<>();
    private MOnClickListener clickListener;

    public AboutOtherCandidateAdapter(Context mContext, MOnClickListener clickListener) {
        this.mContext = mContext;
        this.clickListener = clickListener;
    }

    @Override
    public CandidateViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(mContext).inflate(R.layout.adapter_about_other_candidates, parent, false);

        return new CandidateViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(AboutOtherCandidateAdapter.CandidateViewHolder holder, int position) {
        final OtherCandidate otherCandidate = otherCandidateList.get(position);
        holder.name_txt.setText(otherCandidate.getName());
        holder.party_txt.setText(otherCandidate.getParty());
        holder.ll_main.setOnClickListener(v -> clickListener.onMClick(otherCandidate.getId()));
        holder.votes_count.setText("Votes: " + otherCandidate.getVotes());
        if (otherCandidate.getProfileImage() != null && otherCandidate.getProfileImage().length() > 4) {
            Picasso.with(mContext).load(otherCandidate.getProfileImage()).placeholder(R.drawable.internet_no_cloud).error(R.drawable.error_placeholder).into(holder.image_view);
        } else {
            holder.image_view.setImageResource(R.drawable.sample_image);
        }
    }

    public void addOtherCandidateList(List<OtherCandidate> otherCandidateList) {
        this.otherCandidateList = otherCandidateList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return otherCandidateList.size();
    }

    class CandidateViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ll_main)
        LinearLayout ll_main;
        @BindView(R.id.image_view)
        ImageView image_view;
        @BindView(R.id.name_txt)
        TextView name_txt;
        @BindView(R.id.party_txt)
        TextView party_txt;
        @BindView(R.id.votes_count)
        TextView votes_count;

        CandidateViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
