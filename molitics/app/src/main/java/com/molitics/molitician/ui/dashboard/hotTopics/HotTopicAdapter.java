package com.molitics.molitician.ui.dashboard.hotTopics;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.molitics.molitician.R;
import com.molitics.molitician.ui.dashboard.hotTopics.model.HotTopicModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by rahul on 27/11/17.
 */

public class HotTopicAdapter extends RecyclerView.Adapter<HotTopicAdapter.HotTopicViewHolder> {

    private List<HotTopicModel> hotTopicModels = new ArrayList<>();

    private HotTopicListener hotTopicListener;

    public HotTopicAdapter(HotTopicListener hotTopicListener) {
        this.hotTopicListener = hotTopicListener;
    }

    public interface HotTopicListener {

        void onHotTopicClick(int position, int id, String tag, String extra);

        default void onFollowChannelClick(int channelId, int acton) {
        }
    }

    public void addHotTopics(List<HotTopicModel> hotTopicModels) {
        this.hotTopicModels.addAll(hotTopicModels);
        notifyDataSetChanged();
    }

    public int getHotTopicSize() {
        return hotTopicModels.size();
    }

    @Override
    public HotTopicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_hot_topic, parent, false);
        return new HotTopicViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(HotTopicViewHolder holder, int position) {

        HotTopicModel hotTopicModel = hotTopicModels.get(position);
        holder.hot_topic.setText(hotTopicModel.getTag());
        holder.main_hot_topic.setOnClickListener(v -> hotTopicListener.onHotTopicClick(position, hotTopicModel.getId(), hotTopicModel.getTag(), hotTopicModel.getSourceLogo()));
    }

    @Override
    public int getItemCount() {
        return hotTopicModels.size();
    }

    public class HotTopicViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.hot_topic)
        TextView hot_topic;
        @BindView(R.id.main_hot_topic)
        ConstraintLayout main_hot_topic;

        public HotTopicViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
