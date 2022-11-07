package com.ca_dreamers.cadreamers.adapter.feedback;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ca_dreamers.cadreamers.R;
import com.ca_dreamers.cadreamers.models.feedback.Datum;
import com.ca_dreamers.cadreamers.storage.SharedPrefManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class AdapterFeedback extends RecyclerView.Adapter<AdapterFeedback.FeedbackHolder> {

    private SharedPrefManager sharedPrefManager;
    private String strUserId;
    private String strAddId;
    private  AlertDialog.Builder builder;

    private Context tContext;
    private List<Datum> tModels;
    private String strCatId;

    public AdapterFeedback(List<Datum> tModels, Context tContext) {
        this.tModels = tModels;
        this.tContext = tContext;
        this.strCatId = strCatId;
    }

    @NonNull
    @Override
    public FeedbackHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_feedback, viewGroup, false);

        sharedPrefManager = new SharedPrefManager(tContext);
        strUserId = sharedPrefManager.getUserId();
        builder = new AlertDialog.Builder(view.getContext());

        return new FeedbackHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull FeedbackHolder feedbackHolder, final int i) {
        final Datum tModel = tModels.get(i);

            feedbackHolder.tvFeedbackTitle.setText(tModel.getName());
            feedbackHolder.tvFeedbackDescription.setText(Html.fromHtml(tModel.getDescription()));
        Glide.with(tContext)
                .load(tModel.getImage())
                .into(feedbackHolder.ivFeedbackIcon);


    }

    @Override
    public int getItemCount() {
        return tModels.size();
    }

    public class FeedbackHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tvFeedbackTitle)
        protected TextView tvFeedbackTitle;
        @BindView(R.id.tvFeedbackDescription)
        protected TextView tvFeedbackDescription;
        @BindView(R.id.ivFeedbackIcon)
        protected ImageView ivFeedbackIcon;

        public FeedbackHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
