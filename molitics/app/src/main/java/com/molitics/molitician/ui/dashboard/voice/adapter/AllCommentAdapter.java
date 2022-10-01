package com.molitics.molitician.ui.dashboard.voice.adapter;

import android.content.Context;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.molitics.molitician.R;
import com.molitics.molitician.ui.dashboard.voice.model.CommentModel;
import com.molitics.molitician.util.Constant;
import com.molitics.molitician.util.ExtraUtils;
import com.molitics.molitician.util.PrefUtil;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by rahul on 21/11/17.
 */

public class AllCommentAdapter extends RecyclerView.Adapter<AllCommentAdapter.CommentViewHolder> {

    private List<CommentModel> commentModels = new ArrayList<>();
    private Context mContext;
    private CommentAdapterListener commentAdapterListener;

    public interface CommentAdapterListener {
        void onLoadMore(int count);

        void onCreatorImageClick(int position, int user_id, int replyLeader);

        void onLongClick(Context mContext, View mView, int position, int comment_id, String text);
    }

    public AllCommentAdapter(Context mContext, CommentAdapterListener commentAdapterListener) {
        this.mContext = mContext;
        this.commentAdapterListener = commentAdapterListener;
    }

    public void addCommentBottom(CommentModel commentModel) {
        this.commentModels.add(commentModel);
        notifyDataSetChanged();
    }

    public void editCommentModel(int position, CommentModel commentModel) {
        commentModel.setEdited(1);
        commentModels.set(position, commentModel);
        notifyDataSetChanged();
    }

    public void deleteComment(int position) {
        commentModels.remove(position);
        notifyDataSetChanged();
    }

    public void addAllComments(List<CommentModel> commentModels) {
        Collections.reverse(commentModels);
        this.commentModels.addAll(commentModels);
        notifyDataSetChanged();
    }

    @Override
    public CommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_comments, parent, false);
        return new CommentViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(CommentViewHolder holder, int position) {

        CommentModel commentModel = commentModels.get(position);

        holder.user_name.setText(commentModel.getName());
        holder.user_comment.setText(commentModel.getComment());
        holder.time_txt.setText(commentModel.getTime());
        Linkify.addLinks(holder.user_comment, Linkify.WEB_URLS);
        if (commentModel.getLeaderReply() == 1) {
            holder.reply_dummy_txt.setVisibility(View.VISIBLE);
            holder.user_name.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_green_tick, 0);
        } else {
            holder.reply_dummy_txt.setVisibility(View.GONE);
            holder.user_name.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        }

        if (TextUtils.isEmpty(commentModel.getImage())) {
            holder.user_image.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.sample_image));

        } else {
            if (commentModel.getImage().contains("http"))
                Picasso.with(mContext).load(commentModel.getImage()).into(holder.user_image);
            else
                ExtraUtils.setBitmapImage(mContext, holder.user_image, commentModel.getImage());
        }
        if (position == 0 && commentModels.size() > 9) {
            commentAdapterListener.onLoadMore(commentModels.size());
        }
        if (commentModel.getEdited() == 1) {
            holder.is_edited_view.setVisibility(View.VISIBLE);
        } else {
            holder.is_edited_view.setVisibility(View.GONE);
        }

        holder.user_image.setOnClickListener(v -> commentAdapterListener.onCreatorImageClick(position, commentModel.getUserId(), commentModel.getLeaderReply()));

        holder.user_name.setOnClickListener(v -> commentAdapterListener.onCreatorImageClick(position, commentModel.getUserId(), commentModel.getLeaderReply()));
        holder.ll_main.setOnClickListener(v -> {
            if (commentModel.getUserId() == PrefUtil.getInt(Constant.PreferenceKey.USER_ID))
                commentAdapterListener.onLongClick(mContext, holder.ll_main, position, commentModel.getId(), commentModel.getComment());
        });
    }

    @Override
    public int getItemCount() {
        return commentModels.size();
    }

    public static class CommentViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.user_image)
        CircleImageView user_image;
        @BindView(R.id.user_name)
        TextView user_name;
        @BindView(R.id.user_comment)
        TextView user_comment;
        @BindView(R.id.time_txt)
        TextView time_txt;
        @BindView(R.id.ll_main)
        LinearLayout ll_main;
        @BindView(R.id.is_edited_view)
        TextView is_edited_view;

        @BindView(R.id.reply_dummy_txt)
        TextView reply_dummy_txt;
        @BindView(R.id.leader_star_view)
        ImageView leader_star_view;

        public CommentViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
