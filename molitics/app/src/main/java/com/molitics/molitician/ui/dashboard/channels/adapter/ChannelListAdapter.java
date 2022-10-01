package com.molitics.molitician.ui.dashboard.channels.adapter;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.molitics.molitician.R;
import com.molitics.molitician.ui.dashboard.hotTopics.HotTopicAdapter;
import com.molitics.molitician.ui.dashboard.hotTopics.model.HotTopicModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChannelListAdapter extends RecyclerView.Adapter<ChannelListAdapter.ChanneListlViewHolder> {

    private List<HotTopicModel> hotTopicModels = new ArrayList<>();

    private HotTopicAdapter.HotTopicListener hotTopicListener;

    public ChannelListAdapter(HotTopicAdapter.HotTopicListener hotTopicListener) {
        this.hotTopicListener = hotTopicListener;
    }

    public void addHotTopics(List<HotTopicModel> hotTopicModels) {
        this.hotTopicModels.addAll(hotTopicModels);
        notifyDataSetChanged();
    }

    public void clearData(){
        hotTopicModels.clear();
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ChanneListlViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_channel_list, parent, false);
        return new ChanneListlViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull ChanneListlViewHolder holder, int position) {


        HotTopicModel hotTopicModel = hotTopicModels.get(position);
        holder.sourceName.setText(hotTopicModel.getSource());
        if (hotTopicModel.isFollow()) {
            holder.followButton.setText(holder.sourceName.getContext().getString(R.string.following));
        } else {
            holder.followButton.setText(holder.sourceName.getContext().getString(R.string.follow_tag));
        }

        if (!TextUtils.isEmpty(hotTopicModel.getSourceLogo())) {
            Picasso.with(holder.sourceLogo.getContext()).load(hotTopicModel.getSourceLogo()).into(holder.sourceLogo);
        } else
            holder.sourceLogo.setImageResource(R.drawable.sample_image);

        holder.followButton.setOnClickListener(v -> {
            // temp code, change in follow
            int action;
            if (hotTopicModel.isFollow()) {
                action = 0;
                hotTopicModel.setFollow(false);
            } else {
                action = 1;
                hotTopicModel.setFollow(true);
            }

            hotTopicListener.onFollowChannelClick(hotTopicModel.getId(), action);
            notifyDataSetChanged();
        });
        holder.channelMain.setOnClickListener(v -> hotTopicListener.onHotTopicClick(position, hotTopicModel.getId(), hotTopicModel.getSource(), hotTopicModel.getSourceLogo()));
    }

    @Override
    public int getItemCount() {
        return hotTopicModels.size();
    }

    public class ChanneListlViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.sourceLogo)
        ImageView sourceLogo;
        @BindView(R.id.sourceName)
        TextView sourceName;
        @BindView(R.id.channelMain)
        ConstraintLayout channelMain;
        @BindView(R.id.followButton)
        TextView followButton;

        public ChanneListlViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
