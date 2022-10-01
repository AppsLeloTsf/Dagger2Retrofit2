package com.ca_dreamers.cadreamers.adapter.my_orders.course;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ca_dreamers.cadreamers.R;
import com.ca_dreamers.cadreamers.activity.VimeoPlayerActivity;
import com.ca_dreamers.cadreamers.models.my_orders.course_details.chapters.Topic;
import com.ca_dreamers.cadreamers.models.my_orders.course_details.chapters.topics.Content;
import com.ca_dreamers.cadreamers.models.my_orders.course_details.chapters.topics.Data;
import com.ca_dreamers.cadreamers.utils.Constant;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class AdapterTopicVideo extends RecyclerView.Adapter<AdapterTopicVideo.TopicViewHolder> {

    private Context tContext;
    private List<Content> tModels;
    private String strCatId;
    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();


    public AdapterTopicVideo(List<Content> tModels, Context tContext) {
        this.tModels = tModels;
        this.tContext = tContext;
        this.strCatId = strCatId;
    }

    @NonNull
    @Override
    public TopicViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_chapter_topic, viewGroup, false);

        return new TopicViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TopicViewHolder topicViewHolder, final int i) {
        final Content tModel = tModels.get(i);
        final String strTopicIdId = tModel.getId();
        final String strCourseTitle = tModel.getTitle();

            topicViewHolder.tvTopicContentTitle.setText(strCourseTitle);


            topicViewHolder.rlChapterTopic.setOnClickListener(v -> {

                Intent intent = new Intent(tContext, VimeoPlayerActivity.class);
                intent.putExtra(Constant.CONTENT_ID, tModel.getId());
                intent.putExtra(Constant.CONTENT_TITLE, tModel.getTitle());
                intent.putExtra(Constant.CONTENT_DURATION, tModel.getVideoContent().getDuration());
                intent.putExtra(Constant.CONTENT_EMBED, tModel.getVideoContent().getEmbed());
                tContext.startActivity(intent);

            });

    }

    @Override
    public int getItemCount() {
        return tModels.size();
    }

    public class TopicViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tvTopicContentTitle)
        protected TextView tvTopicContentTitle;

        @BindView(R.id.rlChapterTopic)
        protected RelativeLayout rlChapterTopic;
        public TopicViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
