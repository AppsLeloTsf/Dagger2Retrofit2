package com.ca_dreamers.cadreamers.adapter.top_20;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ca_dreamers.cadreamers.R;
import com.ca_dreamers.cadreamers.models.top_20.Datum;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdapterTop20List extends RecyclerView.Adapter<AdapterTop20List.TopicViewHolder> {

    private final List<Datum> tModels;

    public AdapterTop20List(List<Datum> tModels) {
        this.tModels = tModels;
    }

    @NonNull
    @Override
    public TopicViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.top_20_item, viewGroup, false);
        return new TopicViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TopicViewHolder topicViewHolder, final int i) {
        final Datum tModel = tModels.get(i);
        final String strTop20Des = tModel.getDescription();

            topicViewHolder.tvTop20Title.setText(strTop20Des);

    }

    @Override
    public int getItemCount() {
        return tModels.size();
    }

    public static class TopicViewHolder extends RecyclerView.ViewHolder{
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.tvTop20Title)
        protected TextView tvTop20Title;



        public TopicViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
