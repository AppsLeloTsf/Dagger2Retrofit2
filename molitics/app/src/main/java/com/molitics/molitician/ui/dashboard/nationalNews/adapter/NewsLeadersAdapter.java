package com.molitics.molitician.ui.dashboard.nationalNews.adapter;

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

import com.molitics.molitician.interfaces.LeaderAdapterInterface;
import com.molitics.molitician.ui.dashboard.leader.AllLeaderModel;
import com.molitics.molitician.ui.dashboard.nationalNews.interfacess.OnNewsItemClick;
import com.molitics.molitician.util.Constant;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by rahul on 6/20/2016.
 */

public class NewsLeadersAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<AllLeaderModel> feedItemList;
    private Context mContext;

    private OnNewsItemClick onNewsItemClick;
    private LeaderAdapterInterface.LeaderClickListener adapterInterface;

    public NewsLeadersAdapter(Context context, List<AllLeaderModel> feedItemList, OnNewsItemClick onNewsItemClick, LeaderAdapterInterface.LeaderClickListener adapterInterface, int from) {
        this.feedItemList = feedItemList;
        this.mContext = context;
        this.onNewsItemClick = onNewsItemClick;
        this.adapterInterface = adapterInterface;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View primary_view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.add_recycler_news_horizontal, parent, false);
        return new ViewHolder(primary_view);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.leader_name)
        TextView leader_name;
        @BindView(R.id.leader_position)
        TextView leader_position;
        @BindView(R.id.trending_image)
        CircleImageView trending_image;
        @BindView(R.id.ll_main)
        LinearLayout ll_main;
        @BindView(R.id.verifiedLeaderView)
        ImageView verifiedLeaderView;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int position) {
        final AllLeaderModel leaderModel = feedItemList.get(position);
        final ViewHolder mViewHolder = (ViewHolder) viewHolder;

        if (TextUtils.isEmpty(leaderModel.getProfileImage())) {
            mViewHolder.trending_image.setImageResource(R.drawable.sample_image);
        } else {
            Picasso.with(mContext).load(leaderModel.getProfileImage()).placeholder(R.drawable.sample_image).into(mViewHolder.trending_image);
        }
        mViewHolder.leader_name.setText(leaderModel.getName());
       // mViewHolder.leader_position.setText(leaderModel.getPosition());
        mViewHolder.ll_main.setOnClickListener(v -> onNewsItemClick.onNewsRecyclerClick(position, Constant.Condition.INTERNATIONAL_LEADER));
        if (leaderModel.getIsVerify()==1){
            mViewHolder.verifiedLeaderView.setVisibility(View.VISIBLE);
        }else {
            mViewHolder.verifiedLeaderView.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return feedItemList.size();
    }
}
