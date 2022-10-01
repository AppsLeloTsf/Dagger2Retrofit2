package com.molitics.molitician.ui.dashboard.election.past_election.adapter;

import android.content.Context;


import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.molitics.molitician.R;
import com.molitics.molitician.ui.dashboard.constantData.RecyclerTouchWithType;
import com.molitics.molitician.ui.dashboard.election.past_election.model.AllPastElectionList;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by rahul on 1/9/2017.
 */


public class PastElectionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private RecyclerTouchWithType listener;
    private List<AllPastElectionList> allPastElectionLists = new ArrayList<>();
    private OnLoadMoreResult onLoadMoreResult;

    public PastElectionAdapter(Context mContext, RecyclerTouchWithType listener, OnLoadMoreResult onLoadMoreResult) {
        this.mContext = mContext;
        this.listener = listener;
        this.onLoadMoreResult = onLoadMoreResult;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_past_election, parent, false);
        return new PastElectionHolder(mView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        PastElectionHolder mHolder = (PastElectionHolder) holder;
        final AllPastElectionList pastElectionList = allPastElectionLists.get(position);
        mHolder.state_name.setText(pastElectionList.getState());
        //mHolder.election_date.setText(pastElectionList.getDisplayDate());

        Spannable span = new SpannableString(mContext.getString(R.string.total_seats) + pastElectionList.getTotalSeats());
        span.setSpan(new RelativeSizeSpan(1.0f), mContext.getString(R.string.total_seats).length(), span.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        span.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.follow_leader)), mContext.getString(R.string.total_seats).length(), span.length(), 0);// set color

        // mHolder.total_seats.setText(span);
        //mHolder.party_name.setText(pastElectionList.getRuling_party());

        if (position >= 9 && position == (allPastElectionLists.size() - 1)) {
            onLoadMoreResult.onLoadMore(allPastElectionLists.size());
        }

        if (!TextUtils.isEmpty(pastElectionList.getIcon())) {
            Picasso.with(mContext).load(pastElectionList.getIcon()).placeholder(R.drawable.image_placeholder).into(mHolder.party_logo);
        }

        mHolder.rl_main.setOnClickListener(v -> listener.onVerticalRecycler(pastElectionList.getId(), pastElectionList.getState_id()));
    }

    public void addStateList(List<AllPastElectionList> allPastElectionLists) {
        this.allPastElectionLists.addAll(allPastElectionLists);
        notifyDataSetChanged();
    }

    public void clearStateList() {
        allPastElectionLists.clear();
        notifyDataSetChanged();
    }

    public int getElectionListSize() {
        return allPastElectionLists.size();
    }

    @Override
    public int getItemCount() {
        return allPastElectionLists.size();
    }

    public class PastElectionHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.state_name)
        TextView state_name;
        /*    @BindView(R.id.election_date)
            TextView election_date;
            @BindView(R.id.total_seats)
            TextView total_seats;*/
        @BindView(R.id.party_logo)
        ImageView party_logo;
        @BindView(R.id.rl_main)
        LinearLayout rl_main;
   /*     @BindView(R.id.party_name)
        TextView party_name;*/

        public PastElectionHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnLoadMoreResult {
        void onLoadMore(int totalItem);
    }
}
